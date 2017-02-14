<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridSatisfactionForm'});
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
										var city = '${gridSatisfactionForm.city}';
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
										var provider = '${gridSatisfactionForm.provider}';
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
										var grid = '${gridSatisfactionForm.grid}';
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

<html:form action="/gridSatisfactions.do?method=save" styleId="gridSatisfactionForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="gridSatisfaction.form.heading"/></div>
	</caption>

	<tr>



<!-- 地市区域 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
<!-- 			<html:text property="region" styleId="region"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridSatisfactionForm.region}" />
 -->						
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
			<fmt:message key="gridSatisfaction.provider" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="gridSatisfaction.grid" />&nbsp;<font color='red'>*</font>
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

	<tr>
	
	<!-- 年 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select id="year" name="year" alt="allowBlank:false">
					<option id="0" value="">请选择</option>
				<c:forEach begin="2008" end="2025" var="num">
					<c:if test="${gridSatisfactionForm.year ==num}">
						<option id="${num}" value="${num}" selected="true">${num}年</option>
					</c:if>
					<c:if test="${gridSatisfactionForm.year !=num}">
						<option id="${num}" value="${num}">${num}年</option>
					</c:if>					
				</c:forEach>
			</select>	
			<html:hidden property="year" value="${gridSatisfactionForm.year}" />									
		</td>
	
<!-- 月份 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.month" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select id="month" name="month" alt="allowBlank:false">
					<option id="0" value="">请选择</option>
				<c:forEach begin="1" end="12" var="num">
					<c:if test="${gridSatisfactionForm.month ==num}">
						<option id="${num}" value="${num}" selected="true">${num}月</option>
					</c:if>
					<c:if test="${gridSatisfactionForm.month !=num}">
						<option id="${num}" value="${num}">${num}月</option>
					</c:if>					
				</c:forEach>
			</select>
			<html:hidden property="month" value="${gridSatisfactionForm.month}" />						
		</td>

	</tr>
<!-- 综合评价 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.synRating" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="synRating" styleId="synRating"
						styleClass="text medium" value="${gridSatisfactionForm.synRating}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 与业主关系维系满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.tieMaintenance" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="tieMaintenance" styleId="tieMaintenance"
						styleClass="text medium" value="${gridSatisfactionForm.tieMaintenance}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 故障处理满意度 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.faultDispose" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="faultDispose" styleId="faultDispose"
						styleClass="text medium" value="${gridSatisfactionForm.faultDispose}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 语音网络质量满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.phonicsQuality" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="phonicsQuality" styleId="phonicsQuality"
						styleClass="text medium" value="${gridSatisfactionForm.phonicsQuality}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 营业厅满意度 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.businessLobby" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="businessLobby" styleId="businessLobby"
						styleClass="text medium" value="${gridSatisfactionForm.businessLobby}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 投诉客户满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.customerComplaints" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="customerComplaints" styleId="customerComplaints"
						styleClass="text medium" value="${gridSatisfactionForm.customerComplaints}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 价值客户满意度 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.valueCustomer" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="valueCustomer" styleId="valueCustomer"
						styleClass="text medium" value="${gridSatisfactionForm.valueCustomer}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 集团客户满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.corporateCustomer" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="corporateCustomer" styleId="corporateCustomer"
						styleClass="text medium" value="${gridSatisfactionForm.corporateCustomer}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 主动接受基层公司管理、调遣和检查 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.companyAct" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="companyAct" styleId="companyAct"
						styleClass="text medium" value="${gridSatisfactionForm.companyAct}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 维护人员技术能力、储备及流失情况 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.personnelStatus" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="personnelStatus" styleId="personnelStatus"
						styleClass="text medium" value="${gridSatisfactionForm.personnelStatus}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
<!-- 仪器、仪表到位率及完好率情况 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.instrumentStatus" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="instrumentStatus" styleId="instrumentStatus"
						styleClass="text medium" value="${gridSatisfactionForm.instrumentStatus}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
<!-- 管理水平 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.managementAbility" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="managementAbility" styleId="managementAbility"
						styleClass="text medium"  value="${gridSatisfactionForm.managementAbility}" 
						alt="re:/^([1]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" />
		&nbsp;&nbsp;&nbsp;&nbsp;<font color='greed'>10分制数值</font>
		</td>
	</tr>
	
	<tr>
	
	<!-- 上报人 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.reportUser" />
		</td>
		<td class="content" colspan="3">
			<html:hidden property="reportUser" value="${gridSatisfactionForm.reportUser}" />
			<eoms:id2nameDB id="${gridSatisfactionForm.reportUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty gridSatisfactionForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url=eoms.appPath+'/partner/maintenance/gridSatisfactions.do?method=remove&id=${gridSatisfactionForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${gridSatisfactionForm.id}" />
</html:form>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
	var region = '${gridSatisfactionForm.region}';
	if(region!=''){
 		document.getElementById("region").value = region;
 		changeCity(1);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>