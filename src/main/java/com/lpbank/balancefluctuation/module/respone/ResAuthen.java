package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResAuthen {
    private String errorCode;
    private boolean success;
    private Object content;
    private String errorMessage;
}
