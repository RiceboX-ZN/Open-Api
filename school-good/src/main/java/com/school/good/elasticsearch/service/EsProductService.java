package com.school.good.elasticsearch.service;

import com.school.good.elasticsearch.pojo.EsProduct;
import com.school.good.elasticsearch.pojo.EsProductRelateInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EsProductService {

    /**
     * 导入数据库商品到ES中
     * @return
     */
    int importAll();

    /**
     * 根据商品ID，删除ES中的商品
     * @param productId
     */
    void delete(Long productId);

    /**
     * 批量删除
     * @param productIds
     */
    void delete(List<Long> productIds);


    /**
     * 根据数据库中的商品ID，把该商品信息创建到ES数据中
     * @param productId
     * @return
     */
    EsProduct createEsProductById(Long productId);

    /**
     * 根据keyword进行简单的搜索，默认每页20条数据
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsProduct> searchByKeyWord(String keyword, String schoolName, Integer pageNum, Integer pageSize);

    /**
     * 根据类目查询学校商品
     * @param categoryName
     * @param schoolName
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsProduct> searchByCategory(String categoryName, String schoolName, Integer pageNum, Integer pageSize);

    /**
     * 对学校商品进行复杂搜索，根据 关键词，商品ID，类目ID
     * @param keyword       关键词
     * @param brandId       商品ID
     * @param categoryId    类目ID
     * @param schoolName    学校名字
     * @param pageNum
     * @param pageSize
     * @param sort          排序
     * @return
     */
    Page<EsProduct> complexSearch(String keyword, Long brandId, Long categoryId, String schoolName, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 根据商品的ID,推荐出相关联的商品
     * @param productId
     * @param schoolName
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsProduct> recommendProductByProductId(Long productId, String schoolName, Integer pageNum, Integer pageSize);

    /**
     * 获取搜索相关的品牌和分类属性
     * @param keyword
     * @return
     */
    EsProductRelateInfo searchRelateInfo(String keyword);
}
