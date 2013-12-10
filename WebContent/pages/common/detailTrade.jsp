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
<title><s:text name="trade.detail" /></title>
</head>
<body>
	<h3><s:text name="trade.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateTrade" method="post" namespace="/">
		<s:checkbox key="trade.valid" readonly="true" />
		<s:textfield key="trade.id" value="%{trade.id}" readonly="true" />
		<s:textfield key="trade.tx_no" value="%{trade.tx_no}" readonly="false" />
		<s:textfield key="trade.category.category_description" value="%{trade.category.category_description}" readonly="true" />
		<s:textfield key="trade.fund" value="%{trade.fund}" />
		<s:textfield key="trade.settlement_date" value="%{trade.settlement_date}" readonly="true" />
		<s:textfield key="trade.creation_date" value="%{trade.creation_date}" readonly="true" />
		<s:textfield key="trade.remark" value="%{trade.remark}" />
		<s:textfield key="trade.timestamp" value="%{trade.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>