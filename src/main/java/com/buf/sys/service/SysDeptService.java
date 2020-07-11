package com.buf.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.buf.sys.entity.SysDeptEntity;
import com.buf.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
*部门管理
*
*@author Liangzhu modify
*
*@date 2017-06-20 15:23:47
 */
public interface SysDeptService extends IService<SysDeptEntity> {

	List<SysDeptEntity> getDepart(SysUserEntity loginUser);

	List<SysDeptEntity> getDepByName(String departName);

	List<SysDeptEntity> validateDepByName(String departName,Long departId);

	SysDeptEntity getDepById(Long departId);

	List<SysDeptEntity> getSubDepById(Long departId);

	Long getMaxDepartId();

	void saveDept(SysDeptEntity dept);

	void updateDept(SysDeptEntity dept);

	void deleteDept(Long departId);

}
