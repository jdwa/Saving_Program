package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Category;

public interface CategoryBo {
	public Category findById(Long id);
	public Category findByNo(String no);
	public Category add(Category category);
	public Category delete(Long id);
	public List<Category> list();
	public Category detail(Long id);
	public Category update(Category category);
}
