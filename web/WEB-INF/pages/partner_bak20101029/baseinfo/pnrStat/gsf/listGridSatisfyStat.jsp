
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.webapp.form.GridSatisfyStatForm" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">

function yanzheng(){

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
<html:form action="/pnrStats.do?method=getGridSatisfyStat&first=fir"  method="post" onsubmit="return yanzheng();"> 
<font color='red'>选择年为按年统计，同时选择了年月则为按月统计，年为必选项</font>
<table class="formTable">
	<caption>
		<div class="header center">网格综合满意度上报统计报表</div>
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
List listGridSatisfyStat = (List)request.getAttribute("listGridSatisfyStat");
String time = (String)request.getAttribute("time");

%>
			<tr>
				<td style="background-color:#E7F6FC;">月份(年份)</td>
				<td style="background-color:#E7F6FC;">地市</td>
				<td style="background-color:#E7F6FC;">县区</td>
				<td style="background-color:#E7F6FC;">网格</td>
				<td style="background-color:#E7F6FC;">维护公司</td>
				<td style="background-color:#E7F6FC;">综合评价</td>
				<td style="background-color:#E7F6FC;">与业主关系维系满意度</td>
				<td style="background-color:#E7F6FC;">故障处理满意度</td>
				<td style="background-color:#E7F6FC;">语音网络质量满意度</td>
				<td style="background-color:#E7F6FC;">营业厅满意度</td>
				<td style="background-color:#E7F6FC;">投诉客户满意度</td>
				<td style="background-color:#E7F6FC;">价值客户满意度</td>
				<td style="background-color:#E7F6FC;">集团客户满意度</td>
				<td style="background-color:#E7F6FC;">主动接受基层公司管理、调遣和检查</td>
				<td style="background-color:#E7F6FC;">维护人员技术能力、储备及流失情况</td>
				<td style="background-color:#E7F6FC;">仪器、仪表到位率及完好率情况</td>
				<td style="background-color:#E7F6FC;">管理水平</td>
				<td style="background-color:#E7F6FC;">总分</td>
			</tr>
		
		
		
		<%
		GridSatisfyStatForm gsf = null;
		String tempRegion = "";
		String tempCity = "";
		String tempGrid = "";
		String tempProvider = "";
		String regionRowNum = "";
		String cityRowNum = "";
		String gridRowNum = "";
		String providerRowNum = "";
		for(int i=0;listGridSatisfyStat!=null&&i<listGridSatisfyStat.size();i++){
			gsf =  (GridSatisfyStatForm)listGridSatisfyStat.get(i);
    		regionRowNum = StaticMethod.nullObject2String(rowMap.get(gsf.getRegion()));
    		cityRowNum = StaticMethod.nullObject2String(rowMap.get(gsf.getRegion()+"_"+gsf.getCity()));
    		gridRowNum = StaticMethod.nullObject2String(rowMap.get(gsf.getRegion()+"_"+gsf.getGrid()));
    		providerRowNum = StaticMethod.nullObject2String(rowMap.get(gsf.getRegion()+"_"+gsf.getGrid()+"_"+gsf.getProvider()));
		%>
		<tr>
		<%
		if(i==0){
		%>
		<td rowspan='<%=listGridSatisfyStat.size() %>'>
			<%=time %>
		</td>
		<%
		}
		if(!tempRegion.equals(gsf.getRegion())){
		%>
		<td rowspan='<%=regionRowNum %>'>
			<eoms:id2nameDB id="<%=gsf.getRegion() %>" beanId="tawSystemAreaDao" />
		</td>
		<%
			tempRegion = gsf.getRegion();
		}


		if(!tempCity.equals(gsf.getRegion()+"_"+gsf.getCity())){
		%>
		<td rowspan='<%=cityRowNum %>'>
			<eoms:id2nameDB id="<%=gsf.getCity() %>" beanId="tawSystemAreaDao" />
		</td>
		<%
			tempCity = gsf.getRegion()+"_"+gsf.getCity();
		}
		
		if(!tempGrid.equals(gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid())){
		%>
		<td rowspan='<%=gridRowNum %>'>
			<%=gsf.getGrid() %>
		</td>
		<%
			tempGrid = gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid();
		}
		
		if(!tempProvider.equals(gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid()+"_"+gsf.getProvider())){
		%>
		<td rowspan='<%=providerRowNum %>'>
			<%=gsf.getProvider() %>
		</td>
		<%
			tempProvider = gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid()+"_"+gsf.getProvider();
		}
		%>
		<td>
			<%=gsf.getSynRating() %>
		</td>
		<td>
			<%=gsf.getTieMaintenance() %>
		</td>
		<td>
			<%=gsf.getFaultDispose() %>
		</td>
		<td>
			<%=gsf.getPhonicsQuality() %>
		</td>
		<td>
			<%=gsf.getBusinessLobby() %>
		</td>
		<td>
			<%=gsf.getCustomerComplaints() %>
		</td>
		<td>
			<%=gsf.getValueCustomer() %>
		</td>
		<td>
			<%=gsf.getCorporateCustomer() %>
		</td>
		<td>
			<%=gsf.getCompanyAct() %>
		</td>
		<td>
			<%=gsf.getPersonnelStatus() %>
		</td>
		<td>
			<%=gsf.getInstrumentStatus() %>
		</td>
		<td>
			<%=gsf.getManagementAbility() %>
		</td>
		<td>
			<%=gsf.getSum() %>
		</td>
		</tr>
		<%
		}
		%>		

</table>

</fmt:bundle>


<%@ include file="/common/footer_eoms.jsp"%>