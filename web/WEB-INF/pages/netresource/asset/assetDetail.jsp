<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<fmt:bundle basename="com/boco/eoms/netresource/asset/config/applicationResource-asset">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="asset.detail.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.createUser" />
        </td>
        <td class="content">
                                    
            ${assetForm.createUser}
                
        </td>
    
        <td class="label">
            <fmt:message key="asset.createTime" />
        </td>
        <td class="content">
                                    
            ${assetForm.createTime}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetName" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetName}
                
        </td>
    
        <td class="label">
            <fmt:message key="asset.assetType" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetType}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetModel" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetModel}
                
        </td>
    
        <td class="label">
            <fmt:message key="asset.assetUseTime" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetUseTime}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetBarCode" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetBarCode}
                
        </td>
    
        <td class="label">
            <fmt:message key="asset.assetSitusTag" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetSitusTag}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetLocation" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetLocation}
                
        </td>
    
        <td class="label">
            <fmt:message key="asset.assetRemark" />
        </td>
        <td class="content">
                                    
            ${assetForm.assetRemark}
                
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