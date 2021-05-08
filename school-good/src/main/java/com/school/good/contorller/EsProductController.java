package com.school.good.contorller;

import com.school.common.CommonPage;
import com.school.good.feign.UserRemoteService;
import com.school.good.elasticsearch.pojo.EsProduct;
import com.school.good.elasticsearch.pojo.EsProductRelateInfo;
import com.school.good.elasticsearch.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esProduct")
@Api(tags = "ES商品接口")
public class EsProductController {

    @Autowired
    private UserRemoteService userRemoteService;

    @Autowired
    private EsProductService esProductService;


    @GetMapping("/testOauth2")
    public ResponseEntity<String> testOauth(){
        return ResponseEntity.ok("返回信息。。。。");
    }

    @ApiOperation(value = "导入数据库商品到ES数据库中")
    @PostMapping("/importAll")
    public ResponseEntity<Integer> importAllProductDateToEs() {
        int i = esProductService.importAll();
        return ResponseEntity.ok(i);
    }

    @ApiOperation(value = "根据商品ID删除ES中的商品")
    @PostMapping("/deleteById")
    public ResponseEntity<Void> deleteEsProductById(Long productId) {
        esProductService.delete(productId);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "根据Ids批量删除ES中的商品信息")
    @PostMapping("/delete/batch")
    public ResponseEntity<Void> batchDeleteEsProduct(@RequestParam(value = "productIds") List<Long> productIds) {
        esProductService.delete(productIds);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "根据数据库中的商品ID，创建到ES数据中")
    @PostMapping("/create/es")
    public ResponseEntity<EsProduct> createEsProductById(Long productId) {
        EsProduct esProduct = esProductService.createEsProductById(productId);
        if (esProduct == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(esProduct);
    }

    @ApiOperation(value = "根据关键字进行搜索，默认从0页开始，每页20条数据,先查询登录用户所在学校商品")
    @PostMapping("/search/keyword")
    public ResponseEntity<CommonPage<EsProduct>> searchByKeyWord(@RequestParam(value = "keyword", required = false) String keyword,
                                                                 @RequestParam(value = "schoolName",required = false) String schoolName,
                                                                 @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        if (schoolName == null || !StringUtils.hasText(schoolName)) {
            schoolName = userRemoteService.currentUserInfoFormLogin().getSchoolName();
        }
        Page<EsProduct> esProductPage = esProductService.searchByKeyWord(keyword, schoolName, pageNum, pageSize);
        CommonPage<EsProduct> result = CommonPage.releasePage(esProductPage);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "根据类目查找学校商品，默认分页")
    @PostMapping("/search/category")
    public ResponseEntity<CommonPage<EsProduct>> searchByCategory(@RequestParam(value = "categoryName") String categoryName,
                                                                  @RequestParam(value = "schoolName", required = false) String schoolName,
                                                                  @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        if (schoolName == null || !StringUtils.hasText(schoolName)) {
            schoolName = userRemoteService.currentUserInfoFormLogin().getSchoolName();
        }
        Page<EsProduct> esProductPage = esProductService.searchByCategory(categoryName, schoolName, pageNum, pageSize);
        CommonPage<EsProduct> result = CommonPage.releasePage(esProductPage);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "进行综合搜索，可以根据关键词以及品牌以及类目搜索学校商品")
    @ApiImplicitParam(name = "sort",value = "排序字段，0->按照相关度，1->从新到旧，2->价格从底到高,3->价格从高到低",
    defaultValue = "0",allowableValues = "0,1,2,3",paramType = "query",dataType = "integer")
    @PostMapping("/search/complex")
    public ResponseEntity<CommonPage<EsProduct>> ComplexSearch(@RequestParam(value = "keyword", required = false) String keyword,
                                                               @RequestParam(value = "brandId", required = false) Long brandId,
                                                               @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                               @RequestParam(value = "schoolName", required = false) String schoolName,
                                                               @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                               @RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort) {

        if (schoolName == null || !StringUtils.hasText(schoolName)) {
            schoolName = userRemoteService.currentUserInfoFormLogin().getSchoolName();
        }
        Page<EsProduct> esProducts = esProductService.complexSearch(keyword, brandId, categoryId, schoolName, pageNum, pageSize,sort);
        CommonPage<EsProduct> result = CommonPage.releasePage(esProducts);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "根据商品ID，推荐其他商品")
    @PostMapping("/recommend/{productId}")
    public ResponseEntity<CommonPage<EsProduct>> recommendProductByProductId(@PathVariable Long productId,
                                                                             @RequestParam(value = "schoolName",required = false) String schoolName,
                                                                             @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (schoolName == null || !StringUtils.hasText(schoolName)) {
            schoolName = userRemoteService.currentUserInfoFormLogin().getSchoolName();
        }
        Page<EsProduct> esProducts = esProductService.recommendProductByProductId(productId, schoolName, pageNum, pageSize);
        CommonPage<EsProduct> result = CommonPage.releasePage(esProducts);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "获取搜索的相关品牌和分类信息")
    @PostMapping("/search/relateInfo")
    public ResponseEntity<EsProductRelateInfo> searchRelateInfo(String keyword) {
        EsProductRelateInfo relateInfo = esProductService.searchRelateInfo(keyword);
        return ResponseEntity.ok(relateInfo);
    }
}
