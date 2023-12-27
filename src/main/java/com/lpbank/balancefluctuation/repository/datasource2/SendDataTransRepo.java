package com.lpbank.balancefluctuation.repository.datasource2;

import com.lpbank.balancefluctuation.model.entityDatasource2.SendDataTransactionViettelStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendDataTransRepo extends JpaRepository<SendDataTransactionViettelStore, Long> {
}
