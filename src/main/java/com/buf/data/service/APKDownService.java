package com.buf.data.service;

import com.buf.data.entity.ApkVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mawenguang on 2019/5/27.
 */
public interface APKDownService
{
    public List<ApkVersion> selectAll();

    public int deleteByID(String apkId);

    public int insert(ApkVersion apkVersion);

    public String getUrlByApkId(String apkId);

    public String getVersionByApkId(String apkId);

    List<ApkVersion> selectByApkId(String apkId,String versionCode);

    List<ApkVersion> selectOneByApkId(String apkId);
}
