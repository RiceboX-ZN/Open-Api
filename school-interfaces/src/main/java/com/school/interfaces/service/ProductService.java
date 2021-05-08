package com.school.interfaces.service;


import com.school.interfaces.dto.UserDomainDto;
import com.school.interfaces.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    /**
     * 新增商品
     * @param product
     * @return
     */
    Product creat(Product product);

    /**
     * 查询所有二手商品信息
     *
     * @param product
     * @return
     */
    List<Product> queryAll(Product product);

    /**
     * 更新二手商品信息
     * @param product
     * @return
     */
    Product update(Product product);

    /**
     * 删除用户二手商品信息
     * @param product
     * @return
     */
    Product delete(Product product);
}
