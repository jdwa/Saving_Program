package com.ldcgroup.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldcgroup.bo.ItemBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Item;

public class ItemAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Item ID, for Detail, Delete action
	private Item item; // For Add, Update action
	private ItemBo itemBo;
	private List<Item> itemList; // For List action
	
	public ItemAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.itemBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.itemBo = (ItemBo) cxt.getBean("itemBo");
		}
		
		if (this.itemList == null) {
			this.itemList = getItemBo().list();
		}
	}	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
    // Validation
	@Override
	public void validate(){
		if (item != null) {
			if (getItemBo().findByNo(item.getItem_no()) != null){				
				addActionError(this.getText("errors.duplicate") + item.getItem_no());
			}
		}
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<String, Object> getSession(){
		   return this.session;
	}
	
	@SkipValidation
	public String initialize() throws Exception {
		return SUCCESS;
	}
	
	public String add() {
		Date now = new Date();
		if (item.getCreation_date() == null){
			item.setCreation_date(now);
		}
		
		item.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getItemBo().add(item);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.itemList = getItemBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.item = getItemBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Item orgItem = getItemBo().findById(item.getId());
		
		Date now = new Date(); // Update time
		orgItem.setRemark(item.getRemark());
		orgItem.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgItem.setItem_description(item.getItem_description());

		getItemBo().update(orgItem);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getItemBo().findById(getId()) != null) {
			getItemBo().delete(getId());
			list();
		} else {
			addActionError(this.getText("errors.data.not.exist") + getId());
		}
		return SUCCESS;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ItemBo getItemBo() {
		return this.itemBo;
	}

	public void setItemBo(ItemBo itemBo) {
		this.itemBo = itemBo;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
