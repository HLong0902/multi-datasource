package com.lpbank.balancefluctuation.module.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BodyReq {
   @JsonProperty("SettleBill")
    private SettleBill SettleBill;
}
