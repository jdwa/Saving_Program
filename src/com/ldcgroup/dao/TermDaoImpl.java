package com.ldcgroup.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Term;

public class TermDaoImpl extends HibernateDaoSupport implements TermDao {

	@Override
	@Transactional(readOnly = true)
	public Term findById(Long id) {
		return (Term) getHibernateTemplate().get(Term.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public Term findByNo(String no) {
		List<Term> list = getHibernateTemplate().find("from Term where term_no = ?", no);
		return ((list != null) && (list.size() > 0)) ? (Term) list.get(0) : null;
	}

	@Override
	@Transactional
	public Term add(Term term) {
		getHibernateTemplate().saveOrUpdate(term);
		return term;
	}

	@Override
	@Transactional
	public Term delete(Long id) {
		Term term = findById(id);
		getHibernateTemplate().delete(term);
		return term;
	}

	@Override
	@Transactional(readOnly = true)
	public Term detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Term update(Term term) {
		getHibernateTemplate().saveOrUpdate(term);
		return term;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Term> list() {
		return (List<Term>) getHibernateTemplate().find("from Term");
	}
}
