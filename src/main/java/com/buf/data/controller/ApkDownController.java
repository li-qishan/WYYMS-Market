package com.buf.data.controller;

import com.buf.common.utils.LogUtils;
import com.buf.common.utils.StringUtils;
import com.buf.common.utils.UploadUtils;
import com.buf.data.entity.ApkVersion;
import com.buf.data.entity.SysLog;
import com.buf.data.service.APKDownService;
import com.buf.data.service.SysLogUserSearchService;
import com.buf.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author mawenguang
 * @Description //TODO APK上传下载
 * @Date 13:49 2019/5/27
 * @Param
 * @return
 **/
@Controller
@RequestMapping(value = "/apkDown")
public class ApkDownController
{

    @Autowired
    private APKDownService service;

    @Autowired
    private SysLogUserSearchService logService;

    /**
     * @return java.util.List<com.buf.data.entity.ApkVersion>
     * @Author mawenguang
     * @Description //TODO 查询
     * @Date 15:18 2019/5/27
     * @Param []
     **/
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ApkVersion> getList()
    {
        return service.selectAll();
    }

    /**
     * @return int
     * @Author mawenguang
     * @Description //TODO 根据apkId删除
     * @Date 15:19 2019/5/27
     * @Param [apkId]
     **/
    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public int deleteById(String apkId)
    {
        return service.deleteByID(apkId);
    }


    /**
     * @return java.lang.String
     * @Author mawenguang
     * @Description //TODO 新增及上传
     * @Date 15:19 2019/5/27
     * @Param [request, response]
     **/
    @ResponseBody
    @RequestMapping(value = "/uploadFiles")
    public String uploadFiles(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String result = "";

        if (request != null)
        {
            // 上传文件，获取上传文件信息
            List<Map<String, Object>> list = UploadUtils.uploadFile(request, UploadUtils.uploadUrl() + "apk/");
            if (list != null && list.size() > 0)
            {

                ApkVersion apkVersion = new ApkVersion();

                String apkId = request.getParameter("apkId");

                String versionCode = request.getParameter("versionCode");

                // 拼接访问路径
                String path = request.getContextPath();

                Properties props = System.getProperties();
                String basEPath = request.getScheme() + "://" + request.getServerName();
                // windows 系统增加端口号
//                if (props.getProperty("os.name").contains("Windows"))
//                {
                basEPath += ":" + request.getServerPort();
//                }
                //
                basEPath += path + "/";

//              String basEPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
                // 拼接文件访问路径
                apkVersion.setApkUrl(basEPath + "upload" + "/apk/" + list.get(0).get("fileName").toString());
                apkVersion.setApkName(list.get(0).get("fileName").toString());//文件名
                apkVersion.setApkId(apkId);// apkid
                apkVersion.setVersionCode(versionCode);// 版本号
                apkVersion.setUploadBy(ShiroUtils.getUserEntity().getRealName());// 上传人
                apkVersion.setUploadTime(new Date());// 上传时间
                apkVersion.setIsDelete("0");// 新版本上传需删除旧版本

                List<ApkVersion> listApk = service.selectByApkId(apkId, versionCode);
                if (listApk != null && listApk.size() > 0)
                {
                    return "sizeFalse";
                }

                if (service.insert(apkVersion) > 0)
                {
                    result = "success";
                }
            }
        }
        else
        {
            result = "上传失败！";
        }
        return result;
    }


    /**
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 获取版本号
     * @Date 15:56 2019/5/27
     * @Param [apkId]
     **/
    @ResponseBody
    @RequestMapping(value = "/getVersionCodeByApkId")
    public Map<String, Object> getVersionCodeByApkId(HttpServletRequest request,
                                                     @RequestParam(value = "apkId", required = false) String apkId,
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
            if (StringUtils.isNotBlank(apkId))
            {
                String versionCode = service.getVersionByApkId(apkId);
                List<ApkVersion> list = service.selectOneByApkId(apkId);
                ApkVersion apkVersion = new ApkVersion();
                if (list != null && list.size() > 0)
                {
                    apkVersion = list.get(0);
                }
                data.put("versionCode", versionCode);
                data.put("apk", apkVersion);
                code = 1;
                msg = "数据获取成功！";
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
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        // 插入log
        SysLog log = LogUtils.insertLog(userName, searchTime, "版本号检验", apkId, "9");
        logService.insertLog(request, log);
        return result;
    }


    /**
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @Author mawenguang
     * @Description //TODO 获取下载路径
     * @Date 15:56 2019/5/27
     * @Param [apkId]
     **/
    @ResponseBody
    @RequestMapping(value = "/getUrlByApkId")
    public Map<String, Object> getUrlByApkId(HttpServletRequest request,
                                             @RequestParam(value = "apkId", required = false) String apkId,
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
            if (StringUtils.isNotBlank(apkId))
            {
                String url = service.getUrlByApkId(apkId);
                data.put("url", url);
                code = 1;
                msg = "登录成功！";
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
        long endTime = System.currentTimeMillis(); //获取结束时间
        String searchTime = (endTime - startTime) + "ms";
        // 插入log
        SysLog log = LogUtils.insertLog(userName, searchTime, "获取下载路径", apkId, "0");
        logService.insertLog(request, log);
        return result;
    }
}
