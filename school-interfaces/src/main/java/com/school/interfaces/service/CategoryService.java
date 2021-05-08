package com.school.interfaces.service;

import com.school.interfaces.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 新增类目
     * @param category
     * @return
     */
    Category create(Category category);

    /**
     * 删除类目信息，同时需要删除类目关联的品牌信息
     * @param category
     * @return
     */
    Category delete(Category category);

    /**
     * 更新类目信息，其关联的信息不变
     * @param category
     * @return
     */
    Category update(Category category);

    /**
     * 查询所有类目信息，主要显示于首页
     * @return
     */
    List<Category> queryAll();

    /**
     * 查询单个类目信息
     * @param category
     * @return
     */
    Category query(Category category);
}
