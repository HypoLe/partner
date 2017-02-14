<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
    v = new eoms.form.Validation({form:'assetForm'});
                                                                                                                                                                                                        
});
</script>

<html:form action="/assets.do?method=save" styleId="assetForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/netresource/asset/config/applicationResource-asset">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="asset.form.heading"/></div>
    </caption>
                                                    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="asset.assetName" />
        </td>
        <td class="content">
            <html:text property="assetName" styleId="assetName"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'资产名称 不能为空！',maxLength:100" value="${assetForm.assetName}" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="asset.assetType" />
        </td>
        <td class="content">
            <html:text property="assetType" styleId="assetType"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'资产类型 不能为空！',maxLength:25" value="${assetForm.assetType}" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="asset.assetModel" />
        </td>
        <td class="content">
            <html:text property="assetModel" styleId="assetModel"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'资产规格型号 不能为空！',maxLength:50" value="${assetForm.assetModel}" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="asset.assetUseTime" />
        </td>
        <td class="content">
            <html:text property="assetUseTime" styleId="assetUseTime"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'资产启用时间 不能为空！'" value="${assetForm.assetUseTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="asset.assetBarCode" />
        </td>
        <td class="content">
            <html:text property="assetBarCode" styleId="assetBarCode"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'资产条形码  不能为空且必须为数字串！',maxLength:200" value="${assetForm.assetBarCode}" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="asset.assetSitusTag" />
        </td>
        <td class="content">
            <html:text property="assetSitusTag" styleId="assetSitusTag"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'资产地点标签 不能为空！',maxLength:50" value="${assetForm.assetSitusTag}" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="asset.assetLocation" />
        </td>
        <td class="content">
            <html:text property="assetLocation" styleId="assetLocation"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'分布物理位置 不能为空！',maxLength:50" value="${assetForm.assetLocation}" />
        </td>
    
        <td class="label">
            <font color=''> * </font><fmt:message key="asset.assetRemark" />
        </td>
        <td class="content">
            <html:textarea property="assetRemark" styleId="assetRemark"
                        styleClass="text medium"
                        alt="allowBlank:true,vtext:'资产备注 不能超出125个汉字！',maxLength:125" value="${assetForm.assetRemark}" />
        </td>
    </tr>
                                        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
            <c:if test="${not empty assetForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${assetForm.id}" />
<html:hidden property="createUser" value="${assetForm.createUser}" />
<html:hidden property="createTime" value="${assetForm.createTime}" />
<html:hidden property="isDeleted" value="${assetForm.isDeleted}" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>