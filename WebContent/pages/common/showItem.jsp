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
<title><s:text name="item.detail" /></title>
</head>
<body>
	<h3><s:text name="item.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="item">
		<tr>
			<th><s:text name="item.id" /></th>
			<th><s:text name="item.item_no" /></th>
			<th><s:text name="item.item_description" /></th>
			<th><s:text name="item.creation_date" /></th>
			<th><s:text name="item.remark" /></th>
			<th><s:text name="item.timestamp" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="submit.delete" /></th>
			</security:authorize>
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="itemList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="item_no" /></td>
				<td><s:property value="item_description" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><a href="deleteItem.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				</security:authorize>
				<td><a href="detailItem.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>