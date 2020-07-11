package com.buf.sys.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.buf.common.utils.Constant;

import com.buf.common.utils.MapUtils;
import com.buf.sys.dao.SysMenuDao;
import com.buf.sys.dao.SysValidateDao;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysMenuEntity;
import com.buf.sys.entity.SysValidate;
import com.buf.sys.service.SysValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service("sysValidateService")
public class SysValidateServiceImpl extends ServiceImpl<SysValidateDao, SysValidate> implements SysValidateService {

    @Autowired
    private SysValidateDao sysValidateDao;


    public List<SysValidate> getValidateByCondition(SysValidate validate)
    {
        return sysValidateDao.getValidateByCondition(validate);
    }


}
