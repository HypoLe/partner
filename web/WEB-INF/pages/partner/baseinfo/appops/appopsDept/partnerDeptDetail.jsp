<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
	window.onload = function() {
		var proId = '${iPnrPartnerAppOpsDeptForm.id}';
		var operType = '${operType}';
		if (proId != '' && operType == 'save') {
			parent.setproId(proId);
		}
	}
	var subRetutn = false;
	function sub() {
		if (subRetutn) {
			if (v.check()) {
				$("iPnrPartnerAppOpsDeptForm").submit();
			}
		} else {
			alert("大单位类型为必填项");
			return false;
		}
	}
</script>
<html:form action="/partnerAppOpsDept.do?method=save"
	styleId="iPnrPartnerAppOpsDeptForm" method="post">

	<input type="hidden" name="parentNodeId" value="${parentNodeId }">
	<html:hidden property="treeNodeId" />
	<html:hidden property="treeId" />


	<table class="formTable">
		<caption>
			<div class="header center">
				组织基本信息
			</div>
		</caption>

		<tr>
			<td class="label">
				组织名称
			</td>
			<td class="content" colspan="3">
				${iPnrPartnerAppOpsDeptForm.name}
			</td>
		</tr>
		<tr>
			<td class="label" >
				组织编码
			</td>
			<td class="content" colspan="3">
				${iPnrPartnerAppOpsDeptForm.organizationNo}
			</td>
			
		</tr>
		
		<tr>
			<td class="label">
				所属地市
			</td>
			<td class="content">
				${iPnrPartnerAppOpsDeptForm.areaName}
			</td>
			<td class="label">
				联系电话
			</td>
			<td class="content">
				${iPnrPartnerAppOpsDeptForm.phone}
			</td>
		</tr>
		<tr>
			<td class="label">
				联系人
			</td>
			<td class="content">
				${iPnrPartnerAppOpsDeptForm.contactor}
			</td>

			<td class="label">
				组织地址
			</td>
			<td class="content" colspan="3">
				${iPnrPartnerAppOpsDeptForm.address}
			</td>
		</tr>
		<html:hidden property="deptMagId" value="${iPnrPartnerAppOpsDeptForm.deptMagId}" />
		<html:hidden property="interfaceHeadId"
			value="${iPnrPartnerAppOpsDeptForm.interfaceHeadId}" />
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="3">
				<pre>${iPnrPartnerAppOpsDeptForm.remark}</pre>
			</td>
		</tr>
	</table>
	<table>
			<tr>
				<td>
					<c:if test="${hasRightForAdd=='1'}"><!--是否有修改权限-->
						<c:if test="${empty searchInto}"><!-- 数据钻取时过滤编辑功能-->
							<input type="button" class="btn" value="编辑" onclick="var url='${app}/partner/appops/partnerAppOpsDept.do?method=modifyCompanyBaseInfo&proId=${iPnrPartnerAppOpsDeptForm.id}&isPartner=${isPartner}';location.href=url" />
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
	<html:hidden property="areaId" value="${iPnrPartnerAppOpsDeptForm.areaId}" />
	<html:hidden property="id" value="${iPnrPartnerAppOpsDeptForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>