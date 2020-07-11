/**
*Copyright 2018 人人开源 http://www.renren.io
*<p>
*Licensed under the Apache License, Version 2.0 (the "License"); you may not
*use this file except in compliance with the License. You may obtain a copy of
*the License at
*<p>
*http://www.apache.org/licenses/LICENSE-2.0
*<p>
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
*WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
*License for the specific language governing permissions and limitations under
*the License.
 */

package com.buf.sys.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.buf.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
*部门管理 andele
*
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    /*得到部门信息*/
    @Select("select t.DEPTID,t.PARENTDEPTID,t.DEPTCODE,t.DEPTNAME,t.DEPTNAMEJC,t.ORDERNO,t.DEPARTMENTLEVEL " +
            "     from SYSDEPARTMENT t order by t.DEPTID")
    List<SysDeptEntity> getDepartAll();

    /*得到部门信息*/
    @Select("select t.DEPTID,t.PARENTDEPTID,t.DEPTCODE,t.DEPTNAME,t.DEPTNAMEJC,t.ORDERNO,t.DEPARTMENTLEVEL " +
            "     from SYSDEPARTMENT t " +
            "where t.DEPTID = #{departId}")
    List<SysDeptEntity> getDepart(@Param("departId")Long departId);

    /*得到下级部门信息*/
    @Select("select t.DEPTID,t.PARENTDEPTID,t.DEPTCODE,t.DEPTNAME,t.DEPTNAMEJC,t.ORDERNO,t.DEPARTMENTLEVEL " +
            "     from SYSDEPARTMENT t " +
            "where t.PARENTDEPTID = #{departId}")
    List<SysDeptEntity> getSubDepart(@Param("departId")Long departId);

    /*通过部门名称查找部门信息*/
    @Select("select t.DEPTID,t.PARENTDEPTID,t.DEPTCODE,t.DEPTNAME,t.DEPTNAMEJC,t.ORDERNO,t.DEPARTMENTLEVEL " +
            "     from SYSDEPARTMENT t " +
            "where t.DEPTNAME = #{departName}")
    List<SysDeptEntity> getDepByName(@Param("departName")String departName);

    /*判断用户名称是否重复*/
    @Select("select t.DEPTID,t.PARENTDEPTID,t.DEPTCODE,t.DEPTNAME,t.DEPTNAMEJC,t.ORDERNO,t.DEPARTMENTLEVEL " +
            "     from SYSDEPARTMENT t " +
            "where t.DEPTNAME = #{departName} and t.DEPTID <> #{departId}")
    List<SysDeptEntity> validateDepByName(@Param("departName") String departName,@Param("departId") Long departId);

    /*通过部门ID查找部门信息*/
    @Select("select t.DEPTID,t.PARENTDEPTID,t.DEPTCODE,t.DEPTNAME,t.DEPTNAMEJC,t.ORDERNO,t.DEPARTMENTLEVEL " +
            "     from SYSDEPARTMENT t " +
            "where t.DEPTID = #{departId}")
    SysDeptEntity getDepById(@Param("departId")Long departId);

    /*通过部门ID查找部门信息*/
    @Select("select t.DEPTID,t.PARENTDEPTID,t.DEPTCODE,t.DEPTNAME,t.DEPTNAMEJC,t.ORDERNO,t.DEPARTMENTLEVEL " +
            "     from SYSDEPARTMENT t " +
            "where t.PARENTDEPTID = #{departId}")
    List<SysDeptEntity> getSubDepById(@Param("departId")Long departId);

    /*最大部门ID*/
    @Select("select MAX(t.DEPTID) from SYSDEPARTMENT t ")
    Long getMaxDepartId();

    @Insert("insert into SYSDEPARTMENT (DEPTID,PARENTDEPTID,DEPTCODE,DEPTNAME,DEPTNAMEJC,ORDERNO,DEPARTMENTLEVEL) " +
            "values (#{deptId},#{parentDeptId},#{deptCode}, #{deptName}, #{deptNameJC}, #{orderNo}, #{departMentLevel})")
    void saveDept(SysDeptEntity dept);

    @Update("update SYSDEPARTMENT set PARENTDEPTID = #{parentDeptId},DEPTCODE = #{deptCode},DEPTNAME = #{deptName},DEPTNAMEJC =#{deptNameJC} where DEPTID = #{deptId}")
    void updateDept(SysDeptEntity dept);

    @Delete("delete from SYSDEPARTMENT where DEPTID = #{departId}")
    void deleteDept(@Param("departId")Long departId);


}
