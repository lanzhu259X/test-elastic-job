package com.lanzhu.test.job.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lanzhu.test.job.job.DataFlowTestJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataFlowTestJobConfig {

    @Autowired
    private ZookeeperRegistryCenter registryCenter;
    @Autowired
    private JobEventConfiguration jobEventConfiguration;

    @Value("${live.dataflow.shardingItemParameters}")
    private String shardingItemParams;
    @Value("${live.dataflow.shardingTotalCount}")
    private int shardingTotalCount;
    @Value("${live.dataflow.cron}")
    private String cron;

    @Bean(initMethod = "init")
    public JobScheduler dataFlowTestScheduler(final DataFlowTestJob dataFlowTestJob) {
        return new SpringJobScheduler(dataFlowTestJob,
                registryCenter,
                getLiteJobConfiguration(dataFlowTestJob.getClass()),
                jobEventConfiguration);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends DataflowJob> jobClass) {
        return LiteJobConfiguration.newBuilder(new DataflowJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParams).build(),
                jobClass.getCanonicalName(), true)).overwrite(true).build();
    }

}
