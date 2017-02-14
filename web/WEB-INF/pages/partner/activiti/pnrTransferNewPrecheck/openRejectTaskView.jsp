<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/partner/js/jqueryMultiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/partner/js/jqueryUi/jquery-ui.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/partner/js/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/partner/js/jqueryUi/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/partner/js/jqueryMultiselect/jquery.multiselect.js"></script>

<script type="text/javascript">
	$(function() {
		$("select").multiselect({   
			noneSelectedText: "==请选择==",
			checkAllText: "全选",
			ncheckAllText: '全不选',
			selectedList:100
			}, function(){   //回调函数  
		});
	});
</script>

<html:form action="/pnrTransferNewPrecheck.do?method=textRejectTask" styleId="theform" >
	<select title="Basic example" multiple="multiple" name="example-basic"
			size="5">
			<option value="option1">Option 1</option>
			<option value="option2">Option 2</option>
			<option value="option3">Option 3</option>
			<option value="option4">Option 4</option>
			<option value="option5">Option 5</option>
			<option value="option6">Option 6</option>
			<option value="option7">Option 7</option>
			<option value="option8">Option 8</option>
			<option value="option9">Option 9</option>
			<option value="option10">Option 10</option>
			<option value="option11">Option 11</option>
			<option value="option12">Option 12</option>
		</select>
	
		
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="return changeType1();" styleId="method.save">
				派发
			</html:submit>
		</div>
	</html:form>
