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
@ApiModel(value = "类目表")
@Table(name = "goods_category")
public class Category extends BaseDomain {

    /**
     * 缓存名称
     */
    public static final String CATEGORY_REDIS_KEY = "com:school:interfaces:category";

    //---------------------------------------
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，只读", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long categoryId;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "类目--父ID")
    private Long parentId;

    @NotBlank
    @Length(max = 128)
    @ApiModelProperty(value = "类目名称--中文")
    private String categoryNameZh;

    @Length(max = 128)
    @ApiModelProperty(value = "类目名称--俄语")
    private String categoryNameRu;

    @Length(max = 128)
    @ApiModelProperty(value = "类目名称--英语")
    private String categoryNameEn;

    @ApiModelProperty(value = "排序，暂未使用")
    private Integer sort;

    @Length(max = 128)
    @ApiModelProperty(value = "类目查询关键词")
    private String query;

    @ApiModelProperty(value = "是否启用query")
    private Boolean queryUse;

    @ApiModelProperty(value = "类目单元权重")
    private Float weight;

    @ApiModelProperty(value = "状态--是否启用该类目")
    private Boolean status;

    public Category() {
    }
}
