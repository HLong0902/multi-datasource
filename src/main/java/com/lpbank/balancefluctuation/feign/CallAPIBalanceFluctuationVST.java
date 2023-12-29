package com.lpbank.balancefluctuation.feign;

import com.lpbank.balancefluctuation.module.request.ReqTransactionSync;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;
import java.util.Objects;

@FeignClient(name = "settleClient", url = "http://api-gw-dev.lpbank.com.vn/")
public interface CallAPIBalanceFluctuationVST {
    @PostMapping("/gw/internal/settle-service/api/v1/settle/settle-bill")
    ResTransactionSync transactionSync(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ReqTransactionSync reqTransactionSync
    );
}