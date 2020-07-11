package com.buf.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysMenuEntity;
import com.buf.sys.entity.SysValidate;

import java.util.List;


/**
*菜单管理
*
*@author Liangzhu modify
*
*@date 2016年9月18日 上午9:42:16
 */
public interface SysValidateService extends IService<SysValidate> {

    public List<SysValidate> getValidateByCondition(SysValidate validate);

}
