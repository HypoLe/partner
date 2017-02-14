<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>


<table id="sheet" class="formTable">
	<tr>
		<caption>
		<div class="header center">线路故障核减详细信息</div>
		</caption>

	</tr>
	<tr>
		<td class="label">故障ID</td>
		<td class="content">${line.faultID}</td>
		<td class="label">故障原因</td>
		<td class="content">${line.faultReason}</td>
	</tr>
	<td class="label">故障ID</td>
	<td class="content">${line.faultID}</td>
	<td class="label">故障原因</td>
	<td class="content">${line.faultReason}</td>
	</tr>
	<td class="label">发生地市</td>
	<td class="content">${line.area}</td>
	<td class="label">线路长度</td>
	<td class="content">${line.lineLength}</td>
	</tr>
	</tr>
	<td class="label">发生地市</td>
	<td class="content"><eoms:id2nameDB id="${line.area}"
		beanId="tawSystemAreaDao" /></td>
	<td class="label">线路长度</td>
	<td class="content">${line.lineLength}</td>
	</tr>
	</tr>
	<td class="label">故障发生时间</td>
	<td class="content">
	<bean:write format="yyyy-MM-dd HH:mm:ss"  name="line" property="startDate" />
	</td>
	<td class="label">故障结束时间</td>
	<td class="content">
	<bean:write format="yyyy-MM-dd HH:mm:ss"  name="line" property="endDate" />
	</td>
	</tr>
	</tr>
	<td class="label">光缆级别</td>
	<td class="content">
	<eoms:id2nameDB id="${line.lineLevel}"
		beanId="ItawSystemDictTypeDao" />
	</td>
	
	<td class="label">报告提交人</td>
	<td class="content">
	<eoms:id2nameDB id="${analy.sendUser}"
		beanId="tawSystemUserDao" />
	</td>
	</tr>
	<c:if test="${analy.countryCMCCUser ne null}">
	<tr>
	<td class="label">县审核人（联通）</td>
	<td class="content">
	<eoms:id2nameDB id="${analy.countryCMCCUser}"
		beanId="tawSystemUserDao" />
	</td>
	</c:if>
	<c:if test="${analy.countryCMCCUser eq null}">
	<tr>
	<td class="label">县审核人（联通）</td>
	<td class="content">
	未指定
	</td>
	</c:if>
	<c:if test="${analy.cicyUser ne null}">
	<td class="label">地市审核人（代维公司）</td>
	<td class="content">
	<eoms:id2nameDB id="${analy.cicyUser}"
		beanId="tawSystemUserDao" />
	</td>
	</c:if>
	<c:if test="${analy.cicyUser eq null}">
	<td class="label">地市审核人（代维公司）</td>
	<td class="content">
	未指定
	</td>
	</c:if>
	
	</tr>
	
	
		<c:if test="${analy.cicyCMCCUser ne null}">
	<tr>
	<td class="label">地市审核人（联通）</td>
	<td class="content">
	<eoms:id2nameDB id="${analy.cicyCMCCUser}"
		beanId="tawSystemUserDao" />
	</td>
	</c:if>
	<c:if test="${analy.cicyCMCCUser eq null}">
	<tr>
	<td class="label">地市审核人（联通）</td>
	<td class="content">
	未指定
	</td>
	</c:if>
		<c:if test="${analy.copyToUser ne null}">
	<td class="label">抄送对象（省联通公司）</td>
	<td class="content">
	<eoms:id2nameDB id="${analy.copyToUser}"
		beanId="tawSystemUserDao" />
	</td>
	</c:if>
	<c:if test="${analy.copyToUser eq null}">
	<tr>
	<td class="label">抄送对象（省联通公司）</td>
	<td >
	<td class="content">
	未指定
	</td>
	</c:if>
	
	</tr>
	<tr>
	<td class="label">分析报告</td>
	<td colspan="3">
	<textarea  name="faultReason" id="faultReason" class="textarea max" readonly="readonly">${analy.analyseText}</textarea></td>
	</tr>
	
	<c:if test="${analy.state eq 6&&analy.adviseText ne null}">
	
	<td class="label">批复意见（省联通）</td>
	<td colspan="3">
	<textarea  name="faultReason" id="faultReason" class="textarea max" readonly="readonly">${analy.adviseText}</textarea></td>
	</tr>
	</c:if>
		<c:if test="${analy.state eq 6&&analy.adviseText eq null}">
	
	<td class="label">批复意见（省联通）</td>
	<td colspan="3">
	<textarea  name="faultReason" id="faultReason" class="textarea max" readonly="readonly" >未填写批复意见</textarea></td>
	</tr>
	</c:if>
	
</table>

<%@ include file="/common/footer_eoms.jsp"%>








