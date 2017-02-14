<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

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
	    document.forms[1].action = "${app}/partner/baseinfo/partnerUserAndDepts.do?method=remove";
	    var flag = false;
	    for(var i=0; i<objs.length; i++){
	       if(objs[i].checked==true){
	           flag=true;
	           break;
	       }
	    }
	    if(flag==true){
		    if(confirm("确定删除吗?")){
		       document.forms[1].submit();
		       return true;
		    }else{
		    	return false;
		    }
	    }
	    else if(flag==false){
	        alert("请选择删除项！");
	        return false;
	    }
	}
</script>
<c:set var="buttons">
		  <br/>
	<input type="button" class="btn" value="添加"  
		onclick="javascript:
						var url=eoms.appPath

+'/partner/baseinfo/partnerUserAndDepts.do?method=add';
						location.href=url"/>
	
	
	<html:button style="margin-right: 5px" property="button1" onclick="isChecked()" styleClass="btn"> 
        删除
    </html:button>	
    
</c:set>

<html:form action="/partnerUserAndDepts.do?method=search" styleId="partnerUserAndDeptForm" 

method="post"> 
<table class="formTable" >
	<caption>
		<div class="header center">故障调度人员</div>
	</caption>
	<tr>
		<td class="label">
			人员姓名
		</td>
		<td class="content">
			<input type="text" name="nameSearch" id="nameSearch"
						class="text medium"
						alt="allowBlank:false,vtext:''" />
		</td>

		<td class="label">
			用户ID
		</td>
		<td class="content">
			<input type="text" name="userIdSearch" id="userIdSearch"
						class="text medium"
						alt="allowBlank:false,vtext:''" />
		</td>
	</tr>

</table>

<table align="center">
	<tr>
		<td>
			<input type="submit" class="btn" value="查询" />
			
			<input type="reset" class="btn" value="重置" />
		</td>
	</tr>
</table>
</html:form>

<html:form action="partnerUserAndDepts" styleId="partnerUserAndDeptForm" method="post"> 
<content tag="heading" >
	故障调度人员
</content>

	<display:table name="partnerUserAndDeptList" cellspacing="0" cellpadding="0"
		id="partnerUserAndDeptList" pagesize="${pageSize}" class="table partnerUserAndAreaList"
		export="false"
		requestURI="${app}/partner/baseinfo/partnerUserAndDepts.do?method=search"
		sort="list" partialList="true" size="resultSize">
		
    <display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
         <input type="checkbox" name="checkbox11" value="<c:out 

value='${partnerUserAndDeptList.id}'/>" >
    </display:column>
    
	<display:column property="name" sortable="true"
			headerClass="sortable" title="人员姓名"  paramId="id" 

paramProperty="id"/>

	<display:column property="userId" sortable="true"
			headerClass="sortable" title="用户ID" 

href="${app}/partner/baseinfo/partnerUserAndDepts.do?method=edit" paramId="id" 

paramProperty="id"/>

	<display:column  title="部门">
	
			<c:forTokens items="${partnerUserAndDeptList.deptId}" delims="," 

var="dept_ID" varStatus="status"><eoms:id2nameDB id="${dept_ID}" 

beanId="tawSystemDeptDao"/><c:choose><c:when 

test="${status.last}"></c:when><c:otherwise>,</c:otherwise></c:choose></c:forTokens>
	</display:column>

		<display:setProperty name="paging.banner.item_name" value="partnerUserAndDept" 

/>
		<display:setProperty name="paging.banner.items_name" 

value="partnerUserAndDepts" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>