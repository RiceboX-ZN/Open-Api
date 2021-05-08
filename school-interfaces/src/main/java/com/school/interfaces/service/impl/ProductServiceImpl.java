package com.school.interfaces.service.impl;

import com.school.interfaces.dto.UserDomainDto;
import com.school.interfaces.entity.Category;
import com.school.interfaces.entity.CategoryBrand;
import com.school.interfaces.entity.Product;
import com.school.interfaces.mapper.CategoryBrandMapper;
import com.school.interfaces.mapper.ProductMapper;
import com.school.interfaces.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    private CategoryBrandMapper categoryBrandMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Product creat(Product product) {
        //插入商品
        product.setCreationDataTime(new Date());
        productMapper.insertSelective(product);
        //关联品牌和类目
        if (product.getBrandId() != null && product.getCategoryId() != null) {
            CategoryBrand categoryBrand = new CategoryBrand().setBrandId(product.getBrandId())
                    .setCategoryId(product.getCategoryId());
            //如果该品牌和类目已经关联，则不进行关联操作
            if (categoryBrandMapper.select(categoryBrand) == null) {
                categoryBrandMapper.insertSelective(categoryBrand);
            }
        }
        //重新查询并放回
        return productMapper.selectByPrimaryKey(product.getProductId());
    }

    @Override
    public List<Product> queryAll(Product product) {
        product.setAble(true);
        return this.productMapper.select(product);
    }

    @Override
    public Product update(Product product) {
        Assert.notNull(product, "productId为空，更新失败");
        productMapper.updateByPrimaryKeySelective(product);
        return productMapper.selectByPrimaryKey(product);
    }

    @Override
    public Product delete(Product product) {
        Assert.notNull(product.getProductId(), "productId为空，删除失败");
        product.setAble(false);
        productMapper.updateByPrimaryKeySelective(product);
        return productMapper.selectByPrimaryKey(product);
    }

}
