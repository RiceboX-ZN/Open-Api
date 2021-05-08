package com.school.oauth2.endpoint.domain;

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
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "oauth_user_role")
@Data
@ApiModel(value = "用户权限关联表")
public class UserRole extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，提供给其他表做外键")
    private Long userRoleId;

    @Length(max = 64)
    @ApiModelProperty(value = "用户角色关系编码，非必要")
    private String userRoleCode;

    @NotNull
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    public UserRole() {
    }
}
