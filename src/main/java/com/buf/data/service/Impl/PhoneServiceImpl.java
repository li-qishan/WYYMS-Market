package com.buf.data.service.Impl;

import com.buf.LoginWebService.YdzyServicePortType;
import com.buf.common.utils.JSONFormMapUtils;
import com.buf.common.utils.ServiceUtils;
import com.buf.common.utils.StringUtils;
import com.buf.common.utils.XmlToJsonUtils;
import com.buf.datasource.thirdDao.phoneDao;
import com.buf.data.entity.UpdatePhoneList;
import com.buf.data.service.PhoneService;
import com.buf.datasource.secondDao.PhoneBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mawenguang on 2019/5/22.
 */
@Service
public class PhoneServiceImpl implements PhoneService
{

    @Autowired
    private phoneDao dao;

    @Autowired
    private PhoneBaseDao baseDao;

    @Override
    public List<Map<String, Object>> getUserList(String param, String type, String deptNo, String index, String userName)
    {
        return dao.getUserList(param, type, deptNo, index,userName);
    }

    @Override
    public Map<String, Object> getUserDetail(String consNo, String index, String deptNo, String userName)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("userInfo", dao.getUserDetail(consNo, index, deptNo,userName));

        map.put("userPhones", dao.getUserPhones(consNo, index, deptNo, userName));
//        map.put("userPhones", dao.getUserPhonesTest(consNo, index, deptNo,userName));


        return map;
    }

    @Override
    public Map<String, Object> userPhoneOpera(String consNo, String consName, String userName, String index, String operType, String isSign, String deptNo, String oldPhone, String phone, YdzyServicePortType portType)
    {// 只有修改的时候，oldPhone才会有值
        if (!operType.equals("2"))
        {
            oldPhone = phone;
        }
        Map<String, Object> dataMap = dao.getUserId(consNo, index, deptNo, oldPhone, operType,userName);
        String contactId = "";
        // 增加时不需要获取id
        if (!operType.equals("0"))
        {
            contactId = dataMap.get("CONTACTID").toString();
        }
        String contactName = dataMap.get("CONTACTNAME").toString();

        String mobilePhone = "";
        String homePhone = "";
        String officetel = "";
        if (phone.length() == 11)
        {
            mobilePhone = phone;
        }
        else if (phone.contains("-"))
        {
            homePhone = phone;
        }

        StringBuffer paramData = new StringBuffer();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("contactId", contactId);
        map.put("consNo", consNo);
        map.put("contactMode", index);
        map.put("contactName", contactName);
        map.put("phone", mobilePhone);
        map.put("homephone", homePhone);
        map.put("officeTel", officetel);
        map.put("operType", operType);
        map.put("userId", userName);
        map.put("temId", "127.6.6.8");
        map.put("isSign", isSign);

        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(map);

//        paramMap = setParamMap(paramMap);
        String str = ServiceUtils.appendPhoneXmlParam(mapList);
        // 获取webService数据
        String result = portType.sgpmsGetData(str);
        // 将xml格式结果集变成json格式
        result = XmlToJsonUtils.xml2json(result);
        // 将数据解析成map
        Map<String, Object> resultMap = JSONFormMapUtils.jsonToMap(result);

        savaOperData(consNo, consName, userName, operType, index, oldPhone, phone, isSign);


        return resultMap;
    }

    @Override
    public Map<String, Object> userPhoneBatchOpera(String consNo, String consName, String userName, String operType, String deptNo, String oldPhone, String phone, YdzyServicePortType portType)
    {
        // 获取参数集合
        List<Map<String, Object>> mapList = getBatchOperaParamList(consNo,consName,userName,operType,deptNo,oldPhone,phone);

        Map<String, Object> resultMap = new HashMap<>();

        if (mapList != null && mapList.size() > 0)
        {

//        paramMap = setParamMap(paramMap);
            String str = ServiceUtils.appendPhoneXmlParam(mapList);
            // 获取webService数据
            String result = portType.sgpmsGetData(str);
            // 将xml格式结果集变成json格式
            result = XmlToJsonUtils.xml2json(result);
            // 将数据解析成map
            resultMap = JSONFormMapUtils.jsonToMap(result);

        }
        else
        {
            // 数据为空时，前台提示无可修改数据
            resultMap.put("returnFlag", "0");
        }

        return resultMap;
    }

    // 获取批量操作参数
    public List<Map<String, Object>> getBatchOperaParamList(String consNo, String consName, String userName, String operType, String deptNo, String oldPhone, String phone)
    {
        List<Map<String, Object>> mapList = new ArrayList<>();

        // 只有修改的时候，oldPhone才会有值
        if (!operType.equals("2"))
        {
            oldPhone = phone;
        }

        // 获取包含旧手机号码的用户信息
        List<Map<String, Object>> dataList = dao.getUserPhoneForBatchOpera(consNo, deptNo, oldPhone,userName);

        for (Map<String, Object> dataMap : dataList)
        {
            String contactId = "";
            // 增加时不需要获取id
            if (!operType.equals("0"))
            {
                contactId = dataMap.get("CONTACTID").toString();
            }
            // 修改用户名
            String contactName = "";
            // 手机号类型
            String contactMode = "";
            // 办公电话
            String OFFICE = "";
            // 固定电话
            String HOMEPHONE = "";
            // 移动电话
            String MOBILE = "";
            if (dataMap.keySet().contains("CONTACTNAME"))
            {
                contactName = dataMap.get("CONTACTNAME").toString();
            }
            if (dataMap.keySet().contains("CONTACTMODE"))
            {
                contactMode = dataMap.get("CONTACTMODE").toString();
            }
            if (dataMap.keySet().contains("OFFICE"))
            {
                OFFICE = dataMap.get("OFFICE").toString();
            }
            if (dataMap.keySet().contains("HOMEPHONE"))
            {
                HOMEPHONE = dataMap.get("HOMEPHONE").toString();

            }
            if (dataMap.keySet().contains("MOBILE"))
            {
                MOBILE = dataMap.get("MOBILE").toString();
            }

            String mobilePhone = "";
            String homePhone = "";
            String officetel = "";
            // 修改不为空并且与旧号码相同字段
            if (StringUtils.isNotBlank(OFFICE) && oldPhone.equals(OFFICE))
            {
                officetel = phone;
            }
            else if (StringUtils.isNotBlank(HOMEPHONE) && oldPhone.equals(HOMEPHONE))
            {
                homePhone = phone;
            }
            else if (StringUtils.isNotBlank(MOBILE) && oldPhone.equals(MOBILE))
            {
                mobilePhone = phone;
            }

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("contactId", contactId);
            map.put("consNo", consNo);
            map.put("contactMode", contactMode);
            map.put("contactName", contactName);
            map.put("phone", mobilePhone);
            map.put("homephone", homePhone);
            map.put("officeTel", officetel);
            map.put("operType", operType);
            map.put("userId", userName);
            map.put("temId", "127.6.6.8");
            map.put("isSign", "0");

            mapList.add(map);

            savaOperData(consNo, consName, userName, operType, contactMode, oldPhone, phone, "0");
        }

        return mapList;
    }


    @Override
    public List<UpdatePhoneList> getUpdateList(String userName)
    {
        List<UpdatePhoneList> list = baseDao.selectAll(userName);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (list != null && list.size() > 0)
        {
            for (UpdatePhoneList phoneList : list)
            {
                try
                {
                    Date date = sd.parse(phoneList.getOperTime());
                    phoneList.setOperTime(sd.format(date));
                    // 转为中文操作联系人标识
                    phoneList.setContactMode(ServiceUtils.getContactModeCh(phoneList.getContactMode()));
                    // 转为中文操作标识
                    phoneList.setOperType(ServiceUtils.getOperTypeCh(phoneList.getOperType()));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private int savaOperData(String consNo, String consName, String userName, String operType, String contacaMode, String oldPhone, String newPhone, String isSign)
    {

        // 返回信息
        int result = 0;
        // 时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 实体
        UpdatePhoneList list = new UpdatePhoneList();

        list.setConsNo(consNo);
        list.setConsName(consName);
        list.setContactMode(contacaMode);
        list.setNewPhone(newPhone);
        if (isSign.equals("1") || !operType.equals("2"))
        {
            list.setOldPhone("无");
        }
        else
        {
            list.setOldPhone(oldPhone);
        }

        list.setOperBy(userName);
        list.setOperTime(sdf.format(new Date()));
        if (isSign.equals("1"))
        {
            list.setOperType("#");
        }
        else
        {
            list.setOperType(operType);
        }

        // 存在数据更新，不存在数据添加
        if (baseDao.selectCount(consNo, contacaMode) > 0)
        {
            result = baseDao.update(list);
        }
        else
        {
            result = baseDao.insert(list);
        }

        return result;
    }


}
