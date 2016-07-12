package com.ldcgroup.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.model.Company;
import com.ldcgroup.model.Roll;

public class RollDaoImpl extends HibernateDaoSupport implements RollDao {

	@Override
	@Transactional(readOnly = true)
	public Roll findById(Long id) {
		return (Roll) getHibernateTemplate().get(Roll.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Roll findByNo(String no) {
		List<Object> list = getHibernateTemplate().find("from Roll R where R.ro_no = '" + no + "'");
		return (list != null && list.size() > 0 && list.get(0) != null) ? (Roll) list.get(0) : null;
	}

	@Override
	@Transactional
	public Roll add(Roll roll) {
		getHibernateTemplate().saveOrUpdate(roll);
		return roll;
	}

	@Override
	@Transactional
	public Roll delete(Long id) {
		Roll roll = findById(id);
		getHibernateTemplate().delete(roll);
		return roll;
	}

	@Override
	@Transactional(readOnly = true)
	public Roll detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Roll update(Roll roll) {
		getHibernateTemplate().saveOrUpdate(roll);
		return roll;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Roll> list() {
		return (List<Roll>) getHibernateTemplate().find("from Roll R order by R.settlement_date Desc");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Roll> list(Company company) {
		return (List<Roll>) getHibernateTemplate().find("from Roll R where R.company.id = ? order by R.settlement_date Desc", company.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Roll> listValidate() {
		return (List<Roll>) getHibernateTemplate().find("from Roll R where R.valid = ? order by R.settlement_date Desc", Boolean.TRUE);
	}
}
