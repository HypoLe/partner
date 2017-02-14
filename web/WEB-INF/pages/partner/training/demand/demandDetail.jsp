<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="demand.detail.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="demand.topic" />
        </td>
        <td class="content">
            ${demandForm.topic}
        </td>
        
        <td class="label">
            <fmt:message key="demand.expectTime" />
        </td>
        <td class="content">
            ${demandForm.expectTime}
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="demand.applyUser" />
        </td>
        <td class="content">
            ${demandForm.applyUser}
        </td>
    
        <td class="label">
            <fmt:message key="demand.dept" />
        </td>
        <td class="content">
            ${demandForm.dept}
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="demand.referUnit" />
        </td>
        <td class="content">
            ${demandForm.referUnit}
        </td>
    
        <td class="label">
            <fmt:message key="demand.referSpac" />
        </td>
        <td class="content">
            ${demandForm.referSpac}
        </td>
    </tr>
    
     <tr>
        <td class="label">
            <fmt:message key="demand.linkMobile" />
        </td>
        <td class="content">
            ${demandForm.linkMobile}
        </td>
        
        <td class="label"></td>
        <td class="content"></td>
    </tr>
   
    <tr>
        <td class="label">
            <fmt:message key="demand.expectContent" />
        </td>
        <td class="content" colspan='3'>
            ${demandForm.expectContent}
        </td>
    </tr>
             
    <tr>
        <td class="label">
            <fmt:message key="demand.remark" />
        </td>
        <td class="content" colspan='3'>
            ${demandForm.remark}
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