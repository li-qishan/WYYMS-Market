package com.buf.data.controller;

import com.buf.common.utils.LogUtils;
import com.buf.common.utils.StringUtils;
import com.buf.data.entity.*;
import com.buf.data.service.AddressService;
import com.buf.data.service.SysLogUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址变更
 */
@Controller
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    private SysLogUserSearchService logService;
    @Autowired
    private AddressService service;

    @RequestMapping(value = "/getUserAddressDetail")
    @ResponseBody
    public Map<String, Object> getUserAddressDetail(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestParam(value = "userName", required = false) String userName,
                                                    @RequestParam(value = "consNo", required = false) String consNo) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<AddressEntiry> data = new ArrayList<AddressEntiry>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try {
            if (StringUtils.isNotBlank(consNo)) {
                data = service.getUserAddressDetail(consNo, userName);
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
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户地址明细查询", consNo, "1");
        logService.insertLog(request, log);
        return result;
    }


    @RequestMapping(value = "/getUserCountryArea")
    @ResponseBody
    public Map<String, Object> getUserCountryArea(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(value = "userName", required = false) String userName,
                                                  @RequestParam(value = "consNo", required = false) String consNo) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<CountryAreaEntiry> data = new ArrayList<CountryAreaEntiry>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try {
            if (StringUtils.isNotBlank(userName)) {
                data = service.getUserCountryArea(userName);
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
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户地址明细查询", consNo, "1");
        logService.insertLog(request, log);
        return result;
    }

    @RequestMapping(value = "/getUserStreetAndRoad")
    @ResponseBody
    public Map<String, Object> getUserStreetAndRoad(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestParam(value = "countyCode", required = false) String countyCode,
                                                    @RequestParam(value = "userName", required = false) String userName,
                                                    @RequestParam(value = "consNo", required = false) String consNo) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<StreetAndRoadEntiry> street = new ArrayList<StreetAndRoadEntiry>();
        List<StreetAndRoadEntiry> road = new ArrayList<StreetAndRoadEntiry>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try {
            if (StringUtils.isNotBlank(countyCode)) {
                street = service.getUserStreet(countyCode);
                road = service.getUserRoad(countyCode);
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
        result.put("street", street);
        result.put("road", road);
        result.put("code", code);
        result.put("msg", msg);
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户区县道路街道查询", consNo, "1");
        logService.insertLog(request, log);
        return result;
    }


    @RequestMapping(value = "/getUserCommunityAndSmall")
    @ResponseBody
    public Map<String, Object> getUserCommunityAndSmall(HttpServletRequest request, HttpServletResponse response,
                                                        @RequestParam(value = "countyCode", required = false) String countyCode,
                                                        @RequestParam(value = "pCode", required = false) String pCode,
                                                        @RequestParam(value = "userName", required = false) String userName,
                                                        @RequestParam(value = "type", required = false) String type,
                                                        @RequestParam(value = "consNo", required = false) String consNo) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<CommunityAndSmallEntiry> data = new ArrayList<CommunityAndSmallEntiry>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try {
            if (StringUtils.isNotBlank(countyCode) || StringUtils.isNotBlank(pCode)) {
                data = service.getUserCommunityAndSmall(countyCode, type, pCode);
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
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户区县道路街道查询", consNo, "1");
        logService.insertLog(request, log);
        return result;
    }

}
