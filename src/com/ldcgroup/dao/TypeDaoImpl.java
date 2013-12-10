package com.ldcgroup.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Type;

public class TypeDaoImpl extends HibernateDaoSupport implements TypeDao {

	@Override
	@Transactional(readOnly = true)
	public Type findById(Long id) {
		return (Type) getHibernateTemplate().get(Type.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Type findByNo(String no) {
		List<Type> list = getHibernateTemplate().find("from Type where type_no = ?", no);
		return ((list != null) && (list.size() > 0)) ? (Type) list.get(0) : null;
	}

	@Override
	@Transactional
	public Type add(Type type) {
		getHibernateTemplate().saveOrUpdate(type);
		return type;
	}

	@Override
	@Transactional
	public Type delete(Long id) {
		Type type = findById(id);
		getHibernateTemplate().delete(type);
		return type;
	}

	@Override
	@Transactional(readOnly = true)
	public Type detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Type update(Type type) {
		getHibernateTemplate().saveOrUpdate(type);
		return type;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Type> list() {
		return (List<Type>) getHibernateTemplate().find("from Type");
	}
}
