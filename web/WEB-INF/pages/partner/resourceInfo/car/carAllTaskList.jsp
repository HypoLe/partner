<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script type="text/javascript">
	var myjs=jQuery.noConflict();
	function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}
function res(){
		var formElement=document.getElementById("carForm")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
	         if(inputs[i].type == 'hidden'){
	              inputs[i].value = '';
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
	}
</script>
<table class="formTable">
  	<caption><div class="header center">${carNumber}&nbsp;&nbsp;使用历史记录信息</div></caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;" class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif"  align="absmiddle"  style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/partner/resourceInfo/car.do?method=getCarAllTaskList&&carNumber=${carNumber}" method="post" id="carForm">
		<table id="sheet" class="listTable">
			<tr>
				<td class="label">申请时间</td>
				<td class="content">
					<input id="t1.apply_timeDateGreaterOrEqual" class="Wdate text" name="t1.apply_timeDateGreaterOrEqual" value="${apply_timeDateGreaterOrEqual}"
					readonly="readonly"  type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'t1.apply_timeDateLessOrEqual\')||\'%y-%M-%d\'}'})"/> 
					&nbsp;&nbsp;至&nbsp;&nbsp;
					<input id="t1.apply_timeDateLessOrEqual" class="Wdate text" name="t1.apply_timeDateLessOrEqual" value="${apply_timeDateLessOrEqual}"
					readonly="readonly" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'t1.apply_timeDateGreaterOrEqual\')}',maxDate:'\'%y-%M-%d\''})"/>
				</td>
				<td class="label">归还时间</td>
				<td class="content">
					<input id="t1.back_timeDateGreaterOrEqual" class="Wdate text" name="t1.back_timeDateGreaterOrEqual" value="${back_timeDateGreaterOrEqual}"
					readonly="readonly"  type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'t1.back_timeDateLessOrEqual\')||\'%y-%M-%d\'}'})"/> 
					&nbsp;&nbsp;至&nbsp;&nbsp;
					<input id="t1.back_timeDateLessOrEqual" class="Wdate text" name="t1.back_timeDateLessOrEqual" value="${back_timeDateLessOrEqual}"
					readonly="readonly" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'t1.back_timeDateGreaterOrEqual\')}',maxDate:'\'%y-%M-%d\''})"/>
				</td>
			</tr>
			<tr>
				<td class="label">申请人</td>
				<td class="content">
					<input type="text"  class="text"  name="applyUserName" id="applyUserName" value="${applyUserName}" readonly="readonly"/>
			   		<input type="hidden" name="t1.apply_userStringEqual" id="t1.apply_userStringEqual"  value="${apply_userStringEqual}"/>
					<eoms:xbox id="applyUserName" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user"  
					rootId="2" rootText="用户树"  valueField="t1.apply_userStringEqual" handler="applyUserName" 
					textField="applyUserName" checktype="user" single="true" />
				</td>
				<td class="label">申请人所在部门</td>
				<td class="content">
					<input type="text" class="max text" name="provName" id="provName" value="${provName}" readonly="readonly" />
						<input name="t1.apply_user_deptStringEqual" id="t1.apply_user_deptStringEqual" value="${apply_user_deptStringEqual }" type="hidden" />
						<eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp"
							rootId="" rootText="公司树" valueField="t1.apply_user_deptStringEqual" handler="provName"
							textField="provName" checktype="dept" single="true" />
				</td>
			</tr>
			<tr>
				<td class="label">审核人</td>
				<td class="content">
					<input type="text"  class="text"  name="approveUserName" id="approveUserName" value="${approveUserName}" readonly="readonly"/>
			   		<input type="hidden" name="t1.approve_userStringEqual" id="t1.approve_userStringEqual"  value="${approve_userStringEqual}"/>
					<eoms:xbox id="applyUserName1" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user"  
					rootId="2" rootText="用户树"  valueField="t1.approve_userStringEqual" handler="approveUserName" 
					textField="approveUserName" checktype="user" single="true" />
				</td>
				<td class="label">审核人所在部门</td>
				<td class="content">
					<input type="text" class="max text" name="provName1" id="provName1" value="${provName1}" readonly="readonly" />
						<input name="t1.approve_user_deptStringEqual" id="t1.approve_user_deptStringEqual" value="${approve_user_deptStringEqual }" type="hidden" />
						<eoms:xbox id="provTree1" dataUrl="${app}/xtree.do?method=userFromComp"
							rootId="" rootText="公司树" valueField="t1.approve_user_deptStringEqual" handler="provName1"
							textField="provName1" checktype="dept" single="true" />
				</td>
			</tr>
			<tr>
				<td class="label">任务类型</td>
				<td class="content">
					<eoms:comboBox name="t2.task_typeStringEqual" id="t2.task_typeStringEqual" defaultValue="${task_typeStringEqual}" 
					styleClass="select" initDicId="11233" />
				</td>
				<td class="label">任务名称</td>
				<td class="content">
					<input type="text" class="max text"  id="t2.task_nameStringLike" name="t2.task_nameStringLike" value="${task_nameStringLike}">
				</td>
			</tr>
		</table>		
		<input type="submit" value="查询" class="btn">
		<input type="button" value="重置" class="btn" onclick="res();">
	</form>
	</div>
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="car.do?method=getCarAllTaskList" 
		partialList="true" size="${size}">
		<display:column title="申请时间">
			<fmt:formatDate value="${list.apply_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="归还时间">
			<fmt:formatDate value="${list.back_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="任务类型">
			<eoms:id2nameDB id="${list.task_type}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column title="任务名称">${list.task_name}
		</display:column>
		<display:column title="申请人">
			<eoms:id2nameDB id="${list.apply_user}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column title="申请人所在部门">
			<eoms:id2nameDB id="${list.apply_user_dept}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column title="审核人">
			<eoms:id2nameDB id="${list.approve_user}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column title="审核人所在部门">
			<eoms:id2nameDB id="${list.approve_user_dept}" beanId="tawSystemDeptDao" />
		</display:column>
	</display:table>
</logic:present>
	</br>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
