<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
	<style>
		select.select{
			width:80%;
		}
	</style>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js"></script>
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
	
	function isChecked(){
		var objs = document.getElementsByName("checkbox11");
		var flag = false;
	    for(var i=0; i<objs.length; i++){
	       if(objs[i].checked==true){
	           flag=true;
	           break;
	       }
	    }
	    if(flag==false){
		        alert("请选择删除项!");
		        return;
		    }
		if(confirm("确认删除？")){
		   document.forms[1].action = "${app}/partner/baseinfo/partnerUsers.do?method=remove";
	       document.forms[1].submit();
	       return;
		}
	}
	function add(isPartner){
		window.location.href="<%=request.getContextPath()%>/partner/baseinfo/partnerUsers.do?method=addCompanyUser&isPartner="+isPartner;
	}
</script>
<script type="text/javascript">
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
<div align="center"><b>人员信息列表</div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<html:form action="partnerUsers.do?method=searchCompanyUser&isPartner=${isPartner}" method="post"  styleId="partnerUserForm">
<c:if test="${in!=null}">
</c:if>
<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<table class="listTable">
	<tr>
		<td class="label">
			人员姓名
		</td>
		<td class="content">
			<input type="text" name="nameSearch" id="name"	class="text medium"	  value="${nameSearch}"/>
		</td>
		<td class="label">
			所属组织
		</td>
		<td class="content" >	
			<input type="text" class="text max" name="provName" id="provName"
				value="<eoms:id2nameDB id="${prov}" beanId="partnerDeptDao"/>"
				alt="allowBlank:false" readonly="readonly" />
			<input name="prov" id="prov" value="${prov}" type="hidden" />
			<input name="oldProv" id="oldProv" value="${oldProv}" type="hidden" />
			<eoms:xbox id="provTree"
				dataUrl="${app}/xtree.do?method=getPnrDeptWithRight&isPartner=${isPartner}"
				rootId="" rootText="公司树" valueField="prov" handler="provName"
				textField="provName" checktype="dept" single="true" />
		</td>
	</tr>
	<tr>	
		<td class="label">
			Email
		</td>
		<td class="content">
			<input type="text" name="emailSearch" id="email"	class="text medium"	 value="${emailSearch}" />
		</td>
		<td class="label">
			移动电话
		</td>
		<td class="content">
			<input type="text" name="mobilePhoneSearch" id="mobilePhone" 	class="text medium"   value="${mobilePhoneSearch}"/>
		</td>
	</tr>
</table>
<table >
	<tr>
		<td>
			<input type="submit" class="btn" value="查询" />
			<input type="reset" class="btn" value="重置" >
			 <input type="hidden" value="tomgr" name = "operationType"/>
		</td>
	</tr>
</table>
</html:form>	
</div>
<html:form action="partnerUsers" method="post"  styleId="partnerUserForm">
	<input type="hidden" name="treeNodeId" value="${treeNodeId }">
	<display:table name="partnerUserList" cellspacing="0" cellpadding="0"
		id="partnerUserList" pagesize="${pageSize}" class="table partnerUserList"
		export="false" requestURI="${app}/partner/baseinfo/partnerUsers.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<!--权限控制移动人员才有删除和添加的权限 modify by fengguangping begin-->
	<c:if test="${requestScope.hasRightForDel=='1'}" >	
	    <display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
	         <input type="checkbox" name="checkbox11" value="${partnerUserList.id}" >
	    </display:column>
    </c:if>
	<!--权限控制移动人员才有删除和添加的权限 modify by fengguangping end-->
	<display:column property="name" sortable="true"      
			headerClass="sortable" title="人员姓名" href="${app}/partner/baseinfo/resumes.do?method=newExpert&isPartner=${isPartner}" paramId="id" paramProperty="id"/>
	<display:column property="userId" sortable="userNo"
			headerClass="sortable" title="人员ID"  paramId="id" paramProperty="id"/>
	<display:column  sortable="true" headerClass="sortable" title="性别"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${partnerUserList.sex}" beanId="ItawSystemDictTypeDao"/> 
	</display:column>		
	<display:column property="areaName" sortable="true"
			headerClass="sortable" title="所属地市"  paramId="id" paramProperty="id"/>
	<display:column property="deptName" sortable="true"
			headerClass="sortable" title="所属组织"  paramId="id" paramProperty="id"/>
	<display:column property="mobilePhone" sortable="true"
			headerClass="sortable" title="移动电话"  paramId="id" paramProperty="id"/>
	<display:column property="email" sortable="true"
			headerClass="sortable" title="Email"  paramId="id" paramProperty="id"/>
	</display:table>
</html:form>
<c:if test="${!empty partnerUserList}">
	<eoms:excelExport modelName="com.boco.eoms.partner.baseinfo.model.PartnerUser" 	 serviceBeanId="partnerUserMgr"   title="代维人员信息" >
			<eoms:row name="序号" value="id"/>
			<eoms:row name="姓名" value="name" />
			<eoms:row name="ID" value="userId" />
			<eoms:row name="员工编号" value="userNo" />
			<eoms:row name="性别" value="sex" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="民族" value="nationality" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="身份证号码" value="personCardNo"/>
			<eoms:row name="籍贯" value="nativePlace" />
			<eoms:row name="年龄" value="age" />
			<eoms:row name="学历" value="diploma" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="所属组织" value="deptName"/>
			<eoms:row name="所属地市" value="areaName"/>
			<eoms:row name="Email" value="email" />
			<eoms:row name="移动电话" value="mobilePhone" />
			<eoms:row name="集团短号" value="groupPhone" />
			<eoms:row name="毕业学校" value="graduateSchool" />
			<eoms:row name="所学专业" value="learnSpecialty" />
			<eoms:row name="加入系统时间" value="saveTime" />
			<eoms:row name="在岗状态" value="postState" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="经度" value="longtitude" />
			<eoms:row name="纬度" value="latitude" />
			<eoms:row name="离职时间" value="leavaTime" />
			<eoms:row name="离职原因" value="leavaReason" />
	</eoms:excelExport>
</c:if>
	<br><br>
		<c:if test="${requestScope.hasRightForDel=='1'}">
		<input type="button" value="增加" onclick="add('${isPartner}');">
		<input type="button" value="删除" onclick="isChecked()">
		</c:if>
	    <!--<c:if test="${!empty partnerUserList}">
			<input type="button" value="删除" onclick="isChecked()">
		</c:if> -->	
<%@ include file="/common/footer_eoms.jsp"%>
