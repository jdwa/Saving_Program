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
import com.ldcgroup.bo.RateBo;
import com.ldcgroup.bo.RoleBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Rate;
import com.ldcgroup.model.Role;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Trade Action 107 : 退出提領
 * @author jdwa
 */
public class TradeAction107 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private CategoryBo categoryBo;
	private MemberBo memberBo;
	private RateBo rateBo;
	private RoleBo roleBo;
	private StatementBo statementBo;
	private Member member;
	private Trade trade;
	private Date settlement_date;
	private TradeBo tradeBo;
	private TypeBo typeBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Category> categoryList;
	private List<Member> memberList;
	private List<Rate> rateList;
	private List<Role> roleList;
	private List<Statement> statementList;
	private List<Trade> tradeList;
	private List<Type> typeList;

	public TradeAction107() {
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

		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}

		if (this.rateBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rateBo = (RateBo) cxt.getBean("rateBo");
		}
		
		if (this.roleBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.roleBo = (RoleBo) cxt.getBean("roleBo");
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
		
		if (this.rateList == null) {
			this.rateList = getRateBo().list();
		}

		if (this.roleList == null) {
			this.roleList = getRoleBo().list();
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
		double type003Sum = 0 , type003Withdraw = 0; // 紅利分配 "003"
		double type004Sum = 0 , type004Withdraw = 0; // 公司撥補 "004"
		double type005Sum = 0 , type005Withdraw = 0; // 利息撥補"005"
		double cur_type005Sum = 0 , cur_type005Withdraw = 0; // 利息撥補"005"

		Calendar tradeCalendar = Calendar.getInstance();
		tradeCalendar.setTime(trade.getSettlement_date());						
		
		boolean breakloop = false;
		this.statementList = statementBo.list(this.member);
		Iterator<Statement> i = this.statementList.iterator();
		while (i.hasNext() && (!breakloop)) {
			Statement statement = (Statement) i.next();
			if ("001".equals(statement.getType().getType_no())) {
				Calendar indexCalendar = Calendar.getInstance();
				indexCalendar.setTime(statement.getSettlement_date());
				indexCalendar.add(Calendar.MONTH, 1); // 下個月開始計息
				while (!indexCalendar.after(tradeCalendar)) {
					int idxYear = indexCalendar.get(Calendar.YEAR);
					//-- System.out.println("ID : " + statement.getId() + ", Fund : [" + statement.getFund() + "], Year-Month-Day : [" + indexCalendar.get(Calendar.YEAR) + "-" + (indexCalendar.get(Calendar.MONTH) + 1) + "-" + indexCalendar.get(Calendar.DATE) + "]");
					Rate rate = getRateBo().findByYear(idxYear);
					if ( rate != null) {
						cur_type005Sum += statement.getFund() * (rate.getRate_value()/100/12);
						//-- System.out.println("Year : [" + idxYear + "], interest : " + cur_type005Sum);		
					} else {
						addActionError(getText("rate.year.not.exist") + idxYear);
						returnValue = INPUT;
						breakloop = true;
						break;
					}
					indexCalendar.add(Calendar.MONTH, 1);
				}

				/*--
				for (int idx = 0; idx < this.rateList.size(); idx++) {
					Rate rate = rateList.get(idx);
					calendar.setTime(rate.getRate_date());
					int rYear = calendar.get(Calendar.YEAR);
					if (sYear <= rYear) {
						cur_type005Sum += statement.getFund() * (rate.getRate_value()/100);
					}
				}
				--*/
				type001Sum += statement.getFund();
			} else if ("002".equals(statement.getType().getType_no())) {
				type002Sum += statement.getFund();
			} else if ("003".equals(statement.getType().getType_no())) {
				type003Sum += statement.getFund();
			} else if ("004".equals(statement.getType().getType_no())) {
				type004Sum += statement.getFund();
			} else if ("005".equals(statement.getType().getType_no())) {
				type005Sum += statement.getFund();
			}
		}
		
		if (!breakloop) {
			
			String withdrawType = getText("category.107"); // 退出提領
			
			// Add statement (type001：員工提撥) for member
			if (type001Sum != 0) {
				Type type = getTypeBo().findByNo("001");
				type001Withdraw = Math.floor(type001Sum) * (-1);
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
				type002Withdraw = Math.floor(type002Sum) * (-1);
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type002Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + getText("trade.transfer.to.company.account") + "][" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
			}
	
			// Add statement (type003：紅利分配) for member
			if (type003Sum != 0) {
				Type type = getTypeBo().findByNo("003");
				type003Withdraw = Math.floor(type003Sum) * (-1);
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type003Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + getText("trade.transfer.to.company.account") + "][" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
			}
	
			// Add statement (type004：公司撥補) for member
			if (type004Sum != 0) {
				Type type = getTypeBo().findByNo("004");
				type004Withdraw = Math.floor(type004Sum) * (-1);
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type004Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + getText("trade.transfer.to.company.account") + "][" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
			}
			
			// Add statement (type005：利息撥補) for member
			if (type005Sum != 0) {
				Type type = getTypeBo().findByNo("005");
				type005Withdraw = Math.floor(type005Sum) * (-1);
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type005Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
			}
			
			Member returnAccountHQ = this.memberBo.findByAccount(Definition.HQ_RETURN);
			Member returnAccount = this.memberBo.findReturnAccount(member.getCompany());
			// Add statement (type002：公司提撥) for defaultMember
			if (type002Sum != 0) {
				Type type = getTypeBo().findByNo("002");
				type002Withdraw = Math.floor(type002Sum);
				Statement statement = new Statement();
				statement.setCompany(returnAccount.getCompany());
				statement.setMember(returnAccount);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type002Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + getText("trade.transfer.from") + member.getAccount() + "][" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
				
				Statement statementReturn = new Statement();
				statementReturn.setCompany(returnAccount.getCompany());
				statementReturn.setMember(returnAccount);
				statementReturn.setTrade(this.trade);
				statementReturn.setType(type);
				statementReturn.setFund(type002Withdraw * (-1));
				statementReturn.setSettlement_date(trade.getSettlement_date());
				statementReturn.setCreation_date(trade.getCreation_date());
				statementReturn.setRemark(trade.getRemark() + "[" + getText("trade.company.account.settle") + returnAccount.getAccount() + "][" + withdrawType + "]");
				statementReturn.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statementReturn);
			}
	
			// Add statement (type003：紅利分配) for defaultMember
			if (type003Sum != 0) {
				Type type = getTypeBo().findByNo("003");
				type003Withdraw = Math.floor(type003Sum);
				Statement statement = new Statement();
				statement.setCompany(returnAccountHQ.getCompany());
				statement.setMember(returnAccountHQ);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type003Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + getText("trade.transfer.from") + member.getAccount() + "][" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
	
				Statement statementReturn = new Statement();
				statementReturn.setCompany(returnAccountHQ.getCompany());
				statementReturn.setMember(returnAccountHQ);
				statementReturn.setTrade(this.trade);
				statementReturn.setType(type);
				statementReturn.setFund(type003Withdraw *(-1));
				statementReturn.setSettlement_date(trade.getSettlement_date());
				statementReturn.setCreation_date(trade.getCreation_date());
				statementReturn.setRemark(trade.getRemark() + "[" + getText("trade.company.account.settle") + returnAccountHQ.getAccount() + "][" + withdrawType + "]");
				statementReturn.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statementReturn);
			}
	
			// Add statement (type004：公司撥補) for defaultMember
			if (type004Sum != 0) {
				Type type = getTypeBo().findByNo("004");
				type004Withdraw = Math.floor(type004Sum);
				Statement statement = new Statement();
				statement.setCompany(returnAccountHQ.getCompany());
				statement.setMember(returnAccountHQ);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type004Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + getText("trade.transfer.from") + member.getAccount() + "][" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
	
				Statement statementReturn = new Statement();
				statementReturn.setCompany(returnAccountHQ.getCompany());
				statementReturn.setMember(returnAccountHQ);
				statementReturn.setTrade(this.trade);
				statementReturn.setType(type);
				statementReturn.setFund(type004Withdraw *(-1));
				statementReturn.setSettlement_date(trade.getSettlement_date());
				statementReturn.setCreation_date(trade.getCreation_date());
				statementReturn.setRemark(trade.getRemark() + "[" + getText("trade.company.account.settle") + returnAccountHQ.getAccount() + "][" + withdrawType + "]");
				statementReturn.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statementReturn);
			}
			
			// Add statement (type005：利息撥補) for defaultMember
			if (cur_type005Sum != 0) {
				Type type = getTypeBo().findByNo("005");
				cur_type005Withdraw = Math.floor(cur_type005Sum) * (-1);
				Statement statement = new Statement();
				statement.setCompany(returnAccountHQ.getCompany());
				statement.setMember(returnAccountHQ);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(cur_type005Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + getText("trade.transfer.from") + returnAccountHQ.getAccount() + "][" + withdrawType + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
				
				// 公司撥補利息支出  type004：公司撥補
				type = getTypeBo().findByNo("004");
				Statement statementAdd = new Statement();
				statementAdd.setCompany(returnAccountHQ.getCompany());
				statementAdd.setMember(returnAccountHQ);
				statementAdd.setTrade(this.trade);
				statementAdd.setType(type);
				statementAdd.setFund(cur_type005Withdraw * (-1)); // 公司撥補等額利息支出
				statementAdd.setSettlement_date(trade.getSettlement_date());
				statementAdd.setCreation_date(trade.getCreation_date());
				statementAdd.setRemark(trade.getRemark() + "[" + getText("trade.transfer.into") + returnAccountHQ.getAccount() + "][" + withdrawType + "]");
				statementAdd.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statementAdd);
			}
		}

		this.tradeList.clear();
		this.statementList.clear();

		if ((statements.size() > 0) && (!breakloop)) {
			// Add trade data
			this.trade.setFund((type001Sum + type002Sum + type003Sum + type004Sum + type005Sum) * (-1));
			trade.setValid(Boolean.TRUE);
			trade.setCategory(getCategoryBo().findByNo("107"));
			trade.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			trade.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			this.member.setRole(getRoleBo().findByCode(Definition.ROLE_ABANDON));
			getTransactionServiceBo().addTradeStatementList(trade, statements, member);
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

	public RateBo getRateBo() {
		return this.rateBo;
	}

	public void setRateBo(RateBo rateBo) {
		this.rateBo = rateBo;
	}

	public List<Rate> getRateList() {
		return rateList;
	}

	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
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
	
	public RoleBo getRoleBo() {
		return this.roleBo;
	}

	public void setRoleBo(RoleBo roleBo) {
		this.roleBo = roleBo;
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
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
}
