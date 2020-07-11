package com.buf.data.controller;

import com.buf.common.utils.LogUtils;
import com.buf.common.utils.StringUtils;
import com.buf.data.entity.MP;
import com.buf.data.entity.SysLog;
import com.buf.data.service.SpPrcService;
import com.buf.data.service.SysLogUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/7/23.
 */
@Controller
@RequestMapping(value = "/spPrc")
public class SpPrcController
{

    @Autowired
    private SpPrcService spPrcService;
    @Autowired
    private SysLogUserSearchService logService;

    @RequestMapping(value = "/getSpPrcData")
    @ResponseBody
    public Map<String, Object> getSpPrcData(HttpServletRequest request,HttpServletResponse response,
                                            @RequestParam(value = "consNo", required = false) String consNo,
                                            @RequestParam(value = "userName", required = false) String userName)
    {
        long startTime = System.currentTimeMillis();   //获取开始时间
        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<Map<String, Object>> data = new ArrayList<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;

        // 日志文字描述
        String operateContext = "用户受电点查询";
        try
        {
            if (StringUtils.isNotBlank(consNo))
            {

                if (StringUtils.isBlank(consNo))
                {
                    msg = "查询参数不能为空！";
                }
                else
                {
                    data = spPrcService.getSpPrcList(consNo);
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
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        // 插入log
        SysLog log = LogUtils.insertLog(userName, searchTime, operateContext, consNo, "1");
        logService.insertLog(request, log);
        return result;
    }

    @RequestMapping(value = "/getMpData")
    @ResponseBody
    public Map<String, Object> getMpData(HttpServletRequest request,HttpServletResponse response,
                                         @RequestParam(value = "consNo", required = false) String consNo,
                                         @RequestParam(value = "prcCode", required = false) String prcCode,
                                         @RequestParam(value = "spId", required = false) String spId,
                                         @RequestParam(value = "userName", required = false) String userName)
    {
        long startTime = System.currentTimeMillis();   //获取开始时间
        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<MP> data = new ArrayList<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;

        // 日志文字描述
        String operateContext = "用户受电点查询";
        try
        {
            if (StringUtils.isNotBlank(consNo))
            {

                if (StringUtils.isBlank(consNo))
                {
                    msg = "查询参数不能为空！";
                }
                else
                {
                    data = spPrcService.getMpList(consNo,prcCode,spId);
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
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        // 插入log
        SysLog log = LogUtils.insertLog(userName, searchTime, operateContext, consNo, "1");
        logService.insertLog(request, log);
        return result;
    }

    @RequestMapping(value = "/getMeterData")
    @ResponseBody
    public Map<String, Object> getMeterData(HttpServletRequest request,HttpServletResponse response,
                                         @RequestParam(value = "consNo", required = false) String consNo,
                                         @RequestParam(value = "mpId", required = false) String mpId,
                                         @RequestParam(value = "userName", required = false) String userName)
    {
        long startTime = System.currentTimeMillis();   //获取开始时间
        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<Map<String, Object>> data = new ArrayList<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;

        // 日志文字描述
        String operateContext = "用户受电点查询";
        try
        {
            if (StringUtils.isNotBlank(consNo))
            {

                if (StringUtils.isBlank(consNo))
                {
                    msg = "查询参数不能为空！";
                }
                else
                {
                    data = spPrcService.getMeterAndReadList(consNo,mpId);
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
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        // 插入log
        SysLog log = LogUtils.insertLog(userName, searchTime, operateContext, consNo, "1");
        logService.insertLog(request, log);
        return result;
    }

    @RequestMapping(value = {"/gotoMpDetail"}, method = RequestMethod.GET)
    public String gotoMpDetail(Model model, HttpServletRequest request) {
        return "/app/YxUserInfo/mpDetail.html";
    }

    @RequestMapping(value = {"/gotoPrcDetail"}, method = RequestMethod.GET)
    public String gotoUserInfo(Model model, HttpServletRequest request) {
        return "/app/YxUserInfo/mpDetail.html";
    }


}
