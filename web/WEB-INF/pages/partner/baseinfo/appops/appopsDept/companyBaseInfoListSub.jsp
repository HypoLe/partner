<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
    var checkflag = "false";
	function chooseAll(){	
	    var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        } 
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    } 
		    checkflag="false";
	    }
	}
	
	function isChecked() {
		var objs = document.getElementsByName("checkbox11");
		var ids="";
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
	
	
	
 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
	
</script>
<c:set var="buttons">
<a href="javascript:isChecked();">删除</a>
</c:set>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">


<table class="formTable">
<caption>
			<div class="header center">
				地市组织信息列表
			</div>
		</caption>
</table>
<html:form action="partnerAppOpsDept" method="post"  styleId="partnerDeptForm">
<input type="hidden" name="parentNodeId" value="${parentNodeId }">
	<display:table name="partnerDeptList" cellspacing="0" cellpadding="0"
		id="partnerDeptList" pagesize="${pageSize}" class="table partnerDeptList"
		export="false"
		requestURI="${app}/partner/appops/partnerAppOpsDept.do?method=findCompanyBaseInfo"
		sort="list" partialList="true" size="resultSize">
	<c:if test="${requestScope.hasRightForAdd=='1'}">
	    <display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
	         <input type="checkbox" name="checkbox11" value="<c:out value='${partnerDeptList.id}'/>" >
	    </display:column>
    </c:if>
	<display:column  sortable="true" headerClass="sortable" title="组织名称">
				<a   href="${app}/partner/appops/partnerAppOpsDept.do?method=detail&proId=${partnerDeptList.id}&hasRightForAdd=${hasRightForAdd}&searchInto=${searchInto}&isPartner=${isPartner}">
					${partnerDeptList.name}
				</a>
	</display:column>
	<display:column property="organizationNo" sortable="true"
			headerClass="sortable" title="组织编码"  paramId="id" paramProperty="id"/>
			
	<display:column property="areaName" sortable="true"
			headerClass="sortable" titleKey="partnerDept.areaName"  paramId="id" paramProperty="id"/>
			
	<display:column property="contactor" sortable="true"
			headerClass="sortable" titleKey="partnerDept.contactor"  paramId="id" paramProperty="id"/>
			
	<display:column property="phone" sortable="true"
			headerClass="sortable" titleKey="partnerDept.phone"  paramId="id" paramProperty="id"/><%--
			
	<display:column sortable="true"
			headerClass="sortable" title="地址"  paramId="id" paramProperty="id">
			${partnerDeptList.address}
	</display:column>		
			
	--%><display:column sortable="false"
			headerClass="sortable" title="查看下属组织"  paramId="id" paramProperty="id">
	<a href="${app}/partner/appops/partnerAppOpsDept.do?method=findGrandsonCompanyBaseInfo&id=${partnerDeptList.id }&parent_id=${parent_id }&isPartner=${isPartner}">查看</a>
	</display:column>
	<display:column sortable="false" headerClass="sortable" title="添加下属组织"  paramId="id" paramProperty="id">
	<a href="${app}/partner/appops/partnerAppOpsDept.do?method=toCompanyBaseInfoGrandsonForm&id=${partnerDeptList.id }&parentDeptname=${partnerDeptList.name }&parent_id=${parent_id }&areaid=${partnerDeptList.areaId}&isPartner=${isPartner}">添加区县组织</a>
	</display:column>
	<display:setProperty name="paging.banner.item_name" value="partnerDept" />
	<display:setProperty name="paging.banner.items_name" value="partnerDepts" />
	</display:table>
	<br>
	<c:if test="${hasRightGoBack<2}">
		<input type="button" class="btn" value="返回上一级"
		onclick="javascript:self.history.back();">
	</c:if>
	<c:if test="${requestScope.hasRightForAdd=='1'}">
		<c:if test="${!empty partnerDeptList}">
			<input type="button" class="btn" value="删除"	onclick="isChecked();">
		</c:if>
	</c:if>
</html:form>
</fmt:bundle>
<c:if test="${requestScope.refreshTree == 1}">
<script type="text/javascript">
  try{
	//刷新父框架中的整颗树
	//parent.AppFrameTree.refreshTree();
	//刷新父框架中当前选中的节点
	parent.AppFrameTree.reloadNode();
  }catch(e){}
</script>
</c:if>



<%@ include file="/common/footer_eoms.jsp"%>