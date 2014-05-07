package com.ldcgroup.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.CategoryBo;
import com.ldcgroup.bo.CompanyBo;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.RoleBo;
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
import com.ldcgroup.util.CryptUtils;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Trade Action 02 : 每月提撥（批次）
 * @author jdwa
 */
public class TradeAction002 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Trade trade;
	private Date settlement_date;
	private CategoryBo categoryBo;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private RoleBo roleBo;
	private StatementBo statementBo;
	private TradeBo tradeBo;
	private TypeBo typeBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Category> categoryList;
	private List<Member> memberList;
	private List<Statement> statementList;
	private List<Trade> tradeList;
	private List<Type> typeList;
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;

	public TradeAction002() {
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

		List<Member> members = new ArrayList<Member>();
		List<Statement> statements = new ArrayList<Statement>();
		List<String> lines = FileUtils.readLines(fileUpload, "UTF-8");
		double type001Sum = 0; // 員工提撥 "001"
		double type002Sum = 0; // 公司提撥 "002"
		// double type003Sum = 0; // 紅利分配 "003"
		// double type004Sum = 0; // 公司撥補 "004"
		// double type005Sum = 0; // 利息撥補"005"
		// Do not parse the 1st line (for header).
		for (int idx = 1; idx < lines.size(); idx++) {
			String line = (String)lines.get(idx);
			String[] data = line.split("\t");
			
			if (data.length == 0) {
				// Blank line.
				continue;
			} else if (data.length < 11) {
				addActionError("[" + line + "]" + getText("data.format.error"));
				statements.clear();
				break;
			}
			
			/*
			for (int i = 0; i < data.length; i++) {
				java.lang.System.out.println("Parsing data[" + i + "] ==> [" + data[i].trim() + "]");
			}
			*/
			
			double member_fund = Double.parseDouble(data[7].trim());

			Company company = getCompanyBo().findByNo(data[9].trim());
			if (company == null) {
				addActionError("[" + data[9].trim() + "]" + getText("company.not.exist"));
				statements.clear();
				break;
			}
			
			String account = data[10].trim();
			Member member = getMemberBo().findByAccount(account);
			if (member == null) {
				// 自動新增帳號
				member = new Member();
				member.setAccount(account);
				member.setCreation_date(now);
				// Set password
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(member.getAccount(), sdf.format(member.getCreation_date()));
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(company);
				member.setRole(roleBo.findByCode(Definition.ROLE_NORMAL));
				member.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				members.add(member);
				//getMemberBo().add(member);	
			} else if (!member.getCompany().getCmp_no().equals(company.getCmp_no())) {
				addActionMessage("[" + account + "]" + getText("member.assign.to.company") + "[" + company.getCmp_description() + "]");
				member.setCompany(company);
				getMemberBo().update(member);
				/*--
				addActionError("[" + account + "]" + getText("member.not.belong.this.company") + "[" + company.getCmp_description() + "]");
				statements.clear();
				break;
				--*/
			}
			
			if ((member != null) && (member.getRole().getRole_code().equals(Definition.ROLE_NORMAL))) {
				// 員工提撥
				Type type01 = getTypeBo().findByNo("001");
				// 公司提撥
				Type type02 = getTypeBo().findByNo("002");
				
				// Add statement (type01：員工提撥) for member
				Statement statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type01);
				statement.setFund(member_fund);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + type01.getType_description() + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
				type001Sum += member_fund;
		
				// Add statement (type02：公司提撥) for member
				statement = new Statement();
				statement.setCompany(member.getCompany());
				statement.setMember(member);
				statement.setTrade(this.trade);
				statement.setType(type02);
				statement.setFund(member_fund*4);
				statement.setSettlement_date(trade.getSettlement_date());
				statement.setCreation_date(trade.getCreation_date());
				statement.setRemark(trade.getRemark() + "[" + type02.getType_description() + "]");
				statement.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				statements.add(statement);
				type002Sum += member_fund*4;
			} else {
				addActionError("[" + member.getAccount() + "]," + getText("member.not.qualified") + member.getRole().getRole_description());
				statements.clear();
				break;
			}
		}
		
		this.tradeList.clear();
		this.statementList.clear();
		
		if (statements.size() > 0) {
			// Total amount would be member_fund*5
			this.trade.setFund(type001Sum + type002Sum);
			// Add trade data
			trade.setValid(Boolean.TRUE);
			trade.setCategory(getCategoryBo().findByNo("001")); // Category is the same as "001".
			trade.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			trade.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addTradeStatementList(trade, statements, members);
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
	public void validate() {
		if (trade != null) {
			if (getTradeBo().findByNo(trade.getTx_no()) != null) {				
				addActionError(this.getText("errors.duplicate") + trade.getTx_no());
			}
		}
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<String, Object> getSession() {
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

	public StatementBo getStatementBo() {
		return this.statementBo;
	}

	public void setStatementBo(StatementBo statementBo) {
		this.statementBo = statementBo;
	}
	
	public TradeBo getTradeBo() {
		return this.tradeBo;
	}

	public void setTradeBo(TradeBo tradeBo) {
		this.tradeBo = tradeBo;
	}

	public TypeBo getTypeBo() {
		return this.typeBo;
	}

	public void setTypeBo(TypeBo typeBo) {
		this.typeBo = typeBo;
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
	
	public List<Trade> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<Trade> tradeList) {
		this.tradeList = tradeList;
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

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
}
