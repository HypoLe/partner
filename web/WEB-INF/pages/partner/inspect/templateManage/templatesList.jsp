<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
	<%response.setHeader("cache-control","public"); %>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
jq(function(){
	jq("#addTemplate").click(function(){
		window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToAddTemplate';
		//window.open('${app}/partner/inspect/inspectTemplateManger.do?method=goToAddTemplate', 'newwindow', 'height=400, width=600, top=10, left=400, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	})
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

function delTemplate(id){
	if(confirm('确定要删除该条数据?')){
		Ext.Ajax.request({
		    url:'inspectTemplateManger.do?method=deleteTemplate',
		    params: {'id': id},
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
		    	 	//Ext.Msg.alert('提示','删除数据成功');
		    	 	alert("删除数据成功"); 
		    	 	window.opener.location.href=window.opener.location.href;
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

function detailInspectTemplate(id){
	window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToAddTemplate&id='+id;
}
function detailInspectTemplateItem(id){
	window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplateItemsList&id='+id;
}



</script>
<!-- 
<table class="formTable">
	<caption>
		模版管理
	</caption>
</table>
 -->
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
				action="inspectTemplateManger.do?method=goToTemplatesList"
				method="post">
					<!-- <legend>
						快速查询
					</legend> -->
					<table id="sheet" class="listTable">
						<tr>
							<td class="label">
								模版名称
							</td>
							<td class="content">
							
							
							<input class="text" type="text" name="templateNameStringLike"
					id="templateNameStringLike" alt="allowBlank:false" />
						
							<td class="label">
								<!-- 添加人:  -->
								巡检专业
							</td>
							<td class="content">
								<!-- <input type="text" class="text" name="approvalUserName"
									id="approvalUserName" alt="allowBlank:false"
									readonly="readonly" />
								<input type="hidden" name="addUserStringEqual" id="addUserStringEqual" />
								<eoms:xbox id="approvalUserName"
									dataUrl="${app}/xtree.do?method=userFromDept" rootId="2"
									rootText="用户树" valueField="addUserStringEqual"
									handler="approvalUserName" textField="approvalUserName"
									checktype="user" single="true" /> -->
								<eoms:comboBox name="specialtyStringEqual" id="specialty" 
									initDicId="11225" alt="allowBlank:false" styleClass="select" />
									
							</td>

							
							
							</td>
						</tr>
						
						
						<tr>
						
						<td class="label">
								制作单位
							</td>
							<td>
								
								
								<input type="text"  class="text"  name="deptName" id="deptName"  
					value="" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="deptStringEqual" id="deptId"  value=""/>
				<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" rootId="2"
				rootText='请选择部门' valueField="deptId" handler="deptName" textField="deptName"
				checktype="dept" single="true" ></eoms:xbox>
				</td>
						</tr>
						

						<tr>
							<td colspan="4">
								<div align="left">
									<input type="submit" class="btn" value="确认查询" />
									&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						</tr>
					</table>
			</form>


		</div>
		
		<logic:present name="inspectTemplateList" scope="request">
			<display:table name="inspectTemplateList" cellspacing="0" 
				cellpadding="0" id="inspectTemplateList" pagesize="${pageSize}"
				class="table inspectTemplateList" export="false"
				requestURI="inspectTemplateManger.do" sort="list"
				partialList="true" size="${resultSize}">
				
				<display:column sortable="true" headerClass="sortable" title="巡检资源专业">
						<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
								id="${inspectTemplateList.specialty}" />
				</display:column>
				
				<display:column sortable="true" headerClass="sortable" title="资源类型">
						<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
								id="${inspectTemplateList.resType}" />
				</display:column>
				
				<display:column sortable="true" headerClass="sortable" title="模版名称">
				<c:choose>
				<c:when test="${fn:length(inspectTemplateList.templateName)>10}">
				<!-- 
				<marquee scrolldelay=300 scrollamount=10
								title="${inspectTemplateList.templateName}"
								width=100 onclick="javascript:alert('${inspectTemplateList.templateName}')">
								<span onclick= "javascript:alert('${inspectTemplateList.templateName}')">${inspectTemplateList.templateName}</span>
							</marquee> -->
							${inspectTemplateList.templateName}
				</c:when>
				<c:when test="${fn:length(inspectTemplateList.templateName)<=10}">
				${inspectTemplateList.templateName}
				</c:when>
				</c:choose>
				</display:column>
				
				<display:column sortable="true" headerClass="sortable" title="添加人员">
				<eoms:id2nameDB beanId="tawSystemUserDao"
						id="${inspectTemplateList.addUser}" />
			
		</display:column>
		<%----
				<display:column sortable="true" headerClass="sortable" title="添加时间">
			${inspectTemplateList.addTime}
		</display:column>---%>
		<display:column sortable="true" headerClass="sortable" title="制作单位">
		<eoms:id2nameDB id="${inspectTemplateList.dept}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="模板项数">
			${inspectTemplateList.itemNum}
		</display:column>
				<display:column sortable="false" headerClass="sortable" title="查看"
					media="html">
					<a id="${inspectTemplateList.id }"
								href="javascript:detailInspectTemplate('${inspectTemplateList.id }')"
								shape="rect"> <img src="${app}/images/icons/search.gif" />
							</a>
				</display:column>
				<display:column sortable="false" headerClass="sortable" title="查看模版项"
					media="html">
					<a id="${inspectTemplateList.id }"
								href="javascript:detailInspectTemplateItem('${inspectTemplateList.id }')"
								shape="rect"> <img src="${app}/images/icons/search.gif" />
							</a>
				</display:column>
				
		
				<display:column sortable="false" headerClass="sortable" title="删除"
				media="html">
				
				<!--  <a id="${inspectTemplateList.id }"
							href="javascript:delTemplate('${inspectTemplateList.id }')"
							shape="rect"> <img src="${app}/images/icons/icon.gif">
						</a>-->
					<c:if test="${sessionform.userid eq inspectTemplateList.addUser }">
						 <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
				         {window.location.href = '${app}/partner/inspect/inspectTemplateManger.do?method=deleteTemplate&&id=${inspectTemplateList.id }';}" title="删除"/>
				        	<img src="${app}/images/icons/icon.gif" />
				         </a> 
					</c:if>
				</display:column>
				
				
				
				<display:setProperty name="export.rtf" value="false" />
				<display:setProperty name="export.pdf" value="false" />
				<display:setProperty name="export.xml" value="false" />
				<display:setProperty name="export.csv" value="false" />
			</display:table>
		</logic:present>
		
		
<!-- 测试部门树 -->
<%----
<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
					value="<eoms:id2nameDB id="${inspectTemplate.dept}" beanId="partnerDeptDao"/>" 
					alt="allowBlank:false" readonly="readonly"/>
				 <input name="dept" id="dept"  value="${inspectTemplate.dept}" type="text" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true&checktype=excludeBigNodAndLeaf&showPartnerLevelType=1|3"  
						rootId="" rootText="公司树"  valueField="dept" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />	
--%>
<br/>
<input type="button" id="addTemplate" value="新增模版" class="btn"  name='submitInput' />
<%@ include file="/common/footer_eoms.jsp"%>