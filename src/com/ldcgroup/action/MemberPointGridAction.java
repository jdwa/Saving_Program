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

import com.ldcgroup.bo.PointBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.util.PointComparator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberPointGridAction extends ActionSupport implements Preparable, SessionAware, ParameterAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private PointBo pointBo;
	private List<Point> pointList;
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
	
	public MemberPointGridAction() {
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

		if (this.pointList == null) {
			this.pointList = new ArrayList<Point>();
		}
		
		if (this.userdata == null) {
			this.userdata = new HashMap<String, Object>();
		}
	}
	
	@Override
	public String execute() {
		String returnValue = ERROR;
		
		Member member = (Member) this.session.get("S_Member");
		
		if (member != null) {
			List<Point> memberPointList = this.pointBo.list(member);
	
			// Check for search operation
			if (getSearchField() != null) {
				PointComparator pointComparator = new PointComparator();				
				for (int i = 0; i < memberPointList.size(); i++) {
					Point point = memberPointList.get(i);
					if (pointComparator.isMatch(point, this.searchField, this.searchOper, this.searchString)) {
						this.pointList.add(point);
					}				
				}
			} else {
				this.pointList.addAll(memberPointList);
			}
			
			if (this.pointList != null) {
				if (getSord() != null && getSord().equalsIgnoreCase("asc")) {
					Collections.sort(pointList, new PointComparator(sidx));				
				}
				if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
					Collections.sort(pointList, new PointComparator(sidx));
					Collections.reverse(pointList);
				}
			}
			
			double grandTotal = 0;
			for (int i = 0; i < pointList.size(); i++) {
				Point point = pointList.get(i);
				grandTotal += point.getValue();
			}
			
			Object totalCount = pointList.size();
			this.record = Integer.valueOf(Integer.parseInt((totalCount == null) ? "0" : totalCount.toString()));
			this.total = Integer.valueOf((int) Math.ceil(this.record.doubleValue() / this.rows.doubleValue()));
			
			int fromIndex = ((this.page > 0) ? ((this.page - 1) * this.rows) : 0 );
			int toIndex	= (((this.page * this.rows) > this.record) ? (this.record) : (this.page * this.rows));
			pointList = pointList.subList(fromIndex, toIndex);
			
			double total = 0;
			for (int i = 0; i < pointList.size(); i++) {
				Point point = pointList.get(i);
				total += point.getValue();
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.00");
			userdata.put("company.cmp_description", "Sub Total:");
			userdata.put("value", total);
			userdata.put("settlement_date", "Grand Total:");
			userdata.put("creation_date", formatter.format(grandTotal));
			
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.task.points.zero"));
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

	public PointBo getPointBo() {
		return this.pointBo;
	}

	public void setPointBo(PointBo pointBo) {
		this.pointBo = pointBo;
	}
	
	public List<Point> getPointList() {
		return this.pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
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
