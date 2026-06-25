package com.example.school.service;

import com.example.school.vo.LoginVO;

public interface IAuthService {

    /**
     * 登录，验证账号密码，生成 accessToken + refreshToken 存入 Redis，返回登录信息
     */
    LoginVO login(String username, String password);

    /**
     * 注册新用户
     */
    LoginVO register(String username, String password, String nickname);

    /**
     * 无感刷新 Token：使用 refreshToken 换取新的 accessToken + refreshToken
     *
     * @param refreshToken 前端持有的 refresh token
     * @return 新的 LoginVO（含新 accessToken 和新 refreshToken）
     */
    LoginVO refresh(String refreshToken);

    /**
     * 登出，同时删除 Redis 中的 accessToken 和 refreshToken
     */
    void logout(String accessToken);

    /**
     * 根据用户名获取用户信息
     */
    LoginVO getUser(String username);
}
