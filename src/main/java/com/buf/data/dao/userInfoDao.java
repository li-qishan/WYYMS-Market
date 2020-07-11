package com.buf.data.dao;

import com.buf.data.dao.provider.userInfoProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
public interface userInfoDao
{


    @SelectProvider(type = userInfoProvider.class,method = "getSearchData")
    public List<Map<String,Object>> getSearchData(@Param("param")String param ,@Param("type") String type,
                                                  @Param("deptNo") String deptNo,@Param("userName") String userName);

    @SelectProvider(type = userInfoProvider.class,method = "getBasicData")
    public List<Map<String,Object>> getBasicData(@Param("param")String param ,@Param("type") String type,@Param("deptNo") String deptNo,
                                                 @Param("dataGetNumber") String dataGetNumber,@Param("userName") String userName);

}
