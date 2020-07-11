package com.buf.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.buf.common.utils.R;
import com.buf.common.utils.UUIDUtils;
import com.buf.main.entity.SysCode;
import com.buf.main.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/parameterController")
public class ParameterController  extends BaseController {

    @Autowired
    @Qualifier("ParameterService")
    private ParameterService service;

    @RequestMapping(value = "/getroot", method = RequestMethod.POST)
    public R getroot(String params) {

        List<Map<String,String>> list = service.getroot(params);
        R r = new R();
        r.put("rows", list);
        return r;
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public R remove(String params, HttpServletRequest request) {
        syslogin("指标参数删除",request,"remove");
        int num = service.remove(params);
        R r = new R();
        if (num > 0) {
            r.put("rows", "success");
        }else {
            r.put("rows", "failed");
        }
        return r;
    }

    @RequestMapping(value = "/getByCode", method = RequestMethod.POST)
    public R getByCode(String orderNum,String code, String codetype ) {

        int num = service.getByCode(orderNum, code ,codetype);
        R r = new R();
        r.put("rows", num);
        return r;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    String add(SysCode obj, HttpServletRequest request, String aaaaa) {
        obj.setCODE_ID(UUIDUtils.uuid());
        obj.setCODE_TYPE(aaaaa);
        int num = service.add(obj);
        syslogin("指标参数增加",request,"add");
        String s = "false";
        if (num > 0){
           s = "success";
        }

        return s;
    }
}
