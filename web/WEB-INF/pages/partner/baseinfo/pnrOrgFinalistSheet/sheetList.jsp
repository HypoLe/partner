<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app }/pnrOrgFinalistSheet/pnrOrgFinalistSheetAction.do",
					params:{method:"deletePnrOrgFinalistSheet",id:id},
					success:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					},
					failure:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			}
}
</script>
<script type="text/javascript">
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
</script>

<table class="formTable">
			<caption>
				<div class="header center">
					巡检组织入围资质信息列表
				</div>
			</caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA" class="x-layout-panel-hd">
<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
 style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;"> 

	<form action="${app }/pnrOrgFinalistSheet/pnrOrgFinalistSheetAction.do?method=pnrOrgFinalistSheetList" method="post">
			<table id="sheet" class="listTable">
				<tr>
					<td class="label">
						组织名称:
					</td>
					<td class="content" colspan="3">
						<input type="text" class="text" name="orgName" value="${orgNameSearch}"/>
					</td>
					</tr>
					<tr>
					<td class="label">
						专业 &nbsp;
					</td>
					<td class="content" colspan="3">
						<c:forEach items="${specialtyList}" var="dictBigType">
							<c:if test="${dictBigType.dictRemark=='isTrue'}">
								<input type="checkbox" name="bigType"
									value="${dictBigType.dictId}" checked='true' />${dictBigType.dictName}
					</c:if>
							<c:if test="${dictBigType.dictRemark!='isTrue'}">
								<input type="checkbox" name="bigType"
									value="${dictBigType.dictId}" />${dictBigType.dictName}
					</c:if>
					</c:forEach>
				</td>
				</tr>
			</table>
			<table>
				<tr>
					<td colspan="4" class="content">
							<input type="submit" class="btn" value="确认" />&nbsp;&nbsp;&nbsp;
							<input type="reset" class="btn" value="重置" />
					</td>
				</tr>
			</table>
	</form>
</div>


<logic:present name="pnrOrgFinalistSheetList" scope="request">
	<display:table name="pnrOrgFinalistSheetList" cellspacing="0" cellpadding="0"
		id="pnrOrgFinalistSheetList" pagesize="${pagesize}" 
		class="table pnrOrgFinalistSheetList" export="false"
		requestURI="${app }/pnrOrgFinalistSheet/pnrOrgFinalistSheetAction.do" sort="list" 
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="组织名称" maxLength="24">
			${pnrOrgFinalistSheetList.orgName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="系统编号" maxLength="24">
			${pnrOrgFinalistSheetList.sysno}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="巡检专业" maxLength="16">
			${pnrOrgFinalistSheetList.speciality}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="入围时间">
			${pnrOrgFinalistSheetList.finalistTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="截止时间">
			${pnrOrgFinalistSheetList.finalistEndTime}
		</display:column>
		<c:if test="${hasRightForAddAndDel==1}">
			<display:column sortable="false" headerClass="sortable" title="编辑"
				paramProperty="id" url="/pnrOrgFinalistSheet/pnrOrgFinalistSheetAction.do?method=gotoPage&page=gotoAddPnrOrgFinalistSheet"
				paramId="id" media="html">
				<img src="${app }/images/icons/edit.gif">
			</display:column>
		</c:if>
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${pnrOrgFinalistSheetList.id }"
				href="${app }/pnrOrgFinalistSheet/pnrOrgFinalistSheetAction.do?method=gotoPage&page=gotoPnrOrgFinalistSheetDetail&id=${pnrOrgFinalistSheetList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		<c:if test="${hasRightForAddAndDel==1}">
			<display:column sortable="false" headerClass="sortable" title="删除"
				 media="html">
				 <a href="javascript:void(0)" onclick="deleteInfo('${pnrOrgFinalistSheetList.id}')">
					<img class="delete" src="${app }/images/icons/icon.gif" />
				</a>
			</display:column>
		</c:if>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
