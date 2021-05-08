package com.school.good.elasticsearch.service.impl;

import com.netflix.discovery.converters.Auto;
import com.school.good.elasticsearch.repository.ProductRepository;
import com.school.good.elasticsearch.pojo.EsProduct;
import com.school.good.elasticsearch.pojo.EsProductRelateInfo;
import com.school.good.elasticsearch.service.EsProductService;
import com.school.good.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EsProductServiceImpl implements EsProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public int importAll() {
        List<EsProduct> esProducts = productMapper.getAllEsProductList(null);
        Iterable<EsProduct> iterable = productRepository.saveAll(esProducts);
        Iterator<EsProduct> iterator = iterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void delete(List<Long> productIds) {
        if (!CollectionUtils.isEmpty(productIds)) {
            ArrayList<EsProduct> list = new ArrayList<>();
            for (Long productId : productIds) {
                EsProduct esProduct = new EsProduct();
                esProduct.setProductId(productId);
                list.add(esProduct);
            }
            productRepository.deleteAll(list);
        }
    }

    @Override
    public EsProduct createEsProductById(Long productId) {
        EsProduct esProduct = null;
        if (productId != null) {
            List<EsProduct> list = productMapper.getAllEsProductList(productId);
            if (!CollectionUtils.isEmpty(list)) {
                esProduct = list.get(0);
            }
        }
        if (esProduct != null) {
            productRepository.save(esProduct);
        }
        return esProduct;
    }

    @Override
    public Page<EsProduct> searchByKeyWord(String keyword, String schoolName, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findByFlagOrProductDescAndSchoolNameAndAble(keyword, keyword, schoolName, true, pageable);
    }

    @Override
    public Page<EsProduct> searchByCategory(String categoryName, String schoolName, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findBySchoolNameAndCategoryNameAndAble(schoolName, categoryName, true, pageable);
    }

    @Override
    public Page<EsProduct> complexSearch(String keyword, Long brandId, Long categoryId, String schoolName, Integer pageNum, Integer pageSize, Integer sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //过滤
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("able", true));
        boolQueryBuilder.must(QueryBuilders.matchQuery("schoolName", schoolName));
        if (brandId != null || categoryId != null) {
            if (brandId != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("brandId", brandId));
            }
            if (categoryId != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryId", categoryId));
            }
        }
        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        //搜索
        if (StringUtils.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("productDesc", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("flag", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(5);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        //排序
        if(sort==1){
            //按新品从新到旧
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("lastUpdatedDateTime").order(SortOrder.DESC));
        }else if(sort==2){
            //按价格从低到高
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        }else if(sort==3){
            //按价格从高到低
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        }else{
            //按相关度
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        log.info("DSL:{}", searchQuery.getQuery().toString());
        SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(searchQuery, EsProduct.class);
        if(searchHits.getTotalHits()<=0){
            return new PageImpl<>(Collections.singletonList(null),pageable,0);
        }
        List<EsProduct> searchProductList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(searchProductList,pageable,searchHits.getTotalHits());
    }

    @Override
    public Page<EsProduct> recommendProductByProductId(Long productId, String schoolName, Integer pageNum, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        List<EsProduct> esProducts = productMapper.getAllEsProductList(productId);
        if (esProducts.size() >= 1) {
            //获取其该商品的查询信息
            EsProduct esProduct = esProducts.get(0);
            Long brandId = esProduct.getBrandId();
            Long categoryId = esProduct.getCategoryId();
            String flag = esProduct.getFlag();
            //构建权重
            ArrayList<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            if (brandId != null) {
                filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("brandId", brandId),
                        ScoreFunctionBuilders.weightFactorFunction(5)));
            }
            if (categoryId != null) {
                filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("categoryId", categoryId),
                        ScoreFunctionBuilders.weightFactorFunction(5)));
            }
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("productDesc", flag),
                    ScoreFunctionBuilders.weightFactorFunction(15)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("flag", flag),
                    ScoreFunctionBuilders.weightFactorFunction(15)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(5);
            //开始过滤相同的商品
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (productId != null) {
                boolQueryBuilder.mustNot(QueryBuilders.termQuery("productId", productId.longValue()));
            }
            boolQueryBuilder.must(QueryBuilders.matchQuery("schoolName", schoolName));
            boolQueryBuilder.must(QueryBuilders.matchQuery("able", true));
            //构建查询语句
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
            nativeSearchQueryBuilder.withPageable(pageable);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
            NativeSearchQuery build = nativeSearchQueryBuilder.build();
            SearchHits<EsProduct> hits = elasticsearchRestTemplate.search(build, EsProduct.class);
            if (hits.getTotalHits() <= 0) {
                return new PageImpl<>(Collections.singletonList(null), pageable, 0);
            }
            List<EsProduct> collect = hits.stream().map(SearchHit::getContent).collect(Collectors.toList());
            return new PageImpl<>(collect, pageable, collect.size());
        }
        return new PageImpl<>(Collections.singletonList(null));
    }

    @Override
    public EsProductRelateInfo searchRelateInfo(String keyword) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (keyword.isEmpty()) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        }else {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(keyword, "productDesc", "flag"));
        }
        //聚合搜索品牌名称
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("brandNames").field("brandName"));
        //聚合搜索类目名称
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("categoryNames").field("categoryName"));
        //查询
        NativeSearchQuery build = nativeSearchQueryBuilder.build();
        SearchHits<EsProduct> hits = elasticsearchRestTemplate.search(build, EsProduct.class);
        //把EsProduct转换为RelateInfo
        return convertEsProductToEsProductRelateInfo(hits);

    }

    /**
     * 把搜索结果的聚合结果（EsProduct）解析为EsProductRelateInfo类
     * @param hits
     * @return
     */
    private EsProductRelateInfo convertEsProductToEsProductRelateInfo(SearchHits<EsProduct> hits) {
        EsProductRelateInfo relateInfo = new EsProductRelateInfo();
        Map<String, Aggregation> asMap = hits.getAggregations().getAsMap();
        //根据名称获取brandNames聚合
        Aggregation bradNames = asMap.get("brandNames");
        ArrayList<String> brandNameList = new ArrayList<>();
        //把聚合的结果放入List集合中
        for (int i = 0; i < ((Terms) bradNames).getBuckets().size(); i++) {
            brandNameList.add(((Terms) bradNames).getBuckets().get(i).getKeyAsString());
        }
        relateInfo.setBrandNames(brandNameList);

        Aggregation categoryNames = asMap.get("categoryNames");
        ArrayList<String> categoryNameList = new ArrayList<>();
        for (int i = 0; i < ((Terms) categoryNames).getBuckets().size(); i++) {
            categoryNameList.add(((Terms) categoryNames).getBuckets().get(i).getKeyAsString());
        }
        relateInfo.setCategoryNames(categoryNameList);
        return relateInfo;
    }

}
