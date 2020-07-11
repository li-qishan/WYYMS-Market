package com.buf.main.dao;

import com.buf.main.entity.SaOrg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/5/28.
 */
public interface SaOrgMapper {

    @Select("select * from ind_dept t where t.parent_no = #{orgNo}")
    List<Map<String,Object>> selectDeptList(@Param("orgNo") String orgNo);

    int deleteByPrimaryKey(String orgNo);

    int insert(SaOrg record);

    SaOrg selectByPrimaryKey(String orgNo);

    List<SaOrg> selectAll();

    int updateByPrimaryKey(SaOrg record);

    List<SaOrg> selectByPOrgNo(String PSaOrg);

    List<SaOrg> selectByOrgNo(String SaOrg);

    //List<Map<String,Object>> selectDeptList(String orgNo);
}
