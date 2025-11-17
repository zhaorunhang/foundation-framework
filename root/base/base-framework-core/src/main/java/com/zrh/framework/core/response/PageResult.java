package com.zrh.framework.core.response;

import lombok.Data;

import java.util.List;

/**
* @description:  分页数据返回信息
* @author: zhouzhong
* @date: 2021/07/11 16:39
* @path: com.joyouth.ts.platform.baseinfo.base.PageResult
* @version: 1.0
*/
@Data
public class PageResult<T> {

    private Long total;
    private Integer totalPages;
    private Integer pageSize;
    private Integer pageNumber;
    private List<T> list;

    private PageResult(List<T> list, Long total, Integer pageSize, Integer pageNumber) {
        this.list = list;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPages = Math.toIntExact((total + pageSize - 1) / pageSize);
    }

    public static <T> PageResult<T> of(List<T> list, Long total, Integer pageSize, Integer pageNumber) {
        return new PageResult<>(list, total, pageSize, pageNumber);
    }
}
