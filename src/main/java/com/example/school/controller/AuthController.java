package com.example.school.controller;

import com.example.school.common.Result;
import com.example.school.dto.LoginRequest;
import com.example.school.dto.RegisterRequest;
import com.example.school.service.IAuthService;
import com.example.school.vo.LoginVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    // 登录，返回 accessToken + refreshToken
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        LoginVO loginVO = authService.login(request.getUsername(), request.getPassword());
        return Result.success("登录成功", loginVO);
    }

    // 注册，注册成功后自动登录并返回 token
    @PostMapping("/register")
    public Result<LoginVO> register(@Valid @RequestBody RegisterRequest request) {
        LoginVO loginVO = authService.register(request.getUsername(), request.getPassword(), request.getNickname());
        return Result.success("注册成功", loginVO);
    }

    // 无感刷新 Token：使用 refreshToken 换取新的 accessToken + refreshToken
    @PostMapping("/refresh")
    public Result<LoginVO> refresh(
            @RequestHeader(value = "Refresh-Token", required = false) String refreshToken) {
        LoginVO loginVO = authService.refresh(refreshToken);
        return Result.success("刷新成功", loginVO);
    }

    // 登出，清除 accessToken 和 refreshToken
    @PostMapping("/logout")
    public Result<Void> logout(
            @RequestHeader(value = "Authorization", required = false) String accessToken) {
        authService.logout(accessToken);
        return Result.success("登出成功", null);
    }

    @GetMapping("/user/{username}")
    public Result<LoginVO> getUser(@PathVariable String username) {
        LoginVO loginVO = authService.getUser(username);
        return Result.success("获取用户成功", loginVO);
    }
}
