package com.buf.datasource.thirdDao;

import com.buf.data.dao.provider.ArrearsProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/6/21.
 */
public interface ArrearsDao
{

    @SelectProvider(type = ArrearsProvider.class,method = "getBasicData")
    List<Map<String,Object>> getUserArrearsList(@Param("deptNo") String deptNo, @Param("param") String param, @Param("type") String type
            , @Param("month") String month,@Param("startPage") int startPage,@Param("endPage") int endPage, @Param("userName") String userName);


    @SelectProvider(type = ArrearsProvider.class,method = "getUserDetail")
    List<Map<String,Object>> getUserArrearsDetail(@Param("deptNo") String deptNo, @Param("consNo") String param, @Param("month") String type, @Param("userName") String userName);


    @SelectProvider(type = ArrearsProvider.class,method = "getCFUserByOrgNo")
    List<Map<String,Object>> getCFUserByOrgNo(@Param("deptNo") String deptNo);

    @SelectProvider(type = ArrearsProvider.class,method = "getCBUserByOrgNo")
    List<Map<String,Object>> getCBUserByOrgNo(@Param("deptNo") String deptNo);

    @SelectProvider(type = ArrearsProvider.class, method = "getCBUser")
    List<Map<String, Object>> getCBUser(@Param("userName") String userName, @Param("selectType") String selectType, @Param("inputName") String inputName);


}
