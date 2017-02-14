<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>



<script type="text/javascript">

	Ext.onReady(function(){
	 var v= new eoms.form.Validation({form:'meteringForm'});
	
	 v.custom = function(){ 
	            	if(sureIndexWeight()) {
	            		return true;
	            	} else {
	            		alert("指标系数输入错误，请检查!");
	            		return false;
	            	}
	            };
	
   });

function sureIndexWeight(obj){
    	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var protocolPrice=$('meteringFactor').value; //获得文本框输入的金额
        var protocolPriceDiv=$('indexWeightDiv');	//获得显示信息的div
   		if(protocolPrice.match(price) && ""!=protocolPrice){
   			protocolPriceDiv.innerHTML="格式正确";
   			return true;
      	}else{
           	protocolPriceDiv.innerHTML="输入不合法,请输入正确权重值 例：23,23.00";
           	return false;
      	}
  }
   function returnBack(){
		window.history.back();
	}
  
</script>
<form action="${app}/partner/feeManage/feeMetering.do?method=saveFactor"
	method="post" id="meteringForm" name="meteringForm"/>


	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					编辑计次指标
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>计次类型名称：
			</td>
			<td>
					${meteringName}
			<input type="hidden" id="meteringName" name="meteringName"  value="${meteringName}"/>
			</td>

			<td class="label">
				<font color='red'> * </font>专业：
			</td>
			<td class="content">
			<eoms:id2nameDB id="${major}"  beanId="ItawSystemDictTypeDao" />	
	
					
			<input type="hidden" id="major" name="major"  value="${major}"/>			
					</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>指标名称：
			</td>
			<td>
				${indexName}
			<input type="hidden" id="indexName" name="indexName"  value="${indexName}"/>			
			</td>
	        <td class="label">
				<font color='red'> * </font>指标值：
			</td>
			<td>
			<eoms:id2nameDB id="${feePoolFactor.indexSelected}"  beanId="ItawSystemDictTypeDao" />	
			</td>
			
			</tr>
			<tr>
			<td class="label">
				<font color='red'> * </font>计次系数：
			</td>
			<td>
            <input class="text" type="text" name="meteringFactor"   value="${feePoolFactor.meteringFactor}"
				 id="meteringFactor" alt="allowBlank:false" onblur="javascript:sureIndexWeight(this);"></input>
				 <div id="indexWeightDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>例：23,23.00 </div>
				</div>


			</td>
		</tr>
	<input type="hidden" id="id" name="id"  value="${feePoolFactor.id}"/>
	<input type="hidden" id="meteringId" name="meteringId"  value="${meteringId}"/>
	<input type="hidden" id="meteringId" name="indexName"  value="${indexName}"/>
	</table>
	<tr>
		<td class="label">
		</td>
		<td>
			<input type="submit" value="提交"  />
		</td>
	</tr>

</form>
<%@ include file="/common/footer_eoms.jsp"%>