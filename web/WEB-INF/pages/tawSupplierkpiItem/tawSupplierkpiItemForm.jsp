<%@ page language="java" import="java.util.*, com.boco.eoms.commons.system.role.model.TawSystemSubRole" %> 
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
String dictId = request.getAttribute("dictId").toString();
String serviceType = request.getAttribute("serviceType").toString();
%>
<script type="text/javascript">
Ext.onReady(function(){
	colorRows('list-table');
    //init Form validation and styles
	//valider({form:'tawSupplierkpiItemForm',vbtn:'method.save'});
})

function validate() {
  var kpiName = document.forms[0].kpiName.value;
  var dataSource = document.forms[0].dataSource.value;
  var dataType = document.forms[0].dataType.value;
  var isImpersonality = document.forms[0].isImpersonality.value;
  var statictsCycle = document.forms[0].statictsCycle.value;
  var unit = document.forms[0].unit.value;
  var assessMoment = document.forms[0].assessMoment.value;
  var preActor = document.forms[0].preActor.value;
  var assessContent = document.forms[0].assessContent.value;
  
  if (kpiName.length <= 0) {
    alert("${eoms:a2u('请填写评估指标名称！')}");
    return false;
  }
  else if (dataSource.length <= 0) {
    alert("${eoms:a2u('请选择数据来源！')}");
    return false;
  }
  else if (dataType.length <= 0) {
    alert("${eoms:a2u('请选择数据类型！')}");
    return false;
  }
  else if (isImpersonality.length <= 0) {
    alert("${eoms:a2u('请选择是否客观评价！')}");
    return false;
  }
  else if (unit.length <= 0) {
    alert("${eoms:a2u('请选择单位！')}");
    return false;
  }
  else if (assessMoment.length <= 0) {
    alert("${eoms:a2u('请选择评估阶段！')}");
    return false;
  }
  else if (preActor.length <= 0) {
    alert("${eoms:a2u('请选择确定的预定执行角色！')}");
    return false;
  }
  else if (assessContent.length <= 0) {
    alert("${eoms:a2u('请填写评估内容！')}");
    return false;
  }
  else if (statictsCycle.length <= 0) {
  	if (assessMoment == "106010603") {
  	  alert("${eoms:a2u('请选择评估周期！')}");
    return false;
  	}
  }
  else {
    return true;
  }
}

function setCycle() {
  var assessMoment = document.forms[0].assessMoment.value;
  if (assessMoment == "106010603") {
    document.forms[0].statictsCycle.disabled=false;
  }
  else {
    document.forms[0].statictsCycle.disabled=true;
  }
}
function setDataType(){
  var dataType = document.forms[0].dataType.value;
  var isImpersonality = document.forms[0].isImpersonality.value;
  if(isImpersonality == '1030102'&& dataType == '106010203'){
     alert("${eoms:a2u('非主观评价,数据类型不能是文字！')}");
     document.forms[0].dataType.value = '106010201';
  }
}
</script>
<fmt:bundle basename="com/boco/eoms/extra/supplierkpi/config/ApplicationResources-supplierkpi">
<div class="list-title">
	<fmt:message key="tawSupplierkpiItemDetail.heading"/>
</div>
</fmt:bundle>
<html:form action="saveTawSupplierkpiItem" method="post" styleId="tawSupplierkpiItemForm" onsubmit="return validate();"> 
<html:hidden property="dictId" value="<%=dictId%>" />
<html:hidden property="assessStatus" value="1" />
<div class="list">
<table cellspacing="0" cellpadding="0" border="0" id="list-table">
	<tr height="40">
		<td width="20%">
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.kpiName"/>
		</td>
		<td colspan="3">
			<html:text property="kpiName" styleId="kpiName" styleClass="text medium" style="width:88%" />
		</td>
		
	</tr>
	<tr height="40">
		<td width="20%">
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.dataSource"/>
		</td>
		<td width="30%">
			<eoms:dict key="dict-supplierkpi" dictId="dataSource" isQuery="false"
				defaultId="106010101" selectId="dataSource" beanId="selectXML" />
		</td>

		<td width="20%">
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.dataType"/>
		</td>
		<td width="30%">
			<eoms:dict key="dict-supplierkpi" dictId="dataType" isQuery="false"
				defaultId="106010201" selectId="dataType" beanId="selectXML" onchange="setDataType()" />
		</td>
	</tr>
	<tr height="40">
		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.isImpersonality"/>
		</td>
		<td>
			<eoms:dict key="dict-supplierkpi" dictId="isImpersonality" isQuery="false"
				defaultId="1030102" selectId="isImpersonality" beanId="selectXML" />
		</td>

		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.unit"/>
		</td>
		<td>
			<eoms:dict key="dict-supplierkpi" dictId="unit" isQuery="false"
				defaultId="106010501" selectId="unit" beanId="selectXML" />
		</td>
			
	</tr>
	<tr height="40">
		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.assessMoment"/>
		</td>
		<td>
			<eoms:dict key="dict-supplierkpi" dictId="assessMoment" isQuery="false"
				defaultId="${defaultId}" selectId="assessMoment" beanId="selectXML" onchange="setCycle()" />
		</td>

		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.statictsCycle"/>
		</td>
		<td>
			<eoms:dict key="dict-supplierkpi" dictId="statictsCycle" isQuery="false"
				defaultId="${defaultId}" selectId="statictsCycle" beanId="selectXML" />
		</td>
			
	</tr>
	<!--tr height="40">
		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.writeManner"/>
		</td>
		<td colspan="3">
			<eoms:comboBox name="writeManner" id="writeManner" initDicId="1060104" styleClass="select-class" defaultValue="106010401"/>
		</td>
	</tr-->
	<tr height="40">
		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.preActor"/>
		</td>
		<td colspan="3">
			<select name="preActor" id="preActor" >
			<%if ("10402".equals(serviceType)) {%>
			<option value="">${eoms:a2u('请选择')}</option>
			<option value="">${eoms:a2u('－－角色组－－')}</option>
			<option value="102">${eoms:a2u('地市评估管理员')}</option>
			<option value="95">${eoms:a2u('专业评估管理员')}</option>
			<%}%>
			<option value="">${eoms:a2u('－－子角色－－')}</option>
			<%List subRoles = (List)request.getAttribute("subRoles");%>
        	<%for (int i = 0; i < subRoles.size(); i++) { 
         		TawSystemSubRole subRole = (TawSystemSubRole) subRoles.get(i);%>
            	<option value="<%=subRole.getId() %>"><%=subRole.getSubRoleName() %></option>
        	<%}%>
        	</select>
		</td>
		<!-- td>
			${eoms:a2u('如果选择角色组对象，则角色组下所有子角色均生成评估任务,主要是为代维类型设计')}
		</td-->
	</tr>
	<tr height="40">
		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.assessContent"/>
		</td>
		<td colspan="3">
			<html:textarea property="assessContent" styleId="assessContent" styleClass="textarea small" style="width:88%"/>
		</td>
	</tr>
	<tr height="40">
		<td>
			<eoms:label styleClass="desc" key="tawSupplierkpiItemForm.assessNote"/>
		</td>
		<td colspan="3">
			<html:textarea property="assessNote" styleId="assessNote" styleClass="textarea small" style="width:88%"/>
		</td>
	</tr>
	<tr align="left" height="15">
		<td colspan="4" width="20">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
<script type="text/javascript">
	document.forms[0].statictsCycle.disabled=true;
</script>
</table>
</div>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>