package com.school.interfaces.service.impl;

import com.school.interfaces.entity.Brand;
import com.school.interfaces.entity.Category;
import com.school.interfaces.entity.CategoryBrand;
import com.school.interfaces.mapper.BrandMapper;
import com.school.interfaces.mapper.CategoryBrandMapper;
import com.school.interfaces.service.BrandService;
import com.school.interfaces.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<Brand> queryAll() {
        //先查询cache
        String key = Brand.BRAND_REDIS_KEY;
        if (redisService.hasKey(key)) {
            Map<Object, Object> map = redisService.hGetAll(key);
            List<Brand> list = new ArrayList<>();

            for (Map.Entry<Object, Object> m : map.entrySet()) {
                list.add((Brand) m.getValue());
            }
            return list;
        }

        //cache不存在，操作DB
        List<Brand> brands = brandMapper.selectAll();
        //插入最新数据
        Map<Object, Object> map = new HashMap<>();
        brands.forEach(brand -> {
            map.put(key + ":" + brand.getBrandId(), brand);
        });
        redisService.hSetAll(key,map);

        //return
        return brandMapper.selectAll();
    }

    @Override
    public Brand query(Brand brand) {
        Assert.notNull(brand.getBrandId(), "brandId为空，查询失败");
        //先查cache
        String key = Brand.BRAND_REDIS_KEY;
        String hashKey = key + ":" + brand.getBrandId();
        if (redisService.hasKey(key)) {
            if (redisService.hHashKey(key, hashKey)) {
                return (Brand) redisService.hGet(key, hashKey);
            }
        }
        //没有命中缓存，查DB
        Brand result = brandMapper.selectByPrimaryKey(new Brand().setBrandId(brand.getBrandId()));
        //hash存在，则把数据更新到cache
        if (redisService.hasKey(key)) {
            redisService.hSet(key, hashKey, result);
        }
        return result;
    }

    @Override
    public Brand create(Brand brand) {
        //先操作DB
        brand.setCreationDataTime(new Date());
        brandMapper.insertSelective(brand);
        brand = brandMapper.selectByPrimaryKey(brand.getBrandId());
        //删除整个hash结构
        deleteCache();
        return brand;
    }

    @Override
    public Brand update(Brand brand) {
        //先更新DB
        Assert.notNull(brand.getBrandId(), "brandId为空，更新失败");
        brandMapper.updateByPrimaryKeySelective(brand);
        Brand result = brandMapper.selectByPrimaryKey(brand.getBrandId());
        //删除缓存
        deleteCache();
        //返回更新信息
        return result;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public Brand delete(Brand brand) {
        Assert.notNull(brand.getBrandId(), "brandId为空，删除失败");
        //先操作DB
        brandMapper.deleteByPrimaryKey(new Brand().setBrandId(brand.getBrandId()));
        //删除类目和品牌的关联信息
        Example example = new Example(CategoryBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("brandId", brand.getBrandId());
        example.and(criteria);
        categoryBrandMapper.deleteByExample(example);
        //删除缓存
        deleteCache();
        //返回信息
        return brand;
    }

    @Override
    public Integer relation(Brand brand, List<Long> categories) {
        Assert.notNull(brand.getBrandId(), "brandId为空，关联失败");
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(brand.getBrandId());

        //保证线程安全
        AtomicInteger count = new AtomicInteger();
        categories.forEach(category -> {
            categoryBrand.setCategoryId(category);
            //检查是否关联过,如果没有关联，则进行关联操作
            List<CategoryBrand> select = categoryBrandMapper.select(categoryBrand);
            if (CollectionUtils.isEmpty(select)) {
                categoryBrand.setCreationDataTime(new Date());
                categoryBrandMapper.insertSelective(categoryBrand);
                categoryBrand.setCategoryBrandId(null);
                count.incrementAndGet();
            }
        });
        return count.intValue();
    }

    /**
     * 直接删除整个hash结构
     */
    private void deleteCache() {
        String key = Brand.BRAND_REDIS_KEY;
        if (redisService.hasKey(key)) {
            redisService.delete(key);
        }
    }

}
