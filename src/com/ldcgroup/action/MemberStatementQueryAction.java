package com.ldcgroup.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.model.Bar;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Member Statement Query : 查詢員工帳戶交易資料
 * @author jdwa
 */
public class MemberStatementQueryAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private StatementBo statementBo;
	private List<Bar> barList;
	private List<Statement> statementList;
	private Member member;
	private MemberBo memberBo;
	private List<Member> memberList; 

	public MemberStatementQueryAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.statementBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.statementBo = (StatementBo) cxt.getBean("statementBo");
		}
		
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}
		
		if (this.barList == null) {
			this.barList = new ArrayList<Bar>();
		}
		
		if (this.statementList == null) {
			this.statementList = new ArrayList<Statement>();
		}
		
		if (this.memberList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
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
			this.statementList = this.statementBo.list(member);
			// Set graph data.
			execute_graph();
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("errors.data.not.exist") + member.getAccount());
			returnValue = SUCCESS;
		}
		
		return returnValue;
	}
	
	public String execute_graph() throws Exception {
		String returnValue = ERROR;
		if ((this.session != null) && (this.session.get("S_Member") != null)) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			SimpleDateFormat sdFormat;			
			for (int i = 0; i < 9; i++) {
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.roll(Calendar.DATE, -1);	
				sdFormat = new SimpleDateFormat("yyyy.MM");
				/*--
				if (calendar.get(Calendar.MONTH) == 0) {
					sdFormat = new SimpleDateFormat("yyyy.MM");
				} else {
					sdFormat = new SimpleDateFormat("MM");
				}
				--*/
				Bar bar = new Bar();
				bar.setTime(calendar.getTimeInMillis());
				bar.setLabel(sdFormat.format(calendar.getTime()));
				barList.add(bar);
			}

			for (int i = 0; i < statementList.size(); i++) {
				Statement statement = (Statement) statementList.get(i);
				for (int j = 0; j < barList.size(); j++) {
					Bar bar = (Bar) barList.get(j);
					if (statement.getSettlement_date().getTime() <= bar.getTime()) {
						if ("001".equals(statement.getType().getType_no())) {
							bar.setFund_type_001(bar.getFund_type_001() + statement.getFund());
						} else if ("002".equals(statement.getType().getType_no())) {
							bar.setFund_type_002(bar.getFund_type_002() + statement.getFund());	
						} else if ("003".equals(statement.getType().getType_no())) {
							bar.setFund_type_003(bar.getFund_type_003() + statement.getFund());	
						} else if ("004".equals(statement.getType().getType_no())) {
							bar.setFund_type_004(bar.getFund_type_004() + statement.getFund());	
						} else if ("005".equals(statement.getType().getType_no())) {
							bar.setFund_type_005(bar.getFund_type_005() + statement.getFund());	
						}
					} else {
						break;
					}
				}
			}
			
			Collections.reverse(barList);
			
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
	
	public StatementBo getStatementBo() {
		return this.statementBo;
	}

	public void setStatementBo(StatementBo statementBo) {
		this.statementBo = statementBo;
	}

	public List<Statement> getStatementList() {
		return this.statementList;
	}

	public void setStatementList(List<Statement> statementList) {
		this.statementList = statementList;
	}
	
	public List<Bar> getBarList() {
		return barList;
	}

	public void setBarList(List<Bar> barList) {
		this.barList = barList;
	}
}
