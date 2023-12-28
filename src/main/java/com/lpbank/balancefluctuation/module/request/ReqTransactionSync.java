package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqTransactionSync {
    private HeaderReq header;
    private BodyReq body;
}
