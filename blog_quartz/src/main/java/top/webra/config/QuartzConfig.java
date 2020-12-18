package top.webra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import top.webra.quartz.QuartzJob;

/**
 * quartz 定时任务配置类
 */
@Configuration
public class QuartzConfig {
    /**
     * 1. 创建job对象
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        // 关联工作类
        jobDetailFactoryBean.setJobClass(QuartzJob.class);
        return jobDetailFactoryBean;
    }

    /**
     * 2. 常见trigger 对象
     */
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        // 关联jobDetail对象
        simpleTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        // setRepeatInterval    多长时间执行一次任务(循环)
        simpleTriggerFactoryBean.setRepeatInterval(2000L);
        // setRepeatCount   重复次数  5次
        simpleTriggerFactoryBean.setRepeatCount(1);

        return simpleTriggerFactoryBean;
    }

    /**
     * 3. 创建scheduler对象
     */

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 关联trigger
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        return schedulerFactoryBean;
    }
}
