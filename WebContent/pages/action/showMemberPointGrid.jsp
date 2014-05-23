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
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="plugins/jqplot.barRenderer.min.js"></script>
	<script type="text/javascript" src="plugins/jqplot.pieRenderer.min.js"></script>
	<script type="text/javascript" src="plugins/jqplot.categoryAxisRenderer.min.js"></script>
	<script type="text/javascript" src="plugins/jqplot.pointLabels.min.js"></script>
	<link href="<s:url value ="/css/jquery.jqplot.min.css"/>" rel="stylesheet" type="text/css" />
	
    <script type="text/javascript">    
	    $(document).ready(function(){        
	        var s1 = [<s:iterator value="barList"> 
			   			<s:property value="value_item_001" />, 
				 	  </s:iterator> ];
	        var s2 = [<s:iterator value="barList"> 
						<s:property value="value_item_002" />, 
			 	  	  </s:iterator> ];
	        var s3 = [<s:iterator value="barList"> 
						<s:property value="value_item_003" />, 
	 	  			  </s:iterator> ];
	        var s4 = [<s:iterator value="barList"> 
						<s:property value="value_item_004" />, 
	 	  			  </s:iterator> ];
	        var s5 = [<s:iterator value="barList"> 
						<s:property value="value_item_005" />, 
	 	  			  </s:iterator> ];          
	        var s6 = [<s:iterator value="barList"> 
						<s:property value="value_item_006" />, 
					  </s:iterator> ];          
	        var s7 = [<s:iterator value="barList"> 
						<s:property value="value_item_007" />, 
			  		  </s:iterator> ];          
	        var s8 = [<s:iterator value="barList"> 
						<s:property value="value_item_008" />, 
		  			  </s:iterator> ];          
	        var s101 = [<s:iterator value="barList"> 
						<s:property value="value_item_101" />, 
		    		  </s:iterator> ];          
	        var ticks = [<s:iterator value="barList"> 
	        			   <s:property value="label" />, 
	        			 </s:iterator> ];    
	                     
	        plot = $.jqplot('stacked-graph', [s1, s2, s3, s4, s5, s6, s7, s8, s101], {
	            seriesColors: [ "#FF6666", "#FFFF00", "#3399CC", "#CC0066",
	            				"#663399", "#0066CC", "#99CC00", "#0099CC", "#FF0033",
	            				"#FF5800", "#0085CC", "#C747A3", "#CDDF54", "#FBD178",
	            				"#26B4E3", "#BD70C7" ],
	            negativeSeriesColors: [ "#FF6666", "#FFFF00", "#3399CC", "#CC0066",
	    	            				"#663399", "#0066CC", "#99CC00", "#0099CC", "#FF0033",
	    	            				"#FF5800", "#0085CC", "#C747A3", "#CDDF54", "#FBD178",
	    	            				"#26B4E3", "#BD70C7" ],				            
	        	animate : true,
	        	stackSeries: false,
	            seriesDefaults: {
	            	label: [''],      // label to use in the legend for this line.                
	                pointLabels: { show: true },
	                renderer:$.jqplot.BarRenderer,                
	                rendererOptions:{
	                	fillToZero: true,
	                	barWidth:30
	                }          
		        },
	            series:[
	                    {label: ['<s:text name='item.001' />']},
	                    {label: ['<s:text name='item.002' />']},
	                    {label: ['<s:text name='item.003' />']},
	                    {label: ['<s:text name='item.004' />']},
	                    {label: ['<s:text name='item.005' />']},
	                    {label: ['<s:text name='item.006' />']},
	                    {label: ['<s:text name='item.007' />']},
	                    {label: ['<s:text name='item.008' />']},
	                    {label: ['<s:text name='item.101' />']},
	            ],            
		        axesDefaults: {
		            tickRenderer: $.jqplot.CanvasAxisTickRenderer ,
		            tickOptions: {
		              angle: -30,
		              fontSize: '10pt'
		            }
		        },            
		        axes: {                
		            xaxis: {
			        	show: true,                    
		                renderer: $.jqplot.CategoryAxisRenderer,                    
		                ticks: ticks                
		            },
		            yaxis: {
		                show: true,
		                pad: 1,
		                tickOptions: {formatString: '%#d'}
		            }            
		        },
		        legend: {
		            show: true,
		            placement: 'outsideGrid',
		            location: 'ne',     // compass direction, nw, n, ne, e, se, s, sw, w.
		        },        
	        });
	                     
	        $('#stacked-graph').bind('jqplotDataHighlight',             
	            function (ev, seriesIndex, pointIndex, data) {                
	            	$('#info').html("<s:text name='point.value' />" + ' : ' + data[1].toFixed(1).replace(/\d(?=(\d{3})+\.)/g, '$&,'));            
	            }        
	        );
	                             
	        $('#stacked-graph').bind('jqplotDataUnhighlight',             
	            function (ev) {                
	            	$('#info').html("<s:text name='point.value' />" + ' : 0 ');            
	            }        
	        );    
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
	<div id='info' align='right'></div>
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