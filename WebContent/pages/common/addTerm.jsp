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
<title><s:text name="term.add" /></title>
</head>
<body>
	<h3><s:text name="term.add" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addTerm" method="post" namespace="/">
		<s:textfield key="term.term_no" />
		<s:textfield key="term.term_description" />
		<s:textfield key="term.remark" />
		<sj:datepicker key="term.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="true" showOn="focus"
			timepicker="true" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:submit value="%{getText('submit.add')}" align="center" />
	</s:form>
	<center><br><a href="listTerm.action"><s:text name="term.list.all" /></a></center>
</body>
</html>


