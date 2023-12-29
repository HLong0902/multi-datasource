package com.lpbank.balancefluctuation.model.dto;

import com.lpbank.balancefluctuation.model.entityDatasource2.SendDataTransactionViettelStore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActbDailyLogDTO {
    private SendDataTransactionViettelStore sendDataVTS;

    private String acBranch;
    private String acNo;
    private String trnRefNo;
    private Date txnDtTime;
    private String trnCode;
    private BigDecimal lcyAmount;

}
