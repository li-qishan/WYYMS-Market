package com.buf.data.service;

import com.buf.data.entity.SysLog;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mawenguang on 2019/5/30.
 */
public interface SysLogUserSearchService
{

    int insertLog(HttpServletRequest request, SysLog log);
}
