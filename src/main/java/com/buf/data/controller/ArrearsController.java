package com.buf.data.controller;

import com.buf.common.utils.LogUtils;
import com.buf.common.utils.StringUtils;
import com.buf.data.entity.SysLog;
import com.buf.data.service.ArrearsService;
import com.buf.data.service.SysLogUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/6/21.
 */
@RestController
@RequestMapping(value = "/arrears")
public class ArrearsController
{
    @Autowired
    private ArrearsService service;

    @Autowired
    private SysLogUserSearchService logService;


    @RequestMapping(value = "/getUserArrearsList")
    @ResponseBody
    public Map<String, Object> getUserArrearsList(HttpServletRequest request,HttpServletResponse response ,
                                                  @RequestParam(value = "userName", required = false) String userName,
                                                  @RequestParam(value = "deptNo", required = false) String deptNo,
                                                  @RequestParam(value = "type", required = false) String type,
                                                  @RequestParam(value = "param", required = false) String param,
                                                  @RequestParam(value = "month", required = false) String month,
                                                  @RequestParam(value = "pageNum", required = false) int pageNum,
                                                  @RequestParam(value = "pageSize", required = false) int pageSize)
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
            if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(param))
            {
                data = service.getUserArrearsList(deptNo, param, type,month, pageNum, pageSize,userName);
                code = 1;
                msg = "查询成功！";
            }
            else
            {
                msg = "查询参数不能为空！";
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
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户查询欠费列表", param, type);
        logService.insertLog(request, log);
        return result;
    }


    @RequestMapping(value = "/getUserArrearsDetail")
    @ResponseBody
    public Map<String, Object> getUserArrearsDetail(HttpServletRequest request,HttpServletResponse response ,
                                                    @RequestParam(value = "userName", required = false) String userName,
                                                    @RequestParam(value = "deptNo", required = false) String deptNo,
                                                    @RequestParam(value = "consNo", required = false) String consNo,
                                                    @RequestParam(value = "month", required = false) String month)
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
        try
        {
            if (StringUtils.isNotBlank(consNo) && StringUtils.isNotBlank(month))
            {
                data = service.getUserArrearsDetail(deptNo, consNo,month,userName);
                code = 1;
                msg = "查询成功！";
            }
            else
            {
                msg = "查询参数不能为空！";
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
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户欠费明细查询", consNo, "1");
        logService.insertLog(request, log);
        return result;
    }


    @RequestMapping(value = "/getCBUser")
    @ResponseBody
    public Map<String, Object> getCBUser(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value = "userName", required = false) String userName,
                                         @RequestParam(value = "inputName", required = false) String inputName,
                                         @RequestParam(value = "selectType", required = false) String selectType,
                                         @RequestParam(value = "deptNo", required = false) String deptNo) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<Map<String, Object>> data = new ArrayList<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try {
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(selectType)) {
                data = service.getCBUser(userName, selectType, inputName);
                code = 1;
                msg = "查询成功！";
            } else {
                msg = "查询参数不能为空！";
            }
        } catch (Exception e) {
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
        SysLog log = LogUtils.insertLog(userName, searchTime, "抄表员或催费员查询", "8552242376", "1");
        logService.insertLog(request, log);
        return result;
    }

}
