package com.lpbank.balancefluctuation.model.entityDatasource2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SEND_DATA_VTS_JOB_CONFIG")
public class JobConfig {
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "SERVICE")
    private String service;
    @Column(name = "OPERATION")
    private String operation;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "MERCHANT_ID")
    private String merchantId;
    @Column(name = "CHANEL")
    private String chanel;
}
