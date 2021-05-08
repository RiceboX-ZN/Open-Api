package com.school.oauth2.endpoint.domain;

import com.school.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "oauth_permission")
@ApiModel(value = "权限表")
public class Permission extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，提供给其他表做外键")
    private Long permissionId;

    @NotBlank
    @Length(max = 64)
    @ApiModelProperty(value = "所属微服务名称")
    private String servicePlatform;

    @NotBlank
    @Length(max = 128)
    @ApiModelProperty(value = "请求路径")
    private String requestUrl;

    @NotBlank
    @ApiModelProperty(value = "是否需要认证之后才能访问该权限")
    private Boolean authentication;

    @Transient
    @ApiModelProperty(value = "权限对应的角色，一个权限对应多个角色")
    private List<Role> roleList;

    public Permission() {
    }
}
