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
    			barSpace: 10, // this is default space between bars in pixels
    			width: 800, // default width of your graph
    			height: 250, //default height of your graph
    			color: '#000000', // if you don't send colors for your data this will be default bars color
    			colors: ['#00CD00', '#00FF00', '#FF4500', '#FFFF00', '#1874CD'], // array of colors that will be used for your bars and legends
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
	<br>
	<table id="statement">
		<tr>
			<th><s:text name="statement.id" /></th>
			<th><s:text name="statement.trade.tx_no" /></th>
			<th><s:text name="statement.trade.category.category_description" /></th>
			<th><s:text name="statement.member.account" /></th>
			<th><s:text name="statement.company.cmp_description" /></th>
			<th><s:text name="statement.fund" /></th>
			<th><s:text name="statement.type.type_description" /></th>
			<th><s:text name="statement.settlement_date" /></th>
			<th><s:text name="statement.creation_date" /></th>
			<th><s:text name="statement.remark" /></th>
			<th><s:text name="statement.timestamp" /></th>
		</tr>

		<s:iterator value="statementList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="trade.tx_no" /></td>
				<td><s:property value="trade.category.category_description" /></td>
				<td><s:property value="member.account" /></td>
				<td><s:property value="company.cmp_description" /></td>
				<td><s:property value="fund" /></td>
				<td><s:property value="type.type_description" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>