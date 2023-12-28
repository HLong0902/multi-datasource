package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceB {
    private String serviceId;
    private String productCode;
    private String requestAccount;
    private String receiveAccount;
    private String merchantId;
}
