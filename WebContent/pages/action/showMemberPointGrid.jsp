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
<title><s:text name="point.list.all" /></title>
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
	<h3><s:text name="point.list.all" /></h3>
    <s:url id="pointurl" action="gridMemberPoint"/>
    <sjg:grid
        id="gridtable"
        caption='%{getText("point.detail")}'
        dataType="json"
        href="%{pointurl}"
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
        rowList="5,10,20"
        rowNum="5"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("point.id")}' formatter="integer" sortable="true" />
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