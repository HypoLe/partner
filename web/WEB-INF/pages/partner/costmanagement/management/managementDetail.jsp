<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/management/config/applicationResource-management">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="management.detail.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="management.beginCostTime" />
        </td>
        <td class="content">
                                    
            ${managementForm.beginCostTime}
                
        </td>
    
        <td class="label">
            <fmt:message key="management.endCostTime" />
        </td>
        <td class="content">
                                    
            ${managementForm.endCostTime}
                
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <fmt:message key="management.partnerNum" />
        </td>
        <td class="content">
                                    
            ${managementForm.partnerNum}
                
        </td>
    
        <td class="label">
            <fmt:message key="management.expenseCost" />
        </td>
        <td class="content">
                                    
            ${managementForm.expenseCost}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="management.partnerName" />
        </td>
        <td class="content">
                                    
            <eoms:id2nameDB id="${managementForm.partnerId}" beanId="partnerDeptDao" />
                
        </td>
    
        <td class="label">
            <fmt:message key="management.remark" />
        </td>
        <td class="content">
                                    
            ${managementForm.remark}
                
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