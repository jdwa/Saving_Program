package com.ldcgroup.dao;

import java.util.List;

import com.ldcgroup.model.Company;
import com.ldcgroup.model.Roll;

public interface RollDao {
	public Roll findById(Long id);
	public Roll findByNo(String no);
	public Roll add(Roll roll);
	public Roll delete(Long id);
	public Roll detail(Long id);
	public Roll update(Roll roll);
	public List<Roll> list();
	public List<Roll> list(Company company);
	public List<Roll> listValidate();
}
