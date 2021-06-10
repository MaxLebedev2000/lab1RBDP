package com.example.springboot.config;

import com.example.springboot.quartz.RemindJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class QuartzConfig {
    @Bean
    @ConditionalOnProperty(value = "quartz.enabled", havingValue = "true")
    public JobDetail jobDetail() {
        return newJob()
                .ofType(RemindJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "quartz.enabled", havingValue = "true")
    public Trigger trigger(JobDetail job) {

        return newTrigger()
                .forJob(job)
                .withSchedule(
                        simpleSchedule()
                                .withIntervalInHours(3)
                                .repeatForever())
                .build();
    }
}
