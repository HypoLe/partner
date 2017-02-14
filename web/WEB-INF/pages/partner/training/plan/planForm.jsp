<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
    v = new eoms.form.Validation({form:'planForm'});
                                            
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
</script>

<html:form action="/plans.do?method=save" styleId="planForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="plan.form.heading"/></div>
    </caption>
                                                    
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.planName" />
                        
        </td>
        <td class="content">
            <html:text property="planName" styleId="planName"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'培训计划名称不能为空！'" value="${planForm.planName}" />
                                                                                                
        </td>
        
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.referDemandId" />
                        
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
                                        
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.linkMobile" />
                        
        </td>
        <td class="content">
                                    
            <html:text property="linkMobile" styleId="linkMobile"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'联系电话不能为空！',maxLength:25" value="${planForm.linkMobile}" />
                                                                                                
        </td>
    
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.beginTime" />
                        
        </td>
        <td class="content">
                                                            
            <html:text property="beginTime" styleId="beginTime"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'培训开始时间不能为空！'" value="${planForm.beginTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
                                                                        
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.days" />
                        
        </td>
        <td class="content">
                                    
            <html:text property="days" styleId="days"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'培训天数不能为空！',maxLength:10" value="${planForm.days}" />
                                                                                                
        </td>
    
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.closeTime" />
                        
        </td>
        <td class="content">
                                                            
            <html:text property="closeTime" styleId="closeTime"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'报名截止时间不能为空！'" value="${planForm.closeTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
                                                                        
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.place" />
                        
        </td>
        <td class="content">
                                    
            <html:text property="place" styleId="place"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'培训地点不能为空！',maxLength:50" value="${planForm.place}" />
                                                                                                
        </td>
   
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.organizeUnit" />
                        
        </td>
        <td class="content">
                                    
            <html:text property="organizeUnit" styleId="organizeUnit"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'组织单位不能为空！',maxLength:125" value="${planForm.organizeUnit}" />
                                                                                                
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.referUnit" />
                        
        </td>
        <td class="content">
                                    
            <html:text property="referUnit" styleId="referUnit"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'涉及单位不能为空！',maxLength:125" value="${planForm.referUnit}" />
                                                                                                
        </td>
    
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.referSpac" />
                        
        </td>
        <td class="content">
                                    
            <html:text property="referSpac" styleId="referSpac"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'涉及专业不能为空！',maxLength:125" value="${planForm.referSpac}" />
                                                                                                
        </td>
    </tr>
    
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.releaseUser" />
                        
        </td>
        <td class="content">
                                                                                    
            <html:hidden property="releaseUser" styleId="releaseUser"
                        alt="allowBlank:false,vtext:'培训发布人不能为空！'" value="${planForm.releaseUser}" />
            <input type="button" class="btn" name="releaseUserBtn" value="<fmt:message key="button.select" />" /> 
            <div id="releaseUserView" class="viewer-list"></div>
                                                
        </td>
   
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.dept" />
                        
        </td>
        <td class="content">
                                                                                                
            <html:hidden property="dept" styleId="dept"
                        alt="allowBlank:false,vtext:'所属部门不能为空！'" value="${planForm.dept}" />
            <input type="button" class="btn" name="deptBtn" value="<fmt:message key="button.select" />" /> 
            <div id="deptView" class="viewer-list"></div>
                                    
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.content" />
                        
        </td>
        <td class="content" colspan='3'>
                                                
            <html:textarea property="content" styleId="content"
                        styleClass="text medium" style='width:98%' rows='4'
                        alt="allowBlank:false,vtext:'期望培训内容不能为空！',maxLength:2000" value="${planForm.content}" />
                                                                                    
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
                            
            <font color=''> * </font><fmt:message key="plan.remark" />
                        
        </td>
        <td class="content" colspan='3'>
                                                
            <html:textarea property="remark" styleId="remark"
                        styleClass="text medium" style='width:98%' rows='3'
                        alt="allowBlank:true,vtext:'备注',maxLength:125" value="${planForm.remark}" />
                                                                                    
        </td>
    </tr>
        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
            <c:if test="${not empty planForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${planForm.id}" />
<html:hidden property="createUser" value="${planForm.createUser}" />
<html:hidden property="createTime" value="${planForm.createTime}" />
<html:hidden property="isDeleted" value="${planForm.isDeleted}" />

</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
	var err = '${err}';
    if(err!=''){
    	alert(err);
    }
    var referDemandId = '${planForm.referDemandId}';
    
	if(referDemandId!=''){
 		document.getElementById("referDemandId").value = referDemandId;
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>