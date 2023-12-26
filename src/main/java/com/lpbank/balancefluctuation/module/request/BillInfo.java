package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillInfo {
    private String billDesc;
    private String billAmount;
    private String settledAmount;
    private String otherInfo;
    private String amt_unit;
}
