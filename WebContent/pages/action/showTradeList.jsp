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
<title><s:text name="trade.list.all" /></title>
</head>
<body>
	<h3><s:text name="trade.list.all" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="trade">
		<tr>
			<th><s:text name="trade.id" /></th>
			<th><s:text name="trade.tx_no" /></th>
			<th><s:text name="trade.fund" /></th>
			<th><s:text name="trade.settlement_date" /></th>
			<th><s:text name="trade.creation_date" /></th>
			<th><s:text name="trade.remark" /></th>
			<th><s:text name="trade.timestamp" /></th>
			<th><s:text name="submit.delete" /></th>
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="tradeList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="tx_no" /></td>
				<td><s:property value="fund" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<td><a href="deleteTrade.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				<td><a href="detailTrade.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>