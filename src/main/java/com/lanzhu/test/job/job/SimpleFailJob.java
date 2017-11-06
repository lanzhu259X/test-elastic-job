package com.lanzhu.test.job.job;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lanzhu.test.job.annation.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SimpleFailJob implements SimpleJob {

    @Log
    private Logger logger;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("[SimpleFailJob] job start, shardingItem:{} shardingParam:",
                shardingContext.getShardingItem(), shardingContext.getShardingParameter());
        try {
            TimeUnit.SECONDS.sleep(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("[SimpleFailJob] job end.");
    }


}
