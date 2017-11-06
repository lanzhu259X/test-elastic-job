package com.lanzhu.test.job.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.lanzhu.test.job.annation.Log;
import com.lanzhu.test.job.entity.DataEntity;
import com.lanzhu.test.job.repository.DataEntityRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataFlowTestJob implements DataflowJob<DataEntity> {


    @Log
    private Logger logger;
    @Resource
    private DataEntityRepository repository;

    @Override
    public List<DataEntity> fetchData(ShardingContext shardingContext) {
        logger.info("[fetchData] start get data by shardingItem:{}, shardingParam:{}",
                shardingContext.getShardingItem(), shardingContext.getShardingParameter());
        List<DataEntity> result = repository.findTodoData(shardingContext.getShardingParameter(), 10);
        logger.info("[fetchData] get data by shardingItem:{} shardingParam:{} result size:{}",
                shardingContext.getShardingItem(), shardingContext.getShardingParameter(), result.size());
        return result;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<DataEntity> list) {
        int item = shardingContext.getShardingItem();
        String param = shardingContext.getShardingParameter();
        logger.info("[processData] shardingItem:{} shardingParam:{} begin process data list size:{}",
                item, param, list.size());

        for (DataEntity dataEntity : list) {
            logger.info("[processData] processing {}", dataEntity.toString());
            repository.setCompleted(dataEntity.getId());
        }
        logger.info("[processData] shardingItem:{} shardingParam:{} process end.", item, param);
    }
}
