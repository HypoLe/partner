<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var templateId='${templateId}';
var jq=jQuery.noConflict();
jq(function(){
	jq("#addTemplateItem").click(function(){
		window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToAddTemplateItem&templateId='+templateId;
		//window.open('${app}/partner/inspect/inspectTemplateManger.do?method=goToAddTemplate', 'newwindow', 'height=400, width=600, top=10, left=400, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	});
	jq("#addNetResourceTemplateItem").click(function(){
		//window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToAddNetResourceTemplateItem&templateId='+templateId;
		window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplateItemsList&device=device&id='+templateId;
		//window.open('${app}/partner/inspect/inspectTemplateManger.do?method=goToAddTemplate', 'newwindow', 'height=400, width=600, top=10, left=400, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	});
	jq("#importTemplateItem").click(function(){
		window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToimportTemplateItem&templateId='+templateId;
	});
})

function openSearch(handler) {
	var el = Ext.get('listQueryObject');
	if (el.isVisible()) {
		el.slideOut('t', {
			useDisplay : true
		});
		handler.innerHTML = "打开查询界面";
	} else {
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}

function delInspectTemplateItem(id){
	if(confirm('确定要删除该条数据?')){
		Ext.Ajax.request({
		    url:'inspectTemplateManger.do?method=deleteTemplateItem',
		    params: {'id': id},
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
		    	 	//Ext.Msg.alert('提示','删除数据成功');
		    	 	alert("删除数据成功"); 
		    	 	window.location.reload();
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	Ext.Msg.alert('提示','删除数据失败'); 
      			 }
		    },
	     	failure: function(response) { 
                    Ext.Msg.alert('提示','删除数据失败'); 
            }
			});	
		}else{
      	 return false;
    	}	
}

function detailInspectTemplateItem(id){
	window.location='${app}/partner/inspect/inspectTemplateManger.do?method=alterTemplateItem&id='+id;
}

function detailDeviceInspectTemplateItem(id){
	window.location='${app}/partner/inspect/inspectTemplateManger.do?method=alterTemplateItem&device=device&id='+id;
}

function backTemplateList(){
	window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplatesList';
}

//下载模版项模版
jq("#exportTemplateItem").click(function(){
	alert(123);
})

</script>
<!-- 
<table class="formTable">
	<caption>
		模版管理
	</caption>
</table>

<div style="border: 1px solid #98c0f4; padding: 5px;"
			class="x-layout-panel-hd">
			<img src="${app}/images/icons/search.gif" align="absmiddle"
				style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"
				onclick="openSearch(this);">快速查询</span>
		</div>

		<div id="listQueryObject"
			style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form
				action="inspectTemplateManger.do?method=goToTemplateItemsList"
				method="post">
				<fieldset>
					<legend>
						快速查询
					</legend>
					<table id="sheet" class="formTable">
						<tr>
							<td class="label">
								添加人:
							</td>
							<td class="content">
								<input type="text" class="text" name="approvalUserName"
									id="approvalUserName" alt="allowBlank:false"
									readonly="readonly" />
								<input type="hidden" name="addUserStringEqual" id="addUserStringEqual" />
								<eoms:xbox id="approvalUserName"
									dataUrl="${app}/xtree.do?method=userFromDept" rootId="2"
									rootText="用户树" valueField="addUserStringEqual"
									handler="approvalUserName" textField="approvalUserName"
									checktype="user" single="true" />
							</td>

							<td class="label">
								巡检项目
							</td>
							<td class="content">
							
							
							<input class="text" type="text" name="inspectItemStringLike"
					id="inspectItemStringLike" alt="allowBlank:false" />
							
							</td>
						</tr>
						<tr>
						
						<td class="label">
								周期
							</td>
							<td class="content">
							<eoms:comboBox defaultValue="${inspectTemplateItem.cycle }" name="cycleStringEqual" id="cycleStringEqual" initDicId="1122003"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
							</td>
						</tr>
						<input type="hidden" name="id" value="${templateId}"/>

						<tr>
							<td colspan="4">
								<div align="left">
									<input type="submit" class="btn" value="确认查询" />
									&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
					</table>
				</fieldset>
			</form>


		</div> -->
		
		<table class="listTable">
		<tr>
			<td class="label">
				模版名称*
			</td>
			<td class="content" >
				${inspectTemplate.templateName }
			</td>
			<td class="label">
				巡检专业*
			</td>
			<td class="content" >
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplate.specialty}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				模版制作单位*
			</td>
			<td class="content" >
						<eoms:id2nameDB id="${inspectTemplate.dept}" beanId="tawSystemDeptDao" />
			</td>
			<td class="label">
				模版添加时间*
			</td>
			<td class="content" >
			${inspectTemplate.addTime}
			</td>
		</tr>
		<tr>
			<td class="label">
				模版添加人*
			</td>
			<td class="content" colspan="3">
			<eoms:id2nameDB beanId="tawSystemUserDao"
						id="${inspectTemplate.addUser}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				模板概述
			</td>
			<td class="content" colspan="3">
				<pre>${inspectTemplate.content }</pre>
				<input class="text" type="hidden" name="id" 
							id="id" value="${inspectTemplate.id }" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr><td colspan="4"><input type="button" id="addTemplateItem" value="新增模版项" class="btn" style="margin-left: 260px;"  />
			<c:if test="${pnrInspect2SwitchConfig.openMainSwitch eq true}">
				<!--  <input type="button" id="addNetResourceTemplateItem" value="网络资源模板管理" class="btn" style="margin-left: 100px;" />-->
			</c:if>
			<input type="button"  value="返回" class="btn" style="margin-left: 100px;" onclick="backTemplateList();" />
			
			</td></tr>
		
	</table>
		<br/>
		<logic:present name="inspectTemplateItemList" scope="request">
			<display:table name="inspectTemplateItemList" cellspacing="0"
				cellpadding="0" id="inspectTemplateItemList" 
				class="table inspectTemplateItemList" export="false"
				requestURI="inspectTemplateManger.do" sort="list"
				partialList="true" size="${resultSize}">
		<display:column sortable="true" headerClass="sortable" title="设备类别项目">
			${inspectTemplateItemList.bigitemName}
		</display:column>		
				<display:column sortable="true" headerClass="sortable" title="巡检项目">
			${inspectTemplateItemList.inspectItem}
		</display:column>
				<display:column sortable="true" headerClass="sortable" title="检查内容">
				${inspectTemplateItemList.inspectContent }
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="输入方式">
			<c:choose>
			<c:when test="${inspectTemplateItemList.inputType=='multiple'}">
				多选
			</c:when>
			<c:when test="${inspectTemplateItemList.inputType=='radio'}">
				单选
			</c:when>
			<c:when test="${inspectTemplateItemList.inputType=='number'}">
				数值
			</c:when>
			<c:when test="${inspectTemplateItemList.inputType=='text'}">
				文本
			</c:when>
			<c:when test="${inspectTemplateItemList.inputType=='custom'}">
				查询修改
			</c:when>
			</c:choose>
		
		
			
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="默认值">
		<c:choose>
			<c:when test="${inspectTemplateItemList.inputType == 'radio'}">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplateItemList.defaultValue}" />
			</c:when>
			
			<c:when test="${inspectTemplateItemList.inputType=='multiple'}">
			
				<c:forTokens items="${inspectTemplateItemList.defaultValue}" delims="|" var="default" varStatus="statue">
					<c:if test="${statue.last}" var="it">
						<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${default}" /> 
					</c:if>
					<c:if test="${!it}">
					<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${default}" />  | 
					</c:if>
				</c:forTokens>
			</c:when>
			<c:otherwise>
			${inspectTemplateItemList.defaultValue}
			</c:otherwise>
			</c:choose>
		
		
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="正常值范围">
			<c:choose>
			<c:when test="${inspectTemplateItemList.inputType == 'radio'}">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplateItemList.normalRange}" />
			</c:when>
			<c:when test="${inspectTemplateItemList.inputType=='multiple'}">
			
				<c:forTokens items="${inspectTemplateItemList.normalRange}" delims="|" var="default">
					<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${default}" /> &nbsp
				</c:forTokens>
			</c:when>
			<c:otherwise>
			${inspectTemplateItemList.normalRange}
			</c:otherwise>
			</c:choose>
		
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="关联数据字典">
		<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplateItemList.dictId}" />
		</display:column>
	
		<display:column sortable="true" headerClass="sortable" title="添加人">
		<eoms:id2nameDB beanId="tawSystemUserDao"
						id="${inspectTemplateItemList.addUser}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="添加时间">
			${inspectTemplateItemList.addTime}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="所需照片数">
			${inspectTemplateItemList.pictureNum}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="修改">
		
			<c:choose>
				<c:when test="${inspectTemplateItemList.deviceInspectFlag eq 1}">
					<a id="${inspectTemplateList.id }"
						href="javascript:detailDeviceInspectTemplateItem('${inspectTemplateItemList.id }')"
						shape="rect"> <img src="${app}/images/icons/edit.gif" />
					</a>
				</c:when>
				<c:otherwise>
					<a id="${inspectTemplateList.id }"
						href="javascript:detailInspectTemplateItem('${inspectTemplateItemList.id }')"
						shape="rect"> <img src="${app}/images/icons/edit.gif" />
					</a>
				</c:otherwise>
			</c:choose>
			
			
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="删除">
		<c:if test="${sessionform.userid eq inspectTemplateItemList.addUser }">
			<a id="${inspectTemplateList.id }"
								href="javascript:delInspectTemplateItem('${inspectTemplateItemList.id }')"
								shape="rect"> <img src="${app}/images/icons/icon.gif">
							</a>
		</c:if>
		</display:column>
				
				<!-- Exclude the formats i do not need. -->
				<display:setProperty name="export.rtf" value="false" />
				<display:setProperty name="export.pdf" value="false" />
				<display:setProperty name="export.xml" value="false" />
				<display:setProperty name="export.csv" value="false" />
			</display:table>
		</logic:present>
<br/>
<c:if test="${sessionform.userid eq addUser }">

</c:if>
<!-- 
<input type="button" id="importTemplateItem" value="导入模版项" class="btn"  />
<input type="button" value="下载模版项模板" class="btn" onclick="javascript:location.href='${app}/partner/inspect/inspectTemplateManger.do?method=download'" />
 -->
<%@ include file="/common/footer_eoms.jsp"%>