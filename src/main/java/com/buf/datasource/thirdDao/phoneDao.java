package com.buf.datasource.thirdDao;

import com.buf.data.dao.provider.phoneProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/22.
 */
public interface phoneDao
{
    @SelectProvider(type = phoneProvider.class, method = "getBasicData")
    public List<Map<String, Object>> getUserList(@Param("param") String param, @Param("type") String type,
                                                 @Param("deptNo") String deptNo, @Param("index") String index,
                                                 @Param("userName") String userName);

    @SelectProvider(type = phoneProvider.class, method = "getUserDetail")
    Map<String, Object> getUserDetail(@Param("consNo") String consNo, @Param("index") String index, @Param("deptNo") String deptNo,
                                      @Param("userName") String userName);

    @SelectProvider(type = phoneProvider.class, method = "getUserPhones")
    List<Map<String, Object>> getUserPhones(@Param("consNo") String consNo, @Param("index") String index, @Param("deptNo") String deptNo,
                                            @Param("userName") String userName);

    @SelectProvider(type = phoneProvider.class, method = "getUserPhonesTest")
    List<Map<String, Object>> getUserPhonesTest(@Param("consNo") String consNo, @Param("index") String index, @Param("deptNo") String deptNo,
                                                @Param("userName") String userName);

    @SelectProvider(type = phoneProvider.class, method = "getUserId")
    Map<String, Object> getUserId(@Param("consNo") String consNo, @Param("index") String index, @Param("deptNo") String deptNo,
                                  @Param("phone") String phone, @Param("operType") String operType,
                                  @Param("userName") String userName);

    @SelectProvider(type = phoneProvider.class, method = "getUserPhoneForBatchOpera")
    List<Map<String, Object>> getUserPhoneForBatchOpera(@Param("consNo") String consNo, @Param("deptNo") String deptNo, @Param("oldPhone") String oldPhone,
                                                        @Param("userName") String userName);

}
