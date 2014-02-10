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

import com.ldcgroup.bo.CompanyBo;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.RoleBo;
import com.ldcgroup.bo.PointBo;
import com.ldcgroup.bo.TaskBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.ItemBo;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;
import com.ldcgroup.util.CryptUtils;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Task Action 02 : 點數作業（批次）
 * @author jdwa
 */
public class TaskAction002 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Task task;
	private Date settlement_date;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private RoleBo roleBo;
	private PointBo pointBo;
	private TaskBo taskBo;
	private ItemBo itemBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Member> memberList;
	private List<Point> pointList;
	private List<Task> taskList;
	private List<Item> itemList;
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;

	public TaskAction002() {
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

		if (this.roleBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.roleBo = (RoleBo) cxt.getBean("roleBo");
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

		List<Member> members = new ArrayList<Member>();
		List<Point> points = new ArrayList<Point>();
		List<String> lines = FileUtils.readLines(fileUpload);
		double itemSum = 0; // 明日之星 "001" 技藝之星 "002" 三度之星 "003" 伯樂之星 "004" 創意之星-提案 "005" 創意之星-圓夢 "006" 雲朗之星"007" 其他"008" 兌換點數 "101"
		// Do not parse the 1st line (for header).
		for (int idx = 1; idx < lines.size(); idx++) {
			String line = (String)lines.get(idx);
			String[] data = line.split("\t");
			
			if (data.length < 6) {
				addActionError("[" + line + "]" + getText("data.format.error"));
				points.clear();
				break;
			}
			
			/*
			for (int i = 0; i < data.length; i++) {
				java.lang.System.out.println("Parsing data[" + i + "] ==> [" + data[i].trim() + "]");
			}
			*/
			
			double member_value = Double.parseDouble(data[3].trim());
			String[] itemStr = data[4].trim().split("[()]");
			Item item = getItemBo().findByNo(itemStr[1].trim());
			String[] cmpStr = data[1].trim().split("[()]");
			Company company = getCompanyBo().findByNo(cmpStr[1].trim());
			if (company == null) {
				addActionError("[" + cmpStr[1].trim() + "]" + getText("company.not.exist"));
				points.clear();
				break;
			}
			
			String account = data[2].trim();
			// Check database
			Member member = getMemberBo().findByAccount(account);
			
			// Check current members list
			if (member == null) {
				for (int mdx = 0 ; mdx < members.size(); mdx++) {
					Member mbr = members.get(mdx);
					if (mbr.getAccount().equals(account)) {
						member = mbr;
					}
				}
			}

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
				addActionError("[" + data[2].trim() + "]" + getText("member.not.belong.this.company") + "[" + company.getCmp_description() + "]");
				points.clear();
				break;
			}
			
			if ((member != null) && (member.getRole().getRole_code().equals(Definition.ROLE_NORMAL))) {
				// Add point for member
				Point point = new Point();
				point.setCompany(member.getCompany());
				point.setMember(member);
				point.setTask(this.task);
				point.setItem(item);
				point.setValue(member_value);
				point.setSettlement_date(task.getSettlement_date());
				point.setCreation_date(task.getCreation_date());
				point.setRemark(task.getRemark() + "[" + item.getItem_description() + "]");
				point.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				points.add(point);
				itemSum += member_value;
			} else {
				addActionError("[" + member.getAccount() + "]," + getText("member.not.qualified") + member.getRole().getRole_description());
				points.clear();
				break;
			}
		}
		
		this.taskList.clear();
		this.pointList.clear();
		
		if (points.size() > 0) {
			// Total amount would be summary of member_value
			this.task.setValue(itemSum);
			// Add task data
			task.setValid(Boolean.TRUE);
			task.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			task.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addTaskPointList(task, points, members);
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
	
	public Map<String, Object> getSession() {
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
