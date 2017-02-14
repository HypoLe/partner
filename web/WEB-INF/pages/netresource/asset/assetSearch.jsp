<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language="javascript">

Ext.onReady(function() {

    // 设置检索条件部分不显示
	Ext.fly("listQueryObject").setDisplayed(false);
                                                                                                                                                                                                                                                                                                                                
});

    
    	var checkflag = "false";
    function choose() {
        var objs = document.getElementsByName("ids");
        if(checkflag=="false"){
            for(var i=0; i<objs.length; i++) {
                if(objs[i].type.toLowerCase() == "checkbox" )
                    objs[i].checked = true;
                checkflag="true";
            }
        }else if(checkflag=="true"){
            for(var i=0; i<objs.length; i++) {
                if(objs[i].type.toLowerCase() == "checkbox" )
                    objs[i].checked = false;
                checkflag="false"
            }
        }
    }
	
    function ConfirmDel(){
        
        var flag=false;
        var ids;
        
        var objs = document.getElementsByName("ids");
        for(var i=0; i<objs.length; i++) {
            if(objs[i].type.toLowerCase() == "checkbox" )
            if(objs[i].checked){
                flag=true;
                ids=objs[i];
            }
         }
        if(flag){
            if(confirm("您确定要删除您选择的数据？")){
                return true;
            }else{
                return false;
            }
        }else {
            alert("请选择要删除的数据！");
            return false;
        }
    }
    
    function clear() {
        
    }

    function openQuery(){
      
        // 设置检索条件部分显示
	    Ext.fly("listQueryObject").setDisplayed(true);

        // 设置展开查询的链接部分不显示
	    var openQuery = document.getElementById("openQuery");
	    openQuery.style.display = "none";
	    // 设置关闭查询的链接部分显示
	    var closeQuery = document.getElementById("closeQuery");
	    closeQuery.style.display = "";
   }
   function closeQuery(){

        // 设置检索条件部分不显示
        var listQueryObject = document.getElementById("listQueryObject");
        listQueryObject.style.display = "none";
        
        // 设置展开查询的链接部分显示
        var openQuery = document.getElementById("openQuery");
        openQuery.style.display = "";
        
        // 设置关闭查询的链接部分不显示
        var closeQuery = document.getElementById("closeQuery");
        closeQuery.style.display = "none";
   }

</script>

<fmt:bundle basename="com/boco/eoms/netresource/asset/config/applicationResource-asset">

<div class="form-btns" id="form-btns">
  <span id="openQuery">
  	<input type="button" class="btn" onclick="openQuery()" value='展开快速查询'/>
  </span>
  <span id="closeQuery" style="display:none">
    <input type="button" class="btn" onclick="closeQuery()" value='关闭快速查询'/>
  </span>
</div>

<span id="listQueryObject">

<html:form action="/assets.do?method=search" styleId="assetForm" method="post"> 

<html:hidden property="flag" value="${flag}" />

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="asset.search.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetName" />
        </td>
        <td class="content">
            <html:text property="assetName" styleId="assetName"
                        styleClass="text medium"
                        value="${assetForm.assetName}" />
        </td>
   
        <td class="label">
            <fmt:message key="asset.assetType" />
        </td>
        <td class="content">
            <html:text property="assetType" styleId="assetType"
                        styleClass="text medium"
                        value="${assetForm.assetType}" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetModel" />
        </td>
        <td class="content">
            <html:text property="assetModel" styleId="assetModel"
                        styleClass="text medium"
                        value="${assetForm.assetModel}" />
        </td>
    
        <td class="label">
            <fmt:message key="asset.assetUseTime" />
        </td>
        <td class="content">
            <html:text property="assetUseTime" styleId="assetUseTime"
                        styleClass="text medium"
                        value="${assetForm.assetUseTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetBarCode" />
        </td>
        <td class="content">
            <html:text property="assetBarCode" styleId="assetBarCode"
                        styleClass="text medium"
                        value="${assetForm.assetBarCode}" />
        </td>
    
        <td class="label">
            <fmt:message key="asset.assetSitusTag" />
        </td>
        <td class="content">
            <html:text property="assetSitusTag" styleId="assetSitusTag"
                        styleClass="text medium"
                        value="${assetForm.assetSitusTag}" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="asset.assetLocation" />
        </td>
        <td class="content" colspan='3'>
            <html:text property="assetLocation" styleId="assetLocation"
                        styleClass="text medium"
                        value="${assetForm.assetLocation}" />
        </td>
    </tr>
            
</table>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
        </td>
    </tr>
</table>

</html:form>

</span>

<html:form action="/assets.do?method=removeSel" styleId="assetForm" method="post"> 

<caption>
    <div class="header center"><b><fmt:message key="asset.list.heading"/></b></div>
</caption>

<display:table name="assetList" cellspacing="0" cellpadding="0"
    id="assetList" pagesize="${pageSize}" class="table assetList"
    export="false"
    requestURI="${app}/netresource/asset/assets.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.netresource.asset.util.AssetDecorator">
    
    <logic:present name="assetList" property="id">
    <logic:notEmpty name="assetList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        	 <input type="checkbox" id="${assetList.id}" name="ids" value="${assetList.id}" />
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
    
    <display:column property="assetName" sortable="true" headerClass="sortable"
        titleKey="asset.assetName" paramId="id" paramProperty="id"/>
                    
    <display:column property="assetType" sortable="true" headerClass="sortable"
        titleKey="asset.assetType" paramId="id" paramProperty="id"/>
                    
    <display:column property="assetModel" sortable="true" headerClass="sortable"
        titleKey="asset.assetModel" paramId="id" paramProperty="id"/>
                    
    <display:column property="assetUseTime" sortable="true" headerClass="sortable"
        titleKey="asset.assetUseTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="assetBarCode" sortable="true" headerClass="sortable"
        titleKey="asset.assetBarCode" paramId="id" paramProperty="id"/>
                    
    <display:column property="assetSitusTag" sortable="true" headerClass="sortable"
        titleKey="asset.assetSitusTag" paramId="id" paramProperty="id"/>
                    
    <display:column property="assetLocation" sortable="true" headerClass="sortable"
        titleKey="asset.assetLocation" paramId="id" paramProperty="id"/>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/netresource/asset/assets.do?method=detail&id=${assetList.id}" title="<fmt:message key="button.detail"/>">
			<img src="${app}/images/icons/search.gif" />
		</a>
    </display:column>

	<!-- 此处添加权限控制 -->
	<% if( null != request.getAttribute("flag") && "manager".equals(request.getAttribute("flag")) ){ %>
    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
        <a href="${app}/netresource/asset/assets.do?method=edit&id=${assetList.id}" title="<fmt:message key="button.edit"/>">
        	<img src="${app}/images/icons/edit.gif" />        
        </a>
	    	    
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
        {window.location.href = '${app}/netresource/asset/assets.do?method=remove&flag=manager&id=${assetList.id}';}" 
        title="<fmt:message key="button.remove"/>">
             <img src="${app}/images/icons/icon.gif" />
        </a>
	</display:column>
	<% } %>

    </logic:present>

</display:table>

</html:form>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>