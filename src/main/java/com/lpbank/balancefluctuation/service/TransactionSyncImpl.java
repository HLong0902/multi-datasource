package com.lpbank.balancefluctuation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpbank.balancefluctuation.feign.CallAPIBalanceFluctuationVST;
import com.lpbank.balancefluctuation.module.request.ReqTransactionSync;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionSyncImpl {

    private static final String SERVICE = "PAYMENT_VTS";

    private static final String OPERATION = "REQUEST_TXN";

    private final CallAPIBalanceFluctuationVST callAPIBalanceFluctuationVST;
    public TransactionSyncImpl(CallAPIBalanceFluctuationVST callAPIBalanceFluctuationVST) {
        this.callAPIBalanceFluctuationVST = callAPIBalanceFluctuationVST;
    }

    // call api push data send notis
    public ResTransactionSync transactionSync(String userId) {
        String msgid = String.valueOf(Math.random());
        try {
            ReqTransactionSync reqTransactionSync = new ReqTransactionSync();
            ResponseEntity<String> reqTransactionSyncJSON = callAPIBalanceFluctuationVST.transactionSync(msgid, SERVICE, OPERATION, userId, reqTransactionSync);
            String responseBody = reqTransactionSyncJSON.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResTransactionSync resAuthen = objectMapper.readValue(responseBody, ResTransactionSync.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
