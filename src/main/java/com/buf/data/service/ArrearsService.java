package com.buf.data.service;

import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/6/21.
 */
public interface ArrearsService
{
    Map<String, Object> getUserArrearsList(String deptNo,String param,String type,String month,int pageNum,int pageSize,String userName);
    List<Map<String, Object>> getUserArrearsDetail(String deptNo,String consNo,String month,String userName);

    List<Map<String, Object>> getCBUser(String userName, String selectType, String inputName);
}
