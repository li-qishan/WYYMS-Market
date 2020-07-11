package com.buf.data.controller;

import com.buf.common.utils.LogUtils;
import com.buf.common.utils.StringUtils;
import com.buf.data.entity.SysLog;
import com.buf.data.service.BusinessProcessService;
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
 * 业扩流程查询
 *
 * @author liqishan
 * @create 2019-06-21 15:10
 *
 */
@Controller
@RequestMapping(value = "/businessProcess")
public class BusinessProcessController {

    @Autowired
    BusinessProcessService businessProcessService;

    @Autowired
    private SysLogUserSearchService logService;

    @RequestMapping(value = "/getBusinessProcessByOrderNum")
    @ResponseBody
    public Map<String, Object> getBusinessProcessByOrderNum(HttpServletRequest request,HttpServletResponse response ,
                                                            @RequestParam(value = "type", required = false) String type,
                                                            @RequestParam(value = "userName", required = false) String userName,
                                                            @RequestParam(value = "orderNum", required = false) String orderNum,
                                                            @RequestParam(value = "consNo", required = false) String consNo,
                                                            @RequestParam(value = "deptNo", required = false) String deptNo,
                                                            @RequestParam(value = "selectType", required = false) String selectType,
                                                            @RequestParam(value = "searchType", required = false) String searchType)
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
        String operateContext = "";
        try
        {
            if (StringUtils.isNotBlank(orderNum) && StringUtils.isNotBlank(searchType))
            {

                if (StringUtils.isBlank(orderNum))
                {
                    msg = "查询参数不能为空！";
                }
                else
                {

                 // 用户数据
                    data = businessProcessService.getBusinessProcessByOrderNum(orderNum,searchType,selectType,deptNo,userName);
                    operateContext = "业扩工单数据查询";
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
        SysLog log = LogUtils.insertLog(userName,searchTime,operateContext,orderNum,selectType);
        logService.insertLog(request,log);

        return result;
    }

    @RequestMapping(value = "/getMoreBusinessProcessByOrderNum")
    @ResponseBody
    public Map<String, Object> getMOreBusinessProcessByOrderNum(HttpServletRequest request,HttpServletResponse response ,
                                                            @RequestParam(value = "userName", required = false) String userName,
                                                            @RequestParam(value = "orderNum", required = false) String orderNum,
                                                            @RequestParam(value = "deptNo", required = false) String deptNo,
                                                            @RequestParam(value = "selectType", required = false) String selectType,
                                                            @RequestParam(value = "searchType", required = false) String searchType)
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
        String operateContext = "";
        try
        {
            if (StringUtils.isNotBlank(orderNum) && StringUtils.isNotBlank(searchType))
            {

                if (StringUtils.isBlank(orderNum))
                {
                    msg = "查询参数不能为空！";
                }
                else
                {
                    // 用户数据
                    data = businessProcessService.getMoreBusinessProcessByOrderNum(orderNum,searchType,selectType,deptNo,userName);
                    operateContext = "业扩工单数据查询";
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
        SysLog log = LogUtils.insertLog(userName,searchTime,operateContext,orderNum,searchType);
        logService.insertLog(request,log);

        return result;
    }

    @RequestMapping(value = {"/gotoBusinessProcessDetail"}, method = RequestMethod.GET)
    public String gotoBusinessProcessDetail(Model model, HttpServletRequest request) {
        return "/app/YXBusinessProcess/businessDetail.html";

    }


}
