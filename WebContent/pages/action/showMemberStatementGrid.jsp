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
	<script src="js/jqBarGraph.1.1.js" type="text/javascript" ></script>
    <script type="text/javascript">     
      $(document).ready(function () {        
    	  stackedByMonth = new Array(
  				<s:iterator value="barList">
	            	[[<s:property value='fund_type_001' />, 
	              	<s:property value='fund_type_002' />, 
	              	<s:property value='fund_type_003' />, 
	              	<s:property value='fund_type_004' />, 
	              	<s:property value='fund_type_005' />], 
	              	'<s:property value="label" />'],
				</s:iterator>
    			[[0,0,0,0,0],'']	
    			);
			
    	  $("#stacked-graph").jqBarGraph({
    			data: stackedByMonth, // array of data for your graph
    			title: '', // title of your graph, accept HTML
    			barSpace: 50, // this is default space between bars in pixels
    			width: 800, // default width of your graph
    			height: 250, //default height of your graph
    			color: '#000000', // if you don't send colors for your data this will be default bars color
    			colors: ['#FF6666', '#FFFF00', '#3399CC', '#CC0066', '#663399'], // array of colors that will be used for your bars and legends
    			lbl: '', // if there is no label in your array
    			sort: false, // sort your data before displaying graph, you can sort as 'asc' or 'desc'
    			position: 'bottom', // position of your bars, can be 'bottom' or 'top'. 'top' doesn't work for multi type
    			prefix: '', // text that will be shown before every label
    			postfix: '', // text that will be shown after every label
    			animate: true, // if you don't need animated appearance change to false
    			speed: 2, // speed of animation in seconds
    			legendWidth: 100, // width of your legend box
    			legend: true, // if you want legend change to true
    			legends: ["<s:text name='type.001' />", 
    	                  "<s:text name='type.002' />", 
    	                  "<s:text name='type.003' />", 
    	                  "<s:text name='type.004' />", 
    	                  "<s:text name='type.005' />"], // array for legend. for simple graph type legend will be extracted from labels if you don't set this
    			type: 'stacked', // for multi array data default graph type is stacked, you can change to 'multi' for multi bar type
    			showValues: false, // you can use this for multi and stacked type and it will show values of every bar part
    			showValuesColor: '#fff' // color of font for values 	
    	  });
      });
    </script>

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
	<div id='stacked-graph'></div>
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
    	<sjg:gridColumn name="id" index="id" title='%{getText("statement.id")}' sortable="true" hidden="true" />
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