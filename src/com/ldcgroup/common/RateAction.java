package com.ldcgroup.common;

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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldcgroup.bo.RateBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Rate;

public class RateAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Rate ID, for Detail, Delete action
	private Rate rate; // For Add, Update action
	private RateBo rateBo;
	private List<Rate> rateList; // For List action
	private List<String> yearList; // For years list
	
	public RateAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.rateBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rateBo = (RateBo) cxt.getBean("rateBo");
		}
		
		if (this.rateList == null) {
			this.rateList = getRateBo().list();
		}
		
		if (this.yearList == null) {
			this.yearList = new ArrayList<String>();
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			for (int i = year-5; i < year+5; i++) {
				this.yearList.add((new Integer(i)).toString());
			}
		}
	}	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    // Validation
	@Override
	public void validate(){
		if ((rate != null) && (rate.getRate_date() != null)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(rate.getRate_date());
			if (getRateBo().findByYear(calendar.get(Calendar.YEAR)) != null){				
				addActionError(this.getText("errors.duplicate") + calendar.get(Calendar.YEAR));
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
		if (rate.getCreation_date() == null){
			rate.setCreation_date(now);
		}
		if (rate.getRate_date() == null){
			rate.setRate_date(now);
		}
		
		rate.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getRateBo().add(rate);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.rateList = getRateBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.rate = getRateBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Rate orgRate = getRateBo().findById(rate.getId());
		
		Date now = new Date(); // Update time
		orgRate.setRemark(rate.getRemark());
		orgRate.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgRate.setRate_date(rate.getRate_date());
		orgRate.setRate_value(rate.getRate_value());

		getRateBo().update(orgRate);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getRateBo().findById(getId()) != null) {
			getRateBo().delete(getId());
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

	public Rate getRate() {
		return rate;
	}

	public void setRate(Rate rate) {
		this.rate = rate;
	}

	public RateBo getRateBo() {
		return this.rateBo;
	}

	public void setRateBo(RateBo rateBo) {
		this.rateBo = rateBo;
	}

	public List<Rate> getRateList() {
		return rateList;
	}

	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}
}
