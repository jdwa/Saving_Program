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
<title><s:text name="roll.detail" /></title>
</head>
<body>
	<h3><s:text name="roll.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateRoll" method="post" namespace="/">
		<s:checkbox key="roll.valid" readonly="true" />
		<s:textfield key="roll.id" value="%{roll.id}" readonly="true" />
		<s:textfield key="roll.ro_no" value="%{roll.ro_no}" readonly="false" />
		<s:textfield key="roll.value" value="%{roll.value}" />
		<s:textfield key="roll.pay_date" value="%{roll.pay_date}" readonly="false" />
		<s:textfield key="roll.settlement_date" value="%{roll.settlement_date}" readonly="true" />
		<s:textfield key="roll.creation_date" value="%{roll.creation_date}" readonly="true" />
		<s:textfield key="roll.remark" value="%{roll.remark}" />
		<s:textfield key="roll.timestamp" value="%{roll.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>