package com.ldcgroup.common;

import java.util.Calendar;
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
import com.ldcgroup.bo.CompanyBo;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.PayBo;
import com.ldcgroup.bo.TermBo;
import com.ldcgroup.bo.RollBo;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Term;
import com.ldcgroup.util.Definition;

public class PayAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
	private Long id; // Pay ID, for Detail, Delete action
	private Pay pay; // For Add, Update action
	private Date settlement_date;
	private Date pay_date;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private PayBo payBo;
	private TermBo termBo;
	private RollBo rollBo;
	private List<Company> companyList; // For List action
	private List<Member> memberList; 
	private List<Pay> payList; 
	private List<Term> termList;
	private List<Roll> rollList;
	
	public PayAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {		
		if (this.companyBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.companyBo = (CompanyBo) cxt.getBean("companyBo");
		}
		
		if (this.companyList == null) {
			this.companyList = getCompanyBo().list();
		}
		
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}
		
		if (this.memberList == null) {
			this.memberList = getMemberBo().list();
		}

		if (this.payBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.payBo = (PayBo) cxt.getBean("payBo");
		}

		if (this.payList == null) {
			this.payList = getPayBo().list();
		}
		
		if (this.termBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.termBo = (TermBo) cxt.getBean("termBo");
		}
		
		if (this.termList == null) {
			this.termList = getTermBo().list();
		}

		if (this.rollBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rollBo = (RollBo) cxt.getBean("rollBo");
		}
		
		if (this.rollList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
					this.rollList = getRollBo().list();
				} else {
					this.rollList = getRollBo().list(member.getCompany());
				}
			}
		}
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
    // Validation
	@Override
	public void validate() {
		// Check validation here.
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
		Roll roll = getRollBo().findByNo(this.pay.getRoll().getRo_no());
		Date now = new Date();
		if (pay.getCreation_date() == null){
			pay.setCreation_date(now);
		}
		
		if (pay.getSettlement_date() == null) {
			if (roll.getSettlement_date() != null) {
				pay.setSettlement_date(roll.getSettlement_date());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.roll(Calendar.DATE, -1);
				pay.setSettlement_date(calendar.getTime());
			}
		}
		
		if (pay.getPay_date() == null) {
			if (roll.getPay_date() != null) {
				pay.setPay_date(roll.getPay_date());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DATE, 5);
				pay.setPay_date(calendar.getTime());
			}
		}
		
		pay.setMember(getMemberBo().findByAccount(this.pay.getMember().getAccount()));
		pay.setCompany(getCompanyBo().findByNo(this.pay.getCompany().getCmp_no()));
		pay.setRoll(roll);
		pay.setTerm(getTermBo().findByNo(this.pay.getTerm().getTerm_no()));
		pay.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		roll.setValue(roll.getValue() + pay.getValue());
		getRollBo().update(roll);
		getPayBo().add(pay);
		list();
		
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.payList = getPayBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.pay = getPayBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Pay orgPay = getPayBo().findById(pay.getId());
		
		Date now = new Date(); // Update time
		orgPay.setRemark(pay.getRemark());
		orgPay.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgPay.setValue(pay.getValue());
		
		getPayBo().update(orgPay);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getPayBo().findById(getId()) != null) {
			getPayBo().delete(getId());
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

	public Pay getPay() {
		return pay;
	}

	public void setPay(Pay pay) {
		this.pay = pay;
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
		if (this.pay_date == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 5);
			this.pay_date = calendar.getTime();
		}
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public CompanyBo getCompanyBo() {
		return companyBo;
	}

	public void setCompanyBo(CompanyBo companyBo) {
		this.companyBo = companyBo;
	}

	public MemberBo getMemberBo() {
		return memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public PayBo getPayBo() {
		return this.payBo;
	}

	public void setPayBo(PayBo payBo) {
		this.payBo = payBo;
	}
	
	public TermBo getTermBo() {
		return this.termBo;
	}

	public void setTermBo(TermBo termBo) {
		this.termBo = termBo;
	}

	public RollBo getRollBo() {
		return rollBo;
	}

	public void setRollBo(RollBo rollBo) {
		this.rollBo = rollBo;
	}
	
	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public List<Pay> getPayList() {
		return this.payList;
	}

	public void setPayList(List<Pay> payList) {
		this.payList = payList;
	}
	
	public List<Term> getTermList() {
		return this.termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}

	public List<Roll> getRollList() {
		return rollList;
	}

	public void setRollList(List<Roll> rollList) {
		this.rollList = rollList;
	}
}
