package com.buf.data.service;

import com.buf.data.entity.MP;
import com.buf.data.entity.SP_Prc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/7/23.
 */
public interface SpPrcService
{
    List<Map<String, Object>> getSpPrcList(String consNo);

    List<MP>  getMpList(String consNo, String prcCode, String spId);

    List<Map<String, Object>> getMeterAndReadList(String consNo, String mpId);
}
