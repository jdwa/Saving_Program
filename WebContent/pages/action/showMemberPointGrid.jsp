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
	              	'<s:property value="label" />']
				  </s:iterator>
				  );
			
    	  $("#stacked-graph").jqBarGraph({
    			data: stackedByMonth, // array of data for your graph
    			title: '', // title of your graph, accept HTML
    			barSpace: 200, // this is default space between bars in pixels
    			width: 800, // default width of your graph
    			height: 250, //default height of your graph
    			color: '#000000', // if you don't send colors for your data this will be default bars color
    			colors: ['#FF6666', '#FFFF00', '#3399CC', '#CC0066', '#663399', '#0066CC', '#99CC00', '#0099CC'], // array of colors that will be used for your bars and legends
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
    			showValues: true, // you can use this for multi and stacked type and it will show values of every bar part
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