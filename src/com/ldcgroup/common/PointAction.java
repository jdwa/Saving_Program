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
import com.ldcgroup.bo.PointBo;
import com.ldcgroup.bo.ItemBo;
import com.ldcgroup.bo.TaskBo;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;
import com.ldcgroup.util.Definition;

public class PointAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
	private Long id; // Point ID, for Detail, Delete action
	private Point point; // For Add, Update action
	private Date settlement_date;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private PointBo pointBo;
	private ItemBo itemBo;
	private TaskBo taskBo;
	private List<Company> companyList; // For List action
	private List<Member> memberList; 
	private List<Point> pointList; 
	private List<Item> itemList;
	private List<Task> taskList;
	
	public PointAction() {
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

		if (this.pointBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.pointBo = (PointBo) cxt.getBean("pointBo");
		}

		if (this.pointList == null) {
			this.pointList = getPointBo().list();
		}
		
		if (this.itemBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.itemBo = (ItemBo) cxt.getBean("itemBo");
		}
		
		if (this.itemList == null) {
			this.itemList = getItemBo().list();
		}

		if (this.taskBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.taskBo = (TaskBo) cxt.getBean("taskBo");
		}
		
		if (this.taskList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
					this.taskList = getTaskBo().list();
				} else {
					this.taskList = getTaskBo().list(member.getCompany());
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
		Task task = getTaskBo().findByNo(this.point.getTask().getTk_no());
		Date now = new Date();
		if (point.getCreation_date() == null){
			point.setCreation_date(now);
		}
		
		if (point.getSettlement_date() == null) {
			if (task.getSettlement_date() != null) {
				point.setSettlement_date(task.getSettlement_date());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.roll(Calendar.DATE, -1);
				point.setSettlement_date(calendar.getTime());
			}
		}
		
		point.setMember(getMemberBo().findByAccount(this.point.getMember().getAccount()));
		point.setCompany(getCompanyBo().findByNo(this.point.getCompany().getCmp_no()));
		point.setTask(task);
		point.setItem(getItemBo().findByNo(this.point.getItem().getItem_no()));
		point.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		task.setValue(task.getValue() + point.getValue());
		getTaskBo().update(task);
		getPointBo().add(point);
		list();
		
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.pointList = getPointBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.point = getPointBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Point orgPoint = getPointBo().findById(point.getId());
		
		Date now = new Date(); // Update time
		orgPoint.setRemark(point.getRemark());
		orgPoint.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgPoint.setValue(point.getValue());
		
		getPointBo().update(orgPoint);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getPointBo().findById(getId()) != null) {
			getPointBo().delete(getId());
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

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
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

	public PointBo getPointBo() {
		return this.pointBo;
	}

	public void setPointBo(PointBo pointBo) {
		this.pointBo = pointBo;
	}
	
	public ItemBo getItemBo() {
		return this.itemBo;
	}

	public void setItemBo(ItemBo itemBo) {
		this.itemBo = itemBo;
	}

	public TaskBo getTaskBo() {
		return taskBo;
	}

	public void setTaskBo(TaskBo taskBo) {
		this.taskBo = taskBo;
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

	public List<Point> getPointList() {
		return this.pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}
	
	public List<Item> getItemList() {
		return this.itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
}
