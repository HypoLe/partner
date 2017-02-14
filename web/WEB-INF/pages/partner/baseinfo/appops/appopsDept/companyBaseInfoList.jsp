<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
	var checkflag = "false";
	function chooseAll() {
		var objs = document.getElementsByName("checkbox11");
		if (checkflag == "false") {
			for ( var i = 0; i < objs.length; i++) {
				objs[i].checked = "checked";
			}
			checkflag = "checked";
		} else if (checkflag == "checked") {
			for ( var i = 0; i < objs.length; i++) {
				objs[i].checked = false;
			}
			checkflag = "false";
		}
	}
	function isChecked() {
		var objs = document.getElementsByName("checkbox11");
		var flag = false;
		for ( var i = 0; i < objs.length; i++) {
			if (objs[i].checked == true) {
				flag = true;
				break;
			}
		}
		if (flag == false) {
			alert("请选择删除项！");
			return false;
		}
		if (confirm("确认删除?")) {
			var ids="";
			for(j=0;j<objs.length;j++){
				if(objs[j].checked){
					if(ids!=""){
						ids+=";";
					}
					ids+=objs[j].value;
				}
			};
			Ext.Ajax.request({
		 		url:"${app}/partner/appops/partnerAppOpsDept.do",
		 		params:{method:"remove",id:ids},
		 		success:function(res,opt){
		 			Ext.Msg.alert("提示信息",Ext.util.JSON.decode(res.responseText).info,function(){window.location.reload();})
		 		},
		 		failure:function(){
		 			Ext.Msg.alert("提示信息","请求失败",function(){window.location.reload();})
		 		}
		 	});
		}
	}

	function delAllOption(elementid) {
		var ddlObj = document.getElementById(elementid);//获取对象
		for ( var i = ddlObj.length - 1; i >= 0; i--) {
			ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
		}
	}
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
		var formElement=document.getElementById("partnerDeptForm")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
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
<fmt:bundle	basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<table class="formTable">
			<caption>
				<div class="header center">
					组织信息列表
				</div>
			</caption>
		</table>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form action="partnerAppOpsDept.do?method=findCompanyBaseInfo&isPartner=${isPartner}" method="post" id="partnerDeptForm">
		<table id="sheet" class="listTable">
	<tr>
		<td class="label">
			组织名称
		</td>
		<td class="content">
			<input type="text" name="nameSearch" id="name" class="text max" value="${nameSearch}"/>
		</td>
		<td class="label">
			组织编码
		</td>
		<td class="content">
			<input type="text" name="organizationNoSearch" id="organizationNo" class="text medium" value="${organizationNoSearch}"/>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="partnerDept.phone" />
		</td>
		<td class="content">
			<input type="text" name="phoneSearch" id="phone" class="text medium" value="${phoneSearch}"/>
		</td>
		<td class="label">
            联系人
		</td>
		<td class="content">
			<input type="text" name="contactorSearch" id="contactor" class="text medium" value="${contactorSearch}"/>
		</td>
	</tr>
	</table>
		<html:submit styleClass="btn" property="method.list" styleId="method.iist" value="查询"></html:submit>
		<input type="button" class="btn" value="重置" onclick="res();" />
	</form>
	</div>
</fmt:bundle>
<c:set var="buttons">
	</br>
	<c:if test="${requestScope.hasRightForAdd=='1'}">
		<a
			href='${app}/partner/baseinfo/partnerAppOpsDept.do?method=toCompanyBaseInfoForm'>
			添加省下属组织 <a>
	</c:if>
  <a href="javascript:;">删除</a>
</c:set>
	<html:form action="partnerAppOpsDept" method="post" styleId="partnerDeptForm">
		<input type="hidden" name="parentNodeId" value="${parentNodeId }">
		<display:table name="partnerDeptList" cellspacing="0" cellpadding="0" id="partnerDeptList" pagesize="${pageSize}"
			class="table partnerDeptList" export="true" requestURI="${app}/partner/appops/partnerAppOpsDept.do?method=findCompanyBaseInfo"
			sort="list" partialList="true" size="resultSize" >
			<c:if test="${requestScope.hasRightForAdd=='1'}">
				<display:column title="<input type='checkbox' onclick='javascript:chooseAll();'>" media="html">
					<input type="checkbox" name="checkbox11" value="<c:out value='${partnerDeptList.id}'/>">
				</display:column>
			</c:if>
			<display:column  sortable="true" headerClass="sortable" title="组织名称">
				<a href="${app}/partner/appops/partnerAppOpsDept.do?method=detail&proId=${partnerDeptList.id}&hasRightForAdd=${hasRightForAdd}&searchInto=${searchInto}&isPartner=${isPartner}">
					${partnerDeptList.name}
				</a>
			</display:column>
			<display:column property="organizationNo" sortable="true"
				headerClass="sortable" title="组织编码" paramId="id" paramProperty="id" />
			<display:column property="areaName" sortable="true"
				headerClass="sortable" title="所属地市" paramId="id"
				paramProperty="id" />
			<display:column property="contactor" sortable="true"
				headerClass="sortable" title="联系人" paramId="id"
				paramProperty="id" />
			<display:column property="phone" sortable="true"
				headerClass="sortable" title="联系电话" paramId="id"
				paramProperty="id" />
			<display:column title="查看下属组织" media="html">
					<a href="${app}/partner/appops/partnerAppOpsDept.do?method=findSubCompanyBaseInfo&id=${partnerDeptList.id }&parentname=${partnerDeptList.name }&isPartner=${isPartner}">
						查看</a>
			</display:column>
			<display:column title="添加下属组织" media="html">
				<a href="${app}/partner/appops/partnerAppOpsDept.do?method=toCompanyBaseInfoSubForm&id=${partnerDeptList.id }&parentDeptname=${partnerDeptList.name }&isPartner=${isPartner} ">
                    <c:choose>
                        <c:when test="${isPartner eq '1'}">
                            添加地市组织
                        </c:when>
                        <c:otherwise>
                            添加地市组织
                        </c:otherwise>
                    </c:choose>
                </a>
			</display:column>
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
		<br><br>
		<c:if test="${hasRightForAdd=='1'}">
            <c:choose>
                <c:when test="${isPartner eq '1'}">
                    <input type="button" class="btn" value="添加组织" onclick="javascript:window.location='${app}/partner/appops/partnerAppOpsDept.do?method=toCompanyBaseInfoForm&isPartner=${isPartner}'">
                </c:when>
                <c:otherwise>
                    <input type="button" class="btn" value="添加组织" onclick="javascript:window.location='${app}/partner/appops/partnerAppOpsDept.do?method=toCompanyBaseInfoForm&isPartner=${isPartner}'">
                </c:otherwise>
            </c:choose>

			<c:if test="${!empty partnerDeptList}">
				<input type="button" class="btn" value="删除" onclick="isChecked();">
			</c:if>
		</c:if>
	</html:form>
<c:if test="${requestScope.refreshTree == 1}">
	<script type="text/javascript">
	try {
		//刷新父框架中的整颗树
		//parent.AppFrameTree.refreshTree();
		//刷新父框架中当前选中的节点
		parent.AppFrameTree.reloadNode();
	} catch (e) {
	}
</script>
</c:if>

<%@ include file="/common/footer_eoms.jsp"%>