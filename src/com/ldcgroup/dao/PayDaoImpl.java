package com.ldcgroup.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Term;

public class PayDaoImpl extends HibernateDaoSupport implements PayDao {

	@Override
	@Transactional(readOnly = true)
	public Pay findById(Long id) {
		return (Pay) getHibernateTemplate().get(Pay.class, id);
	}

	@Override
	@Transactional
	public Pay add(Pay pay) {
		getHibernateTemplate().saveOrUpdate(pay);
		return pay;
	}

	@Override
	@Transactional
	public Pay delete(Long id) {
		Pay pay = findById(id);
		getHibernateTemplate().delete(pay);
		return pay;
	}

	@Override
	@Transactional(readOnly = true)
	public Pay detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Pay update(Pay pay) {
		getHibernateTemplate().saveOrUpdate(pay);
		return pay;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Pay> list() {
		return (List<Pay>) getHibernateTemplate().find("from Pay P order by P.settlement_date Desc");
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Pay> list(Member member) {
		return (List<Pay>) getHibernateTemplate().find("from Pay P where P.member.id = ? order by P.settlement_date Desc", member.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Pay> list(Roll roll) {
		return (List<Pay>) getHibernateTemplate().find("from Pay P where P.roll.id = ? order by P.settlement_date Desc", roll.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Pay> list(Roll roll, Member member) {
		return (List<Pay>) getHibernateTemplate().find("from Pay P where P.roll.id = ? and P.member.id = ? order by P.settlement_date Desc", roll.getId(), member.getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Pay> list(Roll roll, Term term) {
		return (List<Pay>) getHibernateTemplate().find("from Pay P where P.roll.id = ? and P.term.id = ? order by P.settlement_date Desc", roll.getId(), term.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Pay> list(Term term) {
		return (List<Pay>) getHibernateTemplate().find("from Pay P where P.term.id = ? order by P.settlement_date Desc", term.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getPaySum() {
		final String queryString = "select sum(value) from Pay";
		Double sum = (Double) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createSQLQuery(queryString);
				List<Object> list = query.list();
				return (list != null && list.size() > 0 && list.get(0) != null) ? list.get(0) : new Double(0);
			}
		});
		return sum.doubleValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getPaySum(Member member) {
		final String queryString = "select sum(value) from Pay where Member = '" + member.getId() + "'";
		Double sum = (Double) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createSQLQuery(queryString);
				List<Object> list = query.list();
				return (list != null && list.size() > 0 && list.get(0) != null) ? list.get(0) : new Double(0);
			}
		});
		return sum.doubleValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getPaySum(Roll roll) {
		final String queryString = "select sum(value) from Pay where Roll = '" + roll.getId() + "'";
		Double sum = (Double) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createSQLQuery(queryString);
				List<Object> list = query.list();
				return (list != null && list.size() > 0 && list.get(0) != null) ? list.get(0) : new Double(0);
			}
		});
		return sum.doubleValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getPaySum(Roll roll, Member member) {
		final String queryString = "select sum(value) from Pay where Roll = '" + roll.getId() + "' and Member ='" + member.getId() + "'";
		Double sum = (Double) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createSQLQuery(queryString);
				List<Object> list = query.list();
				return (list != null && list.size() > 0 && list.get(0) != null) ? list.get(0) : new Double(0);
			}
		});
		return sum.doubleValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getPaySum(Roll roll, Term term) {
		final String queryString = "select sum(value) from Pay where Roll = '" + roll.getId() + "' and Term ='" + term.getId() + "'";
		Double sum = (Double) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createSQLQuery(queryString);
				List<Object> list = query.list();
				return (list != null && list.size() > 0 && list.get(0) != null) ? list.get(0) : new Double(0);
			}
		});
		return sum.doubleValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getPaySum(Term term) {
		final String queryString = "select sum(value) from Pay where Term = '" + term.getId() + "'";
		Double sum = (Double) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createSQLQuery(queryString);
				List<Object> list = query.list();
				return (list != null && list.size() > 0 && list.get(0) != null) ? list.get(0) : new Double(0);
			}
		});
		return sum.doubleValue();
	}

}
