package com.buf.data.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by mawenguang on 2019/5/22.
 */
public interface loginDao
{
    @Select("SELECT * FROM epm_ln.SA_USER where USER_NAME = #{userName}")
    public Map<String,Object> getUserByName(@Param("userName") String userName);

    @Select("select a.org_no from  epm_ln.sa_org a ,epm_ln.sa_dept b ,epm_ln.sa_user c " +
            " where a.org_no=b.org_no and b.dept_id=c.dept_id and c.user_name=#{userName}")
    public String getUserDeptNo(@Param("userName") String userName);
}
