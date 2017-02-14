<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'assKpiConfigForm'});
	v.check = function(){ 
    if(${parentWeight}!=-1&&eoms.$("weight").value.trim()>${parentWeight}){
      alert("当前权重大于上一级设置权重！"); 
      return false; 
    } 
    return true;
  }
  
});

</script>

<html:form action="/tranAssKpiConfigs.do?method=save" styleId="assKpiConfigForm" method="post"> 
<fmt:bundle basename="com/boco/eoms/partner/assess/config/applicationResources-assess">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="assKpiConfig.form.heading"/></div>
	</caption>
	<%
		String algorithmValue = String.valueOf(request.getAttribute("algorithmValue"));

	%>
<c:if test="${assKpiConfigForm.parentNodeId!=1}">
<%
	if("2".equals(algorithmValue)||"5".equals(algorithmValue)){
%>
	<tr>
		<td  class="label">
			值域范围
		</td>
		<td class="content">
			<html:text property="minValue" styleId="minValue"
						styleClass="text medium"  style="width:75px;" 
						alt="re:/^[0-9]+\.{0,1}[0-9]{0,3}$/,allowBlank:false,vtext:''" value="${assKpiConfigForm.minValue}" />
		~
			<html:text property="maxValue" styleId="maxValue" style="width:75px;" 
						styleClass="text medium" onblur="changeRate(document.getElementById('minValue').value,document.getElementById('maxValue').value)"
						alt="re:/^[0-9]+\.{0,1}[0-9]{0,3}$/,allowBlank:false,vtext:''" value="${assKpiConfigForm.maxValue}" />
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.valueConfig" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assess" dictId="config" defaultId="${assKpiConfigForm.valueConfig}" 
								 selectId="valueConfig" beanId="selectXML" alt="allowBlank:false,vtext:'请选择值域判断标识'" />		
		</td>
	</tr>
	<%
	}
	%>
</c:if>
<c:if test="${assKpiConfigForm.parentNodeId==1}">
	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.numConfig" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assess" dictId="config" defaultId="${assKpiConfigForm.numConfig}" 
								 selectId="numConfig" beanId="selectXML" alt="allowBlank:false,vtext:'请选择设备数量判断标识'" />
		</td>
	</tr>
	<tr>		
		<td  class="label">
			<fmt:message key="assKpiConfig.algorithm" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assess" dictId="algorithm" defaultId="${assKpiConfigForm.algorithm}" 
								 selectId="algorithm" beanId="selectXML" alt="allowBlank:false,vtext:'请选择得分算法'" />
		</td>
	</tr>
	<tr>		
		<td  class="label">
			<fmt:message key="assKpiConfig.numRelation" />
		</td>
		<td class="content">
			<input id="numRelationOne" styleClass="text medium" style="width:50px;" alt="vtype:'number'" value = "" onblur="changeRate(document.getElementById('numRelationOne').value,document.getElementById('numRelationTwo').value)"/>~<input id="numRelationTwo" styleClass="text medium" style="width:50px;" alt="vtype:'number'" value=""  onblur="changeRate(document.getElementById('numRelationOne').value,document.getElementById('numRelationTwo').value)"/>		
		</td>		
	</tr>	
	<tr>
		<td  class="label">
			是否采集
		</td>
		<td class="content">
			<c:if test="${assKpiConfigForm.isCollection==''||assKpiConfigForm.isCollection=='uncollection'||assKpiConfigForm.isCollection==null}">
				<INPUT type="radio" name="isCollection" value="collection" onclick="show1('collection');"/>采集
				<INPUT type="radio" name="isCollection" value="uncollection" checked="true"  onclick="show1('uncollection');" />不采集	
			</c:if>
			<c:if test="${assKpiConfigForm.isCollection=='collection'}">
				<INPUT type="radio" name="isCollection" value="collection" onclick="show1('collection');" checked="true"  />采集
				<INPUT type="radio" name="isCollection" value="uncollection" onclick="show1('uncollection');" />不采集	
			</c:if>			
		</td>
	</tr>	

	<tr id="collectionTypeDiv" style="display:none;">
		<td  class="label">
			采集类型
		</td>
		<td class="content">
			<select name="collectionType" id="collectionType"
				onchange="changeConfig(0);">
				<option value="">
					--请选择采集类型--
				</option>
				<logic:iterate id="autoTypelist" name="autoTypelist">
					<option value="${autoTypelist.nodeId}">
						${autoTypelist.nodeName}
					</option>
				</logic:iterate>
			</select>		
		</td>
	</tr>
	
	<tr id="collectionConfigDiv" style="display:none;">
		<td  class="label">
			采集配置
		</td>
		<td class="content">
			<select name="collectionConfig" id="collectionConfig" >
				<option value="">
					--请选择采集配置--
				</option>				
			</select>		
		</td>
	</tr>	
</c:if>	
	<%
		
	if(!"1".equals(algorithmValue)){
	%>
	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.weight" />
		</td>
		<td class="content">
			<html:text property="weight" styleId="weight"
						styleClass="text medium"
						alt="re:/^-?[0-9]+\.{0,1}[0-9]{0,2}$/,allowBlank:false,vtext:''" value="${assKpiConfigForm.weight}" />
		</td>
	</tr>
	<%} %>
	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.remark" />
		</td>
		<td class="content">
			<textarea name="remark" id="remark" maxLength="1000" rows="2" style="width:98%;" alt="allowBlank:true,vtext:'',maxLength:255" >${assKpiConfigForm.remark}</textarea>		
		</td>
	</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
		<c:if test="${assKpiConfigForm.parentNodeId==1}">
			<input type="button"  class="btn" onclick="submitSave();" value="<fmt:message key="button.save"/>" />
		</c:if>
		<c:if test="${assKpiConfigForm.parentNodeId!=1}">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</c:if>		
			<c:if test="${not empty assKpiConfigForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url='${app}/partner/assess/tranAssKpiConfigs.do?method=remove&nodeId=${assKpiConfigForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="numRelation" value="${assKpiConfigForm.numRelation}" />
<html:hidden property="id" value="${assKpiConfigForm.id}" />
<html:hidden property="nodeId" value="${assKpiConfigForm.nodeId}" />
<html:hidden property="parentNodeId" value="${assKpiConfigForm.parentNodeId}" />
<html:hidden property="leaf" value="${assKpiConfigForm.leaf}" />
<html:hidden property="kpiId" value="${assKpiConfigForm.kpiId}" />

<c:if test="${assKpiConfigForm.parentNodeId==1}">
	<html:hidden property="nodeType" value="group" />
</c:if>
<c:if test="${assKpiConfigForm.parentNodeId!=1}">
	<html:hidden property="nodeType" value="leaf" />
</c:if>
</html:form>
<script type="text/javascript">
var numRelationValue = '${assKpiConfigForm.numRelation}';
if(numRelationValue!=''){
	var score = numRelationValue.split(',');
	document.getElementById('numRelationOne').value = score[0];
	document.getElementById('numRelationTwo').value = score[1];
}
function submitSave(){
	var temp = document.getElementById('numRelationOne').value + ',' + document.getElementById('numRelationTwo').value;
	document.getElementById('numRelation').value = temp;
	var isCollection = 'uncollection';
	var obj = document.getElementsByName('isCollection');
    for(i = 0; i < obj.length; i++){ 
      if(obj[i].checked){ 
        isCollection = obj[i].value;
      }
    }
	if(isCollection=='collection'){
		if(document.getElementById('collectionType').value==""||document.getElementById('collectionConfig').value==""){
			alert("请选择采集信息");
			return false;
		}
	}
	$("assKpiConfigForm").submit(); 
}
function changeRate(x,y){
	if(parseFloat(y)< parseFloat(x)){
		alert('最小值不能大于最大值！');
	}
}
function show1(coll){
	if(coll=='collection'){
		document.getElementById('collectionTypeDiv').style.display="block";
		document.getElementById('collectionConfigDiv').style.display="block";
		getRadioBoxValue('isCollection','collection');
	}else{
		document.getElementById('collectionTypeDiv').style.display="none";
		document.getElementById('collectionConfigDiv').style.display="none";
		document.getElementById('collectionType').value="";
		document.getElementById('collectionConfig').value="";
		getRadioBoxValue('isCollection','uncollection');
	}
}
function delAllOption(elementid){
    var ddlObj = document.getElementById(elementid);//获取对象
     for(var i=ddlObj.length-1;i>=0;i--){
         ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
    }
}
//联动采集配置
function changeConfig(con)
		{    
		    delAllOption("collectionConfig");//联动类型选择后，刷新联动配置
			var type = document.getElementById("collectionType").value;
			var url="<%=request.getContextPath()%>/partner/assess/assAutoCollections.do?method=changeConfig&type="+type;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									var collectionConfig = "collectionConfig";
						
									var arrOptions = json[0].configBuffer.split(",");
									var obj=$(collectionConfig);
											
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									if(con==1){
											var collectionConfig = '${assKpiConfigForm.collectionConfig}';
											document.getElementById("collectionConfig").value = collectionConfig;
									}										
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
function getRadioBoxValue(radioName, radiovalue)  
  {  
    var obj = document.getElementsByName(radioName);
    for(i = 0; i < obj.length; i++){ 
      if(obj[i].value == radiovalue){ 
        obj[i].checked = true;
      }else{
    	obj[i].checked = false;
      }
    }
    return true;
  }
		
window.onload = function(){
    var isCollection = '${assKpiConfigForm.isCollection}';
	if(isCollection!=''&&isCollection=='collection'){
		var collectionType = '${assKpiConfigForm.collectionType}';
		var collectionConfig = '${assKpiConfigForm.collectionConfig}';
 		document.getElementById("collectionType").value = collectionType;
 		show1('collection');
		changeConfig(1);
	}
}		
</script>
<%@ include file="/common/footer_eoms.jsp"%>