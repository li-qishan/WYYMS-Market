package com.buf.main.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.buf.main.dao.ParameterMapper;
import com.buf.main.entity.SysCode;
import com.buf.main.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ParameterService")
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterMapper mapper;

    public List<Map<String, String>> getroot(String params) {
        return mapper.getroot(params);
    }

    public int remove(String params) {
        return mapper.remove(params);
    }

    public int getByCode(String codenum, String code, String codetype) {
        return mapper.getByCode(codenum, code ,codetype);
    }

    public int add(SysCode obj){
        return mapper.add(obj);
    }
}
