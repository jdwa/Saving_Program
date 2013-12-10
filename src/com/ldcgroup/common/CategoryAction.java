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
import com.ldcgroup.bo.CategoryBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Category;

public class CategoryAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Category ID, for Detail, Delete action
	private Category category; // For Add, Update action
	private CategoryBo categoryBo;
	private List<Category> categoryList; // For List action
	
	public CategoryAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.categoryBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.categoryBo = (CategoryBo) cxt.getBean("categoryBo");
		}
		
		if (this.categoryList == null) {
			this.categoryList = getCategoryBo().list();
		}
	}	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
    // Validation
	@Override
	public void validate(){
		if (category != null) {
			if (getCategoryBo().findByNo(category.getCategory_no()) != null){				
				addActionError(this.getText("errors.duplicate") + category.getCategory_no());
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
		if (category.getCreation_date() == null){
			category.setCreation_date(now);
		}
		
		category.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getCategoryBo().add(category);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.categoryList = getCategoryBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.category = getCategoryBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Category orgCategory = getCategoryBo().findById(category.getId());
		
		Date now = new Date(); // Update time
		orgCategory.setRemark(category.getRemark());
		orgCategory.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgCategory.setCategory_description(category.getCategory_description());

		getCategoryBo().update(orgCategory);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getCategoryBo().findById(getId()) != null) {
			getCategoryBo().delete(getId());
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CategoryBo getCategoryBo() {
		return this.categoryBo;
	}

	public void setCategoryBo(CategoryBo categoryBo) {
		this.categoryBo = categoryBo;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
}
