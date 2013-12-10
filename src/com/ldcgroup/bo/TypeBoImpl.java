package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.TypeDao;
import com.ldcgroup.model.Type;

public class TypeBoImpl implements TypeBo {
	
	private TypeDao typeDao;

	@Override
	public Type findById(Long id) {
		return typeDao.findById(id);
	}

	@Override
	public Type findByNo(String no) {
		return typeDao.findByNo(no);
	}

	//DI via Spring
	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}
	
	@Override
	@Transactional
	public Type add(Type type) {
		return typeDao.add(type);
	}

	@Override
	@Transactional
	public Type delete(Long id) {
		return typeDao.delete(id);
	}

	@Override
	public List<Type> list() {
		return typeDao.list();
	}

	@Override
	public Type detail(Long id) {
		return typeDao.detail(id);
	}

	@Override
	@Transactional
	public Type update(Type type) {
		return typeDao.update(type);
	}
}
