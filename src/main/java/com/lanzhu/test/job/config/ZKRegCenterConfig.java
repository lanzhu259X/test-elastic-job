package com.lanzhu.test.job.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZKRegCenterConfig {

    @Value("${regCenter.serverList}")
    private String serverList;
    @Value("${regCenter.namespace}")
    private String nameSpace;

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter () {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, nameSpace));
    }

}
