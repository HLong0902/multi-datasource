package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenRes {
    private String errorCode;
    private boolean success;
    private Object content;
    private String errorMessage;
}
