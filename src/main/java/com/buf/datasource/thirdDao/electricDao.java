package com.buf.datasource.thirdDao;

import com.buf.data.dao.provider.electricProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
public interface electricDao
{

    @SelectProvider(type = electricProvider.class,method = "getBasicData")
    public List<Map<String,Object>> getElectricData(@Param("param") String param, @Param("type") String type,
                                                    @Param("deptNo") String deptNo,@Param("dataGetNumber") String dataGetNumber,
                                                    @Param("userName") String userName);

}
