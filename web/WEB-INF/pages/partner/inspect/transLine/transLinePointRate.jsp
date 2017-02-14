<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	jq(function(){
		jq("#sure").click(function(){
			var rate = jq("#rate").val();
			var cycle ="";
			var inspectMethod ="";
			/*var cycle = jq("#cycle").val();
			var inspectMethod = jq("#inspectMethod").val();
			if(rate == ""){
				Ext.Msg.alert('提示','请选择到点率'); 
				return;
			}
			if(cycle == ""){
				Ext.Msg.alert('提示','请选择周期'); 
				return;
			}
			if(inspectMethod == ""){
				Ext.Msg.alert('提示','请选择巡检方式'); 
				return;
			}*/
			if(rate == ""){
				Ext.Msg.alert('提示','请选择到点率'); 
				return;
			}
			var id = '${id}';
			//alert(id);
			alert("rate    "+rate);
			if(2>1){return}
			Ext.Ajax.request({
		    url: '${app }/partner/res/pnrTransLineAction.do?method=updateTransPointRate',
		    params:{id:id,rate:rate,cycle:cycle,inspectMethod:inspectMethod},
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
			    	 window.returnValue=true;
					 window.close();
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	window.returnValue=false;
					window.close();
      			 }
      			
		    },
	     	failure: function(response) {
     			window.returnValue=false;
				window.close();
            }
			});	
			
		})
		
		jq("#sureAll").click(function(){
			/*
			var rate = jq("#rate").val();
			var cycle = jq("#cycle").val();
			var inspectMethod = jq("#inspectMethod").val();
			if(rate == ""){
				Ext.Msg.alert('提示','请选择到点率'); 
				return;
			}
			if(cycle == ""){
				Ext.Msg.alert('提示','请选择周期'); 
				return;
			}
			if(inspectMethod == ""){
				Ext.Msg.alert('提示','请选择巡检方式'); 
				return;
			}*/
			var cycle ="";
			var inspectMethod ="";
			var rate = jq("#rate").val();
			if(rate == ""){
				Ext.Msg.alert('提示','请选择到点率'); 
				return;
			}
			var arr = new Array();
			arr[0] = rate;
			arr[1] = cycle;
			arr[2] = inspectMethod;
			window.returnValue=arr;
			window.close();
		})
	})
</script>

	<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				到点率设置<input name="all" value="${all}" type="hidden">
			</div>
		</caption>
			<%-- 
			<tr>
				<td class="label">周期</td>
				<td class="content">
					<select name="inspectCycle" class="select" id="cycle">
					<option value="">请选择</option>
					<c:forEach items="${list1}" var="cycle" > 
						<option value="${cycle.id},${cycle.reportInterval}">${cycle.reportInterval}</option>
					</c:forEach> 
				<select>
				</td>
			</tr>
			--%>
			<tr>
				<td class="label">到点率</td>
				<td class="content">
					<select name="inspectCycle" class="select" id="rate">
					<option value="">请选择</option>
					<c:forEach items="${list2}" var="reat" > 
						<option value="${reat.id},${reat.arrivedRate}">${reat.country}${reat.arrivedRate}</option>
					</c:forEach> 
				<select>
				</td>
			</tr>
			<%-- 
			<tr>
				<td class="label">巡检方式</td>
				<td class="content">
					<eoms:comboBox  name="inspectMethod" id="inspectMethod"  defaultValue="" 
	        			initDicId="11232"  styleClass="select" />
				</td>
			</tr>
			--%>
			<tr>
				<td align="center" colspan="2">
					<c:if test="${all eq true}"><input type="button" class="btn" value="确定" id="sureAll"></c:if>
					<c:if test="${all eq false}"><input type="button" class="btn" value="确定" id="sure"></c:if>
				</td>
			</tr>
	</table>


<%@ include file="/common/footer_eoms.jsp"%>
