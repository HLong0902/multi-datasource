package com.lpbank.balancefluctuation.feign;

import com.lpbank.balancefluctuation.module.request.ReqAuthen;
import com.lpbank.balancefluctuation.module.request.ReqTransactionSync;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@FeignClient(value = "authClient", url = "http://api-gw-dev.lpbank.com.vn/")
public interface CallAPITokenVST {
    @PostMapping(value = "gw/internal/esb-auth-service")
    ResponseEntity<String> getAuthToken(@RequestBody ReqAuthen reqAuthen);
}

