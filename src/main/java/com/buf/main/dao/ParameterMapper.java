package com.buf.main.dao;

import com.buf.main.entity.SysCode;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ParameterMapper {
    //AND SYS_FLAG = 0
    @Select("SELECT a.* FROM SYS_CODE a WHERE a.CODE_TYPE = #{params} AND DEL_FLAG = 0  ORDER BY ORDER_NUM")
    public List<Map<String,String>> getroot(String params);

    @Update("UPDATE SYS_CODE SET DEL_FLAG = 1 WHERE CODE_ID = #{params}")
    public int remove(String params);

    //SELECT * FROM SYS_CODE a WHERE a.CODE = 'TOTAL' OR a.CODE_TYPE = 'IND_DIM' AND ORDER_NUM = 1
    @Select("SELECT count(*) FROM SYS_CODE a WHERE a.DEL_FLAG = '0' AND  a.CODE_TYPE = #{CODE_TYPE} AND a.ORDER_NUM = #{codenum} OR a.CODE = #{code} AND a.DEL_FLAG = '0'")
    int getByCode(@Param("codenum") String codenum, @Param("code")String code, @Param("CODE_TYPE") String CODE_TYPE);

    @Insert("INSERT INTO SYS_CODE (CODE_ID, CODE_TYPE , CODE, CODE_NAME , ORDER_NUM, REMARK, DEL_FLAG, SYS_FLAG) " +
            "values " +
            "(#{CODE_ID}, #{CODE_TYPE}, #{CODE},  #{CODE_NAME}, #{ORDER_NUM}, #{REMARK}, 0, 0 )")
    public int add(SysCode obj);
}
