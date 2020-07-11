package com.buf.main.service;

import com.alibaba.fastjson.JSONObject;
import com.buf.main.entity.ADept;
import com.buf.main.entity.BDept;

import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/7/16.
 */
public interface ADeptService {

    public ADept getrow();

    public int getByNames(String no,String name);

    public int add(BDept obj);

    public int update(BDept obj);

    public int remove(JSONObject params);
}
