package com.buf.data.dao;

import com.buf.data.dao.provider.SpPrcProvider;
import com.buf.data.entity.MP;
import com.buf.data.entity.Meter;
import com.buf.data.entity.MeterRead;
import com.buf.data.entity.SP_Prc;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by mawenguang on 2019/7/23.
 */
public interface SpPrcDao
{
    @SelectProvider(type = SpPrcProvider.class,method = "getSpPrcData")
    public List<SP_Prc> getSpPrcList(@Param("consNo") String consNo);

    @SelectProvider(type = SpPrcProvider.class,method = "getMpDetail")
    public List<MP> getMpDetail(@Param("consNo") String consNo, @Param("prcCode") String prcCode, @Param("spId") String spId);

    @SelectProvider(type = SpPrcProvider.class,method = "getMeterDetail")
    List<Meter> getMeterDetail(@Param("consNo") String consNo,@Param("mpId") String mpId);

    @SelectProvider(type = SpPrcProvider.class,method = "getMeterReadDetail")
    List<MeterRead> getMeterReadDetail(@Param("consNo") String consNo, @Param("consMtId") String consMtId);

}
