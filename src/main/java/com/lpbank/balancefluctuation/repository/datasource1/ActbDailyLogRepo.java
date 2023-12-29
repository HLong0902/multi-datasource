package com.lpbank.balancefluctuation.repository.datasource1;

import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ActbDailyLogRepo extends JpaRepository<ActbDailyLog, Long> {

    @Query(value = "SELECT A.AC_ENTRY_SR_NO, A.AC_NO, " +
            "B.ADDL_TEXT AS TRN_DESC, " +
            "C.CUST_NO, " +
            "(SELECT l.AC_NO from actb_daily_log l where l.batch_no = A.BATCH_NO and l.trn_dt = A.TRN_DT and l.value_dt = A.value_Dt and l.Drcr_Ind = 'D' and rownum = 1) as SOURCE_ACC, " +
            "A.TRN_REF_NO, A.LCY_AMOUNT, A.VALUE_DT, A.TRN_DT, A.AMOUNT_TAG " +
            "FROM actb_daily_log A " +
            "LEFT JOIN detb_jrnl_txn_detail B ON A.TRN_REF_NO = B.REFERENCE_NO " +
            "LEFT JOIN STTM_CUST_ACCOUNT C ON A.AC_NO = C.CUST_AC_NO " +
            "WHERE DRCR_IND = 'D' " +
            "AND a.auth_stat = 'A' " +
            "AND nvl(a.delete_stat, 'X') <> 'C' " +
            "AND NOT EXISTS ( SELECT 1 FROM actb_daily_log b WHERE b.trn_Ref_no = a.trn_ref_no AND b.event = 'REVR' AND b.auth_stat = 'A' AND nvl(b.delete_stat, 'X') <> 'C') " +
            "AND A.TXN_DT_TIME  BETWEEN TO_DATE((select sysdate - interval '6' minute from dual),'DD/MM/RRRR HH24:MI:SS')  AND SYSDATE " +
            "ORDER BY A.AC_ENTRY_SR_NO", nativeQuery = true)
    List<Object[]> getTransFromCore();

    @Query(value = "SELECT A.AC_ENTRY_SR_NO, A.AC_NO, " +
            "( SELECT stghost.pkg_mis_func.fn_get_event_descr(A.MODULE, A.EVENT, A.TRN_REF_NO, A.trn_dt, A.AC_ENTRY_SR_NO) FROM dual) AS TRN_DESC, " +
            "C.CUST_NO, " +
            "(SELECT l.AC_NO from stghost.actb_daily_log l where l.batch_no = A.BATCH_NO and l.trn_dt = A.TRN_DT  and l.value_dt = A.value_Dt and l.Drcr_Ind = 'D' AND l.EVENT_SR_NO = (select EVENT_SR_NO from (SELECT l.TRN_REF_NO, l.EVENT_SR_NO, COUNT(*) AS TOTAL FROM stghost.actb_daily_log l WHERE l.batch_no = A.BATCH_NO AND l.Drcr_Ind = 'D' GROUP BY TRN_REF_NO, EVENT_SR_NO HAVING COUNT(*) = 1)) and rownum = 1) as SOURCE_ACC, " +
            "A.EXTERNAL_REF_NO, A.MODULE, A.AC_BRANCH, A.TRN_REF_NO, A.BATCH_NO, A.TRN_CODE, A.AUTH_ID, A.LCY_AMOUNT, A.VALUE_DT, A.TRN_DT, A.AMOUNT_TAG, " +
            "A.STMT_DT, CAST(CAST(A.TXN_DT_TIME AS timestamp) AS DATE) TXN_DT_TIME " +
            "FROM stghost.actb_daily_log A " +
            "LEFT JOIN stghost.STTM_CUST_ACCOUNT C ON A.AC_NO = C.CUST_AC_NO " +
            "WHERE DRCR_IND = 'D' " +
            "AND a.auth_stat = 'A' " +
            "AND nvl(a.delete_stat, 'X') <> 'C' " +
            "AND NOT EXISTS ( SELECT 1 FROM stghost.actb_daily_log b WHERE b.trn_Ref_no = a.trn_ref_no AND b.event = 'REVR' AND b.auth_stat = 'A' AND nvl(b.delete_stat, 'X') <> 'C' ) " +
            "AND CAST(CAST(A.TXN_DT_TIME AS timestamp) AS DATE) BETWEEN TO_DATE((select sysdate - interval '6' minute from dual),'DD/MM/RRRR HH24:MI:SS')  AND SYSDATE " +
            "ORDER BY A.AC_ENTRY_SR_NO;", nativeQuery = true)
    List<Object[]> getTransFromDWH();

    @Query(value = "SELECT CUST_NO FROM sttm_cust_account WHERE CUST_AC_NO = :custAcNo;", nativeQuery = true)
    List<Object[]> findCustNo(@Param("custAcNo") String custAcNo);


}
