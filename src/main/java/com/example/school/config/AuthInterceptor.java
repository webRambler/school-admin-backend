package com.example.school.config;

import com.example.school.common.Result;
import com.example.school.common.ResultCode;
import com.example.school.service.impl.AuthServiceImpl;
import com.example.school.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Token 鉴权拦截器：从请求 Header 中读取 accessToken，校验 Redis 中是否存在。
 *
 * <p>双 Token 方案说明：
 * - accessToken：4 小时短效，放入请求 Header Authorization。
 * - refreshToken：7 天长效，放入请求 Header Refresh-Token，仅用于调用 /api/auth/refresh。
 * - 当 accessToken 返回 401 时，前端自动加 refreshToken 调用 /api/auth/refresh 得到新 Token。
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    public AuthInterceptor(RedisService redisService, ObjectMapper objectMapper) {
        this.redisService = redisService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 优先读取 Authorization Header，其次读取名为 token 的 Header
        String accessToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(accessToken)) {
            accessToken = request.getHeader("token");
        }

        if (StringUtils.hasText(accessToken)) {
            // 校验 accessToken 是否存在（短效 4 小时，不续期）
            Object value = redisService.get(AuthServiceImpl.ACCESS_TOKEN_PREFIX + accessToken);
            if (value != null) {
                return true;
            }
        }

        // accessToken 无效或不存在，返回 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        Result<Void> result = Result.error(ResultCode.UNAUTHORIZED, "accessToken 已过期，请使用 refreshToken 刷新");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(result));
        }
        return false;
    }
}
