package com.lpbank.balancefluctuation.module.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Body {
   @JsonProperty("SettleBill")
    private SettleBill SettleBill;
}
