package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceLogRes {
    private String service_info;
    private String txn_req_esb;
    private String txn_res_esb;
    private String txn_req_par;
}
