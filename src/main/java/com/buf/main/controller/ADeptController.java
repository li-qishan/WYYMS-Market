package com.buf.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.buf.common.utils.R;
import com.buf.main.entity.ADept;
import com.buf.main.entity.BDept;
import com.buf.main.service.ADeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/7/16.
 */
@RestController
@RequestMapping("/aDeptController")
public class ADeptController extends BaseController {

    @Autowired
    @Qualifier("ADeptService")
    private ADeptService service;

    @RequestMapping(value = "/getrow", method = RequestMethod.POST)
    public @ResponseBody
    Object getrow(String params,HttpServletRequest request) {
        List<ADept> list = new ArrayList<ADept>();
        ADept a = service.getrow();
        list.add(a);
        R r = new R();
        r.put("rows", list);
        return list;
    }

    @RequestMapping(value = "/getByNames", method = RequestMethod.POST)
    public R getByNames(String no, String name) {
        int num = service.getByNames(no, name);
        R r = new R();
        r.put("rows", num);
        return r;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    String add(BDept dept, HttpServletRequest request) {
        dept.setOrg_NO(dept.getDept_NO());
        int num = service.add(dept);
        syslogin("添加部门信息",request,"add");
        String s = "false";
        if (num > 0) {
            s = "success";
        }
        return s;
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    String update(BDept obj, HttpServletRequest request) {
        int num = service.update(obj);
        syslogin("修改部门信息",request,"update");
        if (num > 0) {
            return "success";
        }
        return "failed";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    R remove(String params,HttpServletRequest request) {
        JSONObject jsonParams = JSONObject.parseObject(params);
        int num = service.remove(jsonParams);
        syslogin("删除部门信息",request,"remove");
        R r = new R();
        if (num > 0) {
            r.put("rows", "success");
        }else {
            r.put("rows", "failed");
        }
        return r;
    }

}
