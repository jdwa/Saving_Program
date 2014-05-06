package com.ldcgroup.action;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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

import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.PayBo;
import com.ldcgroup.bo.RollBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.util.RollComparator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberSalaryGridAction extends ActionSupport implements Preparable, SessionAware, ParameterAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private MemberBo memberBo;
	private RollBo rollBo;
	private PayBo payBo;
	private List<Roll> rollList;
	private List<Pay> payList;
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
	
	public MemberSalaryGridAction() {
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
		
		if (this.rollList == null) {
			this.rollList = new ArrayList<Roll>();
		}
		
		if (this.payList == null) {
			this.payList = new ArrayList<Pay>();
		}
		
		if (this.userdata == null) {
			this.userdata = new HashMap<String, Object>();
		}
	}
	
	@Override
	public String execute() {
		String returnValue = ERROR;

		Member member = (Member) this.session.get("S_Member");
		List<Roll> memberRollList = new ArrayList<Roll>();
		List<Pay> payList = payBo.list(member);
		for (int i = 0; i < payList.size(); i++) {			
			Pay pay = (Pay) payList.get(i);
			Roll roll = pay.getRoll();
			if (!memberRollList.contains(roll)) {
				memberRollList.add(roll);
				roll.setSubvalue(0.0);
			}
			double subvalue = roll.getSubvalue().doubleValue() + pay.getValue().doubleValue();
			roll.setSubvalue(subvalue);
		}
		
		if (memberRollList.size() > 0) {
						
			this.rollList.clear();
			// Check for search operation
			if (getSearchField() != null) {
				for (int i = 0; i < memberRollList.size(); i++) {
					Roll roll = memberRollList.get(i);					
					String[] fieldList = {"id","valid","ro_no","subvalue","company.cmp_description",
										  "creation_date","remark","timestamp"};
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String[] valueList = {roll.getId().toString(), roll.getValid().toString(), roll.getRo_no(), roll.getSubvalue().toString(), roll.getCompany().getCmp_description(), 
										  sdf.format(roll.getCreation_date()), roll.getRemark(), roll.getTimestamp()};
					for (int j = 0; j < fieldList.length; j++) {
						if (getSearchField().equals(fieldList[j])) {
							if (getSearchOper() != null && getSearchOper().equals("eq")) {
								if (valueList[j].equals(getSearchString())) {
									this.rollList.add(roll);
								}
							} else if (getSearchOper() != null && getSearchOper().equals("ne")) {
								if (!valueList[j].equals(getSearchString())) {
									this.rollList.add(roll);
								}
							} else if (getSearchOper() != null && getSearchOper().equals("lt")) {
								if (valueList[j].compareTo(getSearchString()) < 0) {
									this.rollList.add(roll);
								}
							} else if (getSearchOper() != null && getSearchOper().equals("gt")) {
								if (valueList[j].compareTo(getSearchString()) > 0) {
									this.rollList.add(roll);
								}
							}
						}
					}
				}
			} else {
				this.rollList.addAll(memberRollList);
			}			
			
			if (this.rollList != null) {
				if (getSord() != null && getSord().equalsIgnoreCase("asc")) {
					Collections.sort(rollList, new RollComparator(sidx));				
				}
				if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
					Collections.sort(rollList, new RollComparator(sidx));
					Collections.reverse(rollList);
				}
			}		
			
			double grandtotal = 0;
			for (int i = 0; i < rollList.size(); i++) {
				Roll roll = rollList.get(i);
				grandtotal += payBo.getPaySum(roll, member);
			}
			
			Object totalCount = rollList.size();
			this.record = Integer.valueOf(Integer.parseInt((totalCount == null) ? "0" : totalCount.toString()));
			this.total = Integer.valueOf((int) Math.ceil(this.record.doubleValue() / this.rows.doubleValue()));
			
			int fromIndex = ((this.page > 0) ? ((this.page - 1) * this.rows) : 0 );
			int toIndex	= (((this.page * this.rows) > this.record) ? (this.record) : (this.page * this.rows));
			rollList = rollList.subList(fromIndex, toIndex);

			double subtotal = 0;
			for (int i = 0; i < rollList.size(); i++) {
				Roll roll = rollList.get(i);
				subtotal += payBo.getPaySum(roll, member);
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.00");
			userdata.put("company.cmp_description", "Sub Total:");
			userdata.put("subvalue", subtotal);
			userdata.put("settlement_date", "Grand Total:");
			userdata.put("creation_date", formatter.format(grandtotal));
			
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.data.zero"));
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

	public MemberBo getMemberBo() {
		return this.memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public RollBo getRollBo() {
		return this.rollBo;
	}

	public void setRollBo(RollBo rollBo) {
		this.rollBo = rollBo;
	}
	
	public List<Roll> getRollList() {
		return this.rollList;
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
}
