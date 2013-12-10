package com.ldcgroup.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.util.CryptUtils;

public class MemberPasswordChangeAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private MemberBo memberBo;
	private String orgPassword;
	private String newPassword;
	private String cfmPassword;
	
	public MemberPasswordChangeAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}	
	}

	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		Member member = (Member) this.session.get("CurrentMember");
	
		// Check password
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String de = CryptUtils.decryptString(member.getPassword(), sdf.format(member.getCreation_date()));
	
		if ((member != null) && (de.equals(getOrgPassword())) 
			&& (getNewPassword().length() > 6) && (getNewPassword().equals(getCfmPassword()))) {
			
			// Set password
			Date now = new Date();
			String en = CryptUtils.encryptString(getNewPassword(), sdf.format(member.getCreation_date()));
			member.setPassword(en);
			member.setRemark("");
			member.setTimestamp(getText("action.change.password") + ", " + getText("action.last.update") + "[" + now + "]");
			memberBo.update(member);
			returnValue = SUCCESS;
		} else {
			addActionError(getText("action.change.password.error"));
			returnValue = ERROR;
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
	
	public MemberBo getMemberBo() {
		return this.memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public String getOrgPassword() {
		return orgPassword;
	}

	public void setOrgPassword(String orgPassword) {
		this.orgPassword = orgPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCfmPassword() {
		return cfmPassword;
	}

	public void setCfmPassword(String cfmPassword) {
		this.cfmPassword = cfmPassword;
	}
			
}
