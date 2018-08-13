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
<title><s:text name="pay.list.all" /></title>
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
	<h3><s:text name="pay.list.all" /></h3>
    <s:url id="gridMemberSalaryUrl" action="gridMemberSalary"/>
	<s:url id="subgridMemberSalaryUrl" action="subgridMemberSalary"/>
    <sjg:grid
        id="gridtable"
        caption='%{getText("pay.detail")}'
        dataType="json"
        href="%{gridMemberSalaryUrl}"
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
        gridModel="rollList"
        rowList="10,15,20"
        rowNum="10"
        sortname="id"
		sortorder="desc"
    	onSelectRowTopics="rowselect"
    	onEditInlineSuccessTopics="oneditsuccess"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="false">  	    	
    	
	    	<sjg:grid
		        id="subgridtable"
		        caption='%{getText("pay.detail")}'
		        dataType="json"
		        href="%{subgridMemberSalaryUrl}"
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
    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("roll.id")}' sortable="true" hidden="true" />
    	<sjg:gridColumn name="ro_no" index="ro_no" title='%{getText("roll.ro_no")}' sortable="true" />
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("roll.company.cmp_description")}' sortable="true" />
    	<sjg:gridColumn name="subvalue" index="subvalue" title='%{getText("roll.subvalue")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="pay_date" index="settlement_date" title='%{getText("roll.pay_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="settlement_date" index="settlement_date" title='%{getText("roll.settlement_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("roll.creation_date")}' formatter="date" formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d H:i:s'}" sortable="true" />
    	<sjg:gridColumn name="remark" index="remark" title='%{getText("roll.remark")}' sortable="true" />
    	<sjg:gridColumn name="timestamp" index="timestamp" title='%{getText("roll.timestamp")}' sortable="true" />
    </sjg:grid>
    
	<iframe name="labor" id="labor" src="pages/action/laborStandard.jsp" frameborder="0" height="1100" width="800"> </iframe>
		
</body>
</html>