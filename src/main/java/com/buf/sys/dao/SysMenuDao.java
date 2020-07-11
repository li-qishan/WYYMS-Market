/**
*Copyright 2018 人人开源 http://www.renren.io
*<p>
*Licensed under the Apache License, Version 2.0 (the "License"); you may not
*use this file except in compliance with the License. You may obtain a copy of
*the License at
*<p>
*http://www.apache.org/licenses/LICENSE-2.0
*<p>
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
*WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
*License for the specific language governing permissions and limitations under
*the License.
 */

package com.buf.sys.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.buf.sys.entity.SysMenu;
import com.buf.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
*菜单管理
*
*@author andele modify
*
*@date 2018年11月10日 上午9:33:01
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {


	@Select("SELECT c.* from SYSMENU c where 1 = 1 order by c.ORDERNO ")
	public List<SysMenu> getAllMenus();

	@SelectProvider(type = SQLProvider.class,method = "getMenusByCondition")
	public List<SysMenu> getMenusByCondition(@Param("menu")SysMenu menu);


	@Select("SELECT DISTINCT c.* from SYSUSER t,SYSUSERANDROLE a,SYSROLEANDMENU b,SYSMENU c "+
	"WHERE t.USERID = a.USERID and a.ROLEID = b.ROLEID and b.MENUID = c.MENUID and c.MENUATTRIBUTE = 0 AND t.USERID = #{userid} order by c.ORDERNO ")
	public List<SysMenu> getUserMenu(@Param("userid")Long userid);







	@Select("SELECT DISTINCT b.PARENT_ID,b.NAME,b.PERMS,b.ICON,b.MENU_ID " +
			"FROM SYS_ROLE_MENU a LEFT JOIN SYS_MENU b on a.MENU_ID = b.MENU_ID "  +
			"WHERE a.ROLE_ID in (SELECT c.ROLE_ID FROM SYS_USER_ROLE c WHERE c.USER_ID = #{userid} ) AND b.PARENT_ID = '0' ORDER BY b.MENU_ID")
	public List<SysMenu> getAuthMenu(@Param("userid")String userid);

	@Select("SELECT DISTINCT b.*，b.MENU_ID id, b.NAME text " +
			"FROM SYS_ROLE_MENU a LEFT JOIN SYS_MENU b on a.MENU_ID = b.MENU_ID "  +
			"WHERE a.ROLE_ID in (SELECT c.ROLE_ID FROM SYS_USER_ROLE c WHERE c.USER_ID = #{userid} ) AND b.PARENT_ID = #{menu_id} ORDER BY b.ORDER_NUM")
	public List<SysMenu> getAuthChildren(@Param("userid")String userid,@Param("menu_id") String menu_id);


	/**
	*根据父菜单，查询子菜单
	*@param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(String parentId);
	
	/**
	*获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

//	@Select("select t.menu_id,t.parent_id,t.url,t.name,t.perms,t.type,t.icon,t.order_num from sys_menu t ")
	List<SysMenuEntity> selectMenuList();

//	@Select("select t.menu_id,t.parent_id,t.url,t.name,t.perms,t.type,t.icon,t.order_num from sys_menu t where t.menu_id = #{menuId}")
	SysMenuEntity selectMenuById(@Param("menuId")Long menuId);

//	@InsertProvider(type = SQLProvider.class,method = "insertMenu")
//	void insertMenu(@Param("menu")SysMenuEntity menu);

}
