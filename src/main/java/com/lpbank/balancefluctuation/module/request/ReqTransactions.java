package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqTransactions {
    private String refNo;
    private String accountNo;
    private String currency;
    private Long amount;
    private String remask;
    private String dorc;
    private Long transDate;
    private Long openingBalance;
    private Long remainingBalance;
    private String checksum;
}
