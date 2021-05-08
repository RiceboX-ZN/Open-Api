package com.school.good.elasticsearch.pojo;

import com.school.good.elasticsearch.constant.EsDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(indexName = "schooltao_platform", type = "goods_product")
public class EsProduct extends EsDomain implements Serializable {


    @Id
    @ApiModelProperty(value = "主键，提供给其他表做外键", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long productId;

    @ApiModelProperty(value = "商品所属用户ID")
    private Long userId;

    @ApiModelProperty(value = "商品编码，选填")
    private String productCode;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    @ApiModelProperty(value = "商品描述，必填")
    private String productDesc;

    @ApiModelProperty(value = "出售价格，默认为0.00元，必须保留两位小数，必填")
    private BigDecimal price;

    @ApiModelProperty(value = "商品原价格: 必须保留两位小数，选填")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "商品使用程度：全新/半新/老旧/有磨损 或者自定义，最大允许6个汉字，选填")
    private String degreeUse;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    @ApiModelProperty(value = "商品标签，相当于关键词，用户自定义，最多允许10个汉字，选填")
    private String flag;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "商品图片地址,必填")
    private String imageUrl;

    @ApiModelProperty(value = "商品所属类目ID,填了之后可以增加曝光度，选填,使用的话，必须和categoryName连用")
    @Field(type = FieldType.Long)
    private Long categoryId;

    @ApiModelProperty(value = "商品所属品牌ID，填了之后可以增加曝光度，选填，使用的话，必须和brandName连用")
    @Field(type = FieldType.Long)
    private Long brandId;

    @ApiModelProperty(value = "是否上架：1--上架，0--下架，必填")
    private Boolean able;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "商品类目名称,选填")
    private String categoryName;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "商品品牌名称,选填")
    private String brandName;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "商品用户所属学校")
    private String schoolName;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "联系QQ或者微信")
    private String contactQqOrWx;

    @ApiModelProperty(value = "联系人")
    private String contactPeople;

    @ApiModelProperty(value = "推荐状态")
    private Integer recommendStatus;

    public EsProduct() {
    }
}
