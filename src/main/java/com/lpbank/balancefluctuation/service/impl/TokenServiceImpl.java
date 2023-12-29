package com.lpbank.balancefluctuation.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpbank.balancefluctuation.feign.CallAPITokenVST;
import com.lpbank.balancefluctuation.module.request.ReqAuthen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class TokenServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final CallAPITokenVST callAPITokenVST;
    public static final String TOKEN_KEY_PREFIX = "3";
    private final RedisTemplate<String, String> redisTemplate;

    public TokenServiceImpl(JdbcTemplate jdbcTemplate, CallAPITokenVST callAPITokenVST, RedisTemplate<String, String> redisTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.callAPITokenVST = callAPITokenVST;
        this.redisTemplate = redisTemplate;
    }

    // call api get token
    public String getAuthToken() {
        ReqAuthen reqAuthen = new ReqAuthen();
        reqAuthen.setPassword("oV6/taBNIwtOPeAbBxZAUQVdmT6AYJyyk2XV9Fe5Dwy92wQnizIPs/nCk8Hfi41y4hrlgdxBLfkVw78SEcneLwyjaBy+u4KOmSNvDeDNAFujNPO6+5PzxAyiPKaCNfn5wmEV23s7Px74h6AzONasHo8PHALOngDCoWsCTF9RPkM=");
        reqAuthen.setUsername("135_LPB_DEV");
        String token="";
        try {
            ResponseEntity<String> resAuthenJSON = callAPITokenVST.getAuthToken(reqAuthen);
            if(Objects.nonNull(resAuthenJSON.getBody())){
                ObjectMapper objectMapper = new ObjectMapper();
                //JsonNode jsonNode = objectMapper.readTree(resAuthenJSON.getBody());
                token = Objects.requireNonNull(resAuthenJSON.getHeaders().get("Authorization")).get(0);
            }
        } catch (Exception e) {
            //TODO: cập nhật trạng thái active và retry_number
            e.printStackTrace();
        }
        return token;
    }

    public void saveToken(String token, long expirationInSeconds) {
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX, token, expirationInSeconds, TimeUnit.SECONDS);
    }

    public String getToken() {
        return redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX);
    }

}
