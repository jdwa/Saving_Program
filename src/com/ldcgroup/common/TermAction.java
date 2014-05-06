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
import com.ldcgroup.bo.TermBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Term;

public class TermAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Term ID, for Detail, Delete action
	private Term term; // For Add, Update action
	private TermBo termBo;
	private List<Term> termList; // For List action
	
	public TermAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.termBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.termBo = (TermBo) cxt.getBean("termBo");
		}
		
		if (this.termList == null) {
			this.termList = getTermBo().list();
		}
	}	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
    // Validation
	@Override
	public void validate(){
		if (term != null) {
			if (getTermBo().findByNo(term.getTerm_no()) != null){				
				addActionError(this.getText("errors.duplicate") + term.getTerm_no());
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
		if (term.getCreation_date() == null){
			term.setCreation_date(now);
		}
		
		term.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getTermBo().add(term);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.termList = getTermBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.term = getTermBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Term orgTerm = getTermBo().findById(term.getId());
		
		Date now = new Date(); // Update time
		orgTerm.setRemark(term.getRemark());
		orgTerm.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgTerm.setTerm_description(term.getTerm_description());

		getTermBo().update(orgTerm);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getTermBo().findById(getId()) != null) {
			getTermBo().delete(getId());
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

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public TermBo getTermBo() {
		return this.termBo;
	}

	public void setTermBo(TermBo termBo) {
		this.termBo = termBo;
	}

	public List<Term> getTermList() {
		return termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}
}
