package com.ldcgroup.action;

import java.util.ArrayList;
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
	private List<Statement> statementList;
	private List<Trade> tradeList;

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
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		this.tradeList.clear();
		this.statementList.clear();

		trade = getTradeBo().findByNo(trade.getTx_no());
		
		if (trade != null) {
			this.tradeList.add(trade);
			this.statementList = getStatementBo().list(trade);
			this.session.put("S_Trade", trade);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.trade.statements.zero"));
			returnValue = SUCCESS;
		}
		
		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (trade != null) {
			if (getTradeBo().findByNo(trade.getTx_no()) == null) {				
				addActionError(this.getText("errors.data.not.exist") + trade.getTx_no());
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
	
}
