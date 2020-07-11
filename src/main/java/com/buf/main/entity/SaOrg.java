package com.buf.main.entity;

/**
 * Created by L on 2018/5/26.
 */
public class SaOrg {
    private String orgNo;

    private String orgName;

    private String pOrgNo;

    private String orgType;

    private String isdelete;

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getPOrgNo() {
        return pOrgNo;
    }

    public void setPOrgNo(String pOrgNo) {
        this.pOrgNo = pOrgNo == null ? null : pOrgNo.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getIsDelete() {
        return isdelete;
    }

    public void setIsDelete(String isdelete) {
        this.isdelete = isdelete == null ? null : isdelete.trim();
    }

}
