<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">

Ext.onReady(function(){
            v = new eoms.form.Validation({form:'lineForm'});
            });
function dealAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var calc=0;
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";";
			calc++;
		}
	}
	if (idResult == "") {
		alert("请选择需要处理的记录");
		return false;
	} else {
		//如果只有1条记录的话，那么直接弹出隐藏域进行处理，如果有多条记录的话，则转入批处理页面
		if(calc==1){
			if(confirm("确定处理记录吗？")){
				$("dealIds").value=idResult.toString();
				document.forms[0].submit();
			}else{
				return false;
			}
		}else{
			if(confirm("确定批量处理记录吗？")){
				$("dealIds").value=idResult.toString();
				document.forms[0].submit();
			}else{
				return false;
			}
		}
	}
}



function selectAll(){
	var cardNumberList = document.getElementsByName("cardNumber");
	//Judge whether it has been checked first. One element is enough for our decision.
	var temp=cardNumberList[0].checked;
	if(temp==null){
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked='checked';
		}
	}else{
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked=!cardNumberList[i].checked;
		}
	}
}
</script>
<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>


<span id="listQueryObject">

<html:form action="/materialSharing.do?method=list" styleId="MaterialSharingForm" method="post">
 
<table class="formTable">
    <caption>
        <div class="header center">培训资料查询</div>
    </caption>
            
    <tr>
        <td class="label">
            培训计划名
        </td>
        <td class="content">
                                    
            <html:text property="planName" styleId="planName"
                        styleClass="text medium"
                         />
                                                                                                
        </td>
        <td class="label">
            资料名
        </td>
        <td class="content">
                             <html:text property="materialName" styleId="materialName"
                        styleClass="text medium" />                                                                 
        </td>
    </tr>
            
    <tr>
        <td class="label">
            
            
            
            
    
</table>
<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
            <!--<input type="button" class="btn" value="<fmt:message key="button.clear"/>" />-->
        </td>
    </tr>
</table>
</html:form>
</span>




<logic:present name="materialList" scope="request">
	<display:table name="materialList" cellspacing="0" cellpadding="0"
		id="materialList" pagesize="${pagesize}" class="table materialList" export="false"
		requestURI="/partner/training/materialSharing.do?method=list" sort="list"
		partialList="true" size="${size}">

		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber" value="${materialList.id}" />
		</display:column>
		<display:column property="planName" sortable="true"
			headerClass="sortable" title="培训计划名称" />
			<display:column  sortable="true"
			headerClass="sortable" title="培训资料名称" >
			<a href="${app}/partner/training/materialSharing.do?method=detail&id=${materialList.id}">${materialList.materialName}</a>
			</display:column>
			<display:column sortable="true"
			headerClass="sortable" title="培训发布人" >
			<eoms:id2nameDB id="${materialList.releaseUser}"
				beanId="tawSystemUserDao" />
			</display:column>
			<display:column  sortable="true"
			headerClass="sortable" title="所属部门" >
			<eoms:id2nameDB id="${materialList.dept}"
				beanId="tawSystemDeptDao" />
			</display:column>
			
		
		<display:column property="beginTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="培训开始时间" />
			<display:column property="days" sortable="true"
			headerClass="sortable" title="培训天数" />
			<display:column property="accessoriesId" sortable="true"
			headerClass="sortable" title="附件" />

	</display:table>
</logic:present>

<%@ include file="/common/footer_eoms.jsp"%>