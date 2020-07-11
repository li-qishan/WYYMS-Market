package com.buf.sys.dao;
import com.buf.sys.entity.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by andele on 2018/11/12.
 */
public class SQLProvider {

    /*角色管理部分*/
    public String insertRoleAndMenu(Map map){
        String roleId = (String)map.get("roleId");
        String menuIds = (String)map.get("menuIds");
        String[] ids = menuIds.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into SYSROLEANDMENU (ROLEID, MENUID) ");
        sb.append("VALUES ");
        for(int i = 0;i < ids.length;i++){
           if(i==0) sb.append("("+roleId+","+ids[i]+")");
           else {
               sb.append(",");
               sb.append("(" + roleId + "," + ids[i] + ")");
           }
        }
        return sb.toString();
    }










    /*菜单管理部分*/
    public String getMenusByCondition(Map map){
        StringBuilder sb = new StringBuilder();
        SysMenu menu = (SysMenu) map.get("menu");
        sb.append("SELECT * from SYSMENU where 1=1 ");
        if(menu.getMenuAttribute() != null){
            sb.append(" and MENUATTRIBUTE = "+menu.getMenuAttribute());
        }
        sb.append(" order by ORDERNO");
        return sb.toString();
    }

    /*数据权限管理部分*/
    public String getValidateByCondition(Map map){
        StringBuilder sb = new StringBuilder();
        SysValidate validate = (SysValidate) map.get("validate");
        sb.append("SELECT a.*,b.MENUNAME from SYSVALIDATE a,SYSMENU b where a.MENUID = b.MENUID ");

        if(validate.getMenuId() != null){
            sb.append(" and a.MENUID = "+validate.getMenuId());
        }

        if(validate.getValidateAttribute() != null){
            sb.append(" and a.VALIDATEATTRIBUTE = "+validate.getValidateAttribute());
        }
        sb.append(" order by a.VALIDATEID");
        return sb.toString();
    }

    public String insertRoleAndValidate(Map map){
        String roleId = (String)map.get("roleId");
        String validateIds = (String)map.get("validateIds");
        String[] ids = validateIds.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into SYSROLEANDVALIDATE (ROLEID, VALIDATEID) ");
        sb.append("VALUES ");
        for(int i = 0;i < ids.length;i++){
            if(i==0) sb.append("("+roleId+","+ids[i]+")");
            else {
                sb.append(",");
                sb.append("(" + roleId + "," + ids[i] + ")");
            }
        }
        return sb.toString();
    }




    /*用户管理部分*/
    public String getUsersByCondition(Map map){
        StringBuilder sb = new StringBuilder();
        SysUserEntity user = (SysUserEntity) map.get("user");
        sb.append("SELECT USERID,DEPTID,USERCODE,LOGINNAME,SALT,PASSWORD,REALNAME,GENDER,TELEPHONE,USERADDRESS,DESCRIPTION,STATE,CREATEDATE,ISDELETE,MYPAGE,IPSTR from SYSUSER where 1 = 1 ");

        if(user.getRealName() != null && user.getRealName().equals("")){
            sb.append(" and REALNAME = '"+user.getRealName()+"'");
        }

        if(user.getIsDelete() != null){
            sb.append(" and ISDELETE = "+user.getIsDelete());
        }
        sb.append(" order by USERID");
        return sb.toString();
    }

    public String insertUserAndRole(Map map){
        String userId = (String)map.get("userId");
        String roleIds = (String)map.get("roleIds");
        String[] ids = roleIds.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into SYSUSERANDROLE (USERID,ROLEID) ");
        sb.append("VALUES ");
        for(int i = 0;i < ids.length;i++){
            if(i==0) sb.append("("+userId+","+ids[i]+")");
            else {
                sb.append(",");
                sb.append("(" + userId + "," + ids[i] + ")");
            }
        }
        return sb.toString();
    }






    public String updateUserById(Map map){
        StringBuilder sb = new StringBuilder();
        SysUserEntity user = (SysUserEntity) map.get("user");
        sb.append("update SYSUSER set ");

        if(user.getDeptId() != null){
            sb.append("DEPTID = "+user.getLoginName()+",");
        }

        if(user.getUserCode() != null && !user.getUserCode().equals("")){
            sb.append("LOGINNAME = '"+user.getLoginName()+"',");
        }

        if(user.getRealName() != null && !user.getRealName().equals("")){
            sb.append("REALNAME = '"+user.getRealName()+"',");
        }

        if(user.getTelephone() != null && !user.getTelephone().equals("")){
            sb.append("TELEPHONE = '"+user.getTelephone()+"',");
        }
        if(user.getGender() != null){
            sb.append("GENDER ="+user.getState()+",");
        }
        if(user.getState() != null){
            sb.append("STATE = "+user.getState()+",");
        }

        sb.append(" where USERID = "+user.getUserId());
        return sb.toString();
    }


    public String deleteUserById(Map map){

        StringBuilder sb = new StringBuilder();
        String userId = (String) map.get("userId");
        sb.append("delete from sys_user t where user_id = '"+userId+"' ");
        return sb.toString();
    }

}
