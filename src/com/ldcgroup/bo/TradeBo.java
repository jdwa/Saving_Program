package com.ldcgroup.bo;

import java.util.List;

import com.ldcgroup.model.Company;
import com.ldcgroup.model.Trade;

public interface TradeBo {
	public Trade findById(Long id);
	public Trade findByNo(String no);
	public Trade add(Trade trade);
	public Trade delete(Long id);
	public List<Trade> list();
	public List<Trade> list(Company company);
	public List<Trade> listValidate();
	public Trade detail(Long id);
	public Trade update(Trade trade);
}
