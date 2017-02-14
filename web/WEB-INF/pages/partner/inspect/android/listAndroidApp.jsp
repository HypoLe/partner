<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
jq(function(){
	jq("#addAndroidApp").click(function(){
		window.location='${app}/android/androidAppManagerAction.do?method=goToEditAndroidApp';
	})
})



function changeDownloadStatus(id){
	var tempId = "downloadStatus_"+id;
	
	var value = jq("input[name="+tempId+"]:checked").val();
	
	if(confirm("确定要修改吗?")){
		Ext.Ajax.request({
		    url:'androidAppManagerAction.do?method=editAndroidApp',
		    params: {'id': id,'type':'modifyDownloadStatus','value':value},
		    success: function(response){
		    	 if (response.responseText=='success'){
		    	 	alert('修改数据成功');  
      			 }else{
		    	 	alert('修改数据失败'); 
      			 }
		    },
	     	failure: function(response) {
                    alert('删除数据失败'); 
            }
			});
	}
}
function deleteApp(id){
	var tempId = "downloadStatus_"+id;
	
	var value = jq("input[name="+tempId+"]:checked").val();
	
	if(confirm("确定要删除吗?")){
		Ext.Ajax.request({
		    url:'androidAppManagerAction.do?method=deleteAndroidApp',
		    params: {'id': id},
		    success: function(response){
		    	 if (response.responseText=='success'){
		    	 	alert('删除数据成功');  
		    	 	window.location.reload();
      			 }else{
		    	 	alert('删除数据失败'); 
      			 }
		    },
	     	failure: function(response) {
                    alert('删除数据失败'); 
            }
			});
	}
}

</script>
<logic:present name="androidAppInfoList" scope="request">
	<display:table name="androidAppInfoList" cellspacing="0"
		cellpadding="0" id="androidAppInfoList" pagesize="${pagesize}"
		class="table androidAppInfoList" export="false"
		requestURI="androidAppManagerAction.do" sort="list" partialList="true"
		size="${resultSize}">
		<display:column sortable="true" headerClass="sortable" title="应用名称">
			<a href="${app}/android/androidAppManagerAction.do?method=goToEditAndroidApp&id=${androidAppInfoList.id }">${androidAppInfoList.appName}</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="添加人员">
			<eoms:id2nameDB beanId="tawSystemUserDao"
				id="${androidAppInfoList.addUser}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="添加时间">
			${androidAppInfoList.addTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="版本名称">
		${androidAppInfoList.versionName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="版本号">
		${androidAppInfoList.versionCode}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="文件大小">
		${androidAppInfoList.fileSize}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="是否发布">

			<c:choose>
				<c:when test="${androidAppInfoList.downloadStatus == 0}">
					<input type="radio" name="downloadStatus_${androidAppInfoList.id }"
						value="0" checked>发布
				<input type="radio" name="downloadStatus_${androidAppInfoList.id }"
						value="1">暂停发布  
			</c:when>
				<c:when test="${androidAppInfoList.downloadStatus == 1}">
					<input type="radio" name="downloadStatus_${androidAppInfoList.id }"
						value="0">发布  
				<input type="radio" name="downloadStatus_${androidAppInfoList.id }"
						value="1" checked>暂停发布  
			</c:when>
			</c:choose>
			&emsp;&emsp;
			<input align="right" type="button"
				onclick="changeDownloadStatus('${androidAppInfoList.id }')"
				value="修改" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="操作">
			<input align="right" type="button"
				onclick="deleteApp('${androidAppInfoList.id }')" value="删除" />
		</display:column>
	</display:table>
</logic:present>

<input type="button" id="addAndroidApp" value="新增应用" />
<%@ include file="/common/footer_eoms.jsp"%>
