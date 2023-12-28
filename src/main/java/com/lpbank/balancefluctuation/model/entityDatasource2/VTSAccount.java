package com.lpbank.balancefluctuation.model.entityDatasource2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ESB_VTS_ACCOUNT_JOB", catalog = "USER_ESB")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VTSAccount {

    @Id
    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "MODIFY_TIME")
    private LocalDateTime modifyTime;

    @Column(name = "JOB_TIME")
    private LocalDateTime jobTime;

    @Column(name = "JOB_STATUS")
    private String jobStatus;
    
    @Column(name = "RECORD_STAT")
    private String recordStat;
}
