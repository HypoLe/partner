<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">
	function conf(){
		var checks = document.getElementsByName('checkall');
		var len = checks.length;
		var requireId ;
		var checkedCount=0 ;
		for(var i =0; i<checks.length; i++){
			if(checks[i].checked){
				checkedCount++;
			}
		}
		if(checkedCount>1){
			alert('只能选择一个');
			return;
		}
		if(checkedCount==1){
			for(var i =0; i<checks.length; i++){
				if(checks[i].checked){
					requireId = checks[i].value;
				}
			}
		}
	   window.close();    
       window.opener.getRequire(requireId); 
   }
</script>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">


	<display:table name="trainRequireList" cellspacing="0" cellpadding="0"
		id="trainRequireList" pagesize="${pageSize}" class="table trainRequireList"
		export="false"
		requestURI="${app}/kmmanager/trainRequires.do?method=search"
		sort="list" partialList="true" size="resultSize">
	
	<display:column sortable="true" headerClass="sortable" title="主键">
			<input type="checkbox" indexed="true"   id="checkall" name="checkall" value="${trainRequireList.id}"/>
	</display:column>	

	<display:column property="trainNo" sortable="true" title="需求编号"
			headerClass="sortable"   paramId="id" paramProperty="id"/>
	
	<display:column property="trainQuestion" sortable="true"
			headerClass="sortable" titleKey="trainRequire.trainQuestion"  paramId="id" paramProperty="id"/>
	
	<display:column sortable="true" headerClass="sortable" titleKey="trainRequire.trainUser">
		<eoms:id2nameDB id="${trainRequireList.trainUser}" beanId="tawSystemUserDao" />
	</display:column>

	<display:column  sortable="true" headerClass="sortable" titleKey="trainRequire.trainDept" >
			<eoms:id2nameDB id="${trainRequireList.trainDept}" beanId="tawSystemDeptDao" />
	</display:column>
	
	<display:column property="trainVender" sortable="true"
			headerClass="sortable" titleKey="trainRequire.trainVender"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="trainRequire.trainSpeciality" >
		<eoms:id2nameDB id="${trainRequireList.trainSpeciality}" beanId="trainSpecialtyDao" />
	</display:column>

	<display:column property="trainType" sortable="true" title="设备类型"
			headerClass="sortable"   paramId="id" paramProperty="id"/>
	
	<display:column property="trainDate" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="trainRequire.trainDate"  paramId="id" paramProperty="id"/>
	
	<display:column title="查看" headerClass="imageColumn">
		    <a href="javascript:var id = '${trainRequireList.id}';
		                        var url='${app}/kmmanager/trainRequires.do?method=detail';
		                        url = url + '&id=' + id ;
		                        location.href=url">
		       <img src="${app}/images/icons/search.gif"/></a>		    
	</display:column> 
		
		<display:setProperty name="paging.banner.item_name" value="trainRequire" />
		<display:setProperty name="paging.banner.items_name" value="trainRequires" />
	</display:table>

	<input type="button" style="margin-right: 5px" onclick="conf()" value="确定"/>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>