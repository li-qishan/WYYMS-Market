package com.buf.data.service.Impl;

import com.buf.datasource.thirdDao.electricDao;
import com.buf.datasource.thirdDao.paymentDao;
import com.buf.data.dao.userInfoDao;
import com.buf.data.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by mawenguang on 2019/5/14.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService
{
    @Autowired
    private electricDao electricDao;

    @Autowired
    private paymentDao paymentDao;

    @Autowired
    private userInfoDao userInfoDao;

    @Override
    public List<Map<String, Object>> getUserList(String param, String type, String deptNo,String userName)
    {
        return userInfoDao.getSearchData(param,type,deptNo,userName);
    }

    @Override
    public Map<String, Object> getUserInfoDataList(String param, String type,String deptNo,String dataGetNumber,String userName)
    {
        Map<String, Object> data = new HashMap<>();

        // 用户基础数据
        Map<String, Object> userInfo = new HashMap<>();

        List<Map<String, Object>> ss = userInfoDao.getBasicData(param, type,deptNo,dataGetNumber,userName);

        if (ss != null && ss.size() > 0)
        {
            userInfo = ss.get(0);
            try
            {
                // 将时间格式化
                String date = userInfo.get("BUILD_DATE").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                userInfo.put("BUILD_DATE", sdf.format(sdf.parse(date)));
            }
            catch (ParseException e)
            {

            }
        }
        data.put("userInfo", userInfo);
        return data;
    }

    @Override
    public Map<String, Object> getElectricDataList(String param, String type,String deptNo,String dataGetNumber,String userName)
    {
        Map<String, Object> data = new HashMap<>();

        // 电费定量
        List<Map<String, Object>> electric = new ArrayList<>();

        electric = electricDao.getElectricData(param, type,deptNo,dataGetNumber,userName);
        if (electric != null && electric.size() > 0)
        {
            for (Map<String, Object> map : electric)
            {

                String ym = map.get("YM").toString();
                map.put("mouthDay", getDayOfMonth(ym));

                String year = ym.substring(0, 4);
                String month = ym.substring(4, ym.length());
                map.put("year", year);
                map.put("month", month);
            }
        }
        data.put("electric", electric);
        return data;
    }

    @Override
    public Map<String, Object> getPaymentDataList(String param, String type,String deptNo,String dataGetNumber,String userName)
    {
        Map<String, Object> data = new HashMap<>();
        // 缴费
        List<Map<String, Object>> payment = new ArrayList<>();

        payment = paymentDao.getBasicData(param, type,deptNo,dataGetNumber,userName);

        if (payment != null && payment.size() > 0)
        {
            for (Map<String, Object> map : payment)
            {
                String ym = map.get("CHARGE_YM").toString();
                if(ym.equals("AAAAAA")){
                    map.put("paymentTime", "AAAAAA");
                }else{
                    map.put("paymentTime", ym.substring(0,11));
                    map.put("time", ym.substring(11,ym.length()-2));
                }
            }
        }
        data.put("payment", payment);
        return data;
    }

    /**
     * 获取年月的第一天到最后一天的范围
     *
     * @param year
     * @param month
     * @return 例： 05.01-05.31
     */
    public static String getDayOfMonth(String ym)
    {

        int year = Integer.parseInt(ym.substring(0, 4));
        int month = Integer.parseInt(ym.substring(5, ym.length()));

        StringBuffer dateTime = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");

        dateTime.append(sdf.format(cal.getTime()));

        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);

        dateTime.append("-" + sdf.format(cal.getTime()));

        return dateTime.toString();
    }

    public static void main(String args[])
    {
        String ym = "2019-03-30 10:20:12.0";
        System.out.println(ym.substring(0,11));
        System.out.println(ym.substring(11,ym.length()-2));
    }

}
