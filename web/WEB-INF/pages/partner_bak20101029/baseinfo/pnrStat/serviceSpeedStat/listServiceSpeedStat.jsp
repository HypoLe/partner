
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.webapp.form.ServiceSpeedStatForm" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">

function validate(){

	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	
		if(year=="" ){
			alert("年为必选项");
			return false;
		}
			
	    return true;  
}

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
	
	window.onload = function(){
								//合作伙伴
								var providerName = "${providerBuffer}"; 
								var arrOptionsP=providerName.split(",");
								var objp=$("provider");					
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
								}
		}	

function changeGrid(){
    var providerValue = document.getElementById("provider").value;
    if(providerValue!=''){
	    delAllOption("gridName");//合作伙伴和县区选择更新后，重新刷新网格
	    var regionValue = document.getElementById("region").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeGrid&city="+regionValue;
	    
		providerValue = "${eoms:a2u(providerValue)}";
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
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}			

</script>
<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<html:form action="/pnrStats.do?method=getServiceSpeedStat&first=fir"  method="post" onsubmit="return validate();"> 
<font color='red'>选择年为按年统计，同时选择了年月则为按月统计，年为必选项</font>
<table class="formTable">
	<caption>
		<div class="header center">代维响应速度上报统计报表</div>
	</caption>
<!-- 年 -->
	<tr>
		<td class="label">
			年份、&nbsp;
			月份：&nbsp;
		</td>
		<td class="content">
			<select id="year" name="year" >
					<option id="0" value="">--请选择年份--</option>
				<c:forEach begin="2008" end="2025" var="num">
						<option id="${num}" value="${num}">${num}年</option>
				</c:forEach>
			</select>
			<select id="month" name="month" >
					<option id="0" value="">--请选择月份--</option>
				<c:forEach begin="1" end="12" var="num">
						<option id="${num}" value="${num}">${num}月</option>
				</c:forEach>
			</select>
		</td>
<!-- 区域地市 -->
		<td class="label">
			所属地市：&nbsp;
		</td>
		<td class="content">
			<!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'"  onchange="changeGrid();">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="regionBuffer" name="regionBuffer">
					<option value="${regionBuffer.areaid}">
						${regionBuffer.areaname}
					</option>
				</logic:iterate>
			</select>
		</td>
	</tr>
	<tr>	
		<!-- 维护公司 -->
		<td class="label" >
			合作伙伴名称：&nbsp;
		</td>
		<td class="content" >
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" onchange="changeGrid();">
						<option value="">
							--请选择合作伙伴--
						</option>	
															
					</select>
		</td>
		<!-- 网格名称 -->	
		<td class="label" >
			网格名称：&nbsp;
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

</table>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
<html:hidden property="id" value="${gridKPIMonthForm.id}" />
</html:form>


<table class="formTable">
<%
Map rowMap = (Map)request.getAttribute("rowMap");
List listServiceSpeedStat = (List)request.getAttribute("listServiceSpeedStat");
String time = (String)request.getAttribute("time");

%>
			<tr>
				<td style="background-color:#E7F6FC;">月份(年份)</td>
				<td style="background-color:#E7F6FC;">地市</td>
				<td style="background-color:#E7F6FC;">县区</td>
				<td style="background-color:#E7F6FC;">网格</td>
				<td style="background-color:#E7F6FC;">维护公司</td>
				<td style="background-color:#E7F6FC;">网络故障响应度</td>
				<td style="background-color:#E7F6FC;">客户投诉处理响应度</td>
				<td style="background-color:#E7F6FC;">对基层业务、服务的响应度</td>
				<td style="background-color:#E7F6FC;">表报上报及时率</td>
				<td style="background-color:#E7F6FC;">表报上报准确率</td>
				<td style="background-color:#E7F6FC;">资料更新准确率</td>
				<td style="background-color:#E7F6FC;">应急通信保障响应度</td>
				<td style="background-color:#E7F6FC;">总分</td>
			</tr>
		
		
		
		<%
		
		ServiceSpeedStatForm serviceSpeedStatForm = null;
		String tempRegion = "";
		String tempCity = "";
		String tempGrid = "";
		String tempProvider = "";
		String regionRowNum = "";
		String cityRowNum = "";
		String gridRowNum = "";
		String providerRowNum = "";
		for(int i=0;listServiceSpeedStat!=null&&i<listServiceSpeedStat.size();i++){
			serviceSpeedStatForm =  (ServiceSpeedStatForm)listServiceSpeedStat.get(i);
    		regionRowNum = StaticMethod.nullObject2String(rowMap.get(serviceSpeedStatForm.getRegion()));
    		cityRowNum = StaticMethod.nullObject2String(rowMap.get(serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity()));
    		gridRowNum = StaticMethod.nullObject2String(rowMap.get(serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity()+"_"+serviceSpeedStatForm.getGrid()));
    		providerRowNum = StaticMethod.nullObject2String(rowMap.get(serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity()+"_"+serviceSpeedStatForm.getGrid()+"_"+serviceSpeedStatForm.getProvider()));
		%>
		<tr>
		<%
		if(i==0){
		%>
		<td rowspan='<%=listServiceSpeedStat.size() %>'>
			<%=time %>
		</td>
		<%
		}
		
		if(!tempRegion.equals(serviceSpeedStatForm.getRegion())){
		%>
		<td rowspan='<%=regionRowNum %>'>
			<eoms:id2nameDB id="<%=serviceSpeedStatForm.getRegion() %>" beanId="tawSystemAreaDao" />
		</td>
		<%
			tempRegion = serviceSpeedStatForm.getRegion();
		}

		
		if(!tempCity.equals(serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity())){
		%>
		<td rowspan='<%=cityRowNum %>'>
			<eoms:id2nameDB id="<%=serviceSpeedStatForm.getCity() %>" beanId="tawSystemAreaDao" />
		</td>
		<%
			tempCity = serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity();
		}
	
		
		if(!tempGrid.equals(serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity()+"_"+serviceSpeedStatForm.getGrid())){
		%>
		<td rowspan='<%=gridRowNum %>'>
			<%=serviceSpeedStatForm.getGrid() %>
		</td>
		<%
			tempGrid = serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity()+"_"+serviceSpeedStatForm.getGrid();
		}
		if(!tempProvider.equals(serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity()+"_"+serviceSpeedStatForm.getGrid()+"_"+serviceSpeedStatForm.getProvider())){
		%>
		<td rowspan='<%=providerRowNum %>'>
			<%=serviceSpeedStatForm.getProvider() %>
		</td>
		<%
			tempProvider = serviceSpeedStatForm.getRegion()+"_"+serviceSpeedStatForm.getCity()+"_"+serviceSpeedStatForm.getGrid()+"_"+serviceSpeedStatForm.getProvider();
		}
		%>
		<td>
			<%=serviceSpeedStatForm.getWebfailure() %>
		</td>
		<td>
			<%=serviceSpeedStatForm.getCustomerComplaints() %>
		</td>
		<td>
			<%=serviceSpeedStatForm.getToService() %>
		</td>
		<td>
			<%=serviceSpeedStatForm.getFromReport() %>
		</td>
		<td>
			<%=serviceSpeedStatForm.getFromPrecision() %>
		</td>
		<td>
			<%=serviceSpeedStatForm.getDatumUpdate() %>
		</td>
		<td>
			<%=serviceSpeedStatForm.getCommSecurity() %>
		</td>
		<td>
			<%=serviceSpeedStatForm.getSum() %>
		</td>
		</tr>
		<%
		}
		%>		

</table>

</fmt:bundle>


<%@ include file="/common/footer_eoms.jsp"%>