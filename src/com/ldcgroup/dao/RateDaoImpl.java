package com.ldcgroup.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.model.Rate;

public class RateDaoImpl extends HibernateDaoSupport implements RateDao {

	@Override
	@Transactional(readOnly = true)
	public Rate findById(Long id) {
		return (Rate) getHibernateTemplate().get(Rate.class, id);
	}

	@Override
	@Transactional
	public Rate add(Rate rate) {
		getHibernateTemplate().saveOrUpdate(rate);
		return rate;
	}

	@Override
	@Transactional
	public Rate delete(Long id) {
		Rate rate = findById(id);
		getHibernateTemplate().delete(rate);
		return rate;
	}

	@Override
	@Transactional(readOnly = true)
	public Rate detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Rate update(Rate rate) {
		getHibernateTemplate().saveOrUpdate(rate);
		return rate;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Rate> list() {
		return (List<Rate>) getHibernateTemplate().find("from Rate");
	}

	@Override
	@Transactional(readOnly = true)
	public Rate findByYear(int year) {
		List<Rate> list = list();
		Rate returnValue = null;
		for (int i = 0; i < list.size(); i++) {
			Rate rate = (Rate)list.get(i);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(rate.getRate_date());
			if (calendar.get(Calendar.YEAR) == year) {
				returnValue = rate;
				break;
			}
		}
		return returnValue;
	}
}
