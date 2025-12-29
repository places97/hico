package kr.go.config;

import kr.go.scheduler.BatchQuartzJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetailFactoryBean jobDetail() {
        var factory = new JobDetailFactoryBean();
        factory.setJobClass(BatchQuartzJob.class);
        factory.setDurability(true);
        factory.setName("batchJob");
        factory.setGroup("batchGroup");
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean batchJobTrigger(JobDetail batchJobDetail) {
        var factory = new CronTriggerFactoryBean();
        factory.setJobDetail(batchJobDetail);
        factory.setCronExpression("0/10 * * * * ?"); // 매 10초 마다 실행
        factory.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        factory.setName("batchJobTrigger");
        factory.setGroup("batchGroup");
        return factory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    /**
     * 핵심: SchedulerFactoryBean에 DataSource + quartzProperties를 지정하면
     * Quartz가 RAM이 아닌 JDBC(JobStoreTX)로 동작합니다.
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            Trigger dailyBatchTrigger,
            SpringBeanJobFactory springBeanJobFactory,
            @Qualifier("innerDB") DataSource innerDataSource
    ) {
        var factory = new SchedulerFactoryBean();

        factory.setJobFactory(springBeanJobFactory);
        factory.setDataSource(innerDataSource);
        factory.setQuartzProperties(quartzProperties());
        factory.setOverwriteExistingJobs(true);
        factory.setTriggers(dailyBatchTrigger);
        factory.setAutoStartup(true);
        return factory;
    }

    @Bean
    public Properties quartzProperties() {
        Properties props = new Properties();

        // scheduler 식별 (QRTZ_*의 SCHED_NAME과 연결)
        props.setProperty("org.quartz.scheduler.instanceName", "hicoScheduler");
        props.setProperty("org.quartz.scheduler.instanceId", "AUTO");

        // ✅ JDBC JobStore
        props.setProperty("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        props.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        props.setProperty("org.quartz.jobStore.tablePrefix", "app_db.QRTZ_");

        // ✅ 클러스터(원하면 true)
        props.setProperty("org.quartz.jobStore.isClustered", "true");
        props.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");

        // ThreadPool
        props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        props.setProperty("org.quartz.threadPool.threadCount", "5");
        props.setProperty("org.quartz.threadPool.threadPriority", "5");

        return props;
    }
}
