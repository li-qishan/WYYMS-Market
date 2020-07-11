package com.buf.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.buf.common.annotation.DataFilter;
import com.buf.common.config.Global;
import com.buf.common.utils.Constant;
import com.buf.common.utils.PageUtils;
import com.buf.common.utils.Query;
import com.buf.sys.dao.SysRoleDao;
import com.buf.sys.entity.SysDeptEntity;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysRoleEntity;
import com.buf.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
*角色
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysMenuService sysMenuService;




	public List<SysRoleEntity> roleList() {
		return sysRoleDao.roleList();
	}

	public List<SysRoleEntity> getRoleByKey(String roleKey)
	{
		return sysRoleDao.getRoleByKey(roleKey);
	}

	public List<SysRoleEntity> validateRoleByKey(String roleKey,Long roleId)
	{
		return sysRoleDao.validateRoleByKey(roleKey,roleId);
	}

	public SysRoleEntity getRole(Long roleId)
	{
		return sysRoleDao.getRole(roleId);
	}

	public SysRoleEntity getRoleInfo(Long roleId)
	{
		return sysRoleDao.getRoleInfo(roleId);
	}

	public Long getMaxRoleId()
	{
		return sysRoleDao.getMaxRoleId();
	}

	public void saveRole(SysRoleEntity role)
	{
		sysRoleDao.saveRole(role);
	}

	public void updateRole(SysRoleEntity role)
	{
		sysRoleDao.updateRole(role);
	}

	public void deleteRole(Long roleId)
	{
		sysRoleDao.deleteRole(roleId);
	}

	public List<SysMenu> getMenusByRole(Long roleId)
	{
		return sysRoleDao.getMenusByRole(roleId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertRoleAndMenu(String roleId,String menuIds)
	{
		try {
			sysRoleDao.deleteRoleAndMenu(Long.valueOf(roleId));
			sysRoleDao.insertRoleAndMenu(roleId,menuIds);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	public List<SysMenu> getMenusByRoleId(Long roleId)
	{
		List<SysMenu> menusAll = new ArrayList<SysMenu>();
		SysRoleEntity role = sysRoleDao.getRoleInfo(roleId);
		if(role.getRoleKey().equals(Global.ROLESYSTEMMANAGER))
		{
			SysMenu menu = new SysMenu();
			menu.setMenuAttribute(0);
			menusAll = sysMenuService.getMenusByCondition(menu);
		}
		else
		{
			for(SysMenu menu:role.getMenuList())
			{
				if(!menusAll.contains(menu))
				{
					menusAll.add(menu);
				}
			}
		}

		List<SysMenu> menusReturn = new ArrayList<SysMenu>();

		sysMenuService.InitMenuTrees(menusAll,menusReturn);

		return menusReturn;
	}
	@Transactional(rollbackFor = Exception.class)
	public void insertRoleAndValidate(String roleId,String validateIds)
	{
		try {
			sysRoleDao.deleteRoleAndValidate(Long.valueOf(roleId));
			sysRoleDao.insertRoleAndValidate(roleId,validateIds);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}


}
