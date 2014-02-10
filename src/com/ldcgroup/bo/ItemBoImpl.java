package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.ItemDao;
import com.ldcgroup.model.Item;

public class ItemBoImpl implements ItemBo {
	
	private ItemDao itemDao;

	@Override
	public Item findById(Long id) {
		return itemDao.findById(id);
	}

	@Override
	public Item findByNo(String no) {
		return itemDao.findByNo(no);
	}

	//DI via Spring
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	
	@Override
	@Transactional
	public Item add(Item item) {
		return itemDao.add(item);
	}

	@Override
	@Transactional
	public Item delete(Long id) {
		return itemDao.delete(id);
	}

	@Override
	public List<Item> list() {
		return itemDao.list();
	}

	@Override
	public Item detail(Long id) {
		return itemDao.detail(id);
	}

	@Override
	@Transactional
	public Item update(Item item) {
		return itemDao.update(item);
	}
}
