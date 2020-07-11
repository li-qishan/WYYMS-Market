package com.buf.data.entity;

/**
 * 街道实体
 */
public class StreetAndRoadEntiry {

    private String COUNTY_CODE;
    private String CODE_TYPE;
    private String VALUE;
    private String NAME;

    public String getCOUNTY_CODE() {
        return COUNTY_CODE;
    }

    public void setCOUNTY_CODE(String COUNTY_CODE) {
        this.COUNTY_CODE = COUNTY_CODE;
    }

    public String getCODE_TYPE() {
        return CODE_TYPE;
    }

    public void setCODE_TYPE(String CODE_TYPE) {
        this.CODE_TYPE = CODE_TYPE;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
