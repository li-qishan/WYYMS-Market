package com.buf.data.controller;

import com.buf.LoginWebService.YdzyServicePortType;
import com.buf.common.utils.LogUtils;
import com.buf.common.utils.ServiceUtils;
import com.buf.common.utils.StringUtils;
import com.buf.data.entity.SysLog;
import com.buf.data.entity.UpdatePhoneList;
import com.buf.data.service.PhoneService;
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
 * @Author mawenguang
 * @Description //TODO 用户手机号变更Controller
 * @Date 11:29 2019/6/12
 * @Param
 * @return
 **/
@Controller
@RequestMapping(value = "/phone")
public class PhoneController
{
    @Autowired
    private PhoneService service;

    @Autowired
    private SysLogUserSearchService logService;

    /**
     * @return java.util.Map<java.lang.String   ,   java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 用户手机号查询列表
     * @Date 11:29 2019/6/12
     * @Param [request, userName, type, param, deptNo]
     **/
    @RequestMapping(value = "/getUserList")
    @ResponseBody
    public Map<String, Object> getUserList(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "userName", required = false) String userName,
                                           @RequestParam(value = "type", required = false) String type,
                                           @RequestParam(value = "param", required = false) String param,
                                           @RequestParam(value = "deptNo", required = false) String deptNo,
                                           @RequestParam(value = "index", required = false) String index)
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
            if (StringUtils.isNotBlank(param) && StringUtils.isNotBlank(type))
            {
                data = service.getUserList(param, type, deptNo, index,userName);
                code = 1;
                msg = "获取成功！";
            }
            else
            {
                msg = "查询条件不能为空！";
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
        // 插入日志
        long endTime = System.currentTimeMillis(); //获取结束时间
        // 计算查询时长
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户手机号列表查询", param, type);
        logService.insertLog(request, log);
        return result;
    }

    /**
     * @return java.util.Map<java.lang.String   ,   java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 用户明细查询
     * @Date 11:29 2019/6/12
     * @Param [request, userName, consno, index, deptNo]
     **/
    @RequestMapping(value = "/getUserDetail")
    @ResponseBody
    public Map<String, Object> getUserDetail(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "userName", required = false) String userName,
                                             @RequestParam(value = "consNo", required = false) String consno,
                                             @RequestParam(value = "index", required = false) String index,
                                             @RequestParam(value = "deptNo", required = false) String deptNo)
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
            if (StringUtils.isNotBlank(consno) && StringUtils.isNotBlank(index))
            {
                data = service.getUserDetail(consno, index, deptNo,userName);
                code = 1;
                msg = "获取成功！";
            }
            else
            {
                msg = "查询条件不能为空！";
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
        // 插入日志
        long endTime = System.currentTimeMillis(); //获取结束时间
        // 计算查询时长
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户手机号明细查询，", consno, "1");
        logService.insertLog(request, log);
        return result;
    }

    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 手机号操作
     * @Date 11:35 2019/6/12
     * @Param [request, userName, consno, index, deptNo]userPhoneBatchOpera
     **/
    @RequestMapping(value = "/userPhoneOpera")
    @ResponseBody
    public Map<String, Object> userPhoneAdd(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(value = "userName") String userName,// 操作用户名
                                            @RequestParam(value = "consNo") String consNo,// 用户号
                                            @RequestParam(value = "consName") String consName,// 用户名
                                            @RequestParam(value = "index") String index,// 电话类型
                                            @RequestParam(value = "isSign") String isSign,// 是否为催费
                                            @RequestParam(value = "operType") String operType,// 操作标识
                                            @RequestParam(value = "deptNo") String deptNo,// 单位编号
                                            @RequestParam(value = "oldPhone") String oldPhone,// 旧电话（增加删除时，为####）
                                            @RequestParam(value = "phone") String phone)// 新手机号
    {
        // 新电话（增加，删除都传新电话）
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
            // 获取webService接口服务
            YdzyServicePortType portType = ServiceUtils.getPort();

            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(consNo) && StringUtils.isNotBlank(consName) &&
                    StringUtils.isNotBlank(index) && StringUtils.isNotBlank(isSign) && StringUtils.isNotBlank(operType) )
            {
                Map<String, Object> results = service.userPhoneOpera(consNo, consName, userName, index, operType, isSign, deptNo, oldPhone, phone, portType);
                if (!results.isEmpty() && results.get("returnFlag").equals("1"))
                {
                    code = 1;
                    msg = "操作成功！";
                }
                else
                {
                    msg = "操作失败！";
                }
            }
            else
            {
                msg = "查询条件不能为空！";
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
        // 插入日志
        long endTime = System.currentTimeMillis(); //获取结束时间
        // 计算查询时长
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户手机号" + ServiceUtils.getOperTypeCh(operType)
                + "，contactMode类型为" + ServiceUtils.getContactModeCh(index), consNo, "1");
        logService.insertLog(request, log);
        return result;
    }

    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 手机号批量操作
     * @Date 11:35 2019/6/12
     * @Param [request, userName, consno, index, deptNo]
     **/
    @RequestMapping(value = "/userPhoneBatchOpera")
    @ResponseBody
    public Map<String, Object> userPhoneBatchOpera(HttpServletRequest request, HttpServletResponse response,
                                                   @RequestParam(value = "userName") String userName,// 操作用户名
                                                   @RequestParam(value = "consNo") String consNo,// 用户号
                                                   @RequestParam(value = "consName") String consName,// 用户名
                                                   @RequestParam(value = "operType") String operType,// 操作标识
                                                   @RequestParam(value = "deptNo") String deptNo,// 单位编号
                                                   @RequestParam(value = "oldPhone") String oldPhone,// 旧电话（增加删除时，为####）
                                                   @RequestParam(value = "phone") String phone)// 新手机号
    {
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Method", "POST,GET");//允许访问的方式
        // 新电话（增加，删除都传新电话）
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
            // 获取webService接口服务
            YdzyServicePortType portType = ServiceUtils.getPort();

            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(consNo) && StringUtils.isNotBlank(consName) &&
                    StringUtils.isNotBlank(operType) )
            {
                Map<String, Object> results = service.userPhoneBatchOpera(consNo, consName, userName, operType, deptNo, oldPhone, phone, portType);
                if (!results.isEmpty() && results.get("returnFlag").equals("1"))
                {
                    code = 1;
                    msg = "操作成功！";
                }
                else if (!results.isEmpty() && results.get("returnFlag").equals("0"))
                {
                    code = 1;
                    msg = "暂无可操作联系人数据";
                }
                else
                {
                    msg = "操作失败！";
                }
            }
            else
            {
                msg = "查询条件不能为空！";
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
        // 插入日志
        long endTime = System.currentTimeMillis(); //获取结束时间
        // 计算查询时长
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户手机号批量" + ServiceUtils.getOperTypeCh(operType)
                + "，contactMode类型为账务联系人和电器联系人", consNo, "1");
        logService.insertLog(request, log);
        return result;
    }


    /**
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 获取已更新列表
     * @Date 15:08 2019/6/25
     * @Param []
     **/
    @ResponseBody
    @RequestMapping("/getUpdatePhoneList")
    public Map<String, Object> getUpdatePhoneList(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(value = "userName") String userName)// 操作用户名
    {
        long startTime = System.currentTimeMillis();   //获取开始时间
        // 返回集合
        Map<String, Object> result = new HashMap<>();
        // 数据列表
        List<UpdatePhoneList> data = new ArrayList<>();
        // 返回信息
        String msg = "";
        // 返回数据标识码
        int code = -1;
        try
        {
            if (StringUtils.isNotBlank(userName))
            {
                data = service.getUpdateList(userName);
                code = 1;
                msg = "修改成功！";
            }
            else
            {
                msg = "查询条件不能为空！";
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
        // 插入日志
        long endTime = System.currentTimeMillis(); //获取结束时间
        // 计算查询时长
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "获取已更新列表", userName, "0");
        logService.insertLog(request, log);
        return result;
    }


    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 修改手机号
     * @Date 11:35 2019/6/12
     * @Param [request, userName, consno, index, deptNo]
     **/
    @RequestMapping(value = "/userPhoneUpdate")
    @ResponseBody
    public Map<String, Object> userPhoneUpdate(HttpServletRequest request,
                                               @RequestParam(value = "userName", required = false) String userName,
                                               @RequestParam(value = "consNo", required = false) String consno,
                                               @RequestParam(value = "index", required = false) String index,
                                               @RequestParam(value = "deptNo", required = false) String deptNo)
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
            if (StringUtils.isNotBlank(consno) || StringUtils.isNotBlank(index))
            {
//                data = service.getUserDetail(consno, index, deptNo);
                code = 1;
                msg = "修改成功！";
            }
            else
            {
                msg = "查询条件不能为空！";
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
        // 插入日志
        long endTime = System.currentTimeMillis(); //获取结束时间
        // 计算查询时长
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户手机号修改，类型为" + index, consno, "1");
        logService.insertLog(request, log);
        return result;
    }

    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 删除手机号
     * @Date 11:35 2019/6/12
     * @Param [request, userName, consno, index, deptNo]
     **/
    @RequestMapping(value = "/userPhoneDelete")
    @ResponseBody
    public Map<String, Object> userPhoneDelete(HttpServletRequest request,
                                               @RequestParam(value = "userName", required = false) String userName,
                                               @RequestParam(value = "consNo", required = false) String consno,
                                               @RequestParam(value = "index", required = false) String index,
                                               @RequestParam(value = "deptNo", required = false) String deptNo)
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
            if (StringUtils.isNotBlank(consno) || StringUtils.isNotBlank(index))
            {
//                data = service.getUserDetail(consno, index, deptNo);
                code = 1;
                msg = "手机号删除成功！";
            }
            else
            {
                msg = "查询条件不能为空！";
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
        // 插入日志
        long endTime = System.currentTimeMillis(); //获取结束时间
        // 计算查询时长
        String searchTime = (endTime - startTime) + "ms";
        SysLog log = LogUtils.insertLog(userName, searchTime, "用户手机号删除，类型为" + index, consno, "1");
        logService.insertLog(request, log);
        return result;
    }

//    /**
//     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
//     * @Author mawenguang
//     * @Description //TODO 测试用接口
//     * @Date 11:35 2019/6/12
//     * @Param [request, userName, consno, index, deptNo]
//     **/
//    @RequestMapping(value = "/userPhoneBatchOperaTest")
//    @ResponseBody
//    public Map<String, Object> userPhoneBatchOperaTest(HttpServletRequest request,
//                                                       @RequestParam(value = "userName") String userName,// 操作用户名
//                                                       @RequestParam(value = "consNo") String consNo,// 用户号
//                                                       @RequestParam(value = "consName") String consName,// 用户名
//                                                       @RequestParam(value = "operType") String operType,// 操作标识
//                                                       @RequestParam(value = "deptNo") String deptNo,// 单位编号
//                                                       @RequestParam(value = "oldPhone") String oldPhone,// 旧电话（增加删除时，为####）
//                                                       @RequestParam(value = "phone") String phone)// 新手机号
//    {
//        // 新电话（增加，删除都传新电话）
//        long startTime = System.currentTimeMillis();   //获取开始时间
//        // 返回集合
//        Map<String, Object> result = new HashMap<>();
//        // 数据列表
//        String data = null;
//        // 返回信息
//        String msg = "";
//        // 返回数据标识码
//        int code = -1;
//        try
//        {
//            data = ServiceUtils.appendPhoneXmlParam(service.getBatchOperaParamList(consNo, consName, userName, operType, deptNo, oldPhone, phone));
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            code = 0;
//            msg = "数据错误！";
//        }
//        // 设置返回值
//        result.put("data", data);
//        result.put("code", code);
//        result.put("msg", msg);
//        return result;
//    }

    @RequestMapping(value = {"/gotoPhoneDetail"}, method = RequestMethod.GET)
    public String gotoUserList(Model model, HttpServletRequest request)
    {
        return "/app/YXPhoneChange/renew.html";
    }

    @RequestMapping(value = {"/gotoPhoneAddMobile"}, method = RequestMethod.GET)
    public String gotoPhoneAddMobile(Model model, HttpServletRequest request)
    {
        return "/app/YXPhoneChange/addMobile.html";
    }

    @RequestMapping(value = {"/gotoPhoneUpdateMobile"}, method = RequestMethod.GET)
    public String gotoPhoneUpdateMobile(Model model, HttpServletRequest request)
    {
        return "/app/YXPhoneChange/updateMobile.html";
    }

}
