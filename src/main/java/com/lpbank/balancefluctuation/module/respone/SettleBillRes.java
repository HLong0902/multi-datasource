package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SettleBillRes {
    private String confirmTrn;
    private String customerNo;
    private String trnBrn;
    private String trnDesc;
    private String transactionId;
    private String userId;
    private ServiceLogRes serviceLog;
    private PartnerRes partner;
    private ServiceRes service;
    private List<BillInfoRes> listBillInfo;
    private String postInfo;
    private String customerInfo;
    private String settleAccountInfo;

}
