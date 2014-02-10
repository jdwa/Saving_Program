package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Item;

public interface ItemDao {
	public Item findById(Long id);
	public Item findByNo(String no);
	public Item add(Item item);
	public Item delete(Long id);
	public Item detail(Long id);
	public Item update(Item item);
	public List<Item> list();
}
