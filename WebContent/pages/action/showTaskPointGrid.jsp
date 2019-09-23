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
<title><s:text name="task.detail" /></title>
</head>
<body>
	<table>
		<tr>
			<th><s:text name="task.valid" /></th>
			<th><s:text name="task.tk_no" /></th>
			<th><s:text name="task.value" /></th>
			<th><s:text name="task.settlement_date" /></th>
			<th><s:text name="task.creation_date" /></th>	
		</tr>
		<tr>
			<td><s:property value="%{#session.S_Task.valid}"/></td>
			<td><s:property value="%{#session.S_Task.tk_no}"/></td>
			<td><s:property value="%{#session.S_Task.value}"/></td>
			<td><s:date name="%{#session.S_Task.settlement_date}" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td><s:date name="%{#session.S_Task.creation_date}" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</table>
	<h3><s:text name="task.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
    <s:url id="item001Url" action="gridItem001Point"/>
    <s:url id="item002Url" action="gridItem002Point"/>
    <s:url id="item003Url" action="gridItem003Point"/>
    <s:url id="item004Url" action="gridItem004Point"/>
    <s:url id="item005Url" action="gridItem005Point"/>
    <s:url id="item006Url" action="gridItem006Point"/>
    <s:url id="item007Url" action="gridItem007Point"/>
    <s:url id="item008Url" action="gridItem008Point"/>
    <s:url id="item101Url" action="gridItem101Point"/>
    <sjg:grid
        id="gridtable001"
        caption='%{getText("item.001")}'
        dataType="json"
        href="%{item001Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
    <sjg:grid
        id="gridtable002"
        caption='%{getText("item.002")}'
        dataType="json"
        href="%{item002Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
    <sjg:grid
        id="gridtable003"
        caption='%{getText("item.003")}'
        dataType="json"
        href="%{item003Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
    <sjg:grid
        id="gridtable004"
        caption='%{getText("item.004")}'
        dataType="json"
        href="%{item004Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
    <sjg:grid
        id="gridtable005"
        caption='%{getText("item.005")}'
        dataType="json"
        href="%{item005Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
    <sjg:grid
        id="gridtable006"
        caption='%{getText("item.006")}'
        dataType="json"
        href="%{item006Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
    <sjg:grid
        id="gridtable007"
        caption='%{getText("item.007")}'
        dataType="json"
        href="%{item007Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
    <sjg:grid
        id="gridtable008"
        caption='%{getText("item.008")}'
        dataType="json"
        href="%{item008Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>    
    <sjg:grid
        id="gridtable101"
        caption='%{getText("item.101")}'
        dataType="json"
        href="%{item101Url}"
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
        gridModel="pointList"
        rowList="10,20,50"
        rowNum="10"
        sortname="id"
		sortorder="desc"        
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="task.tk_no" index="task.tk_no" title='%{getText("point.task.tk_no")}' sortable="true" />
    	<sjg:gridColumn name="member.account" index="member.account" title='%{getText("point.member.account")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("point.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="value" index="value" title='%{getText("point.value")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="item.item_description" index="item.item_description" title='%{getText("point.item.item_description")}' sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("point.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("point.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("point.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("point.timestamp")}' sortable="true" />
    </sjg:grid>
</body>
</html>