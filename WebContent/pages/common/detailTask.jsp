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
<title><s:text name="task.detail" /></title>
</head>
<body>
	<h3><s:text name="task.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="updateTask" method="post" namespace="/">
		<s:checkbox key="task.valid" readonly="true" />
		<s:textfield key="task.id" value="%{task.id}" readonly="true" />
		<s:textfield key="task.tk_no" value="%{task.tk_no}" readonly="false" />
		<s:textfield key="task.value" value="%{task.value}" />
		<s:textfield key="task.settlement_date" value="%{task.settlement_date}" readonly="true" />
		<s:textfield key="task.creation_date" value="%{task.creation_date}" readonly="true" />
		<s:textfield key="task.remark" value="%{task.remark}" />
		<s:textfield key="task.timestamp" value="%{task.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>