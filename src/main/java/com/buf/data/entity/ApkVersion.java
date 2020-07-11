package com.buf.data.entity;

import java.util.Date;

/**
 * Created by mawenguang on 2019/5/27.
 */
public class ApkVersion
{
    private String versionControId;
    private String apkName;
    private String apkId;
    private String versionCode;
    private Date uploadTime;
    private String uploadBy;
    private String apkUrl;
    private String isDelete;

    public String getVersionControId()
    {
        return versionControId;
    }

    public void setVersionControId(String versionControId)
    {
        this.versionControId = versionControId;
    }

    public String getApkName()
    {
        return apkName;
    }

    public void setApkName(String apkName)
    {
        this.apkName = apkName;
    }

    public String getApkId()
    {
        return apkId;
    }

    public void setApkId(String apkId)
    {
        this.apkId = apkId;
    }

    public String getVersionCode()
    {
        return versionCode;
    }

    public void setVersionCode(String versionCode)
    {
        this.versionCode = versionCode;
    }

    public Date getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    public String getUploadBy()
    {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy)
    {
        this.uploadBy = uploadBy;
    }

    public String getApkUrl()
    {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl)
    {
        this.apkUrl = apkUrl;
    }

    public String getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(String isDelete)
    {
        this.isDelete = isDelete;
    }
}
