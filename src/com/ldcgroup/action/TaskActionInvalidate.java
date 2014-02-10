package com.ldcgroup.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.PointBo;
import com.ldcgroup.bo.TaskBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Task Action Invalidate : µù¾P§@·~
 * @author jdwa
 */
public class TaskActionInvalidate extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private PointBo pointBo;
	private Task task;
	private TaskBo taskBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Point> pointList;
	private List<Task> taskList;

	public TaskActionInvalidate() {
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
		
		if (this.taskBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.taskBo = (TaskBo) cxt.getBean("taskBo");
		}
		
		if (this.transactionServiceBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.transactionServiceBo = (TransactionServiceBo) cxt.getBean("transactionServiceBo");
		}
		
		if (this.pointList == null) {
			this.pointList = new ArrayList<Point>();
		}
		
		if (this.taskList == null) {
			this.taskList = getTaskBo().listValidate();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		Date now = new Date();		
		List<Point> points = new ArrayList<Point>();
		task = getTaskBo().findByNo(task.getTk_no());
		
		if ((task != null) && (task.getValid().booleanValue())) {
			List<Point> taskPoints = getPointBo().list(task);
			for (int i = 0; i < taskPoints.size(); i++) {
				Point point = new Point();
				point.setCompany(taskPoints.get(i).getCompany());
				point.setMember(taskPoints.get(i).getMember());
				point.setTask(this.task);
				point.setItem(taskPoints.get(i).getItem());
				point.setValue(taskPoints.get(i).getValue() * (-1));
				point.setSettlement_date(taskPoints.get(i).getSettlement_date());
				point.setCreation_date(now);
				point.setRemark(task.getRemark() + "[" + getText("task.action.invalidate") + "]");
				point.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				points.add(point);				
			}
			
		}
		
		this.taskList.clear();
		this.pointList.clear();

		if (points.size() > 0) {
			// Total amount would be 0.0 after invalidate
			this.task.setValue(0.0);
			// Add task data
			task.setValid(Boolean.FALSE);
			//task.setCompany(((Member)this.session.get("CurrentMember")).getCompany()); Do not change the Company
			task.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addTaskPointList(task, points);
			task = taskBo.findByNo(task.getTk_no());
			this.taskList.add(task);
			this.pointList.addAll(pointBo.list(task));
			this.session.put("S_Task", task);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.task.points.zero"));
			returnValue = SUCCESS;
		}
		
		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (task != null) {
			if (getTaskBo().findByNo(task.getTk_no()) == null) {				
				addActionError(this.getText("errors.data.not.exist") + task.getTk_no());
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

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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
	
	public TransactionServiceBo getTransactionServiceBo() {
		return transactionServiceBo;
	}

	public void setTransactionServiceBo(TransactionServiceBo transactionServiceBo) {
		this.transactionServiceBo = transactionServiceBo;
	}
}
