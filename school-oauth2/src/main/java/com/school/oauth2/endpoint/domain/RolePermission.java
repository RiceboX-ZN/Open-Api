package com.school.oauth2.endpoint.domain;

import com.school.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "oauth_role_permission")
@ApiModel(value = "角色权限关联表")
public class RolePermission extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，提供给其他表做外键")
    private Long rolePermissionId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "权限ID")
    private Long permissionId;

    public RolePermission() {
    }
}
