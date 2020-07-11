package com.buf.main.entity;

public class SysCode {

    private String CODE_ID;
    private String CODE_TYPE;
    private String CODE;
    private String CODE_NAME;
    private String ORDER_NUM;
    private String REMARK;
    private String DEL_FLAG;
    private String SYS_FLAG;

    public String getCODE_ID() {
        return CODE_ID;
    }

    public void setCODE_ID(String CODE_ID) {
        this.CODE_ID = CODE_ID;
    }

    public String getCODE_TYPE() {
        return CODE_TYPE;
    }

    public void setCODE_TYPE(String CODE_TYPE) {
        this.CODE_TYPE = CODE_TYPE;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getCODE_NAME() {
        return CODE_NAME;
    }

    public void setCODE_NAME(String CODE_NAME) {
        this.CODE_NAME = CODE_NAME;
    }

    public String getORDER_NUM() {
        return ORDER_NUM;
    }

    public void setORDER_NUM(String ORDER_NUM) {
        this.ORDER_NUM = ORDER_NUM;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getDEL_FLAG() {
        return DEL_FLAG;
    }

    public void setDEL_FLAG(String DEL_FLAG) {
        this.DEL_FLAG = DEL_FLAG;
    }

    public String getSYS_FLAG() {
        return SYS_FLAG;
    }

    public void setSYS_FLAG(String SYS_FLAG) {
        this.SYS_FLAG = SYS_FLAG;
    }
}
