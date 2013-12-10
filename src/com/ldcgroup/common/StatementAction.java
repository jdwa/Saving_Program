package com.ldcgroup.common;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldcgroup.bo.CompanyBo;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.ldcgroup.util.Definition;

public class StatementAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
	private Long id; // Statement ID, for Detail, Delete action
	private Statement statement; // For Add, Update action
	private Date settlement_date;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private StatementBo statementBo;
	private TypeBo typeBo;
	private TradeBo tradeBo;
	private List<Company> companyList; // For List action
	private List<Member> memberList; 
	private List<Statement> statementList; 
	private List<Type> typeList;
	private List<Trade> tradeList;
	
	public StatementAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {		
		if (this.companyBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.companyBo = (CompanyBo) cxt.getBean("companyBo");
		}
		
		if (this.companyList == null) {
			this.companyList = getCompanyBo().list();
		}
		
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}
		
		if (this.memberList == null) {
			this.memberList = getMemberBo().list();
		}

		if (this.statementBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.statementBo = (StatementBo) cxt.getBean("statementBo");
		}

		if (this.statementList == null) {
			this.statementList = getStatementBo().list();
		}
		
		if (this.typeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.typeBo = (TypeBo) cxt.getBean("typeBo");
		}
		
		if (this.typeList == null) {
			this.typeList = getTypeBo().list();
		}

		if (this.tradeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.tradeBo = (TradeBo) cxt.getBean("tradeBo");
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
		return SUCCESS;
	}
	
    // Validation
	@Override
	public void validate() {
		// Check validation here.
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
	
	public String add() {
		Trade trade = getTradeBo().findByNo(this.statement.getTrade().getTx_no());
		
		Date now = new Date();
		if (statement.getCreation_date() == null){
			statement.setCreation_date(now);
		}
		
		if (statement.getSettlement_date() == null) {
			if (trade.getSettlement_date() != null) {
				statement.setSettlement_date(trade.getSettlement_date());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.roll(Calendar.DATE, -1);
				statement.setSettlement_date(calendar.getTime());
			}
		}
		
		statement.setMember(getMemberBo().findByAccount(this.statement.getMember().getAccount()));
		statement.setCompany(getCompanyBo().findByNo(this.statement.getCompany().getCmp_no()));
		statement.setTrade(trade);
		statement.setType(getTypeBo().findByNo(this.statement.getType().getType_no()));
		statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		trade.setFund(trade.getFund() + statement.getFund());
		getTradeBo().update(trade);
		getStatementBo().add(statement);
		list();
		
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.statementList = getStatementBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.statement = getStatementBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Statement orgStatement = getStatementBo().findById(statement.getId());
		
		Date now = new Date(); // Update time
		orgStatement.setRemark(statement.getRemark());
		orgStatement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgStatement.setFund(statement.getFund());
		
		getStatementBo().update(orgStatement);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getStatementBo().findById(getId()) != null) {
			getStatementBo().delete(getId());
			list();
		} else {
			addActionError(this.getText("errors.data.not.exist") + getId());
		}
		return SUCCESS;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
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

	public CompanyBo getCompanyBo() {
		return companyBo;
	}

	public void setCompanyBo(CompanyBo companyBo) {
		this.companyBo = companyBo;
	}

	public MemberBo getMemberBo() {
		return memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
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

	public TradeBo getTradeBo() {
		return tradeBo;
	}

	public void setTradeBo(TradeBo tradeBo) {
		this.tradeBo = tradeBo;
	}
	
	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public List<Statement> getStatementList() {
		return this.statementList;
	}

	public void setStatementList(List<Statement> statementList) {
		this.statementList = statementList;
	}
	
	public List<Type> getTypeList() {
		return this.typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	public List<Trade> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<Trade> tradeList) {
		this.tradeList = tradeList;
	}
}
