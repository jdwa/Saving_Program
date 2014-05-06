package com.ldcgroup.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.model.Company;
import com.ldcgroup.model.Task;

public class TaskDaoImpl extends HibernateDaoSupport implements TaskDao {

	@Override
	@Transactional(readOnly = true)
	public Task findById(Long id) {
		return (Task) getHibernateTemplate().get(Task.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Task findByNo(String no) {
		List<Object> list = getHibernateTemplate().find("from Task T where T.tk_no = '" + no + "'");
		return (list != null && list.size() > 0 && list.get(0) != null) ? (Task) list.get(0) : null;
	}

	@Override
	@Transactional
	public Task add(Task task) {
		getHibernateTemplate().saveOrUpdate(task);
		return task;
	}

	@Override
	@Transactional
	public Task delete(Long id) {
		Task task = findById(id);
		getHibernateTemplate().delete(task);
		return task;
	}

	@Override
	@Transactional(readOnly = true)
	public Task detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Task update(Task task) {
		getHibernateTemplate().saveOrUpdate(task);
		return task;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Task> list() {
		return (List<Task>) getHibernateTemplate().find("from Task");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Task> list(Company company) {
		return (List<Task>) getHibernateTemplate().find("from Task T where T.company.id = ?", company.getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Task> listValidate() {
		return (List<Task>) getHibernateTemplate().find("from Task T where T.valid = ?", Boolean.TRUE);
	}
}
