<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<fmt:bundle basename="com/boco/eoms/netresource/point/config/applicationResource-points">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="points.form.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="points.pointName" />
        </td>
        <td class="content">
            ${pointsForm.pointName}
        </td>
        
        <td class="label">
            <fmt:message key="points.pointNo" />
        </td>
        <td class="content">
            ${pointsForm.pointNo}
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="points.region" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${pointsForm.region}" beanId="tawSystemAreaDao" />
        </td>
        
        <td class="label">
            <fmt:message key="points.city" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${pointsForm.city}" beanId="tawSystemAreaDao" />
        </td>
	</tr>
            
    <tr>
        <td class="label">
            <fmt:message key="points.grid" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${pointsForm.grid}" beanId="gridDao" />
        </td>
    
        <td class="label">
            <fmt:message key="points.line" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${pointsForm.line}" beanId="linesDao" />
        </td>
    
    </tr>
    
    <tr>
    	<td class="label">
            <fmt:message key="points.partner" />
        </td>
        <td class="content">
        	<eoms:id2nameDB id="${pointsForm.partner}" beanId="partnerDeptDao" />
        </td>
        
    	<td class="label">
            <fmt:message key="points.specialtyType" />
        </td>
        <td class="content">
            ${pointsForm.specialtyType}
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="points.groupUser" />
        </td>
        <td class="content">
            ${pointsForm.groupUser}
        </td>
    
        <td class="label">
            <fmt:message key="points.groupUserNo" />
        </td>
        <td class="content">
            ${pointsForm.groupUserNo}
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="points.longitude" />
        </td>
        <td class="content">
            ${pointsForm.longitude}
        </td>
    
        <td class="label">
            <fmt:message key="points.latitude" />
        </td>
        <td class="content">
            ${pointsForm.latitude}
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <fmt:message key="points.loadwork" />
        </td>
        <td class="content">
            ${pointsForm.loadwork}
        </td>
        
        <td class="label">
            <fmt:message key="points.errorScope" />
        </td>
        <td class="content">
            ${pointsForm.errorScope}
        </td>
    </tr>
    
</table>

</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="button" class="btn" onclick="javascript:history.back();" value="<fmt:message key="button.back"/>" />
        </td>
    </tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>