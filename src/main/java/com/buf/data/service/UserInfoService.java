package com.buf.data.service;

import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
public interface UserInfoService
{
    List<Map<String,Object>> getUserList(String param, String type, String deptNo,String userName);

    public Map<String,Object> getUserInfoDataList(String param,String type,String deptNo,String dataGetNumber,String userName);

    public Map<String,Object> getElectricDataList(String param,String type,String deptNo,String dataGetNumber,String userName);

    public Map<String,Object> getPaymentDataList(String param,String type,String deptNo,String dataGetNumber,String userName);

}
