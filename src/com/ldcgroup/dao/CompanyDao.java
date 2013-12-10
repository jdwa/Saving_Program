package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Company;

public interface CompanyDao {
	public Company findById(Long id);
	public Company findByNo(String no);
	public Company add(Company company);
	public Company delete(Long id);
	public Company detail(Long id);
	public Company update(Company company);
	public List<Company> list();
}
