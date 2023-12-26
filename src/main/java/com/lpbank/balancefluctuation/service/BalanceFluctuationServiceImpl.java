package com.lpbank.balancefluctuation.service;

import com.lpbank.balancefluctuation.module.respone.ResAuthen;
import com.lpbank.balancefluctuation.module.respone.ResDWH;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BalanceFluctuationServiceImpl {
    private final TokenServiceImpl tokenService;

    private final JdbcTemplate jdbcTemplate;

    public BalanceFluctuationServiceImpl(TokenServiceImpl tokenService, JdbcTemplate jdbcTemplate) {
        this.tokenService = tokenService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void test() {

        try {
            // quét bảng DWH
            String sql = "SELECT * FROM ACTB_HISTORY_DT1 ahd WHERE AC_NO = 000001501313";
            Object[] params = {};
            List<ResDWH> results = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
                ResDWH e = new ResDWH(
                        rs.getString("a")
                );
                return e;
            });
            // nếu có data
            if (!results.isEmpty()) {
                //call api lấy token
                try {
                    ResAuthen resAuthen = tokenService.getToken();

                    //nếu call api thành công và trả về token và type
                    //call api gửi đi VST
                    //call api thành công
                    // api trả về thành công và lưu trạng thái
                    // api trả về lỗi
                    //check đã gửi 5 lần chưa
                    //nếu quá 5 lần lưu trạng thái
                    //chưa quá 5 lần lưu trạng thái gửi lại sau 5p
                    // call api lỗi
                    //check đã gửi 5 lần chưa
                    //nếu quá 5 lần lưu trạng thái
                    //chưa quá 5 lần lưu trạng thái gửi lại sau 5p
                    //call api fail lưu lỗi

                }catch (Exception e){

                }

            }// không có data không làm gì cả
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


