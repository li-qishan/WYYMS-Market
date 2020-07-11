package com.buf.sys.controller;


import com.alibaba.fastjson.JSONObject;
import com.buf.common.annotation.SysLog;
import com.buf.common.config.Global;
import com.buf.common.utils.JsonMapper;
import com.buf.common.utils.PageUtils;
import com.buf.common.utils.R;
import com.buf.common.utils.StringUtils;
import com.buf.common.validator.Assert;
import com.buf.common.validator.ValidatorUtils;
import com.buf.common.validator.group.AddGroup;
import com.buf.common.validator.group.UpdateGroup;
import com.buf.sys.entity.SysRoleEntity;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.service.SysRoleService;
import com.buf.sys.service.SysUserService;
import com.buf.sys.shiro.ShiroUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 系统用户
 *
 *
 *
 * @date andele 2018年11月15日 上午10:40:10
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	/*获取所有用户明细*/
	@RequestMapping("/list")
	@ResponseBody
	@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
	public Object list(SysUserEntity user){
		if(user==null) user = new SysUserEntity();
		user.setIsDelete(0);
		List<SysUserEntity> list = sysUserService.getUsersByCondition(user);
		return list.stream().sorted(Comparator.comparing(SysUserEntity::getUserCode)).collect(toList());
	}

	/*保存用户信息*/
	@RequestMapping("/saveUser")
	@ResponseBody
	public Object saveUser(SysUserEntity user,HttpServletRequest request)
	{
		String result = "";
		try {
			String roles = request.getParameter("hidRoleId").toString();
			if(user.getUserId()==null)
			{
				int count1 = sysUserService.queryUserByUserName(user.getLoginName());

				//用户名已存在
				if(count1 > 0){
					return result = "1";
				}


				user.setUserId(sysUserService.getMaxUserId()+1);
				user.setIsDelete(0);
				user.setCreateDate(new Date());
				sysUserService.saveUser(user,roles);
				result = "保存用户信息成功！";
			}
			else
			{
				sysUserService.updateUser(user,roles);
				result = "修改用户信息成功！";
			}

		}catch (Exception ex)
		{
			result = "9";
		}
		return result;
	}

	/*保存用户信息*/
	@RequestMapping("/deleteUser")
	@ResponseBody
	public Object deleteUser(HttpServletRequest request)
	{
		String result = "";
		try {
			String ids = request.getParameter("ids").toString();
			sysUserService.deleteUser(Long.valueOf(ids));
			result = "删除用户信息成功！";
		}catch (Exception ex)
		{
			result = "9";
		}
		return result;
	}
	/*重置用户密码*/
	@RequestMapping("/resetPass")
	@ResponseBody
	public Object resetPass(HttpServletRequest request,SysUserEntity sysUser)
	{
		String result="";
		try
		{

			List<SysUserEntity> list = sysUserService.getUsersByCondition(sysUser);
			if(list.size()==0) return "0";
			sysUser = list.get(0);

			//sysUserService.resetPass(sysUser);
			result = "1";
		}
		catch (Exception ex)
		{
			result = "0";
		}
		return result;
	}




}
