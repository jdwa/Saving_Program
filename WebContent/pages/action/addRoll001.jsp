<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ldcgroup.model.Term"%>
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
<title><s:text name="action.roll.001" /></title>
</head>
<body>
	<h3><s:text name="action.roll.001" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addRoll001" method="post" namespace="/">
		<s:textfield key="roll.ro_no" />
		<sj:autocompleter key="member.account" list="memberList" listKey="account" listValue="account" />
		<s:select key="term.term_no" list="termList" listKey="term_no" listValue="term_description" />
		<s:textfield key="roll.value" />
		<sj:datepicker key="roll.pay_date" value="%{pay_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />						
		<sj:datepicker key="roll.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<sj:datepicker key="roll.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:textfield key="roll.remark" />	
		<s:submit name="" key="submit.add" align="center" />
	</s:form>
</body>
</html>