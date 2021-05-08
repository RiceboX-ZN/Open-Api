package com.school.good.elasticsearch.pojo;

import com.school.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "定时任务执行时间表")
@Table(name = "schedule_cron")
public class ScheduleCron extends BaseDomain {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键，只读", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long cronId;

    private String cron;

    public ScheduleCron() {
    }
}
