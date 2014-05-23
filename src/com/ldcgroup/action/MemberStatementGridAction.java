package com.ldcgroup.action;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.model.Bar;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.util.StatementComparator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberStatementGridAction extends ActionSupport implements Preparable, SessionAware, ParameterAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private StatementBo statementBo;
	private List<Bar> barList;
	private List<Statement> statementList;
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
	
	public MemberStatementGridAction() {
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

		if (this.barList == null) {
			this.barList = new ArrayList<Bar>();
		}
		
		if (this.statementList == null) {
			this.statementList = new ArrayList<Statement>();
		}
		
		if (this.userdata == null) {
			this.userdata = new HashMap<String, Object>();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		Member member = (Member) this.session.get("S_Member");
		
		if (member != null) {
			List<Statement> memberStatementList = this.statementBo.list(member);
	
			// Check for search operation
			if (getSearchField() != null) {
				StatementComparator statementComparator = new StatementComparator();				
				for (int i = 0; i < memberStatementList.size(); i++) {
					Statement statement = memberStatementList.get(i);
					if (statementComparator.isMatch(statement, this.searchField, this.searchOper, this.searchString)) {
						this.statementList.add(statement);
					}				
				}
			} else {
				this.statementList.addAll(memberStatementList);
			}
			
			if (this.statementList != null) {
				if (getSord() != null && getSord().equalsIgnoreCase("asc")) {
					Collections.sort(statementList, new StatementComparator(sidx));				
				}
				if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
					Collections.sort(statementList, new StatementComparator(sidx));
					Collections.reverse(statementList);
				}
			}
			
			// Set graph data.
			execute_graph();
			
			double grandTotal = 0;
			for (int i = 0; i < statementList.size(); i++) {
				Statement statement = statementList.get(i);
				grandTotal += statement.getFund();
			}
			
			Object totalCount = statementList.size();
			this.record = Integer.valueOf(Integer.parseInt((totalCount == null) ? "0" : totalCount.toString()));
			this.total = Integer.valueOf((int) Math.ceil(this.record.doubleValue() / this.rows.doubleValue()));
			
			int fromIndex = ((this.page > 0) ? ((this.page - 1) * this.rows) : 0 );
			int toIndex	= (((this.page * this.rows) > this.record) ? (this.record) : (this.page * this.rows));
			statementList = statementList.subList(fromIndex, toIndex);
			
			double total = 0;
			for (int i = 0; i < statementList.size(); i++) {
				Statement statement = statementList.get(i);
				total += statement.getFund();
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.00");
			userdata.put("company.cmp_description", "Sub Total:");
			userdata.put("fund", total);
			userdata.put("settlement_date", "Grand Total:");
			userdata.put("creation_date", formatter.format(grandTotal));
			
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.trade.statements.zero"));
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
