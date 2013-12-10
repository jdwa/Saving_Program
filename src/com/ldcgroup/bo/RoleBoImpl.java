package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.RoleDao;
import com.ldcgroup.model.Role;

public class RoleBoImpl implements RoleBo {
	
	private RoleDao roleDao;

	@Override
	public Role findById(Long id) {
		return roleDao.findById(id);
	}

	@Override
	public Role findByCode(String code) {
		return roleDao.findByCode(code);
	}

	//DI via Spring
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	@Override
	@Transactional
	public Role add(Role role) {
		return roleDao.add(role);
	}

	@Override
	@Transactional
	public Role delete(Long id) {
		return roleDao.delete(id);
	}

	@Override
	public List<Role> list() {
		return roleDao.list();
	}

	@Override
	public Role detail(Long id) {
		return roleDao.detail(id);
	}

	@Override
	@Transactional
	public Role update(Role role) {
		return roleDao.update(role);
	}
}
