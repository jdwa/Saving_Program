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
<title><s:text name="type.detail" /></title>
</head>
<body>
	<h3><s:text name="type.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateType" method="post" namespace="/">
		<s:textfield key="type.id" value="%{type.id}" readonly="true" />
		<s:textfield key="type.type_no" value="%{type.type_no}" readonly="true" />
		<s:textfield key="type.type_description" value="%{type.type_description}" />
		<s:textfield key="type.creation_date" value="%{type.creation_date}" readonly="true" />
		<s:textfield key="type.remark" value="%{type.remark}" />
		<s:textfield key="type.timestamp" value="%{type.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>