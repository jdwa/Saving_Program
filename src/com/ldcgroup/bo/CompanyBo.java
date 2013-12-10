package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Company;

public interface CompanyBo {
	public Company findById(Long id);
	public Company findByNo(String no);
	public Company add(Company company);
	public Company delete(Long id);
	public List<Company> list();
	public Company detail(Long id);
	public Company update(Company company);
}
