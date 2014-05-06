package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.RollDao;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Roll;

public class RollBoImpl implements RollBo {
	
	private RollDao rollDao;
	
	@Override
	public Roll findById(Long id) {
		return rollDao.findById(id);
	}

	@Override
	public Roll findByNo(String no) {
		return rollDao.findByNo(no);
	}

	//DI via Spring
	public void setRollDao(RollDao rollDao) {
		this.rollDao = rollDao;
	}
	
	@Override
	@Transactional
	public Roll add(Roll roll) {
		return rollDao.add(roll);
	}

	@Override
	@Transactional
	public Roll delete(Long id) {
		return rollDao.delete(id);
	}

	@Override
	public List<Roll> list() {
		return rollDao.list();
	}
	
	@Override
	public List<Roll> list(Company company) {
		return rollDao.list(company);
	}

	@Override
	public List<Roll> listValidate() {
		return rollDao.listValidate();
	}

	@Override
	public Roll detail(Long id) {
		return rollDao.detail(id);
	}

	@Override
	@Transactional
	public Roll update(Roll roll) {
		return rollDao.update(roll);
	}
}
