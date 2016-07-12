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

import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Trade Action Invalidate : µù¾P¥æ©ö
 * @author jdwa
 */
public class TradeActionInvalidate extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private StatementBo statementBo;
	private Trade trade;
	private TradeBo tradeBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Statement> statementList;
	private List<Trade> tradeList;

	public TradeActionInvalidate() {
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
		
		if (this.transactionServiceBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.transactionServiceBo = (TransactionServiceBo) cxt.getBean("transactionServiceBo");
		}
		
		if (this.statementList == null) {
			this.statementList = new ArrayList<Statement>();
		}
		
		if (this.tradeList == null) {
			this.tradeList = getTradeBo().listValidate();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		Date now = new Date();		
		List<Statement> statements = new ArrayList<Statement>();
		trade = getTradeBo().findByNo(trade.getTx_no());
		
		if ((trade != null) && (trade.getValid().booleanValue())) {
			List<Statement> tradeStatements = getStatementBo().list(trade);
			for (int i = 0; i < tradeStatements.size(); i++) {
				Statement statement = new Statement();
				statement.setCompany(tradeStatements.get(i).getCompany());
				statement.setMember(tradeStatements.get(i).getMember());
				statement.setTrade(this.trade);
				statement.setType(tradeStatements.get(i).getType());
				statement.setFund(tradeStatements.get(i).getFund() * (-1));
				statement.setSettlement_date(tradeStatements.get(i).getSettlement_date());
				statement.setCreation_date(now);
				statement.setRemark(trade.getRemark() + "[" + getText("trade.action.invalidate") + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);				
			}
			
		}
		
		this.tradeList.clear();
		this.statementList.clear();

		if (statements.size() > 0) {
			// Total amount would be 0.0 after invalidate
			this.trade.setFund(0.0);
			// Add trade data
			trade.setValid(Boolean.FALSE);
			//trade.setCategory(); Do not change the Category
			//trade.setCompany(((Member)this.session.get("CurrentMember")).getCompany()); Do not change the Company
			trade.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addTradeStatementList(trade, statements);
			trade = tradeBo.findByNo(trade.getTx_no());
			this.tradeList.add(trade);
			this.statementList.addAll(statementBo.list(trade));
			this.session.put("S_TradeList", tradeList);
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
				this.session.put("S_TradeList", null);
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
	
	public TransactionServiceBo getTransactionServiceBo() {
		return transactionServiceBo;
	}

	public void setTransactionServiceBo(TransactionServiceBo transactionServiceBo) {
		this.transactionServiceBo = transactionServiceBo;
	}
}
