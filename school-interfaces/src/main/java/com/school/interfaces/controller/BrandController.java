package com.school.interfaces.controller;

import com.school.interfaces.entity.Brand;
import com.school.interfaces.entity.Category;
import com.school.interfaces.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "品牌接口")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;


    /**
     * 暂未使用缓存
     * @return
     */
    @PostMapping("/brands")
    @ApiOperation(value = "查询所有品牌")
    public ResponseEntity<List<Brand>> queryAll() {
        return ResponseEntity.ok(brandService.queryAll());
    }

    @ApiOperation(value = "查询单个品牌信息")
    @PostMapping("/query/brand")
    public ResponseEntity<Brand> query(@Validated @RequestBody Brand brand) {
        Assert.notNull(brand, "brand为空，查询失败");
        brand = brandService.query(brand);
        if (brand == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(brand);
    }


    @PostMapping()
    @ApiOperation(value = "新增单个品牌信息")
    public ResponseEntity<Brand> create(@Validated @RequestBody Brand brand) {
        Assert.notNull(brand, "brand为空，新增失败");
        return ResponseEntity.ok(brandService.create(brand));
    }

    @PutMapping()
    @ApiOperation(value = "修改单个品牌信息")
    public ResponseEntity<Brand> update(@Validated @RequestBody Brand brand) {
        Assert.notNull(brand, "brand为空，修改失败");
        return ResponseEntity.ok(brandService.update(brand));
    }


    @DeleteMapping()
    @ApiOperation(value = "删除单个品牌信息")
    public ResponseEntity<Brand> delete(@Validated @RequestBody Brand brand) {
        Assert.notNull(brand, "brand为空，删除失败");
        return ResponseEntity.ok(brandService.delete(brand));
    }

    @ApiOperation(value = "把品牌与类目进行关联,已关联的类目不会再关联")
    @PostMapping("/relation/categories")
    public ResponseEntity<Integer> relationCategory(@Validated @RequestBody Brand brand,
                                                    @RequestParam(name = "categories") ArrayList<Long> categories) {
        Assert.notNull(brand, "brand为空，关联失败");
        Assert.notNull(categories, "categories为空，关联失败");

        Integer count = brandService.relation(brand, categories);
        return ResponseEntity.ok(count);
    }
}
