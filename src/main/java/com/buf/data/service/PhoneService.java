package com.buf.data.service;

import java.util.List;
import java.util.Map;
import com.buf.LoginWebService.YdzyServicePortType;
import com.buf.data.entity.UpdatePhoneList;

/**
 * Created by mawenguang on 2019/5/22.
 */
public interface PhoneService
{
    public List<Map<String, Object>> getUserList(String param, String type, String deptNo, String index, String userName);

    Map<String, Object> getUserDetail(String consNo, String index, String deptNo, String userName);

    Map<String, Object> userPhoneOpera(String consNo,String consName, String userName, String index, String operType, String isSign,String deptNo, String oldPhone, String phone,YdzyServicePortType portType );

    Map<String, Object> userPhoneBatchOpera(String consNo,String consName, String userName, String operType,String deptNo, String oldPhone, String phone,YdzyServicePortType portType );

    List<UpdatePhoneList> getUpdateList(String userName);

    List<Map<String, Object>> getBatchOperaParamList(String consNo, String consName, String userName, String operType, String deptNo, String oldPhone, String phone);
}
