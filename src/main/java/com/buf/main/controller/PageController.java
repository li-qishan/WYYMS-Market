package com.buf.main.controller;

import com.buf.config.SysConfig;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.shiro.ShiroUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Liangzhu on 2018/5/23.
 */
@Controller
public class PageController extends BaseController {

    @GetMapping("/admin")
    public String page(Model model) {

        //控制基类使用日志
        model.addAttribute("title", "首页Dear");
        if (isLogin()) {
            return "admin/main.html";
        } else {
            return "admin/login.html";
        }
    }

    @RequestMapping(value = {"/admin/main"}, method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("name", "first!!!");
        model.addAttribute("userName", getUsername());
        return "admin/main.html";
    }
    @RequestMapping(value = {"/admin/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        return "admin/login.html";
    }

    @RequestMapping(value = "/admin/logout")
    public String logout(Model model){
        Subject subject = ShiroUtils.getSubject();
        subject.logout();
        return "admin/login.html";
    }

    @RequestMapping(value = {"/admin/sysindex"}, method = RequestMethod.GET)
    public String sysindex(Model model) {
        return "admin/sysindex.html";
    }

    @RequestMapping(value = {"/admin/default"}, method = RequestMethod.GET)
    public String default1(Model model) {
        return "admin/default.html";
    }

    @RequestMapping(value = {"/admin/top"}, method = RequestMethod.GET)
    public String top(Model model) {
        return "admin/top.html";
    }

    @RequestMapping(value = {"/admin/left"}, method = RequestMethod.GET)
    public String left(Model model) {
        return "admin/left.html";
    }

    @RequestMapping(value = {"/admin/right"}, method = RequestMethod.GET)
    public String right(Model model) {
        return "admin/right.html";
    }

}
