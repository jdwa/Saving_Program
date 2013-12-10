package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.RateDao;
import com.ldcgroup.model.Rate;

public class RateBoImpl implements RateBo {
	
	private RateDao rateDao;

	@Override
	public Rate findById(Long id) {
		return rateDao.findById(id);
	}

	@Override
	public Rate findByYear(int year) {
		return rateDao.findByYear(year);
	}

	//DI via Spring
	public void setRateDao(RateDao rateDao) {
		this.rateDao = rateDao;
	}
	
	@Override
	@Transactional
	public Rate add(Rate rate) {
		return rateDao.add(rate);
	}

	@Override
	@Transactional
	public Rate delete(Long id) {
		return rateDao.delete(id);
	}

	@Override
	public List<Rate> list() {
		return rateDao.list();
	}

	@Override
	public Rate detail(Long id) {
		return rateDao.detail(id);
	}

	@Override
	@Transactional
	public Rate update(Rate rate) {
		return rateDao.update(rate);
	}
}
