package com.lpbank.balancefluctuation.repository.datasource2;

import com.lpbank.balancefluctuation.model.entityDatasource2.VTSAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VTSAccountRepo extends JpaRepository<VTSAccount, Long> {
    List<VTSAccount> findAllByRecordStat(String recordStat);
}
