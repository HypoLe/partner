<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language="javascript">

Ext.onReady(function() {

    // 设置检索条件部分不显示
	Ext.fly("listQueryObject").setDisplayed(false);
                                                        
    releaseUserViewer = new Ext.JsonView("releaseUserView",
            '<div class="viewlistitem-user">{name}</div>',
            { 
                emptyText : '<div>未选择用户</div>'
            }
        );
    var releaseUserTreeData = '${jsonReleaseUserTree}';
    releaseUserViewer.jsonData = eoms.JSONDecode(releaseUserTreeData);
    releaseUserViewer.refresh();
    var treeAction='${app}/xtree.do?method=userFromDept';
    releaseUserTree = new xbox({
            btnId:'releaseUserBtn',
            dlgId:'hello-dlg',
            treeDataUrl:treeAction,
            treeRootId:'-1',
            treeRootText:'选择用户',
            treeChkMode:'',
            treeChkType:'user',
            saveChkFldId:'releaseUser',
            viewer:releaseUserViewer
        });
                                        
    deptViewer = new Ext.JsonView("deptView",
            '<div class="viewlistitem-dept">{name}</div>',
            { 
                emptyText : '<div>未选择部门</div>'
            }
        );
    var deptTreeData = '${jsonDeptTree}';
    deptViewer.jsonData = eoms.JSONDecode(deptTreeData);
    deptViewer.refresh();
    var treeAction='${app}/xtree.do?method=userFromDept';
    deptTree = new xbox({
            btnId:'deptBtn',
            dlgId:'hello-dlg',
            treeDataUrl:treeAction,
            treeRootId:'-1',
            treeRootText:'选择部门',
            treeChkMode:'',
            treeChkType:'dept',
            saveChkFldId:'dept',
            viewer:deptViewer
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

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<div class="form-btns" id="form-btns">
  <span id="openQuery">
  	<input type="button" class="btn" onclick="openQuery()" value='展开快速查询'/>
  </span>
  <span id="closeQuery" style="display:none">
    <input type="button" class="btn" onclick="closeQuery()" value='关闭快速查询'/>
  </span>
</div>

<span id="listQueryObject">

<html:form action="/plans.do?method=search" styleId="planForm" method="post">
 
<html:hidden property="flag" value="${flag}" />

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="plan.search.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.planName" />
        </td>
        <td class="content">
                                    
            <html:text property="planName" styleId="planName"
                        styleClass="text medium"
                        value="${planForm.planName}" />
                                                                                                
        </td>
        
        <td class="label">
            <fmt:message key="plan.linkMobile" />
        </td>
        <td class="content">
                                    
            <html:text property="linkMobile" styleId="linkMobile"
                        styleClass="text medium"
                        value="${planForm.linkMobile}" />
                                                                                                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.releaseUser" />
        </td>
        <td class="content">
                                                                                    
            <html:hidden property="releaseUser" styleId="releaseUser"
                        value="${planForm.releaseUser}" />
            <input type="button" class="btn" name="releaseUserBtn" value="<fmt:message key="button.select" />" /> 
            <div id="releaseUserView" class="viewer-list"></div>
                                                
        </td>
    
        <td class="label">
            <fmt:message key="plan.dept" />
        </td>
        <td class="content">
                                                                                                
            <html:hidden property="dept" styleId="dept"
                        value="${planForm.dept}" />
            <input type="button" class="btn" name="deptBtn" value="<fmt:message key="button.select" />" /> 
            <div id="deptView" class="viewer-list"></div>
                                    
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.beginTime" />
        </td>
        <td class="content">
                                                            
            <html:text property="beginTime" styleId="beginTime"
                        styleClass="text medium"
                        value="${planForm.beginTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
                                                                        
        </td>
    	
    	<td class="label">
            <fmt:message key="plan.closeTime" />
        </td>
        <td class="content">
                                                            
            <html:text property="closeTime" styleId="closeTime"
                        styleClass="text medium"
                        value="${planForm.closeTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
                                                                        
        </td>
    </tr>
            
    <tr>
    	<td class="label">
            <fmt:message key="plan.days" />
        </td>
        <td class="content">
                                    
            <html:text property="days" styleId="days"
                        styleClass="text medium"
                        value="${planForm.days}" />
                                                                                                
        </td>
    	
        <td class="label">
            <fmt:message key="plan.place" />
        </td>
        <td class="content">
                                    
            <html:text property="place" styleId="place"
                        styleClass="text medium"
                        value="${planForm.place}" />
                                                                                                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.organizeUnit" />
        </td>
        <td class="content">
                                    
            <html:text property="organizeUnit" styleId="organizeUnit"
                        styleClass="text medium"
                        value="${planForm.organizeUnit}" />
                                                                                                
        </td>
    
        <td class="label">
            <fmt:message key="plan.referUnit" />
        </td>
        <td class="content">
                                    
            <html:text property="referUnit" styleId="referUnit"
                        styleClass="text medium"
                        value="${planForm.referUnit}" />
                                                                                                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.referSpac" />
        </td>
        <td class="content">
                                    
            <html:text property="referSpac" styleId="referSpac"
                        styleClass="text medium"
                        value="${planForm.referSpac}" />
                                                                                                
        </td>
    
        <td class="label">
            <fmt:message key="plan.referDemandId" />
        </td>
        <td class="content">
                                    
            <select name="referDemandId" id="referDemandId"
				alt="allowBlank:false,vtext:'涉及需求编号不能为空！'">
				<option value="">
					--请选涉及需求编号--
				</option>
				<logic:iterate id="demandList" name="demandList">
					<option value="${demandList.id}">
						${demandList.topic}
					</option>
				</logic:iterate>
			</select>
                                                                                                
        </td>
    </tr>
    
</table>
<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
            <!--<input type="button" class="btn" value="<fmt:message key="button.clear"/>" />-->
        </td>
    </tr>
</table>
</html:form>
</span>

<html:form action="/plans.do?method=removeSel" styleId="planForm" method="post"> 
<caption>
    <div class="header center"><b><fmt:message key="plan.list.heading"/></b></div>
</caption>
<display:table name="planList" cellspacing="0" cellpadding="0"
    id="planList" pagesize="${pageSize}" class="table planList"
    export="false"
    requestURI="${app}/partner/training/plans.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.partner.training.util.PlanDecorator">
    
    <logic:present name="planList" property="id">
    <logic:notEmpty name="planList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        
        	           <input type="checkbox" id="${planList.id}" name="ids" value="${planList.id}" />
	            
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
    
    <display:column property="planName" sortable="true" headerClass="sortable"
        titleKey="plan.planName" paramId="id" paramProperty="id"/>
                    
    <display:column property="releaseUser" sortable="true" headerClass="sortable"
        titleKey="plan.releaseUser" paramId="id" paramProperty="id"/>
                    
    <display:column property="dept" sortable="true" headerClass="sortable"
        titleKey="plan.dept" paramId="id" paramProperty="id"/>
                    
    <display:column property="linkMobile" sortable="true" headerClass="sortable"
        titleKey="plan.linkMobile" paramId="id" paramProperty="id"/>
                    
    <display:column property="beginTime" sortable="true" headerClass="sortable"
        titleKey="plan.beginTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="days" sortable="true" headerClass="sortable"
        titleKey="plan.days" paramId="id" paramProperty="id"/>
                    
    <display:column property="closeTime" sortable="true" headerClass="sortable"
        titleKey="plan.closeTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="place" sortable="true" headerClass="sortable"
        titleKey="plan.place" paramId="id" paramProperty="id"/>
                    
    <display:column property="organizeUnit" sortable="true" headerClass="sortable"
        titleKey="plan.organizeUnit" paramId="id" paramProperty="id"/>
                    
    <display:column property="referUnit" sortable="true" headerClass="sortable"
        titleKey="plan.referUnit" paramId="id" paramProperty="id"/>
                    
    <display:column property="referSpac" sortable="true" headerClass="sortable"
        titleKey="plan.referSpac" paramId="id" paramProperty="id"/>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/partner/training/plans.do?method=detail&id=${planList.id}"
            title="<fmt:message key="button.detail"/>">
            	<img src="${app}/images/icons/search.gif" />
            </a>
    </display:column>

	<!-- 此处添加权限控制 -->
	<% if( null != request.getAttribute("flag") && "manager".equals(request.getAttribute("flag")) ){ %>
    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
        	           <a href="${app}/partner/training/plans.do?method=edit&id=${planList.id}"
                title="<fmt:message key="button.edit"/>">
                	<img src="${app}/images/icons/edit.gif" />
                </a>
	    	    
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
                        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
            {window.location.href = '${app}/partner/training/plans.do?method=remove&flag=manager&id=${planList.id}';}"
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