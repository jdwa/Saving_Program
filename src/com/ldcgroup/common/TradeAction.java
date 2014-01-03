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
import com.ldcgroup.bo.CategoryBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.bo.TradeBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;
import com.ldcgroup.util.Definition;

public class TradeAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Trade ID, for Detail, Delete action
	private Trade trade; // For Add, Update action
	private Date settlement_date;
	private CategoryBo categoryBo;
	private StatementBo statementBo;
	private TradeBo tradeBo;
	private TypeBo typeBo;
	private List<Category> categoryList;
	private List<Statement> statementList; 
	private List<Trade> tradeList; // For List action
	private List<Type> typeList;
	
	public TradeAction() {
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
		
		if (this.categoryList == null) {
			this.categoryList = getCategoryBo().list();
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
		
		if (this.typeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.typeBo = (TypeBo) cxt.getBean("typeBo");
		}
		
		if (this.typeList == null) {
			this.typeList = getTypeBo().list();
		}	
	}	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
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
	
	public String add() {
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
		
		trade.setValid(Boolean.TRUE);
		trade.setCategory(getCategoryBo().findByNo(this.trade.getCategory().getCategory_no()));
		trade.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
		trade.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getTradeBo().add(trade);
		trade = tradeBo.findByNo(trade.getTx_no());
		this.tradeList.clear();
		this.tradeList.add(trade);
		this.statementList.clear();
		this.statementList.addAll(statementBo.list(trade));
		
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
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
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.trade = getTradeBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Trade orgTrade = getTradeBo().findById(trade.getId());
		
		Date now = new Date(); // Update time
		orgTrade.setValid(trade.getValid());
		orgTrade.setRemark(trade.getRemark());
		orgTrade.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
		orgTrade.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgTrade.setFund(trade.getFund());
		
		getTradeBo().update(orgTrade);
		trade = getTradeBo().findById(trade.getId());
		this.tradeList.clear();
		this.tradeList.add(trade);
		this.statementList.clear();
		this.statementList.addAll(statementBo.list(trade));
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		trade = tradeBo.findById(getId());
		if (trade != null) {
			this.tradeList.clear();
			this.tradeList.add(trade);
			this.statementList.clear();
			this.statementList.addAll(statementBo.list(trade));
			for (int i = 0; i < this.statementList.size(); i++) {
				Statement statement = this.statementList.get(i);
				getStatementBo().delete(statement.getId());
			}
			getTradeBo().delete(getId());
			this.addActionMessage(getText("action.system.delete.data"));
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

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
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
}
