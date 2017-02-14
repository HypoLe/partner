<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
<script type="text/javascript" >
function validate(){
	var flag = true;
	var runturnalert = '';
	var remark = document.getElementById("remark").value;
		if(remark=="" || remark == null){
			flag = false;
			runturnalert= runturnalert +'|审批意见|';
		}
		if(false==flag){
				alert(runturnalert+'请核对后再试.');
		}
	return flag;
}
	
</script>
</head>
<body>
	<body>
		
		<form action="storeAllot.do?method=audit" method="post">
		<input type="hidden" name="id" value="${storeAllot.id}" />
		<input type="hidden" name="storeExamineStatus" value="${storeAllot.storeExamineStatus}" />
		<input type="hidden" name="shenpiren" value="${username}" />
			<div>
		<p><center><span style="font-size:20px">审核</span></center></p>
</div>
<br/>
<table class="formTable">
	<tr>
		<td class="label">审核意见</td>
		<td colspan="3">
			<textarea style="border: #FF0000 1px solid;" name="remark" id="remark" class="comments" rows="4" cols="120"></textarea>
		</td>
	</tr>
	
	<table class="formTable">
	<tr>
		<td >
			&nbsp;<input type="submit" value="确定" onclick="return validate()"/>
			&nbsp;<input type="button" onClick="javascript:window.history.go(-1)" value="返回" />
		</td>

	</tr>
	
</table>
<br />
		<div style="border-top:1px solid #000;width:100%;height:1px;"> </div>
<br/>
<br/>

<table class="formTable">
			<tr>
		<td class="label">
			调拨日期
		</td>
		<td class="content">
			<input type="text" readonly="true" value="${storeAllot.storeBillingDate}" onClick="popUpCalendar(this, this,null,null,null,true,-1);"/>
		</td>
		<td class="label">
			原始单号
		</td>
		<td class="content">								
			<input readonly="true" class="text" type="text" value="${storeAllot.storeOriginalNum}"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			调出仓库
		</td>
		<td class="content">
			<select  disabled="true" >
			<option >${storeAllot.storeSname}</option>
			</select>
		</td>

		<td class="label">
			调入仓库
		</td>
		<td class="content">								
			<input disabled="true" class="text"  value="${storeAllot.inputStoreName}"/>		
		</td>
	</tr>

	<tr>
		<td class="label">
			经办人
		</td>
		<td class="content">
			<input readonly="true" class="text" type="text" value="${storeAllot.storeRequisitioner}" />
		</td>

		<td class="label">
			制单人			
		</td>
		<td class="content">
			<input readonly="true" class="text" type="text" value="${storeAllot.storeMakeBillPeople}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
		    <input readonly="true" type="text" value="${storeAllot.storeRemark}" class="text max" />
		</td>
	</tr>
		</table>
			
		<display:table name="list" cellspacing="0" cellpadding="0" id="list"
			sort="list" class="table" >
			<display:column property="encode" sortable="true"
				headerClass="sortable" title="材料编码" />
			<display:column property="materialName" sortable="true"
				headerClass="sortable" title="材料名称" />
			<display:column property="specification" sortable="true"
				headerClass="sortable" title="材料规格" />
			<display:column property="unit" sortable="true"
				headerClass="sortable" title="单位" />
			<display:column property="materialAmount" sortable="true"
				headerClass="sortable" title="数量" />
			<display:column property="materialPrice" sortable="true"
				headerClass="sortable" title="单价" />
			
		</display:table>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>