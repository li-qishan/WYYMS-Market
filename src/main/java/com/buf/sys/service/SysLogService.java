package com.buf.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.buf.common.utils.PageUtils;
import com.buf.sys.entity.SysLogEntity;

import java.util.Map;


/**
*系统日志
*
*@author Liangzhu modify
*
*@date 2017-03-08 10:40:56
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
