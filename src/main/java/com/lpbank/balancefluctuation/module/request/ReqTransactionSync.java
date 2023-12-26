package com.lpbank.balancefluctuation.module.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqTransactionSync {
    private String confirmTrn;
    private String customerNo;
    private String trnBrn;
    private String trnDesc;
    private Service service;
    private Partner partner;
    private List<BillInfo> listBillInfo;
}
