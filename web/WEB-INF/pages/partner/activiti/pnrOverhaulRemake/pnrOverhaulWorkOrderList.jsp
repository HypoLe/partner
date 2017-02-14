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
			document.getElementById("wsTitle").value="";	        // 工单主题
			document.getElementById("status").value="";	        // 当前状态
			document.getElementById("region").value="";	        
			document.getElementById("country").value="";	      
			document.getElementById("processType").value="";	      
			document.getElementById("workOrderDegree").value="";	      			      
			document.getElementById("lineType").value="";	      
		       
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
		var inputs =document.getElementsByName("Fruit");
		var total=0;
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].checked){
					total++;
				}
			}		
		if(total==0){
			alert("请勾选要批量处理的工单！");
		}else{
			var url = '${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=openBatchApproveView';
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
		}else{
			var url = '${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=openBatchRegressionView';
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
   		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=listBacklog"+condition;
	} 

   function archive(){
       if(confirm("确定不需要手机端抽查，直接归档吗？")){
		 	var url = "${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=doOneWorkOrderArchiving&taskId="+arguments[0]+"&processInstanceId="+arguments[1];
		     window.location.href = url;
	       }	 	
	 } 
	
	 //提交时对查询条件进行校验
	function changeType1(){
		var sheetAcceptLimit = document.getElementById("sheetAcceptLimit").value; 
		var sheetCompleteLimit = document.getElementById("sheetCompleteLimit").value;
		var wsNum = document.getElementById("wsNum").value;//工单号为空时校验时间，工单号不为空时校验时间
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
<script type="text/javascript">

		 //动态获取场景下拉选
	 jq(function(){
		var mainSceneUrl="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=getMainScene";
		var tempMainSceneId="${mainSceneId}";
		jq.getJSON(mainSceneUrl,function (data) {
                jq(data).each(function () {
                	if(this.id == tempMainSceneId){
                		jq("<option/>").html(this.name).val(this.id).attr("selected","selected").appendTo("#mainSceneId");
                	}else{
                	 jq("<option/>").html(this.name).val(this.id).appendTo("#mainSceneId");
                	}
                	
                });

            });
       });
</script>
		



 
 

		<div id="sheetform">
			<html:form action="/pnrOverhaulRemake.do?method=pnrOverhaulWorkOrderList"
				styleId="theform">
				<input type="hidden" id="queryAllowed" name="queryAllowed" value="Y" /><!-- 能查询标识 -->
				<input type="hidden" id="condition" name="condition" value="${condition}" />
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							派单开始时间
						</td>
						<td class="text">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							派单结束时间
						</td>
						<td class="text">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1,0)"
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
						<!-- 工单主题 -->
						<td class="label">
							项目名称
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
			
					<td class="label">地市</td>
					<td class="text">
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
					<td class="text">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
				<tr>
			
		   
						<!-- 当前状态 -->
						<td class="label">
							工单状态
						</td>
						<td >
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="">
									所有
								</option>
								<option value="cityLineExamine" ${wsStatus == "cityLineExamine" ? 'selected="selected"':'' }>
									市线路发起
								</option>
								<option value="cityLineDirectorAudit" ${wsStatus == "cityLineDirectorAudit" ? 'selected="selected"':'' }>
									市线路主任审核
								</option>
								<option value="cityManageExamine" ${wsStatus == "cityManageExamine" ? 'selected="selected"':'' }>
									市运维主管审核
								</option>
								<option value="cityManageDirectorAudit" ${wsStatus == "cityManageDirectorAudit" ? 'selected="selected"':'' }>
									市运维主任审核
								</option>								
								<option value="cityViceAudit" ${wsStatus == "cityViceAudit" ? 'selected="selected"':'' }>
									市副总审核
								</option>
								<option value="provinceLineExamine" ${wsStatus == "provinceLineExamine" ? 'selected="selected"':'' }>
									省线路主管审核
								</option>
								<option value="provinceLineViceAudit" ${wsStatus == "provinceLineViceAudit" ? 'selected="selected"':'' }>
									省线路分管经理审核
								</option>
								<option value="provinceManageExamine" ${wsStatus == "provinceManageExamine" ? 'selected="selected"':'' }>
									省运维主管审核
								</option>
								<option value="provinceManageViceAudit" ${wsStatus == "provinceManageViceAudit" ? 'selected="selected"':'' }>
									省运维分管经理审批
								</option>
								<option value="sendOrder" ${wsStatus == "sendOrder" ? 'selected="selected"':'' }>
									代维公司转派
								</option>
								<option value="worker" ${wsStatus == "worker" ? 'selected="selected"':'' }>
									施工队现场处理
								</option>
								<option value="daiweiAudit" ${wsStatus == "daiweiAudit" ? 'selected="selected"':'' }>
									工单发起人审核
								</option>
								<option value="orderAudit" ${wsStatus == "orderAudit" ? 'selected="selected"':'' }>
									市运维主管抽查
								</option>
								<option value="waitOrder" ${wsStatus == "waitOrder" ? 'selected="selected"':'' }>
									待办
								</option>
								<option value="waitingCallInterface" ${wsStatus == "waitingCallInterface" ? 'selected="selected"':'' }>
									省公司审批通过-等待商城
								</option>
								<option value="spotChecks" ${wsStatus == "spotChecks" ? 'selected="selected"':'' }>
									已抽查
								</option>
								<option value="archive" ${wsStatus == "archive" ? 'selected="selected"':'' }>
									已归档
								</option>
								<option value="delete" ${wsStatus == "delete" ? 'selected="selected"':'' }>
									已删除
								</option>
							</select>
						</td>
		<!-- 	<td class="label">
				工单类别
			</td>
			 <td class="class="text"">
				<eoms:comboBox name="precheckFlag" id="precheckFlag"
					defaultValue="${precheckFlag}" initDicId="1012314"
					alt="allowBlank:false" styleClass="select" />
			 </td> -->
			  	<td class="label" >
					大修/改造
				</td>
				<td class="text">
					<select id="processType" name="processType" class="select">
						<option value="">
							请选择
						</option>
						<option value="overhaul" <c:if test="${processType =='overhaul'}">selected="selected"</c:if> >
							大修
						</option>
						<option value="reform" <c:if test="${processType =='reform'}">selected="selected"</c:if> >
							改造
						</option>
					</select>
				</td>
					<td class="label">
							紧急程度
						</td>
						
							<td class="class="text"">
								<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
									defaultValue="${workOrderDegree}" initDicId="1012309"
									alt="allowBlank:false" styleClass="select" />
							 </td>
					</tr>
					
					
			<tr>
			<td class="label">
							线路级别
						</td>
						
							<td class="class="text"" >
								<eoms:comboBox name="lineType" id="lineType"
									defaultValue="${lineType}" initDicId="101231702"
									alt="allowBlank:false" styleClass="select" />
							 </td>
				<td colspan="4">
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
						styleId="method.save" onclick="return changeType1();">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
					<c:if test="${tishiFlag eq 'Y'}">
				    	<span><font color="red">请选择查询条件，查询相对应的数据!</font></span>
			        </c:if>
<!--					<html:button property="" styleClass="btn" onclick="batchApprove()">批量处理</html:button>-->
<!--					<html:button property="" styleClass="btn" onclick="batchRegression()">批量回退</html:button>-->
				</div>
			</html:form>
		</div>
		<bean:define id="url" value="pnrOverhaulRemake.do" />
		<c:if test="${carApprove ne 'yes'}">
			<c:set var="export" value="false"></c:set>
		</c:if>
		<!-- 新增批量-start -->
		<form id="testform" name="testform" action="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=doMoreWorkOrderArchiving" method="post">	
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="false" requestURI="pnrOverhaulRemake.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="true" headerClass="sortable" title="工单号">
				<a href="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
				${taskList.processInstanceId}
				</a>
			</display:column>
			
			<display:column property="sheetId" sortable="true" headerClass="sortable" title="工单编码" />
				
			<display:column property="theme" sortable="false"
				headerClass="sortable" title="项目名称" maxLength="15" />
				
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="工单状态">
				${taskList.statusName}
			</display:column>
			<display:column property="applicationTime" sortable="true"	headerClass="sortable" title="立项时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="distributedInterfaceTime" sortable="true"	headerClass="sortable" title="省公司批复时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="紧急程度">
				<eoms:id2nameDB id="${taskList.workOrderDegree}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="true" property="projectAmount" headerClass="sortable" title="项目预算"/>
			<display:column sortable="true" headerClass="sortable" title="实物赔补">
				<fmt:formatNumber value="${taskList.kindRestitution}" pattern="##.##" maxFractionDigits='2' minFractionDigits='0'  />
			</display:column>
			<display:column sortable="true" property="compensate" headerClass="sortable" title="现金赔补"/>
			<display:column sortable="true" property="calculateIncomeRatio" headerClass="sortable" title="收支比" />

			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
<c:if test="${!empty taskList}">
	<eoms:excelExportToProcess modelName="com.boco.activiti.partner.process.po.TaskModel" serviceBeanId="pnrTransferNewPrecheckService" title="大修改造工单查询" queryFlag="localNetworkWorkOrder" processKey="overHaulNewProcess" flag="">		
			<eoms:rowToProcess name="工单号" value="processInstanceId"/>
			<eoms:rowToProcess name="工单编码" value="sheetId"/>
			<eoms:rowToProcess name="项目名称" value="theme" />
			<eoms:rowToProcess name="地市" value="city" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="区县" value="country" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="工单状态" value="statusName" />
			<eoms:rowToProcess name="立项时间" value="applicationTime" />
			<eoms:rowToProcess name="省公司批复时间" value="distributedInterfaceTime" />
			<eoms:rowToProcess name="紧急程度" value="workOrderDegree" />
			<eoms:rowToProcess name="项目预算" value="projectAmount" />
			<eoms:rowToProcess name="实物赔补" value="kindRestitution" />
			<eoms:rowToProcess name="现金赔补" value="compensate"/>
			<eoms:rowToProcess name="收支比" value="calculateIncomeRatio" />
	</eoms:excelExportToProcess>
</c:if>	
		<%@ include file="/common/footer_eoms.jsp"%>