<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8" %>
<html>

<body>
<form action="material.do?method=save" method="post">
<table>
	<tr>
		<td>  
			材料编码<input type="text" name="encode">
		</td>
		
		<td>
			材料类别<select name="categoryNum">
			<option value=1>类别一</option>
			<option value=2>类别二</option>
			<option value=3>类别三</option>
			</select>
		</td>
</tr>
<tr>
		<td>
			材料名称<input type="text" name="name">
		</td>
		
		<td>
			材料规格<input type="text" name="specification">
		</td>
</tr>
<tr>
		<td>
			材料条码<input type="text">
		</td>
		
		<td>
			生产厂家<input type="text" name="manufacturer">
		</td>
</tr>
<tr>
		<td>
			材料单价<input type="text" name="inputPrice">
		</td>
		
		<td>
			材料单位<input type="text" name="unit">
		</td>
</tr>
<tr>
		<td>
			库存上限<input type="text" name="inventoryMax">
		</td>
		
		<td>
			库存下限<input type="text" name="inventoryMin">
		</td>
</tr>
<tr>
		<td>
			材料备注<input type="text" name="remark">
		</td>
		
		<td>
			材料图片<input type="file">
		</td>
</tr>
</table>

<input type="submit" value="保存">

 <input type="button" class="bnt_an" value="返回"
	onclick="history.back();" />

</form>


<%@ include file="/common/footer_eoms.jsp"%>
</body>
</html>