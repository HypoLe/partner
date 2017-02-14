
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
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
<html:form action="/pnrStats.do?method=getKPImonthReport&first=fir"  method="post" onsubmit="return yanzheng();"> 
<font color='red'>选择年为按年统计，同时选择了年月则为按月统计，年为必选项</font>
<table class="formTable">
	<caption>
		<div class="header center">KPI统计</div>
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
List gridKPIMonthList = (List)request.getAttribute("listKPImStat");
String my = (String)request.getAttribute("yearmonth");

%>
			<tr>
				<td style="background-color:#E7F6FC;">月份(年份)</td>
				<td style="background-color:#E7F6FC;">地市</td>
				<td style="background-color:#E7F6FC;">维护公司</td>
				<td style="background-color:#E7F6FC;">网格</td>
				<td style="background-color:#E7F6FC;">网络故障（基站）工单处理及时率</td>
				<td style="background-color:#E7F6FC;">月度基站告警比例</td>
				<td style="background-color:#E7F6FC;">基站告警数量削减</td>
				<td style="background-color:#E7F6FC;">掉话率</td>
				<td style="background-color:#E7F6FC;">基站退服率</td>
				<td style="background-color:#E7F6FC;">最坏小区比</td>
				<td style="background-color:#E7F6FC;">无线接入性</td>
				<td style="background-color:#E7F6FC;">接入网重大故障率(手工)</td>
				<td style="background-color:#E7F6FC;">季度板件无故障返修率(手工)</td>
			</tr>
		
			<%
			if(gridKPIMonthList!=null){
			String regionTem = "";
			String companyNameTem = "";
			String regionStat = null;
			String companyNameStat = null;
			String grid = null;
			String workOrderATR = null;
			String bsWarning = null;
			String bswReduce = null;
			String dropCallRate = null;
			String bsOutRate = null;
			String mbplot = null;
			String wirelessIn = null;
			String mostfailRate = null;
			String nofaiRamRate = null;
			for(int i=0;i<gridKPIMonthList.size();i++){
				Object[] gridKPIMonth =  (Object[])gridKPIMonthList.get(i);
				
				regionStat = (String)gridKPIMonth[0];
				companyNameStat = (String)gridKPIMonth[1];
				grid = (String)gridKPIMonth[2];
				
				workOrderATR = (String)gridKPIMonth[3];
				bsWarning = (String)gridKPIMonth[4];
				bswReduce = (String)gridKPIMonth[5];
				dropCallRate = (String)gridKPIMonth[6];
				bsOutRate = (String)gridKPIMonth[7];
				mbplot = (String)gridKPIMonth[8];
				wirelessIn = (String)gridKPIMonth[9];
				mostfailRate = (String)gridKPIMonth[10];
				nofaiRamRate = (String)gridKPIMonth[11];
	    		
	    		String regionTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_num"));
	    		String companyNameTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_"+companyNameStat+"_num"));
			 %>
			<tr>
			<%
			if(i==0){
			%>		
				<td rowspan="<%=gridKPIMonthList.size()%>">
					<%= my %>
				</td>
			<%
			}
			if(!regionTem.equals(regionStat)){
			 %>
				<td rowspan="<%=regionTab%>">
					<eoms:id2nameDB id="<%=regionStat %>" beanId="tawSystemAreaDao" />
				</td>
				
			<%
			}
			if(!regionTem.equals(regionStat)||!companyNameTem.equals(companyNameStat)){
			 %>
				<td rowspan="<%=companyNameTab %>">
					<%=companyNameStat %>
				</td>
			<%
			}
			 %>
			 	<td>
			 			<%
						if(grid==null){
							out.print("暂不维护网格");
						
						}else{
							out.print(grid);
						}
						 %>	
				</td>
				<td>
			 			<%
						if(workOrderATR == null){
							out.print("---");
						
						}else{
							out.print(workOrderATR);
						}
						 %>					
				</td>
				<td >
			 			<%
						if(bsWarning == null){
							out.print("---");
						
						}else{
							out.print(bsWarning);
						}
						 %>	
				</td>
				<td >
			 			<%
						if(bswReduce == null){
							out.print("---");
						
						}else{
							out.print(bswReduce);
						}
						 %>	
				</td>
				<td >
							 			<%
						if(dropCallRate == null){
							out.print("---");
						
						}else{
							out.print(dropCallRate);
						}
						 %>	
				</td>							
				<td >
							 			<%
						if(bsOutRate == null){
							out.print("---");
						
						}else{
							out.print(bsOutRate);
						}
						 %>	
				</td>
				<td >
							 			<%
						if(mbplot == null){
							out.print("---");
						
						}else{
							out.print(mbplot);
						}
						 %>	
				</td>
				<td >
							 			<%
						if(wirelessIn == null){
							out.print("---");
						
						}else{
							out.print(wirelessIn);
						}
						 %>	
				</td>
				<td >
							 			<%
						if(mostfailRate == null){
							out.print("---");
						
						}else{
							out.print(mostfailRate);
						}
						 %>	
				</td>	
				<td >
							 			<%
						if(nofaiRamRate == null){
							out.print("---");
						
						}else{
							out.print(nofaiRamRate);
						}
						 %>	
				</td>																		
			</tr>
			<%

				if(!regionTem.equals(regionStat)){
					regionTem = regionStat;
				}
				if(!companyNameTem.equals(companyNameStat)){
					companyNameTem = companyNameStat;
				}
			}
			}else{
			 %>	
			 	<tr></tr>
			 <% }%>
</table>

</fmt:bundle>


<%@ include file="/common/footer_eoms.jsp"%>