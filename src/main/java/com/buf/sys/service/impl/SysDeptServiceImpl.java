package com.buf.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.buf.common.annotation.DataFilter;
import com.buf.common.config.Global;
import com.buf.common.utils.Constant;
import com.buf.sys.dao.SysDeptDao;
import com.buf.sys.entity.SysDeptEntity;
import com.buf.sys.entity.SysUserEntity;
import com.buf.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

	@Autowired
	private SysDeptDao sysDeptDao;


	public List<SysDeptEntity> getDepart(SysUserEntity loginUser)
	{
		List<SysDeptEntity> deptList = new ArrayList<SysDeptEntity>();

		List<SysDeptEntity> deptsAll = sysDeptDao.getDepartAll();
		InitDeptTrees(deptsAll,deptList,loginUser);
		return deptList;
	}

	/*初始化菜单树*/
	private void InitDeptTrees(List<SysDeptEntity> deptsAll,List<SysDeptEntity> deptsReturn,SysUserEntity loginUser)
	{
		List<SysDeptEntity> DeptsRecursion = new ArrayList<SysDeptEntity>();
		DeptsRecursion = deptsAll;

		List<SysDeptEntity> listRoot = new ArrayList<SysDeptEntity>();

		if(loginUser.getRoleStr().contains(Global.ROLESYSTEMMANAGER))
		{
			listRoot=DeptsRecursion.stream().filter(m->m.getParentDeptId().equals(0L))
					.sorted(Comparator.comparing(SysDeptEntity::getOrderNo)).collect(toList());
		}
		else
		{
			listRoot=DeptsRecursion.stream().filter(m->m.getDeptId().equals(loginUser.getDeptId()))
					.sorted(Comparator.comparing(SysDeptEntity::getOrderNo)).collect(toList());
		}

		for(SysDeptEntity dept:listRoot)
		{
			if(!deptsReturn.contains(dept)) {
				deptsReturn.add(dept);
				DeptsRecursion.remove(dept);
				InitDeptTreesSub(dept, DeptsRecursion,deptsReturn);
			}
		}
	}
	/*初始化菜单子项*/
	private  void InitDeptTreesSub(SysDeptEntity sysdept, List<SysDeptEntity> DeptsRecursion,List<SysDeptEntity> deptsReturn)
	{
		List<SysDeptEntity> deptsSub = DeptsRecursion.stream().filter(menusRecursion->menusRecursion.getParentDeptId().equals(sysdept.getDeptId()))
				.sorted(Comparator.comparing(SysDeptEntity::getOrderNo)).collect(toList());
		for (SysDeptEntity dept : deptsSub)
		{
			sysdept.getChildren().add(dept);
			DeptsRecursion.remove(dept);
			if (IsHasChildren(dept, DeptsRecursion))
			{
				InitDeptTreesSub(dept, DeptsRecursion,deptsReturn);
			}

		}
	}
	/*判断菜单项是否有子项*/
	private Boolean IsHasChildren(SysDeptEntity sysdept, List<SysDeptEntity> waitmenulist)
	{
		int ncount = waitmenulist.stream().filter(m->m.getParentDeptId().equals(sysdept.getDeptId()))
				.collect(toList()).size();
		if (ncount > 0)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	/*通过部门名称查找部门信息*/
	public List<SysDeptEntity> getDepByName(String departName)
	{
		return sysDeptDao.getDepByName(departName);
	}
	/*验证部门名称是否重复*/
	public List<SysDeptEntity> validateDepByName(String departName,Long departId)
	{
		return sysDeptDao.validateDepByName(departName,departId);
	}

	public SysDeptEntity getDepById(Long departId)
	{
		return sysDeptDao.getDepById(departId);
	}

	public List<SysDeptEntity> getSubDepById(Long departId)
	{
		return sysDeptDao.getSubDepById(departId);
	}

	public Long getMaxDepartId()
	{
		return sysDeptDao.getMaxDepartId();
	}

	public void saveDept(SysDeptEntity dept)
	{
		sysDeptDao.saveDept(dept);
	}

	public void updateDept(SysDeptEntity dept)
	{
		sysDeptDao.updateDept(dept);
	}

	public void deleteDept(Long departId)
	{
		sysDeptDao.deleteDept(departId);
	}



}
