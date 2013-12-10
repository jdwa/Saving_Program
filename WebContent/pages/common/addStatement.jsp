<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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
<title><s:text name="statement.add" /></title>
</head>
<body>
	<h3><s:text name="statement.add" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addStatement" method="post" namespace="/">
		<s:select key="statement.trade.tx_no" list="tradeList" listKey="tx_no" listValue="tx_no" /> 
		<s:select key="statement.type.type_no" list="typeList" listKey="type_no" listValue="type_description" />
		<s:select key="statement.company.cmp_no" list="companyList" listKey="cmp_no" listValue="cmp_description" /> 
		<s:select key="statement.member.account" list="memberList" listKey="account" listValue="account" /> 
		<s:textfield key="statement.fund" />
		<s:textfield key="statement.remark" />
		<sj:datepicker key="statement.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />		
		<sj:datepicker key="statement.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="true" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:submit value="%{getText('submit.add')}" align="center" />
	</s:form>
	<a href="listStatement.action"><s:text name="statement.list.all" /></a>
</body>
</html>


