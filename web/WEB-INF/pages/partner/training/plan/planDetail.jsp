<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="plan.detail.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.planName" />
        </td>
        <td class="content">
                                    
            ${planForm.planName}
                
        </td>
        
        <td class="label">
            <fmt:message key="plan.linkMobile" />
        </td>
        <td class="content">
                                    
            ${planForm.linkMobile}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.releaseUser" />
        </td>
        <td class="content">
                                
            ${planForm.releaseUser}
                
        </td>
    
        <td class="label">
            <fmt:message key="plan.dept" />
        </td>
        <td class="content">
                                
            ${planForm.dept}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.beginTime" />
        </td>
        <td class="content">
                                    
            ${planForm.beginTime}
                
        </td>
    
        <td class="label">
            <fmt:message key="plan.days" />
        </td>
        <td class="content">
                                    
            ${planForm.days}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.closeTime" />
        </td>
        <td class="content">
                                    
            ${planForm.closeTime}
                
        </td>
    
        <td class="label">
            <fmt:message key="plan.place" />
        </td>
        <td class="content">
                                    
            ${planForm.place}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.organizeUnit" />
        </td>
        <td class="content">
                                    
            ${planForm.organizeUnit}
                
        </td>
    
        <td class="label">
            <fmt:message key="plan.referUnit" />
        </td>
        <td class="content">
                                    
            ${planForm.referUnit}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.referSpac" />
        </td>
        <td class="content">
                                    
            ${planForm.referSpac}
                
        </td>
    
        <td class="label">
            <fmt:message key="plan.referDemandId" />
        </td>
        <td class="content">
                                    
            <eoms:id2nameDB id="${planForm.referDemandId}" beanId="demandDao" />
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.content" />
        </td>
        <td class="content" colspan='3'>
                                    
            ${planForm.content}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.remark" />
        </td>
        <td class="content" colspan='3'>
                                    
            ${planForm.remark}
                
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