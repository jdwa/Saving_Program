package com.ldcgroup.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.PointBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Member Point Query : 查詢員工帳戶作業資料
 * @author jdwa
 */
public class MemberPointQueryAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private PointBo pointBo;
	private Member member;
	private MemberBo memberBo;
	private List<Member> memberList; 

	public MemberPointQueryAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.pointBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.pointBo = (PointBo) cxt.getBean("pointBo");
		}
		
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}
		
		if (this.memberList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)
					|| (member.getRole().getRole_code().equals(Definition.ROLE_TRAINER) && member.getCompany().getCmp_no().equals(Definition.HQ_NO))) {											
					this.memberList = getMemberBo().list();
				} else {
					this.memberList = getMemberBo().list(member.getCompany());
				}
			}			
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		member = getMemberBo().findByAccount(member.getAccount());
		if (member != null) {
			this.session.put("S_Member", member);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("errors.data.not.exist") + member.getAccount());
			returnValue = SUCCESS;
		}
		
		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (member != null) {
			if (getMemberBo().findByAccount(member.getAccount()) == null) {				
				addActionError(getText("errors.data.not.exist") + member.getAccount());
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public MemberBo getMemberBo() {
		return memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	
	public PointBo getPointBo() {
		return this.pointBo;
	}

	public void setPointBo(PointBo pointBo) {
		this.pointBo = pointBo;
	}
}
