package com.school.interfaces.mapper;

import com.school.interfaces.entity.CategoryBrand;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface CategoryBrandMapper extends Mapper<CategoryBrand> {

    void deleteByBrandIdOrCategoryId(CategoryBrand categoryBrand);

}

