package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.CategoryDao;
import com.ldcgroup.model.Category;

public class CategoryBoImpl implements CategoryBo {
	
	private CategoryDao categoryDao;

	@Override
	public Category findById(Long id) {
		return categoryDao.findById(id);
	}

	@Override
	public Category findByNo(String no) {
		return categoryDao.findByNo(no);
	}

	//DI via Spring
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	@Override
	@Transactional
	public Category add(Category category) {
		return categoryDao.add(category);
	}

	@Override
	@Transactional
	public Category delete(Long id) {
		return categoryDao.delete(id);
	}

	@Override
	public List<Category> list() {
		return categoryDao.list();
	}

	@Override
	public Category detail(Long id) {
		return categoryDao.detail(id);
	}

	@Override
	@Transactional
	public Category update(Category category) {
		return categoryDao.update(category);
	}
}
