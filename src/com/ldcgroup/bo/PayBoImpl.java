package com.ldcgroup.bo;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.dao.PayDao;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Term;

public class PayBoImpl implements PayBo {
	
	private PayDao payDao;

	@Override
	public Pay findById(Long id) {
		return payDao.findById(id);
	}

	//DI via Spring
	public void setPayDao(PayDao payDao) {
		this.payDao = payDao;
	}
	
	@Override
	@Transactional
	public Pay add(Pay pay) {
		return payDao.add(pay);
	}

	@Override
	@Transactional
	public Pay delete(Long id) {
		return payDao.delete(id);
	}

	@Override
	public Pay detail(Long id) {
		return payDao.detail(id);
	}

	@Override
	@Transactional
	public Pay update(Pay pay) {
		return payDao.update(pay);
	}

	@Override
	public List<Pay> list() {
		return payDao.list();
	}
	
	@Override
	public List<Pay> list(Member member) {
		return payDao.list(member);
	}

	@Override
	public List<Pay> list(Roll roll) {
		return payDao.list(roll);
	}

	@Override
	public List<Pay> list(Roll roll, Member member) {
		return payDao.list(roll, member);
	}
	
	@Override
	public List<Pay> list(Roll roll, Term term) {
		return payDao.list(roll, term);
	}

	@Override
	public List<Pay> list(Term term) {
		return payDao.list(term);
	}

	@Override
	public double getPaySum() {
		return payDao.getPaySum();
	}

	@Override
	public double getPaySum(Member member) {
		return payDao.getPaySum(member);
	}

	@Override
	public double getPaySum(Roll roll) {
		return payDao.getPaySum(roll);
	}

	@Override
	public double getPaySum(Roll roll, Member member) {
		return payDao.getPaySum(roll, member);
	}
	
	@Override
	public double getPaySum(Roll roll, Term term) {
		return payDao.getPaySum(roll, term);
	}

	@Override
	public double getPaySum(Term term) {
		return payDao.getPaySum(term);
	}

}
