package com.buf.main.entity;

import java.util.List;

/**
 * Created by L on 2018/5/28.
 */
public class SaOrgTree {
    /// <summary>
    /// id:主键
    /// </summary>
    public String id;


    /// <summary>
    /// text:节点名称
    /// </summary>
    public String text;


    /// <summary>
    /// DeviceCount:设备数量
    /// </summary>
    public int level;


    /// <summary>
    /// nodeId:节点ID
    /// </summary>
    public String nodeId;


    /// <summary>
    /// parentId:父节点ID
    /// </summary>
    public String parentId;


    /// <summary>
    /// selected:选择
    /// </summary>
    public boolean selected;


    /// <summary>
    /// expanded:展开
    /// </summary>
    public boolean expanded;


    /// <summary>
    /// nodes:子集合
    /// </summary>
    public List<SaOrgTree> nodes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<SaOrgTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<SaOrgTree> nodes) {
        this.nodes = nodes;
    }

}
