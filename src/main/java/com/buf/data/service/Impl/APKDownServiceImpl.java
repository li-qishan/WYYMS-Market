package com.buf.data.service.Impl;

import com.buf.datasource.secondDao.APKDownDao;
import com.buf.data.entity.ApkVersion;
import com.buf.data.service.APKDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mawenguang on 2019/5/27.
 */
@Service
public class APKDownServiceImpl implements APKDownService
{
    @Autowired
    private APKDownDao dao;

    @Override
    public int insert(ApkVersion apkVersion)
    {
        return dao.insert(apkVersion);
    }

    @Override
    public String getUrlByApkId(String apkId)
    {
        return dao.getUrlByApkId(apkId);
    }

    @Override
    public String getVersionByApkId(String apkId)
    {
        return dao.getVersionByApkId(apkId);
    }

    @Override
    public List<ApkVersion> selectByApkId(String apkId,String versionCode)
    {
        // 查询之前需删除掉之前的旧版本
        deleteByID(apkId);
        return dao.selectByApkId(apkId,versionCode);
    }

    @Override
    public List<ApkVersion> selectOneByApkId(String apkId)
    {
        return dao.selectOneByApkId(apkId);
    }

    @Override
    public List<ApkVersion> selectAll()
    {
        return dao.selectAll();
    }

    @Override
    public int deleteByID(String apkId)
    {
        return dao.delete(apkId);
    }
}
