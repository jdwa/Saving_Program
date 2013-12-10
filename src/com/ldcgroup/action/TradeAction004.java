package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.CategoryBo;
import com.ldcgroup.bo.CompanyBo;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class TradeAction004 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private CategoryBo categoryBo;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private StatementBo statementBo;
	private Trade trade;
	private Date settlement_date;
	private TradeBo tradeBo;
	private TypeBo typeBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Category> categoryList;
	private List<Member> memberList;
	private List<Statement> statementList;
	private List<Trade> tradeList;
	private List<Type> typeList;
	private List<Company> companyList;

	public TradeAction004() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.categoryBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.categoryBo = (CategoryBo) cxt.getBean("categoryBo");
		}

		if (this.companyBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.companyBo = (CompanyBo) cxt.getBean("companyBo");
		}
		
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}	
		
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
		
		if (this.typeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.typeBo = (TypeBo) cxt.getBean("typeBo");
		}
		
		if (this.transactionServiceBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.transactionServiceBo = (TransactionServiceBo) cxt.getBean("transactionServiceBo");
		}
		
		if (this.categoryList == null) {
			this.categoryList = getCategoryBo().list();
		}
		
		if (this.companyList == null) {
			this.companyList = getCompanyBo().list();
		}

		if (this.memberList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
					this.memberList = getMemberBo().listNormal();
				} else {
					this.memberList = getMemberBo().listNormal(member.getCompany());
				}
			}			
		}
		
		if (this.statementList == null) {
			this.statementList = new ArrayList<Statement>();
		}
		
		if (this.tradeList == null) {
			this.tradeList = new ArrayList<Trade>();
		}
		
		if (this.typeList == null) {
			this.typeList = getTypeBo().list();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
				
		Date now = new Date();
		if (trade.getCreation_date() == null){
			trade.setCreation_date(now);
		}
		
		if (trade.getSettlement_date() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			trade.setSettlement_date(calendar.getTime());
		}
		
		// ¤½¥q¼·¸É
		Type type = getTypeBo().findByNo("004");

		// Sum of total statements
		List<Statement> statements = new ArrayList<Statement>();
		double sum = getStatementBo().getStatementSum();

		// Get remainder
		Member defaultMember = this.memberBo.findByAccount(Definition.HQ_REMAIN);
		double totalFund = trade.getFund();
		double remain = getStatementBo().getStatementSum(defaultMember);
		if (remain != 0) {
			totalFund += remain;			
			Statement statement = new Statement();
			statement.setCompany(defaultMember.getCompany());
			statement.setMember(defaultMember);
			statement.setTrade(trade);
			statement.setType(type);
			statement.setFund(remain * (-1));
			statement.setSettlement_date(trade.getSettlement_date());
			statement.setCreation_date(trade.getCreation_date());
			statement.setRemark(trade.getRemark() + "[" + getText("trade.remain.dividend") + "]"); 
			statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			statements.add(statement);
		}
		
		// Add statements for each Normal member
		double truncatedSum = 0;
		this.memberList = getMemberBo().listNormal();
		int totalMember = this.memberList.size(); 
		Iterator<Member> i = this.memberList.iterator();
		while (i.hasNext()) {
			Member member = (Member) i.next();
			double memberSum = getStatementBo().getStatementSum(member);
			double memberDiv = Math.floor((sum <= 0) ? (totalFund / totalMember) : (totalFund * memberSum / sum));
			truncatedSum += memberDiv;
			Statement statement = new Statement();
			statement.setCompany(member.getCompany());
			statement.setMember(member);
			statement.setTrade(trade);
			statement.setType(type);
			statement.setFund(memberDiv);
			statement.setSettlement_date(trade.getSettlement_date());
			statement.setCreation_date(trade.getCreation_date());
			statement.setRemark(trade.getRemark() + "[" + type.getType_description() + "]");
			statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			statements.add(statement);
		}
		
		// Set remainder
		// Member defaultMember = this.memberBo.findByAccount(Definition.HQ_REMAIN);
		Statement statement = new Statement();
		statement.setCompany(defaultMember.getCompany());
		statement.setMember(defaultMember);
		statement.setTrade(trade);
		statement.setType(type);
		statement.setFund(totalFund-truncatedSum);
		statement.setSettlement_date(trade.getSettlement_date());
		statement.setCreation_date(trade.getCreation_date());
		statement.setRemark(trade.getRemark() + "[" + type.getType_description() + "]");
		statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		statements.add(statement);
		
		this.tradeList.clear();
		this.statementList.clear();

		if (statements.size() > 0) {
			// Add trade data
			trade.setValid(Boolean.TRUE);
			trade.setCategory(getCategoryBo().findByNo("004"));
			trade.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			trade.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addTradeStatementList(trade, statements);
			trade = tradeBo.findByNo(trade.getTx_no());
			this.tradeList.add(trade);
			this.statementList.addAll(statementBo.list(trade));
			this.session.put("S_Trade", trade);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.trade.statements.zero"));
			returnValue = INPUT;
		}

		return returnValue;
	}

    // Validation
	@Override
	public void validate(){
		if (trade != null) {
			if (getTradeBo().findByNo(trade.getTx_no()) != null){				
				addActionError(this.getText("errors.duplicate") + trade.getTx_no());
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
	
	public CategoryBo getCategoryBo() {
		return this.categoryBo;
	}

	public void setCategoryBo(CategoryBo categoryBo) {
		this.categoryBo = categoryBo;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	public CompanyBo getCompanyBo() {
		return companyBo;
	}

	public void setCompanyBo(CompanyBo companyBo) {
		this.companyBo = companyBo;
	}

	public MemberBo getMemberBo() {
		return this.memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	public Date getSettlement_date() {
		if (this.settlement_date == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			this.settlement_date = calendar.getTime();
		}
		return settlement_date;
	}

	public void setSettlement_date(Date settlement_date) {
		this.settlement_date = settlement_date;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
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
	
	public TypeBo getTypeBo() {
		return this.typeBo;
	}

	public void setTypeBo(TypeBo typeBo) {
		this.typeBo = typeBo;
	}
	
	public List<Type> getTypeList() {
		return this.typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}
	
	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public TransactionServiceBo getTransactionServiceBo() {
		return transactionServiceBo;
	}

	public void setTransactionServiceBo(TransactionServiceBo transactionServiceBo) {
		this.transactionServiceBo = transactionServiceBo;
	}
}
