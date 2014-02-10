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
<title><s:text name="point.add" /></title>
</head>
<body>
	<h3><s:text name="point.add" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addPoint" method="post" namespace="/">
		<s:select key="point.task.tk_no" list="taskList" listKey="tk_no" listValue="tk_no" /> 
		<s:select key="point.item.item_no" list="itemList" listKey="item_no" listValue="item_description" />
		<s:select key="point.company.cmp_no" list="companyList" listKey="cmp_no" listValue="cmp_description" /> 
		<s:select key="point.member.account" list="memberList" listKey="account" listValue="account" /> 
		<s:textfield key="point.value" />
		<s:textfield key="point.remark" />
		<sj:datepicker key="point.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />		
		<sj:datepicker key="point.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="true" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:submit value="%{getText('submit.add')}" align="center" />
	</s:form>
	<center><br><a href="listPoint.action"><s:text name="point.list.all" /></a></center>
</body>
</html>


