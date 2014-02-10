package com.ldcgroup.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldcgroup.bo.CategoryBo;
import com.ldcgroup.bo.CompanyBo;
import com.ldcgroup.bo.MemberBo;
import com.ldcgroup.bo.RoleBo;
import com.ldcgroup.bo.TypeBo;
import com.ldcgroup.model.Category;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Role;
import com.ldcgroup.model.Type;
import com.ldcgroup.util.CryptUtils;
import com.ldcgroup.util.Definition;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class DataInitializeAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private CategoryBo categoryBo;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private RoleBo roleBo;
	private TypeBo typeBo;
	private String account;
	private String password;
	
	public DataInitializeAction() {
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
		
		if (this.typeBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.typeBo = (TypeBo) cxt.getBean("typeBo");
		}
	}
	
	@Override
	public String execute() throws Exception {
		String returnValue = ERROR;
		Date now = new Date();
		
		if ("it.admin".equals(getAccount()) && "it.admin".equals(getPassword())) {
			
			// 公司資料初始化
			if (companyBo.findByNo(Definition.HQ_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.HQ_NO);
				cmp.setCmp_description(getText("company.hq"));
				cmp.setRemark(getText("company.hq"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.ZH_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.ZH_NO);
				cmp.setCmp_description(getText("company.zh"));
				cmp.setRemark(getText("company.zh"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.SML_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.SML_NO);
				cmp.setCmp_description(getText("company.sml"));
				cmp.setRemark(getText("company.sml"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.HL_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.HL_NO);
				cmp.setCmp_description(getText("company.hl"));
				cmp.setRemark(getText("company.hl"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.KH_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.KH_NO);
				cmp.setCmp_description(getText("company.kh"));
				cmp.setRemark(getText("company.kh"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.CL_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.CL_NO);
				cmp.setCmp_description(getText("company.cl"));
				cmp.setRemark(getText("company.cl"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.CY_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.CY_NO);
				cmp.setCmp_description(getText("company.cy"));
				cmp.setRemark(getText("company.cy"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.TY_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.TY_NO);
				cmp.setCmp_description(getText("company.ty"));
				cmp.setRemark(getText("company.ty"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.SJ_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.SJ_NO);
				cmp.setCmp_description(getText("company.sj"));
				cmp.setRemark(getText("company.sj"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.PDC_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.PDC_NO);
				cmp.setCmp_description(getText("company.pdc"));
				cmp.setRemark(getText("company.pdc"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			// 職務資料初始化
			if (roleBo.findByCode(Definition.ROLE_ADMIN) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_ADMIN);
				role.setRole_description(getText("role.admin"));
				role.setRemark(getText("role.admin"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
	
			if (roleBo.findByCode(Definition.ROLE_HR) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_HR);
				role.setRole_description(getText("role.hr"));
				role.setRemark(getText("role.hr"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
			
			if (roleBo.findByCode(Definition.ROLE_FIN) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_FIN);
				role.setRole_description(getText("role.fin"));
				role.setRemark(getText("role.fin"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}

			if (roleBo.findByCode(Definition.ROLE_TRAINER) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_TRAINER);
				role.setRole_description(getText("role.trainer"));
				role.setRemark(getText("role.trainer"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
			
			if (roleBo.findByCode(Definition.ROLE_NORMAL) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_NORMAL);
				role.setRole_description(getText("role.normal"));
				role.setRemark(getText("role.normal"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
			
			if (roleBo.findByCode(Definition.ROLE_RESIGN) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_RESIGN);
				role.setRole_description(getText("role.resign"));
				role.setRemark(getText("role.resign"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
	
			if (roleBo.findByCode(Definition.ROLE_RETIRE) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_RETIRE);
				role.setRole_description(getText("role.retire"));
				role.setRemark(getText("role.retire"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
			
			if (roleBo.findByCode(Definition.ROLE_REMAIN) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_REMAIN);
				role.setRole_description(getText("role.remain"));
				role.setRemark(getText("role.remain"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
	
			if (roleBo.findByCode(Definition.ROLE_RETURN) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_RETURN);
				role.setRole_description(getText("role.return"));
				role.setRemark(getText("role.return"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
			
			if (roleBo.findByCode(Definition.ROLE_PROMOTE) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_PROMOTE);
				role.setRole_description(getText("role.promote"));
				role.setRemark(getText("role.promote"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
			
			if (roleBo.findByCode(Definition.ROLE_ABANDON) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_ABANDON);
				role.setRole_description(getText("role.abandon"));
				role.setRemark(getText("role.abandon"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
			
			if (roleBo.findByCode(Definition.ROLE_ACCIDENT) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_ACCIDENT);
				role.setRole_description(getText("role.accident"));
				role.setRemark(getText("role.accident"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}

			// 類別資料初始化
			if (categoryBo.findByNo("001") == null) {
				Category category = new Category();
				category.setCategory_no("001");
				category.setCategory_description(getText("category.001"));
				category.setRemark(getText("category.001"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}
			
			/*--
			if (categoryBo.findByNo("002") == null) {
				Category category = new Category();
				category.setCategory_no("002");
				category.setCategory_description(getText("category.002"));
				category.setRemark(getText("category.002"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}
			--*/

			if (categoryBo.findByNo("003") == null) {
				Category category = new Category();
				category.setCategory_no("003");
				category.setCategory_description(getText("category.003"));
				category.setRemark(getText("category.003"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}

			if (categoryBo.findByNo("004") == null) {
				Category category = new Category();
				category.setCategory_no("004");
				category.setCategory_description(getText("category.004"));
				category.setRemark(getText("category.004"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}

			if (categoryBo.findByNo("101") == null) {
				Category category = new Category();
				category.setCategory_no("101");
				category.setCategory_description(getText("category.101"));
				category.setRemark(getText("category.101"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}
			
			/*--
			if (categoryBo.findByNo("102") == null) {
				Category category = new Category();
				category.setCategory_no("102");
				category.setCategory_description(getText("category.102"));
				category.setRemark(getText("category.102"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}
			--*/

			if (categoryBo.findByNo("103") == null) {
				Category category = new Category();
				category.setCategory_no("103");
				category.setCategory_description(getText("category.103"));
				category.setRemark(getText("category.103"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}

			if (categoryBo.findByNo("104") == null) {
				Category category = new Category();
				category.setCategory_no("104");
				category.setCategory_description(getText("category.104"));
				category.setRemark(getText("category.104"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}
			
			if (categoryBo.findByNo("105") == null) {
				Category category = new Category();
				category.setCategory_no("105");
				category.setCategory_description(getText("category.105"));
				category.setRemark(getText("category.105"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}
			
			if (categoryBo.findByNo("106") == null) {
				Category category = new Category();
				category.setCategory_no("106");
				category.setCategory_description(getText("category.106"));
				category.setRemark(getText("category.106"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}

			if (categoryBo.findByNo("107") == null) {
				Category category = new Category();
				category.setCategory_no("107");
				category.setCategory_description(getText("category.107"));
				category.setRemark(getText("category.107"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}

			if (categoryBo.findByNo("201") == null) {
				Category category = new Category();
				category.setCategory_no("201");
				category.setCategory_description(getText("category.201"));
				category.setRemark(getText("category.201"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}

			if (categoryBo.findByNo("202") == null) {
				Category category = new Category();
				category.setCategory_no("202");
				category.setCategory_description(getText("category.202"));
				category.setRemark(getText("category.202"));
				category.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				category.setCreation_date(now);
				categoryBo.add(category);			
			}

			// 類型資料初始化
			if (typeBo.findByNo("001") == null) {
				Type type = new Type();
				type.setType_no("001");
				type.setType_description(getText("type.001"));
				type.setRemark(getText("type.001"));
				type.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				type.setCreation_date(now);
				typeBo.add(type);			
			}
	
			if (typeBo.findByNo("002") == null) {
				Type type = new Type();
				type.setType_no("002");
				type.setType_description(getText("type.002"));
				type.setRemark(getText("type.002"));
				type.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				type.setCreation_date(now);
				typeBo.add(type);			
			}
			
			if (typeBo.findByNo("003") == null) {
				Type type = new Type();
				type.setType_no("003");
				type.setType_description(getText("type.003"));
				type.setRemark(getText("type.003"));
				type.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				type.setCreation_date(now);
				typeBo.add(type);			
			}
			
			if (typeBo.findByNo("004") == null) {
				Type type = new Type();
				type.setType_no("004");
				type.setType_description(getText("type.004"));
				type.setRemark(getText("type.004"));
				type.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				type.setCreation_date(now);
				typeBo.add(type);			
			}
	
			if (typeBo.findByNo("005") == null) {
				Type type = new Type();
				type.setType_no("005");
				type.setType_description(getText("type.005"));
				type.setRemark(getText("type.005"));
				type.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				type.setCreation_date(now);
				typeBo.add(type);			
			}
			
			// 帳號資料初始化
			if (memberBo.findByAccount("it.admin") == null) {
				Member member = new Member();
				// Set password, default password : "it.admin"
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("it.admin", sdf.format(now));
				member.setAccount("it.admin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.HQ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_ADMIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_ADMIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("hq.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("hq.hr", sdf.format(now));
				member.setAccount("hq.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.HQ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("hq.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("hq.fin", sdf.format(now));
				member.setAccount("hq.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.HQ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("zh.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("zh.hr", sdf.format(now));
				member.setAccount("zh.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.ZH_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("zh.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("zh.fin", sdf.format(now));
				member.setAccount("zh.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.ZH_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("sml.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("sml.hr", sdf.format(now));
				member.setAccount("sml.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.SML_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("sml.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("sml.fin", sdf.format(now));
				member.setAccount("sml.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.SML_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("hl.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("hl.hr", sdf.format(now));
				member.setAccount("hl.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.HL_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("hl.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("hl.fin", sdf.format(now));
				member.setAccount("hl.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.HL_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("kh.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("kh.hr", sdf.format(now));
				member.setAccount("kh.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.KH_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("kh.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("kh.fin", sdf.format(now));
				member.setAccount("kh.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.KH_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("cl.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("cl.hr", sdf.format(now));
				member.setAccount("cl.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.CL_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("cl.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("cl.fin", sdf.format(now));
				member.setAccount("cl.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.CL_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("cy.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("cy.hr", sdf.format(now));
				member.setAccount("cy.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.CY_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("cy.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("cy.fin", sdf.format(now));
				member.setAccount("cy.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.CY_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("ty.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("ty.hr", sdf.format(now));
				member.setAccount("ty.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.TY_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("ty.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("ty.fin", sdf.format(now));
				member.setAccount("ty.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.TY_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("sj.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("sj.hr", sdf.format(now));
				member.setAccount("sj.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.SJ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("sj.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("sj.fin", sdf.format(now));
				member.setAccount("sj.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.SJ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			if (memberBo.findByAccount("pdc.hr") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("pdc.hr", sdf.format(now));
				member.setAccount("pdc.hr");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.PDC_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_HR));
				member.setRemark(roleBo.findByCode(Definition.ROLE_HR).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			if (memberBo.findByAccount("pdc.fin") == null) {
				Member member = new Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("pdc.fin", sdf.format(now));
				member.setAccount("pdc.fin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.PDC_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_FIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_FIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 使用者資料初始化
			// 建立系統預設之公司代表帳號：Definition.HQ_REMAIN。畸零或回沖之金額將列入此帳戶。可再分配。
			if(memberBo.findByAccount(Definition.HQ_REMAIN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.HQ_REMAIN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.HQ_REMAIN, sdf.format(now));
				member.setAccount(Definition.HQ_REMAIN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.HQ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_REMAIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_REMAIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號：Definition.HQ_RETURN。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.HQ_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.HQ_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.HQ_RETURN, sdf.format(now));
				member.setAccount(Definition.HQ_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.HQ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			// 建立系統預設之公司代表帳號：Definition.ZH_RETURN。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.ZH_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.ZH_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.ZH_RETURN, sdf.format(now));
				member.setAccount(Definition.ZH_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.ZH_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
			
			// 建立系統預設之公司代表帳號："Definition.SML_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.SML_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.SML_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.SML_RETURN, sdf.format(now));
				member.setAccount(Definition.SML_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.SML_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號："Definition.HL_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.HL_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.HL_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.HL_RETURN, sdf.format(now));
				member.setAccount(Definition.HL_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.HL_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號："Definition.KH_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount("Definition.KH_RETURN") == null) {
				Member member = new Member();	
				// Set password, default password : Definition.KH_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.KH_RETURN, sdf.format(now));
				member.setAccount(Definition.KH_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.KH_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號："Definition.CL_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.CL_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.CL_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.CL_RETURN, sdf.format(now));
				member.setAccount(Definition.CL_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.CL_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號："Definition.CY_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.CY_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.CY_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.CY_RETURN, sdf.format(now));
				member.setAccount(Definition.CY_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.CY_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號："Definition.TY_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.TY_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.TY_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.TY_RETURN, sdf.format(now));
				member.setAccount(Definition.TY_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.TY_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號："Definition.SJ_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount("Definition.SJ_RETURN") == null) {
				Member member = new Member();	
				// Set password, default password : Definition.SJ_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.SJ_RETURN, sdf.format(now));
				member.setAccount(Definition.SJ_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.SJ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
	
			// 建立系統預設之公司代表帳號："Definition.PDC_RETURN"。公司收回之金額將列入此帳戶。不可再分配。
			if(memberBo.findByAccount(Definition.PDC_RETURN) == null) {
				Member member = new Member();	
				// Set password, default password : Definition.PDC_RETURN
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString(Definition.PDC_RETURN, sdf.format(now));
				member.setAccount(Definition.PDC_RETURN);
				member.setPassword(en);
				member.setActive(Boolean.FALSE);
				member.setCompany(companyBo.findByNo(Definition.PDC_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_RETURN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_RETURN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}
		
		}
		return returnValue;
	}

	@Override
	public void validate(){
		// Check initial member account 
		if(memberBo.findByAccount("it.admin") != null){
			addActionError(getText("action.system.admin.already.exist"));
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
	
	public RoleBo getRoleBo() {
		return roleBo;
	}

	public void setRoleBo(RoleBo roleBo) {
		this.roleBo = roleBo;
	}

	public TypeBo getTypeBo() {
		return typeBo;
	}

	public void setTypeBo(TypeBo typeBo) {
		this.typeBo = typeBo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
