package com.school.common;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public class CommonPage<T> implements Serializable {

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 总页数
     */
    private Integer totalPages;


    /**
     * 当前页大小
     */
    private Integer pageSize;

    /**
     * 总条数
     */
    private Long totalElements;

    /**
     * 分页数据
     */
    private List<T> list;
;

    public static <T> CommonPage<T> releasePage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<>();
        result.setTotalPages(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setList(pageInfo.getContent());
        result.setTotalElements(pageInfo.getTotalElements());
        return result;
    }


    public CommonPage() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
