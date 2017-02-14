<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridKPIYearForm'});
});
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
function createRequest()
{
	var xmlhttp = null;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
}
function getResponseText(url) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
    xmlhttp.send(null);
    return xmlhttp.responseText;
}

function changeCity(con)
		{    
		    delAllOption("companyName");//地市选择更新后，重新刷新合作伙伴
		    delAllOption("gridName");//地市选择更新后，重新刷新网格
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeToPartner&region="+region;

			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									 if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									
			
									var companyNameName = "companyName";
									var arrOptionsP=json[0].pb.split(",");
									var objp=$(companyNameName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsP.length;m++){
										var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}
									
									var gridName1 = "gridName";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName1);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}
									if(con==1){
										var companyName = '${gridKPIYearForm.companyName}';
										if(companyName!=''){
									 		document.getElementById("companyName").value = companyName;
										}
									}		
									changeGrid(con);							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
			
			
		}

function changeGrid(con){
    var providerValue = document.getElementById("companyName").value;
    if(providerValue!=''){
	    delAllOption("gridName");//合作伙伴和县区选择更新后，重新刷新网格
	    var regionValue = document.getElementById("region").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeGrid&city="+regionValue;
	    
			Ext.Ajax.request({
									url : url ,
									method: 'POST',
									params:{providerValue:providerValue},
									success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
										var json = eval(res);
								     	var countyName = "gridName";
										var arrOptions = json[0].gl.split(",");
										var obj=$(countyName);
										var i=0;
										var j=0;
										for(i=0;i<arrOptions.length;i++){
												var opt=new Option(arrOptions[i+1],arrOptions[i]);
											obj.options[j]=opt;
											j++;
											i++;
										}
										if(con==1){
											var gridName = '${gridKPIYearForm.gridName}';
											if(gridName!=''){
										 		document.getElementById("gridName").value = gridName;
											}												
										}
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}			
	

</script>

<html:form action="/gridKPIYears.do?method=save" styleId="gridKPIYearForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="gridKPIYear.form.heading"/></div>
	</caption>
<!-- 年 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select id="year" name="year" alt="allowBlank:false">
					<option id="0" value="">请选择</option>
				<c:forEach begin="2008" end="2025" var="num">
					<c:if test="${gridKPIYearForm.year ==num}">
						<option id="${num}" value="${num}" selected="true">${num}年</option>
					</c:if>
					<c:if test="${gridKPIYearForm.year !=num}">
						<option id="${num}" value="${num}">${num}年</option>
					</c:if>					
				</c:forEach>
			</select>	
			<html:hidden property="year" value="${gridKPIYearForm.year}" />									
		</td>
<!-- 区域地市 -->
		<td class="label">
			<fmt:message key="gridKPIYear.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'"  onchange="changeCity(0);">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
						
		</td>
	</tr>
	<tr>
<!-- 基站告警数量削减 -->
		<td class="label">
			<fmt:message key="gridKPIYear.bswReduce" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="bswReduce" styleId="bswReduce"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.bswReduce}" />
		</td>
<!-- 维护公司 -->
		<td class="label">
			<fmt:message key="gridKPIYear.companyName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select name="companyName" id="companyName" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'" onchange="changeGrid(0);">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
		</td>
	</tr>
<!-- 网月度基站告警比例 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.bsWarning" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="bsWarning" styleId="bsWarning"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.bsWarning}" />
		</td>
<!-- 网格名称 -->
		<td class="label">
			<fmt:message key="gridKPIYear.gridName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select name="gridName" id="gridName" 
				alt="allowBlank:false,vtext:'请选择所属网格'" >
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
		</td>
	</tr>
<!-- 最坏小区比 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.mbplot" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="mbplot" styleId="mbplot"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.mbplot}" />
		</td>
<!-- 无线接入性 -->
		<td class="label">
			<fmt:message key="gridKPIYear.wirelessIn" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="wirelessIn" styleId="wirelessIn"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.wirelessIn}" />
		</td>
	</tr>
<!-- 掉话率 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.dropCallRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="dropCallRate" styleId="dropCallRate"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.dropCallRate}" />
		</td>
<!-- 基站退服率 -->
		<td class="label">
			<fmt:message key="gridKPIYear.bsOutRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="bsOutRate" styleId="bsOutRate"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.bsOutRate}" />
		</td>
	</tr>	
<!-- 接入网重大故障率 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.mostfailRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="mostfailRate" styleId="mostfailRate"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.mostfailRate}" />
		</td>
<!-- 季度板件无故障返修率 -->
		<td class="label">
			<fmt:message key="gridKPIYear.nofaiRamRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="nofaiRamRate" styleId="nofaiRamRate"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.nofaiRamRate}" />
		</td>
	</tr>
<!-- 网络故障（基站）工单处理及时率 -->
	<tr>
		<td class="label" colspan="1">
			<fmt:message key="gridKPIYear.workOrderATR" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			<html:text property="workOrderATR" styleId="workOrderATR"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridKPIYearForm.workOrderATR}" />
		</td>
		
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty gridKPIYearForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url=eoms.appPath+'/partner/maintenance/gridKPIYears.do?method=remove&id=${gridKPIYearForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${gridKPIYearForm.id}" />
</html:form>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${gridKPIYearForm.region}';
	if(region!=''){
 		document.getElementById("region").value = region;
 		changeCity(1);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>