<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

    var jq=jQuery.noConflict();
	Ext.onReady(function(){
	
            v = new eoms.form.Validation({form:'faultInfoForm'});
            v.custom = function(){ 
           	generateTakeTime();
           		return true;
          		};
   });
 

   
	function returnBack(){
		window.history.back();
	}
	
 </script>
 	
	<content tag="heading">
	<c:out value="巡检段信息修改" />
	</content><br/><br/>
<form action="checkSegmentAction.do?method=edit" method="post"
	id="checkSegmentForm" name="checkSegmentForm">
	
	<!-- hidden area start -->
		<input type="hidden" name="id" value="${checkSegment.id}" />
		
	<!-- hidden area end -->
	
		<table id="sheet" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 巡检段名称* 
			</td>
			<td class="content" colspan="3">
				${checkSegment.segmentName}
			</td>
			
		</tr>
		
		<tr>
			<td class="label">
			传输线路类型* 
			</td>
			<td class="content">
				<eoms:comboBox name="lineType" defaultValue="${checkSegment.lineType}"
					id="sheetType" initDicId="1010104" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				传输线路级别*
			</td>
			<td class="content">
				<eoms:comboBox name="lineLevel" defaultValue="${checkSegment.lineLevel}"
					id="sheetType" initDicId="1010102" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				起始点标示*
			</td>
			<td class="content">
			  <input class="text" type="text" name="startFlag" value="${checkSegment.startFlag}"
					id="topic" alt="allowBlank:false" />
			</td> 
			<td class="label">
				结束点标示*
			</td>
			<td class="content">
			  <input class="text" type="text" name="endFlag" value="${checkSegment.endFlag}"
					id="topic" alt="allowBlank:false" />
			</td> 
		</tr>
		
		
		<tr>
			<td class="label">
				巡检段类型*
			</td>
			<td colspan="3" class="content">
				<eoms:comboBox name="segmentType" defaultValue="${checkSegment.segmentType}"
					id="sheetType" initDicId="1010102" alt="allowBlank:false" styleClass="select" />
			</td> 
		</tr>
		</table>

		<br/>
		
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存修改" ></html:submit>
		<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>

<%@ include file="/common/footer_eoms.jsp"%>