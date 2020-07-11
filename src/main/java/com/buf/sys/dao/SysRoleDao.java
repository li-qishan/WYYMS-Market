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
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysRoleEntity;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.entity.SysValidate;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
*角色管理
*
*@author andele modify
*
*@date 2019年10月11日 上午9:33:33
 */
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {


    @Select("select ROLEID,ROLENAME,ROLEKEY,DESCRIPTION FROM SYSROLE")
    List<SysRoleEntity> roleList();

    /*根据角色标识查找角色信息*/
    @Select("select ROLEID,ROLENAME,ROLEKEY,DESCRIPTION FROM SYSROLE WHERE ROLEKEY = #{roleKey}")
    List<SysRoleEntity> getRoleByKey(@Param("roleKey") String roleKey);
    /*根据角色标识查找角色信息*/
    @Select("select ROLEID,ROLENAME,ROLEKEY,DESCRIPTION FROM SYSROLE WHERE ROLEID = #{roleId}")
    SysRoleEntity getRole(@Param("roleId") Long roleId);

    /*判断用户名称是否重复*/
    @Select("select t.ROLEID,t.ROLENAME,t.ROLEKEY,t.DESCRIPTION " +
            "     from SYSROLE t " +
            "where t.ROLEKEY = #{roleKey} and t.ROLEID <> #{roleId}")
    List<SysRoleEntity> validateRoleByKey(@Param("roleKey") String roleKey,@Param("roleId") Long roleId);


    @Insert("insert into SYSROLE (ROLEID,ROLENAME,ROLEKEY,DESCRIPTION) values (#{roleId},#{roleName}, #{roleKey},#{description})")
    void saveRole(SysRoleEntity role);

    @Update("update SYSROLE set ROLENAME = #{roleName},ROLEKEY = #{roleKey},DESCRIPTION =#{description} where ROLEID = #{roleId}")
    void updateRole(SysRoleEntity role);

    /*最大角色ID*/
    @Select("select MAX(t.ROLEID) from SYSROLE t ")
    Long getMaxRoleId();

    /*根据角色ID查找角色信息,包括用户列表和菜单列表*/
    @Select("select ROLEID,ROLENAME,ROLEKEY,DESCRIPTION FROM SYSROLE WHERE ROLEID = #{roleId}")
    @Results({
            @Result(column="ROLEID",property="roleId"),
            @Result(column="ROLEID",property="menuList",
                    many=@Many(
                            select="com.buf.sys.dao.SysRoleDao.getMenusByRole",
                            fetchType= FetchType.EAGER
                    )
            ),
            @Result(column="ROLEID",property="userList",
                    many=@Many(
                            select="com.buf.sys.dao.SysRoleDao.getUsersByRole",
                            fetchType= FetchType.EAGER
                    )
            ),
            @Result(column="ROLEID",property="validateList",
                    many=@Many(
                            select="com.buf.sys.dao.SysRoleDao.getValidatesByRole",
                            fetchType= FetchType.EAGER
                    )
            )
    })
    SysRoleEntity getRoleInfo(@Param("roleId") Long roleId);

    @Select("select a.* from SYSROLEANDMENU t,SYSMENU a where t.MENUID = a.MENUID and t.ROLEID = #{roleId} ")
    List<SysMenu> getMenusByRole(@Param("roleId") Long roleId);

    @Select("select a.* from SYSUSERANDROLE t,SYSUSER a where t.USERID = a.USERID and t.ROLEID = #{roleId} ")
    List<SysUserEntity> getUsersByRole(@Param("roleId") Long roleId);

    @Select("select a.* from SYSROLEANDVALIDATE t,SYSVALIDATE a where t.VALIDATEID = a.VALIDATEID and t.ROLEID = #{roleId} ")
    List<SysValidate> getValidatesByRole(@Param("roleId") Long roleId);

    @Delete("delete from SYSROLE where ROLEID = #{roleId}")
    void deleteRole(@Param("roleId")Long roleId);


    @InsertProvider(type = SQLProvider.class,method = "insertRoleAndMenu")
    void insertRoleAndMenu(@Param("roleId")String roleId,@Param("menuIds")String menuIds);

    @Delete("delete from SYSROLEANDMENU where ROLEID = #{roleId}")
    void deleteRoleAndMenu(@Param("roleId")Long roleId);


    @InsertProvider(type = SQLProvider.class,method = "insertRoleAndValidate")
    void insertRoleAndValidate(@Param("roleId")String roleId,@Param("validateIds")String validateIds);

    @Delete("delete from SYSROLEANDVALIDATE where ROLEID = #{roleId}")
    void deleteRoleAndValidate(@Param("roleId")Long roleId);

    @Select("select t.* from SYSROLE t,SYSUSERANDROLE a where t.ROLEID = a.ROLEID and a.USERID =#{userId} ")
    List<SysRoleEntity> getRolesfromUserId(@Param("userId") Long userId);

}
