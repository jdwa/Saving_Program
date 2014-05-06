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
<title><s:text name="pay.add" /></title>
</head>
<body>
	<h3><s:text name="pay.add" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addPay" method="post" namespace="/">
		<s:select key="pay.roll.ro_no" list="rollList" listKey="ro_no" listValue="ro_no" /> 
		<s:select key="pay.term.term_no" list="termList" listKey="term_no" listValue="term_description" />
		<s:select key="pay.company.cmp_no" list="companyList" listKey="cmp_no" listValue="cmp_description" /> 
		<s:select key="pay.member.account" list="memberList" listKey="account" listValue="account" /> 
		<s:textfield key="pay.value" />
		<s:textfield key="pay.remark" />
		<sj:datepicker key="pay.pay_date" value="%{pay_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />		
		<sj:datepicker key="pay.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />		
		<sj:datepicker key="pay.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="true" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:submit value="%{getText('submit.add')}" align="center" />
	</s:form>
	<center><br><a href="listPay.action"><s:text name="pay.list.all" /></a></center>
</body>
</html>


