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
import javax.validation.constraints.NotBlank;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Table(name = "oauth_captcha")
@ApiModel(value = "验证码表")
@Data
public class Captcha extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，提供给其他表做外键")
    private Long captchaId;

    @NotBlank
    @Length(max = 128)
    @ApiModelProperty(value = "验证码唯一ID")
    private String uuid;

    @Length(max = 20)
    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "验证码过期时间，默认是3分钟，用过一次就失效")
    private Date expireTime;

    public Captcha() {
    }
}
