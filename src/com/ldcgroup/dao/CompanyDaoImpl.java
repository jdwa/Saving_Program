package com.ldcgroup.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Company;

public class CompanyDaoImpl extends HibernateDaoSupport implements CompanyDao {

	@Override
	@Transactional
	public Company findById(Long id) {
		return (Company) getHibernateTemplate().get(Company.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Company findByNo(String no) {
		List<Company> list = getHibernateTemplate().find("from Company where cmp_no = ?", no);
		return ((list != null) && (list.size() > 0)) ? (Company) list.get(0) : null;
	}

	@Override
	@Transactional
	public Company add(Company company) {
		getHibernateTemplate().saveOrUpdate(company);
		return company;
	}

	@Override
	@Transactional
	public Company delete(Long id) {
		Company company = findById(id);
		getHibernateTemplate().delete(company);
		return company;
	}

	@Override
	@Transactional(readOnly = true)
	public Company detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Company update(Company company) {
		getHibernateTemplate().saveOrUpdate(company);
		return company;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Company> list() {
		return (List<Company>) getHibernateTemplate().find("from Company");
	}
}
