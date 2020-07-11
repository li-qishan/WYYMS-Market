package com.buf.sys.controller;

import com.buf.common.annotation.SysLog;
import com.buf.common.exception.RRException;
import com.buf.common.utils.Constant;
import com.buf.common.utils.JsonMapper;
import com.buf.common.utils.R;
import com.buf.main.entity.SaOrg;
import com.buf.main.entity.SaOrgTree;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysMenuEntity;
import com.buf.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
//import com.github.jlinqer.collections.List

import static java.util.stream.Collectors.toList;

/**
 * 系统菜单
 *
 *
 *
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;

	/*得到用户菜单权限*/
    @RequestMapping(value = "/getUserMenu")
    @ResponseBody
    public List<SysMenu> getUserMenu()
    {
        Long  userid =   getUserId();
        List<SysMenu> list = sysMenuService.getUserMenu(userid);
        return list;
    }

    /*得到所有功能菜单*/
    @RequestMapping(value = "/getAllMenus")
    @ResponseBody
    public List<SysMenu> getAllMenus()
    {
        List<SysMenu> list = sysMenuService.getAllMenus();
        return list;
    }



}
