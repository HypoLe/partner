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
			document.getElementById("wsNum").value="";		// 工单号
			document.getElementById("wsTitle").value="";	        // 工单主题
			document.getElementById("status").value="";	        // 当前状态
			document.getElementById("region").value="";	        
			document.getElementById("country").value="";	      
			//document.getElementById("lineType").value="";	       
			//document.getElementById("provName").value="";	        
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

	function selectArchiveAll(obj) {
        var temp = document.getElementsByName("Fruit"); 
        for (var i =0; i<temp.length; i++) 
        { 
            temp[i].checked = obj.checked; 
        } 
    } 
    
    function cancelArchive(obj,all) {
        var flag = 0; 
        var all = document.getElementsByName(all)[0]; 
        if (!obj.checked) 
        { 
            all.checked = false; 
        } 
        else 
        { 
            for (var i=0; i<document.getElementsByName(obj.name).length; i++) 
            { 
                if (!document.getElementsByName(obj.name)[i].checked) 
                { 
                    all.checked = false; 
                } 
                else 
                { 
                    flag++; 
                } 
            } 
            if (flag == document.getElementsByName(obj.name).length) 
            { 
                all.checked = true; 
            } 
        } 
    }  
    
    //点击批量审批按钮，弹出子窗口
	 function batchApprove(){
		var batchApprovalFlag =document.getElementById("batchApprovalFlag").value;
		var inputs =document.getElementsByName("Fruit");
		var total=0;
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].checked){
					total++;
				}
			}		
		if(total==0){
			alert("请勾选要批量处理的工单！");
		}else if(total>50){
			alert("批量处理最多可选50条,当前已勾选"+total+"条!");
		}else{
			var wsStatusFlag =document.getElementById("wsStatusFlag").value;
			var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openBatchApproveView';
			var _sHeight = 260;
		    var _sWidth = 820;
		    var sTop=(window.screen.availHeight-_sHeight)/2;
		    var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(url,window,sFeatures); 
		}
	}
	
	//点击批量回退按钮，弹出子窗口
	 function batchRegression(){
		var inputs =document.getElementsByName("Fruit");
		var total=0;
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].checked){
					total++;
				}
			}		
		if(total==0){
			alert("请勾选要批量回退的工单！");
		}else if(total>50){
			alert("批量回退最多可选50条,当前已勾选"+total+"条!");
		}else{
			var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openBatchRegressionView';
			var _sHeight = 200;
		    var _sWidth = 820;
		    var sTop=(window.screen.availHeight-_sHeight)/2;
		    var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(url,window,sFeatures); 
	     //  	alert("已选");
		}
	}
	
	
 
   //此刷新函数被弹出的子模态窗口调用
   function refresh(){
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=listBacklog";
	} 
	
	function archive(){
       if(confirm("确定手动归档吗？")){
       		var condition=document.getElementById("condition").value;
		 	var url = "${app}/activiti/roomDemolition/roomDemolition.do?method=doOneWorkOrderArchiving&taskId="+arguments[0]+"&processInstanceId="+arguments[1]+"&titleId="+arguments[2]+"&title="+arguments[3]+"&linkName=manualArchive"+"&flag=listview"+condition;
		     window.location.href = url;
	       }	 	
	 } 



</script>
		

 
 

		<div id="sheetform">
			<html:form action="/roomDemolition.do?method=listBacklog"
				styleId="theform">
				<input type="hidden" id="batchApprovalFlag" name="batchApprovalFlag" value="${batchApprovalFlag}" /><!-- 是否能进行批量处理标识 -->
				<input type="hidden" id="wsStatusFlag" name="wsStatusFlag" value="${wsStatus}" /><!-- 工单状态标识 -->
				<input type="hidden" id="condition" name="condition" value="${condition}" />
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
						<!-- 工单号  -->
						<td class="label">
							工单号
						</td>
						<td>
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${wsNum}" />
						</td>
						<!-- 工单主题 -->
						<td class="label">
							工单名称
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
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
		<!-- 		<tr>
			<td class="label">
				线路属性
			</td>
			<td class="content">
			<eoms:comboBox name="lineType" id="lineType"
					defaultValue="${lineType}" initDicId="1012310"
					alt="allowBlank:false" styleClass="select" />
			</td>
		   <td class="label">
				工单类型
			</td>
			<td class="content">
					<input type="text" class="text" name="provName" id="provName"
						value="${provName}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="workOrderType" id="workOrderType" value="${pnrTransferOffice.workOrderType}" type="hidden" />
					<eoms:xbox id="provTree2"
						dataUrl="${app}/xtree.do?method=dictXbox&level=3"
						rootId="1012307" rootText="工单类型" valueField="workOrderType" handler="provName"
						textField="provName" checktype="dict" />
			</td>
		</tr> -->
					<tr>
						<!-- 当前状态 -->
						<td class="label">
							工单状态
						</td>
						<td colspan="3">
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="">
									所有
								</option>
								<option value="need" ${wsStatus == "need" ? 'selected="selected"':'' }>
									区县维护中心主管工单发起
								</option>
								<option value="cityManageExamine" ${wsStatus == "cityManageExamine" ? 'selected="selected"':'' }>
									市公司运维部主管审批
								</option>
								<option value="provinceManageExamine" ${wsStatus == "provinceManageExamine" ? 'selected="selected"':'' }>
									省公司运维部主管审批
								</option>
								<option value="worker" ${wsStatus == "worker" ? 'selected="selected"':'' }>
									区县维护中心主管回单
								</option>
								<option value="orderAudit" ${wsStatus == "orderAudit" ? 'selected="selected"':'' }>
									市公司运维部主管验收
								</option>
								<option value="daiweiAudit" ${wsStatus == "daiweiAudit" ? 'selected="selected"':'' }>
									省公司运维部主管审核
								</option>
								<option value="manualArchive" ${wsStatus == "manualArchive" ? 'selected="selected"':'' }>
									省公司运维部主管手动归档
								</option>
							</select>
						</td>
		<!-- 	<td class="label">
				工单类别
			</td>
			 <td class="content">
				<eoms:comboBox name="precheckFlag" id="precheckFlag"
					defaultValue="${precheckFlag}" initDicId="1012314"
					alt="allowBlank:false" styleClass="select" />
			 </td>  -->
					</tr>
			<tr>
				<td colspan="4" class="content">
					<div  style="text-align:right;">
						设置每页显示条数
						<select id="pagesize" name="pagesize" style="width: 58px">
										<option value="20" ${pageSize== "20" ? 'selected="selected"':'' } >
											20
										</option>
										<option value="50" ${pageSize== "50" ? 'selected="selected"':'' } >
											50
										</option>
										<option value="100" ${pageSize== "100" ? 'selected="selected"':'' } >
											100
										</option><!--
										<option value="500" ${pageSize== "500" ? 'selected="selected"':'' } >
											500
										</option>
										<option value="1000" ${pageSize== "1000" ? 'selected="selected"':'' } >
											1000
										</option>
										<option value="5000" ${pageSize== "5000" ? 'selected="selected"':'' } >
											5000
										</option>
									--></select>
					</div>
				</td>
			</tr>
					
				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
				<!--	<c:if test="${loginUserId eq 's_admin' || loginUserId eq 's_admin'||loginUserId eq 's_admin'||loginUserId eq 'superUser'}">
					<html:button property="" styleClass="btn" onclick="batchApprove()">批量处理</html:button>
					<html:button property="" styleClass="btn" onclick="batchRegression()">批量回退</html:button>
					</c:if> -->
				</div>
			</html:form>
		</div>
		<br/>
		<h6><font color="red">*注意：</font>工单号变红为驳回工单，如：<font color="red">123456</font></h6><br />
		<!-- <h6><font color="blue">*注意：</font>工单号变蓝为抓回工单，如：<font color="blue">208818</font></h6> -->
		<br/>
		<bean:define id="url" value="roomDemolition.do" />
		<c:if test="${carApprove ne 'yes'}">
			<c:set var="export" value="false"></c:set>
		</c:if>
		<!-- 新增批量-start -->
		<form id="testform" name="testform" action="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=doMoreWorkOrderArchiving" method="post">	
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="false" requestURI="roomDemolition.do" sort="list"
			size="total" partialList="true">
			<!-- partialList="true" -->
		
			<display:column sortable="true" headerClass="sortable" title="工单号">
				<c:if test="${taskList.rollbackFlag == null  ||taskList.rollbackFlag eq '' || taskList.rollbackFlag eq '0'}">
				 <a
					href="${app}/activiti/roomDemolition/roomDemolition.do?method=todo&taskId=${taskList.taskId}${condition}"
					 title="回复">${taskList.processInstanceId}</a>	
				</c:if>
					 
				<c:if test="${taskList.rollbackFlag eq '1'}">
			     <a
					href="${app}/activiti/roomDemolition/roomDemolition.do?method=todo&taskId=${taskList.taskId}${condition}"
					 title="回复"><font color='red'>${taskList.processInstanceId}</font></a>		 
				</c:if>
			</display:column>
			
			<display:column property="sheetId" sortable="true" headerClass="sortable" title="工单编码" />
				
			<display:column property="theme" sortable="false"
				headerClass="sortable" title="工单名称" maxLength="15" />
				
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="工单状态">
				${taskList.statusName}
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="机房类型">
				<eoms:id2nameDB id="${taskList.stationType}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column property="stationName" sortable="false" headerClass="sortable" title="机房名称" />
			<display:column property="stationLevel" sortable="false" headerClass="sortable" title="机房级别" />
			<display:column property="stationArea" sortable="false" headerClass="sortable" title="机房面积" />
			<display:column sortable="false" headerClass="sortable" title="机房产权">
				<eoms:id2nameDB id="${taskList.stationEquity}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column property="annualRent" sortable="false" headerClass="sortable" title="年租金" />
			<display:column property="hireDate" sortable="true"	headerClass="sortable" title="租用日期"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="contractTime" sortable="true"	headerClass="sortable" title="合同到期时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="opticcableNum" sortable="false" headerClass="sortable" title="光缆纤芯数" />	
			<display:column sortable="false" headerClass="sortable" title="光缆改造方式">
				<eoms:id2nameDB id="${taskList.opticcableWay}" beanId="ItawSystemDictTypeDao" />
			</display:column>	
			<display:column property="equipmentRackNum" sortable="false" headerClass="sortable" title="设备机架数" />	
			<display:column property="materialCost" sortable="false" headerClass="sortable" title="材料费用" />	
			<display:column property="energyStationName" sortable="false" headerClass="sortable" title="对应能耗系统机房名称" />	
			<display:column property="energyStationCode" sortable="false" headerClass="sortable" title="对应能耗系统机房编号" />
			<display:column property="sendTime" sortable="true"	headerClass="sortable" title="派发时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
				
			<display:column sortable="false" headerClass="sortable" title="处理" media="html">
				<c:if test="${taskList.taskDefKey eq 'manualArchive'}">
					<a href="#" onclick="archive(${taskList.taskId},${taskList.processInstanceId},'${taskList.id}','${taskList.theme}')" title="手动归档">手动归档</a>
				</c:if>
				<c:if test="${taskList.taskDefKey ne 'manualArchive'}">
					<a
						href="${app}/activiti/roomDemolition/roomDemolition.do?method=todo&taskId=${taskList.taskId}${condition}"
						title="回复"> 处理 </a>
				</c:if>		
			</display:column>

			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
<c:if test="${!empty taskList}">
	<eoms:excelExportToProcess modelName="com.boco.activiti.partner.process.po.RoomDemolitionModel" serviceBeanId="roomDemolitionService" title="待回复工单" queryFlag="listBacklog" processKey="roomDemolition" flag="backlog">		
			<eoms:rowToProcess name="工单号" value="processInstanceId"/>
			<eoms:rowToProcess name="工单编码" value="sheetId"/>
			<eoms:rowToProcess name="工单名称" value="theme"/>
			<eoms:rowToProcess name="地市" value="city" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="区县" value="country" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="工单状态" value="statusName"/>
			<eoms:rowToProcess name="机房类型" value="stationType" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="机房名称" value="stationName"/>
			<eoms:rowToProcess name="机房级别" value="stationLevel"/>
			<eoms:rowToProcess name="机房面积" value="stationArea"/>
			<eoms:rowToProcess name="机房产权" value="stationEquity" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="年租金" value="annualRent"/>
			<eoms:rowToProcess name="租用日期" value="hireDate"/>
			<eoms:rowToProcess name="合同到期时间" value="contractTime"/>
			<eoms:rowToProcess name="光缆纤芯数" value="opticcableNum"/>
			<eoms:rowToProcess name="光缆改造方式" value="opticcableWay" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="设备机架数" value="equipmentRackNum"/>
			<eoms:rowToProcess name="材料费用" value="materialCost"/>
			<eoms:rowToProcess name="对应能耗系统机房名称" value="energyStationName"/>
			<eoms:rowToProcess name="对应能耗系统机房编号" value="energyStationCode"/>
			<eoms:rowToProcess name="派发时间" value="sendTime"/>
	</eoms:excelExportToProcess>
</c:if>	
		<%@ include file="/common/footer_eoms.jsp"%>