package com.buf.main.service;

import java.util.Date;

/**
 * Created by lyf on 2018/7/23.
 */
public interface BaselogService {

    public int insertLog(Date syncdate,String usercode,String loginname ,String  realname,String loginip);
}
