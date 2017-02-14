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
		   document.forms[1].action = "${app}/partner/appops/partnerAppOpsUsers.do?method=remove";
	       document.forms[1].submit();
	       return;
		}
	}
	function add(isPartner){
		window.location.href="<%=request.getContextPath()%>/partner/appops/partnerAppOpsUsers.do?method=toAddPage&isPartner="+isPartner;
	}

	function setNullValue(){
		var name = document.getElementById("name");
		name.value="";    
		var provName = document.getElementById("provName");
		provName.value="";
		var prov = document.getElementById("prov");
		prov.value="";
		    
		var email = document.getElementById("email");
		email.value="";  
		  
		var mobilePhone = document.getElementById("mobilePhone");
		mobilePhone.value="";
		    
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
<html:form action="partnerAppOpsUsers.do?method=searchAppOpsUser" method="post"  styleId="partnerUserForm">
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
				value="<eoms:id2nameDB id="${prov}" beanId="partnerAppopsDeptDao"/>"
				alt="allowBlank:false" readonly="readonly" />
			<input name="prov" id="prov" value="${prov}" type="hidden" />
			<eoms:xbox id="provTree"
				dataUrl="${app}/xtree.do?method=getPnrDeptWithRightDW&isPartner=${isPartner}"
				rootId="" rootText="组织树" valueField="prov" handler="provName"
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
			<input type="button" class="btn" onclick="setNullValue();" value="重置" >
			 <input type="hidden" value="tomgr" name = "operationType"/>
		</td>
	</tr>
</table>
</html:form>	
</div>
<html:form action="partnerAppOpsUsers" method="post"  styleId="partnerUserForm">
	<display:table name="partnerUserList" cellspacing="0" cellpadding="0"
		id="partnerUserList" pagesize="${pageSize}" class="table partnerUserList"
		export="false" requestURI="${app}/partner/appops/partnerAppOpsUsers.do?method=searchAppOpsUser"
		sort="list" partialList="true" size="resultSize">
		
    <display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
         <input type="checkbox" name="checkbox11" value="<c:out value='${partnerUserList.id}'/>" >
    </display:column>

	<display:column property="userName" sortable="true"      
			headerClass="sortable" title="姓名" href="${app}/partner/appops/partnerAppOpsUsers.do?method=toAddPage" paramId="id" paramProperty="id"/>
	
	<display:column  sortable="true"
			headerClass="sortable" title="性别"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${partnerUserList.userSex}" beanId="ItawSystemDictTypeDao"/> 
	</display:column>		

	
	<display:column property="phone" sortable="true"
			headerClass="sortable" title="手机"  paramId="id" paramProperty="id"/>

	<display:column property="email" sortable="true"
			headerClass="sortable" title="邮箱"  paramId="id" paramProperty="id"/>			
				
		<display:setProperty name="paging.banner.item_name" value="partnerUser" />
		<display:setProperty name="paging.banner.items_name" value="partnerUsers" />

	<c:out value="${buttons}" escapeXml="false" />
		
  </display:table>
</html:form>
<c:if test="${!empty partnerUserList}">
	<eoms:excelExport modelName="com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser" 	 serviceBeanId="pnrAppopsUserService"   title="运维人员信息" >		
			<eoms:row name="1.分公司*" value="company" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="2.员工编号" value="userNumber" />
			<eoms:row name="3.姓名*" value="userName" />
			<eoms:row name="4.性别*" value="userSex" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="5.出生年份*" value="userBorth" />
			<eoms:row name="6.固定电话" value="telephone" />
			<eoms:row name="7.手机*" value="phone" />
			<eoms:row name="8.邮箱*" value="email" />
			<eoms:row name="9.参加工作时间*" value="workTime" />
			<eoms:row name="10.入司时间*" value="companyTime" />
			<eoms:row name="11.从事维护工作时间*" value="appopsWorkTime" />
			<eoms:row name="12.最高学历*" value="highestDegree" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="13.最高学位毕业院校*" value="school"/>
			<eoms:row name="14.第一学位" value="firstDegree" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="15.第一学位专业" value="firstSpecialty" />
			<eoms:row name="16.第二学位" value="secondDegree" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="17.第二学位专业" value="secondSpecialty" />
			<eoms:row name="18.其它证书" value="certificate" />
			<eoms:row name="19.员工类别*" value="workSort" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="20.职称*" value="jobTitle" dictDaoName="ItawSystemDictTypeDao" />
			<eoms:row name="21.用工类别*" value="useSort" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="22.岗位职级*" value="jobLevel" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="23.管理层级*" value="managerLevel" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="24.所在部门*" value="deptName" />
			<eoms:row name="25.组织编码(所在部门编码)*" value="deptCode" />
			<eoms:row name="26.所在班组*" value="teamName" />
			<eoms:row name="27.专业类别*" value="specialty" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="28.工作岗位--专职*" value="operatingPostZ" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:row name="29.工作岗位--兼职*" value="operatingPostJName"/>
			<eoms:row name="30.工作职责描述*" value="workDescribe" />			
	</eoms:excelExport>
</c:if>

</br>
		<c:if test="${requestScope.hasRightForDel=='1'}">
		<input type="button" value="增加" onclick="add('${isPartner}');">
		<input type="button" value="删除" onclick="isChecked()">
		</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
