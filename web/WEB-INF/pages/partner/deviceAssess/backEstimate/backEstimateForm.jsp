<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'backEstimateForm'});
});
/** 
*实现两个数据的简单计算；计算结果进行四舍五入，只保留小数据点后两位； 
*num1，第一个数据。如果是减法与除法；则作为被减数、被除数； 
*num2，第二个数据。如果是减法与除法：则作为减数、除数； 
*str，运算符号，目前只接受:+、-、*、/:分别对应：加法、减法、乘法、除法； 
*返回计算结果；计算结果进行四舍五入，只保留小数据点后两位； 
*/ 
function account(str,num1,num2){ 
	if(num1!=""&&num2!=""&&str!=""){ 
		num1 = parseFloat(num1); 
		num2= parseFloat(num2); 
		var rs=0.0; 
		  if(str=="+"){ 
		   rs=num1+num2; 
		   return (Math.round(rs*100)/100);
		   //保保留小数点后两位数； 
		   //如果要保留三位则改为:Math.round(rs*1000)/1000; 
		   //如果要保留四位则改为:Math.round(rs*10000)/10000;.....以次类推
		  } 
		  if(str=="-"){ 
		   rs=num1-num2; 
		   return (Math.round(rs*100)/100); 
		  } 
		  if(str=="*"){ 
		   rs=num1*num2; 
		   return (Math.round(rs*100)/100); 
		  } 
		  if(str=="/"){ 
		   if(num2!="0"){ 
		    rs=num1/num2; 
		    return (Math.round(rs*100)/100); 
		   }else{ 
		    alert("Error"); 
		   } 
		  } 
	} 
}
//获得总分
function sum(){

	var sumValue = 0;
	
	if(document.getElementById("facilityFauPoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("facilityFauPoi").value);
	}
	if(document.getElementById("badPlankPoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("badPlankPoi").value);	
	}
	if(document.getElementById("softwareSucPoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("softwareSucPoi").value);
	}
	if(document.getElementById("faultAvgPoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("faultAvgPoi").value);
	}
	if(document.getElementById("operationRenewPoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("operationRenewPoi").value);
	}
	if(document.getElementById("plankRepairPoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("plankRepairPoi").value);
	}
	if(document.getElementById("referServePoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("referServePoi").value);
	}
	if(document.getElementById("projectServePoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("projectServePoi").value);
	}
	if(document.getElementById("trainServePoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("trainServePoi").value);
	}
	if(document.getElementById("specialServePoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("specialServePoi").value);
	}
	if(document.getElementById("artServePoi").value!=''){
		sumValue = sumValue + parseFloat(document.getElementById("artServePoi").value);
	}			
	if(document.getElementById("bigFaultPoi").value!=''){
		sumValue = sumValue - parseFloat(document.getElementById("bigFaultPoi").value);
	}
	if(document.getElementById("facilityQuePoi").value!=''){
		sumValue = sumValue - parseFloat(document.getElementById("facilityQuePoi").value);
	}
	
	document.getElementById("totalShow").innerHTML = sumValue;
	document.getElementById("total").value = sumValue;
}
</script>

<html:form action="/backEstimates.do?method=save" styleId="backEstimateForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">后评估指标体系</div>
	</caption>
	<tr>
		<td  class="label"  >指标分类</td>
		<td  class="label"  >指标名称</td>
		<td  class="label"  >指标值(季度)</td>
		<td  class="label"  >参考分值</td>
		<td  class="label"  >分值</td>
		<td  class="label"  >备注</td>
	</tr>
	<tr>
		<td  class="label"   rowspan="4">设备质量（40%）</td>
		<td  class="label"  >设备故障率</td>
		<td>${backEstimateForm.facilityFauTar}%</td>
		<td>20</td>
		<td>
			<html:text property="facilityFauPoi" styleId="facilityFauPoi"
						styleClass="text medium"  onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.facilityFauPoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >坏板率</td>
		<td>${backEstimateForm.badPlankTar}%</td>
		<td>20</td>
		<td>
			<html:text property="badPlankPoi" styleId="badPlankPoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.badPlankPoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >重大故障次数</td>
		<td>${backEstimateForm.bigFaultTar}</td>
		<td>-</td>
		<td>
			<html:text property="bigFaultPoi" styleId="bigFaultPoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.bigFaultPoi}" />
		</td>
		<td>扣分</td>	
	</tr>	
	<tr>
		<td  class="label"  >设备问题数</td>
		<td>${backEstimateForm.facilityQueTar}</td>
		<td>-</td>
		<td>
			<html:text property="facilityQuePoi" styleId="facilityQuePoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.facilityQuePoi}" />
		</td>
		<td>扣分</td>	
	</tr>
	<tr>
		<td  class="label"   rowspan="5">服务质量（48%）</td>
		<td  class="label"  >软件升级成功率</td>
		<td>${backEstimateForm.softwareSucTar}%</td>
		<td>8</td>
		<td>
			<html:text property="softwareSucPoi" styleId="softwareSucPoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.softwareSucPoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >故障平均时长</td>
		<td>${backEstimateForm.faultAvgTar}小时</td>
		<td>10</td>
		<td>
			<html:text property="faultAvgPoi" styleId="faultAvgPoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.faultAvgPoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >业务恢复平均时长</td>
		<td>${backEstimateForm.operationRenewTar}小时</td>
		<td>15</td>
		<td>
			<html:text property="operationRenewPoi" styleId="operationRenewPoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.operationRenewPoi}" />
		</td>
		<td></td>	
	</tr>	
	<tr>
		<td  class="label"  >板件返修平均时长</td>
		<td>${backEstimateForm.plankRepairTar}天</td>
		<td>15</td>
		<td>
			<html:text property="plankRepairPoi" styleId="plankRepairPoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.plankRepairPoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >咨询服务反馈平均时长</td>
		<td>${backEstimateForm.referServeTar}天</td>
		<td>5</td>
		<td>
			<html:text property="referServePoi" styleId="referServePoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.referServePoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  rowspan="4">服务满意度（12%）</td>
		<td  class="label"  >技术服务满意度</td>
		<td>${backEstimateForm.artServeTar}分</td>
		<td>3</td>
		<td>
			<html:text property="artServePoi" styleId="artServePoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.artServePoi}" />
		</td>
		<td></td>	
	</tr>	
	<tr>
		<td  class="label"  >工程服务满意度</td>
		<td>${backEstimateForm.projectServeTar}分</td>
		<td>3</td>
		<td>
			<html:text property="projectServePoi" styleId="projectServePoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.projectServePoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >培训服务满意度</td>
		<td>${backEstimateForm.trainServeTar}分</td>
		<td>3</td>
		<td>
			<html:text property="trainServePoi" styleId="trainServePoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.trainServePoi}" />
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >特通服务满意度</td>
		<td>${backEstimateForm.specialServeTar}分</td>
		<td>3</td>
		<td>
			<html:text property="specialServePoi" styleId="specialServePoi"
						styleClass="text medium" onblur="sum();"
						alt="allowBlank:false,vtext:''" value="${backEstimateForm.specialServePoi}" />
		</td>
		<td></td>	
	</tr>	
	<tr>
		<td colspan="2" class="label">总分</td>
		<td>-</td>
		<td>-</td>
		<td id = "totalShow">0</td>
		<td></td>
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>
<html:hidden property="year" value="${backEstimateForm.year}" />
<html:hidden property="quarter" value="${backEstimateForm.quarter}" />
<html:hidden property="factory" value="${backEstimateForm.factory}" />
<html:hidden property="speciality" value="${backEstimateForm.speciality}" />
<html:hidden property="equipmentType" value="${backEstimateForm.equipmentType}" />
<html:hidden property="specialServeTar" value="${backEstimateForm.specialServeTar}" />
<html:hidden property="trainServeTar" value="${backEstimateForm.trainServeTar}" />
<html:hidden property="projectServeTar" value="${backEstimateForm.projectServeTar}" />
<html:hidden property="artServeTar" value="${backEstimateForm.artServeTar}" />
<html:hidden property="referServeTar" value="${backEstimateForm.referServeTar}" />
<html:hidden property="plankRepairTar" value="${backEstimateForm.plankRepairTar}" />
<html:hidden property="operationRenewTar" value="${backEstimateForm.operationRenewTar}" />
<html:hidden property="faultAvgTar" value="${backEstimateForm.faultAvgTar}" />
<html:hidden property="softwareSucTar" value="${backEstimateForm.softwareSucTar}" />
<html:hidden property="facilityQueTar" value="${backEstimateForm.facilityQueTar}" />
<html:hidden property="bigFaultTar" value="${backEstimateForm.bigFaultTar}" />
<html:hidden property="badPlankTar" value="${backEstimateForm.badPlankTar}" />
<html:hidden property="facilityFauTar" value="${backEstimateForm.facilityFauTar}" />
<html:hidden property="total" value="${backEstimateForm.total}" />
<html:hidden property="id" value="${backEstimateForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>