<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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
<title><s:text name="trade.action.query" /></title>
<script type="text/javascript">

	function initialize(){
		$("#singleQuery").show();
		$("#yearQuery").hide();
	}

	function Operate(value, op1, op2) { 
		if (value == op1) {
			$("#singleQuery").show();
			$("#yearQuery").hide();
		} else {
			$("#singleQuery").hide();
			$("#yearQuery").show();
		}
	}
	
</script>
</head>
<body onload="initialize()">
	<h3><s:text name="trade.action.query" /></h3>
	<s:actionerror />
	<s:actionmessage />
	
	<s:form>
		<s:radio key="query" list="queryList" value="%{getText('query.trade.no')}" onclick="Operate(this.value, \"%{getText('query.trade.no')}\", \"%{getText('query.trade.year')}\")" />
	</s:form>

	<s:form id="singleQuery" action="queryTrade" method="post" namespace="/">  
	    <s:hidden key="query" value="%{getText('query.trade.no')}" />
		<sj:autocompleter key="trade.tx_no" list="tradeList" listKey="tx_no" listValue="tx_no" />
		<s:submit value="%{getText('submit.query')}" align="center" />
	</s:form>

	<s:form id="yearQuery" action="queryTrade" method="post" namespace="/">
		<s:hidden key="query" value="%{getText('query.trade.year')}" />
		<s:select key="queryYear" list="yearList" />
		<s:submit value="%{getText('submit.query')}" align="center" />
	</s:form>

</body>
</html>


