package com.ldcgroup.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;

public class PointDaoImpl extends HibernateDaoSupport implements PointDao {

	@Override
	@Transactional(readOnly = true)
	public Point findById(Long id) {
		return (Point) getHibernateTemplate().get(Point.class, id);
	}

	@Override
	@Transactional
	public Point add(Point point) {
		getHibernateTemplate().saveOrUpdate(point);
		return point;
	}

	@Override
	@Transactional
	public Point delete(Long id) {
		Point point = findById(id);
		getHibernateTemplate().delete(point);
		return point;
	}

	@Override
	@Transactional(readOnly = true)
	public Point detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Point update(Point point) {
		getHibernateTemplate().saveOrUpdate(point);
		return point;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Point> list() {
		return (List<Point>) getHibernateTemplate().find("from Point");
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Point> list(Member member) {
		return (List<Point>) getHibernateTemplate().find("from Point S where S.member.id = ?", member.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Point> list(Task task) {
		return (List<Point>) getHibernateTemplate().find("from Point S where S.task.id = ?", task.getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Point> list(Task task, Item item) {
		return (List<Point>) getHibernateTemplate().find("from Point S where S.task.id = ? and S.item.id = ?", task.getId(), item.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Point> list(Item item) {
		return (List<Point>) getHibernateTemplate().find("from Point S where S.item.id = ?", item.getId());
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public double getPointSum() {
		final String queryString = "select sum(value) from Point";
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
	public double getPointSum(Member member) {
		final String queryString = "select sum(value) from Point where Member = '" + member.getId() + "'";
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
	public double getPointSum(Task task) {
		final String queryString = "select sum(value) from Point where Task = '" + task.getId() + "'";
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
	public double getPointSum(Task task, Item item) {
		final String queryString = "select sum(value) from Point where Task = '" + task.getId() + "' and Item ='" + item.getId() + "'";
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
	public double getPointSum(Item item) {
		final String queryString = "select sum(value) from Point where Item = '" + item.getId() + "'";
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
