package com.buf.data.service;

import com.buf.LoginWebService.YdzyServicePortType;

import java.util.Map;

/**
 * Created by mawenguang on 2019/5/22.
 */
public interface LoginService
{
    public Map<String, Object> getUserByName(String userName, String password, YdzyServicePortType portType );

    public String getUserDeptNo(String userName, YdzyServicePortType portType );
}
