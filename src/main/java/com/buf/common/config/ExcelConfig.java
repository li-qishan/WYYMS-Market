package com.buf.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xujin on 2018/10/18.
 */
@Configuration()
public class ExcelConfig {

    @Value("${serverPath.filePath}")
    private String filePath;

    @Value("${serverPath.deleteDay}")
    private int deleteDay;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getDeleteDay() {
        return deleteDay;
    }

    public void setDeleteDay(int deleteDay) {
        this.deleteDay = deleteDay;
    }
}
