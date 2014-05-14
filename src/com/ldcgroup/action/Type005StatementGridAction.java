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

import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.ldcgroup.util.StatementComparator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class Type005StatementGridAction extends ActionSupport implements Preparable, SessionAware, ParameterAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private StatementBo statementBo;
	private TypeBo typeBo;
	private List<Statement> statementList;
	private Map<String, Object> userdata;

	protected Integer rows = new Integer(0);;
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
	
	public Type005StatementGridAction() {
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
		
		if (this.typeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.typeBo = (TypeBo) cxt.getBean("typeBo");
		}

		if (this.statementList == null) {
			this.statementList = new ArrayList<Statement>();
		}
		
		if (this.userdata == null) {
			this.userdata = new HashMap<String, Object>();
		}
	}
	
	@Override
	public String execute() {
		String returnValue = ERROR;
		
		Trade trade = (Trade) this.session.get("S_Trade");
		Type type = (Type)typeBo.findByNo("005");

		if (trade != null) { 
			List<Statement> typeStatementList = this.statementBo.list(trade, type);
	
			// Check for search operation
			if (getSearchField() != null) {
				StatementComparator statementComparator = new StatementComparator();				
				for (int i = 0; i < typeStatementList.size(); i++) {
					Statement statement = typeStatementList.get(i);
					if (statementComparator.isMatch(statement, this.searchField, this.searchOper, this.searchString)) {
						this.statementList.add(statement);
					}				
				}
			} else {
				this.statementList.addAll(typeStatementList);
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
			
			NumberFormat formatter = new DecimalFormat("#,###,##0.00");
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
	
	public TypeBo getTypeBo() {
		return this.typeBo;
	}

	public void setTypeBo(TypeBo typeBo) {
		this.typeBo = typeBo;
	}
	
	public List<Statement> getStatementList() {
		return this.statementList;
	}

	public void setStatementList(List<Statement> statementList) {
		this.statementList = statementList;
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
