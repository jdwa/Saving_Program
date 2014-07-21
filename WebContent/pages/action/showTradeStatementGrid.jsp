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
<title><s:text name="trade.detail" /></title>
</head>
<body>
	<table>
		<tr>
			<th><s:text name="trade.valid" /></th>
			<th><s:text name="trade.tx_no" /></th>
			<th><s:text name="trade.category.category_description" /></th>
			<th><s:text name="trade.fund" /></th>
			<th><s:text name="trade.settlement_date" /></th>
			<th><s:text name="trade.creation_date" /></th>	
		</tr>
		<tr>
			<td><s:property value="%{#session.S_Trade.valid}"/></td>
			<td><s:property value="%{#session.S_Trade.tx_no}"/></td>
			<td><s:property value="%{#session.S_Trade.category.category_description}"/></td>
			<td><s:property value="%{#session.S_Trade.fund}"/></td>
			<td><s:date name="%{#session.S_Trade.settlement_date}" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td><s:date name="%{#session.S_Trade.creation_date}" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</table>
	<h3><s:text name="trade.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
    <s:url id="type001Url" action="gridType001Statement"/>
    <s:url id="type002Url" action="gridType002Statement"/>
    <s:url id="type003Url" action="gridType003Statement"/>
    <s:url id="type004Url" action="gridType004Statement"/>
    <s:url id="type005Url" action="gridType005Statement"/>
    <sjg:grid
        id="gridtable001"
        caption='%{getText("type.001")}'
        dataType="json"
        href="%{type001Url}"
        pager="true"
        autowidth="true"
        navigator="true"
        navigatorSearchOptions="{sopt:['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']}"
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
        sortname="id"
		sortorder="desc"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' formatter="integer" sortable="true" hidden="true" />
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
    <sjg:grid
        id="gridtable002"
        caption='%{getText("type.002")}'
        dataType="json"
        href="%{type002Url}"
        pager="true"
        autowidth="true"
        navigator="true"
        navigatorSearchOptions="{sopt:['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']}"
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
        sortname="id"
		sortorder="desc"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' formatter="integer" sortable="true" hidden="true" />
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
    <sjg:grid
        id="gridtable003"
        caption='%{getText("type.003")}'
        dataType="json"
        href="%{type003Url}"
        pager="true"
        autowidth="true"
        navigator="true"
        navigatorSearchOptions="{sopt:['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']}"
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
        sortname="id"
		sortorder="desc"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' formatter="integer" sortable="true" hidden="true" />
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
    <sjg:grid
        id="gridtable004"
        caption='%{getText("type.004")}'
        dataType="json"
        href="%{type004Url}"
        pager="true"
        autowidth="true"
        navigator="true"
        navigatorSearchOptions="{sopt:['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']}"
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
        sortname="id"
		sortorder="desc"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' formatter="integer" sortable="true" hidden="true" />
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
    <sjg:grid
        id="gridtable005"
        caption='%{getText("type.005")}'
        dataType="json"
        href="%{type005Url}"
        pager="true"
        autowidth="true"
        navigator="true"
        navigatorSearchOptions="{sopt:['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']}"
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
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' formatter="integer" sortable="true" hidden="true" />
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