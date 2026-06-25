package com.example.school.common;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * 通用分页响应包装类
 */
@Data
public class PageResult<T> {

    private List<T> records;
    private long total;
    private int pageNum;
    private int pageSize;
    private int pages;

    /**
     * 将 PageHelper 的 Page 对象转换为 PageResult
     */
    public static <T> PageResult<T> of(List<T> list) {
        PageResult<T> result = new PageResult<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            result.setRecords(page.getResult());
            result.setTotal(page.getTotal());
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setPages(page.getPages());
        } else {
            result.setRecords(list);
            result.setTotal(list.size());
            result.setPageNum(1);
            result.setPageSize(list.size());
            result.setPages(1);
        }
        return result;
    }
}
