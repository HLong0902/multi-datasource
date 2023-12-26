package com.lpbank.balancefluctuation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpbank.balancefluctuation.feign.CallAPITokenVST;
import com.lpbank.balancefluctuation.module.request.ReqAuthen;
import com.lpbank.balancefluctuation.module.respone.ResAuthen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TokenServiceImpl {
    private final CallAPITokenVST callAPITokenVST;

    private final JdbcTemplate jdbcTemplate;

    public TokenServiceImpl(CallAPITokenVST callAPITokenVST, JdbcTemplate jdbcTemplate) {
        this.callAPITokenVST = callAPITokenVST;
        this.jdbcTemplate = jdbcTemplate;
    }

    // call api get token
    public ResAuthen getToken() {
        ReqAuthen reqAuthen = new ReqAuthen();
        reqAuthen.setPassword("oV6/taBNIwtOPeAbBxZAUQVdmT6AYJyyk2XV9Fe5Dwy92wQnizIPs/nCk8Hfi41y4hrlgdxBLfkV\\r\\nw78SEcneLwyjaBy+u4KOmSNvDeDNAFujNPO6+5PzxAyiPKaCNfn5wmEV23s7Px74h6AzONasHo8P\\r\\nHALOngDCoWsCTF9RPkM=");
        reqAuthen.setUsername("135_LPB_DEV");
        try {
//            ResponseEntity<String> resAuthenJSON = callAPITokenVST.getAuthToken(reqAuthen);
//            String responseBody = resAuthenJSON.getBody();
//            ObjectMapper objectMapper = new ObjectMapper();
//            ResAuthen resAuthen = objectMapper.readValue(responseBody, ResAuthen.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
