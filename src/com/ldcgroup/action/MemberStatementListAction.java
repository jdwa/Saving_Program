package com.ldcgroup.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.CompanyBo;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Bar;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberStatementListAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private StatementBo statementBo;
	private TypeBo typeBo;
	private TradeBo tradeBo;
	private List<Bar> barList; 
	private List<Company> companyList; // For List action
	private List<Member> memberList; 
	private List<Statement> statementList; 
	private List<Type> typeList;
	private List<Trade> tradeList;
		
	public MemberStatementListAction() {
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

		if (this.typeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.typeBo = (TypeBo) cxt.getBean("typeBo");
		}
		
		if (this.tradeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.tradeBo = (TradeBo) cxt.getBean("tradeBo");
		}

		if (this.barList == null) {
			this.barList = new ArrayList<Bar>();
		}
		
		if (this.companyList == null) {
			this.companyList = getCompanyBo().list();
		}
		
		if (this.memberList == null) {
			this.memberList = getMemberBo().listNormal();
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
		if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
			Member member = (Member) this.session.get("CurrentMember");
			this.statementList = this.statementBo.list(member);

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			SimpleDateFormat sdFormat;			
			for (int i = 0; i < 9; i++) {
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.roll(Calendar.DATE, -1);	
				if (calendar.get(Calendar.MONTH) == 0) {
					sdFormat = new SimpleDateFormat("yyyy.MM");
				} else {
					sdFormat = new SimpleDateFormat("MM");
				}
				Bar bar = new Bar();
				bar.setTime(calendar.getTimeInMillis());
				bar.setLabel(sdFormat.format(calendar.getTime()));
				barList.add(bar);
			}

			for (int i = 0; i < statementList.size(); i++) {
				Statement statement = (Statement) statementList.get(i);
				for (int j = 0; j < barList.size(); j++) {
					Bar bar = (Bar) barList.get(j);
					if (statement.getSettlement_date().getTime() <= bar.getTime()) {
						if ("001".equals(statement.getType().getType_no())) {
							bar.setFund_type_001(bar.getFund_type_001() + statement.getFund());
						} else if ("002".equals(statement.getType().getType_no())) {
							bar.setFund_type_002(bar.getFund_type_002() + statement.getFund());	
						} else if ("003".equals(statement.getType().getType_no())) {
							bar.setFund_type_003(bar.getFund_type_003() + statement.getFund());	
						} else if ("004".equals(statement.getType().getType_no())) {
							bar.setFund_type_004(bar.getFund_type_004() + statement.getFund());	
						} else if ("005".equals(statement.getType().getType_no())) {
							bar.setFund_type_005(bar.getFund_type_005() + statement.getFund());	
						}
					} else {
						break;
					}
				}
			}
			
			Collections.reverse(barList);
			
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
	
	@SkipValidation
	public String initialize() throws Exception {
		return SUCCESS;
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

	public List<Bar> getBarList() {
		return barList;
	}

	public void setBarList(List<Bar> barList) {
		this.barList = barList;
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
