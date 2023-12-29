package com.lpbank.balancefluctuation.model.entityDatasource2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "send_data_transaction_viettel_store")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendDataTransactionViettelStore {
    @Column(name = "ISSUE_DATE")
    private LocalDateTime issueDate;

    @Id
    @Column(name = "AC_ENTRY_SR_NO")
    private Long acEntrySrNo;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "RETRY_NUMBER")
    private Integer retryNum;

    @Column(name = "MESSAGE_LOG")
    private String messageLog;

    @Column(name = "CUST_NO")
    private String custNo;

    @Column(name = "TRN_DESC")
    private String trnDesc;

    @Column(name = "SOURCE_ACC")
    private String sourceAcc;

    public void setMessageLog(String messageLog) {
        if (this.messageLog == null) this.messageLog = messageLog;
        else this.messageLog += messageLog + "\n";
    }
}
