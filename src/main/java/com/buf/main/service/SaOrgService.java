package com.buf.main.service;

import com.buf.main.entity.SaOrg;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2018/5/28.
 */
public interface SaOrgService {
    List<SaOrg> queryList();

    List<SaOrg> selectByPOrgNo(String PSaOrg);

    List<SaOrg> selectByOrgNo(String SaOrg);

    List<Map<String,Object>> selectDeptList(String orgNo);
}
