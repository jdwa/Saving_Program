package com.ldcgroup.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;

public class StatementDaoImpl extends HibernateDaoSupport implements StatementDao {

	@Override
	@Transactional(readOnly = true)
	public Statement findById(Long id) {
		return (Statement) getHibernateTemplate().get(Statement.class, id);
	}

	@Override
	@Transactional
	public Statement add(Statement statement) {
		getHibernateTemplate().saveOrUpdate(statement);
		return statement;
	}

	@Override
	@Transactional
	public Statement delete(Long id) {
		Statement statement = findById(id);
		getHibernateTemplate().delete(statement);
		return statement;
	}

	@Override
	@Transactional(readOnly = true)
	public Statement detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Statement update(Statement statement) {
		getHibernateTemplate().saveOrUpdate(statement);
		return statement;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Statement> list() {
		return (List<Statement>) getHibernateTemplate().find("from Statement");
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Statement> list(Member member) {
		return (List<Statement>) getHibernateTemplate().find("from Statement S where S.member.id = ?", member.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Statement> list(Trade trade) {
		return (List<Statement>) getHibernateTemplate().find("from Statement S where S.trade.id = ?", trade.getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Statement> list(Trade trade, Type type) {
		return (List<Statement>) getHibernateTemplate().find("from Statement S where S.trade.id = ? and S.type.id = ?", trade.getId(), type.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Statement> list(Type type) {
		return (List<Statement>) getHibernateTemplate().find("from Statement S where S.type.id = ?", type.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getStatementSum() {
		final String queryString = "select sum(fund) from Statement";
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
	public double getStatementSum(Member member) {
		final String queryString = "select sum(fund) from Statement where Member = '" + member.getId() + "'";
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
	public double getStatementSum(Trade trade) {
		final String queryString = "select sum(fund) from Statement where Trade = '" + trade.getId() + "'";
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
	public double getStatementSum(Trade trade, Type type) {
		final String queryString = "select sum(fund) from Statement where Trade = '" + trade.getId() + "' and Type ='" + type.getId() + "'";
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
	public double getStatementSum(Type type) {
		final String queryString = "select sum(fund) from Statement where Type = '" + type.getId() + "'";
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
