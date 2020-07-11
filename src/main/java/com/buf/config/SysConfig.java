package com.buf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Liangzhu on 2018/7/6.
 */
@Configuration
public class SysConfig {

    public String getAppName() {
        return "微应用后台管理系统";
    }

}
