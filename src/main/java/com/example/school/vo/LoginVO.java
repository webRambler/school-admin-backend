package com.example.school.vo;

import lombok.Data;

/**
 * 登录成功返回给前端的视图对象
 * - accessToken：短效 Token（30 分钟），每次请求将此值放入 Authorization Header
 * - refreshToken：长效 Token（7 天），仅用于调用 /api/auth/refresh 换取新的 accessToken
 */
@Data
public class LoginVO {

    private String accessToken;
    private String refreshToken;
    private String username;
    private String nickname;
}
