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
<title><s:text name="item.detail" /></title>
</head>
<body>
	<h3><s:text name="item.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateItem" method="post" namespace="/">
		<s:textfield key="item.id" value="%{item.id}" readonly="true" />
		<s:textfield key="item.item_no" value="%{item.item_no}" readonly="true" />
		<s:textfield key="item.item_description" value="%{item.item_description}" />
		<s:textfield key="item.creation_date" value="%{item.creation_date}" readonly="true" />
		<s:textfield key="item.remark" value="%{item.remark}" />
		<s:textfield key="item.timestamp" value="%{item.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>