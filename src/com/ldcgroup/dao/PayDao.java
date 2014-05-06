package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Term;

public interface PayDao {
	public Pay findById(Long id);
	public Pay add(Pay pay);
	public Pay delete(Long id);
	public Pay detail(Long id);
	public Pay update(Pay pay);
	public List<Pay> list();
	public List<Pay> list(Member member);
	public List<Pay> list(Roll roll);
	public List<Pay> list(Roll roll, Member member);
	public List<Pay> list(Roll roll, Term term);
	public List<Pay> list(Term term);
	public double getPaySum();
	public double getPaySum(Member member);
	public double getPaySum(Roll roll);
	public double getPaySum(Roll roll, Member member);
	public double getPaySum(Roll roll, Term term);
	public double getPaySum(Term term);
}