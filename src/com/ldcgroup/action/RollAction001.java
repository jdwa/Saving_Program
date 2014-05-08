package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.PayBo;
import com.ldcgroup.bo.RollBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.TermBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Term;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Roll Action 01 : 薪資作業（單筆）
 * @author jdwa
 */
public class RollAction001 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Term term;
	private Member member;
	private Roll roll;
	private Date settlement_date;
	private Date pay_date;
	private MemberBo memberBo;
	private PayBo payBo;
	private RollBo rollBo;
	private TermBo termBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Category> categoryList;
	private List<Member> memberList;
	private List<Pay> payList;
	private List<Roll> rollList;
	private List<Term> termList;

	public RollAction001() {
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
		
		if (this.payBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.payBo = (PayBo) cxt.getBean("payBo");
		}
		
		if (this.rollBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rollBo = (RollBo) cxt.getBean("rollBo");
		}
		
		if (this.termBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.termBo = (TermBo) cxt.getBean("termBo");
		}
		
		if (this.transactionServiceBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.transactionServiceBo = (TransactionServiceBo) cxt.getBean("transactionServiceBo");
		}
		
		if (this.memberList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) { 
					this.memberList = getMemberBo().listNormal();
				} else {
					this.memberList = getMemberBo().listNormal(member.getCompany());
				}
			}			
		}
		
		if (this.payList == null) {
			this.payList = new ArrayList<Pay>();
		}
		
		if (this.rollList == null) {
			this.rollList = new ArrayList<Roll>();
		}
		
		if (this.termList == null) {
			this.termList = getTermBo().list();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		Date now = new Date();
		if (roll.getCreation_date() == null) {
			roll.setCreation_date(now);
		}
		
		if (roll.getSettlement_date() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			roll.setSettlement_date(calendar.getTime());
		}
		
		if (roll.getPay_date() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 5);
			roll.setPay_date(calendar.getTime());
		}

		List<Pay> pays = new ArrayList<Pay>();
		this.member = getMemberBo().findByAccount(this.member.getAccount());
		
		if ((member != null) && (member.getRole().getRole_code().equals(Definition.ROLE_NORMAL))) {
			// 點數項目
			Term term = getTermBo().findByNo(getTerm().getTerm_no());
			
			// Add pay for member
			Pay pay = new Pay();
			pay.setCompany(member.getCompany());
			pay.setMember(member);
			pay.setRoll(this.roll);
			pay.setTerm(term);
			pay.setValue(this.roll.getValue());
			pay.setSettlement_date(roll.getSettlement_date());
			pay.setPay_date(roll.getPay_date());
			pay.setCreation_date(roll.getCreation_date());
			pay.setRemark(roll.getRemark() + "[" + term.getTerm_description() + "]");
			pay.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			pays.add(pay);

		} else {
			addActionError("[" + member.getAccount() + "]," + getText("member.not.qualified") + member.getRole().getRole_description());
			pays.clear();
		}

		this.rollList.clear();
		this.payList.clear();

		if (pays.size() > 0) {
			// Total amount would be roll.getValue()
			this.roll.setValue(this.roll.getValue());
			// Add roll data
			roll.setValid(Boolean.TRUE);
			roll.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			roll.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addRollPayList(roll, pays);
			roll = rollBo.findByNo(roll.getRo_no());
			this.rollList.add(roll);
			this.payList.addAll(payBo.list(roll));
			this.session.put("S_Roll", roll);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.roll.pays.zero"));
			returnValue = INPUT;
		}
		
		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (roll != null) {
			if (getRollBo().findByNo(roll.getRo_no()) != null) {				
				addActionError(this.getText("errors.duplicate") + roll.getRo_no());
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

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Roll getRoll() {
		return roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
	}
	
	public Date getSettlement_date() {
		if (this.settlement_date == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			this.settlement_date = calendar.getTime();
		}
		return settlement_date;
	}

	public void setSettlement_date(Date settlement_date) {
		this.settlement_date = settlement_date;
	}
	
	public Date getPay_date() {
		if (this.settlement_date == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 5);
			this.pay_date = calendar.getTime();
		}
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	public MemberBo getMemberBo() {
		return this.memberBo;
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
	
	public RollBo getRollBo() {
		return this.rollBo;
	}

	public void setRollBo(RollBo rollBo) {
		this.rollBo = rollBo;
	}

	public List<Roll> getRollList() {
		return rollList;
	}

	public void setRollList(List<Roll> rollList) {
		this.rollList = rollList;
	}
	
	public PayBo getPayBo() {
		return this.payBo;
	}

	public void setPayBo(PayBo payBo) {
		this.payBo = payBo;
	}
	
	public List<Pay> getPayList() {
		return this.payList;
	}

	public void setPayList(List<Pay> payList) {
		this.payList = payList;
	}
	
	public TermBo getTermBo() {
		return this.termBo;
	}

	public void setTermBo(TermBo termBo) {
		this.termBo = termBo;
	}
	
	public List<Term> getTermList() {
		return this.termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}

	public TransactionServiceBo getTransactionServiceBo() {
		return transactionServiceBo;
	}

	public void setTransactionServiceBo(TransactionServiceBo transactionServiceBo) {
		this.transactionServiceBo = transactionServiceBo;
	}
}
