package com.buf.main.entity;

import java.util.List;

/**
 * Created by lyf on 2018/7/16.
 */
public class ADept {
    private String DEPT_NO;
    private String DEPT_NAME;
    private String PARENT_NO;
    private String ORG_NO;
    private String SHORT_NAME;
    private String IND_DIM_ID;
    private String id;
    private String text;
    private List<ADept> children;

    public String getDEPT_NO() {
        return DEPT_NO;
    }

    public void setDEPT_NO(String DEPT_NO) {
        this.DEPT_NO = DEPT_NO;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public String getPARENT_NO() {
        return PARENT_NO;
    }

    public void setPARENT_NO(String PARENT_NO) {
        this.PARENT_NO = PARENT_NO;
    }

    public String getORG_NO() {
        return ORG_NO;
    }

    public void setORG_NO(String ORG_NO) {
        this.ORG_NO = ORG_NO;
    }

    public String getSHORT_NAME() {
        return SHORT_NAME;
    }

    public void setSHORT_NAME(String SHORT_NAME) {
        this.SHORT_NAME = SHORT_NAME;
    }

    public String getIND_DIM_ID() {
        return IND_DIM_ID;
    }

    public void setIND_DIM_ID(String IND_DIM_ID) {
        this.IND_DIM_ID = IND_DIM_ID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ADept> getChildren() {
        return children;
    }

    public void setChildren(List<ADept> children) {
        this.children = children;
    }
}
