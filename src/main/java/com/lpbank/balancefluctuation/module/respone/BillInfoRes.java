package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

import javax.persistence.Access;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillInfoRes {
    private String billDesc;
    private String billAmount;
    private String settledAmount;
    private String otherInfo;
    private String amt_unit;
}
