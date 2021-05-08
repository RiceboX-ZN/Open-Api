package com.school.interfaces.service;

import com.school.interfaces.entity.Brand;
import com.school.interfaces.entity.Category;

import java.util.List;

public interface BrandService {

    /**
     * 查询所有品牌信息
     * @return
     */
    List<Brand> queryAll();

    /**
     * 查询单个品牌信息
     * @param brand
     * @return
     */
    Brand query(Brand brand);

    /**
     * 新增品牌信息
     * @param brand
     * @return
     */
    Brand create(Brand brand);

    /**
     * 更新单个品牌信息
     * @param brand
     * @return
     */
    Brand update(Brand brand);

    /**
     * 删除单个品牌信息
     * @param brand
     * @return
     */
    Brand delete(Brand brand);

    /**
     * 品牌关联类目
     * @param brand
     * @param categories
     * @return
     */
    Integer relation(Brand brand, List<Long> categories);
}
