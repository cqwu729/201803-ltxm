package com.snow.ly.blog.common.bean;

import java.util.ArrayList;
import java.util.List;

public class PageUserBean {

    private Long totalElements;
    private Integer totalPages;

    private List<UserBean> beans=new ArrayList<>();

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserBean> getBeans() {
        return beans;
    }

    public void setBeans(List<UserBean> beans) {
        this.beans = beans;
    }
}
