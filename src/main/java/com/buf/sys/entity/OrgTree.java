package com.buf.sys.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12.
 */

public class OrgTree {
    private String id;
    private String text;
    private List<OrgTree> children;
    public String getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public List<OrgTree> getChildren() {
        return children;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setChildren(List<OrgTree> children) {
        this.children = children;
    }
    public OrgTree() {
        super();
    }
    /**
     * @param id
     * @param text
     * @param children
     */
    public OrgTree(String id, String text,
                   List<OrgTree> children) {
        super();
        this.id = id;
        this.text = text;
        this.children = children;
    }
}