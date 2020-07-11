package com.buf.data.entity;

import java.util.Date;

/**
 * Created by mawenguang on 2019/5/30.
 */
public class SysLog
{
    private String operator;// 操作人
    private Date operate_time;// 操作时间
    private String search_params;// 查询参数
    private String search_time;// 查询时长
    private Date log_time;// 生成时间
    private String operate_context;// 操作内容
    private String input_param;// 页面输入参数
    private String search_type;//查询类型（0，登录，1，户号，2，表号）

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public Date getOperate_time()
    {
        return operate_time;
    }

    public void setOperate_time(Date operate_time)
    {
        this.operate_time = operate_time;
    }

    public String getSearch_params()
    {
        return search_params;
    }

    public void setSearch_params(String search_params)
    {
        this.search_params = search_params;
    }

    public String getSearch_time()
    {
        return search_time;
    }

    public void setSearch_time(String search_time)
    {
        this.search_time = search_time;
    }

    public Date getLog_time()
    {
        return log_time;
    }

    public void setLog_time(Date log_time)
    {
        this.log_time = log_time;
    }

    public String getOperate_context()
    {
        return operate_context;
    }

    public void setOperate_context(String operate_context)
    {
        this.operate_context = operate_context;
    }

    public String getInput_param()
    {
        return input_param;
    }

    public void setInput_param(String input_param)
    {
        this.input_param = input_param;
    }

    public String getSearch_type()
    {
        return search_type;
    }

    public void setSearch_type(String search_type)
    {
        this.search_type = search_type;
    }
}

