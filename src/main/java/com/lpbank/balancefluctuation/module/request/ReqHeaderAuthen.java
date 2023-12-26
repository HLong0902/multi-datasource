package com.lpbank.balancefluctuation.module.request;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqHeaderAuthen {
    private Map<String, String> header;
}
