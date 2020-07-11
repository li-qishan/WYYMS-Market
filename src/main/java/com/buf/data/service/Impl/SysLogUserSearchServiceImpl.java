package com.buf.data.service.Impl;

import com.buf.common.utils.JsonMapper;
import com.buf.data.entity.SysLog;
import com.buf.data.service.SysLogUserSearchService;
import com.buf.datasource.secondDao.LogDao;
import com.buf.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by mawenguang on 2019/5/30.
 */
@Service(value = "logService")
public class SysLogUserSearchServiceImpl implements SysLogUserSearchService
{
    @Autowired
    private LogDao dao;

    @Override
    public int insertLog(HttpServletRequest request,SysLog log)
    {
        log.setLog_time(new Date());
        log.setSearch_params(JsonMapper.toJson(request.getParameterMap()));
        log.setOperate_time(new Date());
        return dao.insertLog(log);
    }
}
