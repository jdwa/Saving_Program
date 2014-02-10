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
<title><s:text name="point.detail" /></title>
</head>
<body>
	<h3><s:text name="point.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updatePoint" method="post" namespace="/">
		<s:textfield key="point.id" value="%{point.id}" readonly="true" />
		<s:textfield key="point.member.account" value="%{point.member.account}" readonly="true" />
		<s:textfield key="point.value" value="%{point.value}" readonly="true" />
		<s:textfield key="point.task.tk_no" value="%{point.task.tk_no}" readonly="true" />
		<s:textfield key="point.item.item_description" value="%{point.item.item_description}" readonly="true" />
		<s:textfield key="point.company.cmp_description" value="%{point.company.cmp_description}" readonly="true" />
		<s:textfield key="point.settlement_date" value="%{point.settlement_date}" readonly="true" />
		<s:textfield key="point.creation_date" value="%{point.creation_date}" readonly="true" />
		<s:textfield key="point.remark" value="%{point.remark}" />
		<s:textfield key="point.timestamp" value="%{point.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>