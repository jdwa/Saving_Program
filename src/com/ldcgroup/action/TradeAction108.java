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
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.ldcgroup.util.Definition;
import com.ldcgroup.util.PropertyBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Trade Action 108 : 定額提領（單筆）
 * @author jdwa
 */
public class TradeAction108 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private PropertyBean propertyBean;
	private CategoryBo categoryBo;
	private MemberBo memberBo;
	private StatementBo statementBo;
	private Member member;
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
	private int withdrawAmount;

	public TradeAction108() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.propertyBean == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.propertyBean = (PropertyBean) cxt.getBean("propertyBean");
		}
		
		if (this.categoryBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.categoryBo = (CategoryBo) cxt.getBean("categoryBo");
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

		List<Statement> statements = new ArrayList<Statement>();
		this.member = getMemberBo().findByAccount(this.member.getAccount());
		
		double type001Sum = 0 , type001Withdraw = 0; // 員工提撥 "001"
		double type002Sum = 0 , type002Withdraw = 0; // 公司提撥 "002"
		//-- double type003Sum = 0 , type003Withdraw = 0; // 紅利分配 "003"
		//-- double type004Sum = 0 , type004Withdraw = 0; // 公司撥補 "004"
		//-- double type005Sum = 0 , type005Withdraw = 0; // 利息撥補 "005"

		// 檢查年資		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.member.getCreation_date());
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		int startY = calendar.get(Calendar.YEAR);  
		int startM = calendar.get(Calendar.MONTH);  
		  
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		int endY = calendar.get(Calendar.YEAR);  
		int endM = calendar.get(Calendar.MONTH);  
			
		int period = propertyBean.getPeriod();
		if (((endY - startY) > period) || (((endY - startY) == period) && ((endM - startM) >=0))) {
			this.statementList = statementBo.list(this.member);
			Iterator<Statement> i = this.statementList.iterator();
			// 符合計畫年資條件
			while (i.hasNext()) {
				Statement statement = (Statement) i.next();
				// 結算日後的提撥資料不列入計算
				if (statement.getSettlement_date().getTime() > trade.getSettlement_date().getTime()) continue;
				
				if ("001".equals(statement.getType().getType_no())) {
					type001Sum += statement.getFund();
				} else if ("002".equals(statement.getType().getType_no())) {
					type002Sum += statement.getFund();
				}
			}
	
			String withdrawType = getText("category.102"); // 定額提領
			
			// Add statement (type001：員工提撥) for member
			if (type001Sum != 0) {
				Type type = getTypeBo().findByNo("001");
				//檢查帳戶餘額，不能提領超過餘額。
				if (type001Sum > Math.floor(this.withdrawAmount * 0.2)) {
					type001Withdraw = Math.floor(this.withdrawAmount * 0.2) * (-1);
				} else {
					type001Withdraw = type001Sum * (-1);
				}
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type001Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
			}
	
			// Add statement (type002：公司提撥) for member
			if (type002Sum != 0) {
				Type type = getTypeBo().findByNo("002");
				//檢查帳戶餘額，不能提領超過餘額。
				if (type002Sum > Math.floor(this.withdrawAmount * 0.8)) {
					type002Withdraw = Math.floor(this.withdrawAmount * 0.8) * (-1);
				} else {
					type002Withdraw = type002Sum * (-1);
				}
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type002Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
			}
	
		} else {
			addActionError("[" + member.getAccount() + "]," + getText("member.seniority.less.than.qualified.years", (new String[]{(new Integer(period)).toString()}) ));
			statements.clear();
		}
		
		this.tradeList.clear();
		this.statementList.clear();

		if (statements.size() > 0) {
			// Add trade data
			this.trade.setFund(type001Withdraw + type002Withdraw);
			trade.setValid(Boolean.TRUE);
			trade.setCategory(getCategoryBo().findByNo("102"));
			trade.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			trade.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addTradeStatementList(trade, statements);
			trade = tradeBo.findByNo(trade.getTx_no());
			this.tradeList.add(trade);
			this.statementList.addAll(statementBo.list(trade));
			this.session.put("S_TradeList", tradeList);
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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
	
	public MemberBo getMemberBo() {
		return this.memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
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

	public TransactionServiceBo getTransactionServiceBo() {
		return transactionServiceBo;
	}

	public void setTransactionServiceBo(TransactionServiceBo transactionServiceBo) {
		this.transactionServiceBo = transactionServiceBo;
	}

	public int getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(int withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
}
