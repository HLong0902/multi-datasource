package com.lpbank.balancefluctuation.common;


import javax.persistence.criteria.CriteriaBuilder;

public enum SendStatus {
    ACTIVE(0),
    SENDING(1),
    FAIL(2),
    SENT(3);


    SendStatus(Integer i) {
    }

    public Integer value(){
        return this.ordinal();
    }
}
