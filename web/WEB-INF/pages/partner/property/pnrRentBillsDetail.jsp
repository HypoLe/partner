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

<div align="center"><b>租赁费用-详情页面</div><br><br/>
<table id="sheet" class="formTable">
				<tr>
					<td class="label">
					 	合同编码<span class="redStar">*</span>
					</td>
					<td class="content" >
							${pnrRentBills.relatedAgreementNo}
					</td>
					<td class="label">
					 	合同名称<span class="redStar">*</span>
					</td>
					<td class="content" >
							${pnrRentBills.relatedAgreementName}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	合同类型
					</td>
					<td class="content" >
							<eoms:id2nameDB id="${pnrRentBills.relatedAgreementType}" beanId="ItawSystemDictTypeDao"/>
					</td>
					<td class="label">
					 	所属行政区域<span class="redStar">*</span>
					</td>
					<td class="content" >
							<eoms:id2nameDB id="${pnrRentBills.relatedDistrict}" beanId="tawSystemAreaDao"/>
					</td>
				</tr>
				<tr>
					<td class="label">
					 	甲方
					</td>
					<td class="content" >
							${pnrRentBills.relatedParta}
					</td>
					<td class="label">
					 	乙方<span class="redStar">*</span>
					</td>
					<td class="content" >
							${pnrRentBills.relatedPartb}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	应付金额（单位：元）<span class="redStar">*</span>
					</td>
					<td class="content" >
							${pnrRentBills.mustPayMoney}
					</td>
					<td class="label">
					 	已付金额（单位：元）<span class="redStar">*</span>
					</td>
					<td class="content" >
							${pnrRentBills.paidMoney}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	未付金额（单位：元）<span class="redStar">*</span>
					</td>
					<td class="content" >
							${pnrRentBills.unpaidMoney}
					</td>
					<td class="label">
					 	付款方式<span class="redStar">*</span>
					</td>
					<td class="content" >
							<eoms:id2nameDB id="${pnrRentBills.payWay}" beanId="ItawSystemDictTypeDao"/>
					</td>
				</tr>
				<tr>
					<td class="label">
					 	付款周期<span class="redStar">*</span>
					</td>
					<td class="content" >
							<eoms:id2nameDB id="${pnrRentBills.payCircle}" beanId="ItawSystemDictTypeDao"/>
					</td>
					<td class="label">
					 	经办人<span class="redStar">*</span>
					</td>
					<td class="content" >
							${pnrRentBills.manager}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	结算日期<span class="redStar">*</span>
					</td>
					<td class="content" colspan="3">
						<fmt:formatDate value="${pnrRentBills.settlementDate}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td class="label">
					 	结算时间段<span class="redStar">*</span>
					</td>
					<td class="content"  colspan="3">
						<fmt:formatDate value="${pnrRentBills.settlementTimeSectStart}" pattern="yyyy-MM-dd" />
						<span class="text">至</span>
						<fmt:formatDate value="${pnrRentBills.settlementTimeSectEnd}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td class="label">
					 	到期提醒对象
					</td>
					<td class="content" colspan="7">
						<c:forEach var="userid" items="${userids}" varStatus="s1">
							<eoms:id2nameDB id="${userid}" beanId="tawSystemUserDao"/>
							<c:if test="${!s1.last}">,
							</c:if>
						</c:forEach>
						<c:forEach var="phone" items="${phones}" >,${phone}
						</c:forEach>
					</td>
				</tr>
				<tr rowspan="2">
					<td class="label">
						附件列表&nbsp;*
					</td>
					<td class="content" colspan="7">
						<eoms:attachment name="pnrRentBills" property="attachment" scope="request" 
						idField="attachment" appCode="contract" viewFlag="Y"/> 
					</td>
			</tr>
			<tr >
	  			<td class="label">
						备注&nbsp;
				</td>
				<td colspan="7">
					<pre>${pnrRentBills.remark}</pre>
				</td>
	  		</tr>
	</table>

<%@ include file="/common/footer_eoms.jsp"%>