package com.ldcgroup.bo;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.dao.TermDao;
import com.ldcgroup.model.Term;

public class TermBoImpl implements TermBo {
	
	private TermDao termDao;

	@Override
	public Term findById(Long id) {
		return termDao.findById(id);
	}

	@Override
	public Term findByNo(String no) {
		return termDao.findByNo(no);
	}

	//DI via Spring
	public void setTermDao(TermDao termDao) {
		this.termDao = termDao;
	}
	
	@Override
	@Transactional
	public Term add(Term term) {
		return termDao.add(term);
	}

	@Override
	@Transactional
	public Term delete(Long id) {
		return termDao.delete(id);
	}

	@Override
	public List<Term> list() {
		return termDao.list();
	}

	@Override
	public Term detail(Long id) {
		return termDao.detail(id);
	}

	@Override
	@Transactional
	public Term update(Term term) {
		return termDao.update(term);
	}
}
