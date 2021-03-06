package com.lanzhu.test.job.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lanzhu.test.job.job.SimpleFailJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleFailJobConfig {

    @Autowired
    private ZookeeperRegistryCenter registryCenter;
    @Autowired
    private JobEventConfiguration jobEventConfiguration;

    @Value("${live.simplefail.shardingItemParameters}")
    private String shardingItemParams;
    @Value("${live.simplefail.shardingTotalCount}")
    private int shardingTotalCount;
    @Value("${live.simplefail.cron}")
    private String cron;

    @Bean(initMethod = "init")
    public JobScheduler simpleFailJobScheduler(final SimpleFailJob simpleFailJob) {
        return new SpringJobScheduler(simpleFailJob,
                registryCenter,
                getJobConfiguration(simpleFailJob.getClass()),
                jobEventConfiguration);
    }

    private LiteJobConfiguration getJobConfiguration(final Class<? extends SimpleJob> jobClass) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParams).build(), jobClass.getCanonicalName()))
                .overwrite(true).build();
    }

}
