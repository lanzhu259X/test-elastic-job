package com.lanzhu.test.job.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lanzhu.test.job.annation.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SimpleTestJob implements SimpleJob {

    @Log
    private Logger logger;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("[SimpleTestJob] job start by shardingItem:{} shardingParam:",
                shardingContext.getShardingItem(), shardingContext.getShardingParameter());
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("[SimpleTestJob] job end.");

    }
}
