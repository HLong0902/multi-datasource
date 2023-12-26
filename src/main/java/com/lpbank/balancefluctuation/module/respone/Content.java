package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private String accessToken;
    private int expiresIn;
    private String tokenType;
}
