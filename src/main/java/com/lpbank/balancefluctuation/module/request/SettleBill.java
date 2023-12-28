package com.lpbank.balancefluctuation.module.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettleBill {
    private String confirmTrn;
    private String customerNo;
    private String trnBrn;
    private String trnDesc;
    private ServiceB service;
    private Partner partner;
    private List<BillInfo> listBillInfo;
}
