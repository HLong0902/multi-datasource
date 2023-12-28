package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeaderReq {
    private String msgid;
    private String service;
    private String operation;
    private String userid;
}
