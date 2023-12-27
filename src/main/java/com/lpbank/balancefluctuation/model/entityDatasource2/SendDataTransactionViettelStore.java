package com.lpbank.balancefluctuation.model.entityDatasource2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "send_data_transaction_viettel_store", catalog = "LVBHOST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendDataTransactionViettelStore {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "AC_ENTRY_SR_NO")
    private Long acEntrySrNo;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "RETRY_NUMBER")
    private Integer retryNum;

    @Column(name = "MESSAGE_LOG")
    private String messageLog;
}
