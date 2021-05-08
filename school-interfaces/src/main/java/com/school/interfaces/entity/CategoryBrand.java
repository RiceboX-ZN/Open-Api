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

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "类目-品牌 关系表")
@Table(name = "goods_category_brand")
public class CategoryBrand extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，只读", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long categoryBrandId;

    @ApiModelProperty(value = "类目ID")
    private Long categoryId;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @Length(max = 1024)
    @ApiModelProperty(value = "简短描述")
    private Long describ;

    public CategoryBrand() {
    }
}
