package com.ldcgroup.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Role;

public class RoleDaoImpl extends HibernateDaoSupport implements RoleDao {

	@Override
	@Transactional(readOnly = true)
	public Role findById(Long id) {
		return (Role) getHibernateTemplate().get(Role.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Role findByCode(String code) {
		List<Role> list = getHibernateTemplate().find("from Role where role_code = ?", code);
		return ((list != null) && (list.size() > 0)) ? (Role) list.get(0) : null;
	}

	@Override
	@Transactional
	public Role add(Role role) {
		getHibernateTemplate().saveOrUpdate(role);
		return role;
	}

	@Override
	@Transactional
	public Role delete(Long id) {
		Role role = findById(id);
		getHibernateTemplate().delete(role);
		return role;
	}

	@Override
	@Transactional(readOnly = true)
	public Role detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Role update(Role role) {
		getHibernateTemplate().saveOrUpdate(role);
		return role;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Role> list() {
		return (List<Role>) getHibernateTemplate().find("from Role");
	}
}
