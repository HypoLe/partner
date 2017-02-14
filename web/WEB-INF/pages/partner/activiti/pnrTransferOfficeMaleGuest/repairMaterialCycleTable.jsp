<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
		//重置
		function newReset(){
			document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
			document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
			document.getElementById("maleGuestNumber").value="";		// 工单号
			document.getElementById("wsTitle").value="";	        // 工单主题
			document.getElementById("wsNum").value="";	        // 业务号码
			document.getElementById("status").value="";	        // 当前状态
			document.getElementById("installAddress").value="";	        // 地址
			document.getElementById("dept").value="";	        // 所属区域
			document.getElementById("person").value="";	        // 当前处理人
		}
		</script>
		
		<!-- 批量回复按钮点击事件  -->	
		<script type="text/javascript">
		function batchReply(){
			var total = 0;
			var max =document.testform.Fruit.length;
	        for (var idx = 0; idx < max; idx++) {
	        if (eval("document.testform.Fruit[" + idx + "].checked") == true) {
	          total += 1;
	        }
	       }
	       if(total==0){
	       	//alert("您选择了 " + total + " 个选项！");
	       	alert("请选择要处理的工单！");
	       }else if(total>50){
			alert("批量回复最多回复50条,当前已勾选"+total+"条!");
		   }else{
	      // 	alert("提交");
	       		document.getElementById("testform").submit();
	       }
	 
		}
			
		</script>
		
		<script type="text/javascript">
function getAll(){ 
var tit = document.getElementById("operAll");
var inputs = document.testform.Fruit;
for(var i = 0; i < inputs.length; i++) {

if(tit.checked == true)   { 
inputs[i].checked = true;  
 }else{
 inputs[i].checked = false;  
 } 
 }
 }
 
 </script>
 
 	<script type="text/javascript">
		
		function batchApprove(){
			if(window.confirm('确定要把选中的工单进行批量处理操作麽？')){
                		var total = 0;
						var objs ="";
						var max =document.testform.Fruit.length;
						if(max == null || max == ''){
							if(eval("document.testform.Fruit.checked") == true){
								total += 1;
				          		objs+=document.testform.Fruit.value+";";
							}
						}else {
							  for (var idx = 0; idx < max; idx++) {
						        if (eval("document.testform.Fruit[" + idx + "].checked") == true) {
						          total += 1;
						          objs+=document.testform.Fruit[idx].value+";";
						        }
						      }
						}
				       if(total==0){
				       		//alert("您选择了 " + total + " 个选项！");
				       		alert("请选择要处理的工单！");
				       }else if(total>50){
							alert("批量处理最多处理50条,当前已勾选"+total+"条!");
					   }else{
				       		    $.ajax({
				       		    	type:'POST',      
								    url:'${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=batchApprove',      
								    type:'post',      
								    data:'taskids='+objs,      
								    dataType:'text',
								    async : true, //默认为true 异步          
								    success:function(data, textStatus){
									    if(data.indexOf("true")>-1){ //判断返回的字符串中是否包含true
					                     	 alert("批量处理成功!");      
									      	 location.reload(true);   
				                        }else{
				                         	 alert("批量处理出错,请联系管理员!");
				                        }        			
								       
								    }   
								}); 
				       }
                 return true;
              }else{
                 //alert("取消");
                 return false;
             }

		
			
	 
		}
 	</script>
		
		

		<div id="sheetform">
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=repairMaterialCycleTable"
				styleId="theform">
			    <!-- 地市 --><input type="hidden" id="tRegion" name="tRegion" value="${region}" />		
		        <!--区县 --><input type="hidden" id="tCountry" name="tCountry" value="${country}" />			
		        <!--开始日期 --><input type="hidden" id="tStartTime" name="tStartTime" value="${startTime}" />			
		        <!--结束日期 --><input type="hidden" id="tEndTime" name="tEndTime" value="${endTime}" />			
		        <!-- 总条数 --><input type="hidden" id="tTotal" name="tTotal" value="${total}" />
		        <!--可操作标识 --><input type="hidden" id="operateFlag" name="operateFlag" value="${operateFlag}" />
		        <!--是否地市 --><input type="hidden" id="isCity" name="isCity" value="${isCity}" />
			
				<table class="formTable">
					<!-- 地市 -->
					<tr>
						<td class="label" style="width:10%">地市</td>
					<td class="content" style="width:20%">
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
					<!-- 区县 -->
					<td class="label" style="width:10%">区县</td>
					<td class="content" style="width:20%">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
						<!-- 日期 -->
						<td class="label">
							开始日期
						</td>
						<td>
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${selectAttribute.beginTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							结束日期
						</td>
						<td>
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${selectAttribute.endTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
					</tr>
					<tr>
						<td colspan='8'>
								<html:submit styleClass="btn" property="method.save" styleId="method.save" onclick="return changeType1();">查询</html:submit>
								<c:if test="${createFlag eq 'yes' && operateFlag eq 'yes'}">
									<input type="button" class="btn" id="generateReportBtn" name="generateReportBtn" value="生成报表" />
								</c:if>
								<span id="exportSpan">
									<c:if test="${exportFlag eq 'yes'}">
										<input type="button" value="导出报表" class="btn" id="exportReport" name="exportReport"  />
										<input type="button" value="导出详情" class="btn" id="exportDetails" name="exportDetails"  />
									</c:if>			
							   </span>
								<c:if test="${tishiFlag eq 'yes'}">
							    	<span><font color="red">请选择查询条件中的区县和日期，查询相对应的报表数据!</font></span>
							    </c:if>
								<c:if test="${cycleCollarReportMsg ne ''}">
							    	<span><font color="red">选择的时间范围，在${cycleCollarReportMsg}存在，可以按对应的时间条件查看历史数据。在历史数据中不存在的时间，可以选择对应的时间，生成报表！</font></span>
							    </c:if>
							    <c:if test="${operateFlag eq 'no'}">
							    	<span><font color="red">当前登录人只有查看权限，没有处理权限！</font></span>
							    </c:if>
						</td>
					</tr>
				</table>
				<!-- buttons -->
				<fieldset style="border-width: 2px; border-color: #98C0F4;margin-bottom:10px;">
					<legend>
						<B>审批</B>
					</legend>
					<div>
						<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/materialQuantityUtil.jsp"%><!-- 材料数量 -->
					<!-- <div style="margin-bottom:10px;">
						材料金额：XXXX元 
			
			
			
						电杆：100（棵）
			
			
			
						光缆数量：5000（米）
			
			
			
						接头盒数量：20（套）
					</div> -->
						<div>
							<table class="formTable">
								<tr>
									<td>报表编号</td>
									<td><input type="text" class="text" id="reportNumber" name="reportNumber" value="${reportNumber}" disabled="true"/></td>
									<td>请上传签字版故障综合周期性报表</td>
									<td><eoms:attachment name="sheetMain" property="sheetAccessories"
									scope="request" idField="sheetAccessories"
									appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件" /></td>
								</tr>
								<tr>
									<td>联系方式<font color="red">*</font></td>
									<td colspan='3'><input type="text" class="text" id="approvalPhone" name="approvalPhone" value="${approvalPhone}" ${submitFlag eq "yes" && operateFlag eq "yes"? '':'disabled="disabled"'}/></td>
								</tr>
								<tr>
									<td colspan='4'>
									<span id="signSpan">
										<c:if test="${submitFlag eq 'yes' && operateFlag eq 'yes'}">
											<input type="button" value="提交" class="btn" id="signBtn" name="signBtn" />
										</c:if>
										<c:if test="${submitFlag eq 'timeOut'}">
											<font color="red">未提交，已超过7天</font>
										</c:if>
										<c:if test="${submitFlag eq 'over'}">
											<font color="red">已完成提交</font>
										</c:if>
									</span>
									<span id="tiJiaoMsgSpan" style="display:none"><!-- 提交时给出提示 -->
										<font color="red">提交操作正在后台执行中，过程可能较慢，请不要在返回提示结果之前，刷新或关闭窗口!</font>
									</span>
							 </td>
								</tr>
							</table>
						</div>
					</div>
			</fieldset>
				
			</html:form>
		</div>
<br />
<bean:define id="url" value="pnrTransferOfficeMaleGuest.do"/>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
		sort="list" size="total" partialList="true">
   		<display:column  sortable="true" headerClass="sortable" title="工单号">
   		 	<a href="${app}/activiti/${taskList.pathOne}/${taskList.pathTwo}.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
						${taskList.processInstanceId}
			</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单类型">${taskList.processTypeName}</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="工单主题"  >
			${taskList.theme}
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市" >
			<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="是否归集">
			${taskList.maleGuestStateName}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="归集工单查看">
			<c:if test="${taskList.maleGuestState eq '1'}">
				<a href="javascript:void(0);" onclick="viewSingleCollection(&quot;${taskList.processInstanceId}&quot;,&quot;${taskList.siteCd}&quot;)">查看</a>
			</c:if>
			<c:if test="${taskList.maleGuestState ne '1'}">
				-
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="主场景" >
			${taskList.recentMainScenesName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="子场景" >
			${taskList.recentCopyScenesName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="场景模板" >
			<a href="javascript:void(0);" onclick="viewUpdateSceneTemplate(this,&quot;${taskList.processType}&quot;,&quot;${taskList.processInstanceId}&quot;,'auditReport',&quot;${operateFlag}&quot;,'Y')">查看</a>
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="公客系统申告时间">
				<fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${taskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
				${taskList.processLimit}
		</display:column>
		
       <display:column  sortable="true"
			headerClass="sortable" title="业务号码" >
			${taskList.barrierNumber}
			</display:column>
			
       <display:column  sortable="true"
			headerClass="sortable" title="工单子类型" >
				<eoms:id2nameDB id="${taskList.subType}" beanId="ItawSystemDictTypeDao" />
			</display:column>

       <display:column  sortable="true"
			headerClass="sortable" title="局站名称" >
				${taskList.stationName}
			</display:column>
       	<display:column  sortable="true" headerClass="sortable" title="所属区域">
			<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障原因" >
			<eoms:id2nameDB id="${taskList.faultSource}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="现场拍照" >
			<a href="javascript:void(0);" onclick="viewLiveCamera(&quot;${taskList.processInstanceId}&quot;)">查看</a>
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
				<fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		
		<display:column sortable="true"
			headerClass="sortable" title="故障处理回复时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${taskList.lastReplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
				${taskList.workTask}
		</display:column>
		
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			${taskList.statusName}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="联系人" >
			${taskList.connectPerson}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			${taskList.installAddress}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>

<!-- 导出 -->	
<c:if test="${!empty taskList}">
	<eoms:excelExportToProcess modelName="com.boco.activiti.partner.process.po.TaskModel" serviceBeanId="pnrTransferOfficeService" title="抢修材料周期领用表" queryFlag="cycle" processKey="myMaleGuestProcess" flag="" hideFlag="hide">		
			<eoms:rowToProcess name="工单号" value="processInstanceId"/>
			<eoms:rowToProcess name="工单类型" value="processTypeName"/>
			<eoms:rowToProcess name="工单主题" value="theme"/>
			<eoms:rowToProcess name="地市" value="city" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="区县" value="country" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="是否归集" value="maleGuestStateName"/>
			<eoms:rowToProcess name="主场景" value="recentMainScenesName"/>
			<eoms:rowToProcess name="子场景" value="recentCopyScenesName"/>
			<eoms:rowToProcess name="公客系统申告时间" value="createTime"/>
			<eoms:rowToProcess name="派单时间" value="sendTime"/>
			<eoms:rowToProcess name="处理时限(小时)" value="processLimit"/>
			<eoms:rowToProcess name="业务号码" value="barrierNumber"/>
			<eoms:rowToProcess name="工单子类型" value="subType" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="局站名称" value="stationName"/>
			<eoms:rowToProcess name="所属区域" value="deptId" dictDaoName="tawSystemDeptDao"/>
			<eoms:rowToProcess name="代维公司" value="initiator" dictDaoName="tawSystemUserDao"/>
			<eoms:rowToProcess name="故障原因" value="faultSource" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="故障发生时间" value="createTime"/>
			<eoms:rowToProcess name="故障处理回复时间" value="lastReplyTime"/>
			<eoms:rowToProcess name="工单历时" value="workTask"/>
			<eoms:rowToProcess name="当前状态" value="statusName"/>
			<eoms:rowToProcess name="联系人" value="connectPerson"/>
			<eoms:rowToProcess name="地址" value="installAddress"/>
			<eoms:rowToProcess name="当前处理人" value="executor" dictDaoName="tawSystemUserDao"/>
	</eoms:excelExportToProcess>
</c:if>		
	
	
<script type="text/javascript">
jq(function(){
	var values = "${isCity}";
	if(values=='city'){
		jq("#region").attr("disabled",true);
	}else if(values=='country'){
		jq("#region").attr("disabled",true);
		jq("#country").attr("disabled",true);
	}
	
	//控制附件的上传下载
	var submitFlagVal = "${submitFlag}";
	var operateFlagVal = "${operateFlag}";
	if(submitFlagVal=='yes' && operateFlagVal=='yes'){
		jq(window.frames["UIFrame1-sheetAccessories"].document).find("input[name='button']").each(function(index,data){
			jq(data).removeAttr("disabled");
		});
	}else{
		jq(window.frames["UIFrame1-sheetAccessories"].document).find("input[name='button']").each(function(index,data){
			jq(data).attr("disabled",true);
		});
	}
});

function changeType1(){
 	var country=jq("#country").val();
 	var sheetAcceptLimit=jq("#sheetAcceptLimit").val();
 	var sheetCompleteLimit=jq("#sheetCompleteLimit").val();
 	if(country==""){
 		alert("区县不能为空，请选择！");
 		return false;
 	}else if(sheetAcceptLimit==""){
 		alert("开始日期不能为空，请选择！");
 		return false;
 	}else if(sheetCompleteLimit==""){
 		alert("结束日期不能为空，请选择！");
 		return false;
 	}
 	
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
 	
 	jq("#region").removeAttr("disabled");
	jq("#country").removeAttr("disabled");	
}

//生成报表
jq(document).delegate("#generateReportBtn","click", function(){
	var obj=jq(this);
	var tRegion=jq("#tRegion").val();
	var tCountry=jq("#tCountry").val();
	var tStartTime=jq("#tStartTime").val();
	var tEndTime=jq("#tEndTime").val();
	var tTotal=jq("#tTotal").val();
	
	var date = new Date();
	var st=tStartTime;
 	st = st.replace(/-/g,"/");
 	var dateS = new Date(st);
 	var time = date.getTime()- dateS.getTime();
 	var days = parseInt(time / (1000 * 60 * 60 * 24));
 	if(days > 15){
 		alert("统计周期为15天，开始日期不能超过当前日期的前15天！");
 		return;
 	}
	
	var et=tEndTime;
 	et = et.replace(/-/g,"/");
 	var date2 = new Date(et);
 	time = date.getTime()- date2.getTime();
 	days = parseInt(time / (1000 * 60 * 60 * 24));
 	if(days > 15){
 		alert("统计周期为15天，结束日期不能超过当前日期的前15天！");
 		return;
 	}
 	
 	obj.attr("disabled", true); //点击生成报表按钮后，回显，避免重复点击
 	 
 	var url="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=createCycleCollarReport&tRegion="+tRegion+"&tCountry="+tCountry+"&tStartTime="+tStartTime+"&tEndTime="+tEndTime+"&tTotal="+tTotal;
	jq.ajax({
		type: "post",
 		url: url,
 		//async: false,//控制同步        
		contentType: "application/json; charset=utf-8",
 		dataType: "json",
 		cache: false,
 		success: function (data) {
 			if(data.result=='success'){
 				alert("生成报表成功！报表编号为："+data.content);
 				jq("#signSpan").append("<input type='button' value='提交' class='btn' id='signBtn' name='signBtn' />");
 				jq("#exportSpan").append("<input type='button' value='导出报表' class='btn' id='exportReport' name='exportReport' /><input type='button' value='导出详情' class='btn' id='exportDetails' name='exportDetails' />");
 				jq("#reportNumber").val(data.content);
 				jq("#approvalPhone").attr("disabled",false);
 				jq(window.frames["UIFrame1-sheetAccessories"].document).find("input[name='button']").each(function(index,data){
					jq(data).removeAttr("disabled");
				});
 				obj.remove();
 				
 				//设置材料汇总信息
 				jq("#totalAmountSpan").text(data.totalAmount);
	 			//jq("#poleNumSpan").text(data.poleNum);
	 			//jq("#jointBoxNumSpan").text(data.jointBoxNum);
	 			//jq("#opticalCableLengthSpan").text(data.opticalCableLength);
	 			
	 			//显示材料汇总div
	 			//jq("#maleSceneStatisticsDiv").css("display","block"); 
	 			
	 			//设置材料数量统计
	 			 jq(data.materialQuantity).each(function () {
	 			 	jq("#"+this.filedName).text(this.filedVal);
	 			 });
	 			
	 			//显示材料数量统计div
	 			jq("#materialQuantityDiv").css("display","block");
 			}
 		},
 		error: function (err) {
           alert("生成报表失败！");
        }		
	});
});

//提交按钮
jq(document).delegate("#signBtn","click", function(){
	var obj=jq(this);
	var fuJianVal=jq("#sheetAccessories").val();
	var approvalPhoneObj=jq("#approvalPhone");
	var approvalPhone=approvalPhoneObj.val();
	
	var tRegion=jq("#tRegion").val();
	var tCountry=jq("#tCountry").val();
	var tStartTime=jq("#tStartTime").val();
	var tEndTime=jq("#tEndTime").val();
	
	var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;//验证座机
	var isMob=/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;//验证手机号
	if(fuJianVal==''){
		alert("请上传签字！");
		return;
	}
	if(approvalPhone == ""){
		alert("联系方式不允许为空，请填写后再提交！");
		return;
	}
	
	if(!isMob.test(approvalPhone)&&!isPhone.test(approvalPhone)){
		alert("联系方式格式不正确，请填写后再提交！");
		return;
	}
	
	obj.attr("disabled", true); //点击提交按钮后，回显，避免重复点击
	jq("#tiJiaoMsgSpan").css("display","block");//显示提交时候的提示信息
	
	var reportNumber=jq("#reportNumber").val();
	var url="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=submitCreateCycleCollarReport&reportNumber="+reportNumber+"&fuJianVal="+fuJianVal+"&approvalPhone="+approvalPhone+"&tRegion="+tRegion+"&tCountry="+tCountry+"&tStartTime="+tStartTime+"&tEndTime="+tEndTime;
	jq.ajax({
		type: "post",
 		url: url,
 		//async: false,//控制同步        
		contentType: "application/json; charset=utf-8",
 		dataType: "json",
 		cache: false,
 		success: function (data) {
 			if(data.result=='success'){
 				jq("#tiJiaoMsgSpan").css("display","none");//隐藏提示信息
 				alert("提交成功！");
 				jq("#signSpan").append("<font color='red'>已完成提交</font>");
 				approvalPhoneObj.attr("disabled",true);
 				obj.remove();
 			}
 		},
 		error: function (err) {
 		   jq("#tiJiaoMsgSpan").css("display","none");//隐藏提示信息
           alert("提交失败！");
        }		
	});
});

//导出报表
jq(document).delegate("#exportReport","click", function(){
	jq("span[class='export excel']").click();
});

//导出详情
jq(document).delegate("#exportDetails","click", function(){
	var tCountry = jq("#tCountry").val(); //区县
	var tStartTime = jq("#tStartTime").val(); //开始日期
	var tEndTime = jq("#tEndTime").val(); //结束日期

	window.location.href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=exportDetails&reportFlag=auditReport&country="+tCountry+"&sheetAcceptLimit="+tStartTime+"&sheetCompleteLimit="+tEndTime;
});

//导出材料数量汇总结果
jq(document).delegate("#exportMaterialQuantity","click", function(){
	var tCountry = jq("#tCountry").val(); //区县
	var tStartTime = jq("#tStartTime").val(); //开始日期
	var tEndTime = jq("#tEndTime").val(); //结束日期

	window.location.href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=exportMaterialQuantity&reportFlag=auditReport&country="+tCountry+"&sheetAcceptLimit="+tStartTime+"&sheetCompleteLimit="+tEndTime;
});
</script>
<!-- 引入场景模板公用js代码  lookSceneJsUtil.jsp -->	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/lookSceneJsUtil.jsp"%>	
<%@ include file="/common/footer_eoms.jsp"%>