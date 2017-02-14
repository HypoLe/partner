<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
			
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>

		<%
		//附件下载人员超限提示
		Object err = request.getAttribute("downlimiterror");
			if (err!=null){
		%>
				<script>alert("同时下载人数超限，请稍后再试！");</script>
		<%
			}
			else{
		%>
			
		<%
			}			
		%>
<script type="text/javascript">
 Ext.onReady(function(){
 			
  			// 初始的时候给区县默认值
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
					var country = "${country}";
					if(arrOptions[i]==country){
					obj.options[j].selected=true;
					}
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
		
  });
		//重置
		function newReset(){

			document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
			document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
			
			document.getElementById("region").value="";	        
			document.getElementById("country").value="";
			
			//$("input[name='themeinterface']").attr("checked", false);//工单类型
			//$("input[name='taskdefkey']").attr("checked", false);//所属环节
			var themeinterface = document.getElementsByName("themeinterface");//获取对象
         	for(var i=themeinterface.length-1;i>=0;i--){
             if(themeinterface[i].checked)
             	themeinterface[i].checked = false;
        	}	
			var taskdefkey = document.getElementsByName("taskdefkey");//获取对象
         	for(var i=taskdefkey.length-1;i>=0;i--){
             if(taskdefkey[i].checked)
             	taskdefkey[i].checked = false;
        	}
			
		}
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
		
<script type="text/javascript">

   //此刷新函数被弹出的子模态窗口调用
   function refresh(){
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=downAttachMent";
	} 
	
   //分页设置
   function changePage(v) {
		location.href = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=downAttachMent&pagesize='+v;
	}
	
	//保存查询条件 后面导出使用
	function getquerycondition()
	{
		var sheetAcceptLimit = document.getElementById("sheetAcceptLimit").value;//开始时间
		var sheetCompleteLimit = document.getElementById("sheetCompleteLimit").value;//结束时间
		
		var region= document.getElementById("region").value;    
		var country=document.getElementById("country").value;
		
		var themeinterface = document.getElementsByName("themeinterface");//获取对象
		var strgdlx='';
         	for(var i=themeinterface.length-1;i>=0;i--){
             if(themeinterface[i].checked)
             	strgdlx += "'"+ themeinterface[i].value + "',"; 
        	}	
		if (strgdlx.length>0)
			strgdlx = strgdlx.substring(0, strgdlx.length-1);
				
		var taskdefkey = document.getElementsByName("taskdefkey");//获取对象
		var strsshj='';
         	for(var i=taskdefkey.length-1;i>=0;i--){
             if(taskdefkey[i].checked)
             	strsshj += "'"+ taskdefkey[i].value + "',"; 
        	}	
		if (strsshj.length>0)
			strsshj = strsshj.substring(0, strsshj.length-1);
			
		return "sheetAcceptLimit=" + sheetAcceptLimit + "&sheetCompleteLimit=" + sheetCompleteLimit + "&region=" + region + "&country=" + country + "&themeinterface=" + strgdlx + "&taskdefkey=" + strsshj;
		 
	}

	//下载附件
  function download() {  	
  	window.location.href ='pnrTransferNewPrecheck.do?method=downAttachMentToZip&' + getquerycondition();
  }	

</script>

		<div id="sheetform">
					<html:form
				action="/pnrTransferNewPrecheck.do?method=downAttachMent"
				styleId="theform">
				<input type="hidden" name="pagesize" value="${pageSize}">
				<input type="hidden" name="pagesize" value="${querycondition}">
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							派单开始时间
						</td>
						<td class="content">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							派单结束时间
						</td>
						<td class="content">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
					</tr>

					

                  
                  <tr>
					<td class="label">地市</td>
					<td class="content">
						<select name="region"  id="region" class="select"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="changeCity(0);">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:iterate id="city" name="city">
							<c:if test="${city.areaid ==region}">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<option value="${city.areaid}">
								${city.areaname}
							</option>
							</logic:iterate>
						</select>
					</td>
					<td class="label">区县</td>
					<td class="content">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
					<tr>
						<!-- 工单类型  -->
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
				</table>
					<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">查询</html:submit>

					<html:button property="" styleClass="btn" onclick="newReset()">重置测试</html:button>

				</div>
				</html:form>
		</div>
		<bean:define id="url" value="pnrTransferNewPrecheck.do" />

		<form id="testform" name="testform">
			<display:table name="taskList" cellspacing="0" cellpadding="0"
				id="taskList" pagesize="${pageSize}" class="listTable taskList"
				export="true" requestURI="pnrTransferNewPrecheck.do" sort="list"
				size="total" partialList="true">

				<display:column property="processInstanceId" sortable="true"
					headerClass="sortable" title="工单号" />

				<display:column property="sheetId" sortable="true"
					headerClass="sortable" title="工单编码" />

				<display:column property="theme" sortable="false"
					headerClass="sortable" title="工单名称" maxLength="15" />

				<display:column property="themeinterface" sortable="false"
					headerClass="sortable" title="工单类别" maxLength="15" />

				<display:column property="taskdefkey" sortable="false"
					headerClass="sortable" title="所属环节" maxLength="15" />

				<display:column property="accessoriescnname" sortable="false"
					headerClass="sortable" title="附件名称" maxLength="15" />
				
				<display:column sortable="false" headerClass="sortable" title="地市">
					<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
				</display:column>
				
				<display:column sortable="false" headerClass="sortable" title="区县">
					<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
				</display:column>

				<display:column property="sendTime" sortable="true"
					headerClass="sortable" title="派单时间"
					format="{0,date,yyyy-MM-dd HH:mm:ss}" />		
			</display:table>
			<table>
	  			<tr>
	  				<td>
	  					<input type="button" name="button" class="button" value="附件下载" onclick="download()" >
	  				</td>
	  			</tr>
	  		</table>
		</form>

		

		<%@ include file="/common/footer_eoms.jsp"%>