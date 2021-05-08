package com.school.interfaces.entity;

import com.school.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品信息表")
@Table(name = "goods_product")
public class Product extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，只读", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long productId;

    @NotNull
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @Length(max = 32)
    @ApiModelProperty(value = "商品编码,只读", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String productCode;

    @Length(max = 1024)
    @NotBlank
    @ApiModelProperty(value = "商品描述，必填")
    private String productDesc;

    @ApiModelProperty(value = "出售价格，默认为0.0")
    private BigDecimal price;

    @ApiModelProperty(value = "商品原价")
    private BigDecimal originPrice;

    @Length(max = 32)
    @ApiModelProperty(value = "商品使用程度")
    private String degreeUse;

    @Length(max = 64)
    @ApiModelProperty(value = "商品标志词，搜索的时候，经常会用到")
    private String flag;

    @Length(max = 1024)
    @ApiModelProperty(value = "图片地址,存储的时候，采用json格式")
    private String imageUrl;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @Length(max = 64)
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @Length(max = 64)
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @Column(name = "is_able")
    @ApiModelProperty(value = "是否上架，默认为上架")
    private Boolean able;

    @Length(max = 64)
    @ApiModelProperty(value = "商品用户所属学校")
    private String schoolName;

    @Length(max = 20)
    @ApiModelProperty(value = "物主联系电话")
    private String contactPhone;

    @Length(max = 32)
    @ApiModelProperty(value = "联系方式：QQ/WX")
    private String contactQqOrWx;

    @ApiModelProperty(value = "推荐状态，分数越高，推荐越靠前")
    private Integer recommendStatus;

    public Product() {
    }
}
