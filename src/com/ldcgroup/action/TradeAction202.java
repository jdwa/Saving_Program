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
import com.ldcgroup.bo.RateBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Rate;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Trade Action 202 : 年度分公司績效考核
 * @author jdwa
 */
public class TradeAction202 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private CategoryBo categoryBo;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private RateBo rateBo;
	private StatementBo statementBo;
	private Company company;
	private Trade trade;
	private Date settlement_date;
	private String appraisal;
	private int appraisalYear;
	private TradeBo tradeBo;
	private TypeBo typeBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Category> categoryList;
	private List<Company> companyList;
	private List<Rate> rateList;
	private List<Statement> statementList;
	private List<Trade> tradeList;
	private List<Type> typeList;
	private List<String> yearList; // For years list
	private List<String> appraisalList; // For years appraisal list

	public TradeAction202() {
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
		
		if (this.rateBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rateBo = (RateBo) cxt.getBean("rateBo");
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
		
		if (this.rateList == null) {
			this.rateList = getRateBo().list();
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
		
		if (this.yearList == null) {
			this.yearList = new ArrayList<String>();
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			for (int i = year-1; i < year+1; i++) {
				this.yearList.add((new Integer(i)).toString());
			}
		}
		
		if (this.appraisalList == null) {
			this.appraisalList = new ArrayList<String>();
			this.appraisalList.add(getText("appraisal.20_off"));
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
		
		double type002WithdrawAmount = 0; // 公司提撥 "002"
		this.company = getCompanyBo().findByNo(this.company.getCmp_no());
		List<Statement> statements = new ArrayList<Statement>();
		List<Member> memberList = getMemberBo().listNormal(this.company);
		for (int idx = 0; idx < memberList.size(); idx++) {
			// 考核所有分公司員工帳號	
			Member member = (Member) memberList.get(idx);

			// double type001Sum = 0 , type001Withdraw = 0; // 員工提撥 "001"
			double type002Sum = 0 , type002Withdraw = 0 , type002WithdrawAlready = 0; // 公司提撥 "002"
			// double type003Sum = 0 , type003Withdraw = 0; // 紅利分配 "003"
			// double type004Sum = 0 , type004Withdraw = 0; // 公司撥補 "004"
			// double type005Sum = 0 , type005Withdraw = 0; // 利息撥補"005"

			this.statementList = statementBo.list(member);
			Iterator<Statement> i = this.statementList.iterator();
			while (i.hasNext()) {
				Statement statement = (Statement) i.next();
				if ("002".equals(statement.getType().getType_no())) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(statement.getSettlement_date());
					int sYear = calendar.get(Calendar.YEAR);
					if (sYear == this.appraisalYear) {
						if (statement.getFund() >= 0) {
							type002Sum += statement.getFund();
						} else {
							type002WithdrawAlready += statement.getFund();
						}
					}
				}
			}
	
			String withdrawType = getText("category.202"); // 年度分公司績效考核
	
			Type type = getTypeBo().findByNo("002");
			if (getText("appraisal.20_off").equals(this.appraisal)) {
				type002Withdraw = Math.floor(type002Sum) * (-0.2);
				withdrawType += "(-20%)";
			}
			
			// 扣到 0 為止。
			if ((type002Sum + type002Withdraw + type002WithdrawAlready) < 0) {
				type002Withdraw = (type002Sum + type002WithdrawAlready) * (-1);
			}
	
			type002WithdrawAmount += type002Withdraw;
			
			// Add statement (type002：公司提撥) for member
			if (type002Sum != 0) {
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type002Withdraw);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + withdrawType + "][" + getText("trade.transfer.to.company.account") + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
			}
	
			Member returnAccount = this.memberBo.findReturnAccount(member.getCompany());
			// Add statement (type002：公司提撥) for defaultMember
			if (type002Sum != 0) {
				Statement statement = new Statement();
				statement.setCompany(returnAccount.getCompany());
				statement.setMember(returnAccount);
				statement.setTrade(this.trade);
				statement.setType(type);
				statement.setFund(type002Withdraw * (-1));
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + withdrawType + "][" + getText("trade.transfer.from") + member.getAccount() + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);

				Statement statementReturn = new Statement();
				statementReturn.setCompany(returnAccount.getCompany());
				statementReturn.setMember(returnAccount);
				statementReturn.setTrade(this.trade);
				statementReturn.setType(type);
				statementReturn.setFund(type002Withdraw);
				statementReturn.setSettlement_date(trade.getSettlement_date());
				statementReturn.setCreation_date(trade.getCreation_date());
				statementReturn.setRemark(trade.getRemark() + "[" + getText("trade.company.account.settle") + returnAccount.getAccount() + "][" + withdrawType + "]");				
				statementReturn.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statementReturn);				
			}
		}
		
		this.tradeList.clear();
		this.statementList.clear();

		if (statements.size() > 0) {
			// Add trade data
			this.trade.setFund(type002WithdrawAmount);
			trade.setValid(Boolean.TRUE);
			trade.setCategory(getCategoryBo().findByNo("202"));
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
			returnValue = SUCCESS;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public CompanyBo getCompanyBo() {
		return this.companyBo;
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

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
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

	public int getAppraisalYear() {
		return appraisalYear;
	}

	public void setAppraisalYear(int appraisalYear) {
		this.appraisalYear = appraisalYear;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}

	public String getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(String appraisal) {
		this.appraisal = appraisal;
	}

	public List<String> getAppraisalList() {
		return appraisalList;
	}

	public void setAppraisalList(List<String> appraisalList) {
		this.appraisalList = appraisalList;
	}
}
