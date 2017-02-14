<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
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
			$("#form1").attr("action","certificate.do?method=edit");
		}
		$("#select_type").change(function(){
			if($(this).val()=="其他"){
				$("#typeId").show();
				$("#typeId").val(null);
				}
			else{
				$("#typeId").hide();
				$("#typeId").val($(this).val());
				}
			})
		var cerType = "${requestScope.certificate.type}";
		if(cerType!=""){
			if(cerType!="电工证"&&cerType!="登高证"&&cerType!="驾驶证"&&cerType!="制冷证"){
				$("#typeId").show();
				$("#typeId").val(cerType);
				$("#select_type").attr("value","其他");
				}
			else{
				$("#select_type").attr("value",cerType);
			}
		}
		//--------------------validate-----------------------------
		/*西安需求不需要对证书编号验证，而且证书编码不一定都是数字，校验不合理modify by fengguangping start 
		$("#codeId").blur(function(){//证书编码
			var reNumber = /^\d+(\.{1}\d+)?$/;
			var val = $(this).val();
			if(val!=""&&val!=null)
			 if(!reNumber.test(val)){
			  	 alert("证书编码只能为数字！");
			  	 $(this).val(null);
			  	 return;
			  }
		})
		*//*modify by fengguangping start */
		
		//修改时 姓名不可修改
/*		var name = '';
		name = '${requestScope.certificate.workername }';
		if(name=='')
			name = '${workername }';
		if(name!=''){
			$("#intocheckman").attr("disabled","disabled");
		}
	*/	
	});
	function saveImport() {
	//表单验证
		if(!v2.check()) {
			return;
		}
		new Ext.form.BasicForm('importForm').submit({
		method : 'post',
			url : "${app}/personnel/certificate.do?method=importData",
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
	 	window.location.href="<%=request.getContextPath()%>/personnel/certificate.do?method=search&operationType=tomgr&personCardNo=${personCardNo}"
	 }
</script>
<div align="center"><b>人员证书信息管理-人员证书信息编辑</div><br><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer">从Excel导入</span>
</div>
<div id="listQueryObject"		style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="certificate.do?method=importData" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
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
										onclick="javascript:location.href='${app}/personnel/certificate.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<form action="certificate.do?method=save" method="post" id="form1" >
		<table id="sheet" class="formTable">
		<c:if  test="${!empty workerid}">  <!-- 综合信息处理页面 -->
			<input type='hidden' id="workername" name="workername" value="${workername }"   />
			<input type='hidden' id="workerid" name="workerid" value="${workerid }"/>
			<input type='hidden' id="personCardNo" name="personCardNo" value="${personCardNo}"/>
		</c:if>
		<c:if test="${(empty workerid)||workerid==''}">
			<tr>
				<td class="label" >
				 员工姓名* 
				</td>
				<td class="content" colspan="3">
					<eoms:xbox id="dutyManTree" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user&isPartner=${isPartner}" rootId="${sessionform.rootPnrDeptId}"
							rootText='代维人员' valueField="dutyMan" handler="intocheckman" textField="intocheckman"
							checktype="user" single="true"></eoms:xbox>
					<input type='text' id='intocheckman' name="workername"  class="text"   readonly="true" value="${requestScope.certificate.workername }"  
						   alt="allowBlank:false,vtext:'员工姓名不能为空！'" />
					<input type='hidden' id="dutyMan" name="workerid" value="${requestScope.certificate.workerid }"   />
				</td>
			</tr>
		</c:if>	
			<tr>
				<td class="label">
				 证书类别* 
				</td>
				<td class="content">
					<select id="select_type" class="select" alt="allowBlank:false,vtext:'证书类别不能为空！'">
						<option value="">--请选择--</option>
						<option value="电工证">电工证</option>
						<option value="登高证">登高证</option>
						<option value="制冷证">制冷证</option>
						<option value="驾驶证">驾驶证</option>
						<option value="其他">其他</option>
					</select>
				 		<input class="text" type="text" name="type" style="display: none;"
						id="typeId"  value="${requestScope.certificate.type }"
						alt="vtext:'证书类别不能为空！',allowBlank:false" 	/>
				</td>
				<td class="label">
				 证书等级
				</td>
				<td class="content">
					<input class="text" type="text" name="level"  maxlength="10"
						id="levelId"  value="${requestScope.certificate.level }"/>
				</td>
			</tr>
			<tr>
				<td class="label">
				 颁发时间*
				</td>
				<td class="content">
				<input id="issuetime" class="Wdate" type="text"  name="issuetime"  readonly="readonly" value='${requestScope.certificate.issuetime}'
						 class="text" alt="vtext:' 颁发时间不能为空！',allowBlank:false" 
						onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'validity\')||\'%y-%M-%d\'}'})"/> 
				</td>
				<td class="label">
				 发证机关 
				</td>
				<td class="content">
					<input class="text" type="text" name="issueorg"  maxlength="40"
						id="orgId"  value="${requestScope.certificate.issueorg }"/>
				</td>
			</tr>
			<tr>
				<td class="label">
				 有效期* 
				</td>
				<td class="content">
				<input id="validity" class="Wdate" type="text"  readonly="readonly" name="validity" value='${requestScope.certificate.validity}'
					 class="text medium" alt="vtext:' 有效期不能为空！',allowBlank:false" 
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'issuetime\')}'})"/>
				</td>
				<td class="label">
				 证书编号
				</td>
				<td class="content">
					<input class="text" type="text" name="codeno"  maxlength="20"
						id="codeId" value="${requestScope.certificate.codeno }"/>
				</td>
			</tr>
			<tr>
				<td class="label">
				 证书附件 
				</td>
				<td class="content" colspan="3" height="100px">
					<%--<eoms:download ids="${requestScope.certificate.imagepath }"></eoms:download>
					<eoms:attachment scope="request" idField="imagepath" appCode="baseinfo"   />
				--%>
					<eoms:attachment name="certificate" property="imagepath" 
		            scope="request" idField="imagepath" appCode="dwInfoFile" alt="allowBlank:true"/> 	
				</td>
			</tr>
			<tr>
				<td class="label"> 
					备注 
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="memo"  onkeyup="this.value = this.value.slice(0, 180)"
						id="memoId"  >${requestScope.certificate.memo }</textarea>
				</td>
			</tr>
		</table>
		<br/>
		<input type="hidden" value="${requestScope.certificate.id }" name="id">
		<input type="hidden" value="${requestScope.certificate.sysno}" name="sysno">
		<input type="hidden" value="${requestScope.certificate.adduser }" name="adduser">
		<input type="hidden" value="${requestScope.certificate.adddept }" name="adddept">
		<input type="hidden" value="${requestScope.certificate.addtime }" name="addtime">
		<input type="hidden" value="0" name="isdelete">
		<input id="save_submit" type="submit" class="btn" value="保存" />
		<input type="reset" class="btn" value="重置" />
		<input type="button" class="btn" value="返回" onclick="goBack()"/>
</form>
<%@ include file="/common/footer_eoms.jsp"%>