package com.buf.data.service.Impl;

import com.buf.data.dao.SpPrcDao;
import com.buf.data.entity.MP;
import com.buf.data.entity.Meter;
import com.buf.data.entity.MeterRead;
import com.buf.data.entity.SP_Prc;
import com.buf.data.service.SpPrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mawenguang on 2019/7/23.
 */
@Service
public class SpPrcServiceImpl implements SpPrcService
{
    @Autowired
    private SpPrcDao dao;

    @Override
    public List<Map<String, Object>> getSpPrcList(String consNo)
    {

        List<Map<String, Object>> resultList = new ArrayList<>();

        List<SP_Prc> listData = dao.getSpPrcList(consNo);

        Map<String, List<SP_Prc>> collect = listData.stream().collect(Collectors.groupingBy(SP_Prc::getSpId));

        List<String> spIdList = new ArrayList<>(collect.keySet());

        for (String spId : spIdList)
        {
            Map<String, Object> map = new LinkedHashMap<>();

            List<SP_Prc> listSingle = collect.get(spId);

            map.put("prcType", listSingle.get(0).getPrcType());
            map.put("baCalc", listSingle.get(0).getBaCalc());
            map.put("dmdSpecValue", listSingle.get(0).getDmdSpecValue());
            map.put("pfEvalMode", listSingle.get(0).getPfEvalMode());
            // 电价码数据
            map.put("prcList", listSingle);

            resultList.add(map);
        }

        return resultList;
    }

    @Override
    public List<MP> getMpList(String consNo, String prcCode, String spId)
    {
        return dao.getMpDetail(consNo, prcCode, spId);
    }

    @Override
    public List<Map<String, Object>> getMeterAndReadList(String consNo, String mpId)
    {
        List<Map<String, Object>> resultList = new ArrayList<>();

        Map<String, Object> result = new HashMap<>();

        List<Meter> meterList = dao.getMeterDetail(consNo, mpId);

        if (meterList != null && meterList.size() > 0)
        {
            Meter meter = meterList.get(0);

            String consMtId = meter.getConsMtId();

            result.put("barCode", meter.getBarCode());
            result.put("tFactor", meter.gettFactor());
            result.put("instDate", meter.getInstDate());
            result.put("lastCheckDate", meter.getLastCheckDate());

            List<MeterRead> readList = dao.getMeterReadDetail(consNo, consMtId);

            result.put("meterRead", readList);
        }
        resultList.add(result);

        return resultList;
    }
}
