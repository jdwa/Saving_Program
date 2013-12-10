package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Type;

public interface TypeBo {
	public Type findById(Long id);
	public Type findByNo(String no);
	public Type add(Type type);
	public Type delete(Long id);
	public List<Type> list();
	public Type detail(Long id);
	public Type update(Type type);
}
