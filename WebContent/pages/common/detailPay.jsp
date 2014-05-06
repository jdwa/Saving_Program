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
<title><s:text name="pay.detail" /></title>
</head>
<body>
	<h3><s:text name="pay.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updatePay" method="post" namespace="/">
		<s:textfield key="pay.id" value="%{pay.id}" readonly="true" />
		<s:textfield key="pay.member.account" value="%{pay.member.account}" readonly="true" />
		<s:textfield key="pay.value" value="%{pay.value}" readonly="true" />
		<s:textfield key="pay.roll.ro_no" value="%{pay.roll.ro_no}" readonly="true" />
		<s:textfield key="pay.term.term_description" value="%{pay.term.term_description}" readonly="true" />
		<s:textfield key="pay.company.cmp_description" value="%{pay.company.cmp_description}" readonly="true" />
		<s:textfield key="pay.pay_date" value="%{pay.pay_date}" readonly="true" />
		<s:textfield key="pay.settlement_date" value="%{pay.settlement_date}" readonly="true" />
		<s:textfield key="pay.creation_date" value="%{pay.creation_date}" readonly="true" />
		<s:textfield key="pay.remark" value="%{pay.remark}" />
		<s:textfield key="pay.timestamp" value="%{pay.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>