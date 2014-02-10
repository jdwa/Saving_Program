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
	<script src="js/jqBarGraph.1.1.js" type="text/javascript" ></script>
    <script type="text/javascript">     
      $(document).ready(function () {        
    	  stackedByMonth = new Array(
  				<s:iterator value="barList">
	            	[[<s:property value='value_item_001' />, 
	              	<s:property value='value_item_002' />, 
	              	<s:property value='value_item_003' />, 
	              	<s:property value='value_item_004' />, 
	              	<s:property value='value_item_005' />, 
	              	<s:property value='value_item_006' />, 
	              	<s:property value='value_item_007' />, 
	              	<s:property value='value_item_008' />],
	              	'<s:property value="label" />'],
				</s:iterator>
    			[[0,0,0,0,0,0,0,0],'']	
    			);
			
    	  $("#stacked-graph").jqBarGraph({
    			data: stackedByMonth, // array of data for your graph
    			title: '', // title of your graph, accept HTML
    			barSpace: 5, // this is default space between bars in pixels
    			width: 800, // default width of your graph
    			height: 250, //default height of your graph
    			color: '#000000', // if you don't send colors for your data this will be default bars color
    			colors: ['#00CD00', '#00FF00', '#FF4500', '#FFFF00', '#1874CD', '#0045FF', '#FF00FF', '#18FFFF'], // array of colors that will be used for your bars and legends
    			lbl: '', // if there is no label in your array
    			sort: false, // sort your data before displaying graph, you can sort as 'asc' or 'desc'
    			position: 'bottom', // position of your bars, can be 'bottom' or 'top'. 'top' doesn't work for multi type
    			prefix: '', // text that will be shown before every label
    			postfix: '', // text that will be shown after every label
    			animate: true, // if you don't need animated appearance change to false
    			speed: 2, // speed of animation in seconds
    			legendWidth: 150, // width of your legend box
    			legend: true, // if you want legend change to true
    			legends: ["<s:text name='item.001' />", 
    	                  "<s:text name='item.002' />", 
    	                  "<s:text name='item.003' />", 
    	                  "<s:text name='item.004' />", 
    	                  "<s:text name='item.005' />", 
    	                  "<s:text name='item.006' />", 
    	                  "<s:text name='item.007' />", 
    	                  "<s:text name='item.008' />"], // array for legend. for simple graph type legend will be extracted from labels if you don't set this
    			type: 'multi', // for multi array data default graph type is stacked, you can change to 'multi' for multi bar type
    			showValues: false, // you can use this for multi and stacked type and it will show values of every bar part
    			showValuesColor: '#000' // color of font for values 	
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
	<h3><s:text name="point.list.all" /></h3>
	<br>
	<table id="point">
		<tr>
			<th><s:text name="point.id" /></th>
			<th><s:text name="point.task.tk_no" /></th>
			<th><s:text name="point.member.account" /></th>
			<th><s:text name="point.company.cmp_description" /></th>
			<th><s:text name="point.value" /></th>
			<th><s:text name="point.item.item_description" /></th>
			<th><s:text name="point.settlement_date" /></th>
			<th><s:text name="point.creation_date" /></th>
			<th><s:text name="point.remark" /></th>
			<th><s:text name="point.timestamp" /></th>
		</tr>

		<s:iterator value="pointList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="task.tk_no" /></td>
				<td><s:property value="member.account" /></td>
				<td><s:property value="company.cmp_description" /></td>
				<td><s:property value="value" /></td>
				<td><s:property value="item.item_description" /></td>
				<td><s:date name="settlement_date" format="yyyy-MM-dd" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>