package com.buf.sys.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.buf.common.utils.Constant;

import com.buf.common.utils.MapUtils;
import com.buf.sys.dao.SysMenuDao;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysMenuEntity;
import com.buf.sys.service.SysMenuService;
import com.buf.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;

    @Autowired
    private SysMenuDao sysMenuDao;

    public List<SysMenu> getUserMenu(Long userid)
    {
        List<SysMenu> list = sysMenuDao.getUserMenu(userid);
        /*初始化菜单树*/
        List<SysMenu> menusReturn = new ArrayList<SysMenu>();
        InitMenuTrees(list,menusReturn);
        return menusReturn;
    }
    /*初始化菜单树*/
    public void InitMenuTrees(List<SysMenu> menusAll,List<SysMenu> menusReturn)
    {
        List<SysMenu> MenusRecursion = new ArrayList<SysMenu>();
        MenusRecursion = menusAll;
        List<SysMenu> listRoot=MenusRecursion.stream().filter(m->m.getParentMenuId().equals(0L))
                .sorted(Comparator.comparing(SysMenu::getOrderNo)).collect(toList());
        for(SysMenu menu:listRoot)
        {
            if(!menusReturn.contains(menu)) {
                menusReturn.add(menu);
                MenusRecursion.remove(menu);
                InitMenuTreesSub(menu, MenusRecursion,menusReturn);
            }
        }
    }
    /*初始化菜单子项*/
    private  void InitMenuTreesSub(SysMenu sysmenu, List<SysMenu> MenusRecursion,List<SysMenu> menusReturn)
    {
        List<SysMenu> menusSub = MenusRecursion.stream().filter(menusRecursion->menusRecursion.getParentMenuId().equals(sysmenu.getMenuId()))
                .sorted(Comparator.comparing(SysMenu::getOrderNo)).collect(toList());
        for (SysMenu menu : menusSub)
        {
            sysmenu.getChildren().add(menu);
            MenusRecursion.remove(menu);
            if (IsHasChildren(menu, MenusRecursion))
            {
                InitMenuTreesSub(menu, MenusRecursion,menusReturn);
            }

        }
    }
    /*判断菜单项是否有子项*/
    private Boolean IsHasChildren(SysMenu sysmenu, List<SysMenu> waitmenulist)
    {
        int ncount = waitmenulist.stream().filter(m->m.getParentMenuId().equals(sysmenu.getMenuId()))
                .collect(toList()).size();
        if (ncount > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public List<SysMenu> getAllMenus()
    {
        List<SysMenu> list =  sysMenuDao.getAllMenus();
        /*初始化菜单树*/
        List<SysMenu> menusReturn = new ArrayList<SysMenu>();
        InitMenuTrees(list,menusReturn);
        return menusReturn;
    }

    public List<SysMenu> getMenusByCondition(SysMenu menu)
    {
        return sysMenuDao.getMenusByCondition(menu);
    }


}
