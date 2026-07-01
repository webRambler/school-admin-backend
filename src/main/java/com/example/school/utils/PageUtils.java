package com.example.school.utils;

import com.github.pagehelper.PageHelper;

/**
 * 分页工具类：统一封装 PageHelper.startPage，并对分页参数做边界校验。
 */
public class PageUtils {

    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 500;

    private PageUtils() {
        // 工具类禁止实例化
    }

    /**
     * 启动分页，pageNum 和 pageSize 会被规范到合法范围。
     *
     * @param pageNum  页码（小于1时重置为1）
     * @param pageSize 每页大小（小于1时重置为默认值，超过上限时重置为上限）
     */
    public static void startPage(int pageNum, int pageSize) {
        int normalizedPageNum = pageNum < 1 ? DEFAULT_PAGE_NUM : pageNum;
        int normalizedPageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : Math.min(pageSize, MAX_PAGE_SIZE);
        PageHelper.startPage(normalizedPageNum, normalizedPageSize);
    }
}
