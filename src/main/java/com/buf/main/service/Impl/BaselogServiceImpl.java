package com.buf.main.service.Impl;

import com.buf.main.dao.BaselogMapper;
import com.buf.main.service.BaselogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lyf on 2018/7/23.
 */
@Service("BaselogService")
public class BaselogServiceImpl implements BaselogService {

    @Autowired
    private BaselogMapper mapper;

    public int insertLog(Date syncdate, String usercode, String loginname , String  realname, String loginip){
        return mapper.insertLog(syncdate, usercode, loginname , realname,loginip);
    }
}
