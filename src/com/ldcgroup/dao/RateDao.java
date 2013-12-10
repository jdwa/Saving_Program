package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Rate;

public interface RateDao {
	public Rate findById(Long id);
	public Rate findByYear(int year);
	public Rate add(Rate rate);
	public Rate delete(Long id);
	public Rate detail(Long id);
	public Rate update(Rate rate);
	public List<Rate> list();
}