package com.school.interfaces.controller;

import com.school.interfaces.entity.Category;
import com.school.interfaces.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Api(tags = "类目接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @ApiOperation(value = "查询所有类目信息，主要显示于首页，用于用户进行类目筛选")
    @PostMapping("/categories")
    public ResponseEntity<List<Category>> queryAll() {
        return ResponseEntity.ok(categoryService.queryAll());
    }

    @PostMapping()
    @ApiOperation(value = "新增类目，此时暂不关联品牌")
    public ResponseEntity<Category> create(@Validated @RequestBody Category category) {
        Assert.notNull(category, "类目信息为空，新增失败");
        category = categoryService.create(category);
        if (category == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(category);
    }

    @DeleteMapping()
    @ApiOperation(value = "删除类目信息")
    public ResponseEntity<Category> delete(@Validated @RequestBody Category category) {
        Assert.notNull(category, "类目信息为空，删除失败");
        category = categoryService.delete(category);
        if (category == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping
    @ApiOperation(value = "根据主键更新类目信息")
    public ResponseEntity<Category> update(@Validated @RequestBody Category category) {
        Assert.notNull(category, "类目信息为空，更新失败");
        category = categoryService.update(category);
        if (category == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping("/query/category")
    public ResponseEntity<Category> queryCategory(@Validated @RequestBody Category category) {
        Assert.notNull(category, "category为空，查询失败");
        category = categoryService.query(category);
        if (category == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(category);
    }
}
