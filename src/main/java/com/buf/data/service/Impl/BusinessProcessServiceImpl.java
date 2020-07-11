package com.buf.data.service.Impl;

import com.buf.common.utils.StringUtils;
import com.buf.data.dao.businessProcessDao;
import com.buf.data.service.BusinessProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业扩流程查询Impl
 *
 * @author liqishan
 * @create 2019-06-21 15:18
 */
@Service
public class BusinessProcessServiceImpl implements BusinessProcessService {

    @Autowired
    businessProcessDao businessProcess;

    @Override
    public List<Map<String, Object>> getBusinessProcessByOrderNum(String orderNum, String searchType,String selectType,String deptNo,String userName) throws ParseException {
        //返回事件
        Map<String, Object> data = new HashMap<>();
        //返回数据
//        Map<String, Object> order = new HashMap<>();
        // 用户基础数据
        List<Map<String, Object>> orderList =null;
        //判断  是工单编号简略查询  还是工单编号详细查询
        if(searchType.equals("1")){
        // 查询类别 1 简略查询
            orderList = businessProcess.getLessBusinessProcessByOrderNum(orderNum,selectType,deptNo,userName);
            for(Map<String, Object> orders:orderList){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 将时间格式化
                if(orders.get("ACCEPTTIME")!=null){
                    String ACCEPTTIME =orders.get("ACCEPTTIME").toString();
                    orders.put("ACCEPTTIME", sdf.format(sdf.parse(ACCEPTTIME)));
                }

                //
                if(orders.get("FINISHEDTIME")!=null){
                    String FINISHEDTIME =orders.get("FINISHEDTIME").toString();
                    orders.put("FINISHEDTIME", sdf.format(sdf.parse(FINISHEDTIME)));
                }
            }

        }else if(searchType.equals("2")){
        // 查询类别 2 详细查询
            orderList = businessProcess.getMoreBusinessProcessByOrderNum(orderNum,selectType,deptNo,userName);
            for(Map<String, Object> orders:orderList){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if(orders.get("CREATETIME")!=null){
                    String CREATETIME =orders.get("CREATETIME").toString();
                    orders.put("CREATETIME", sdf.format(sdf.parse(CREATETIME)));
                }

                //
                if(orders.get("FINISHEDTIME")!=null){
                    String FINISHEDTIME  =orders.get("FINISHEDTIME").toString();
                    orders.put("FINISHEDTIME", sdf.format(sdf.parse(FINISHEDTIME)));
                }

            }
        }
        return orderList;
    }

    @Override
    public List<Map<String, Object>> getMoreBusinessProcessByOrderNum(String orderNum, String searchType,String selectType,String deptNo,String userName) throws ParseException {
        //返回事件
        Map<String, Object> data = new HashMap<>();
        //返回数据
//        Map<String, Object> order = new HashMap<>();
        // 用户基础数据
        List<Map<String, Object>> orderList =null;
        //判断  是工单编号简略查询  还是工单编号详细查询
        if(searchType.equals("1")){
            // 查询类别 1 简略查询
            orderList = businessProcess.getLessBusinessProcessByOrderNum(orderNum,selectType,deptNo,userName);
            for(Map<String, Object> orders:orderList){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 将时间格式化
                if(orders.get("ACCEPTTIME")!=null){
                    String ACCEPTTIME =orders.get("ACCEPTTIME").toString();
                    orders.put("ACCEPTTIME", sdf.format(sdf.parse(ACCEPTTIME)));
                }

                //
                if(orders.get("FINISHEDTIME")!=null){
                    String FINISHEDTIME =orders.get("FINISHEDTIME").toString();
                    orders.put("FINISHEDTIME", sdf.format(sdf.parse(FINISHEDTIME)));
                }
            }

        }else if(searchType.equals("2")){
            // 查询类别 2 详细查询
            orderList = businessProcess.getMoreBusinessProcessByOrderNum(orderNum,selectType,deptNo,userName);
            for(Map<String, Object> orders:orderList){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if(orders.get("CREATETIME")!=null){
                    String CREATETIME =orders.get("CREATETIME").toString();
                    orders.put("CREATETIME", sdf.format(sdf.parse(CREATETIME)));
                }

                //
                if(orders.get("FINISHEDTIME")!=null){
                    String FINISHEDTIME  =orders.get("FINISHEDTIME").toString();
                    orders.put("FINISHEDTIME", sdf.format(sdf.parse(FINISHEDTIME)));
                }

            }
        }
        return orderList;
    }
}
