package com.school.interfaces.controller;

import com.school.interfaces.dto.UserDomainDto;
import com.school.interfaces.entity.Product;
import com.school.interfaces.feign.UserRemoteService;
import com.school.interfaces.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Api(tags = "二手商品接口")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRemoteService userRemoteService;

    @PostMapping
    @ApiOperation(value = "用户添加二手商品")
    public ResponseEntity<Product> create(@Validated @RequestBody Product product) {
        UserDomainDto userDomainDto = userRemoteService.currentUserInfoFormLogin();
        Assert.notNull(product.getUserId(), "userId为空，产品找不到所属用户，添加失败");
        Assert.notNull(userDomainDto, "当前没有用户登录，添加新商品失败");
        Assert.notNull(userDomainDto.getUserId(), "userId为空，添加新商品失败");
        if (product.getSchoolName() == null) {
            product.setSchoolName(userDomainDto.getSchoolName());
        }
        return ResponseEntity.ok(productService.creat(product));
    }

    @PostMapping("/user/products")
    @ApiOperation(value = "查询当前登录用户的所有二手商品信息")
    public ResponseEntity<List<Product>> queryAllForUser() {
        UserDomainDto userDomainDto = userRemoteService.currentUserInfoFormLogin();
        Assert.notNull(userDomainDto, "当前没有用户登录，查询失败");
        Assert.notNull(userDomainDto.getUserId(), "userId为空，查询失败");

        Product product = new Product().setUserId(userDomainDto.getUserId());
        return ResponseEntity.ok(productService.queryAll(product));
    }

    @PutMapping()
    @ApiOperation(value = "修改二手商品信息")
    public ResponseEntity<Product> update(@Validated @RequestBody Product product) {
        UserDomainDto userDomainDto = userRemoteService.currentUserInfoFormLogin();
        Assert.notNull(product.getUserId(), "userId为空，产品找不到所属用户，更新失败");
        Assert.notNull(userDomainDto, "当前没有用户登录");
        Assert.notNull(userDomainDto.getUserId(), "userId为空，更新失败");
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping()
    @ApiOperation(value = "删除商品信息")
    public ResponseEntity<Product> delete(@Validated @RequestBody Product product) {
        Assert.notNull(product.getUserId(), "userId为空，产品找不到所属用户，删除失败");
        return ResponseEntity.ok(productService.delete(product));
    }

}
