package com.school.good.mapper;

import com.school.good.elasticsearch.pojo.EsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品数据持久层
 */
@Mapper
public interface ProductMapper{


    /**
     * 获取指定ID的搜索商品
     *
     * @param productId
     * @return
     */
    List<EsProduct> getAllEsProductList(Long productId);

}
