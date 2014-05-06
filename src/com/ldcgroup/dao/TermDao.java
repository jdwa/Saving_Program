package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Term;

public interface TermDao {
	public Term findById(Long id);
	public Term findByNo(String no);
	public Term add(Term term);
	public Term delete(Long id);
	public Term detail(Long id);
	public Term update(Term term);
	public List<Term> list();
}
