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
<title><s:text name="category.add" /></title>
</head>
<body>
	<h3><s:text name="category.add" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addCategory" method="post" namespace="/">
		<s:textfield key="category.category_no" />
		<s:textfield key="category.category_description" />
		<s:textfield key="category.remark" />
		<sj:datepicker key="category.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="true" showOn="focus"
			timepicker="true" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:submit value="%{getText('submit.add')}" align="center" />
	</s:form>
	<center><br><a href="listCategory.action"><s:text name="category.list.all" /></a></center>
</body>
</html>


