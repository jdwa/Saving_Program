package com.ldcgroup.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.StatementBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.util.Definition;
import com.ldcgroup.util.MemberComparator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberGridAction extends ActionSupport implements Preparable, SessionAware, ParameterAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private MemberBo memberBo;
	private StatementBo statementBo;
	private List<Member> memberList;
	private List<Statement> statementList;
	private Map<String, Object> userdata;

	protected Integer rows = new Integer(0);
	protected Integer page = new Integer(0);;
	protected Integer total = new Integer(0);;
	protected Integer record = new Integer(0);;
	protected String sord;
	protected String sidx;
	protected String search;
	protected String searchField;
	protected String searchOper;
	protected String searchString;
	protected String filters;
	
	public MemberGridAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
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
			this.statementList = getStatementBo().list();
		}
		
		if (this.userdata == null) {
			this.userdata = new HashMap<String, Object>();
		}
	}
	
	@Override
	public String execute() {
		String returnValue = ERROR;
		
		Date now = new Date();
		List<Member> memberNormalList = new ArrayList<Member>();
		Member member = (Member) this.session.get("CurrentMember");
		if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
			memberNormalList = getMemberBo().listNormal();
		} else {
			memberNormalList = getMemberBo().listNormal(member.getCompany());
		}
		
		if (memberNormalList != null) {
			
			for (int i = 0; i < memberNormalList.size(); i++) {
				Date startDate = new Date();
				member = memberNormalList.get(i);				
				List<Statement> statementList = statementBo.list(member);
				Iterator<Statement> idx = statementList.iterator();
				while (idx.hasNext()) {
					Statement statement = (Statement) idx.next();
					if (statement.getSettlement_date().getTime() < startDate.getTime()) {
						startDate = statement.getSettlement_date();
					}
					// 計算符合資格之定期提領年資。以最近一次提領為計算年資之起始時間。
					if ("101".equals(statement.getTrade().getCategory().getCategory_no())
						|| "103".equals(statement.getTrade().getCategory().getCategory_no())
						|| "104".equals(statement.getTrade().getCategory().getCategory_no())
						|| "105".equals(statement.getTrade().getCategory().getCategory_no())
						|| "106".equals(statement.getTrade().getCategory().getCategory_no())
						|| "107".equals(statement.getTrade().getCategory().getCategory_no())) {
						double accumulation = ((double)(now.getTime() - statement.getTrade().getSettlement_date().getTime()) / (double)(86400000) / (double)(365));				
						if ((member.getAccumulation() == null) || (accumulation < member.getAccumulation().doubleValue())) {
							member.setAccumulation(accumulation);
						}
					}
				}
				
				// 設定員工加入年資為『定期提領』資格之年資預設值。
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startDate);
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.roll(Calendar.DATE, -1);					
				double accumulation = ((double)(now.getTime() - calendar.getTime().getTime()) / (double)(86400000) / (double)(365));
				member.setAccumulation(accumulation);
			}
			
			this.memberList.clear();
			// Check for search operation
			if (getSearchField() != null) {
				for (int i = 0; i < memberNormalList.size(); i++) {
					member = memberNormalList.get(i);					
					String[] fieldList = {"id","active","accumulation","account","company.cmp_description","role.role_description",
										  "creation_date","remark","timestamp"};
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String[] valueList = {member.getId().toString(), member.getActive().toString(), member.getAccumulation().toString(), member.getAccount(),
										  member.getCompany().getCmp_description(), member.getRole().getRole_description(),
										  sdf.format(member.getCreation_date()),member.getRemark(), member.getTimestamp()};
					for (int j = 0; j < fieldList.length; j++) {
						if (getSearchField().equals(fieldList[j])) {
							if (getSearchOper() != null && getSearchOper().equals("eq")) {
								if (valueList[j].equals(getSearchString())) {
									this.memberList.add(member);
								}
							} else if (getSearchOper() != null && getSearchOper().equals("ne")) {
								if (!valueList[j].equals(getSearchString())) {
									this.memberList.add(member);
								}
							} else if (getSearchOper() != null && getSearchOper().equals("lt")) {
								if (valueList[j].compareTo(getSearchString()) < 0) {
									this.memberList.add(member);
								}
							} else if (getSearchOper() != null && getSearchOper().equals("gt")) {
								if (valueList[j].compareTo(getSearchString()) > 0) {
									this.memberList.add(member);
								}
							}
						}
					}
				}
			} else {
				this.memberList.addAll(memberNormalList);
			}			
			
			if (this.memberList != null) {
				if (getSord() != null && getSord().equalsIgnoreCase("asc")) {
					Collections.sort(memberList, new MemberComparator(sidx));				
				}
				if (getSord() != null && getSord().equalsIgnoreCase("desc")) {
					Collections.sort(memberList, new MemberComparator(sidx));
					Collections.reverse(memberList);
				}
			}			
			
			Object totalCount = memberList.size();
			this.record = Integer.valueOf(Integer.parseInt((totalCount == null) ? "0" : totalCount.toString()));
			this.total = Integer.valueOf((int) Math.ceil(this.record.doubleValue() / this.rows.doubleValue()));
			
			int fromIndex = ((this.page > 0) ? ((this.page - 1) * this.rows) : 0 );
			int toIndex	= (((this.page * this.rows) > this.record) ? (this.record) : (this.page * this.rows));
			memberList = memberList.subList(fromIndex, toIndex);

			userdata.put("company.cmp_description", "Total:");
			userdata.put("role.role_description", totalCount);
			
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.data.zero"));
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
	
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		  if (parameters.get("_") != null) {
			  parameters.remove("_");
		  }
		  if (parameters.get("_search") != null) {
			  parameters.remove("_search");
		  }
		  if (parameters.get("nd") != null) {
			  parameters.remove("nd");
		  }
	}

	@SkipValidation
	public String initialize() throws Exception {
		return SUCCESS;
	}

	public MemberBo getMemberBo() {
		return this.memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}
	
	public List<Member> getMemberList() {
		return this.memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
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
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecord() {
		return record;
	}

	public void setRecord(Integer record) {
		this.record = record;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
	
	public Map<String, Object> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
}