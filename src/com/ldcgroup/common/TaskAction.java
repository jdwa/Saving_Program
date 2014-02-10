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
import com.ldcgroup.bo.PointBo;
import com.ldcgroup.bo.TaskBo;
import com.ldcgroup.bo.ItemBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;
import com.ldcgroup.util.Definition;

public class TaskAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Task ID, for Detail, Delete action
	private Task task; // For Add, Update action
	private Date settlement_date;
	private PointBo pointBo;
	private TaskBo taskBo;
	private ItemBo itemBo;
	private List<Category> categoryList;
	private List<Point> pointList; 
	private List<Task> taskList; // For List action
	private List<Item> itemList;
	
	public TaskAction() {
		super();
	}

	@Override
	public void prepare() throws Exception {
		if (this.pointBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.pointBo = (PointBo) cxt.getBean("pointBo");
		}

		if (this.pointList == null) {
			this.pointList = getPointBo().list();
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
		
		if (this.itemBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.itemBo = (ItemBo) cxt.getBean("itemBo");
		}
		
		if (this.itemList == null) {
			this.itemList = getItemBo().list();
		}	
	}	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

    // Validation
	@Override
	public void validate(){
		if (task != null) {
			if (getTaskBo().findByNo(task.getTk_no()) != null){				
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
	
	public String add() {
		Date now = new Date();
		if (task.getCreation_date() == null){
			task.setCreation_date(now);
		}
		
		if (task.getSettlement_date() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			task.setSettlement_date(calendar.getTime());
		}
		
		task.setValid(Boolean.TRUE);
		task.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
		task.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getTaskBo().add(task);
		task = taskBo.findByNo(task.getTk_no());
		this.taskList.clear();
		this.taskList.add(task);
		this.pointList.clear();
		this.pointList.addAll(pointBo.list(task));
		
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
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
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.task = getTaskBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Task orgTask = getTaskBo().findById(task.getId());
		
		Date now = new Date(); // Update time
		orgTask.setValid(task.getValid());
		orgTask.setRemark(task.getRemark());
		orgTask.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
		orgTask.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgTask.setValue(task.getValue());
		
		getTaskBo().update(orgTask);
		task = getTaskBo().findById(task.getId());
		this.taskList.clear();
		this.taskList.add(task);
		this.pointList.clear();
		this.pointList.addAll(pointBo.list(task));
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		task = taskBo.findById(getId());
		if (task != null) {
			this.taskList.clear();
			this.taskList.add(task);
			this.pointList.clear();
			this.pointList.addAll(pointBo.list(task));
			for (int i = 0; i < this.pointList.size(); i++) {
				Point point = this.pointList.get(i);
				getPointBo().delete(point.getId());
			}
			getTaskBo().delete(getId());
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

	public PointBo getPointBo() {
		return this.pointBo;
	}

	public void setPointBo(PointBo pointBo) {
		this.pointBo = pointBo;
	}

	public TaskBo getTaskBo() {
		return this.taskBo;
	}

	public void setTaskBo(TaskBo taskBo) {
		this.taskBo = taskBo;
	}
	
	public ItemBo getItemBo() {
		return this.itemBo;
	}

	public void setItemBo(ItemBo itemBo) {
		this.itemBo = itemBo;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Point> getPointList() {
		return this.pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	
	public List<Item> getItemList() {
		return this.itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
