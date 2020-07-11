package com.buf.data.controller;

import com.buf.common.config.Global;
import com.buf.common.utils.LogUtils;
import com.buf.data.entity.SysLog;
import com.buf.data.service.PhoneService;
import com.buf.data.service.SysLogUserSearchService;
import com.buf.data.service.UserInfoService;
import com.buf.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController
{

    @Autowired
    private UserInfoService service;

    @Autowired
    private SysLogUserSearchService logService;

    @RequestMapping(value = "/searchData")
    @ResponseBody
    public Map<String, Object> searchData(HttpServletRequest request,HttpServletResponse response,
                                                @RequestParam(value = "param", required = false) String param,
                                                @RequestParam(value = "type", required = false) String type,
                                                @RequestParam(value = "deptNo", required = false) String deptNo,
                                                @RequestParam(value = "userName", required = false) String userName)
    {

        long startTime=System.currentTimeMillis();   //获取开始时间
        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<Map<String, Object>> data = new ArrayList<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;

        String operateContext = "用户档案列表查询";
        try
        {
            if (StringUtils.isNotBlank(param) && StringUtils.isNotBlank(type) &&StringUtils.isNotBlank(deptNo))
            {
                if (StringUtils.isBlank(param))
                {
                    msg = "查询参数不能为空！";
                }
                else if (StringUtils.isBlank(type))
                {
                    msg = "查询类型不能为空！";
                }
                else
                {

                    data = service.getUserList(param,type,deptNo,userName);
                    code = 1;
                    msg = "获取数据成功！";
                }
            }
            else
            {
                msg = "参数获取失败！";
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
        long endTime=System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime-startTime)+"ms";
        // 插入log
        SysLog log = LogUtils.insertLog(userName,searchTime,operateContext,param,type);
        logService.insertLog(request,log);
        return result;
    }


    @RequestMapping(value = "/getData")
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request,HttpServletResponse response,
                                                @RequestParam(value = "param", required = false) String param,
                                                @RequestParam(value = "type", required = false) String type,
                                                @RequestParam(value = "index", required = false) String index,
                                                @RequestParam(value = "deptNo", required = false) String deptNo,
                                                @RequestParam(value = "userName", required = false) String userName,
                                                @RequestParam(value = "dataGetNumber", required = false) String dataGetNumber)
    {

        long startTime=System.currentTimeMillis();   //获取开始时间
        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        Map<String, Object> data = new HashMap<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;

        String operateContext = "";
        try
        {
            if (StringUtils.isNotBlank(param) && StringUtils.isNotBlank(type) &&StringUtils.isNotBlank(deptNo))
            {
//                // 查询参数
//                String param = map.get("param").toString();
//                // 查询类型
//                String type = map.get("type").toString();

                if (StringUtils.isBlank(param))
                {
                    msg = "查询参数不能为空！";
                }
                else if (StringUtils.isBlank(type))
                {
                    msg = "查询类型不能为空！";
                }
                else
                {
                    if(index.equals("userInfo")){// 用户数据
                        data = service.getUserInfoDataList(param,type,deptNo,dataGetNumber,userName);
                        operateContext = "用户数据查询";
                    } else if(index.equals("electric")){// 电费电量
                        data = service.getElectricDataList(param,type,deptNo,dataGetNumber,userName);
                        operateContext = "电费电量查询";
                    } else if(index.equals("payment")){// 缴费记录
                        data = service.getPaymentDataList(param,type,deptNo,dataGetNumber,userName);
                        operateContext = "缴费记录查询";
                    }
                    code = 1;
                    msg = "获取数据成功！";
                }
            }
            else
            {
                msg = "参数获取失败！";
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
        long endTime=System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime-startTime)+"ms";
        // 插入log
        SysLog log = LogUtils.insertLog(userName,searchTime,operateContext,param,type);
        logService.insertLog(request,log);
        return result;
    }

    @RequestMapping(value = {"/gotoUserInfo"}, method = RequestMethod.GET)
    public String gotoUserInfo(Model model, HttpServletRequest request) {
        return "/app/YxUserInfo/userInfo.html";
    }

    @RequestMapping(value = {"/gotoUserList"}, method = RequestMethod.GET)
    public String gotoUserList(Model model, HttpServletRequest request) {
        return "/app/YxUserInfo/userSearch.html";
    }

    @RequestMapping(value = {"/gotoPrcType"}, method = RequestMethod.GET)
    public String gotoPrcType(Model model, HttpServletRequest request) {
        return "/app/YxUserInfo/prcType.html";
    }

}
