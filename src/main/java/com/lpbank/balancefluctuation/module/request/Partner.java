package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Partner {
    private String txnRefNo;
    private String txnDatetime;
    private String txnCode;
    private String chanel;
}
