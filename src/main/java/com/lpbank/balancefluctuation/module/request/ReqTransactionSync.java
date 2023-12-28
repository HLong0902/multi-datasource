package com.lpbank.balancefluctuation.module.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqTransactionSync {
    private Header header;
    private Body body;
}
