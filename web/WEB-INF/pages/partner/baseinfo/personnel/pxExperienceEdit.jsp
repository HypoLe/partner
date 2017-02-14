<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
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
			$("#form1").attr("action","pxExperience.do?method=edit");
		}
	});
	
	function saveImport() {
	//表单验证
		if(!v2.check()) {
			return;
		}
		new Ext.form.BasicForm('importForm').submit({
		method : 'post',
			url : "${app}/personnel/pxExperience.do?method=importData",
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
	function  getPxDays(){
		var starttimeValue="";
		var endtimeValue="";
		starttimeValue+=$("starttime").value;
		endtimeValue+=$("endtime").value;
		if(starttimeValue!=""&&endtimeValue!=""){//如果存在2个时间时计算天数；
			 var objDate1=new Date(starttimeValue.substring(0,4),starttimeValue.substring(5,7),starttimeValue.substring(8,10));
			 var objDate2=new Date(endtimeValue.substring(0,4),endtimeValue.substring(5,7),endtimeValue.substring(8,10));
			 var msSpan=objDate2.getTime()-objDate1.getTime();
			 var c = (msSpan/(60*60*1000*24))
			 $("days").value=c;
		}
	}
	function goBack(){
	 	window.location.href="<%=request.getContextPath()%>/personnel/pxExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}"
	 }
</script>
<div align="center"><b>人员培训经历管理-人员培训经历信息编辑</div><br><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer">从Excel导入</span>
</div>
<div id="listQueryObject"		style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="pxExperience.do?method=importData" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
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
										onclick="javascript:location.href='${app}/personnel/pxExperience.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<form action="pxExperience.do?method=save" method="post" id="form1" >
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
			<td class="content" colspan="3">
				<eoms:xbox id="dutyManTree" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user&isPartner=${isPartner}" rootId="${sessionform.rootPnrDeptId}"
						rootText='代维人员' valueField="dutyMan" handler="intocheckman" textField="intocheckman" checktype="user" single="true">
				</eoms:xbox>
				<input type='text' id='intocheckman' name="workername"	   readonly="true" value="${requestScope.pxExperience.workername }"  
					   alt="allowBlank:false,vtext:'员工姓名不能为空！'" class="text" />
				<input type='hidden' id="dutyMan" name="workerid" value="${requestScope.pxExperience.workerid }"   />
			</td>
		</tr>	
		</c:if>
		<tr>
			<td class="label" >
			 培训起始时间* 
			</td>
			<td class="content" >
        		<input type="text" id="starttime" name="starttime" class="text medium"	onfocus="WdatePicker({onpicked:getPxDays()})"
        		readonly="true" alt="vtype:'lessThen',link:'endtime',vtext:'培训起始时间不能大于培训结束时间',allowBlank:false"	
        		value='${requestScope.pxExperience.starttime }'  />
			</td>
			<td class="label" >
			 培训结束时间* 
			</td>
			<td class="content" >
				<input type="text" id="endtime" name="endtime"  class="text medium" 	 onfocus="WdatePicker({onpicked:getPxDays()})"
					   alt="vtype:'moreThen',link:'starttime',vtext:'培训结束时间不能小于培训起始时间',allowBlank:false"  
					    value='${requestScope.pxExperience.endtime }' readonly="true" />
			</td>
		</tr>
		<tr>
			<td class="label">
			 培训天数*
			</td>
			<td class="content" >
				<input type="text"  id="days"  value="${requestScope.pxExperience.days}" class="text"  readonly="readonly" name="days" />
			</td>
			<td class="label">
			 培训成绩
			</td>
			<td class="content" >
				<input type="text" value="${requestScope.pxExperience.score }"   class="text" name="score" id="scoreId" />
			</td>
		</tr>
		<tr>
			<td class="label">
			 培训证书扫描件 
			</td>
			<td class="content" colspan="7" height="100px">
				<%--<eoms:download ids="${requestScope.pxExperience.imagepath }"></eoms:download>
				<eoms:attachment scope="request" idField="imagepath" appCode="baseinfo"   />
				 --%>
				 <eoms:attachment name="pxExperience" property="imagepath" 
		            scope="request" idField="imagepath" appCode="dwInfoFile" alt="allowBlank:true"/> 	
			</td>
		</tr>		
		<tr>
			<td class="label">
			 培训内容
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="content" onkeyup="this.value = this.value.slice(0, 180)"
					id="memoId"  >${requestScope.pxExperience.content }</textarea>
			</td>
		</tr>
		<tr>
			<td class="label"> 
				备注 
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="memo" onkeyup="this.value = this.value.slice(0, 180)"
					id="memoId"  >${requestScope.pxExperience.memo }</textarea>
			</td>
		</tr>
		</table>
		
		<br/>
		
		<input type="hidden" value="${requestScope.pxExperience.id }" name="id">
		<input type="hidden" value="${requestScope.pxExperience.sysno}" name="sysno">
		<input type="hidden" value="${requestScope.pxExperience.adduser }" name="adduser">
		<input type="hidden" value="${requestScope.pxExperience.adddept }" name="adddept">
		<input type="hidden" value="${requestScope.pxExperience.addtime }" name="addtime">
		<input type="hidden" value="0" name="isdelete">
		
		<input id="save_submit" type="submit" class="btn" value="保存" />
		<input type="reset" class="btn" value="重置" />
		<input type="button" class="btn" value="返回" onclick="goBack()"/>
</form>
<%@ include file="/common/footer_eoms.jsp"%>