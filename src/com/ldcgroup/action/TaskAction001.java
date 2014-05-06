package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.PointBo;
import com.ldcgroup.bo.TaskBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.ItemBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Task Action 01 : 點數作業（單筆）
 * @author jdwa
 */
public class TaskAction001 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Item item;
	private Member member;
	private Task task;
	private Date settlement_date;
	private MemberBo memberBo;
	private PointBo pointBo;
	private TaskBo taskBo;
	private ItemBo itemBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Category> categoryList;
	private List<Member> memberList;
	private List<Point> pointList;
	private List<Task> taskList;
	private List<Item> itemList;

	public TaskAction001() {
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
		
		if (this.pointBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.pointBo = (PointBo) cxt.getBean("pointBo");
		}
		
		if (this.taskBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.taskBo = (TaskBo) cxt.getBean("taskBo");
		}
		
		if (this.itemBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.itemBo = (ItemBo) cxt.getBean("itemBo");
		}
		
		if (this.transactionServiceBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.transactionServiceBo = (TransactionServiceBo) cxt.getBean("transactionServiceBo");
		}
		
		if (this.memberList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN) 
					|| (member.getRole().getRole_code().equals(Definition.ROLE_TRAINER) && member.getCompany().getCmp_no().equals(Definition.HQ_NO))) {											
					this.memberList = getMemberBo().listNormal();
				} else {
					this.memberList = getMemberBo().listNormal(member.getCompany());
				}
			}			
		}
		
		if (this.pointList == null) {
			this.pointList = new ArrayList<Point>();
		}
		
		if (this.taskList == null) {
			this.taskList = new ArrayList<Task>();
		}
		
		if (this.itemList == null) {
			this.itemList = getItemBo().list();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		Date now = new Date();
		if (task.getCreation_date() == null) {
			task.setCreation_date(now);
		}
		
		if (task.getSettlement_date() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			task.setSettlement_date(calendar.getTime());
		}

		List<Point> points = new ArrayList<Point>();
		this.member = getMemberBo().findByAccount(this.member.getAccount());
		
		if ((member != null) && (member.getRole().getRole_code().equals(Definition.ROLE_NORMAL))) {
			// 點數項目
			Item item = getItemBo().findByNo(getItem().getItem_no());
			
			// Add point for member
			Point point = new Point();
			point.setCompany(member.getCompany());
			point.setMember(member);
			point.setTask(this.task);
			point.setItem(item);
			point.setValue(this.task.getValue());
			point.setSettlement_date(task.getSettlement_date());
			point.setCreation_date(task.getCreation_date());
			point.setRemark(task.getRemark() + "[" + item.getItem_description() + "]");
			point.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			points.add(point);

		} else {
			addActionError("[" + member.getAccount() + "]," + getText("member.not.qualified") + member.getRole().getRole_description());
			points.clear();
		}

		this.taskList.clear();
		this.pointList.clear();

		if (points.size() > 0) {
			// Total amount would be task.getValue()
			this.task.setValue(this.task.getValue());
			// Add task data
			task.setValid(Boolean.TRUE);
			task.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			task.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addTaskPointList(task, points);
			task = taskBo.findByNo(task.getTk_no());
			this.taskList.add(task);
			this.pointList.addAll(pointBo.list(task));
			this.session.put("S_Task", task);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.task.points.zero"));
			returnValue = INPUT;
		}
		
		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (task != null) {
			if (getTaskBo().findByNo(task.getTk_no()) != null) {				
				addActionError(this.getText("errors.duplicate") + task.getTk_no());
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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
	
	public TaskBo getTaskBo() {
		return this.taskBo;
	}

	public void setTaskBo(TaskBo taskBo) {
		this.taskBo = taskBo;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	
	public PointBo getPointBo() {
		return this.pointBo;
	}

	public void setPointBo(PointBo pointBo) {
		this.pointBo = pointBo;
	}
	
	public List<Point> getPointList() {
		return this.pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}
	
	public ItemBo getItemBo() {
		return this.itemBo;
	}

	public void setItemBo(ItemBo itemBo) {
		this.itemBo = itemBo;
	}
	
	public List<Item> getItemList() {
		return this.itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public TransactionServiceBo getTransactionServiceBo() {
		return transactionServiceBo;
	}

	public void setTransactionServiceBo(TransactionServiceBo transactionServiceBo) {
		this.transactionServiceBo = transactionServiceBo;
	}
}
