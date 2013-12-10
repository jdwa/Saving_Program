package com.ldcgroup.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import com.ldcgroup.model.Member;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ActionInterceptor extends AbstractInterceptor implements StrutsStatics {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ActionInterceptor.class.getName());

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
		HttpSession session = request.getSession(false);
		String actionName = invocation.getAction().getClass().getSimpleName();
		ActionSupport action = (ActionSupport) invocation.getAction();
		
		String returnValue = Action.ERROR;
		if (session != null) {
			try {
				if (actionName.contains("DataInitialize")) {
					returnValue = invocation.invoke();
					logger.info(action.getText("action.system.initialize")  + ", [" + returnValue + "], " + action.getText("action.invoke") + " : [" + invocation.getAction().getClass().getSimpleName() + "][" + invocation.getProxy().getMethod() + "]");
				} else if (actionName.contains("MemberLogin")) {
					returnValue = invocation.invoke();
					logger.info(action.getText("action.login.member") + request.getAttribute("account") + ", [" + returnValue + "], " + action.getText("action.invoke") + " : [" + invocation.getAction().getClass().getSimpleName() + "][" + invocation.getProxy().getMethod() + "]");
				} else if (session.getAttribute("CurrentMember") != null) {
					Member member = (Member) session.getAttribute("CurrentMember");
					if (actionName.contains("MemberLogout")) {
						returnValue = invocation.invoke();
						logger.info(action.getText("action.logout.member") + member.getAccount() + ", [" + returnValue + "], " + action.getText("action.invoke") + " : [" + invocation.getAction().getClass().getSimpleName() + "][" + invocation.getProxy().getMethod() + "]");						
					}else {
						returnValue = invocation.invoke();
						logger.info(action.getText("action.system.member") + member.getAccount() + ", [" + returnValue + "], " + action.getText("action.invoke") + " : [" + invocation.getAction().getClass().getSimpleName() + "][" + invocation.getProxy().getMethod() + "]");
					}
				} else {
					returnValue = Action.LOGIN;
				}
			} catch (Exception e) {
				logger.fatal(action.getText("action.invoke.error") + " [" + invocation.getAction().getClass().getSimpleName() + "]", e);
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				action.addActionError(stacktrace);
				/*--
				Map<String, Object> map= new HashMap<String, Object>();
				map.put("exceptionClassName", actionName);
				map.put("message", action.getText("action.system.error.contact.information"));
				map.put("exceptionStackTrace", stacktrace);
				invocation.getStack().push(map);
				--*/
				returnValue = Action.ERROR;
			}
		} else {
			returnValue = Action.LOGIN;
		}
		return returnValue;
	}

}
