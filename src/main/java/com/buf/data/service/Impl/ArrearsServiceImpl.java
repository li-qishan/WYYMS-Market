package com.buf.data.service.Impl;

import com.buf.datasource.thirdDao.ArrearsDao;
import com.buf.data.service.ArrearsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/6/21.
 */
@Service
public class ArrearsServiceImpl implements ArrearsService
{

    @Autowired
    private ArrearsDao dao;

    @Override
    public Map<String, Object> getUserArrearsList(String deptNo, String param, String type, String month, int pageNum, int pageSize,String userName)
    {
        Map<String, Object> data = new HashMap<>();

        int startPage = 0;
        int endPage = 0;
        // 计算页标
        startPage = 1 + (pageSize * (pageNum - 1)); // 开始页码
        endPage = pageNum * pageSize;           // 结束页码
        month = month.replaceAll("-", "");
        List<Map<String, Object>> list = dao.getUserArrearsList(deptNo, param, type, month, startPage, endPage,userName);

        data.put("list", list);
        // 总欠费人数
        int userNum =0;
        // 计算总欠费金额
        Double moneyNum = 0d;

        if(list != null && list.size() > 0){
            moneyNum = Double.parseDouble(list.get(0).get("SUMMONEY").toString());
            userNum = Integer.parseInt(list.get(0).get("COUNT").toString());
        }

//        if (!type.equals("1") && startPage == 1){
//            /COUNT
//            moneyNum = Double.parseDouble(list.get(0).get("SUMMONEY").toString());
//            userNum = Integer.parseInt(list.get(0).get("COUNT").toString());
//        }else{
//            moneyNum = list.stream().mapToDouble(m -> Double.valueOf(m.get("ARREARSMONEY").toString())).sum();
//            userNum = list.size();
//        }

        data.put("moneyNum", moneyNum);
        data.put("userNum", userNum);

        return data;
    }

    @Override
    public List<Map<String, Object>> getUserArrearsDetail(String deptNo, String consNo, String month,String userName)
    {
        return dao.getUserArrearsDetail(deptNo, consNo, month,userName);
    }

    @Override
    public List<Map<String, Object>> getCBUser(String userName, String selectType, String inputName) {
        return dao.getCBUser(userName, selectType, inputName);
    }


    public static void main(String args[])
    {
        String month = "2019-07";
        System.out.println(month.replaceAll("-", ""));
    }
}
