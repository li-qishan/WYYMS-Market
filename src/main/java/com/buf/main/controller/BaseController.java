package com.buf.main.controller;

import com.buf.common.utils.ServletUtils;
import com.buf.main.service.ADeptService;
import com.buf.main.service.BaseService;
import com.buf.main.service.BaselogService;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Liangzhu on 2018/5/23.
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    @Qualifier("BaselogService")
    private BaselogService service;

    protected SysUserEntity getUser() {
//        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        return (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }


    protected String getUsername() {
        return getUser().getLoginName();
    }

    protected Long getDeptId() {
        return getUser().getDeptId();
    }

    protected String getDeptNo() {
        return getUser().getDeptNo();
    }

    protected boolean isLogin() {
        return ShiroUtils.isLogin();
    }

    protected void syslogin(String method, HttpServletRequest request,String operation ) {
        String ip = request.getRemoteHost();
        String user = getUserId().toString();
//        String user = "admin";
        service.insertLog(new Date(), user, user , user,ip);

    }

    /*返回结果集*/
    protected String renderResult(String result, String message, Object data) {
        return ServletUtils.renderResult(result, message, data);
    }

    protected String renderResult(HttpServletResponse response, String result, String message, Object data) {
        return ServletUtils.renderResult(response, result, message, data);
    }

    protected String renderResult(String result, String message) {
        return this.renderResult((String)result, message, (Object)null);
    }

    protected String renderResult(String result, StringBuilder message) {
        return this.renderResult(result, message != null?message.toString():"");
    }

    protected String renderResult(HttpServletResponse response, String result, String message) {
        return this.renderResult(response, result, message, (Object)null);
    }

}
