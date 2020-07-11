package com.buf.main.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by lyf on 2018/7/23.
 */
@Mapper
public interface BaselogMapper {

    @Insert("INSERT INTO SYSLOGINRECORD (SYNCDATE , USERCODE, LOGINNAME , REALNAME, LOGINIP) " +
            "   values" +
            "( #{syncdate}, #{usercode},  #{loginname},#{realname}, #{loginip} )")
    public int insertLog(@Param("syncdate") Date syncdate, @Param("usercode") String usercode, @Param("loginname") String loginname , @Param("realname") String  realname, @Param("loginip") String loginip);

}
