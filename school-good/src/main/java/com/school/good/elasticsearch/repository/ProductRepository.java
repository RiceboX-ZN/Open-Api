package com.school.good.elasticsearch.repository;

import com.school.good.elasticsearch.pojo.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品数据持久层--ES
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<EsProduct, Long> {

    /**
     * 根据学校名称或者标志词语或者商品描述查询
     * @param schoolName    学校名称
     * @param flag          标志词语
     * @param ProductDesc   商品描述
     * @param page          分页信息
     * @return
     */
    Page<EsProduct> findByFlagOrProductDescAndSchoolNameAndAble(String flag, String ProductDesc,String schoolName,Boolean able, Pageable page);

    /**
     * 根据类目查找本校二手商品
     * @param schoolName        学校
     * @param categoryName      类目
     * @param able              是否上架
     * @return
     */
    Page<EsProduct> findBySchoolNameAndCategoryNameAndAble(String schoolName, String categoryName, Boolean able,Pageable page);
}
