package com.school.oauth2.endpoint.domain;

import com.school.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
@Table(name = "oauth_role")
@ApiModel(value = "角色表")
public class Role extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "角色ID，提供给其他不做外键", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long roleId;

    @NotBlank(message = "角色编码不能为空")
    @Length(max = 20)
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @NotBlank(message = "角色姓名不能为空")
    @Length(max = 128)
    @ApiModelProperty(value = "角色名称，guest/user/admin")
    private String roleName;

    @ApiModelProperty(value = "父亲角色ID")
    private Long parentId;

    @ApiModelProperty(value = "角色层级水平")
    private Integer level;

    @Length(max = 1024, message = "角色描述字段过长")
    @ApiModelProperty(value = "角色简单描述")
    private String desc;

    @Column(name = "is_able")
    @ApiModelProperty(value = "角色是否启用，默认为启用")
    private Boolean able;

    @Transient
    @ApiModelProperty(value = "角色对应权限，一个角色对应多个权限")
    private List<Permission> permissionList;

    public Role() {
    }
}
