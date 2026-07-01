package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.CacheConstants;
import com.example.school.common.ResultCode;
import com.example.school.entity.User;
import com.example.school.repository.IUserRepository;
import com.example.school.service.IAuthService;
import com.example.school.service.RedisService;
import com.example.school.vo.LoginVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements IAuthService {

    /** Redis key 前缀：access token */
    public static final String ACCESS_TOKEN_PREFIX = CacheConstants.AUTH_ACCESS_PREFIX;
    /** Redis key 前缀：refresh token（存储用户名） */
    public static final String REFRESH_TOKEN_PREFIX = CacheConstants.AUTH_REFRESH_PREFIX;
    /** Redis key 前缀：用户名 → refresh token 反查（登出时删除 refresh key 用） */
    private static final String USER_REFRESH_PREFIX = CacheConstants.AUTH_USER_REFRESH_PREFIX;

    /** accessToken 有效期：4 小时 */
    private static final long ACCESS_TTL_HOURS = 4;
    /** refreshToken 有效期：7 天 */
    private static final long REFRESH_TTL_DAYS = 7;

    private final IUserRepository userRepository;
    private final RedisService redisService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(IUserRepository userRepository, RedisService redisService,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginVO login(String username, String password) {
        // 1. 验证用户名密码（生产环境通过 HTTPS 传输）
        User user = userRepository.selectByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "账号或密码错误");
        }

        // 2. 生成双 Token
        return buildTokenPair(user);
    }

    @Override
    @Transactional
    public LoginVO register(String username, String password, String nickname) {
        // 1. 校验用户名是否已存在
        User existUser = userRepository.selectByUsername(username);
        if (existUser != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "账号已存在");
        }

        // 2. 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname != null && !nickname.isEmpty() ? nickname : username);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userRepository.insert(user);

        // 3. 注册成功后自动登录，生成双 Token
        return buildTokenPair(user);
    }

    @Override
    public LoginVO refresh(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "refreshToken 不能为空");
        }

        // 1. 校验 refreshToken 是否存在于 Redis
        String refreshKey = REFRESH_TOKEN_PREFIX + refreshToken;
        Object usernameObj = redisService.get(refreshKey);
        if (usernameObj == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "refreshToken 已过期，请重新登录");
        }
        String username = usernameObj.toString();

        // 2. 查询用户信息
        User user = userRepository.selectByUsername(username);
        if (user == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "用户不存在");
        }

        // 3. 删除旧 refreshToken
        redisService.delete(refreshKey);
        // 删除旧的用户反查 key
        redisService.delete(USER_REFRESH_PREFIX + username);

        // 4. 生成新的双 Token
        return buildTokenPair(user);
    }

    @Override
    public void logout(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return;
        }
        String accessKey = ACCESS_TOKEN_PREFIX + accessToken;
        // 1. 先读取 username，用于居后删除 refreshToken
        Object usernameObj = redisService.get(accessKey);
        // 2. 删除 accessToken
        redisService.delete(accessKey);
        // 3. 通过用户名反查 refreshToken 并删除
        if (usernameObj != null) {
            String username = usernameObj.toString();
            Object refreshTokenObj = redisService.get(USER_REFRESH_PREFIX + username);
            if (refreshTokenObj != null) {
                redisService.delete(REFRESH_TOKEN_PREFIX + refreshTokenObj.toString());
            }
            redisService.delete(USER_REFRESH_PREFIX + username);
        }
    }

    /**
     * 公共方法：生成 accessToken + refreshToken 并存入 Redis
     */
    private LoginVO buildTokenPair(User user) {
        String accessToken = UUID.randomUUID().toString().replace("-", "");
        String refreshToken = UUID.randomUUID().toString().replace("-", "");

        // access: key=auth:access:{token}, value=username, TTL=4小时
        redisService.set(ACCESS_TOKEN_PREFIX + accessToken,
                user.getUsername(), ACCESS_TTL_HOURS, TimeUnit.HOURS);

        // refresh: key=auth:refresh:{token}, value=username, TTL=7天
        redisService.set(REFRESH_TOKEN_PREFIX + refreshToken,
                user.getUsername(), REFRESH_TTL_DAYS, TimeUnit.DAYS);

        // 用户名 → 当前 refreshToken 反查 （登出时居用）
        redisService.set(USER_REFRESH_PREFIX + user.getUsername(),
                refreshToken, REFRESH_TTL_DAYS, TimeUnit.DAYS);

        LoginVO vo = new LoginVO();
        vo.setAccessToken(accessToken);
        vo.setRefreshToken(refreshToken);
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        return vo;
    }

    @Override
    public LoginVO getUser(String username) {
        User user = userRepository.selectByUsername(username);
        if (user == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "用户不存在");
        }
        LoginVO vo = new LoginVO();
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        return vo;
    }
}
