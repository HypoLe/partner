<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
 Ext.onReady(function(){
          var v = new eoms.form.Validation({form:'facilityQuantityForm'});
          
 });
</script>

<div>
<form action="facilityquantity.do?method=add" method="post" id="facilityQuantityForm" name="facilityQuantityForm" >

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<table id="sheetone" class="formTable">
<tr>
	
		<td class="label">
			<fmt:message key="facilityNum.factory" />
		</td>
		<td class="content">
			<eoms:comboBox name="factory" id="factory" initDicId="1121502"  onchange="check(this.value)"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>		
		</td>
		</tr>
		</table>
<table id="sheet" class="formTable">
	
	<logic-el:present name="mapObjList">
		<tbody>
	<td class="label">专业</td><td class="label" colspan="100">设备类型</td>
			<c:forEach items="${mapObjList}" var="tdList">
			
				<tr>
				<td class="label">${tdList.key}</td>
				
					<c:forEach items="${tdList.list}" var="td">
					<td class="label">${td}</td>
					<td ><input size="5" type="text" name="${td}"  onblur="javascript:numberCheck(this);"
					id="${td}"  /></td>	
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</logic-el:present>
</table>
</fmt:bundle>

   <html:submit styleClass="btn"  property="method.save" 
	        styleId="method.save" value="新增设备量信息" ></html:submit>
</div>

<script type="text/javascript">

    function check(factory){
     Ext.Ajax.request({
					url:"${app}/partner/deviceAssess/facilityquantity.do",
					params:{method:"checkfactory",factory:factory},
					dataType: "JSON",
					success:function(result) {
					var er = eval(result.responseText);
					
					if(er[0].writerResult==0){
					
					alert("该厂家设备信息已存在，请点击设备量信息列表页面进行修改");
					document.getElementById("factory").value=''
					}
					}
 });
}

function numberCheck(obj){
         var price=/^\d+$/;
         var numberValue=obj.value; 
         if(!numberValue.match(price) && ""!=numberValue){
         alert("格式不正确，设备数量只能为非负整数");
					obj.value='';
         }
}
</script>


<%@ include file="/common/footer_eoms.jsp"%>