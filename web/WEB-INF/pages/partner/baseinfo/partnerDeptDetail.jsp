<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
	window.onload = function() {
		var proId = '${partnerDeptForm.id}';
		var operType = '${operType}';
		if (proId != '' && operType == 'save') {
			parent.setproId(proId);
		}
	}
	var subRetutn = false;
	function sub() {
		if (subRetutn) {
			if (v.check()) {
				$("partnerDeptForm").submit();
			}
		} else {
			alert("大单位类型为必填项");
			return false;
		}
	}
</script>
<html:form action="/partnerDepts.do?method=save"
	styleId="partnerDeptForm" method="post">

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
				${partnerDeptForm.name}
			</td>
		</tr>
		<tr>
			<td class="label">
				组织编码
			</td>
			<td class="content">
				${partnerDeptForm.organizationNo}
			</td>
			<td class="label">
				是否为公司
			</td>
			<td class="content">
				<c:if test="${partnerDeptForm.ifCompany=='yes'}">
			是
			</c:if>
				<c:if test="${partnerDeptForm.ifCompany=='no'}">
			否
			</c:if>

			</td>
		</tr>
		<c:if test="${partnerDeptForm.ifCompany=='yes'}">
			<tr>
				<td class="label">
					组织性质
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerDeptForm.type}"
						beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">
					法人
				</td>
				<td class="content">
					${partnerDeptForm.manager}
				</td>
			</tr>
			<tr>
			<td class="label">
					入围级别
				</td>
				<td class="content">

					<eoms:id2nameDB id="${partnerDeptForm.selectedLevel}"
						beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">
					注册日期
				</td>
				<td class="content">
					${partnerDeptForm.registerTime}
				</td>
			</tr>
			<tr>
				<td class="label">
					货币种类
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerDeptForm.kindCurrencies}"
						beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">
					注册资金（万）
				</td>
				<td class="content">
					${partnerDeptForm.fund}
				</td>
			</tr>

		</c:if>
		<tr>
			<td class="label">
				所属地市
			</td>
			<td class="content">
				${partnerDeptForm.areaName}
			</td>
			<td class="label">
				联系电话
			</td>
			<td class="content">
				${partnerDeptForm.phone}
			</td>
		</tr>
		<tr>
			<td class="label">
				邮箱
			</td>
			<td class="content">
				${partnerDeptForm.email}
			</td>

			<td class="label">
				传真
			</td>
			<td class="content">
				${partnerDeptForm.fax}
			</td>
		</tr>

		<tr>
			<td class="label">
				联系人
			</td>
			<td class="content">
				${partnerDeptForm.contactor}
			</td>

			<td class="label">
				邮编
			</td>
			<td class="content">
				${partnerDeptForm.zip}
			</td>
		</tr>
		<tr>
			<td class="label">
				专业
			</td>
			<td class="content" colspan="3">
				<c:forEach items="${specialtyList}" var="dictBigType">
					<c:if test="${dictBigType.dictRemark=='isTrue'}">
				${dictBigType.dictName};
			</c:if>
				</c:forEach>
			</td>
		</tr>
		<c:if test="${partnerList!=null}">
			<tr>
				<td class="label">
					所属公司
				</td>
				<td class="content" colspan="3">
					<eoms:id2nameDB id="${partnerDeptForm.interfaceHeadId}"
						beanId="partnerDeptDao" />
				</td>
			</tr>
		</c:if>
		<html:hidden property="deptMagId" value="${partnerDeptForm.deptMagId}" />
		<html:hidden property="interfaceHeadId"
			value="${partnerDeptForm.interfaceHeadId}" />
		<tr>
			<td class="label">
				公司地址
			</td>
			<td class="content" colspan="3">
				${partnerDeptForm.address}
			</td>
		</tr>
		<tr>
			<td class="label">
				简介
			</td>
			<td class="content" colspan="3">
				${partnerDeptForm.companySynopses}
			</td>
		</tr>
	<c:if test="${qualifyConfig=='1'}">
	</table>
		<fieldset>
		<legend >
			<b>组织资质信息</b>
		</legend>
			<table class="formTable">
				<tr>
					<td align="center" class="label">
						系统编号
					</td>
					<td align="center" class="label">
						公司资质名称
					</td>
					<td align="center" class="label">
						资质级别
					</td>
					<td align="center" class="label">
						发证机关
					</td>
					<td align="center" class="label">
						资质截止日期
					</td>
				</tr>
				<c:if test="${!empty list}">
					<c:forEach items="${list}" var="q">
						<tr>
							<td  align="center" >
								${q.sysno}
							</td>
							<td  align="center" >
								${q.qualifyName}
							</td>
							<td align="center">
								${q.qualifyLevel}
							</td>
							<td align="center">
								${q.issueAuthority}
							</td>
							<td   align="center">
								${q.outOfDate}
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
	</fieldset>
	<table  class="formTable">
	</c:if>
		<tr>
			<td class="label">
				附件
			</td>
			<td class="content" colspan="3">
				<eoms:attachment name="partnerDeptForm" property="accessory"
					scope="request" idField="accessory" appCode="partnerBaseinfo"
					viewFlag="Y" />
			</td>
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="3">
				<pre>${partnerDeptForm.remark}</pre>
			</td>
		</tr>
	</table>
	<table>
			<tr>
				<td>
					<c:if test="${hasRightForAdd=='1'}"><!--是否有修改权限-->
						<c:if test="${empty searchInto}"><!-- 数据钻取时过滤编辑功能-->
							<input type="button" class="btn" value="编辑" onclick="var url='${app}/partner/baseinfo/partnerDepts.do?method=modifyCompanyBaseInfo&proId=${partnerDeptForm.id}&isPartner=${isPartner}';location.href=url" />
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
	<html:hidden property="areaId" value="${partnerDeptForm.areaId}" />
	<html:hidden property="id" value="${partnerDeptForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>