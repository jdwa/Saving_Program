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
<sj:head jqueryui="true" jquerytheme="cupertino" />
<title>jQuery toggle to display and hide content</title>

<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script>
		$(document).ready(function() {
		  $('.nav-toggle').click(function(){
			//get collapse content selector
			var collapse_content_selector = $(this).attr('name');

			//make the collapse content to be shown or hide
			var toggle_switch = $(this);
			$(collapse_content_selector).toggle(function(){
			  if($(this).css('display')=='none'){
                                //change the button label to be 'On'
				toggle_switch.html('<s:text name="labor.on" />');
			  }else{
                                //change the button label to be 'Off'
				toggle_switch.html('<s:text name="labor.off" />');
			  }
			});
		  });
		});
	</script>
<style>
h3 {
    color: blue;
    font-family: verdana;
    font-size: 150%;
    text-align: center;
}
h4 {
    color: black;
    font-family: verdana;
    font-size: 110%;
    text-align: left;
}
h5 {
    color: blue;
    font-family: verdana;
    font-size: 100%;
    text-align: left;
}
</style>
</head>
<body>
	<div>
		<button name="#collapse1" class="nav-toggle"><s:text name="labor.on" /></button>
	</div>
	<div id="collapse1" style="display: none">
			<h3><s:text name="labor.title" /></h3>
			<h4><s:text name="labor.r1.1" /></h4>
			<s:text name="labor.r1.2" />
			<h5><s:text name="labor.r1.3" /></h5>
			<h4><s:text name="labor.r2.1" /></h4>
			<h5><s:text name="labor.r2.2" /></h5>
			<s:text name="labor.r2.3" /><br>
			<s:text name="labor.r2.4" /><br>
			<s:text name="labor.r2.5" />
			<h5><s:text name="labor.r2.6" /></h5>
			<s:text name="labor.r2.7" /><br>
			<s:text name="labor.r2.8" /><br>
			<s:text name="labor.r2.9" />
			<h4><s:text name="labor.r3.1" /></h4>
			<s:text name="labor.r3.2" />
			<h4><s:text name="labor.r4.1" /></h4>
			<h5><s:text name="labor.r4.2" /></h5>
			<s:text name="labor.r4.3" /><br>
			<s:text name="labor.r4.4" /><br>
			<s:text name="labor.r4.5" /><br>
			<s:text name="labor.r4.6" />
			<h5><s:text name="labor.r4.7" /></h5>
			<s:text name="labor.r4.8" />
	</div>
</body>
</html>