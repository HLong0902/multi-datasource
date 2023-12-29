package com.lpbank.balancefluctuation.repository.datasource2;

import com.lpbank.balancefluctuation.model.entityDatasource2.JobConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobConfigRepo extends JpaRepository<JobConfig, Long> {

    List<JobConfig> findAllByStatus(Integer status);
}
