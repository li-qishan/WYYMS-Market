package com.buf.data.entity;

/**
 * Created by mawenguang on 2019/7/23.
 */
public class SP_Prc
{
    private String prcType;// 定价策略
    private String baCalc;// 基本电费计算方式
    private String dmdSpecValue;// 需量核定值
    private String pfEvalMode;// 功率因数考核标准
    private String prcCode;// 电价码
    private String catPrcName;// 目录电价名称
    private Double kwhPrc;// 电价金额
    private String trade;// 电价行业类型
    private String fsFlag;// 分时标志
    private String pfStd;// 功率因数标准
    private String fixRatio;// 固定力率
    private String consNo;
    private String orgNo;
    private String spId;// 受电点id
    private String spName;// 受电点名称

    public String getPrcType()
    {
        return prcType;
    }

    public void setPrcType(String prcType)
    {
        this.prcType = prcType;
    }

    public String getBaCalc()
    {
        return baCalc;
    }

    public void setBaCalc(String baCalc)
    {
        this.baCalc = baCalc;
    }

    public String getDmdSpecValue()
    {
        return dmdSpecValue;
    }

    public void setDmdSpecValue(String dmdSpecValue)
    {
        this.dmdSpecValue = dmdSpecValue;
    }

    public String getPfEvalMode()
    {
        return pfEvalMode;
    }

    public void setPfEvalMode(String pfEvalMode)
    {
        this.pfEvalMode = pfEvalMode;
    }

    public String getPrcCode()
    {
        return prcCode;
    }

    public void setPrcCode(String prcCode)
    {
        this.prcCode = prcCode;
    }

    public String getCatPrcName()
    {
        return catPrcName;
    }

    public void setCatPrcName(String catPrcName)
    {
        this.catPrcName = catPrcName;
    }

    public Double getKwhPrc()
    {
        return kwhPrc;
    }

    public void setKwhPrc(Double kwhPrc)
    {
        this.kwhPrc = kwhPrc;
    }

    public String getTrade()
    {
        return trade;
    }

    public void setTrade(String trade)
    {
        this.trade = trade;
    }

    public String getFsFlag()
    {
        return fsFlag;
    }

    public void setFsFlag(String fsFlag)
    {
        this.fsFlag = fsFlag;
    }

    public String getPfStd()
    {
        return pfStd;
    }

    public void setPfStd(String pfStd)
    {
        this.pfStd = pfStd;
    }

    public String getFixRatio()
    {
        return fixRatio;
    }

    public void setFixRatio(String fixRatio)
    {
        this.fixRatio = fixRatio;
    }

    public String getConsNo()
    {
        return consNo;
    }

    public void setConsNo(String consNo)
    {
        this.consNo = consNo;
    }

    public String getOrgNo()
    {
        return orgNo;
    }

    public void setOrgNo(String orgNo)
    {
        this.orgNo = orgNo;
    }

    public String getSpId()
    {
        return spId;
    }

    public void setSpId(String spId)
    {
        this.spId = spId;
    }

    public String getSpName()
    {
        return spName;
    }

    public void setSpName(String spName)
    {
        this.spName = spName;
    }
}
