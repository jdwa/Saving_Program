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
<title><s:text name="member.action.query.point" /></title>
</head>
<body>
	<h3><s:text name="member.action.query.point" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="queryMemberPoint" method="post" namespace="/">
		<sj:autocompleter key="member.account" list="memberList" listKey="account" listValue="account" />
		<s:submit value="%{getText('submit.query')}" align="center" />
	</s:form>
</body>
</html>


