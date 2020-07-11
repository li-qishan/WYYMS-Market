package com.buf.main.entity;

/**
 * Created by lyf on 2018/7/17.
 */
public class BDept {
    private String dept_NO;
    private String dept_NAME;
    private String parent_NO;
    private String org_NO;
    private String short_NAME;
    private String ind_DIM_ID;

    public String getDept_NO() {
        return dept_NO;
    }

    public void setDept_NO(String dept_NO) {
        this.dept_NO = dept_NO;
    }

    public String getDept_NAME() {
        return dept_NAME;
    }

    public void setDept_NAME(String dept_NAME) {
        this.dept_NAME = dept_NAME;
    }

    public String getParent_NO() {
        return parent_NO;
    }

    public void setParent_NO(String parent_NO) {
        this.parent_NO = parent_NO;
    }

    public String getOrg_NO() {
        return org_NO;
    }

    public void setOrg_NO(String org_NO) {
        this.org_NO = org_NO;
    }

    public String getShort_NAME() {
        return short_NAME;
    }

    public void setShort_NAME(String short_NAME) {
        this.short_NAME = short_NAME;
    }

    public String getInd_DIM_ID() {
        return ind_DIM_ID;
    }

    public void setInd_DIM_ID(String ind_DIM_ID) {
        this.ind_DIM_ID = ind_DIM_ID;
    }
}
