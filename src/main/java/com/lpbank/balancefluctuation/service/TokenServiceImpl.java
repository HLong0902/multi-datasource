package com.lpbank.balancefluctuation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpbank.balancefluctuation.feign.CallAPIBalanceFluctuationVST;
import com.lpbank.balancefluctuation.feign.CallAPITokenVST;
import com.lpbank.balancefluctuation.module.request.ReqAuthen;
import com.lpbank.balancefluctuation.module.respone.ResAuthen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class TokenServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CallAPITokenVST  callAPITokenVST;
    private static final String TOKEN_KEY_PREFIX = "3";
    private final RedisTemplate<String, String> redisTemplate;
    public TokenServiceImpl(JdbcTemplate jdbcTemplate, CallAPITokenVST callAPITokenVST, RedisTemplate<String, String> redisTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.callAPITokenVST = callAPITokenVST;
        this.redisTemplate = redisTemplate;
    }

    // call api get token
    public ResAuthen getAuthToken() {
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
    public void saveToken(String token, long expirationInSeconds) {
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX, token, expirationInSeconds, TimeUnit.SECONDS);
    }

    public String getToken() {
        return redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX);
    }

}
