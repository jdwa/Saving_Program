<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ldcgroup.model.Type"%>
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
<title><s:text name="action.trade.002" /></title>
</head>
<body>
	<h3><s:text name="action.trade.002" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="addTrade002" method="post" namespace="/" enctype="multipart/form-data">
		<s:textfield key="trade.tx_no" />
		<s:file key="fileUpload" size="50" />
		<sj:datepicker key="trade.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />		
		<sj:datepicker key="trade.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:textfield key="trade.remark" />	
		<s:submit name="" key="submit.add" align="center" />
	</s:form>
</body>
</html>