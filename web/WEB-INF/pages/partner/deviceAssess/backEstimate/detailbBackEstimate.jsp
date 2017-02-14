<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'backEstimateForm'});
});
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
			${backEstimateForm.facilityFauPoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >坏板率</td>
		<td>${backEstimateForm.badPlankTar}%</td>
		<td>20</td>
		<td>
			${backEstimateForm.badPlankPoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >重大故障次数</td>
		<td>${backEstimateForm.bigFaultTar}</td>
		<td>-</td>
		<td>
			${backEstimateForm.bigFaultPoi}
		</td>
		<td>扣分</td>	
	</tr>	
	<tr>
		<td  class="label"  >设备问题数</td>
		<td>${backEstimateForm.facilityQueTar}</td>
		<td>-</td>
		<td>
			${backEstimateForm.facilityQuePoi}
		</td>
		<td>扣分</td>	
	</tr>
	<tr>
		<td  class="label"   rowspan="5">服务质量（48%）</td>
		<td  class="label"  >软件升级成功率</td>
		<td>${backEstimateForm.softwareSucTar}%</td>
		<td>8</td>
		<td>
			${backEstimateForm.softwareSucPoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >故障平均时长</td>
		<td>${backEstimateForm.faultAvgTar}小时</td>
		<td>10</td>
		<td>
			${backEstimateForm.faultAvgPoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >业务恢复平均时长</td>
		<td>${backEstimateForm.operationRenewTar}小时</td>
		<td>15</td>
		<td>
			${backEstimateForm.operationRenewPoi}
		</td>
		<td></td>	
	</tr>	
	<tr>
		<td  class="label"  >板件返修平均时长</td>
		<td>${backEstimateForm.plankRepairTar}天</td>
		<td>15</td>
		<td>
			${backEstimateForm.plankRepairPoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >咨询服务反馈平均时长</td>
		<td>${backEstimateForm.referServeTar}天</td>
		<td>5</td>
		<td>
			${backEstimateForm.referServePoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  rowspan="4">服务满意度（12%）</td>
		<td  class="label"  >技术服务满意度</td>
		<td>${backEstimateForm.artServeTar}分</td>
		<td>3</td>
		<td>
			${backEstimateForm.artServePoi}
		</td>
		<td></td>	
	</tr>	
	<tr>
		<td  class="label"  >工程服务满意度</td>
		<td>${backEstimateForm.projectServeTar}分</td>
		<td>3</td>
		<td>
			${backEstimateForm.projectServePoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >培训服务满意度</td>
		<td>${backEstimateForm.trainServeTar}分</td>
		<td>3</td>
		<td>
			${backEstimateForm.trainServePoi}
		</td>
		<td></td>	
	</tr>
	<tr>
		<td  class="label"  >特通服务满意度</td>
		<td>${backEstimateForm.specialServeTar}分</td>
		<td>3</td>
		<td>
			${backEstimateForm.specialServePoi}
		</td>
		<td></td>	
	</tr>	
	<tr>
		<td colspan="2" class="label" >总分</td>
		<td>-</td>
		<td>-</td>
		<td>${backEstimateForm.total}</td>
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