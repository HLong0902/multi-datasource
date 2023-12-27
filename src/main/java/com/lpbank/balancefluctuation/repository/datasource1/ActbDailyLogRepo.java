package com.lpbank.balancefluctuation.repository.datasource1;

import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActbDailyLogRepo extends JpaRepository<ActbDailyLog, Long> {

}
