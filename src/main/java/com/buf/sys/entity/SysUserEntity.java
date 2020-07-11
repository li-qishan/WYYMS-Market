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

package com.buf.sys.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.buf.common.utils.StringUtils;
import com.buf.common.validator.group.AddGroup;
import com.buf.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
*系统用户
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:28:55
 */
@TableName("sysuser")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	*用户ID
	 */
	@TableId
	private Long userId;

	private String userCode;

	/**
	*用户名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String loginName;
	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	*密码
	 */
//	@NotBlank(message="密码不能为空", groups = AddGroup.class)
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	/**
	*性别
	 */
	private String gender;

	private String salt;

	/**
	 *用户地址
	 */
	private String userAddress;

	/**
	 *描述
	 */
	private String description;

	/**
	*手机号
	 */
	private String telephone;

	/**
	*状态  0：禁用   1：正常
	 */
	private Integer state;

	/**
	 *角色标识
	 */
	private String roleStr;
	/**
	*创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	private Integer isDelete;

	private String mypage;

	private String ipStr;

	/**
	 *角色列表
	 */
	private List<SysRoleEntity> roleList = new ArrayList<SysRoleEntity>();

	private String roleIds;

	private String roleNames;

	private SysDeptEntity sysDept;

	/**
	*部门ID
	 */
	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;

	/**
	*部门名称
	 */
	@TableField(exist=false)
	private String deptName;

	/**
	 *部门编码
	 */
	private String deptNo;

	/**
	*设置：
	*@param userId 
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	*获取：
	*@return Long
	 */
	public Long getUserId() {
		return userId;
	}


	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	*设置：用户名
	*@param username 用户名
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	*获取：用户名
	*@return String
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	*设置：密码
	*@param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	*获取：密码
	*@return String
	 */


	public String getPassword() {
		return password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realname) {
		this.realName = realname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	*设置：手机号
	*@param telephone 手机号
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	*获取：手机号
	*@return String
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	*设置：状态  0：禁用   1：正常
	*@param state 状态  0：禁用   1：正常
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	*获取：状态  0：禁用   1：正常
	*@return Integer
	 */
	public Integer getState() {
		return state;
	}

	/**
	*设置：创建时间
	*@param createTime 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	*获取：创建时间
	*@return Date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setMypage(String mypage) {
		this.mypage = mypage;
	}

	public String getIpStr() {
		return ipStr;
	}

	public void setIpStr(String ipStr) {
		this.ipStr = ipStr;
	}

	public List<SysRoleEntity> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRoleEntity> roleList) {
		this.roleList = roleList;
	}

	public String getRoleIds() {
		return StringUtils.join(roleList.stream().map(SysRoleEntity::getRoleId).collect(toList()),",");
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return StringUtils.join(roleList.stream().map(SysRoleEntity::getRoleName).collect(toList()),",");
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public SysDeptEntity getSysDept() {
		return sysDept;
	}

	public void setSysDept(SysDeptEntity sysDept) {
		this.sysDept = sysDept;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getRoleStr() {
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}
}
