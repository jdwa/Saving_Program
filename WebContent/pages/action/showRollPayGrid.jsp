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
<title><s:text name="roll.detail" /></title>
</head>
<body>
	<table>
		<tr>
			<th><s:text name="roll.valid" /></th>
			<th><s:text name="roll.ro_no" /></th>
			<th><s:text name="roll.value" /></th>
			<th><s:text name="roll.settlement_date" /></th>
			<th><s:text name="roll.creation_date" /></th>	
		</tr>
		<tr>
			<td><s:property value="%{#session.S_Roll.valid}"/></td>
			<td><s:property value="%{#session.S_Roll.ro_no}"/></td>
			<td><s:property value="%{#session.S_Roll.value}"/></td>
			<td><s:date name="%{#session.S_Roll.settlement_date}" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td><s:date name="%{#session.S_Roll.creation_date}" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</table>
	<h3><s:text name="roll.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
    <s:url id="gridRollPayUrl" action="gridRollPay"/>
	<sjg:grid
	       id="gridtable"
	       caption='%{getText("pay.detail")}'
	       dataType="json"
	       href="%{gridRollPayUrl}"
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
	       gridModel="payList"
	       rowList="10,15,20"
	       rowNum="10"
	       sortname="id"
		   sortorder="desc"
		   onSelectRowTopics="rowselect"
	   	   onEditInlineSuccessTopics="oneditsuccess"
		   userDataOnFooter="true"
		   footerrow="true"
		   multiselect="false">  	    	
		   <sjg:gridColumn name="id" index="id" title='%{getText("pay.id")}' sortable="true" hidden="true" />
		   <sjg:gridColumn name="roll.ro_no" index="roll.ro_no" title='%{getText("pay.roll.ro_no")}' sortable="true" />
		   <sjg:gridColumn name="member.account" index="member.account" title='%{getText("pay.member.account")}' sortable="true" />
		   <sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("pay.company.cmp_description")}' sortable="true" />
		   <sjg:gridColumn name="value" index="value" title='%{getText("pay.value")}' formatter="currency" sortable="true" />
		   <sjg:gridColumn name="term.term_description" index="term.term_description" title='%{getText("pay.term.term_description")}' sortable="true" />
		   <sjg:gridColumn name="pay_date" index="pay_date" title='%{getText("pay.pay_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
		   <sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("pay.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
		   <sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("pay.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
		   <sjg:gridColumn name="remark" index="remark" title='%{getText("pay.remark")}' sortable="true" />
		   <sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("pay.timestamp")}' sortable="true" />
	</sjg:grid>
</body>
</html>