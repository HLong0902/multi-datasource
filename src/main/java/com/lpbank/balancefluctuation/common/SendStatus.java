package com.lpbank.balancefluctuation.common;


import javax.persistence.criteria.CriteriaBuilder;

public enum SendStatus {
    ACTIVE(1),
    SENDING(2),
    FAIL(3),
    SENT(4);


    SendStatus(Integer i) {
    }

    public Integer value(){
        return this.ordinal();
    }
}
