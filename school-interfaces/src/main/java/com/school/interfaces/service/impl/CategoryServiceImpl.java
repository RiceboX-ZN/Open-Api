package com.school.interfaces.service.impl;

import com.school.interfaces.entity.Category;
import com.school.interfaces.entity.CategoryBrand;
import com.school.interfaces.mapper.CategoryBrandMapper;
import com.school.interfaces.mapper.CategoryMapper;
import com.school.interfaces.service.CategoryService;
import com.school.interfaces.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * redis使用Hash结构
 */
@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    public RedisService redisService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Category create(Category category) {
        //写操作:先更新DB，再删除缓存
        Category temp = new Category().setCategoryNameZh(category.getCategoryNameZh());
        temp = categoryMapper.selectOne(temp);
        if (temp != null) {
            return null;
        }
        //插入
        category.setCreationDataTime(new Date());
        categoryMapper.insertSelective(category);
        Category var1 = categoryMapper.selectByPrimaryKey(category.getCategoryId());
        //删除缓存,直接把整个hash结构删除，防止出现数据不一致的情况
        deleteCache();
        return var1;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public Category delete(Category category) {
        Assert.notNull(category.getCategoryId(), "categoryId 为空，删除失败");
        categoryMapper.deleteByPrimaryKey(category.getCategoryId());
        CategoryBrand categoryBrand = new CategoryBrand().setCategoryId(category.getCategoryId());
        categoryBrandMapper.delete(categoryBrand);
        //删除cache
        String key = Category.CATEGORY_REDIS_KEY;
        if (redisService.hasKey(key)) {
            String hashKey = key + ":" + category.getCategoryId();
            if (redisService.hHashKey(key, hashKey)) {
                redisService.hDel(key, hashKey);
            }
        }
        return category;
    }

    @Override
    public Category update(Category category) {
        Assert.notNull(category.getCategoryId(), "更新失败，categoryId 为空");
        Category var1 = categoryMapper.selectByPrimaryKey(category.getCategoryId());
        //根据ID查询为空，则说明数据不存在
        if (var1 == null) {
            return null;
        }
        categoryMapper.updateByPrimaryKeySelective(category);
        //删除缓存,直接把整个hash结构删除，防止出现数据不一致的情况
        deleteCache();
        return categoryMapper.selectByPrimaryKey(category.getCategoryId());
    }

    @Override
    public List<Category> queryAll() {
        String key = Category.CATEGORY_REDIS_KEY;

        //命中缓存
        if (redisService.hasKey(key)) {
            List<Category> list = new ArrayList<>();
            //获取整个hash结构
            Map<Object, Object> map = redisService.hGetAll(key);
            for (Map.Entry<Object, Object> m : map.entrySet()) {
                list.add((Category) m.getValue());
            }
            return list;
        }
        //没有命中缓存，查询DB
        List<Category> categories = categoryMapper.selectAll();
        //再把数据插入cache
        Map<Object, Object> map = new HashMap<>();
        categories.forEach(category -> {
            map.put(key + ":" + category.getCategoryId(), category);
        });
        redisService.hSetAll(key,map);

        return categories;
    }

    @Override
    public Category query(Category category) {
        Assert.notNull(category.getCategoryId(), "categoryId不能为空，查询失败");
        //先查询cache
        String key = Category.CATEGORY_REDIS_KEY;
        if (redisService.hasKey(key)) {
            String hashKey = key + ":" + category.getCategoryId();
            if (redisService.hHashKey(key, hashKey)) {
                return (Category) redisService.hGet(key, hashKey);
            }
        }
        //cache没有查到，查DB
        Category result = categoryMapper.selectByPrimaryKey(category.getCategoryId());
        //把新数据存储到cache里面,有hash结构才存储，没有则不用存储
        if (redisService.hasKey(key)) {
            String hashKey = key + ":" + category.getCategoryId();
            redisService.hSet(key, hashKey, result);
        }

        return result;
    }


    public void deleteCache() {
        //删除整个hash
        String key = Category.CATEGORY_REDIS_KEY;
        if (redisService.hasKey(key)) {
            redisService.delete(key);
        }
    }
}
