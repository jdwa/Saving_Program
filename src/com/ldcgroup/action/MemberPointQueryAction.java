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

import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.PointBo;
import com.ldcgroup.model.Bar;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Member Point Query : 查詢員工帳戶作業資料
 * @author jdwa
 */
public class MemberPointQueryAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private PointBo pointBo;
	private List<Bar> barList;
	private List<Point> pointList;
	private Member member;
	private MemberBo memberBo;
	private List<Member> memberList; 

	public MemberPointQueryAction() {
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
		
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}
		
		if (this.barList == null) {
			this.barList = new ArrayList<Bar>();
		}
		
		if (this.pointList == null) {
			this.pointList = new ArrayList<Point>();
		}
		
		if (this.memberList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)
					|| (member.getRole().getRole_code().equals(Definition.ROLE_HR) && member.getCompany().getCmp_no().equals(Definition.HQ_NO))	
					|| (member.getRole().getRole_code().equals(Definition.ROLE_TRAINER) && member.getCompany().getCmp_no().equals(Definition.HQ_NO))) {											
					this.memberList = getMemberBo().list();
				} else {
					this.memberList = getMemberBo().list(member.getCompany());
				}
			}			
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		member = getMemberBo().findByAccount(member.getAccount());
		if (member != null) {
			this.session.put("S_Member", member);
			this.pointList = this.pointBo.list(member);
			// Set graph data.
			execute_graph();
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("errors.data.not.exist") + member.getAccount());
			returnValue = SUCCESS;
		}
		
		return returnValue;
	}
	
	public String execute_graph() throws Exception {
		String returnValue = ERROR;
		if ((this.session != null) && (this.session.get("S_Member") != null)) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			SimpleDateFormat sdFormat;			
			for (int i = 0; i < 1; i++) {
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.roll(Calendar.DATE, -1);
				sdFormat = new SimpleDateFormat("yyyy.MM");
				/*--
				if (calendar.get(Calendar.MONTH) == 0) {
					sdFormat = new SimpleDateFormat("yyyy.MM");
				} else {
					sdFormat = new SimpleDateFormat("MM");
				}
				--*/
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

    // Validation
	@Override
	public void validate() {
		if (member != null) {
			if (getMemberBo().findByAccount(member.getAccount()) == null) {				
				addActionError(getText("errors.data.not.exist") + member.getAccount());
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public MemberBo getMemberBo() {
		return memberBo;
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
	
	public List<Bar> getBarList() {
		return barList;
	}

	public void setBarList(List<Bar> barList) {
		this.barList = barList;
	}
}
