package com.lpbank.balancefluctuation.module.respone;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataRes {
    private HeaderRes header;
    private BodyRes body;
}
