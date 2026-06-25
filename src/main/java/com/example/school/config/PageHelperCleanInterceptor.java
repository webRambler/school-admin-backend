package com.example.school.config;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PageHelper 分页状态清理拦截器。
 * 在每个请求开始时清理上一个请求可能泄漏的 ThreadLocal 分页标记，
 * 防止 PageHelper.startPage() 影响非分页查询。
 */
@Component
public class PageHelperCleanInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        PageHelper.clearPage();
        return true;
    }
}
