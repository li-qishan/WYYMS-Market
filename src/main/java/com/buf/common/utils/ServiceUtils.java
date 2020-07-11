package com.buf.common.utils;


import com.buf.LoginWebService.YdzyService;
import com.buf.LoginWebService.YdzyServicePortType;

import java.util.*;

/**
 * Created by mawenguang on 2019/5/30.
 */
public class ServiceUtils {

    // 拼接报文
    public static String appendXmlParam(Map<String, Object> map) {

        StringBuffer sb = new StringBuffer();
        sb.append("<ORDER>");

        Set keys = map.keySet();
        Object[] keyArray = keys.toArray();
        for (int i = 0; i < keyArray.length; i++) {
            String key = keyArray[i].toString();
            // 因为前四列字段固定
            if (i == 4) {
                sb.append("<data>");
            }
            sb.append("<" + key + ">" + map.get(key) + "</" + key + ">");
        }
        {

        }
        sb.append("</data>");
        sb.append("</ORDER>");
        return sb.toString();
    }

    public static String appendPhoneXmlParam(List<Map<String, Object>> mapList) {
        StringBuffer sb = new StringBuffer();
        sb.append("<ORDER>" +
                "<data>" +
                "<serviceCode>LnUpdatePhone</serviceCode> " +
                "<source>123</source>" +
                "<target>11102</target>" +
                "<phoneImei>285525277212769</phoneImei>" +
                "<lists>");
        if (mapList != null && mapList.size() > 0) {
            for (int i = 0; i < mapList.size(); i++) {
                sb.append("<list>");
                Map<String, Object> map = mapList.get(i);
                Set keys = map.keySet();
                Object[] keyArray = keys.toArray();
                for (int j = 0; j < keyArray.length; j++) {
                    String key = keyArray[j].toString();
//                    if (i == 4) {
//                        sb.append("<data>");
//                    }
                    sb.append("<" + key + ">" + map.get(key) + "</" + key + ">");
                }
                sb.append("</list>");
            }
        }
        sb.append("</lists>");
        sb.append("</data>");
        sb.append("</ORDER>");
        return sb.toString();
    }

    /**
     * 获取正式接口
     *
     * @return
     */
    public static YdzyServicePortType getPort() {
        YdzyService service = new YdzyService();
        return service.getYdzyServiceHttpSoap12Endpoint();
    }

    /**
     * 获取测试接口
     *
     * @return
     */
    public static com.buf.PhoneWebService.YdzyServicePortType getPhonePort() {
        com.buf.PhoneWebService.YdzyService service = new com.buf.PhoneWebService.YdzyService();
        return service.getYdzyServiceHttpSoap12Endpoint();
    }



    // 获取联系人类型
    public static String getContactModeCh(String index)
    {
        String indexCh = "";// 0新增，1删除，2更新

        switch (index)
        {
            case "01":
                indexCh = "电气联系人";
                break;
            case "02":
                indexCh = "账务联系人";
                break;
            case "03":
                indexCh = "停送电联系人";
                break;

        }
        return indexCh;
    }

    // 操作标识
    public static String getOperTypeCh(String operType)
    {
        String operTypeCh = "";// 0新增，1删除，2更新

        switch (operType)
        {
            case "0":
                operTypeCh = "增加";
                break;
            case "1":
                operTypeCh = "删除";
                break;
            case "2":
                operTypeCh = "更新";
                break;
            case "#":
                operTypeCh = "设置催费短信";
                break;

        }
        return operTypeCh;
    }


    public static void main(String args[]) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("contactId", "");
        map.put("consNo", "022220");
        map.put("contactMode", "01");
        map.put("contactName", "1");
        map.put("phone", "123123415");
        map.put("homephone", "");
        map.put("officeTel", "");
        map.put("operType", "0");
        map.put("userId", "cx_sy");
        map.put("temId", "127.6.6.8");
        map.put("isSign", "0");
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(map);
        System.out.println(appendPhoneXmlParam(mapList));
    }
}
