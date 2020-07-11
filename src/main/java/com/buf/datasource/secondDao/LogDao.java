package com.buf.datasource.secondDao;

import com.buf.data.entity.SysLog;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by mawenguang on 2019/5/30.
 */
public interface LogDao
{

    @Insert(" INSERT INTO SYSLOG_USERSEARCH( OPERATOR, OPERATE_TIME, SEARCH_PARAMS, SEARCH_TIME, LOG_TIME, OPERATE_CONTEXT,INPUT_PARAM,SEARCH_TYPE)" +
            " VALUES (#{operator},#{operate_time},#{search_params},#{search_time},#{log_time},#{operate_context},#{input_param},#{search_type})")
    int insertLog(SysLog log);
}
