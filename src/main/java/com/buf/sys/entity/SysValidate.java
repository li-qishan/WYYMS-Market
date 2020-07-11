package com.buf.sys.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andele on 2018/11/14.
 */
public class SysValidate {
    private Long validateId;
    private Long menuId;
    private String menuName;
    private String validateName;
    private String validateKey;
    private Integer validateAttribute;
    private String description;

    public Long getValidateId() {
        return validateId;
    }

    public void setValidateId(Long validateId) {
        this.validateId = validateId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getValidateName() {
        return validateName;
    }

    public void setValidateName(String validateName) {
        this.validateName = validateName;
    }

    public String getValidateKey() {
        return validateKey;
    }

    public void setValidateKey(String validateKey) {
        this.validateKey = validateKey;
    }

    public Integer getValidateAttribute() {
        return validateAttribute;
    }

    public void setValidateAttribute(Integer validateAttribute) {
        this.validateAttribute = validateAttribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
