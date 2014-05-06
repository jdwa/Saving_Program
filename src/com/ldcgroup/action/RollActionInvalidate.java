package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.PayBo;
import com.ldcgroup.bo.RollBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Roll Action Invalidate : µù¾P§@·~
 * @author jdwa
 */
public class RollActionInvalidate extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private PayBo payBo;
	private Roll roll;
	private RollBo rollBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Pay> payList;
	private List<Roll> rollList;

	public RollActionInvalidate() {
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
		
		if (this.transactionServiceBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.transactionServiceBo = (TransactionServiceBo) cxt.getBean("transactionServiceBo");
		}
		
		if (this.payList == null) {
			this.payList = new ArrayList<Pay>();
		}
		
		if (this.rollList == null) {
			this.rollList = getRollBo().listValidate();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		Date now = new Date();		
		List<Pay> pays = new ArrayList<Pay>();
		roll = getRollBo().findByNo(roll.getRo_no());
		
		if ((roll != null) && (roll.getValid().booleanValue())) {
			List<Pay> rollPays = getPayBo().list(roll);
			for (int i = 0; i < rollPays.size(); i++) {
				Pay pay = new Pay();
				pay.setCompany(rollPays.get(i).getCompany());
				pay.setMember(rollPays.get(i).getMember());
				pay.setRoll(this.roll);
				pay.setTerm(rollPays.get(i).getTerm());
				pay.setValue(rollPays.get(i).getValue() * (-1));
				pay.setSettlement_date(rollPays.get(i).getSettlement_date());
				pay.setPay_date(rollPays.get(i).getPay_date());
				pay.setCreation_date(now);
				pay.setRemark(roll.getRemark() + "[" + getText("roll.action.invalidate") + "]");
				pay.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				pays.add(pay);				
			}
			
		}
		
		this.rollList.clear();
		this.payList.clear();

		if (pays.size() > 0) {
			// Total amount would be 0.0 after invalidate
			this.roll.setValue(0.0);
			// Add roll data
			roll.setValid(Boolean.FALSE);
			//roll.setCompany(((Member)this.session.get("CurrentMember")).getCompany()); Do not change the Company
			roll.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addRollPayList(roll, pays);
			roll = rollBo.findByNo(roll.getRo_no());
			this.rollList.add(roll);
			this.payList.addAll(payBo.list(roll));
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
	
	public TransactionServiceBo getTransactionServiceBo() {
		return transactionServiceBo;
	}

	public void setTransactionServiceBo(TransactionServiceBo transactionServiceBo) {
		this.transactionServiceBo = transactionServiceBo;
	}
}
