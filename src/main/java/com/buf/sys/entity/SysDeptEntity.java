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
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
*部门管理
*
 */
@TableName("sys_dept")
public class SysDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//部门ID
	@TableId
	private Long deptId;
	//上级部门ID，一级部门为0
	private Long parentDeptId;
	//部门代码
	private String deptCode;
	//部门名称
	private String deptName;
	//部门简称
	private String deptNameJC;
	//排序
	private Integer orderNo;

	private Integer departMentLevel;

	List<SysDeptEntity> children = new ArrayList<SysDeptEntity>();

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getParentDeptId() {
		if(parentDeptId==null) return 0L;
		return parentDeptId;
	}

	public void setParentDeptId(Long parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNameJC() {
		return deptNameJC;
	}

	public void setDeptNameJC(String deptNameJC) {
		this.deptNameJC = deptNameJC;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getDepartMentLevel() {
		return departMentLevel;
	}

	public void setDepartMentLevel(Integer departMentLevel) {
		this.departMentLevel = departMentLevel;
	}

	public String getId() {
		if(this.deptId==null) return "";
		return  this.getDeptId().toString();
	}

	public String getText() {
		if(this.deptName==null) return "";
		return this.deptName;
	}

	public List<SysDeptEntity> getChildren() {
		return children;
	}

	public void setChildren(List<SysDeptEntity> children) {
		this.children = children;
	}
}
