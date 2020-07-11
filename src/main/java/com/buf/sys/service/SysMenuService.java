package com.buf.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysMenuEntity;

import java.util.List;


/**
*菜单管理
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:42:16
 */
public interface SysMenuService extends IService<SysMenu> {

	public List<SysMenu> getAllMenus();

	public List<SysMenu> getMenusByCondition(SysMenu menu);


	List<SysMenu> getUserMenu(Long userid);

	void InitMenuTrees(List<SysMenu> menusAll,List<SysMenu> menusReturn);

}
