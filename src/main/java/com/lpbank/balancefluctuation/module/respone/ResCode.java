package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResCode {
    private String errorCode;
    private String errorDesc;
    private String refCode;
    private String refDesc;
}
