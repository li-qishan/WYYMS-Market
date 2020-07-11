package com.buf.data.dao;

import com.buf.data.dao.provider.businessProcessProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 业扩流程查询
 *
 * @author liqishan
 * @create 2019-06-21 15:21
 */
public interface businessProcessDao {

    @SelectProvider(type = businessProcessProvider.class,method = "getLessBusinessProcessByOrderNum")
    public List<Map<String,Object>> getLessBusinessProcessByOrderNum(@Param("orderNum") String orderNum,@Param("selectType") String selectType,
                                                                     @Param("deptNo") String deptNo,@Param("userName") String userName);


    @SelectProvider(type = businessProcessProvider.class,method = "getMoreBusinessProcessByOrderNum")
    public List<Map<String,Object>> getMoreBusinessProcessByOrderNum(@Param("orderNum") String orderNum ,@Param("selectType") String selectType,
                                                                     @Param("deptNo") String deptNo,@Param("userName") String userName);
}
