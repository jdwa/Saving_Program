package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.CompanyDao;
import com.ldcgroup.model.Company;

public class CompanyBoImpl implements CompanyBo {
	
	private CompanyDao companyDao;

	@Override
	public Company findById(Long id) {
		return companyDao.findById(id);
	}

	@Override
	public Company findByNo(String no) {
		return companyDao.findByNo(no);
	}

	//DI via Spring
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	@Override
	@Transactional
	public Company add(Company company) {
		return companyDao.add(company);
	}

	@Override
	@Transactional
	public Company delete(Long id) {
		return companyDao.delete(id);
	}

	@Override
	public List<Company> list() {
		return companyDao.list();
	}

	@Override
	public Company detail(Long id) {
		return companyDao.detail(id);
	}

	@Override
	@Transactional
	public Company update(Company company) {
		return companyDao.update(company);
	}
}
