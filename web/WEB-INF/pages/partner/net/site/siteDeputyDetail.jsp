<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'SiteNauticaDeputyForm'});
});

function onRelease(){
		if(confirm('你确定提交?')){
			return true;
		}
		return false;
	}
</script>
<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<html:form  action="/SiteNauticaDeputys.do?method=save" styleId="SiteNauticaDeputyForm" method="post" >

<table class="formTable">
	<caption>
		<div class="header center">申请站点经纬度变更</div>
	</caption>
	<tr>
	    <td class="label">
			站点名称&nbsp;
		</td>
		<td class="content">						
			<eoms:id2nameDB id="${SiteNauticaDeputyForm.siteName}" beanId="siteDao"/>
			<input type="hidden" name="siteName" value="${SiteNauticaDeputyForm.siteName}">						
		</td>	
		<td class="label">
			<fmt:message key="site.region" />&nbsp;
		</td>
		<td class="content">
		    <eoms:id2nameDB id="${SiteNauticaDeputyForm.region}" beanId="tawSystemAreaDao"/>
		    <input type="hidden" name="region" value="${SiteNauticaDeputyForm.region}">																
		</td>
	</tr>	
	<tr>			
	    <td class="label">
			<fmt:message key="site.city" />&nbsp;
		</td>		
		<td class="content">
		   <eoms:id2nameDB id="${SiteNauticaDeputyForm.city}" beanId="tawSystemAreaDao" />
		   <input type="hidden" name="city" value="${SiteNauticaDeputyForm.city}">										
		</td>				
		<td class="label">
			<fmt:message key="site.grid" />&nbsp;
		</td>
		<td class="content">
			<eoms:id2nameDB id="${SiteNauticaDeputyForm.grid}" beanId="gridDao"/>
			<input type="hidden" name="grid" value="${SiteNauticaDeputyForm.grid}">						
		</td>
	</tr>
	<tr>
		<td class="label">
			申请人
		</td>
		<td class="content">
			        ${SiteNauticaDeputyForm.requestPerson}
			<html:hidden property="requestPerson" value="${SiteNauticaDeputyForm.requestPerson}" />		
		</td>
		<td class="label">
			申请人联系方式
		</td>
		<td class="content">
			${SiteNauticaDeputyForm.requestPersonTel}
			<input type="hidden" name="requestPersonTel" value="${SiteNauticaDeputyForm.requestPersonTel}">			
		</td>
	</tr>	
	<tr>
		<td class="label">
			站点经度
		</td>
		<td class="content">			       
		    ${SiteNauticaDeputyForm.longitude}
		    <input type="hidden" name="longitude" value="${SiteNauticaDeputyForm.longitude}">			
		</td>
		<td class="label">
			站点维度
		</td>
		<td class="content">
			${SiteNauticaDeputyForm.latitude}	
			<input type="hidden" name="latitude" value="${SiteNauticaDeputyForm.latitude}">		
		</td>
	</tr>	
	<tr>
		<td class="label">
			申请说明&nbsp;
		</td>
		<td class="content" colspan="3">
			${SiteNauticaDeputyForm.requestReason}
			<input type="hidden" name="requestReason" value="${SiteNauticaDeputyForm.requestReason}">
		</td>
	</tr>

<html:hidden property="id" value="${SiteNauticaDeputyForm.id}" />

        <tr>
			   <td class="label">
			         审核是否通过&nbsp;
			   </td>
		       <td>
		           <select name="state">
		                <option value="2">通过</option>
		                <option value="0">驳回</option>
		           </select>
		       </td>
		    </tr>
			<tr height="20">
			    <td class="label">
			           审核意见 
			    </td>
				<td class="content max" colspan="3">
				    <textarea cols="83" rows="5" id="auditReason" name="auditReason"></textarea>		
				</td>			
		   </tr> 
       
</table>
<table>
   <tr height="20">
		<td colspan="2">
			<input type="submit" class="btn" value="提交" onclick="return onRelease()"/>
		</td>
	</tr>
</table>
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>