<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
	function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
	            }
    }
	//专业 联动 设备类型	
	function changeFacility(con){
		
		    delAllOption("equipmentType");
			var speciality = document.getElementById("speciality").value;
			var url="<%=request.getContextPath()%>/partner/deviceAssess/backEstimates.do?method=changeFacility&speciality="+speciality;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									var equipmentType = "equipmentType";
									var arrOptions = json[0].facility.split(",");
									var obj=$(equipmentType);
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
	}

</script>

<html:form action="/backEstimates.do?method=searchBackEstimate" styleId="mainForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">设备厂家后评估</div>
	</caption>
	<tr>
		<td class="label">
			厂家
		</td>
		<td class="content" >
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" defaultValue="${bigFaultForm.factory}"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>			 
		</td>
		<td class="label">
			专业
		</td>
		<td class="content">
			<eoms:comboBox name="speciality" id="speciality" initDicId="11216" defaultValue="${bigFaultForm.speciality}"
			     onchange="changeFacility(0);"/>	
		</td>			
	</tr>
	<tr>
		<td class="label">
			设备类型
		</td> 
		<td class="content">						
			<select name="equipmentType" id="equipmentType" 
				alt="allowBlank:true,vtext:'请选择设备类型'" >
				<option value="">
					请选择设备类型
				</option>				
			</select>	
		</td>	
		<td class="label">
			年. 季度
		</td>
		<td class="content">
			<select id="year" name="year" >
					<option id="0" value="">请选择</option>
				<c:forEach begin="2008" end="2025" var="num">
					<c:if test="${yearValue ==num}">
						<option id="${num}" value="${num}" selected="true">${num}年</option>
					</c:if>
					<c:if test="${yearValue !=num}">
						<option id="${num}" value="${num}">${num}年</option>
					</c:if>					
				</c:forEach>
			</select>
			<select id="quarter" name="quarter" >
					<option id="0" value="">请选择</option>
				<c:forEach begin="1" end="4" var="num">
					<c:if test="${quarterValue ==num}">
						<option id="${num}" value="${num}" selected="true">第${num}季度</option>
					</c:if>
					<c:if test="${quarterValue !=num}">
						<option id="${num}" value="${num}">第${num}季度</option>
					</c:if>					
				</c:forEach>
			</select>
		</td>
	</tr>
</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="下一步" />
		</td>
	</tr>
</table>
<%-- <html:hidden property="mainId" value="${mainForm.mainId}" /> --%>
</html:form>
<div >
	<dl>
		<dt>帮助：</dt>
		<dd><br>
	    </dd>
	</dl>
</div>
<%@ include file="/common/footer_eoms.jsp"%>