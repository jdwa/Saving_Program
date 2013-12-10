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
<title><s:text name="category.detail" /></title>
</head>
<body>
	<h3><s:text name="category.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateCategory" method="post" namespace="/">
		<s:textfield key="category.id" value="%{category.id}" readonly="true" />
		<s:textfield key="category.category_no" value="%{category.category_no}" readonly="true" />
		<s:textfield key="category.category_description" value="%{category.category_description}" />
		<s:textfield key="category.creation_date" value="%{category.creation_date}" readonly="true" />
		<s:textfield key="category.remark" value="%{category.remark}" />
		<s:textfield key="category.timestamp" value="%{category.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>