package com.example.school.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC 配置：注册 Token 鉴权拦截器
 * 拦截所有 /api/** 接口，仅放行登录接口 /api/auth/login
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final PageHelperCleanInterceptor pageHelperCleanInterceptor;

    public WebMvcConfig(AuthInterceptor authInterceptor, PageHelperCleanInterceptor pageHelperCleanInterceptor) {
        this.authInterceptor = authInterceptor;
        this.pageHelperCleanInterceptor = pageHelperCleanInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // PageHelper 清理拦截器最先执行，防止上次请求的分页状态泄漏
        registry.addInterceptor(pageHelperCleanInterceptor)
                .addPathPatterns("/api/**");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login", "/api/auth/register", "/api/auth/refresh");
    }
}
