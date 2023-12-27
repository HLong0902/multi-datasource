package com.lpbank.balancefluctuation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACTB_DAILY_LOG", catalog = "LVBHOST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActbDailyLog {
    @Id
    @Column(name = "AC_ENTRY_SR_NO")
    private Long acEntrySrNo;

    @Column(name = "MODULE")
    private String module;

    @Column(name = "TRN_REF_NO")
    private String trnRefNo;

    @Column(name = "EVENT_SR_NO")
    private Integer eventSrNo;

    @Column(name = "EVENT")
    private String event;

    @Column(name = "AC_BRANCH")
    private String acBranch;

    @Column(name = "AC_NO")
    private String acNo;

    @Column(name = "AC_CCY")
    private String acCcy;

    @Column(name = "DRCR_IND")
    private String drCrInd;

    @Column(name = "TRN_CODE")
    private String trnCode;

    @Column(name = "AMOUNT_TAG")
    private String amountTag;

    @Column(name = "FCY_AMOUNT")
    private BigDecimal fcyAmount;

    @Column(name = "EXCH_RATE")
    private BigDecimal exchRate;

    @Column(name = "LCY_AMOUNT")
    private BigDecimal lcyAmount;

    @Column(name = "RELATED_CUSTOMER")
    private String relatedCustomer;

    @Column(name = "RELATED_ACCOUNT")
    private String relatedAccount;

    @Column(name = "RELATED_REFERENCE")
    private String relatedReference;

    @Column(name = "BATCH_NO")
    private String batchNo;

    @Column(name = "CURR_NO")
    private Integer currNo;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "BANK_CODE")
    private String bankCode;

    @Column(name = "AVLDAYS")
    private Integer avlDays;

    @Column(name = "BALANCE_UPD")
    private String balanceUpd;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "AUTH_ID")
    private String authId;

    @Column(name = "PRINT_STAT")
    private String printStat;

    @Column(name = "AUTH_STAT")
    private String authStat;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "CUST_GL")
    private String custGl;

    @Column(name = "DISTRIBUTED")
    private String distributed;

    @Column(name = "NODE")
    private String node;

    @Column(name = "DELETE_STAT")
    private String deleteStat;

    @Column(name = "ON_LINE")
    private String onLine;

    @Column(name = "UPDACT")
    private String updact;

    @Column(name = "NODE_SR_NO")
    private Long nodeSrNo;

    @Column(name = "NETTING_IND")
    private String nettingInd;

    @Column(name = "IB")
    private String ib;

    @Column(name = "FLG_POSITION_STATUS")
    private String flgPositionStatus;

    @Column(name = "GLMIS_UPDATE_FLAG")
    private String glmisUpdateFlag;

    @Column(name = "PRODUCT_ACCRUAL")
    private String productAccrual;

    @Column(name = "GLMIS_UPDATE_STATUS")
    private String glmisUpdateStatus;

    @Column(name = "VDBAL_UPDATE_FLAG")
    private String vdbalUpdateFlag;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "GLMIS_VAL_UPD_FLAG")
    private String glmisValUpdFlag;

    @Column(name = "EXTERNAL_REF_NO")
    private String externalRefNo;

    @Column(name = "PROCESSED_FLAG")
    private String processedFlag;

    @Column(name = "MIS_SPREAD")
    private String misSpread;

    @Column(name = "CUST_GL_UPDATE")
    private String custGlUpdate;

    @Column(name = "DONT_SHOWIN_STMT")
    private String dontShowInStmt;

    @Column(name = "IC_BAL_INCLUSION")
    private String icBalInclusion;

    @Column(name = "AML_EXCEPTION")
    private String amlException;

    @Column(name = "ORIG_PNL_GL")
    private String origPnlGl;

    @Column(name = "STMT_DT")
    private Date stmtDt;

    @Column(name = "ENTRY_SEQ_NO")
    private Integer entrySeqNo;

    @Column(name = "IL_BVT_PROCESSED")
    private String ilBvtProcessed;

    @Column(name = "VIRTUAL_AC_NO")
    private String virtualAcNo;

    @Column(name = "CLAIM_AMOUNT")
    private BigDecimal claimAmount;

    @Column(name = "TXN_DT_TIME")
    private Date txnDtTime;
}
