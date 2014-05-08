<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="com.ldcgroup.model.Member"%>
<%@ page import="com.ldcgroup.model.Role"%>
<%@ page import="com.ldcgroup.util.Definition"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<s:head />
<sj:head jqueryui="true" jquerytheme="cupertino"/>
<title><s:text name="memu.system.function.panel" /></title>
</head>
<body>
	<!-- 
	<s:actionerror />
	<s:actionmessage />
	-->
	<sj:accordion id="accordion" heightStyle="content" animate="true" collapsible="true">
		<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">
			<s:set name="menu.system.operation.salary.program"><s:text name="menu.system.operation.salary.program" /></s:set>
			<sj:accordionItem title="%{menu.system.operation.salary.program}">
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">	
					<sj:div><a href="initializeRoll001.action"><s:text name="action.roll.001" /></a></sj:div>		
					<sj:div><a href="initializeRoll002.action"><s:text name="action.roll.002" /></a></sj:div>					
					<sj:div><a href="initializeInvalidateRoll.action"><s:text name="roll.action.invalidate" /></a></sj:div>
					<sj:div><a href="initializeQueryRoll.action"><s:text name="roll.action.query" /></a></sj:div>
					<sj:div><a href="initializeQueryMemberPayRoll.action"><s:text name="member.action.query.pay.roll" /></a></sj:div>
				</security:authorize>
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">
					<sj:div><a href="listMemberPayRollGrid.action"><s:text name="pay.roll.grid.member" /></a></sj:div>	
				</security:authorize>
			</sj:accordionItem>
		</security:authorize>

		<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">
			<s:set name="menu.system.operation.saving.program"><s:text name="menu.system.operation.saving.program" /></s:set>
			<sj:accordionItem title="%{menu.system.operation.saving.program}">
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">
					<sj:div><a href="initializeTrade001.action"><s:text name="action.trade.001" /></a></sj:div>		
					<sj:div><a href="initializeTrade002.action"><s:text name="action.trade.002" /></a></sj:div>
					<sj:div><a href="initializeTrade003.action"><s:text name="action.trade.003" /></a></sj:div>	
					<sj:div><a href="initializeTrade004.action"><s:text name="action.trade.004" /></a></sj:div>
					<sj:div><a href="initializeTrade101.action"><s:text name="action.trade.101" /></a></sj:div>
				</security:authorize>
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">			
					<sj:div><a href="initializeTrade102.action"><s:text name="action.trade.102" /></a></sj:div>
				</security:authorize>	
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">	
					<sj:div><a href="initializeTrade103.action"><s:text name="action.trade.103" /></a></sj:div>		
					<sj:div><a href="initializeTrade104.action"><s:text name="action.trade.104" /></a></sj:div>
					<sj:div><a href="initializeTrade105.action"><s:text name="action.trade.105" /></a></sj:div>
					<sj:div><a href="initializeTrade106.action"><s:text name="action.trade.106" /></a></sj:div>
					<sj:div><a href="initializeTrade107.action"><s:text name="action.trade.107" /></a></sj:div>
					<sj:div><a href="initializeTrade201.action"><s:text name="action.trade.201" /></a></sj:div>	
					<sj:div><a href="initializeTrade202.action"><s:text name="action.trade.202" /></a></sj:div>
					<sj:div><a href="initializeInvalidateTrade.action"><s:text name="trade.action.invalidate" /></a></sj:div>
					<sj:div><a href="initializeQueryTrade.action"><s:text name="trade.action.query" /></a></sj:div>
					<sj:div><a href="initializeQueryMemberStatement.action"><s:text name="member.action.query.statement" /></a></sj:div>
				</security:authorize>
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">	
					<sj:div><a href="listMemberStatementGrid.action"><s:text name="statement.grid.member" /></a></sj:div>					
					<sj:div><a href="listMemberStatement.action"><s:text name="statement.list.member" /></a></sj:div>
				</security:authorize>
			</sj:accordionItem>
		</security:authorize>

		<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_TRAINER', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">
			<s:set name="menu.system.operation.star.program"><s:text name="menu.system.operation.star.program" /></s:set>
			<sj:accordionItem title="%{menu.system.operation.star.program}">
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_TRAINER')">	
					<sj:div><a href="initializeTask001.action"><s:text name="action.task.001" /></a></sj:div>		
					<sj:div><a href="initializeTask002.action"><s:text name="action.task.002" /></a></sj:div>					
					<sj:div><a href="initializeInvalidateTask.action"><s:text name="task.action.invalidate" /></a></sj:div>
					<sj:div><a href="initializeQueryTask.action"><s:text name="task.action.query" /></a></sj:div>
					<sj:div><a href="initializeQueryMemberPoint.action"><s:text name="member.action.query.point" /></a></sj:div>
				</security:authorize>
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">	
					<sj:div><a href="listMemberPointGrid.action"><s:text name="point.grid.member" /></a></sj:div>					
					<sj:div><a href="listMemberPoint.action"><s:text name="point.list.member" /></a></sj:div>
				</security:authorize>
			</sj:accordionItem>
		</security:authorize>
				
		<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_TRAINER', 'ROLE_FIN')">
			<s:set name="menu.system.config"><s:text name="menu.system.config" /></s:set>
			<sj:accordionItem title="%{menu.system.config}">
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">	
					<sj:div><a href="initializeMember.action"><s:text name="member.add" /></a></sj:div>
					<sj:div><a href="listMember.action"><s:text name="member.list.all" /></a></sj:div>
				</security:authorize>	
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_FIN')">						
					<sj:div><a href="initializeRate.action"><s:text name="rate.add" /></a></sj:div>
					<sj:div><a href="listRate.action"><s:text name="rate.list.all" /></a></sj:div>
					<br>
				</security:authorize>		
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<sj:div><a href="initializeCompany.action"><s:text name="company.add" /></a></sj:div>
					<sj:div><a href="listCompany.action"><s:text name="company.list.all" /></a></sj:div>
					<sj:div><a href="initializeRole.action"><s:text name="role.add" /></a></sj:div>
					<sj:div><a href="listRole.action"><s:text name="role.list.all" /></a></sj:div>
					<br>
					<sj:div><a href="initializeCategory.action"><s:text name="category.add" /></a></sj:div>
					<sj:div><a href="listCategory.action"><s:text name="category.list.all" /></a></sj:div>				
					<sj:div><a href="initializeType.action"><s:text name="type.add" /></a></sj:div>
					<sj:div><a href="listType.action"><s:text name="type.list.all" /></a></sj:div>
					<sj:div><a href="initializeStatement.action"><s:text name="statement.add" /></a></sj:div>
					<sj:div><a href="listStatement.action"><s:text name="statement.list.all" /></a></sj:div>
					<sj:div><a href="initializeTrade.action"><s:text name="trade.add" /></a></sj:div>
					<sj:div><a href="listTrade.action"><s:text name="trade.list.all" /></a></sj:div>
					<br>
					<sj:div><a href="initializeItem.action"><s:text name="item.add" /></a></sj:div>
					<sj:div><a href="listItem.action"><s:text name="item.list.all" /></a></sj:div>						
					<sj:div><a href="initializePoint.action"><s:text name="point.add" /></a></sj:div>
					<sj:div><a href="listPoint.action"><s:text name="point.list.all" /></a></sj:div>
					<sj:div><a href="initializeTask.action"><s:text name="task.add" /></a></sj:div>
				</security:authorize>	
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_TRAINER')">	
					<sj:div><a href="listTask.action"><s:text name="task.list.all" /></a></sj:div>
				</security:authorize>	
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">	
					<br>
					<sj:div><a href="initializeTerm.action"><s:text name="term.add" /></a></sj:div>
					<sj:div><a href="listTerm.action"><s:text name="term.list.all" /></a></sj:div>						
					<sj:div><a href="initializePay.action"><s:text name="pay.add" /></a></sj:div>
					<sj:div><a href="listPay.action"><s:text name="pay.list.all" /></a></sj:div>
					<sj:div><a href="initializeRoll.action"><s:text name="roll.add" /></a></sj:div>
				</security:authorize>	
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">	
					<sj:div><a href="listRoll.action"><s:text name="roll.list.all" /></a></sj:div>					
				</security:authorize>
			</sj:accordionItem>
		</security:authorize>
		
		<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_TRAINER', 'ROLE_FIN', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">
			<s:set name="menu.system.management"><s:text name="menu.system.management" /></s:set>		
			<sj:accordionItem title="%{menu.system.management}">
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_TRAINER', 'ROLE_FIN', 'ROLE_NORMAL', 'ROLE_RESIGN', 'ROLE_RETIRE' , 'ROLE_REMAIN', 'ROLE_RETURN')">
					<sj:div><a href="initializeChangeMemberPassword.action"><s:text name="submit.changePassword" /></a></sj:div>
					<sj:div><a href="logoutMember.action"><s:text name="submit.logout" /></a></sj:div>
				</security:authorize>
			</sj:accordionItem>
		</security:authorize>
	</sj:accordion>
</body>
</html>


