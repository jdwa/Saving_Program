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
<title><s:text name="term.detail" /></title>
</head>
<body>
	<h3><s:text name="term.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateTerm" method="post" namespace="/">
		<s:textfield key="term.id" value="%{term.id}" readonly="true" />
		<s:textfield key="term.term_no" value="%{term.term_no}" readonly="true" />
		<s:textfield key="term.term_description" value="%{term.term_description}" />
		<s:textfield key="term.creation_date" value="%{term.creation_date}" readonly="true" />
		<s:textfield key="term.remark" value="%{term.remark}" />
		<s:textfield key="term.timestamp" value="%{term.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>