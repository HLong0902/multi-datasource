package com.lpbank.balancefluctuation.repository.datasource2;

import com.lpbank.balancefluctuation.model.entityDatasource2.SendDataTransactionViettelStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendDataTransRepo extends JpaRepository<SendDataTransactionViettelStore, Long> {
    @Query(value = "SELECT * FROM SEND_DATA_TRANSACTION_VIETTEL_STORE sdtvs WHERE STATUS = 0 AND RETRY_NUMBER  < :retryNum and rownum <= :batchSize", nativeQuery = true)
    List<SendDataTransactionViettelStore> findAllToSend(@Param("batchSize") Integer batchSize, @Param("retryNum") Integer retryNum);
}
