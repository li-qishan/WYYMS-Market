package com.buf.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.buf.common.utils.PageUtils;
import com.buf.sys.entity.SysUserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
*系统用户
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUserEntity> {

	List<SysUserEntity> getUsersByCondition(SysUserEntity user);

	Long getMaxUserId();

	void saveUser(SysUserEntity user,String roles);

	void updateUser(SysUserEntity user,String roles);

	void deleteUser(Long userId);

	int queryUserByUserName(String userName);

	void resetPass (SysUserEntity user);


















}
