<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
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
<title><s:text name="rate.detail" /></title>
</head>
<body>
	<h3><s:text name="rate.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="rate">
		<tr>
			<th><s:text name="rate.id" /></th>
			<th><s:text name="rate.rate_date" /></th>
			<th><s:text name="rate.rate_value" /></th>
			<th><s:text name="rate.creation_date" /></th>
			<th><s:text name="rate.remark" /></th>
			<th><s:text name="rate.timestamp" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="submit.delete" /></th>
			</security:authorize>
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="rateList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:date name="rate_date" format="yyyy" /></td>
				<td><s:property value="rate_value" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><a href="deleteRate.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				</security:authorize>
				<td><a href="detailRate.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>