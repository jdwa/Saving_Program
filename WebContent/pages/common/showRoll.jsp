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
<title><s:text name="roll.detail" /></title>
</head>
<body>
	<h3><s:text name="roll.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="roll">
		<tr>
			<th><s:text name="roll.valid" /></th>
			<th><s:text name="roll.id" /></th>
			<th><s:text name="roll.ro_no" /></th>
			<th><s:text name="roll.value" /></th>
			<th><s:text name="roll.pay_date" /></th>
			<th><s:text name="roll.settlement_date" /></th>
			<th><s:text name="roll.creation_date" /></th>
			<th><s:text name="roll.remark" /></th>
			<th><s:text name="roll.timestamp" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="submit.delete" /></th>
			</security:authorize>	
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="rollList">
			<tr>
				<td><s:property value="valid"/></td>
				<td><s:property value="id" /></td>
				<td><s:property value="ro_no" /></td>
				<td><s:property value="value" /></td>
				<td><s:date name="pay_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><a href="deleteRoll.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				</security:authorize>	
				<td><a href="detailRoll.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
	<br>
	<h3><s:text name="pay.detail" /></h3>
	<table id="pay">
		<tr>
			<th><s:text name="pay.id" /></th>
			<th><s:text name="pay.roll.ro_no" /></th>
			<th><s:text name="pay.member.account" /></th>
			<th><s:text name="pay.company.cmp_description" /></th>
			<th><s:text name="pay.value" /></th>
			<th><s:text name="pay.term.term_description" /></th>
			<th><s:text name="pay.settlement_date" /></th>
			<th><s:text name="pay.creation_date" /></th>
			<th><s:text name="pay.remark" /></th>
			<th><s:text name="pay.timestamp" /></th>
			<th><s:text name="submit.delete" /></th>
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="payList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="roll.ro_no" /></td>
				<td><s:property value="member.account" /></td>
				<td><s:property value="company.cmp_description" /></td>
				<td><s:property value="value" /></td>
				<td><s:property value="term.term_description" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<td><a href="deletePay.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				<td><a href="detailPay.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>	
</body>
</html>