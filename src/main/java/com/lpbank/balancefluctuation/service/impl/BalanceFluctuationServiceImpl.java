package com.lpbank.balancefluctuation.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpbank.balancefluctuation.module.request.ReqTransactionSync;
import com.lpbank.balancefluctuation.module.respone.ResDWH;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BalanceFluctuationServiceImpl {
    private final TokenServiceImpl tokenService;

    private final TransactionSyncImpl transactionSync;

    private final JdbcTemplate jdbcTemplate;

    public BalanceFluctuationServiceImpl(TokenServiceImpl tokenService, TransactionSyncImpl transactionSync, JdbcTemplate jdbcTemplate) {
        this.tokenService = tokenService;
        this.transactionSync = transactionSync;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void reportDebit() throws JsonProcessingException {
        List<ResDWH> results = new ArrayList<>();
        try {
            // quét bảng DWH
            String sql = "SELECT * FROM ACTB_DAILY_LOG where AC_ENTRY_SR_NO = '3219648285' ";
            Object[] params = {};
            results = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
                ResDWH e = new ResDWH(rs.getString("TRN_REF_NO"));
                return e;
            });
        } catch (Exception e) {
            //TODO: cập nhật trạng thái active và retry_number
            e.printStackTrace();
        }
        // nếu có data
        String tes = "";
        if (Objects.isNull(tokenService.getToken()) || tokenService.getToken().isEmpty()) {
            tes = tokenService.getAuthToken();
            tokenService.saveToken(tes, 120);
            System.out.println("Token moi : " + tes + "\n Time: " + new Date());
        } else {
            tes = tokenService.getToken();
            System.out.println("Token co : " + tes + "\n Time: " + new Date());
        }
        //call api gửi đi VST
        ReqTransactionSync reqTransactionSync = new ReqTransactionSync();
        ResTransactionSync resTransactionSync = transactionSync.transactionSync(tes, reqTransactionSync);

//        if (!results.isEmpty()) {
//            try {
//                //call api lấy token
//                String token = "";
//                //nếu token hết hạn(không còn trong cache) call api gọi mới
//                if (Objects.isNull(tokenService.getToken()) || tokenService.getToken().isEmpty()) {
//                    token = tokenService.getAuthToken();
//                    //lưu token vào cache
//                    tokenService.saveToken(token, 60);
//                    System.out.println("Token moi : " + token + "\n Time: " + new Date());
//                } else {
//                    token = tokenService.getToken();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                //TODO: cập nhật trạng thái active và retry_number
//            }
//            try {
//                //call api gửi đi VST
//                ReqTransactionSync reqTransactionSync = new ReqTransactionSync();
//                ResTransactionSync resTransactionSync = transactionSync.transactionSync(reqTransactionSync);
//                //call api thành công
//                if ( Objects.nonNull(resTransactionSync.getErrorCode()) || !resTransactionSync.getErrorCode().isEmpty() && StringUtils.hasLength(resTransactionSync.getErrorCode()) && Objects.equals(HttpStatus.OK,resTransactionSync.getErrorCode())) {
//                    // api trả về thành công và lưu trạng thái
//                } else {
//                    //TODO: cập nhật trạng thái active và retry_number
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                //TODO: cập nhật trạng thái active và retry_number
//            }
//        }// không có data không làm gì cả

    }
}


