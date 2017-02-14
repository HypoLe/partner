<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8" %>
<html>

<body>
<form action="material.do?method=select" method="post">
<table>
	<tr>
		<td> 
			材料编码<input type="text">
		</td>
		
		<td>
			材料类别<select>
			<option value=1>类别一</option>
			<option value=2>类别二</option>
			<option value=3>类别三</option>
			</select>
		</td>
</tr>
<tr>
		<td>
			材料名称<input type="text">
		</td>
		
		<td>
			材料条码<input type="text">
		</td>
</tr>
</table>

<input type="submit" value="查询">

<input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/materials/material.do?method=addMaterial"/>'"
        value="新增材料"/>
<input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/"/>'"
        value="返回"/>

</form>

<display:table name="list" cellspacing="0" cellpadding="0"
    id="list"   sort="list" class="table">

    <display:column property="id" sortable="true" headerClass="sortable"
         title="ID"/>      
    <display:column property="encode" sortable="true" headerClass="sortable"
         title="材料编码"/>
    <display:column property="name" sortable="true" headerClass="sortable"
         title="材料名称"/>
    <display:column property="unit" sortable="true" headerClass="sortable"
         title="材料单位"/>
    <display:column property="inputPrice" sortable="true" headerClass="sortable"
         title="单价"/>
   	<display:column sortable="true" headerClass="sortable"
			title="选择">
			<html:link href="${app}/contract/tawContracts.do?method=detail"
				paramId="id" paramProperty="id" paramName="list"
				>
						选择
					</html:link>

		</display:column>
    

</display:table>
<%@ include file="/common/footer_eoms.jsp"%>
</body>
</html>