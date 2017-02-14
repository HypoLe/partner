<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

window.onload = function(){
    var personCardNo = '${partnerUserForm.personCardNo}';
	var operType = '${operType}';
	if(personCardNo != '' && operType == 'save'){
	    parent.setPersonCardNo(personCardNo);
    }
}
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}
	window.frameElement.height = 900;
	var isEdit = '${isEdit}';
	if(isEdit!='add'){
		document.body.style.overflow = "hidden";
	}       
</script>
<script language="javascript" for="document" event="onkeydown">
    if (event.keyCode == 13)
    {
        isEmail(document.getElementById('email').value);
    }
</script>

<html:form action="/partnerUsers.do?method=save"
	styleId="partnerUserForm" method="post" onsubmit="">
	
	<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
		<html:hidden property="treeNodeId" />
		<table class="formTable">
			<caption>
			<div class="header center"><fmt:message
				key="partnerUser.form.heading" /></div>
			</caption>
			<tr>
				<td class="label"><fmt:message key="partnerUser.name" />
				</td>
				<td class="content">${partnerUserForm.name}</td>
				<td rowspan="4"  class="label" valign="middle" align="center"><fmt:message key="partnerUser.photo" /></td>
				<td rowspan="4"  valign="bottom">
				<html:hidden property="photo" styleId="photo" value="${partnerUserForm.photo}" /> 
				<html:hidden property="accessory" styleId="accessory" value="${partnerUserForm.accessory}" />
				<c:if test="${not empty partnerUserForm.photo}">
					<!-- 修改时 -->
					<div id="imgdiv"><img id="imgUser" name="imgUser" src="${partnerUserForm.photo}" border="0" width="130" height="180">
					<br>
					</div>
					<iframe id="upframe" name="upframe" class="uploadframe"
						frameborder="0" style="display:none;height:50pt;width:100%"
						src="${app }/partner/baseinfo/partnerUsers.do?method=toUploadphotoPage"
						scrolling="no"></iframe>
				</c:if> 
				<c:if test="${empty partnerUserForm.photo}">
					<img id="imgUserd" name="imgUserd" src="${app }/images/head/man.gif" border="0" width="130" height="180">
		  		 </c:if>
		  		 </td>
			</tr>
			<tr>
				<td class="label"><fmt:message key="partnerUser.userId" />
				</td>
					<td class="content">${partnerUserForm.userId}</td>
			</tr>
			<tr>
				<td class="label"><fmt:message key="partnerUser.personCardNo" />
				</td>
				<td class="content">${partnerUserForm.personCardNo}</td>
			</tr>
			<tr>
				<td class="label"><fmt:message key="partnerUser.deptName" />
				</td>
				<td class="content">${partnerUserForm.deptName} <html:hidden
					property="deptId" /></td>
			</tr>
			<tr>
				<td class="label">员工编码
				</td>
				<td class="content">${partnerUserForm.userNo}</td>
				<td class="label"><fmt:message key="partnerUser.areaName" />
				</td>
				<td class="content">${partnerUserForm.areaName} <html:hidden
					property="areaId" styleId="areaId" /></td>
			</tr>
			<tr>
				<td class="label">籍贯
				</td>
				<td class="content">${partnerUserForm.nativePlace}</td>
				<td class="label">民族
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerUserForm.nationality}" beanId="ItawSystemDictTypeDao"/>
				</td>
			</tr>
			<tr>
				<td class="label">年龄
				</td>
				<td class="content">${partnerUserForm.age}</td>
				<td class="label">集团短号
				</td>
				<td class="content">${partnerUserForm.groupPhone}</td>
			</tr>
			<tr>
				<td class="label">经度
				</td>
				<td class="content">${partnerUserForm.longtitude}</td>
				<td class="label">纬度
				</td>
				<td class="content">${partnerUserForm.latitude}</td>
			</tr>
			<tr>
				<td class="label">毕业院校
				</td>
				<td class="content">${partnerUserForm.graduateSchool}</td>
				<td class="label">所学专业
				</td>
				<td class="content">${partnerUserForm.learnSpecialty}</td>
			</tr>
			<tr>
				<td class="label"><fmt:message key="partnerUser.mobilePhone" />
				</td>
				<td class="content">${partnerUserForm.mobilePhone}</td>
				<td class="label">Email
				</td>
				<td class="content">${partnerUserForm.email}</td>
			</tr>
			<tr>
				<td class="label"><fmt:message key="partnerUser.sex" />
				</td>
				<td class="content"><eoms:id2nameDB id="${partnerUserForm.sex}"
					beanId="ItawSystemDictTypeDao" /></td>

				<td class="label"><fmt:message key="partnerUser.birthdey" />
				</td>
				<td class="content">${partnerUserForm.birthdey}</td>
			</tr>
			<tr>
				<td class="label"><fmt:message key="partnerUser.diploma" />
				</td>
				<td class="content"><eoms:id2nameDB
					id="${partnerUserForm.diploma}" beanId="ItawSystemDictTypeDao" /></td>

				<td class="label"><fmt:message key="partnerUser.workTime" />
				</td>
				<td class="content">${partnerUserForm.workTime}</td>
			</tr>
			<tr>
				<td class="label">在职状态
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerUserForm.postState}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">黑名单标示
				</td>
				<td class="content">
					<c:if test="${partnerUserForm.blacklist=='1'}">
					是
					</c:if>
					<c:if test="${partnerUserForm.blacklist=='0'}">
					否
					</c:if>
				</td>
			</tr>
			<c:if test="${partnerUserForm.postState=='1240903'}">
				<tr>
					<td class="label">离职时间
					</td>
					<td class="content" >${partnerUserForm.leavaTime}
					</td>
					<td class="label">离职原因
					</td>
					<td class="content" >${partnerUserForm.leavaReason}
					</td>
				</tr>
			</c:if>
			<tr>
			<td class="label">
				附件
			</td>
			<td class="content" colspan="3">
				<eoms:attachment name="partnerUserForm" property="identificationAccessory"
					scope="request" idField="identificationAccessory" appCode="partnerBaseinfo"
					viewFlag="Y" />
			</td>
		</tr>
		<tr>
			<td class="label"><fmt:message key="partnerUser.remark" /></td>
			<td class="content" colspan="3">
				<pre>${partnerUserForm.remark}</pre>
			</td>
		</tr>
		</table>
	</fmt:bundle>
	<table>
		<tr>
			<td>
			<c:if test="${empty searchInto}"><!-- 数据钻取时或者arcGis地图显示详情时过滤编辑功能-->
				<c:if test="${hasRightForDel==1}">
					<input type="button" class="btn" value="编辑"
					onclick="var url='${app}/partner/baseinfo/partnerUsers.do?method=toEditCompanyUser&id=${partnerUserForm.id}&type=${type}&isPartner=${isPartner}';location.href=url" />
				</c:if>
			</c:if>
			<!-- 
			<input type="button" class="btn" value="批量导入"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=toXls&treeNodeId=${partnerUserForm.treeNodeId}';
						location.href=url}" />
		 --></td>
		</tr>
	</table>
	<html:hidden property="id" value="${partnerUserForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
