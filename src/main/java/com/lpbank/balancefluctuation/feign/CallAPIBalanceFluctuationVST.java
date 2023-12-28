package com.lpbank.balancefluctuation.feign;

import com.lpbank.balancefluctuation.module.request.ReqTransactionSync;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "balanceFluctuation", url = "http://10.36.126.90:30101")
public interface CallAPIBalanceFluctuationVST {
    @PostMapping(value = "/esb-gateway-service/settle-service/api/v1/settle/settle-bill")
    ResponseEntity<String> transactionSync(
            @RequestHeader("msgid") String msgid,
            @RequestHeader("service") String service,
            @RequestHeader("operation") String operation,
            @RequestHeader("userid") String userid,
            @RequestBody ReqTransactionSync reqTransactionSync);
}
