package com.lpbank.balancefluctuation.service;

import com.lpbank.balancefluctuation.module.respone.ResAuthen;
import com.lpbank.balancefluctuation.module.respone.ResDWH;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    public void reportDebit() {
        List<ResDWH> results = new ArrayList<>();
        try {
            // quét bảng DWH
            String sql = "SELECT * FROM ACTB_DAILY_LOG where AC_ENTRY_SR_NO = '3219648285' ";
            Object[] params = {};
            results = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
                ResDWH e = new ResDWH(
                        rs.getString("TRN_REF_NO")
                );
                return e;
            });
        } catch (Exception e) {
            //TODO: cập nhật trạng thái active và retry_number
            e.printStackTrace();
        }
        tokenService.saveToken("token", 6000);
        System.out.println(tokenService.getToken());
        // nếu có data
        if (!results.isEmpty()) {
            try {
                //call api lấy token
                String token = "";
                ResAuthen resAuthen = new ResAuthen();
                //nếu token hết hạn(không còn trong cache) call api gọi mới
                if (Objects.isNull(tokenService.getToken()) && tokenService.getToken().isEmpty()) {
                    resAuthen = tokenService.getAuthToken();
                    if (Objects.nonNull(resAuthen.getErrorCode()) || !resAuthen.getErrorCode().isEmpty() || StringUtils.hasLength(resAuthen.getErrorCode()) || Objects.equals(HttpStatus.OK,resAuthen.getErrorCode())) {
                        token = (String) resAuthen.getContent();
                    } else {
                        //TODO: cập nhật trạng thái active và retry_number
                    }
                    //lưu token vào cache
                    tokenService.saveToken(token, 60);
                } else {
                    token = tokenService.getToken();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO: cập nhật trạng thái active và retry_number
            }
            try {
                //call api gửi đi VST
                ResTransactionSync resTransactionSync = transactionSync.transactionSync("1");
                //call api thành công
                if ( Objects.nonNull(resTransactionSync.getErrorCode()) || !resTransactionSync.getErrorCode().isEmpty() && StringUtils.hasLength(resTransactionSync.getErrorCode()) && Objects.equals(HttpStatus.OK,resTransactionSync.getErrorCode())) {
                    // api trả về thành công và lưu trạng thái
                } else {
                    //TODO: cập nhật trạng thái active và retry_number
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //TODO: cập nhật trạng thái active và retry_number
            }
        }// không có data không làm gì cả

    }
}


