<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<script type="text/javascript">

	Ext.onReady(function(){
	            var v = new eoms.form.Validation({form:"quotaForm"});
	            	            	            v.custom = function(){
try{
	var proportion = document.getElementById("proportion");
	
	
	if(notNumber(proportion.value.trim())){
	   
			alert("权重必须是数字且小于等于100");
			proportion.focus();
			return false;
		}
	if(0>=parseFloat(proportion.value.trim())||parseFloat(proportion.value.trim())>100)	
	{alert("权重必须是数字且小于等于100");
			proportion.focus();
			return false;}
	
	}catch(e){
	
		return false;
	}
	
	return true;
}
	           
     function notNumber(value){

  	
  	
  	   	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var value=value; 
   		if(value.match(price) && ""!=value){
   			return false;
      	}else{
           	return true;
      	}
  	
  	
  	
  	
  	
  	
  }
	            
   });
   
  
   function proportionValidate(obj){
    	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var proportion=$('proportion').value; //获得文本框输入的增补警示牌/宣传牌中继段里程（公里）
        var proportionDiv=$('proportionDiv');	//获得显示信息的div
   		if(proportion.match(price) && ""!=proportion){
   			proportionDiv.innerHTML="格式正确";
   			proportionDiv.style.backgroundColor="#FFFF00";
   			// return true;
      	}else{
           	proportionDiv.innerHTML="输入不合法,请输入正确的权重 例：100.00";
           	proportionDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
      	//计算分数
      	if(proportion!=""){      	
      		$('fraction').value=(obj*proportion*0.01).toFixed(2);
      	}
  }
   function fractionValidate(obj){
    	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var fraction=$('fraction').value; //获得文本框输入的增补警示牌/宣传牌中继段里程（公里）
        var fractionDiv=$('fractionDiv');	//获得显示信息的div
   		if(fraction.match(price) && ""!=fraction){
   			fractionDiv.innerHTML="格式正确";
   			fractionDiv.style.backgroundColor="#FFFF00";
   			// return true;
      	}else{
           	fractionDiv.innerHTML="输入不合法,请输入正确的分数 例：100.00";
           	fractionDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
      	//计算权重
      	if(fraction!=""){
      		$('proportion').value=(fraction/obj*100).toFixed(2);
      	}
  }
 </script>







<form action="${app}/partner/evaluation/quota.do?method=add"
	method="post" id="quotaForm" name="quotaForm">
	<input type="hidden" id="id" name="id" value="${quota.id}" />
	<input type="hidden" id="ownPrgmId" name="ownPrgmId"
		value="${ownPrgmId}" />
	<div>
		<font color="red">${errormsg}</font>
	</div>
	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					编辑指标
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>考核指标名
			</td>
			<td>
				<input type="text" class="text" name="idctNm" id="idctNm"  style="width:80%;"
					alt="allowBlank:false,vtext:'考核指标名不能为空 不能超出1000个汉字！',maxLength:2000"
					value="${quota.idctNm }" />
			</td>

			<td class="label">
				<font color='red'> * </font>权重
			</td>
			<td>


				<input class="text" type="text" name="proportion"
					value="${quota.proportion}" id="proportion" alt="allowBlank:false"
					onblur="javascript:proportionValidate(${fraction});" />
				<div id="proportionDiv" class="ui-state-highlight ui-corner-all"
					style="width: 150px">
					<span class="ui-icon ui-icon-circle-triangle-e"
						style="float: left; margin-right: .6em;"></span>
					<div>
						例：100.00
					</div>
				</div>

			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>分数
			</td>
			<td>


				<input class="text" type="text" name="fraction"
					value="${quota.fraction}" id="fraction" alt="allowBlank:false"
					onblur="javascript:fractionValidate(${fraction});" />
				<div id="fractionDiv" class="ui-state-highlight ui-corner-all"
					style="width: 150px">
					<span class="ui-icon ui-icon-circle-triangle-e"
						style="float: left; margin-right: .6em;"></span>
					<div>
						例：100.00
					</div>
				</div>

			</td>
			<td class="label" style="height:100px">
				<font color='red'> * </font>评分说明
			</td>
			<td class="content" style="height:100px">
				<textarea name="scoreExpl" id="scoreExpl" class="text medium"
					alt="allowBlank:false,vtext:'评分说明 不能超出1000个汉字！',maxLength:2000" style="width:95%;height:100px">${quota.scoreExpl}</textarea>
			</td>
			

		</tr>
		<tr>
			<td class="label" style="higth:300px;">
				<font color='red'> * </font>指标实际定义
			</td>
			<td class="content" style="heigth:100px">
				<textarea name="idCtdFnt" id="idCtdFnt" class="text medium"
					alt="allowBlank:false,vtext:'指标实际定义 不能超出1000个汉字！',maxLength:2000" style="width:95%;height:100px">${quota.idCtdFnt}</textarea>
			</td>
			<td class="label">
				<font color='red'> * </font>评分类型
			</td>
			<td class="content">
				<select id="scoreTyp" name="scoreTyp" class="text">

					<option value="0" <%="0".equals(request.getAttribute("scoreTyp"))?"selected='selected'":""%>>
						加分
					</option>
					<option value="1" <%="1".equals(request.getAttribute("scoreTyp"))?"selected='selected'":""%> >
						减分
					</option>
					<option value="2" <%="2".equals(request.getAttribute("scoreTyp"))?"selected='selected'":""%>>
						指标计算
					</option>
				</select>
			</td>


			

		</tr>
		<tr>
			<td class="label">
				备注
			</td>

			<td class="content">
				<textarea name="note" id="note" class="text medium"
					alt="allowBlank:true,vtext:'备注 不能超出1000个汉字！',maxLength:2000" style="width:95%;height:100px">${quota.note}</textarea>
			</td>
				<td class="label">
				关联实现类
			</td>
					<td class="content">
				<select id="quotatoimplid" name="quotatoimplid" class="text" style="width:80%;">

					<option value="0" >
						请选择实现类
					</option>
					<c:forEach items='${quotaToImplList}' var="entity">
					<option value="${entity.id}" <c:if test="${entity.id eq quota.quotatoimplid}">selected='selected'</c:if>>
						${entity.name}
					</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<c:if test="${scoreFlag=='1'}">
		<tr>
		<td colspan="4">
		注意：如果修改评分类型，则指标下面原来有的规则和公式将会被删除
		</td>
		
		
		</tr>
		
		
		</c:if>
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