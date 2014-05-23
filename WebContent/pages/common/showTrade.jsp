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
<title><s:text name="trade.detail" /></title>
</head>
<body>
	<h3><s:text name="trade.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="trade">
		<tr>
			<th><s:text name="trade.valid" /></th>
			<th><s:text name="trade.id" /></th>
			<th><s:text name="trade.tx_no" /></th>
			<th><s:text name="trade.category.category_description" /></th>
			<th><s:text name="trade.fund" /></th>
			<th><s:text name="trade.settlement_date" /></th>
			<th><s:text name="trade.creation_date" /></th>
			<th><s:text name="trade.remark" /></th>
			<th><s:text name="trade.timestamp" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="submit.delete" /></th>
			</security:authorize>	
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="tradeList">
			<tr>
				<td><s:property value="valid"/></td>
				<td><s:property value="id" /></td>
				<td><s:property value="tx_no" /></td>
				<td><s:property value="category.category_description" /></td>
				<td><s:property value="fund" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><a href="deleteTrade.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				</security:authorize>	
				<td><a href="detailTrade.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
	<br>
	<h3><s:text name="statement.detail" /></h3>
	<table id="statement">
		<tr>
			<th><s:text name="statement.id" /></th>
			<th><s:text name="statement.trade.tx_no" /></th>
			<th><s:text name="statement.trade.category.category_description" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="statement.member.account" /></th>
			</security:authorize>
			<th><s:text name="statement.company.cmp_description" /></th>
			<th><s:text name="statement.fund" /></th>
			<th><s:text name="statement.type.type_description" /></th>
			<th><s:text name="statement.settlement_date" /></th>
			<th><s:text name="statement.creation_date" /></th>
			<th><s:text name="statement.remark" /></th>
			<th><s:text name="statement.timestamp" /></th>
			<th><s:text name="submit.delete" /></th>
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="statementList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="trade.tx_no" /></td>
				<td><s:property value="trade.category.category_description" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><s:property value="member.account" /></td>
				</security:authorize>
				<td><s:property value="company.cmp_description" /></td>
				<td><s:property value="fund" /></td>
				<td><s:property value="type.type_description" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<td><a href="deleteStatement.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				<td><a href="detailStatement.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>	
</body>
</html>