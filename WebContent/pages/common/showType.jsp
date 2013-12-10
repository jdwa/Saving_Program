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
<title><s:text name="type.detail" /></title>
</head>
<body>
	<h3><s:text name="type.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="type">
		<tr>
			<th><s:text name="type.id" /></th>
			<th><s:text name="type.type_no" /></th>
			<th><s:text name="type.type_description" /></th>
			<th><s:text name="type.creation_date" /></th>
			<th><s:text name="type.remark" /></th>
			<th><s:text name="type.timestamp" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="submit.delete" /></th>
			</security:authorize>	
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="typeList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="type_no" /></td>
				<td><s:property value="type_description" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><a href="deleteType.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				</security:authorize>	
				<td><a href="detailType.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>