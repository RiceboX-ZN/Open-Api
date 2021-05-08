package com.school.good.task;

import com.school.good.elasticsearch.pojo.ScheduleCron;
import com.school.good.elasticsearch.service.EsProductService;
import com.school.good.mapper.ScheduleCronMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableScheduling
public class EsProductTask implements SchedulingConfigurer {

    public static final Long SCHEDULE_CRON_SEQUENCE = 1L;
    @Autowired
    private ScheduleCronMapper scheduleCronMapper;

    @Autowired
    private EsProductService esProductService;

    @Async
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(()->esProductService.importAll(),
                triggerContext -> {
                    log.info("es-product-import schedule start");
                    ScheduleCron scheduleCron = scheduleCronMapper.selectByPrimaryKey(SCHEDULE_CRON_SEQUENCE);
                    String cron = "0/5 * * * * ?";
                    if (scheduleCron != null) {
                        cron = scheduleCron.getCron();
                    }
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });
    }

}
