package com.buf.data.entity;

/**
 * Created by mawenguang on 2019/7/24.
 */
public class Meter
{

    private String consMtId;
    private String barCode;
    private String tFactor;
    private String instDate;
    private String lastCheckDate;

    public String getConsMtId()
    {
        return consMtId;
    }

    public void setConsMtId(String consMtId)
    {
        this.consMtId = consMtId;
    }

    public String getBarCode()
    {
        return barCode;
    }

    public void setBarCode(String barCode)
    {
        this.barCode = barCode;
    }

    public String gettFactor()
    {
        return tFactor;
    }

    public void settFactor(String tFactor)
    {
        this.tFactor = tFactor;
    }

    public String getInstDate()
    {
        return instDate;
    }

    public void setInstDate(String instDate)
    {
        this.instDate = instDate;
    }

    public String getLastCheckDate()
    {
        return lastCheckDate;
    }

    public void setLastCheckDate(String lastCheckDate)
    {
        this.lastCheckDate = lastCheckDate;
    }
}
