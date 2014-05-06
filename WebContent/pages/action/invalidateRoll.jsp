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
<title><s:text name="roll.action.invalidate" /></title>
</head>
<body>
	<h3><s:text name="roll.action.invalidate" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="invalidateRoll" method="post" namespace="/">
		<sj:autocompleter key="roll.ro_no" list="rollList" listKey="ro_no" listValue="ro_no" />
		<s:submit value="%{getText('submit.invalidate')}" align="center" />
	</s:form>
</body>
</html>


