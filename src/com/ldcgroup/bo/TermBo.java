package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Term;

public interface TermBo {
	public Term findById(Long id);
	public Term findByNo(String no);
	public Term add(Term term);
	public Term delete(Long id);
	public List<Term> list();
	public Term detail(Long id);
	public Term update(Term term);
}
