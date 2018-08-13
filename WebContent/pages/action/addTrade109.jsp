<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ldcgroup.model.Type"%>
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
<title><s:text name="action.trade.109" /></title>
<script type="text/javascript">

	idsOfSelectedRows = [];
	
    updateIdsOfSelectedRows = function (id, isSelected) {
        var index = $.inArray(id, idsOfSelectedRows);
        if (!isSelected && index >= 0) {
            idsOfSelectedRows.splice(index, 1); // remove id from the list
        } else if (index < 0) {
            idsOfSelectedRows.push(id);
        }
    };

	$.subscribe('gridtable_onSelectRow', function (event, data) {
		var id = event.originalEvent.id;
		var isSelected = event.originalEvent.status;	
        var index = $.inArray(id, idsOfSelectedRows);
        if (!isSelected && index >= 0) {
            idsOfSelectedRows.splice(index, 1); // remove id from the list
        } else if (index < 0) {
            idsOfSelectedRows.push(id);
        }
        document.getElementById("ids").value = idsOfSelectedRows;
    });

	$.subscribe('gridtable_onSelectAll', function (event, data) {
        var i, count, id;
        var isSelected = event.originalEvent.status;
        var aRowids = event.originalEvent.ids;
        for (i = 0, count = aRowids.length; i < count; i++) {
            id = aRowids[i];
            updateIdsOfSelectedRows(id, isSelected);
        }
        document.getElementById("ids").value = idsOfSelectedRows;
    });
	
	$.subscribe('gridtable_loadComplete', function (event, data) {
        var $this = $("#gridtable"), i, count;
        for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
            $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
        }
        document.getElementById("ids").value = idsOfSelectedRows;
    });

	/*
	$.subscribe('before', function(event, data) {
		var fData = event.originalEvent.formData;
		fData[0].value = idsOfSelectedRows;
		
		alert('About to submit: \n\n' + fData[0].value + ' to target '
				+ event.originalEvent.options.target + ' with timeout '
				+ event.originalEvent.options.timeout);

		var form = event.originalEvent.form[0];
		if (form.echo.value.length < 2) {
			alert('Please enter a value with min 2 characters');
			// Cancel Submit comes with 1.8.0          
			event.originalEvent.options.submit = false;
		}
	});

	$.subscribe('complete',	function(event, data) {
		alert('status: '
				+ event.originalEvent.status
				+ '\n\nresponseText: \n'
				+ event.originalEvent.request.responseText
				+ '\n\nThe output div should have already been updated with the responseText.');
	});

	$.subscribe('errorState', function(event, data) {
		alert('status: ' + event.originalEvent.status + '\n\nrequest status: '
				+ event.originalEvent.request.status);
	});
	*/
	
</script>
</head>
<body>
	<h3><s:text name="action.trade.102" /></h3>
	<s:actionerror />
	<s:actionmessage />
	
	<s:url id="gridMemberSeniorityUrl" action="gridMemberSeniority"/>
	<s:url id="subgridMemberSeniorityUrl" action="subgridMemberSeniority"/>
    <sjg:grid
        id="gridtable"
        caption='%{getText("member.detail")}'
        dataType="json"
        href="%{gridMemberSeniorityUrl}"
        pager="true"
        autowidth="true"
        navigator="true"
        navigatorSearchOptions="{sopt:['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']}"
        navigatorAddOptions="{height:280,reloadAfterSubmit:false}"
        navigatorEditOptions="{height:280,reloadAfterSubmit:false}"
        navigatorView="true"
        navigatorAdd="true"
        navigatorEdit="false"
        navigatorDelete="false"
        navigatorDeleteOptions="{height:280,reloadAfterSubmit:false}"
        gridModel="memberList"
        rowList="5,10,20"
        rowNum="5"
        onCompleteTopics="gridtable_loadComplete"
    	onSelectRowTopics="gridtable_onSelectRow"
    	onSelectAllTopics="gridtable_onSelectAll"
    	userDataOnFooter="true"
    	footerrow="true"
    	multiselect="true">
    
    	    <sjg:grid
		        id="subgridtable"
		        caption='%{getText("statement.detail")}'
		        dataType="json"
		        href="%{subgridMemberSeniorityUrl}"
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
		    	userDataOnFooter="true"
		    	footerrow="true"
		    	multiselect="false">  	    	
		    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' sortable="true" />
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
      	    	
    	<sjg:gridColumn name="id" index="id" title='%{getText("member.id")}' sortable="true" />
    	<sjg:gridColumn name="active" index="active" title='%{getText("member.active")}' sortable="true" />		
    	<sjg:gridColumn name="accumulation" index="accumulation" title='%{getText("member.accumulation")}' formatter="currency" sortable="true" />
    	<sjg:gridColumn name="account" index="account" title='%{getText("member.account")}' sortable="true" />
    	<sjg:gridColumn name="amount" index="amount" title='%{getText("member.amount")}' formatter="currency" sortable="true" />	
    	<sjg:gridColumn name="company.cmp_description" index="company.cmp_description" title='%{getText("member.company.cmp_description")}' sortable="true" />	
    	<sjg:gridColumn name="role.role_description" index="role.role_description" title='%{getText("member.role.role_description")}' sortable="true" />	
    	<sjg:gridColumn name="creation_date" index="creation_date" title='%{getText("member.creation_date")}' sortable="true" />	
    </sjg:grid>

	<s:form action="addTrade109" method="post" namespace="/">
		<s:hidden id="ids" name="ids" />
		<s:textfield key="trade.tx_no" />
		<s:textfield key="withdrawAmount" value="6000" readonly="true" />
		<sj:datepicker key="trade.settlement_date" value="%{settlement_date}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />		
		<sj:datepicker key="trade.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="false" showOn="focus"
			timepicker="false" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:textfield key="trade.remark" />	
		<s:submit name="" key="submit.add" align="center" />	
	</s:form>
	 
	<!-- 
	<s:form action="addTrade102" method="post" namespace="/">
		<s:textfield id="ids" name="ids" value="Hello World!" />
		<sj:submit key="submit.add" timeout="2500"
			indicator="indicator" onBeforeTopics="before"
			onCompleteTopics="complete" onErrorTopics="errorState"
			effect="highlight" effectOptions="{ color : '#222222' }"
			effectDuration="3000" />
	</s:form>
	-->

</body>
</html>

