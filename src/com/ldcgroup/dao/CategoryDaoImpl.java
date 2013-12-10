package com.ldcgroup.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Category;

public class CategoryDaoImpl extends HibernateDaoSupport implements CategoryDao {

	@Override
	@Transactional(readOnly = true)
	public Category findById(Long id) {
		return (Category) getHibernateTemplate().get(Category.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public Category findByNo(String no) {
		List<Category> list = getHibernateTemplate().find("from Category where category_no = ?", no);
		return ((list != null) && (list.size() > 0)) ? (Category) list.get(0) : null;
	}

	@Override
	@Transactional
	public Category add(Category category) {
		getHibernateTemplate().saveOrUpdate(category);
		return category;
	}

	@Override
	@Transactional
	public Category delete(Long id) {
		Category category = findById(id);
		getHibernateTemplate().delete(category);
		return category;
	}

	@Override
	@Transactional(readOnly = true)
	public Category detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Category update(Category category) {
		getHibernateTemplate().saveOrUpdate(category);
		return category;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Category> list() {
		return (List<Category>) getHibernateTemplate().find("from Category");
	}
}
