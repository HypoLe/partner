
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">

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
function changeRegion()
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/pnrStats.do?method=changeRegion&region="+region;
			
						Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									 if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									var cityName = "city";
									var arrOptions = json[0].cb.split(",");
									var obj=$(cityName);
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
	
	
	function sub(){
	
	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	
		if(year!="" && month==""){
			alert("请选择要统计的月份！");
			return false;
		}else if(year=="" && month!=""){
			alert("请选择要统计的年份！");
			return false;
		}
	    return true;  
	}

</script>
<html:form action="/pnrStats.do?method=getSiteReport" styleId="SiteReportFrom" method="post" onsubmit="return sub();"> 

<table class="formTable">
	<caption>
		<div class="header center">基站统计</div>
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
						alt="allowBlank:false,vtext:'请选择所在地市'" 
						onchange="changeRegion();" >
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
			维护公司：&nbsp;
		</td>
		<td class="content" >
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" 
						alt="allowBlank:false,vtext:'请选择合作伙伴'">
						<option value="">
							--请选择合作伙伴--
						</option>	
															
					</select>
		</td>
<!-- 网格名称 -->	
		<td class="label" >
			所属县区：&nbsp;
		</td>
		<td class="content">
					<!-- 县区 -->			
					<select name="city" id="city" 
						alt="allowBlank:false,vtext:'请选择所在县区'">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>	
		</td>
	
	</tr>

</table>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>


<table class="formTable">
<%
Map rowMap = (Map)request.getAttribute("rowMap");
List listSiteStat = (List)request.getAttribute("listSiteStat");


%>
			<tr>
				<td class="label">合作伙伴名称</td>
				<td class="label">地市</td>
				<td class="label">县区</td>
				<td class="label">站点数量</td>
				<td class="label">应配维护人员数量</td>
				<td class="label">维护人员实配数量</td>
			</tr>
		
			<%
			if(listSiteStat!=null){			
			String proTem = "";
			String regionTem = "";
			String proStat = null;
			String regStat = null;
			String cityStat = null;
			String siteNumStat = null;
			String personConStat = null;
			String personStat = null;
			String cityNumStat = null;
			String regNumStat = null;
			String providerStat = null;
			for(int i=0;i<listSiteStat.size();i++){
				Object[] mark =  (Object[])listSiteStat.get(i);
				
				proStat = (String)mark[0];
				regStat = (String)mark[1];
				cityStat = (String)mark[2];
				//站点数量
				siteNumStat = String.valueOf(mark[3]);
				//应配维护人员数量
				personConStat = String.valueOf(mark[4]);
				//维护人员实配数量
				personStat = String.valueOf(mark[5]);
				regNumStat = String.valueOf(mark[6]);
				cityNumStat= String.valueOf(mark[7]);
				providerStat= (String)mark[8];
	    		String proTab = StaticMethod.nullObject2String(rowMap.get(proStat+"_num"));
	    		String regionTab = StaticMethod.nullObject2String(rowMap.get(proStat+"_"+regStat+"_num"));
			 %>
			<tr>
			<%
			if(!proTem.equals(proStat)){
			 %>
				<td rowspan="<%=proTab%>">
					<%=proStat %>
				</td>
			<%
			}
			if(!proTem.equals(proStat)||!regionTem.equals(regStat)){
			 %>
				<td rowspan="<%=regionTab%>">
					<%=regStat %>
				</td>
			<%} %>
			<td>
				<%=cityStat %>
			</td>
			 	<td>
						<%
						if(siteNumStat == "null"){
							out.print("0");
						
						}else{
							String url = "pnrStats.do?method=searchSite&region="+regNumStat+"&city="+cityNumStat+"&provider="+providerStat;
							out.print("<a href='"+url+"' target='_blank'>"+siteNumStat+"</a>");
						}
						 %>	
						
						 		 	
				</td>
			
				<td>
						<%if(personConStat == "null"){
							out.print("0");
						
						}else{
							out.print(personConStat);
						}
						 %>				
				</td>
				<td >
						<%if(personStat == "null"){
							out.print("0");
						
						}else{
							out.print(personStat);
						}
						 %>					
				</td>
			</tr>
			<%
				if(!proTem.equals(proStat)){
					proTem = proStat;
				}
				if(!regionTem.equals(regStat)){
					regionTem = regStat;
				}
			}
			}else{
			 %>	
			 <% }%>
</table>



<%@ include file="/common/footer_eoms.jsp"%>