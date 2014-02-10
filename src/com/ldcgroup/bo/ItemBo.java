package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Item;

public interface ItemBo {
	public Item findById(Long id);
	public Item findByNo(String no);
	public Item add(Item item);
	public Item delete(Long id);
	public List<Item> list();
	public Item detail(Long id);
	public Item update(Item item);
}
