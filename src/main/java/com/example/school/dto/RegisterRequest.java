package com.example.school.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @NotBlank(message = "账号不能为空")
    @Size(min = 3, max = 20, message = "账号长度为3-20个字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度为6-20个字符")
    private String password;

    @Size(max = 20, message = "昵称不能超过20个字符")
    private String nickname;
}
