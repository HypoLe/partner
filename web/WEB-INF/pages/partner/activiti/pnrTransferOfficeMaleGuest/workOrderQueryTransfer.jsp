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
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>		
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
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
			document.getElementById("wsNum").value="";		// 工单号
			document.getElementById("region").value="";	        
			document.getElementById("country").value="";	   
			document.getElementById("status").value="";	        // 当前状态
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
     
      //提交时对查询条件进行校验
	function changeType1(){
		var sheetAcceptLimit = document.getElementById("sheetAcceptLimit").value; 
		var sheetCompleteLimit = document.getElementById("sheetCompleteLimit").value;
		var wsNum = document.getElementById("wsNum").value; //工单号为空时校验时间，工单号不为空时校验时间
		if(wsNum == ''){
			if(sheetAcceptLimit == '' || sheetCompleteLimit == ''){
				alert("开始时间和结束时间不能为空，请选择！");
	 			return false;
			} 
			
			//判断开始时间是否大于结束时间
			var st=sheetAcceptLimit;
	 		st = st.replace(/-/g,"/");
	 		var date = new Date(st);
	 	
	 		var et=sheetCompleteLimit;
	 		et = et.replace(/-/g,"/");
	 		var date2 = new Date(et);
	 	
		 	if(date > date2){
		 		alert("开始日期不能大于结束日期，请重新选择！");
		 		return false;
		 	}
		 	
		 	//判断开始时间和结束时间相差天数，默认查询31天之内的
		 	time = date2.getTime()- date.getTime();
	 		days = parseInt(time / (1000 * 60 * 60 * 24));
	 		if(days > 150){
		 		alert("统计周期为150天！请重新选择后再查询！");
		 		return false;
	 		}	
		}
	}	
     
</script>

		<div id="sheetform">
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=showQueryPage"
				styleId="theform">
				<input type="hidden" id="queryAllowed" name="queryAllowed" value="Y" /><!-- 能查询标识 -->
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							开始时间
						</td>
						<td class="class="text"">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							结束时间
						</td>
						<td class="class="text"">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
						<!-- 工单号  -->
						<td class="label">
							工单号
						</td>
						<td>
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${wsNum}" />
						</td>
					</tr>

					<tr>
					<td class="label">地市</td>
					<td class="class="text"">
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
					<td class="class="text"">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
					
					<td class="label">
							工单状态
						</td>
						<td >
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="">
									所有
								</option>
								<option value="auditor" ${wsStatus == "auditor" ? 'selected="selected"':'' }>
									故障处理
								</option>
								<option value="acheck" ${wsStatus == "acheck" ? 'selected="selected"':'' }>
									区县维护中心一次核验
								</option>
								<option value="twoSpotChecks" ${wsStatus == "twoSpotChecks" ? 'selected="selected"':'' }>
									区县维护中心二次抽查
								</option>
								<option value="auditReport" ${wsStatus == "auditReport" ? 'selected="selected"':'' }>
									区县维护中心综合报表审核
								</option>
							</select>
						</td>
				</tr>
				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save" onclick="return changeType1();">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
					<c:if test="${tishiFlag eq 'Y'}">
				    	<span><font color="red">请选择查询条件，查询相对应的数据!</font></span>
			        </c:if>
				</div>
			</html:form>
		</div>
		
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="true" requestURI="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=showQueryPage" sort="list"
			size="total" partialList="true">
			<display:column sortable="true" headerClass="sortable" title="工单号">${taskList.processInstanceId}</display:column>
			<display:column property="theme" sortable="false" headerClass="sortable" title="工单名称" maxLength="15" />
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="局站名称">
				${taskList.stationName}
			</display:column>
			<display:column property="createTime" sortable="true"	headerClass="sortable" title="公客发单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="sendTime" sortable="true"	headerClass="sortable" title="现场发单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="lastReplyTime" sortable="true"	headerClass="sortable" title="施工队回单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="机线员">
				${taskList.tempTask}
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="所在账号">
				<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="归集工单号">
				${taskList.sheetId}
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="当前状态">
				${taskList.taskDefKeyName}
			</display:column>
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		<%@ include file="/common/footer_eoms.jsp"%>