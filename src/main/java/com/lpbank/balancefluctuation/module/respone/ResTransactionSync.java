package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResTransactionSync {
    private boolean success;
    private String errorCode;
    private String errorMessage;
    private Map<String, List<ResTransactions>> content;
}
