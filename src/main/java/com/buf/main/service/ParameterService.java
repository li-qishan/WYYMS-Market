package com.buf.main.service;
import com.buf.main.entity.SysCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ParameterService {

    public List<Map<String,String>> getroot(String params);

    public int remove(String params);

    public int getByCode( String codenum,String code,String codetype);

    public int add(SysCode obj);
}
