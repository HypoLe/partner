<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language="javascript">

Ext.onReady(function() {

    // 设置检索条件部分不显示
	Ext.fly("listQueryObject").setDisplayed(false);
                                                                                                                                            
    approveUserViewer = new Ext.JsonView("approveUserView",
            '<div class="viewlistitem-user">{name}</div>',
            { 
                emptyText : '<div>未选择用户</div>'
            }
        );
    var approveUserTreeData = '${jsonApproveUserTree}';
    approveUserViewer.jsonData = eoms.JSONDecode(approveUserTreeData);
    approveUserViewer.refresh();
    var treeAction='${app}/xtree.do?method=userFromDept';
    approveUserTree = new xbox({
            btnId:'approveUserBtn',
            dlgId:'hello-dlg',
            treeDataUrl:treeAction,
            treeRootId:'-1',
            treeRootText:'选择用户',
            treeChkMode:'',
            treeChkType:'user',
            saveChkFldId:'approveUser',
            viewer:approveUserViewer
        });
                                                                                                                                                                                                                                                    
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

<fmt:bundle basename="com/boco/eoms/netresource/modify/config/applicationResource-modify">

<div class="form-btns" id="form-btns">
  <span id="openQuery">
  	<input type="button" class="btn" onclick="openQuery()" value='展开快速查询'/>
  </span>
  <span id="closeQuery" style="display:none">
    <input type="button" class="btn" onclick="closeQuery()" value='关闭快速查询'/>
  </span>
</div>

<span id="listQueryObject">

<html:form action="/modifys.do?method=search" styleId="modifyForm" method="post"> 

<html:hidden property="flag" value="${flag}" />

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="modify.search.heading"/></div>
    </caption>
    
    <html:hidden property="flag" value="${flag}" />
            
    <tr>
        <td class="label">
            <fmt:message key="modify.approveUser" />
        </td>
        <td class="content">
            <html:hidden property="approveUser" styleId="approveUser"
                        value="${modifyForm.approveUser}" />
            <input type="button" class="btn" name="approveUserBtn" value="<fmt:message key="button.select" />" /> 
            <div id="approveUserView" class="viewer-list"></div>
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <fmt:message key="modify.description" />
        </td>
        <td class="content">
            <html:text property="description" styleId="description"
                        styleClass="text medium"
                        value="${modifyForm.description}" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.approveStatus" />
        </td>
        <td class="content">
            <select name="approveStatus" id="approveStatus" style="width:20%;" >
				<option value=""> -- 请选择申请状态 -- </option>
				<option value="1">待审批</option>
				<option value="2">已受理</option>
				<option value="3">已同意</option>
				<option value="4">已驳回</option>
			</select>
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.modifyType" />
        </td>
        <td class="content">
			<select name="modifyType" id="modifyType" style="width:20%;" >
				<option value=""> -- 请选择变更类型 -- </option>
				<option value="1">坐标变更</option>
				<option value="2">资源新增</option>
				<option value="3">资源修改</option>
				<option value="4">资源删除</option>
			</select>
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.resourceType" />
        </td>
        <td class="content">
            <select name="resourceType" id="resourceType" style="width:20%;" >
				<option value=""> -- 请选择资源类型 -- </option>
				<option value="1">基站</option>
				<option value="2">线路</option>
				<option value="3">标点</option>
				<option value="4">设备</option>
			</select>
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

<html:form action="/modifys.do?method=removeSel" styleId="modifyForm" method="post"> 
<caption>
    <div class="header center"><b><fmt:message key="modify.list.heading"/></b></div>
</caption>
<display:table name="modifyList" cellspacing="0" cellpadding="0"
    id="modifyList" pagesize="${pageSize}" class="table modifyList"
    export="false"
    requestURI="${app}/netresource/modify/modifys.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.netresource.modify.util.ModifyDecorator">
    
    <logic:present name="modifyList" property="id">
    
    <logic:notEmpty name="modifyList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        	           <input type="checkbox" id="${modifyList.id}" name="ids" value="${modifyList.id}" />
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
                
    <display:column sortable="true" headerClass="sortable" titleKey="modify.applyUser">
        <eoms:id2nameDB id="${modifyList.applyUser}" beanId="tawSystemUserDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="modify.applyDept">
        <eoms:id2nameDB id="${modifyList.applyDept}" beanId="tawSystemDeptDao" />
    </display:column>
    
    <display:column property="applyTime" sortable="true" headerClass="sortable"
        titleKey="modify.applyTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="description" sortable="true" headerClass="sortable"
        titleKey="modify.description" paramId="id" paramProperty="id"/>

    <display:column sortable="true" headerClass="sortable" titleKey="modify.approveUser">
        <eoms:id2nameDB id="${modifyList.approveUser}" beanId="tawSystemUserDao" />
    </display:column>                
    
    <display:column property="acceptTime" sortable="true" headerClass="sortable"
        titleKey="modify.acceptTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="approveTime" sortable="true" headerClass="sortable"
        titleKey="modify.approveTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="approveStatus" sortable="true" headerClass="sortable"
        titleKey="modify.approveStatus" paramId="id" paramProperty="id"/>
                    
    <display:column property="modifyType" sortable="true" headerClass="sortable"
        titleKey="modify.modifyType" paramId="id" paramProperty="id"/>
                    
    <display:column property="resourceType" sortable="true" headerClass="sortable"
        titleKey="modify.resourceType" paramId="id" paramProperty="id"/>
                    
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/netresource/modify/modifys.do?method=detail&id=${modifyList.id}"
            title="<fmt:message key="button.detail"/>"><img src="${app}/images/icons/search.gif" /></a>
    </display:column>
	
	<!-- 此处添加权限控制 -->
	<% if( null != request.getAttribute("flag") && "manager".equals(request.getAttribute("flag")) ){ %>
    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
        <a href="${app}/netresource/modify/modifys.do?method=edit&id=${modifyList.id}"
                title="<fmt:message key="button.edit"/>"><img src="${app}/images/icons/edit.gif" /></a>
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
        {window.location.href = '${app}/netresource/modify/modifys.do?method=remove&flag=manager&id=${modifyList.id}';}"
                title="<fmt:message key="button.remove"/>"><img src="${app}/images/icons/icon.gif" /></a>
	</display:column>
	<% } %>

    </logic:present>
</display:table>

</html:form>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>