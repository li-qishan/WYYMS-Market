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
import com.buf.sys.entity.SysRoleEntity;
import com.buf.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
*用户管理
*
*@author andele modify
*
*@date 2018年11月15日 15:34:11
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	/*按条件获取用户明细*/
	@SelectProvider(type = SQLProvider.class,method = "getUsersByCondition")
	@Results({
			@Result(column="USERID",property="userId"),
			@Result(column="USERID",property="roleList",
					many=@Many(
							select="com.buf.sys.dao.SysUserDao.getRolesByUser",
							fetchType= FetchType.EAGER
					)
			),
			@Result(column="DEPTID",property="deptId"),
			@Result(column="DEPTID",property="deptName",
					many=@Many(
							select="com.buf.sys.dao.SysUserDao.getDeptById",
							fetchType= FetchType.EAGER
					)
			)
	})
	public List<SysUserEntity> getUsersByCondition(@Param("user")SysUserEntity user);


	/*通过用户ID获取分配权限列表*/
	@Select("select deptname from SYSDEPARTMENT where deptID = #{deptId} ")
	String getDeptById(@Param("deptId") Long deptId);

	/*通过用户ID获取所属部门名称*/
	@Select("select a.* from SYSUSERANDROLE t,SYSROLE a where t.ROLEID = a.ROLEID and t.USERID = #{userId} ")
	List<SysRoleEntity> getRolesByUser(@Param("userId") Long userId);



	/*最大用户ID*/
	@Select("select MAX(t.USERID) from SYSUSER t ")
	Long getMaxUserId();

	@Insert("insert into SYSUSER (USERID,DEPTID,USERCODE,LOGINNAME,SALT,PASSWORD,REALNAME,GENDER,TELEPHONE,USERADDRESS,DESCRIPTION,STATE,CREATEDATE,ISDELETE,MYPAGE,IPSTR)"+
			" values (#{userId},#{deptId},#{userCode}, #{loginName},#{salt},#{password},#{realName},#{gender},#{telephone},#{userAddress},#{description},#{state},#{createDate},#{isDelete},#{mypage},#{ipStr})")
	void saveUser(SysUserEntity user);


	@Update("update SYSUSER set DEPTID = #{deptId},USERCODE = #{userCode},LOGINNAME =#{loginName},REALNAME =#{realName},GENDER =#{gender},STATE =#{state},TELEPHONE =#{telephone},IPSTR =#{ipStr} where USERID = #{userId}")
	void updateUser(SysUserEntity user);

	@InsertProvider(type = SQLProvider.class,method = "insertUserAndRole")
	void insertUserAndRole(@Param("userId")String userId,@Param("roleIds")String roleIds);

	@Delete("delete from SYSUSERANDROLE where USERID = #{userId}")
	void deleteUserAndRole(@Param("userId")Long userId);

	@Delete("delete from SYSUSER where USERID = #{userId}")
	void deleteUser(@Param("userId")Long userId);

	@Select("select t.loginname,t.password,t.salt,t.realname,t.useraddress,t.telephone,t.state,t.deptid,t.createdate,t.userid " +
			"     from SYSUSER t " +
			"where t.loginname = #{loginname}")
	SysUserEntity findUser(@Param("loginname") String username);

	/***
	 * 修改密码
	 * @param userId
	 * @param password
	 * @param newPassword
	 * @return
	 */

	@Update("UPDATE SYSUSER a SET a.password = #{newPassword} WHERE a.USER_ID = #{userId} AND a.PASSWORD = #{password}")
	void updatePassword(@Param("userId") String userId,@Param("password") String password, @Param("newPassword") String newPassword);

	@Update("UPDATE SYSUSER a SET a.password = #{password} WHERE a.USER_ID = #{userId}")
	void resetPass(SysUserEntity user);


	@Select("select count(*) from SYSUSER where loginname = #{userName}")
	int queryUserByUserName(String userName);

}
