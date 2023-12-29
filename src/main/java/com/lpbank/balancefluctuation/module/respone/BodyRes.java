package com.lpbank.balancefluctuation.module.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BodyRes {
    @JsonProperty("SettleBill")
    private SettleBillRes SettleBill;
}
