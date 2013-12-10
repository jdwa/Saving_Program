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
<title><s:text name="rate.detail" /></title>
</head>
<body>
	<h3><s:text name="rate.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateRate" method="post" namespace="/">
		<s:textfield key="rate.id" value="%{rate.id}" readonly="true" />
		<s:textfield key="rate.rate_date" value="%{rate.rate_date}" readonly="true" />
		<s:textfield key="rate.rate_value" value="%{rate.rate_value}" />
		<s:textfield key="rate.creation_date" value="%{rate.creation_date}" readonly="true" />
		<s:textfield key="rate.remark" value="%{rate.remark}" />
		<s:textfield key="rate.timestamp" value="%{rate.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>