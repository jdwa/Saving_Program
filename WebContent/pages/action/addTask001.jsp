<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ldcgroup.model.Item"%>
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
<title><s:text name="action.task.001" /></title>
</head>
<body>
	<h3><s:text name="action.task.001" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addTask001" method="post" namespace="/">
		<s:textfield key="task.tk_no" />
		<sj:autocompleter key="member.account" list="memberList" listKey="account" listValue="account" />
		<s:select key="item.item_no" list="itemList" listKey="item_no" listValue="item_description" />
		<s:textfield key="task.value" />
		<sj:datepicker key="task.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<sj:datepicker key="task.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:textfield key="task.remark" />	
		<s:submit name="" key="submit.add" align="center" />
	</s:form>
</body>
</html>