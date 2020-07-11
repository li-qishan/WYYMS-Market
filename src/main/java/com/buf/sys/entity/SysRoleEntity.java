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
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*角色
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:27:38
 */
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/**
	*角色ID
	 */
	@TableId
	private Long roleId;

	/**
	*角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	private String roleName;

	/**
	*备注
	 */
	private String description;

	/**
	 *标识
	 */
	private String roleKey;

	/**
	*部门ID
	 */
	@NotNull(message="部门不能为空")
	private Long deptId;

	/**
	*部门名称
	 */
	@TableField(exist=false)
	private String deptName;

	@TableField(exist=false)
	private List<SysMenu> menuList = new ArrayList<SysMenu>();

	@TableField(exist=false)
	private List<SysValidate> validateList = new ArrayList<SysValidate>();

	@TableField(exist=false)
	private List<SysDeptEntity> deptList = new ArrayList<SysDeptEntity>();

	@TableField(exist=false)
	private List<SysUserEntity> userList = new ArrayList<SysUserEntity>();

	
	/**
	*创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	*设置：
	*@param roleId 
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	*获取：
	*@return Long
	 */
	public Long getRoleId() {
		return roleId;
	}
	
	/**
	*设置：角色名称
	*@param roleName 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	*获取：角色名称
	*@return String
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	*设置：备注
	*@param remark 备注
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public List<SysMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}

	public List<SysValidate> getValidateList() {
		return validateList;
	}

	public void setValidateList(List<SysValidate> validateList) {
		this.validateList = validateList;
	}

	public List<SysDeptEntity> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<SysDeptEntity> deptList) {
		this.deptList = deptList;
	}

	public List<SysUserEntity> getUserList() {
		return userList;
	}

	public void setUserList(List<SysUserEntity> userList) {
		this.userList = userList;
	}

	public String getId() {
		if(this.roleId==null) return "";
		return  this.getRoleId().toString();
	}

	public String getText() {
		if(this.roleName==null) return "";
		return this.roleName;
	}


}
