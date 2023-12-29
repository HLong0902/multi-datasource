package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PartnerRes {
    private String txnRefNo;
    private String txnDatetime;
    private String txnCode;
    private String chanel;
    private String terminalId;
    private String txnConfirmDt;
}
