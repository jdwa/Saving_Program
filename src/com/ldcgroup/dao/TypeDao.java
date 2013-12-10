package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Type;

public interface TypeDao {
	public Type findById(Long id);
	public Type findByNo(String no);
	public Type add(Type type);
	public Type delete(Long id);
	public Type detail(Long id);
	public Type update(Type type);
	public List<Type> list();
}
