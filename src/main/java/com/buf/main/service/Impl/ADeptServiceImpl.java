package com.buf.main.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.buf.main.dao.ADeptMapper;
import com.buf.main.entity.ADept;
import com.buf.main.entity.BDept;
import com.buf.main.service.ADeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/7/16.
 */
@Service("ADeptService")
public class ADeptServiceImpl implements ADeptService{
    @Autowired
    private ADeptMapper mapper;
    public ADept getrow(){
        return mapper.getrow();
    }

    public int getByNames(String no,String name){
        return  mapper.getByNames(no, name);
    }

    public int add(BDept obj){
        return  mapper.add(obj);
    }

    public int update(BDept obj){
        return mapper.update(obj);
    }

    public int remove(JSONObject params){
        return mapper.remove(params);
    }
}
