package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Category;

public interface CategoryDao {
	public Category findById(Long id);
	public Category findByNo(String no);
	public Category add(Category category);
	public Category delete(Long id);
	public Category detail(Long id);
	public Category update(Category category);
	public List<Category> list();
}
