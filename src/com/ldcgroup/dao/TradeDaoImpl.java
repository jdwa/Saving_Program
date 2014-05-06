package com.ldcgroup.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.model.Company;
import com.ldcgroup.model.Trade;

public class TradeDaoImpl extends HibernateDaoSupport implements TradeDao {

	@Override
	@Transactional(readOnly = true)
	public Trade findById(Long id) {
		return (Trade) getHibernateTemplate().get(Trade.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Trade findByNo(String no) {
		List<Object> list = getHibernateTemplate().find("from Trade T where T.tx_no = '" + no + "'");
		return (list != null && list.size() > 0 && list.get(0) != null) ? (Trade) list.get(0) : null;
	}

	@Override
	@Transactional
	public Trade add(Trade trade) {
		getHibernateTemplate().saveOrUpdate(trade);
		return trade;
	}

	@Override
	@Transactional
	public Trade delete(Long id) {
		Trade trade = findById(id);
		getHibernateTemplate().delete(trade);
		return trade;
	}

	@Override
	@Transactional(readOnly = true)
	public Trade detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Trade update(Trade trade) {
		getHibernateTemplate().saveOrUpdate(trade);
		return trade;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Trade> list() {
		return (List<Trade>) getHibernateTemplate().find("from Trade");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Trade> list(Company company) {
		return (List<Trade>) getHibernateTemplate().find("from Trade T where T.company.id = ?", company.getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Trade> listValidate() {
		return (List<Trade>) getHibernateTemplate().find("from Trade T where T.valid = ?", Boolean.TRUE);
	}
}
