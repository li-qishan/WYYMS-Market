package com.buf.data.entity;

/**
 * Created by mawenguang on 2019/6/25.
 */
public class UpdatePhoneList
{
    private String consNo;// 用户号
    private String consName;// 用户名
    private String operType;// 操作类型
    private String newPhone;// 新号码
    private String oldPhone;// 旧号码
    private String operTime;// 操作时间
    private String operBy;// 操作人
    private String contactMode;// 联系人类型

    public String getConsNo()
    {
        return consNo;
    }

    public void setConsNo(String consNo)
    {
        this.consNo = consNo;
    }

    public String getConsName()
    {
        return consName;
    }

    public void setConsName(String consName)
    {
        this.consName = consName;
    }

    public String getOperType()
    {
        return operType;
    }

    public void setOperType(String operType)
    {
        this.operType = operType;
    }

    public String getNewPhone()
    {
        return newPhone;
    }

    public void setNewPhone(String newPhone)
    {
        this.newPhone = newPhone;
    }

    public String getOldPhone()
    {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone)
    {
        this.oldPhone = oldPhone;
    }

    public String getOperTime()
    {
        return operTime;
    }

    public void setOperTime(String operTime)
    {
        this.operTime = operTime;
    }

    public String getOperBy()
    {
        return operBy;
    }

    public void setOperBy(String operBy)
    {
        this.operBy = operBy;
    }

    public String getContactMode()
    {
        return contactMode;
    }

    public void setContactMode(String contactMode)
    {
        this.contactMode = contactMode;
    }
}
