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
import com.ldcgroup.bo.PointBo;
import com.ldcgroup.bo.TaskBo;
import com.ldcgroup.bo.ItemBo;
import com.ldcgroup.model.Bar;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberPointListAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private PointBo pointBo;
	private ItemBo itemBo;
	private TaskBo taskBo;
	private List<Bar> barList; 
	private List<Company> companyList; // For List action
	private List<Member> memberList; 
	private List<Point> pointList; 
	private List<Item> itemList;
	private List<Task> taskList;
		
	public MemberPointListAction() {
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
		
		if (this.pointBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.pointBo = (PointBo) cxt.getBean("pointBo");
		}

		if (this.itemBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.itemBo = (ItemBo) cxt.getBean("itemBo");
		}
		
		if (this.taskBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.taskBo = (TaskBo) cxt.getBean("taskBo");
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
		if ((this.session != null) && (this.session.get("S_Member") != null)) {
			Member member = (Member) this.session.get("S_Member");
			this.pointList = this.pointBo.list(member);

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

			for (int i = 0; i < pointList.size(); i++) {
				Point point = (Point) pointList.get(i);
				for (int j = 0; j < barList.size(); j++) {
					Bar bar = (Bar) barList.get(j);
					if (point.getSettlement_date().getTime() <= bar.getTime()) {
						if ("001".equals(point.getItem().getItem_no())) {
							bar.setValue_item_001(bar.getValue_item_001() + point.getValue());
						} else if ("002".equals(point.getItem().getItem_no())) {
							bar.setValue_item_002(bar.getValue_item_002() + point.getValue());	
						} else if ("003".equals(point.getItem().getItem_no())) {
							bar.setValue_item_003(bar.getValue_item_003() + point.getValue());	
						} else if ("004".equals(point.getItem().getItem_no())) {
							bar.setValue_item_004(bar.getValue_item_004() + point.getValue());	
						} else if ("005".equals(point.getItem().getItem_no())) {
							bar.setValue_item_005(bar.getValue_item_005() + point.getValue());	
						} else if ("006".equals(point.getItem().getItem_no())) {
							bar.setValue_item_006(bar.getValue_item_006() + point.getValue());	
						} else if ("007".equals(point.getItem().getItem_no())) {
							bar.setValue_item_007(bar.getValue_item_007() + point.getValue());	
						} else if ("008".equals(point.getItem().getItem_no())) {
							bar.setValue_item_008(bar.getValue_item_008() + point.getValue());	
						} else if ("101".equals(point.getItem().getItem_no())) {
							bar.setValue_item_101(bar.getValue_item_101() + point.getValue());	
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
