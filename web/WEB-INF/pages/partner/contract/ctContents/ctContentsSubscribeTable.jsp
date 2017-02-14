<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<jsp:directive.page import="com.boco.eoms.partner.contract.table.util.CtTableGeneralConstants"/>

<script type="text/javascript">
	function onSubmit(){
		var arr = [<c:forEach items="${ctContentsSubscribeTableList}" var="obj" varStatus="status"><c:if test="${status.count > 1}">, </c:if>'${obj.themeId}'</c:forEach>];
		for(var i=0;i<arr.length;i++){
			if(document.forms[0].themeId.value==arr[i]){
				alert('已经被订阅过了');
				return false;
			}
		}	
	
    	if(document.forms[0].themeName.value==""){
    		alert('请选择要订阅分类');
    		return false; 
    	}
        document.forms[0].submit();
        return true;
   	}
   	
function search(){
	var url="${app}/partner/contract/ctContentsSubscribes.do?method=getNodesRadioTree";
    window.location.href = url;
}
</script>

<html:form action="/ctContentsSubscribes.do?method=saveSubscribeTable" styleId="ctContentsForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

	<eoms:xbox id="tree" dataUrl="${app}/partner/contract/ctTableThemes.do?method=getNodesRadioTreeForAll" 
	  	rootId="<%=CtTableGeneralConstants.TREE_ROOT_ID%>" 
	  	rootText="合同分类" 
	  	valueField="themeId" handler="themeName"
		textField="themeName"
		checktype="forums" single="true" 
	  />
	<table>
		<tr>
			<td>增加订阅</td>
			<td>
				<input type="text"   id="themeName" name="themeName" class="text" readonly="readonly"  
					value='<eoms:id2nameDB id="${ctTableGeneralForm.themeId}" beanId="ctTableThemeDao" />' alt="allowBlank:false'"/>
				<input type="hidden" id="themeId"   name="themeId" value="${ctTableGeneralForm.themeId}" />
			</td>
		</tr>
		<tr>
			<td>
			<input type="button" value="提交订阅" onclick="javascript:onSubmit();" class="button" />
			</td>
		</tr>
	</table>
	<display:table name="ctContentsSubscribeTableList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}" class="table ctContentsCollectList"
		export="false"
		requestURI="${app}/partner/contract/ctContentsSubscribes.do?method=saveSubscribeTable"
		>

		<display:column sortable="true" headerClass="sortable" title="订阅人">
			<eoms:id2nameDB id="${ctContentsList.subscribeUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column property="createTime"  title="订阅时间" sortable="true" 
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" title="订阅的分类">
			<eoms:id2nameDB id="${ctContentsList.themeId}" beanId="ctTableThemeDao" />
		</display:column>
		
		<display:column title="删除订阅" headerClass="imageColumn">
		    <a href="javascript:if(confirm('确定要删除该订阅?')){
		                        var id = '${ctContentsList.id }';
		                        var url='${app}/partner/contract/ctContentsSubscribes.do?method=removeTable';
		                        url = url + '&ID=' + id ;
		                        location.href=url}"><img src="${app}/images/icons/list-delete.gif"/></a>		    
		</display:column>
		
		<display:setProperty name="paging.banner.item_name"  value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />
	</display:table>
</fmt:bundle>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>