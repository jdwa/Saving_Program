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
import com.ldcgroup.bo.PayBo;
import com.ldcgroup.bo.RollBo;
import com.ldcgroup.bo.TermBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Term;
import com.ldcgroup.util.Definition;

public class RollAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Roll ID, for Detail, Delete action
	private Roll roll; // For Add, Update action
	private Date settlement_date;
	private Date pay_date;
	private PayBo payBo;
	private RollBo rollBo;
	private TermBo termBo;
	private List<Category> categoryList;
	private List<Pay> payList; 
	private List<Roll> rollList; // For List action
	private List<Term> termList;
	
	public RollAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.payBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.payBo = (PayBo) cxt.getBean("payBo");
		}

		if (this.payList == null) {
			this.payList = getPayBo().list();
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
		if (roll != null) {
			if (getRollBo().findByNo(roll.getRo_no()) != null){				
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
	
	public String add() {
		Date now = new Date();
		if (roll.getCreation_date() == null){
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
		
		roll.setValid(Boolean.TRUE);
		roll.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
		roll.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getRollBo().add(roll);
		roll = rollBo.findByNo(roll.getRo_no());
		this.rollList.clear();
		this.rollList.add(roll);
		this.payList.clear();
		this.payList.addAll(payBo.list(roll));
		
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
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
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.roll = getRollBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Roll orgRoll = getRollBo().findById(roll.getId());
		
		Date now = new Date(); // Update time
		orgRoll.setValid(roll.getValid());
		orgRoll.setRemark(roll.getRemark());
		orgRoll.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
		orgRoll.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgRoll.setValue(roll.getValue());
		
		getRollBo().update(orgRoll);
		roll = getRollBo().findById(roll.getId());
		this.rollList.clear();
		this.rollList.add(roll);
		this.payList.clear();
		this.payList.addAll(payBo.list(roll));
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		roll = rollBo.findById(getId());
		if (roll != null) {
			this.rollList.clear();
			this.rollList.add(roll);
			this.payList.clear();
			this.payList.addAll(payBo.list(roll));
			for (int i = 0; i < this.payList.size(); i++) {
				Pay pay = this.payList.get(i);
				getPayBo().delete(pay.getId());
			}
			getRollBo().delete(getId());
			this.addActionMessage(getText("action.system.delete.data"));
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
	
	public PayBo getPayBo() {
		return this.payBo;
	}

	public void setPayBo(PayBo payBo) {
		this.payBo = payBo;
	}

	public RollBo getRollBo() {
		return this.rollBo;
	}

	public void setRollBo(RollBo rollBo) {
		this.rollBo = rollBo;
	}
	
	public TermBo getTermBo() {
		return this.termBo;
	}

	public void setTermBo(TermBo termBo) {
		this.termBo = termBo;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Pay> getPayList() {
		return this.payList;
	}

	public void setPayList(List<Pay> payList) {
		this.payList = payList;
	}

	public List<Roll> getRollList() {
		return rollList;
	}

	public void setRollList(List<Roll> rollList) {
		this.rollList = rollList;
	}
	
	public List<Term> getTermList() {
		return this.termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}
}
