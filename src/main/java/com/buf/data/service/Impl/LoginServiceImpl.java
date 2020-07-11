package com.buf.data.service.Impl;

import com.buf.LoginWebService.YdzyServicePortType;
import com.buf.common.utils.JSONFormMapUtils;
import com.buf.common.utils.ServiceUtils;
import com.buf.common.utils.XmlToJsonUtils;
import com.buf.data.dao.loginDao;
import com.buf.data.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/22.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private loginDao dao;

    @Override
    public Map<String, Object> getUserByName(String userName, String password, YdzyServicePortType portType) {
        // 返回参数
        boolean bool = false;
        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("serviceCode", "ydzyLogon");
        paramMap.put("source", "04");
        paramMap.put("target", "21102");
        paramMap.put("phoneImei", "861097031222296");
        paramMap.put("userNo", userName);
        paramMap.put("password", password);
//        paramMap = setParamMap(paramMap);
        String str = ServiceUtils.appendXmlParam(paramMap);
        // 获取webService数据
        String result = portType.sgpmsGetData(str);
        // 将xml格式结果集变成json格式
        result = XmlToJsonUtils.xml2json(result);
        // 将数据解析成map
        Map<String, Object> resultMap = JSONFormMapUtils.jsonToMap(result);
        //      Map<String, Object> user = dao.getUserByName(userName);

        return resultMap;
    }

    @Override
    public String getUserDeptNo(String userName, YdzyServicePortType portType) {

//        String orgNo = "";
//
//        Map<String, Object> paramMap = new LinkedHashMap<>();
//        paramMap.put("serviceCode", "ydzyGetUserInfo");
//        paramMap.put("source", "04");
//        paramMap.put("target", "21102");
//        paramMap.put("phoneImei", "861097031222296");
//        paramMap.put("userNo", userName);
////        paramMap = setParamMap(paramMap);
//        // 获取webService数据
//        String result = portType.sgpmsGetData(ServiceUtils.appendXmlParam(paramMap));
//        // 将xml格式结果集变成json格式
//        result = XmlToJsonUtils.xml2json(result);
//        // 将数据解析成map
//        Map<String, Object> resultMap = JSONFormMapUtils.jsonToMap(result);
//        //获取单位编号
//        if (resultMap != null && !resultMap.get("result").equals("0")&&resultMap.containsKey("orgNo")) {
//            orgNo = resultMap.get("orgNo").toString();
//        }
        return dao.getUserDeptNo(userName);
    }

    private Map<String, Object> setParamMap(Map<String, Object> map) {
        map.put("source", "04");
        map.put("target", "21102");
        map.put("phoneImei", "861097031222296");
        return map;
    }

}


//        paramMap.put("userNo", "sy_neusoft");
//                paramMap.put("orgNo", "21401");
//                paramMap.put("serviceCode", "ydzyLogon");
//                paramMap.put("source", "04");
//                paramMap.put("target", "");
//                paramMap.put("phoneImei", "860820030105837");
//                paramMap.put("ydzyUserNo", userName);
//                paramMap.put("ydzyPwd", password);
