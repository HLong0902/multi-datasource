package com.lpbank.balancefluctuation.module.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqAuthen {
    private String password;
    private String username;
}
