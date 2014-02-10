package com.ldcgroup.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldcgroup.model.Item;

public class ItemDaoImpl extends HibernateDaoSupport implements ItemDao {

	@Override
	@Transactional(readOnly = true)
	public Item findById(Long id) {
		return (Item) getHibernateTemplate().get(Item.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public Item findByNo(String no) {
		List<Item> list = getHibernateTemplate().find("from Item where item_no = ?", no);
		return ((list != null) && (list.size() > 0)) ? (Item) list.get(0) : null;
	}

	@Override
	@Transactional
	public Item add(Item item) {
		getHibernateTemplate().saveOrUpdate(item);
		return item;
	}

	@Override
	@Transactional
	public Item delete(Long id) {
		Item item = findById(id);
		getHibernateTemplate().delete(item);
		return item;
	}

	@Override
	@Transactional(readOnly = true)
	public Item detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Item update(Item item) {
		getHibernateTemplate().saveOrUpdate(item);
		return item;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Item> list() {
		return (List<Item>) getHibernateTemplate().find("from Item");
	}
}
