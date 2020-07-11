package com.buf.data.controller;

import com.buf.LoginWebService.YdzyServicePortType;
import com.buf.common.utils.LogUtils;
import com.buf.common.utils.ServiceUtils;
import com.buf.common.utils.StringUtils;
import com.buf.data.entity.SysLog;
import com.buf.data.service.LoginService;
import com.buf.data.service.SysLogUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/22.
 */
@RestController
@RequestMapping(value = "/user/login")
public class LoginController
{
    @Autowired
    private LoginService service;

    @Autowired
    private SysLogUserSearchService logService;

    /**
     * @return java.util.Map<java.lang.String,java.lang.Object>用户数据
     * @Author mawenguang
     * @Description //TODO 登录验证(已下线)
     * @Date 11:38 2019/5/22
     * @Param [userName, password]
     **/
    @RequestMapping(value = "/loginValidateDev")
    @ResponseBody
    public Map<String, Object> loginValidateDev(HttpServletRequest request,
                                             @RequestParam(value = "userName", required = false) String userName,
                                             @RequestParam(value = "password", required = false) String password)
    {
        long startTime = System.currentTimeMillis();   //获取开始时间

        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        Map<String, Object> data = new HashMap<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try
        {
//            data.put("deptNo", "");
//            code = -1;
            msg = "测试应用已下线，请使用移动门户！";

//            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password))
//            {
//                // 获取webService接口服务
//                YdzyServicePortType portType = ServiceUtils.getPort();
//
//                Map<String, Object> resultMap = service.getUserByName(userName, password, portType);
//                if (resultMap != null && !resultMap.get("result").equals("0"))
//                {
//                    String resultCode = resultMap.get("returnCode").toString();
//                    if (resultCode.equals("1101"))
//                    {
//                        msg = "登录失败，当前用户不存在！";
//                    }
//                    else if (resultCode.equals("1102"))
//                    {
//                        msg = "登录失败，用户名或者密码错误！";
//                    }
//                    else if (resultCode.equals("1103"))
//                    {
//                        msg = "登录失败，用户已锁定！";
//                    }
//                    else if (resultCode.equals("1104"))
//                    {
//                        msg = "登录失败，用户不可用！";
//                    }
//                    else
//                    {
//                        String orgNo = service.getUserDeptNo(userName, portType);
//                        if (StringUtils.isNotBlank(orgNo))
//                        {
//                        }
//                        else
//                        {
//                            msg = "登录失败，查询用户所属供电单位失败！";
//                        }
//                    }
//                }
//            }
//            else
//            {
//                msg = "用户名密码不能为空！";
//            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            code = 0;
            msg = "数据错误！";
        }
        // 设置返回值
        result.put("data", data);
        result.put("code", code);
        result.put("msg", msg);
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户登录", userName, "0");
        logService.insertLog(request, log);
        return result;
    }

    /**
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 根据用户名获取用户局编码
     * @Date 16:16 2019/8/12
     * @Param [request, userName, password]
     **/
    @RequestMapping(value = "/getUserOrgNo")
    @ResponseBody
    public Map<String, Object> getUserOrgNo(HttpServletRequest request,
                                            @RequestParam(value = "userName", required = false) String userName)
    {
        long startTime = System.currentTimeMillis();   //获取开始时间

        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        Map<String, Object> data = new HashMap<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try
        {
            if (StringUtils.isNotBlank(userName))
            {
                YdzyServicePortType portType = null;

                String orgNo = service.getUserDeptNo(userName, portType);
                if (StringUtils.isNotBlank(orgNo))
                {
                    data.put("deptNo", orgNo);
                    code = 1;
                    msg = "登录成功！";
                }
            }
            else
            {
                msg = "用户名不能为空！";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            code = 0;
            msg = "数据错误！";
        }
        // 设置返回值
        result.put("data", data);
        result.put("code", code);
        result.put("msg", msg);
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户登录", userName, "0");
        logService.insertLog(request, log);
        return result;
    }
}
