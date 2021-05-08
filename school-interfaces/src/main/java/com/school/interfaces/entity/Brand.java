package com.school.interfaces.entity;

import com.school.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "品牌表")
@Table(name = "goods_brand")
public class Brand extends BaseDomain {

    public static final String BRAND_REDIS_KEY = "com:school:interfaces:brand";

    //-------------------------------------------
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，只读", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long brandId;

    @NotBlank
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @Length(max = 64)
    @ApiModelProperty(value = "品牌编号")
    private String brandCode;

    @Length(max = 1024)
    @ApiModelProperty(value = "品牌图片")
    private String brandImage;

    @ApiModelProperty(value = "品牌首字母")
    private Character letter;

    @Length(max = 1024)
    @ApiModelProperty(value = "品牌简短描述")
    private String describ;

    public Brand() {
    }
}
