<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
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
<title><s:text name="statement.list.all" /></title>
</head>
<body>
	<s:actionerror />
	<s:actionmessage />
	<table>
		<tr>
			<th><s:text name="member.account" /></th>
			<th><s:text name="member.company.cmp_description" /></th>
			<th><s:text name="member.role.role_description" /></th>
			<th><s:text name="member.creation_date" /></th>	
		</tr>
		<tr>
			<td><s:property value="%{#session.S_Member.account}"/></td>
			<td><s:property value="%{#session.S_Member.company.cmp_description}"/></td>
			<td><s:property value="%{#session.S_Member.role.role_description}"/></td>
			<td><s:date name="%{#session.S_Member.creation_date}" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</table>
	<br>
	<h3><s:text name="statement.list.all" /></h3>
    <s:url id="statementurl" action="gridMemberStatement"/>
    <sjg:grid
        id="gridtable"
        caption='%{getText("statement.detail")}'
        dataType="json"
        href="%{statementurl}"
        pager="true"
        autowidth="true"
        navigator="true"
        navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
        navigatorAddOptions="{height:280,reloadAfterSubmit:false}"
        navigatorEditOptions="{height:280,reloadAfterSubmit:false}"
        navigatorView="true"
        navigatorAdd="false"
        navigatorEdit="false"
        navigatorDelete="false"
        navigatorDeleteOptions="{height:280,reloadAfterSubmit:false}"
        gridModel="statementList"
        rowList="5,10,20"
        rowNum="5"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' formatter="integer" sortable="true" />
    	<sjg:gridColumn name="trade.tx_no" index="trade.tx_no" title='%{getText("statement.trade.tx_no")}' sortable="true" />
    	<sjg:gridColumn name="trade.category.category_description" index="trade.category.category_description" title='%{getText("statement.trade.category.category_description")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("statement.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("statement.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="fund" index="fund" title='%{getText("statement.fund")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="type.type_description" index="type.type_description" title='%{getText("statement.type.type_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("statement.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("statement.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("statement.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("statement.timestamp")}' sortable="true" />
    </sjg:grid>
</body>
</html>