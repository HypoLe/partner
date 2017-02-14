<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'serviceSpeedForm'});
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

//县区联动
function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
		    delAllOption("grid");
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									var countyName = "city";
						
									var arrOptions = json[0].cb.split(",");
									var obj=$(countyName);
											
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									var providerName = "provider";
									var arrOptionsP=json[0].pb.split(",");
									var objp=$(providerName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsP.length;m++){
										var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
										objp.options[n]=optp;
										n++;
										m++;
										
									}
									
									var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
									
									if(con==1){
										var city = '${serviceSpeedForm.city}';
										if(city!=''){
											document.getElementById("city").value = city;
										}	
									}	
									changePartner(con);							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
		
//地市、县区联动合作伙伴		
function changePartner(con)
		{    
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
		    delAllOption("grid");
			//地市
			var regionValue = document.getElementById("region").value;
			//县区
			var cityValue = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changePartner&region="+regionValue+"&city="+cityValue;
			//var result=getResponseText(url);
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							//params:{providerValue:providerValue},
							success: function ( result, request ) { 
							res = result.responseText;
							if(res.indexOf("<\/SCRIPT>")>0){
						  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
							}
								var json = eval(res);
								
								var providerName = "provider";
								var arrOptionsP=json[0].pb.split(",");
								var objp=$(providerName);
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
									
								}
								
								var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
								if(con==1){
										var provider = '${serviceSpeedForm.provider}';
										if(provider!=''){
											document.getElementById("provider").value = provider;
										}
										
										changeGrid(con);
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
		
		
		
//地市、县区、合作伙伴 联动 网格		
function changeGrid(con){
    var cityValue = document.getElementById("city").value;
    var providerValue = document.getElementById("provider").value;
    if(cityValue!=''&&providerValue!=''){
	    delAllOption("grid");//合作伙伴和县区选择更新后，重新刷新网格
	    var regionValue = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeGrid&city="+regionValue+"&cityValue="+cityValue;
		
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
		     					var countyName = "grid";
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
										var grid = '${serviceSpeedForm.grid}';
										if(grid!=''){
											document.getElementById("grid").value = grid;
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

<html:form action="/serviceSpeeds.do?method=save" styleId="serviceSpeedForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="serviceSpeed.form.heading"/></div>
	</caption>
<tr>
<!-- 地市地域 -->		
		<td class="label">
			<fmt:message key="serviceSpeed.region" />&nbsp;<font color='red'>*</font>
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
		
		<td class="label"> 
			县区&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 县区 -->			
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'" onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>

		</td>			
	</tr>

	<tr>
<!-- 合作伙伴 -->		
		<td class="label">
			<fmt:message key="serviceSpeed.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'" onchange="changeGrid(0);">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
		</td>	
		
			<!-- 网格 -->		
		<td class="label">
			<fmt:message key="serviceSpeed.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<!-- 所属网格 -->
			<select name="grid" id="grid" 
				alt="allowBlank:false,vtext:'请选择所属网格'" >
				<option value="">
					--请选择所属网格--
				</option>				
			</select>		
		</td>	
	</tr>
	</tr>

	<tr>
	
	
		<!-- 年 -->
		<td class="label">
			<fmt:message key="serviceSpeed.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select id="year" name="year" alt="allowBlank:false">
					<option id="0" value="">请选择</option>
				<c:forEach begin="2008" end="2025" var="num">
					<c:if test="${serviceSpeedForm.year ==num}">
						<option id="${num}" value="${num}" selected="true">${num}年</option>
					</c:if>
					<c:if test="${serviceSpeedForm.year !=num}">
						<option id="${num}" value="${num}">${num}年</option>
					</c:if>					
				</c:forEach>
			</select>	
			<html:hidden property="year" value="${serviceSpeedForm.year}" />									
		</td>
	<!-- 月份 -->
		<td class="label">
			<fmt:message key="serviceSpeed.month" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select id="month" name="month" alt="allowBlank:false">
					<option id="0" value="">请选择</option>
				<c:forEach begin="1" end="12" var="num">
					<c:if test="${serviceSpeedForm.month ==num}">
						<option id="${num}" value="${num}" selected="true">${num}月</option>
					</c:if>
					<c:if test="${serviceSpeedForm.month !=num}">
						<option id="${num}" value="${num}">${num}月</option>
					</c:if>					
				</c:forEach>
			</select>
			<html:hidden property="month" value="${serviceSpeedForm.month}" />						
		</td>
	</tr>
<!-- 网络故障响应度 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.webfailure" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="webfailure" styleId="webfailure"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" value="${serviceSpeedForm.webfailure}" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 客户投诉处理响应度 -->
		<td class="label">
			<fmt:message key="serviceSpeed.customerComplaints" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="customerComplaints" styleId="customerComplaints"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" value="${serviceSpeedForm.customerComplaints}" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 表报上报及时率 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.fromReport" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fromReport" styleId="fromReport"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" value="${serviceSpeedForm.fromReport}" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 表报上报准确率 -->
		<td class="label">
			<fmt:message key="serviceSpeed.fromPrecision" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fromPrecision" styleId="fromPrecision"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" value="${serviceSpeedForm.fromPrecision}" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 资料更新准确率 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.datumUpdate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="datumUpdate" styleId="datumUpdate"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" value="${serviceSpeedForm.datumUpdate}" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 应急通信保障响应度 -->
		<td class="label">
			<fmt:message key="serviceSpeed.commSecurity" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="commSecurity" styleId="commSecurity"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" value="${serviceSpeedForm.commSecurity}" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 对基层业务、服务的响应度 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.toService" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<html:text property="toService" styleId="toService"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" value="${serviceSpeedForm.toService}" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
		
<!-- 上报人 -->
		<td class="label">
			<fmt:message key="serviceSpeed.reportUser" />
		</td>
		<td class="content">
			<html:hidden property="reportUser" value="${serviceSpeedForm.reportUser}" />
			<eoms:id2nameDB id="${serviceSpeedForm.reportUser}" beanId="tawSystemUserDao" />
		</td>
		
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty serviceSpeedForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url=eoms.appPath+'/partner/maintenance/serviceSpeeds.do?method=remove&id=${serviceSpeedForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${serviceSpeedForm.id}" />
</html:form>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${serviceSpeedForm.region}';
	if(region!=''){
 		document.getElementById("region").value = region;
 		changeCity(1);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>