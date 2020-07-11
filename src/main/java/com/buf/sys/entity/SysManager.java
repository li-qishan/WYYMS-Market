package com.buf.sys.entity;

/**
 * Created by xujin on 2018/11/1.
 */
public enum  SysManager {
    ISDELETE("已删除","0"), NODOWNLOADED("未下载","1"), ISDOWNLOADED("已下载","2");

    private final String chinese;
    private final String index;

    private SysManager(String chinese,String index) {
        this.chinese = chinese;
        this.index = index;
    }

    public String getValue() {
        return index;
    }
}
