package com.lpbank.balancefluctuation.feign;

import com.lpbank.balancefluctuation.module.request.ReqTransactionSync;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "balanceFluctuation", url = "http://10.36.126.90:30101")
public interface CallAPIBalanceFluctuationVST {
    //    @GetMapping(value = "/auth/token")
//    ResponseEntity<String> getToken(@RequestHeader("CLIENT_ID") String clientId,
//                                    @RequestHeader("CLIENT_CURRENT_TIMESTAMP") String clientCurrentTimestamp,
//                                    @RequestHeader("CHECK_SUM") String checkSum);
//
    @PostMapping(value = "/esb-gateway-service/settle-service/api/v1/settle/settle-bill")
    ResTransactionSync transactionSync(@RequestHeader("service") String service,
                                       @RequestHeader("operation") String operation,
                                       @RequestBody ReqTransactionSync reqTransactionSync);

}
