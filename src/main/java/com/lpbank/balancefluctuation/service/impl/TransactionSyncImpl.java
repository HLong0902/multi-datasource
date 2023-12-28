package com.lpbank.balancefluctuation.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpbank.balancefluctuation.feign.CallAPIBalanceFluctuationVST;
import com.lpbank.balancefluctuation.module.request.*;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TransactionSyncImpl {

    private static final String SERVICE = "PAYMENT_VTS";

    private static final String OPERATION = "REQUEST_TXN";

    private static final String USER_ID = "135_LPB_DEV";
    private final CallAPIBalanceFluctuationVST callAPIBalanceFluctuationVST;

    public TransactionSyncImpl(CallAPIBalanceFluctuationVST callAPIBalanceFluctuationVST) {
        this.callAPIBalanceFluctuationVST = callAPIBalanceFluctuationVST;
    }

    // call api push data send notis
    public ResTransactionSync transactionSync(String token, ReqTransactionSync transactionSync) {
        String msgid = String.valueOf(Math.random());
        try {
            Header h = new Header(String.valueOf(Math.random()), "PAYMENT_VTS", "REQUEST_TXN", "135_LPB_DE");
            transactionSync.setHeader(h);
            Body body = new Body();
            ServiceB serviceB = new ServiceB("030005_36","PAYMENT_VTS","021638280001", null, "PAYMENT_VTS");
            Partner partner = new Partner(null,null,null,"J031");
            BillInfo billInfo = new BillInfo("825821-Begin97318End", "10101", "10101", "0011lz3232330001","2023-09-08 00:00:00" );
            List<BillInfo> billInfos = new ArrayList<>();
            billInfos.add(billInfo);
            SettleBill settleBill = new SettleBill("N", "00056248", "001", "Test Loi", serviceB,partner,billInfos);
            body.setSettleBill(settleBill);
            transactionSync.setBody(body);


            ResponseEntity<Map<String, Object>> reqTransactionSyncJSON = callAPIBalanceFluctuationVST.transactionSync(token, transactionSync);
            String responseBody = reqTransactionSyncJSON.getBody().toString();
//            ObjectMapper objectMapper = new ObjectMapper();
//            ResTransactionSync resAuthen = objectMapper.readValue(responseBody, ResTransactionSync.class);
//            System.out.println(resAuthen.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}