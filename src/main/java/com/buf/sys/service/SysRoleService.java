package com.buf.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.buf.common.utils.PageUtils;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
*角色
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends IService<SysRoleEntity> {


	List<SysRoleEntity> roleList();

	List<SysRoleEntity> getRoleByKey(String roleKey);

	List<SysRoleEntity> validateRoleByKey(String roleKey,Long roleId);

	SysRoleEntity getRole(Long roleId);

	SysRoleEntity getRoleInfo(Long roleId);

	Long getMaxRoleId();

	void saveRole(SysRoleEntity role);

	void updateRole(SysRoleEntity role);

	void deleteRole(Long roleId);

	List<SysMenu> getMenusByRole(Long roleId);

	void insertRoleAndMenu(String roleId,String menuIds);

	List<SysMenu> getMenusByRoleId(Long roleId);

	void insertRoleAndValidate(String roleId,String validateIds);

}
