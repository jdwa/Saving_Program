package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.PayBo;
import com.ldcgroup.bo.RollBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Roll Action Query : 查詢作業資料
 * @author jdwa
 */
public class RollActionQuery extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private PayBo payBo;
	private Roll roll;
	private RollBo rollBo;
	private List<Pay> payList;
	private List<Roll> rollList;

	public RollActionQuery() {
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
		
		if (this.rollBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rollBo = (RollBo) cxt.getBean("rollBo");
		}
				
		if (this.payList == null) {
			this.payList = new ArrayList<Pay>();
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
		String returnValue = ERROR;
		
		this.rollList.clear();
		this.payList.clear();

		roll = getRollBo().findByNo(roll.getRo_no());
		
		if (roll != null) {
			this.rollList.add(roll);
			this.payList = getPayBo().list(roll);
			this.session.put("S_Roll", roll);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.roll.pays.zero"));
			returnValue = SUCCESS;
		}
		
		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (roll != null) {
			if (getRollBo().findByNo(roll.getRo_no()) == null) {				
				addActionError(this.getText("errors.data.not.exist") + roll.getRo_no());
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

	public Roll getRoll() {
		return roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
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
	
}
