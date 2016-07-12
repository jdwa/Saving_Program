package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Trade Action Query : 查詢交易資料
 * @author jdwa
 */
public class TradeActionQuery extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private StatementBo statementBo;
	private Trade trade;
	private TradeBo tradeBo;
	private String query;
	private int queryYear;
	private List<Statement> statementList;
	private List<Trade> tradeList;
	private List<String> yearList; // For years list
	private List<String> queryList; // For query list

	public TradeActionQuery() {
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
		
		if (this.tradeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.tradeBo = (TradeBo) cxt.getBean("tradeBo");
		}
				
		if (this.statementList == null) {
			this.statementList = new ArrayList<Statement>();
		}
		
		if (this.tradeList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
					this.tradeList = getTradeBo().list();
				} else {
					this.tradeList = getTradeBo().list(member.getCompany());
				}
			}
		}
		
		if (this.yearList == null) {
			this.yearList = new ArrayList<String>();
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			for (int i = 2013; i < year+1; i++) {
				this.yearList.add((new Integer(i)).toString());
			}
		}
		
		if (this.queryList == null) {
			this.queryList = new ArrayList<String>();
			this.queryList.add(getText("query.trade.no"));
			this.queryList.add(getText("query.trade.year"));
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		List<Trade> orgTradeList = new ArrayList<Trade>();
		
		orgTradeList.addAll(tradeList);
		this.tradeList.clear();
		this.statementList.clear();

		if (getText("query.trade.no").equals(this.query)) {
			trade = getTradeBo().findByNo(trade.getTx_no());
			if (trade != null) {
				this.tradeList.add(trade);
				this.statementList = getStatementBo().list(trade);
				this.session.put("S_TradeList", tradeList);
				returnValue = SUCCESS;
			} else {
				addActionMessage(getText("action.trade.statements.zero"));
				returnValue = SUCCESS;
			}
		} else if (getText("query.trade.year").equals(this.query)) {
			for (int i = 0; i < orgTradeList.size(); i++){
				trade = orgTradeList.get(i);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(trade.getSettlement_date());
				int sYear = calendar.get(Calendar.YEAR);
				if (sYear == this.queryYear) {
					this.tradeList.add(trade);
					this.statementList = getStatementBo().list(trade);
				}	
			}
			this.session.put("S_TradeList", tradeList);
			returnValue = SUCCESS;
		}
		
		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (query == null) {
			addActionError(this.getText("errors.no.data.selected"));
			this.session.put("S_TradeList", null);
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

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	public TradeBo getTradeBo() {
		return this.tradeBo;
	}

	public void setTradeBo(TradeBo tradeBo) {
		this.tradeBo = tradeBo;
	}

	public List<Trade> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<Trade> tradeList) {
		this.tradeList = tradeList;
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
	
	public List<String> getYearList() {
		return yearList;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}

	public int getQueryYear() {
		return queryYear;
	}

	public void setQueryYear(int queryYear) {
		this.queryYear = queryYear;
	}

	public List<String> getQueryList() {
		return queryList;
	}

	public void setQueryList(List<String> queryList) {
		this.queryList = queryList;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
