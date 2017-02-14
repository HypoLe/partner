<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%response.setHeader("cache-control","public"); %>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript" >
			// 地区、区县连动
function changeCity(con)
		{    
		    delAllOption("country");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("[{")!=0){
								res = "[{"+result.responseText;
					}
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					
					var json = eval(res);
					
					var countyName = "country";
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
										
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}
		 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }	
</script>
	
<html:form  action="/pnrStatistics.do?method=findPreflightPartnerStatistic" styleId="theform" > 
<table class="formTable">
					
					<tr>
						<td class="label">
							工单类别
						</td>
						<td colspan="3">
							<table border="0" cellspacing="0" cellpadding="0" rules=none>
								<tr>

									<td width="110px" style="border: 0;">
										<input type="checkbox" name="themeinterface" value="1" 
										<%
											if (request.getAttribute("themeinterface")!=null)
											{
												if(request.getAttribute("themeinterface").toString().contains("1")){
													out.print("checked");
												}
											}
										%>
										/>
										&nbsp;预检预修-本地网
									</td>
									<td width="110px" style="border: 0;">
										<input type="checkbox" name="themeinterface" value="2" 
<%
											if (request.getAttribute("themeinterface")!=null)
											{
												if(request.getAttribute("themeinterface").toString().contains("2")){
													out.print("checked");
												}
											}
										%>
										/>
										&nbsp;预检预修-干线
									</td>
									<td width="110px" style="border: 0;">
										<input type="checkbox" name="themeinterface" value="3" 
										<%
											if (request.getAttribute("themeinterface")!=null)
											{
												if(request.getAttribute("themeinterface").toString().contains("3")){
													out.print("checked");
												}
											}
										%>
										/>
										&nbsp;大修改造
									</td>
								</tr>
							</table>
						</td>

					</tr>
					<tr>
						<!-- 所属环节  -->
						<td class="label">
							所属环节
						</td>
						<td colspan="3">
							<table border="0" cellspacing="0" cellpadding="0" rules=none>
								<tr>
									<td width="110px" style="border: 0;">
										<input type="checkbox" name="taskdefkey" value="1" 
										<%
											if (request.getAttribute("taskdefkey")!=null)
											{
												if(request.getAttribute("taskdefkey").toString().contains("1")){
													out.print("checked");
												}
											}
										%>
										/>
										&nbsp;省线路主管
									</td>
									<td width="110px" style="border: 0;">
										<input type="checkbox" name="taskdefkey" value="2" 
										<%
											if (request.getAttribute("taskdefkey")!=null)
											{
												if(request.getAttribute("taskdefkey").toString().contains("2")){
													out.print("checked");
												}
											}
										%>
										/>
										&nbsp;省线路主任
									</td>
									<td width="110px" style="border: 0;">
										<input type="checkbox" name="taskdefkey" value="3" 
										<%
											if (request.getAttribute("taskdefkey")!=null)
											{
												if(request.getAttribute("taskdefkey").toString().contains("3")){
													out.print("checked");
												}
											}
										%>
										/>
										&nbsp;省运维主管
									</td>
									<td width="110px" style="border: 0;">
										<input type="checkbox" name="taskdefkey" value="4" 
										<%
											if (request.getAttribute("taskdefkey")!=null)
											{
												if(request.getAttribute("taskdefkey").toString().contains("4")){
													out.print("checked");
												}
											}
										%>
										/>
										&nbsp;省运维总经理
									</td>
								</tr>
							</table>
						</td>
					</tr>
						
					 <tr>
						 <td class="label">
							季度
						</td>
						<td class="content">
						<eoms:dict key="dict-partner-preflight" selectId="quarter"
								dictId="quarterflag" beanId="selectXML" cls="select" defaultId="${quarter}"/>
						</td>
						
						<td class="label">
							月度
						</td>
						<td class="content">
							<eoms:dict key="dict-partner-preflight" selectId="month"
								dictId="monthflag" beanId="selectXML" cls="select" defaultId="${month}" />
						</td>
					</tr>
						
				</table>
<table>					
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" onclick="return query()"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</html:form>
<br/>

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
        requestURI="/pnrStatistics.do?method=findPreflightPartnerStatistic"
		id="list" class="table list" export="false" sort="list" >
		<display:column   title="地市" >
			<a href="${app}/activiti/statistics/pnrStatistics.do?method=partnerPreflightCountryStatistic&city=${list.city }&themeinterface=${themeinterface}&taskdefkey=${taskdefkey}&month=${month}&quarter=${quarter}"> 
				<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao"/><a>
		</display:column>	
		<display:column property="budgetAmount" title="预算金额" />	
		<display:column property="auditNum" title="申报项目数量" />
		<display:column property="auditMoney" title="申报项目金额" />
		<display:column property="auditPercent" title="申报完成比率" />
		<display:column property="passNum" title="审批合格数量" />
		<display:column property="passMoney" title="审批合格金额" />
		<display:column property="noPassNum" title="不合格数量" />
		<display:column property="noPassNumMoney" title="不合格金额" />
		<display:column property="auditPassNum" title="审批同意项目数量" />
		<display:column property="auditPassMoney" title="审批合格金额" />
		<display:column property="passPercent" title="会审合格率" />
		<display:column property="auditPassPercent" title="审批同意率" />
		
	</display:table>
</logic:present>
	</br>
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
