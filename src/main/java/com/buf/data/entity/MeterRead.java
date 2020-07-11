package com.buf.data.entity;

/**
 * Created by mawenguang on 2019/7/24.
 */
public class MeterRead
{
    private String readType;
    private String readCount;
    private String checkType;
    private String mrDate;
    private String usageCode;

    public String getReadType()
    {
        return readType;
    }

    public void setReadType(String readType)
    {
        this.readType = readType;
    }

    public String getReadCount()
    {
        return readCount;
    }

    public void setReadCount(String readCount)
    {
        this.readCount = readCount;
    }

    public String getCheckType()
    {
        return checkType;
    }

    public void setCheckType(String checkType)
    {
        this.checkType = checkType;
    }

    public String getMrDate()
    {
        return mrDate;
    }

    public void setMrDate(String mrDate)
    {
        this.mrDate = mrDate;
    }

    public String getUsageCode()
    {
        return usageCode;
    }

    public void setUsageCode(String usageCode)
    {
        this.usageCode = usageCode;
    }
}
