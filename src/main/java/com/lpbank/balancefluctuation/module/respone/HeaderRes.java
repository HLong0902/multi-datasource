package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeaderRes {
    private String msgid;
    private String userid;
    private String service;
    private String operation;
    private String destination;
}
