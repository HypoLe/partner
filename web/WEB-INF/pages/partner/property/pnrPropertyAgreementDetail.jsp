<%@page import="com.boco.eoms.deviceManagement.common.utils.CommonUtils"%>
<%@page import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style>
	.redStar {
		color:red;
	}
</style>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<div align="center"><b>物业合同-详情页面</div><br><br/>
<table id="sheet" class="formTable">
				<tr>
					<td class="label">
					 	合同编码
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.agreementNo}
					</td>
					<td class="label">
					 	所属站点
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.site}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	合同名称
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.agreementName}
					</td>
					<td class="label">
					 	甲方
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.parta}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	乙方
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.partb}
					</td>
					<td class="label">
					 	合同类型
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrPropertyAgreement.agreementType}" beanId="ItawSystemDictTypeDao"/>
					</td>
				</tr>
				<tr>
					<td class="label">
					 	签订日期
					</td>
					<td class="content" colspan="3">
						<fmt:formatDate value="${pnrPropertyAgreement.signDate}" pattern="yyyy-MM-dd" />
					</td>
					<td class="label">
					 	截止日期
					</td>
					<td class="content" colspan="3">
						<fmt:formatDate value="${pnrPropertyAgreement.endDate}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td class="label">
					 	付款周期
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrPropertyAgreement.payCycle}" beanId="IItawSystemDictTypeDao"/>
					</td>
					<td class="label">
					 	所属行政区域
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrPropertyAgreement.distirct}" beanId="tawSystemAreaDao"/>
					</td>
				</tr>
				<tr>
					<td class="label">
					 	甲方签订人
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.partaSigningPerson}
					</td>
					<td class="label">
					 	甲方签订人电话
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.partaSigningPersonTel}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	乙方签订人
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.partbSigningPerson}
					</td>
					<td class="label">
					 	乙方签订人电话
					</td>
					<td class="content" colspan="3">
							${pnrPropertyAgreement.partbSigningPersonTel}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	合同金额(单位:元)
					</td>
					<td class="content" colspan="7">
							${pnrPropertyAgreement.agreementAmount}
					</td>
				<tr>
				<tr>
					<td class="label">
					 	到期提醒对象
					</td>
					<td class="content" colspan="7">
						<c:forEach var="userid" items="${userids}" varStatus="s1" >
							<eoms:id2nameDB id="${userid}" beanId="tawSystemUserDao"/>
							<c:if test="${!s1.last}">,
							</c:if>
						</c:forEach>
						<c:forEach var="phone" items="${phones}" varStatus="s2">,${phone}
						</c:forEach>
					</td>
				</tr>
				<tr rowspan="2">
					<td class="label">
						申请附件&nbsp;
					</td>
					<td class="content" colspan="7">
						<eoms:attachment name="pnrPropertyAgreement" property="attachment" scope="request" 
						idField="attachment" appCode="contract" viewFlag="Y"/> 
					</td>
			</tr>
			<tr >
	  			<td class="label">
						备注&nbsp;
				</td>
				<td colspan="7">
					<pre>${pnrPropertyAgreement.remark}</pre> 
				</td>
	  		</tr>
</table>
<%@ include file="/common/footer_eoms.jsp"%>