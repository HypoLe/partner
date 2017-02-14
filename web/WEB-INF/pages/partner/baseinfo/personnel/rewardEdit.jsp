<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	jQuery.noConflict(); 
	Ext.onReady(function() {
	    v = new eoms.form.Validation({form:'form1'});
	    v2 = new eoms.form.Validation({form:'importForm'});
	    v2.custom = function() {
			var reg = "(.xls)$";
			var file = $("importFile").value;
			var right = file.match(reg);
			if(right == null) {
				alert("请选择Excel文件，目前系统只支持\".xls\"类型的文件!");
				return false;
			} else {
				return true;
			}
		}
	});
	jQuery(function($){  
		var operationType = "${requestScope.operationType}";
		if(operationType=="toedit"){
			$("#form1").attr("action","reward.do?method=edit");
		}
	});
	function saveImport() {
	//表单验证
		if(!v2.check()) {
			return;
		}
		new Ext.form.BasicForm('importForm').submit({
		method : 'post',
			url : "${app}/personnel/reward.do?method=importData",
		  	waitTitle : '请稍后...',
			waitMsg : '正在导入数据,请稍后...',
		    success : function(form, action) {
				alert(action.result.infor);
				jQuery("#importFile").val("");
			},
			failure : function(form, action) {
				alert(action.result.infor);
				jQuery("#importFile").val("");
			}
	    });
	 }
	 function openImport(){
	 	var handler = document.getElementById("openQuery");
		var el = Ext.get('listQueryObject'); 
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开导入界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭导入界面";
		}
	}
	function goBack(){
	 	window.location.href="<%=request.getContextPath()%>/personnel/reward.do?method=search&operationType=tomgr&personCardNo=${personCardNo}"
	 }
</script>

<div align="center"><b>人员奖励信息管理-人员奖励信息编辑</div><br><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer">从Excel导入</span>
</div>
<div id="listQueryObject"		style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="reward.do?method=importData" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
				<fieldset>
					<legend>
						从Excel导入
					</legend>
					<table class="formTable">
						<tr>
							<td class="label">
								选择Excel文件
							</td>
							<td width="35%">
								<input id="importFile" type="file" name="importFile"	class="file" alt="allowBlank:false" />
							</td>
						</tr>
						<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
										onclick="javascript:location.href='${app}/personnel/reward.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<form action="reward.do?method=save" method="post" id="form1" >
	<table id="sheet" class="formTable">
	<c:if  test="${!empty workerid}">  <!-- 综合信息处理页面 -->
			<input type='hidden' id="workername" name="workername" value="${workername }"   />
			<input type='hidden' id="dutyMan" name="workerid" value="${workerid }"   />
			<input type='hidden' id="personCardNo" name="personCardNo" value="${personCardNo}"/>
	</c:if>
	<c:if test="${empty workerid}">
			<tr>
				<td class="label" >
				 员工姓名* 
				</td>
				<td class="content" colspan="7">
					<eoms:xbox id="dutyManTree" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user&isPartner=${isPartner}" rootId="${sessionform.rootPnrDeptId}"
							rootText='代维人员' valueField="dutyMan" handler="intocheckman" textField="intocheckman"
							checktype="user" single="true"></eoms:xbox>
					<input type='text' id='intocheckman' name="workername"    readonly="true" value="${requestScope.reward.workername }"  
						   alt="allowBlank:false,vtext:'员工姓名不能为空！'"  class="text"/>
					<input type='hidden' id="dutyMan" name="workerid" value="${requestScope.reward.workerid }"   />
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="label">
			 曾获奖励名称*
			</td>
			<td class="content" colspan="3">
				<input type="text" class="text" name="reward"   id="memoId"  value="${requestScope.reward.reward }"/>
			</td>
			<td class="label">
			 获奖时间*
			</td>
			<td class="content" colspan="3">
				<input type="text" class="text" name="awardtime"   id="awardtime"   value="${requestScope.reward.awardtime }"
				onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="label">
			 授奖单位*
			</td>
			<td class="content" colspan="7">
				<input type="text" class="text" name="awarddept"   id="awarddept"  value="${requestScope.reward.awarddept }"/>
			</td>
		</tr>
		<tr>
			<td class="label">
			 证书附件 
			</td>
			<td class="content" colspan="7" height="100px">
				<eoms:attachment name="reward" property="imagepath" scope="request" 
				idField="imagepath" appCode="dwInfoFile" alt="allowBlank:true"/> 	
			</td>
		</tr>
		<tr>
			<td class="label"> 
				备注 
			</td>
			<td class="content" colspan="7">
				<textarea class="textarea max" name="memo"  onkeyup="this.value = this.value.slice(0, 180)"
					id="memoId"  >${requestScope.reward.memo}</textarea>
			</td>
		</tr>
		</table>
		<br/>
		
		<input type="hidden" value="${requestScope.reward.id }" name="id">
		<input type="hidden" value="${requestScope.reward.sysno}" name="sysno">
		<input type="hidden" value="${requestScope.reward.adduser}" name="adduser">
		<input type="hidden" value="${requestScope.reward.adddept }" name="adddept">
		<input type="hidden" value="${requestScope.reward.addtime }" name="addtime">
		<input type="hidden" value="0" name="isdelete">
		
		<input id="save_submit" type="submit" class="btn" value="保存" />
		<input type="reset" class="btn" value="重置" />
		<input type="button" class="btn" value="返回" onclick="goBack()"/>
</form>
<%@ include file="/common/footer_eoms.jsp"%>