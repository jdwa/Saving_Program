package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Rate;

public interface RateBo {
	public Rate findById(Long id);
	public Rate findByYear(int year);
	public Rate add(Rate rate);
	public Rate delete(Long id);
	public List<Rate> list();
	public Rate detail(Long id);
	public Rate update(Rate rate);
}
