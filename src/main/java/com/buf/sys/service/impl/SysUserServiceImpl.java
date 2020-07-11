package com.buf.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.buf.common.annotation.DataFilter;
import com.buf.common.utils.Constant;
import com.buf.common.utils.PageUtils;
import com.buf.common.utils.Query;
import com.buf.sys.dao.SysUserDao;
import com.buf.sys.entity.SysDeptEntity;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.service.SysDeptService;
import com.buf.sys.service.SysUserService;
import com.buf.sys.shiro.ShiroUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
//import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
*系统用户
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {


	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysUserDao sysUserDao;

	/*按条件获取用户明细*/
	public List<SysUserEntity> getUsersByCondition(SysUserEntity user)
	{
		return sysUserDao.getUsersByCondition(user);
	}

	/*取用户最大序号*/
	public Long getMaxUserId()
	{
		return sysUserDao.getMaxUserId();
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveUser(SysUserEntity user,String roles)
	{
		try {
			//sha256加密
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setSalt(salt);
			user.setPassword(ShiroUtils.sha256("123", user.getSalt()));
//			user.setDeptId(1L);

			sysUserDao.saveUser(user);
			sysUserDao.insertUserAndRole(user.getUserId().toString(),roles);
		}catch (Exception ex)
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

	}
	@Transactional(rollbackFor = Exception.class)
	public void updateUser(SysUserEntity user,String roles)
	{
		try {
			sysUserDao.updateUser(user);
			sysUserDao.deleteUserAndRole(Long.valueOf(user.getUserId()));
			sysUserDao.insertUserAndRole(user.getUserId().toString(),roles);
		}catch (Exception ex)
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

	}
	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(Long userId)
	{
		try {
			sysUserDao.deleteUserAndRole(userId);
			sysUserDao.deleteUser(userId);
		}catch (Exception ex)
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@Override
	public int queryUserByUserName(String userName)
	{
		return sysUserDao.queryUserByUserName(userName);
	}

	public void resetPass (SysUserEntity user)
	{
		try {
			sysUserDao.resetPass(user);
		}catch (Exception ex)
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}




}
