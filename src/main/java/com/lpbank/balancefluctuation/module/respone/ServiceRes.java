package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceRes {
    private String serviceId;
    private String productCode;
    private String requestAccount;
    private String merchantId;
}
