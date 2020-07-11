package com.buf.main.service.Impl;


import com.buf.main.dao.SaOrgMapper;
import com.buf.main.entity.SaOrg;
import com.buf.main.service.SaOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/5/28.
 */
@Service
public class SaOrgServiceImpl  implements SaOrgService {
    @Autowired
    private SaOrgMapper saOrgMapper;

    @Override
    public List<SaOrg> queryList(){
        List <SaOrg> list =  saOrgMapper.selectAll();
        return list;
    }

    @Override
    public List<SaOrg> selectByPOrgNo(String PSaOrg){
        List <SaOrg> list =  saOrgMapper.selectByPOrgNo(PSaOrg);
        return list;
    }

    @Override
    public List<SaOrg> selectByOrgNo(String SaOrg){
        List <SaOrg> list =  saOrgMapper.selectByOrgNo(SaOrg);
        return list;
    }
    @Override
    public List<Map<String,Object>> selectDeptList(String orgNo){
        return saOrgMapper.selectDeptList(orgNo);
    }

}
