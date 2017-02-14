<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrActReviewStaffForm'});
});

Ext.onReady(function(){
	//地域树
    new xbox({
		btnId:'cityName',
		treeDataUrl:'${app}/xtree.do?method=area&areaMaxLevel=2',treeRootId:'28',treeRootText:'全省',treeChkMode:'single',treeChkType:'area',
		showChkFldId:'cityName',saveChkFldId:'cityId',returnJSON:false
	});

	//人员树
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
			new xbox({
				btnId:'userName',dlgId:'dlg-audit',dlgTitle:'请选择人员',
				treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'所有人员',treeChkType:'user',
				showChkFldId:'userName',saveChkFldId:'userId'
			});
})
</script>
<script type="text/javascript">
	function validateCityIdUnique(){
	
		//判断地市是否为空
		var tempCityId=document.getElementById("cityId").value;
		if(tempCityId==""||tempCityId==null){
			alert("地市不能为空");
			return false;
		}
		
		//判断人员是否为空
		var tempUserId=document.getElementById("userId").value;
		if(tempUserId==""||tempUserId==null){
			alert("人员不能为空");
			return false;
		}
		
		 //验证手机号码是否已存在，校验唯一性
	  
      var tempId=document.getElementById('id').value;
      var tempCityName=document.getElementById("cityName").value; 
      var urlStr = "${app}/activiti/pnrTransferPrecheck/pnrActReviewStaff.do?method=checkCityId";
      jq.ajax({
			type : "POST",
			url : urlStr, 
			data : {"cityId": tempCityId,"id":tempId},
			success : function(data){ // 回调函数
				if(data==1){
					alert(tempCityName+"的会审人员已存在！");
					return false;
				}else{
			//	alert(document.getElementById("pnrActReviewStaffForm"));
			//	alert(jq("#pnrActReviewStaffForm").submit());
				jq("#pnrActReviewStaffForm").submit();
					//$('form1'搜索).submit();
					
				}
			}	
		});
	}
</script>


<html:form action="/pnrActReviewStaff.do?method=savePnrActReviewStaff"  styleId="pnrActReviewStaffForm" method="post"> 
<html:hidden property="id" value="${pnrActReviewStaff.id}" />

<table class="formTable middle">
	<caption>
		<div class="header center">会审人员配置</div>
	</caption>

	<tr>
		<td class="label">
			地市
		</td>
		<td class="content">
			<html:text property="cityName" styleId="cityName"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${pnrActReviewStaff.cityName}" />
			<html:hidden property="cityId" styleId="cityId" value="${pnrActReviewStaff.cityId}"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			人员
		</td>
		<td class="content">
		<html:text property="userName" styleId="userName"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${pnrActReviewStaff.userName}" />
		<html:hidden property="userId" styleId="userId" value="${pnrActReviewStaff.userId}"/>				
		</td>
	</tr>

	

</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="保存" onclick="validateCityIdUnique();" />
		</td>
	</tr>
</table>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>