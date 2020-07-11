package com.buf.datasource.secondDao;

import com.buf.data.entity.ApkVersion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by mawenguang on 2019/5/27.
 */
public interface APKDownDao
{
    @Insert("INSERT INTO APK_VERSION ( APKNAME, APKID, VERSIONCODE, UPLOADTIME, UPLOADBY, APKURL, IS_DELETE ) " +
            "VALUES ( #{apkName}, #{apkId}, #{versionCode}, #{uploadTime}, #{uploadBy}, #{apkUrl},#{isDelete} ) ")
    public int insert(ApkVersion a);

    @Select("SELECT * FROM APK_VERSION WHERE IS_DELETE = '0'")
    public List<ApkVersion> selectAll();

    @Select("SELECT * FROM APK_VERSION WHERE  APKID = #{apkId} AND IS_DELETE = '0'")
    public List<ApkVersion> selectByApkId(@Param("apkId") String apkId,@Param("versionCode") String versionCode);

    @Select("SELECT * FROM APK_VERSION WHERE  APKID = #{apkId} AND IS_DELETE = '0'")
    public List<ApkVersion> selectOneByApkId(@Param("apkId") String apkId);

    @Select("SELECT APKURL FROM APK_VERSION WHERE APKID = #{apkId} AND IS_DELETE = '0'")
    public String getUrlByApkId(@Param("apkId") String apkId);


    @Select("SELECT VERSIONCODE FROM APK_VERSION WHERE APKID = #{apkId} AND IS_DELETE = '0'")
    public String getVersionByApkId(@Param("apkId") String apkId);

    @Update("UPDATE APK_VERSION SET IS_DELETE = '1' WHERE APKID = #{apkId} AND IS_DELETE = '0'")
    public int delete(@Param("apkId") String apkId);


}
