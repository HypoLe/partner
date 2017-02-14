<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style>
	.redStar {
		color:red;
	}
</style>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<table class="formTable">
			<caption>
				<div class="header center">
					巡检组织入围资质表单添加页面
				</div>
			</caption>
</table>
<br>
<br/>

<form action="${app }/pnrOrgFinalistSheet/pnrOrgFinalistSheetAction.do?method=addPnrOrgFinalistSheet" method="post" id="pnrOrgFinalistSheetForm" name="pnrOrgFinalistSheetForm" >
	<input type="hidden" id="id" name="id" value="${pnrOrgFinalistSheet.id }">
	<input type="hidden" id="addUser" name="addUser" value="${pnrOrgFinalistSheet.addUser }">
	<input type="hidden" id="addTime" name="addTime" value="${pnrOrgFinalistSheet.addTime }">
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">
			 	组织名称<span class="redStar">*</span> 
			</td>
			<td class="content" colspan="3">
			<input type="text"  class="max text"  name="orgName" id="orgName"  
					value="${pnrOrgFinalistSheet.orgName}"
					alt="allowBlank:false" readonly="readonly"/>
				<input name="orgId" id="orgId"  value="${pnrOrgFinalistSheet.orgId}" type="hidden" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight"  
							rootId="" rootText="巡检组织树"  valueField="orgId" handler="orgName"
							textField="orgName" checktype="dept" single="true"/>
			</td>
			<%--<td class="label">
				巡检专业<span class="redStar">*</span>
			</td>
			<td class="content">
				<input class="text" type="text" name="speciality" value="${pnrOrgFinalistSheet.speciality}"
					id="speciality" alt="allowBlank:false" />
			</td>
		--%></tr>
		<tr>
			<td class="label">
				专业<span class="redStar">*</span> 
			</td>
			<td class="content" colspan="3">
				<c:forEach items="${specialtyList}" var="dictBigType" >
					<c:if test="${dictBigType.dictRemark=='isTrue'}">
						<input type="checkbox" name="bigType" id="data"
							value="${dictBigType.dictId}" checked='true' />${dictBigType.dictName}
			</c:if>
					<c:if test="${dictBigType.dictRemark!='isTrue'}">
						<input type="checkbox" name="bigType" id="data"
							value="${dictBigType.dictId}" />${dictBigType.dictName}
			</c:if>
				</c:forEach>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				入围时间<span class="redStar">*</span>
			</td>
			<td class="content">
					<input type="text" id="finalistTime" name="finalistTime"  class="text medium"
							onclick="popUpCalendar(this, this,null,null,null,false,-1);"  readonly="true"
							alt="allowBlank:false" value='${pnrOrgFinalistSheet.finalistTime}'/>
				 
			</td>
			
			<td class="label">
				截止时间<span class="redStar">*</span>
			</td>
			<td class="content">
					<input type="text" id="finalistEndTime" name="finalistEndTime" class="text medium"
							onclick="popUpCalendar(this, this,null,null,null,false,-1);" readonly="true"
							alt="allowBlank:false,vtype:'moreThen',link:'finalistTime',vtext:'截止时间必须大于入围时间'" value='${pnrOrgFinalistSheet.finalistEndTime}'/>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" alt="allowBlank:true" name="remark" id="remark" value="${pnrOrgFinalistSheet.remark}">${pnrOrgFinalistSheet.remark}</textarea>
			</td>
		</tr>
		
		</table>
		
		<br/>
		<input type="hidden" name="orgDeptId" value="${pnrOrgFinalistSheet.orgDeptId}"/>
		<input type="hidden" name="isdeleted" value="${pnrOrgFinalistSheet.isdeleted}"/>
		<input type="hidden" name="sysno" value="${pnrOrgFinalistSheet.sysno}"/>
		<input type="hidden" name="areaId" value="${pnrOrgFinalistSheet.areaId}"/>
		<input type="hidden" name="isEffected" value="${pnrOrgFinalistSheet.isEffected}"/>
		<input type="hidden" name="addTime" value="${pnrOrgFinalistSheet.addTime}"/>
		<input type="hidden" name="addUser" value="${pnrOrgFinalistSheet.addUser}"/>
		<input type="submit" class="btn"  value="保存" />
		<input type="reset" class="btn"  value="重置" />
</form>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
        v1 = new eoms.form.Validation({form:'pnrOrgFinalistSheetForm'});
        v1.custom = function() {
        	var d=document.getElementsByName("bigType");
        	var ff=false;
        	for(var i=0;i<d.length;i++){
        		if(d[i].checked){
        			ff=d[i].checked;
        		}
        	}
        	if(ff){
        		return ff;
        	}	
        	else{
        		alert("请选择专业！");
        	}
        }
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>