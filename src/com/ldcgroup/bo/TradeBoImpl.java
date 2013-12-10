package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.TradeDao;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Trade;

public class TradeBoImpl implements TradeBo {
	
	private TradeDao tradeDao;
	
	@Override
	public Trade findById(Long id) {
		return tradeDao.findById(id);
	}

	@Override
	public Trade findByNo(String no) {
		return tradeDao.findByNo(no);
	}

	//DI via Spring
	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}
	
	@Override
	@Transactional
	public Trade add(Trade trade) {
		return tradeDao.add(trade);
	}

	@Override
	@Transactional
	public Trade delete(Long id) {
		return tradeDao.delete(id);
	}

	@Override
	public List<Trade> list() {
		return tradeDao.list();
	}
	
	@Override
	public List<Trade> list(Company company) {
		return tradeDao.list(company);
	}
	
	@Override
	public List<Trade> listValidate() {
		return tradeDao.listValidate();
	}

	@Override
	public Trade detail(Long id) {
		return tradeDao.detail(id);
	}

	@Override
	@Transactional
	public Trade update(Trade trade) {
		return tradeDao.update(trade);
	}
}
