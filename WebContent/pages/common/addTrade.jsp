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
<title><s:text name="trade.add" /></title>
</head>
<body>
	<h3><s:text name="trade.add" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addTrade" method="post" namespace="/">
		<s:checkbox key="trade.valid" fieldValue="true" readonly="true"/>
		<s:textfield key="trade.tx_no" />
		<s:select key="trade.category.category_no" list="categoryList" listKey="category_no" listValue="category_description" />
		<s:textfield key="trade.fund" />
		<s:textfield key="trade.remark" />
		<sj:datepicker key="trade.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />				
		<sj:datepicker key="trade.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="true" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:submit value="%{getText('submit.add')}" align="center" />
	</s:form>
	<a href="listTrade.action"><s:text name="trade.list.all" /></a>
</body>
</html>


