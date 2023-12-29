package com.lpbank.balancefluctuation.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpbank.balancefluctuation.feign.CallAPIBalanceFluctuationVST;
import com.lpbank.balancefluctuation.module.request.*;
import com.lpbank.balancefluctuation.module.respone.ResCode;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResTransactionSync transactionSync(String token, ReqTransactionSync transactionSync) throws JsonProcessingException {
        String msgid = String.valueOf(Math.random());
        ResTransactionSync resAuthen = new ResTransactionSync();
        try {
            HeaderReq h = new HeaderReq(String.valueOf(Math.random()), "PAYMENT_VTS", "REQUEST_TXN", "135_LPB_DE");
            transactionSync.setHeader(h);
            BodyReq bodyReq = new BodyReq();
            ServiceB serviceB = new ServiceB("030005_36", "PAYMENT_VTS", "021638280001", null, "PAYMENT_VTS");
            Partner partner = new Partner(null, null, null, "J031");
            BillInfo billInfo = new BillInfo("825821-Begin97318End", "10101", "10101", "0011lz3232330001", "2023-09-08 00:00:00");
            BillInfo billInfo2 = new BillInfo("Chuyen tien tu so TK 025840550001 den so TK 021638280001, ma GD 14862360, Begin97419End", "12333", "12333", "0011km3232330002", "2023-09-08 00:00:00");

            List<BillInfo> billInfos = new ArrayList<>();
            billInfos.add(billInfo);
            billInfos.add(billInfo2);
            SettleBill settleBill = new SettleBill("N", "00056248", "001", "Test Loi", serviceB, partner, billInfos);
            bodyReq.setSettleBill(settleBill);
            transactionSync.setBody(bodyReq);

            ReqTransactionSync renew = new ReqTransactionSync();
            renew.setBody(new BodyReq());
            renew.setHeader(new HeaderReq());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValueAsString(renew);
            resAuthen = callAPIBalanceFluctuationVST.transactionSync(token, renew);
            System.out.println(resAuthen.getResCode().getErrorCode());
            return resAuthen;
        } catch (Exception e) {
            //TODO: cập nhật trạng thái active và retry_number
            String message = e.getMessage();
            String jsonErrorMessage = message.substring(message.indexOf("[{"), message.lastIndexOf("]") + 1);
            JSONArray errorArray = new JSONArray(jsonErrorMessage);
            JSONObject errorObject = errorArray.getJSONObject(0);
            JSONObject resCodeObject = errorObject.getJSONObject("resCode");
            String errorCode = resCodeObject.getString("errorCode");
            String errorDesc = resCodeObject.getString("errorDesc");

            ResCode resCode = new ResCode(errorCode, errorDesc, null, null);
            resAuthen.setResCode(resCode);
            return resAuthen;
        }
    }
}