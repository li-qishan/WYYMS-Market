package com.buf.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.buf.common.annotation.SysLog;
import com.buf.common.exception.RRException;
import com.buf.common.utils.Constant;
import com.buf.common.utils.JsonMapper;
import com.buf.common.utils.R;
import com.buf.main.entity.SaOrg;
import com.buf.main.entity.SaOrgTree;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysMenuEntity;
import com.buf.sys.entity.SysValidate;
import com.buf.sys.service.SysMenuService;
import com.buf.sys.service.SysValidateService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
//import com.github.jlinqer.collections.List

import static java.util.stream.Collectors.toList;

/**
 * 数据权限管理
 *
 *
 *
 * @date andele 2018年11月15日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/val")
public class SysValidateController extends AbstractController {

    @Autowired
    private SysValidateService sysValidateService;

    /*获取角色使用权限*/
    @RequestMapping(value = "/validate")
    @ResponseBody
    public Object validate(HttpServletRequest request)
    {
        String result ="";
        try
        {
            SysValidate validate = new SysValidate();
            validate.setValidateAttribute(0);

            if (request.getParameter("menuId") != null)
            {
                Long menuTreeID = Long.valueOf(request.getParameter("menuId"));

                if (menuTreeID != 0)
                {
                    validate.setMenuId(menuTreeID);
                }
            }

            List<SysValidate> getValids = sysValidateService.getValidateByCondition(validate);
            if (getValids != null)
            {
                getValids = getValids.stream().sorted(Comparator.comparing(SysValidate::getValidateKey)).collect(toList());
            }

            JSONObject jo = new JSONObject();
            jo.put("total",getValids.size());
            jo.put("rows",getValids);
            return jo;
        }
        catch (Exception ex)
        {
            result = "获取角色使用权限失败！";
        }

        return result;
    }


}
