<div align="right"><%@include file="/common/taglibs.jsp"%> 
<%@include file="/common/header_eoms_form.jsp"%> 
<%@page language="java" pageEncoding="UTF-8"%> 
</div><div class="header center">
	<b>审批记录
</div>
<br>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<form action="${app}/partner/process/process.do?method=goToSignedList" id="signedListForm" method="post">
	<table id="sheet" class="listTable">
		<tr>
				<td class="label">
					变更类型&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox  name="changeTypeStringEqual" id="changeType"  defaultValue="${referenceModelStringEqual}"
					initDicId="1230501" styleClass="input select"/>
				</td>
				<td class="label">
					申请时间&nbsp;
				</td>
				<td class="content">
					<input class="text"  name="m.start_timeStringGreaterOrEqual"  id="m.start_timeStringGreaterOrEqual"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,true,-1)" value="${m.start_timeStringGreaterOrEqual}"
					readonly="readonly"/>&nbsp;&nbsp;至
					<input class="text"  name="m.start_timeStringLessOrEqual"  id="m.start_timeStringLessOrEqual"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,true,-1)" value="${m.start_timeStringLessOrEqual}"
					readonly="readonly"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					申请模块&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox  name="referenceModelStringEqual" id="referenceModelStringEqual" initDicId="${dictId}" 
					defaultValue="${referenceModelStringEqual}" styleClass="input select"/>
				</td>
				<td class="label">
					审批时间&nbsp;
				</td>
				<td class="content">
					<input class="text"  name="applyTimeStringGreaterOrEqual"  id="applyTimeStringGreaterOrEqual"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,true,-1)" value="${applyTimeStringGreaterOrEqual}"
					readonly="readonly"/>&nbsp;&nbsp;至
					<input class="text"  name="applyTimeStringLessOrEqual"  id="applyTimeStringLessOrEqual"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,true,-1)" value="${applyTimeStringLessOrEqual}"
					readonly="readonly"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					审核结果&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox  name="curentStateStringEqual" id="curentStateStringEqual"  defaultValue="${curentStateStringEqual}"
					initDicId="1230502" styleClass="input select"/>
				</td>
				<td class="label">
					归档时间&nbsp;
				</td>
				<td class="content">
					<input class="text"  name="applyTimeStringGreaterOrEqual"  id="applyTimeStringGreaterOrEqual"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,true,-1)" value="${applyTimeStringGreaterOrEqual}"
					readonly="readonly"/>&nbsp;&nbsp;至
					<input class="text"  name="applyTimeStringLessOrEqual"  id="applyTimeStringLessOrEqual"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,true,-1)" value="${applyTimeStringLessOrEqual}"
					readonly="readonly"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					是否归档&nbsp;
				</td>
				<td class="content" colspan="3">
					<eoms:comboBox  name="deletedStringEqual" id="deletedStringEqual" initDicId="12504" 
					defaultValue="${deletedStringEqual}" styleClass="input select" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
			<td colspan='4' class='label'>
				<input type="submit" 	 value="查询">
				<input type="button"  id="reset" value="重置" onclick="res();">
			</td>
		</tr>
	</table>
</form>
</div>
<div >
	<logic:notEmpty name="resultList" scope="request">
	<display:table name="resultList" cellspacing="0" cellpadding="0" id="resultList" pagesize="${pageSize}"
			class="table resultList" export="false" 
			requestURI="${app}/partner/process/process.do?method=goToSignedList" sort="list"
			partialList="true" size="${resultSize}">
		<display:column sortable="true" headerClass="sortable" title="申请时间">${resultList['start_time']}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批时间">${resultList['end_time']}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批人员">
			<eoms:id2nameDB id="${resultList['data_receiver']}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请变更类型">
			<eoms:id2nameDB id="${resultList['change_type']}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请模块">
			<eoms:id2nameDB id="${resultList['reference_model']}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核结果">
				<eoms:id2nameDB id="${resultList['state']}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="已归档">
			<c:if test="${resultList['deleted']=='0'}">
				否
			</c:if>
			<c:if test="${resultList['deleted']=='1'}">
				是
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档时间">
			<c:if test="${resultList['deleted']=='1'}">
				${resultList['signedtime']}
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档">
			<c:if test="${resultList['deleted']=='0'}">
				<a href="javascript:void(0);" onclick="operate('${resultList['id']}')">执行</a>
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="删除">
			<c:if test="${resultList['deleted']=='0'}">
				<a href="javascript:void(0);" onclick="del('${resultList['id']}')">删除</a>
			</c:if>
		</display:column>
	</display:table>
	</logic:notEmpty> 
	<logic:empty name="resultList" scope="request">
	没有记录！
	</logic:empty> 
</div>
<script type="text/javascript">
	function operate(id){
		Ext.MessageBox.confirm("确认框","确定执行归档么?",function(btn){
			if(btn=="yes"){
				parent.Ext.get("ext-gen54").mask("数据归档中，请稍等......");
				Ext.Ajax.request({
					url:"${app}/partner/process/process.do",
					params:{method:"applyToFile",idMain:id},
					success:function(response,opt){
			 			Ext.MessageBox.alert("提示信息",response.responseText,function(){window.location.reload();});
			 			parent.Ext.get("ext-gen54").unmask();
					},
					failure:function(response,opt){
						parent.Ext.get("ext-gen54").unmask();
			 			Ext.MessageBox.alert("提示信息",response.responseText,function(){window.location.reload();});
						}
				})
			}
		});
	}
	function del(id){
		Ext.MessageBox.confirm("确认框","删除后数据不可恢复,你确定删除么?",function(btn){
			if(btn=="yes"){
				Ext.Ajax.request({
					url:"${app}/partner/process/process.do",
					params:{method:"delete",idMain:id},
					success:function(response,opt){
			 			Ext.MessageBox.alert("提示信息",response.responseText,function(){window.location.reload();});
			 			parent.Ext.get("ext-gen54").unmask();
					},
					failure:function(response,opt){
						parent.Ext.get("ext-gen54").unmask();
			 			Ext.MessageBox.alert("提示信息",response.responseText,function(){window.location.reload();});
					}
				});
			}
		});
	}
	function openImport(){
		var handler = document.getElementById("openQuery");
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
</script>
<%@ include file="/common/footer_eoms.jsp"%>