package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.model.Member;

public class MemberLoginAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private MemberBo memberBo;
	private String account;
	private String password;
	
	public MemberLoginAction() {
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
		if(memberBo.findByAccount("it.admin") == null){
			Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
			GrantedAuthority role_code=new SimpleGrantedAuthority("ROLE_INITIALIZE");
			auths.add(role_code);
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null, auths));
			addActionMessage(getText("action.system.initialize"));
			returnValue = "initialize";
		} else {
			Member member = memberBo.findByAccount(this.account);
			if (member == null) {
				addActionError(getText("action.account.invalid"));
				returnValue = "input";
			} else {
				if (!member.getActive().booleanValue()) {
					addActionError(getText("action.account.inactive"));
					returnValue = "input";
				} else {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(this.account, this.password);
					Authentication authentication = memberBo.authenticate(token);
					if (authentication != null) {
						SecurityContextHolder.getContext().setAuthentication(authentication);
						this.session.put("CurrentMember", member);
						this.session.put("S_Member", member);
						returnValue = SUCCESS;
					} else {
						addActionError(getText("action.login.failure"));
						returnValue = "input";
					}
				}
			}
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
		return memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
