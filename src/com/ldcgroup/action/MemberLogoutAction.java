package com.ldcgroup.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberLogoutAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	
	public MemberLogoutAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		// Not thing to prepare for log out.
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;

		if (this.session != null){
			this.session.clear();
			returnValue = SUCCESS;
		}
		
		return returnValue;
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

}
