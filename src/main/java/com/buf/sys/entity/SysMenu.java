package com.buf.sys.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/7/24.
 */
public class SysMenu {
    private Long menuId;
    private Long parentMenuId;
    private String menuCode;
    private String menuName;
    private String menuURL;
    private String description;
    private String iconCls;
    private Integer orderNo;
    private Integer menuAttribute;
    private List<SysMenu> children = new ArrayList<SysMenu>();
    private String id;
    private String text;
    private String state;
    private boolean checked;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentMenuId() {
        if(parentMenuId==null) parentMenuId=0L;
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuURL() {
        return menuURL;
    }

    public void setMenuURL(String menuURL) {
        this.menuURL = menuURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getMenuAttribute() {
        return menuAttribute;
    }

    public void setMenuAttribute(Integer menuAttribute) {
        this.menuAttribute = menuAttribute;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public String getId() {
        return this.menuId.toString();
    }

    public String getText() {
        return this.menuName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
