package com.ldcgroup.action;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.RollBo;
import com.ldcgroup.bo.PayBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.util.PayComparator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberSalarySubgridAction extends ActionSupport implements Preparable, SessionAware, ParameterAware {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Map<String, Object> session;
	private RollBo rollBo;
	private PayBo payBo;
	private List<Member> memberList;
	private List<Pay> payList;
	private List<Roll> rollList;
	private Map<String, Object> userdata;

	protected Integer rows = new Integer(0);
	protected Integer page = new Integer(0);;
	protected Integer total = new Integer(0);;
	protected Integer record = new Integer(0);;
	protected String sord;
	protected String sidx;
	protected String search;
	protected String searchField;
	protected String searchOper;
	protected String searchString;
	protected String filters;
	
	public MemberSalarySubgridAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.rollBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rollBo = (RollBo) cxt.getBean("rollBo");
		}
		
		if (this.payBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.payBo = (PayBo) cxt.getBean("payBo");
		}

		if (this.payList == null) {
			this.payList = new ArrayList<Pay>();
		}
		
		if (this.rollList == null) {
			this.rollList = new ArrayList<Roll>();
		}
		
		if (this.userdata == null) {
			this.userdata = new HashMap<String, Object>();
		}
	}
	
	@Override
	public String execute() {
		String returnValue = ERROR;
		
		Member member = (Member) this.session.get("S_Member");
		Roll roll = (Roll) getRollBo().findById(this.id.longValue());
		
		if (member != null) {
			
			List<Pay> memberRollPayList = this.payBo.list(roll, member);
			// Check for search operation
			if (getSearchField() != null) {
				PayComparator payComparator = new PayComparator();				
				for (int i = 0; i < memberRollPayList.size(); i++) {
					Pay pay = memberRollPayList.get(i);
					if (payComparator.isMatch(pay, this.searchField, this.searchOper, this.searchString)) {
						this.payList.add(pay);
					}				
				}
			} else {
				this.payList.addAll(memberRollPayList);
			}
			
			if (this.payList != null) {
				if (getSord() != null && getSord().equalsIgnoreCase("asc")) {
					Collections.sort(payList, new PayComparator(sidx));				
				}
				if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
					Collections.sort(payList, new PayComparator(sidx));
					Collections.reverse(payList);
				}
			}
			
			double grandTotal = 0;
			for (int i = 0; i < payList.size(); i++) {
				Pay pay = payList.get(i);
				grandTotal += pay.getValue();
			}
			
			Object totalCount = payList.size();
			this.record = Integer.valueOf(Integer.parseInt((totalCount == null) ? "0" : totalCount.toString()));
			this.total = Integer.valueOf((int) Math.ceil(this.record.doubleValue() / this.rows.doubleValue()));
			
			int fromIndex = ((this.page > 0) ? ((this.page - 1) * this.rows) : 0 );
			int toIndex	= (((this.page * this.rows) > this.record) ? (this.record) : (this.page * this.rows));
			payList = payList.subList(fromIndex, toIndex);
			
			double subtotal = 0;
			for (int i = 0; i < payList.size(); i++) {
				Pay pay = payList.get(i);
				subtotal += pay.getValue();
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.00");
			userdata.put("company.cmp_description", "Sub Total:");
			userdata.put("value", subtotal);
			userdata.put("settlement_date", "Grand Total:");
			userdata.put("creation_date", formatter.format(grandTotal));
			
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.roll.pays.zero"));
			returnValue = SUCCESS;
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
	
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		  if (parameters.get("_") != null) {
			  parameters.remove("_");
		  }
		  if (parameters.get("_search") != null) {
			  parameters.remove("_search");
		  }
		  if (parameters.get("nd") != null) {
			  parameters.remove("nd");
		  }
	}

	@SkipValidation
	public String initialize() throws Exception {
		return SUCCESS;
	}
	
	public RollBo getRollBo() {
		return this.rollBo;
	}

	public void setRollBo(RollBo rollBo) {
		this.rollBo = rollBo;
	}
	
	public List<Member> getMemberList() {
		return this.memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
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

	public List<Roll> getRollList() {
		return this.rollList;
	}

	public void setRollList(List<Roll> rollList) {
		this.rollList = rollList;
	}
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecord() {
		return record;
	}

	public void setRecord(Integer record) {
		this.record = record;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
	
	public Map<String, Object> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	
	public void setId(Integer id) {
	    this.id = id;
	}
	
}
