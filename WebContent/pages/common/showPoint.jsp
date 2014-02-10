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
<title><s:text name="point.detail" /></title>
</head>
<body>
	<h3><s:text name="point.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="point">
		<tr>
			<th><s:text name="point.id" /></th>
			<th><s:text name="point.task.tk_no" /></th>
			<th><s:text name="point.member.account" /></th>
			<th><s:text name="point.company.cmp_description" /></th>
			<th><s:text name="point.value" /></th>
			<th><s:text name="point.item.item_description" /></th>
			<th><s:text name="point.settlement_date" /></th>
			<th><s:text name="point.creation_date" /></th>
			<th><s:text name="point.remark" /></th>
			<th><s:text name="point.timestamp" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="submit.delete" /></th>
			</security:authorize>	
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="pointList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="task.tk_no" /></td>
				<td><s:property value="member.account" /></td>
				<td><s:property value="company.cmp_description" /></td>
				<td><s:property value="value" /></td>
				<td><s:property value="item.item_description" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><a href="deletePoint.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				</security:authorize>	
				<td><a href="detailPoint.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>