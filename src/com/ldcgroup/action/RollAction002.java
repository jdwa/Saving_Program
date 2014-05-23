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
import com.ldcgroup.bo.PayBo;
import com.ldcgroup.bo.RollBo;
import com.ldcgroup.bo.TransactionServiceBo;
import com.ldcgroup.bo.TermBo;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Term;
import com.ldcgroup.util.CryptUtils;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Roll Action 02 : 薪資作業（批次）
 * @author jdwa
 */
public class RollAction002 extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Roll roll;
	private Date settlement_date;
	private Date pay_date;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private RoleBo roleBo;
	private PayBo payBo;
	private RollBo rollBo;
	private TermBo termBo;
	private TransactionServiceBo transactionServiceBo;
	private List<Member> memberList;
	private List<Pay> payList;
	private List<Roll> rollList;
	private List<Term> termList;
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;

	public RollAction002() {
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
		
		if (this.payBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.payBo = (PayBo) cxt.getBean("payBo");
		}
		
		if (this.rollBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.rollBo = (RollBo) cxt.getBean("rollBo");
		}
		
		if (this.termBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.termBo = (TermBo) cxt.getBean("termBo");
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
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
					this.memberList = getMemberBo().listNormal();
				} else {
					this.memberList = getMemberBo().listNormal(member.getCompany());
				}
			}			
		}
		
		if (this.payList == null) {
			this.payList = new ArrayList<Pay>();
		}
		
		if (this.rollList == null) {
			this.rollList = new ArrayList<Roll>();
		}
		
		if (this.termList == null) {
			this.termList = getTermBo().list();
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		
		Date now = new Date();

		if (roll.getCreation_date() == null){
			roll.setCreation_date(now);
		}
		
		if (roll.getSettlement_date() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			roll.setSettlement_date(calendar.getTime());
		}
		
		if (roll.getPay_date() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 5);
			roll.setPay_date(calendar.getTime());
		}
		
		List<Member> members = new ArrayList<Member>();
		List<Pay> pays = new ArrayList<Pay>();
		List<String> lines = FileUtils.readLines(fileUpload, "UTF-8");
		double valueSum = 0;
		// Do not parse the 1st line (for header).
		for (int idx = 1; idx < lines.size(); idx++) {
			String line = (String)lines.get(idx);
			String[] data = line.split("\t");
			
			if (data.length == 0) {
				// Blank line.
				continue;
			} else if (data.length < 16) {
				addActionError("[" + line + "]" + getText("data.format.error"));
				pays.clear();
				break;
			}
			
			/*--
			for (int i = 0; i < data.length; i++) {
				java.lang.System.out.println("Parsing data[" + i + "] ==> [" + data[i].trim() + "]");
			}
			--*/
			
			double member_value = Double.parseDouble(data[9].trim());
			String cmpStr = data[14].trim();
			Company company = getCompanyBo().findByNo(data[14].trim());
			if (company == null) {
				addActionError("[" + cmpStr + "]" + getText("company.not.exist"));
				pays.clear();
				break;
			}
			
			String termNoStr = cmpStr + "-" + data[6].trim();
			Term term = getTermBo().findByNo(termNoStr);
			if (term == null) {
				term = new Term();
				term.setTerm_no(termNoStr);
				term.setTerm_description(data[8].trim() + "-" + company.getCmp_description());
				term.setRemark(term.getTerm_description());
				term.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				term.setCreation_date(now);
				termBo.add(term);
				addActionMessage(getText("term.add.by.system") + term.getTerm_description());
			}
			
			String account = data[5].trim();
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
				addActionMessage("[" + account + "]" + getText("member.assign.to.company") + "[" + company.getCmp_description() + "]");
				member.setCompany(company);
				getMemberBo().update(member);
				/*--
				addActionError("[" + account + "]" + getText("member.not.belong.this.company") + "[" + company.getCmp_description() + "]");
				pays.clear();
				break;
				--*/
			}
			
			if ((member != null) && (member.getRole().getRole_code().equals(Definition.ROLE_NORMAL))) {
				// Add pay for member
				Pay pay = new Pay();
				pay.setCompany(member.getCompany());
				pay.setMember(member);
				pay.setRoll(this.roll);
				pay.setTerm(term);
				pay.setValue(member_value);
				pay.setSettlement_date(roll.getSettlement_date());
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					pay.setPay_date(sdf.parse(data[4].trim()));
				} catch (Exception e) {
					pay.setPay_date(roll.getPay_date());
				}
				pay.setCreation_date(roll.getCreation_date());
				pay.setRemark(roll.getRemark() + "[" + term.getTerm_description() + "]");
				pay.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
				pays.add(pay);
				valueSum += member_value;
			} else {
				addActionError("[" + member.getAccount() + "]," + getText("member.not.qualified") + member.getRole().getRole_description());
				pays.clear();
				break;
			}
		}
		
		this.rollList.clear();
		this.payList.clear();
		
		if (pays.size() > 0) {
			// Total amount would be summary of member_value
			this.roll.setValue(valueSum);
			// Add roll data
			roll.setValid(Boolean.TRUE);
			roll.setCompany(((Member)this.session.get("CurrentMember")).getCompany());
			roll.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
			getTransactionServiceBo().addRollPayList(roll, pays, members);
			roll = rollBo.findByNo(roll.getRo_no());
			this.rollList.add(roll);
			this.payList.addAll(payBo.list(roll));
			this.session.put("S_Roll", roll);
			returnValue = SUCCESS;
		} else {
			addActionMessage(getText("action.roll.pays.zero"));
			returnValue = INPUT;
		}

		return returnValue;
	}

    // Validation
	@Override
	public void validate() {
		if (roll != null) {
			if (getRollBo().findByNo(roll.getRo_no()) != null) {				
				addActionError(this.getText("errors.duplicate") + roll.getRo_no());
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

	public Roll getRoll() {
		return roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
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
	
	public Date getPay_date() {
		if (this.settlement_date == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 5);
			this.pay_date = calendar.getTime();
		}
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
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

	public PayBo getPayBo() {
		return this.payBo;
	}

	public void setPayBo(PayBo payBo) {
		this.payBo = payBo;
	}
	
	public RollBo getRollBo() {
		return this.rollBo;
	}

	public void setRollBo(RollBo rollBo) {
		this.rollBo = rollBo;
	}

	public TermBo getTermBo() {
		return this.termBo;
	}

	public void setTermBo(TermBo termBo) {
		this.termBo = termBo;
	}
	
	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	
	public List<Pay> getPayList() {
		return this.payList;
	}

	public void setPayList(List<Pay> payList) {
		this.payList = payList;
	}
	
	public List<Roll> getRollList() {
		return rollList;
	}

	public void setRollList(List<Roll> rollList) {
		this.rollList = rollList;
	}
	
	public List<Term> getTermList() {
		return this.termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
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
