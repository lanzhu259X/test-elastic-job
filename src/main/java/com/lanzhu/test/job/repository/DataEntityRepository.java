package com.lanzhu.test.job.repository;

import com.lanzhu.test.job.entity.DataEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DataEntityRepository {

    private Map<Integer, DataEntity> data = new ConcurrentHashMap<>(300, 1);

    public DataEntityRepository() {
        initData();
    }

    private void initData () {
        addData(0, 100, "SH");
        addData(100, 200, "BJ");
        addData(200, 300, "GZ");
    }

    private void addData(final int idFrom, final int idTo, final String location) {
        for (int i = idFrom; i < idTo; i++) {
            data.put(i, new DataEntity(i, location, DataEntity.Status.TODO));
        }
    }

    public List<DataEntity> findTodoData(final String location, final int limit) {
        List<DataEntity> result = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, DataEntity> entry : data.entrySet()) {
            DataEntity dataEntity = entry.getValue();
            if (dataEntity.getLocation().equals(location) && dataEntity.getStatus() == DataEntity.Status.TODO) {
                result.add(dataEntity);
                count ++;
                if (count == limit) {
                    break;
                }
            }
        }
        return result;
    }

    public void setCompleted(final int id) {
        data.get(id).setStatus(DataEntity.Status.COMPLETED);
    }
}
