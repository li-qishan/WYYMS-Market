package com.buf.sys.controller;

import com.buf.common.utils.Constant;
import com.buf.common.utils.JsonMapper;
import com.buf.common.utils.R;
import com.buf.sys.entity.SysDeptEntity;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.stream.Collectors.toList;


/**
 * 部门管理
 *
 *
 *
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
	@Autowired
	private SysDeptService sysDeptService;


	/**
	 * 得到部门节点信息
	 */
	@RequestMapping("/getDepart")
	@ResponseBody
	public Object getDepart(HttpServletRequest request){



		if(!StringUtils.isBlank(request.getParameter("deptId")))
		{
			System.out.println(request.getParameter("deptId").toString());
		}

		SysUserEntity currUser = getUser();

		List<SysDeptEntity> deptList = sysDeptService.getDepart(currUser);

		for(SysDeptEntity sd:deptList)
		{
			System.out.println(sd.getDeptName());
		}


		Boolean isHome =false;
		if(!StringUtils.isBlank(request.getParameter("isHome")))
		{
			isHome = request.getParameter("isHome").trim().equals("1");
		}
		Boolean isUpdate =false;
		if(!StringUtils.isBlank(request.getParameter("isUpdate")))
		{
			isHome = request.getParameter("isUpdate").trim().equals("0");
		}

		if(isHome)
		{
			Long removeDeptid = Long.valueOf(request.getParameter("deptId"));

			System.out.println(removeDeptid);

			List<SysDeptEntity> removeList=deptList.stream().filter(m->m.getDeptId().equals(removeDeptid)).collect(toList());
			SysDeptEntity removeDept = null;
			if(removeList.size()>0) removeDept = removeList.get(0);
			if(isUpdate)
			{
				DelSonDeptId(deptList,removeDeptid);
			}
			deptList.remove(removeDept);


			List<SysDeptEntity> rootFunList = new ArrayList<SysDeptEntity>();

			SysDeptEntity root = new SysDeptEntity();
			root.setDeptId(0L);
			root.setDeptCode("0");
			root.setDeptName("根节点");
			root.setChildren(deptList);

			rootFunList.add(root);
			return rootFunList;
		}
		return deptList;
	}

	private void DelSonDeptId(List<SysDeptEntity> deptList, Long sonid)
	{
		for (SysDeptEntity item:deptList)
		{
			if (item.getDeptId() == sonid)
			{
				deptList.remove(item);
				return;
			}
			else
			{
				DelSonDeptId(item.getChildren(), sonid);
			}
		}
	}
	@RequestMapping("/modifyDept")
	@ResponseBody
	public Object modifyDept(HttpServletRequest request)
	{
		Long deptId = Long.valueOf(request.getParameter("deptId"));
		SysDeptEntity de = sysDeptService.getDepById(deptId);
		System.out.println(de.getDeptName());
		return de;
	}


	@RequestMapping("/saveDept")
	@ResponseBody
	public Object saveDept(SysDeptEntity sde)
	{
		if(sde.getDeptId()==null)
		{
			System.out.println(sde.getDeptName());

			System.out.println(JsonMapper.toJson(sde));
			List<SysDeptEntity> list = sysDeptService.getDepByName(sde.getDeptName());
			System.out.println(list.size());
			if (list.size()>0)
			{
				return "2";
			}
			Long depId = sysDeptService.getMaxDepartId()+1;

			System.out.println(depId);
			sde.setDeptId(depId);
			sde.setOrderNo(0);
			sde.setDepartMentLevel(0);
			sysDeptService.saveDept(sde);
			return "保存部门【"+sde.getDeptName()+"】成功！";
		}
		else
		{
			List<SysDeptEntity> list = sysDeptService.validateDepByName(sde.getDeptName(),sde.getDeptId());
			if(list.size()>0)
			{
				return "2";
			}
			sysDeptService.updateDept(sde);

			return "修改部门【"+sde.getDeptName()+"】成功！";

		}
	}
	@RequestMapping("/deleteDept")
	@ResponseBody
	public Object deleteDept(HttpServletRequest request)
	{
		String result="";
		try
		{
			Long deptId = Long.valueOf(request.getParameter("Ids"));
			List<SysDeptEntity> subList = sysDeptService.getSubDepById(deptId);
			SysDeptEntity dept = sysDeptService.getDepById(deptId);

			if (subList.size() > 0)
			{
				result = "此部门下已有所属子部门，不允许删除！";
			}
			else if (1==2)//delDept.SysUserList.Count > 0)
			{
				result = "此部门下已有人员绑定，不允许删除！";
			}
			else
			{
				sysDeptService.deleteDept(deptId);
				result = "删除部门【"+dept.getDeptName()+"】成功!";
			}
		}
		catch (Exception ex)
		{
			result = "9";
		}
		return result;
	}

	/**
	 * 获了所有部门及子部门信息
	 */
	@RequestMapping("/getAllDepart")
	@ResponseBody
	public Object getAllDepart(HttpServletRequest request){
		SysUserEntity currUser = getUser();
		List<SysDeptEntity> deptList = sysDeptService.getDepart(currUser);
		return deptList;
	}

}
