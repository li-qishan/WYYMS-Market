package com.buf.data.entity;

public class Payment {

    private String CONS_NO;

    private String CHARGE_YM;

    private String PAYMENTTYPE;

    private String RCV_AMT;

    private String paymentTime;

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCONS_NO() {
        return CONS_NO;
    }

    public void setCONS_NO(String CONS_NO) {
        this.CONS_NO = CONS_NO;
    }

    public String getCHARGE_YM() {
        return CHARGE_YM;
    }

    public void setCHARGE_YM(String CHARGE_YM) {
        this.CHARGE_YM = CHARGE_YM;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPAYMENTTYPE() {
        return PAYMENTTYPE;
    }

    public void setPAYMENTTYPE(String PAYMENTTYPE) {
        this.PAYMENTTYPE = PAYMENTTYPE;
    }

    public String getRCV_AMT() {
        return RCV_AMT;
    }

    public void setRCV_AMT(String RCV_AMT) {
        this.RCV_AMT = RCV_AMT;
    }
}
