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
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Type;

public class TypeAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Type ID, for Detail, Delete action
	private Type type; // For Add, Update action
	private TypeBo typeBo;
	private List<Type> typeList; // For List action
	
	public TypeAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.typeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.typeBo = (TypeBo) cxt.getBean("typeBo");
		}		
	}	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
    // Validation
	@Override
	public void validate(){
		if (type != null) {
			if (getTypeBo().findByNo(type.getType_no()) != null){				
				addActionError(this.getText("errors.duplicate") + type.getType_no());
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
		if (type.getCreation_date() == null){
			type.setCreation_date(now);
		}
		
		type.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getTypeBo().add(type);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.typeList = getTypeBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.type = getTypeBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Type orgType = getTypeBo().findById(type.getId());
		
		Date now = new Date(); // Update time
		orgType.setRemark(type.getRemark());
		orgType.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgType.setType_description(type.getType_description());

		getTypeBo().update(orgType);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getTypeBo().findById(getId()) != null) {
			getTypeBo().delete(getId());
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public TypeBo getTypeBo() {
		return this.typeBo;
	}

	public void setTypeBo(TypeBo typeBo) {
		this.typeBo = typeBo;
	}

	public List<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}
}
