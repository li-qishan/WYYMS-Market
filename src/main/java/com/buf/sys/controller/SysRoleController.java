package com.buf.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.buf.common.annotation.SysLog;
import com.buf.common.utils.JsonMapper;
import com.buf.common.utils.PageUtils;
import com.buf.common.utils.R;
import com.buf.common.utils.StringUtils;
import com.buf.common.validator.ValidatorUtils;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysRoleEntity;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.entity.SysValidate;
import com.buf.sys.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 角色管理
 *
 *
 *
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysMenuService sysMenuService;


	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(String params){
		List<SysRoleEntity> roleList = sysRoleService.roleList();
		return roleList;
	}
	/*验证角色标识*/
	@RequestMapping("/validateRoleKey")
	@ResponseBody
	public Object validateRoleKey(HttpServletRequest request)
	{
		String result="";
		try
		{
			Long roleId = Long.valueOf(request.getParameter("roleId"));
			String roleKey = request.getParameter("roleKey").toString();
			String editType = request.getParameter("editType").toString();


			if (editType.equals("add"))
			{
				List<SysRoleEntity> sysRoles = sysRoleService.getRoleByKey(roleKey);
				if(sysRoles!=null && sysRoles.size()>0)
				{
					result = "0";
				}
				else
				{
					result = "1";
				}
			}
			else if (editType.equals("mod"))
			{
				List<SysRoleEntity> sysRoles = sysRoleService.validateRoleByKey(roleKey,roleId);
				if (sysRoles != null && sysRoles.size()>0)
				{
					result = "0";
				}
				else
				{
					result = "1";
				}
			}

		}
		catch (Exception ex)
		{
			result = "9";
		}
		System.out.println(result);
		return result;
	}

	@RequestMapping("/modifyRole")
	@ResponseBody
	public Object modifyRole(HttpServletRequest request)
	{
		Long roleId = Long.valueOf(request.getParameter("roleId"));
		SysRoleEntity de = sysRoleService.getRole(roleId);
		System.out.println(de.getDeptName());
		return de;
	}

	/*保存角色信息*/
	@RequestMapping("/saveRole")
	@ResponseBody
	public Object saveRole(HttpServletRequest request,SysRoleEntity roleData)
	{

		System.out.println(JsonMapper.toJson(roleData));
		String result ="";
		try
		{
			String editType = request.getParameter("editType");
			System.out.println(editType);


			if (editType.equals("add"))
			{
				List<SysRoleEntity> list = sysRoleService.getRoleByKey(roleData.getRoleKey());
				if (list.size()>0)
				{
					return "2";
				}


				Long roleID = sysRoleService.getMaxRoleId()+1;
				roleData.setRoleId(roleID);

				sysRoleService.saveRole(roleData);

				result = "保存角色【"+roleData.getRoleName()+"】成功!";

			}
			else if (editType.equals("mod"))
			{
				List<SysRoleEntity> list = sysRoleService.validateRoleByKey(roleData.getRoleKey(),roleData.getRoleId());
				if (list.size()>0)
				{
					return "2";
				}

				sysRoleService.updateRole(roleData);

				result = "修改角色【"+roleData.getRoleName()+"】成功!";


			}


		}
		catch (Exception ex)
		{
			System.out.println(ex.fillInStackTrace());
			result = "9";
		}

		return result;
	}
	/*删除角色信息*/
	@RequestMapping("/deleteRole")
	@ResponseBody
	public Object deleteRole(HttpServletRequest request)
	{
		String result = "";
		try {
			String roleId = request.getParameter("ids");
			SysRoleEntity role = sysRoleService.getRoleInfo(Long.valueOf(roleId));

			if (role.getUserList().size() > 0)
			{
				result = "8";
			}
			else if (role.getMenuList().size() > 0)
			{
				result ="7";
			}
			else
			{
				sysRoleService.deleteRole(Long.valueOf(roleId));
				result = "删除角色成功!";
			}

		}catch (Exception ex)
		{
			result = "9";
		}

		return result;
	}

	@RequestMapping("/getMenuDataById")
	@ResponseBody
	public Object getMenuDataById(HttpServletRequest request)
	{
		Long roleId = Long.valueOf(request.getParameter("roleId"));
		List<SysMenu> list = sysRoleService.getMenusByRole(roleId);
		String str = StringUtils.join(list.stream().map(SysMenu::getMenuId).collect(toList()),",");
		System.out.println(str);
		return str;
	}

	@RequestMapping("/saveRoleMenu")
	@ResponseBody
	public Object saveRoleMenu(HttpServletRequest request)
	{
		String result ="";
		String roleId = request.getParameter("roleId").toString();

		String menuIds =request.getParameter("menuIds");
		try {
			sysRoleService.insertRoleAndMenu(roleId,menuIds);
		}catch (Exception ex)
		{
			result = "9";
		}
		result = "分配菜单成功！";
		return result;
	}

	/*分配权限*/
	@RequestMapping("/getMenusByRoleId")
	@ResponseBody
	public Object getMenusByRoleId(HttpServletRequest request)
	{
		String result ="";
		try
		{
			Long roleId = Long.valueOf(request.getParameter("roleId"));
			List<SysMenu> mv = sysRoleService.getMenusByRoleId(roleId);
			System.out.println(JsonMapper.toJson(mv));
			return mv;
		}
		catch (Exception ex)
		{
			result = "获取角色菜单失败！";
		}
		return result;
	}

	/*获取角色已有的权限*/
	@RequestMapping(value = "/validateChecked")
	@ResponseBody
	public Object validateChecked(HttpServletRequest request)
	{
		String result="";
		try
		{
			Long roleId = Long.valueOf(request.getParameter("roleId"));
			SysRoleEntity haverole = sysRoleService.getRoleInfo(roleId);
			String str = StringUtils.join(haverole.getValidateList().stream().map(SysValidate::getValidateId).collect(toList()),",");

			System.out.println("---------:"+str);
			return str;
		}
		catch (Exception ex)
		{
			result = "获取角色已有的权限失败！";
		}
		return result;
	}
	/*保存角色和菜单下的权限*/
	@RequestMapping(value = "/saveRoleAndValidate")
	@ResponseBody
	public Object saveRoleAndValidate(HttpServletRequest request)
	{
		String result = "";
		try
		{
			Long saveroldvalid = Long.valueOf(request.getParameter("roleId"));

			SysRoleEntity saveRoleValid = sysRoleService.getRoleInfo(saveroldvalid);

			List<Long> saveValids = new ArrayList<Long>();
			if (!StringUtils.isBlank(request.getParameter("validId")))
			{

				Long menuId =  Long.valueOf(request.getParameter("menuId"));
				List<Long> oldSaveValids = new ArrayList<Long>();
				for (SysValidate item : saveRoleValid.getValidateList())
				{
					if (!item.getMenuId().equals(menuId))
					{
						oldSaveValids.add(item.getValidateId());
					}
				}

				String[] ids = request.getParameter("validId").split(",");

				for(String vid:ids)
				{
					saveValids.add(Long.valueOf(vid));
				}
				saveValids.addAll(oldSaveValids);
			}

			String validIds = StringUtils.join(saveValids,",");

			try {
				sysRoleService.insertRoleAndValidate(saveroldvalid.toString(),validIds);
			}catch (Exception ex)
			{
				result = "9";
			}

			result = "分配权限成功！";
		}
		catch (Exception ex)
		{
			result = "9";
		}
		return result;
	}

	/**
	 * 下拉角色列表
	 */
	@RequestMapping("/getAllRole")
	@ResponseBody
	public Object getAllRole(){
		List<SysRoleEntity> roleList = sysRoleService.roleList();
		return roleList;
	}


}
