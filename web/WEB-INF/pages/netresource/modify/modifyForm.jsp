<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

Ext.onReady(function() {
    v = new eoms.form.Validation({form:'modifyForm'});
    v.custom = function(){
	    if( eoms.$('modifyType').value != "2" && eoms.$('resourceId').value == "" ){
	      	alert("请选择 【变更资源】!"); 
	      	return false; 
	    }
	    if(eoms.$('resourceType').value == "4" && eoms.$('inspectDeviceId').value == ""){
	    	alert("请选择 【变更设备】!"); 
	      	return false; 
	    }
      	document.getElementById("submitInput").disabled = true;//锁定提交按钮
      	return true;
   	}
                                                                          
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

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i); //firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

//资源变更类型改动
function modifyTypes(con){
	document.getElementById("resourceType").value = "";//清空已选择
	document.getElementById("resourceId").value = "";//清空已选择
	document.getElementById("inspectDeviceId").value = "";//清空已选择
	
	var modifyType = document.getElementById("modifyType").value;
	if(modifyType == '2'){//新增资源
		document.getElementById("resourceId").disabled = true;//不提交
		document.getElementById("inspectDeviceId").disabled = true;//不提交
	}else{
		document.getElementById("resourceId").disabled = false;//提交
		document.getElementById("inspectDeviceId").disabled = false;//提交
	}
}

//资源类型变更 ：级联当前用户所在合作伙伴所负责的该资源类型下的全部资源列表
function resourceTypes(con){    
		    delAllOption("resourceId");//资源类型选择更新后，重新刷新变更资源列表
		    var modifyType = document.getElementById("modifyType").value;
			var resourceType = document.getElementById("resourceType").value;
			
			if(resourceType != '4'){//没有选择变更设备
				document.getElementById("inspectDeviceId").disabled = true;//不提交
			}else if(modifyType != '2'){
				document.getElementById("inspectDeviceId").disabled = false;//提交
			}
			
			var url="<%=request.getContextPath()%>/netresource/modify/modifys.do?method=changeResType&resourceType="+resourceType;
			
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									
									var countyName = "resourceId";
									var arrOptions = json[0].res.split(",");
									var obj=$(countyName);
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									
									if(con==1){
										var resourceType = '${modifyForm.resourceType}';
										if(resourceType!=''){
											document.getElementById("resourceType").value = resourceType;
										}	
									}
									
									//changeResource(con);//级联设备
									
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}

//通过选择站点、标点，级联所选点下的设备列表
function changeResource(con){
		delAllOption("inspectDeviceId");//站点 标点更新后，重新刷新变更设备列表
		var resourceType = document.getElementById("resourceType").value;
			
		if(resourceType == '4'){//选择变更设备
			var resourceId = document.getElementById("resourceId").value;//站点或标点ID
			var url="<%=request.getContextPath()%>/netresource/modify/modifys.do?method=changeResType&resourceType=5&=siteId"+resourceId;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									
									var countyName = "inspectDeviceId";
									var arrOptions = json[0].res.split(",");
									var obj=$(countyName);
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									
									if(con==1){
										var inspectDeviceId = '${modifyForm.resourceId}';
										if(inspectDeviceId!=''){
											document.getElementById("inspectDeviceId").value = inspectDeviceId;
										}	
									}
									
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
			}
		}	
		
</script>

<html:form action="/modifys.do?method=save" styleId="modifyForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/netresource/modify/config/applicationResource-modify">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="modify.form.heading"/></div>
    </caption>

    <tr> 
        <td class="label">
            <font color='red'> * </font><fmt:message key="modify.modifyType" />
        </td>
        <td class="content">
			<select name="modifyType" id="modifyType"
				alt="allowBlank:false,vtext:'请选资源变更类型'" onchange="modifyTypes(0)">
				<option value=""> ---- 请选择变更类型 ---- </option>
				<option value="1">坐标变更</option>
				<option value="2">资源新增</option>
				<option value="3">资源修改</option>
				<option value="4">资源删除</option>
			</select>
        </td>
      
        <td class="label">
            <font color='red'> * </font><fmt:message key="modify.resourceType" />
        </td>
        <td class="content">
        	<select name="resourceType" id="resourceType"
				alt="allowBlank:false,vtext:'请选资源类型'" onchange="resourceTypes(0)">
				<option value=""> ---- 请选择资源类型 ---- </option>
				<option value="1">基站</option>
				<option value="2">线路</option>
				<option value="3">标点</option>
				<option value="4">设备</option>
			</select>
        </td>
    </tr>
    
    <tr>    
        <td class="label">
            <font color='red'> * </font><fmt:message key="modify.resourceId" />
        </td>
        <td class="content">
        	<select name="resourceId" id="resourceId"
				alt="allowBlank:true,vtext:'请选变更资源'"  onchange="changeResource(0)" >
				<option value=""> ---- 请选择变更资源 ---- </option>
			</select>
        </td>
        
        <td class="label">
            <font color='red'> * </font>变更设备
        </td>
        <td class="content">
        	<select name="inspectDeviceId" id="inspectDeviceId"
				alt="allowBlank:true,vtext:'请选变更设备'" >
				<option value=""> ---- 请选择变更设备 ---- </option>
			</select>
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="modify.description" />
        </td>
        <td class="content" colspan='3'>
            <html:textarea property="description" styleId="description"
               styleClass="text medium"  style='width:99%' rows='6'
               alt="allowBlank:false,vtext:'申请描述',maxLength:5000" value="${modifyForm.description}" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="modify.approveUser" />
        </td>
        <td class="content" colspan='3'>
            <html:hidden property="approveUser" styleId="approveUser"
                        alt="allowBlank:false,vtext:'审批人'" value="${modifyForm.approveUser}" />
            <input type="button" class="btn" name="approveUserBtn" value="<fmt:message key="button.select" />" /> 
            <div id="approveUserView" class="viewer-list"></div>
        </td>
   	</tr>
        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" id='submitInput' name='submitInput' />
            <c:if test="${not empty modifyForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${modifyForm.id}" />
<html:hidden property="applyTime" value="${modifyForm.applyTime}" />
<html:hidden property="approveTime" value="${modifyForm.approveTime}" />
<html:hidden property="acceptTime" value="${modifyForm.acceptTime}" />
<html:hidden property="isDeleted" value="${modifyForm.isDeleted}" />
<html:hidden property="applyUser" value="${modifyForm.applyUser}" />
<html:hidden property="applyDept" value="${modifyForm.applyDept}" />
<html:hidden property="approveStatus" value="${modifyForm.approveStatus}" />

</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面
window.onload = function(){
	var err = '${err}';
    if(err!=''){
    	alert(err);
    }
    var modifyType = '${modifyForm.modifyType}';
    var resourceType = '${modifyForm.resourceType}';
    //var 
	if(modifyType!='' && modifyType!='0'){
 		document.getElementById("modifyType").value = modifyType;
	}
	if(resourceType!='' && resourceType!='0'){
 		document.getElementById("resourceType").value = resourceType;
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>