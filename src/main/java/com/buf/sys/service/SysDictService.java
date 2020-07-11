package com.buf.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.buf.common.utils.PageUtils;
import com.buf.sys.entity.SysDictEntity;

import java.util.Map;

/**
*数据字典
 *
*@author Mark sunlightcs@gmail.com
*@since 3.1.0 2018-01-27
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

