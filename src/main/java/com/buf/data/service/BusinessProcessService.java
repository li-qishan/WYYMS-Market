package com.buf.data.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 业扩流程查询Service
 *
 * @author liqishan
 * @create 2019-06-21 15:17
 */
public interface BusinessProcessService {

    List<Map<String, Object>> getBusinessProcessByOrderNum(String orderNum, String searchType,String selectType,String deptNo,String userName) throws ParseException;

    List<Map<String, Object>> getMoreBusinessProcessByOrderNum(String orderNum, String searchType,String selectType,String deptNo,String userName) throws ParseException;
}
