package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Header {
    private String msgid;
    private String service;
    private String operation;
    private String userid;
}
