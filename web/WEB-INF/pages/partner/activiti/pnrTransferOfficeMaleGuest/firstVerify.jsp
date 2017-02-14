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
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=firstVerify"
				styleId="theform">
		<input type="hidden" id="tdate" name="tdate" value="${selectAttribute.beginTime}" />		
		<input type="hidden" id="tcountry" name="tcountry" value="${country}" />
		<input type="hidden" id="operateFlag" name="operateFlag" value="${operateFlag}" />
		<input type="hidden" id="isCity" name="isCity" value="${isCity}" />
		<table class="formTable" style="width:100%">
			<!-- 区县 -->
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
				<td class="label" style="width:10%">
					日期
				</td>
				<td class="content" style="width:20%">
					<input type="text" class="text" name="sheetAcceptLimit"
						readonly="readonly" id="sheetAcceptLimit" value="${selectAttribute.beginTime}"
						onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,-1)"
						alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />
				</td>
			</tr>
			<tr>
				<td colspan='4'>
						<html:submit styleClass="btn" property="method.save" styleId="method.save" onclick="return changeType1();">查询</html:submit>
						<c:if test="${exportFlag eq 'yes'}">
						<input type="button" value="导出查询结果" class="btn" id="exportQueryResults" name="exportQueryResults"  />
						<input type="button" value="导出详情" class="btn" id="exportDetails" name="exportDetails"  />
						</c:if>
						<c:if test="${tishiFlag eq 'yes'}">
					    	<span><font color="red">请选择查询条件，查询相对应的报表数据!</font></span>
					    </c:if>
					    <c:if test="${operateFlag eq 'no'}">
					    	<span><font color="red">当前登录人只有查看权限，没有处理权限！</font></span>
					    </c:if>
				</td>
				<td colspan='2'>
					<font color="red">*注意：</font>工单号变红为未审批超时工单，如：<font color="red">123456</font>
				</td>
			</tr>
		</table>
		<!-- buttons -->
		<fieldset style="border-width: 2px; border-color: #98C0F4;margin-bottom:10px;">
			<legend>
				<B>审批</B>
			</legend>
			<div>
				<c:if test="${statisticsFlag eq 'yes'}">
					<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/sceneStatisticsUtil.jsp"%>
				</c:if>
				
				
				<!-- <div style="margin-bottom:10px;">
				材料金额：XXXX元 
	
	
	
				电杆：100（棵）
	
	
	
				光缆数量：5000（米）
	
	
	
				接头盒数量：20（套）
			</div> -->
				<div>
					<span>审批人签字<font color="red">*</font></span>
					<span><input type="text" class="text" id="approvalSign" name="approvalSign" value="${approvalSign}" ${showFlag== "yes" && operateFlag== "yes"? '':'disabled="disabled"'}/></span>&nbsp;&nbsp;&nbsp;
					
					<span>联系方式<font color="red">*</font></span>
					<span><input type="text" class="text" id="approvalPhone" name="approvalPhone" value="${approvalPhone}" ${showFlag== "yes" && operateFlag== "yes"? '':'disabled="disabled"'}/></span>
					
					<c:if test="${showFlag eq 'yes' && operateFlag eq 'yes'}">
						<span><input type="button" value="提交" class="btn" id="signBtn" name="signBtn" /></span>
					</c:if>
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
		<display:column sortable="true" headerClass="sortable" title="审批">
			<c:if test="${taskList.approveFlag eq '0' && operateFlag eq 'yes'}">
				<select name="approveOperate">
					<option value="0">未审批</option>
					<option value="1">通过</option>
					<option value="2">驳回</option>
				</select>
				<input type="hidden" name="approvePId" value="${taskList.processInstanceId}" /> 
				<input type="hidden" name="approveTaskId" value="${taskList.taskId}" />
				<input type="hidden" name="approveProcessType" value="${taskList.processType}" />
			</c:if>
			<c:if test="${taskList.approveFlag eq '0' && operateFlag eq 'no'}">
				未审批
			</c:if>
			<c:if test="${taskList.approveFlag eq '1'}">
				通过
			</c:if>
			<c:if test="${taskList.approveFlag eq '2'}">
				驳回
			</c:if>
  	     </display:column>	
   		 <display:column  sortable="true" headerClass="sortable" title="工单号">
   		 	<c:if test="${taskList.changeColor eq 'true'}">		
				<a href="${app}/activiti/${taskList.pathOne}/${taskList.pathTwo}.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
						<font color="red">${taskList.processInstanceId}</font>
				</a>
			</c:if>
   		 	<c:if test="${taskList.changeColor eq 'false'}">		
				<a href="${app}/activiti/${taskList.pathOne}/${taskList.pathTwo}.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
						${taskList.processInstanceId}
				</a>
			</c:if>
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
			<a name="viewUpdateScene" href="javascript:void(0);" onclick="viewUpdateSceneTemplate(this,&quot;${taskList.processType}&quot;,&quot;${taskList.processInstanceId}&quot;,'acheck',&quot;${operateFlag}&quot;,&quot;${taskList.handleFlag}&quot;)">查看</a>
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
	<eoms:excelExportToProcess modelName="com.boco.activiti.partner.process.po.TaskModel" serviceBeanId="pnrTransferOfficeService" title="一次核验" queryFlag="first" processKey="myMaleGuestProcess" flag="" hideFlag="hide">
	        <eoms:rowToProcess name="审批" value="approveFlagName"/>		
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
});

//审批操作
jq(document).delegate("select[name='approveOperate']","change", function(){
	var approveObj=jq(this);
	var approveVal=approveObj.val();
	var tsStr="操作";
	if(approveVal=="1"){
		tsStr="通过";
	}else if(approveVal=="2"){
		tsStr="驳回";
	}
	if(approveVal!="0"){
		if(confirm("确定要"+tsStr+"吗？"+tsStr+"后，不可撤销！")){
	  		//alert(approveVal);
	  		var _tdObj=approveObj.parent("td");
	  		var approvePId=_tdObj.find("input[name='approvePId']").val();
	  		var approveTaskId=_tdObj.find("input[name='approveTaskId']").val();
	  		var approveProcessType=_tdObj.find("input[name='approveProcessType']").val();//流程类型
	  		var tdate=jq("#tdate").val();
	  		var tcountry=jq("#tcountry").val();
	  		var url="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=firstVerifyApprove&approvePId="+approvePId+"&approveTaskId="+approveTaskId+"&approveFlag="+approveVal+"&tdate="+tdate+"&tcountry="+tcountry+"&approveProcessType="+approveProcessType;
			jq.ajax({
				type: "post",
		 		url: url,
		 		//async: false,//控制同步        
				contentType: "application/json; charset=utf-8",
		 		dataType: "json",
		 		cache: false,
		 		success: function (data) {
		 			if(data.result=='success'){
		 				alert(data.content);
		 				_tdObj.text(tsStr);
		 			}else if(data.result=='error'){
		 				alert(data.content);
		 				approveObj.find("option[value='0']").attr("selected",true);
		 			}
		 		},
		 		error: function (err) {
		           alert(err);
		           approveObj.find("option[value='0']").attr("selected",true);
		        }		
			});
	  		
	  		//alert(approvePId);
	  		//alert(approveTaskId);
	  		//_tdObj.text("已审批");
		}else{
	  		approveObj.find("option[value='0']").attr("selected",true);
		}
	}	
});

//提交
jq(document).delegate("#signBtn","click", function(){
	var tdate=jq("#tdate").val();
	var tcountry=jq("#tcountry").val();
	var approvalSignObj=jq("#approvalSign");
	var approvalSign=approvalSignObj.val();
	var signBtnObj=jq(this);
	var spanObj=signBtnObj.parent("span");
	var approvalPhoneObj=jq("#approvalPhone");
	var approvalPhone=approvalPhoneObj.val();
	var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;//验证座机
	var isMob=/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;//验证手机号
	
	if(approvalSign==""){
		alert("审批人签字不允许为空，请填写后再提交！");
		return;
	}else if(approvalPhone == ""){
		alert("联系方式不允许为空，请填写后再提交！");
		return;
	}else if(!isMob.test(approvalPhone)&&!isPhone.test(approvalPhone)){
		alert("联系方式格式不正确，请填写后再提交！");
		return;
	}else{
		var temp = encodeURI(encodeURI(approvalSign));
		var url="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=submitFirstVerifyApprovalSign&tdate="+tdate+"&tcountry="+tcountry+"&approvalSign="+temp+"&approvalPhone="+approvalPhone;
		jq.ajax({
			type: "post",
	 		url: url,
	 		//async: false,//控制同步        
			contentType: "application/json; charset=utf-8",
	 		dataType: "json",
	 		cache: false,
	 		success: function (data) {
	 			if(data.result=='success'){
	 				alert(data.content);
	 				spanObj.remove();
	 				approvalSignObj.attr("disabled",true);
	 				approvalPhoneObj.attr("disabled",true);
	 				jq("#totalAmountSpan").text(data.totalAmount);
	 				jq("#poleNumSpan").text(data.poleNum);
	 				jq("#jointBoxNumSpan").text(data.jointBoxNum);
	 				jq("#opticalCableLengthSpan").text(data.opticalCableLength);
	 			}else if(data.result=='error'){
	 				alert(data.content);
	 			}
	 		},
	 		error: function (err) {
	           alert("一次核验提交失败");
	        }		
		});
	}
});

function changeType1(){
 	var country=jq("#country").val();
 	var sheetAcceptLimit=jq("#sheetAcceptLimit").val();
 	if(country==""){
 		alert("区县不能为空，请选择！");
 		return false;
 	}else if(sheetAcceptLimit==""){
 		alert("日期不能为空，请选择！");
 		return false;
 	}
 	
 	jq("#region").removeAttr("disabled");
	jq("#country").removeAttr("disabled");
}

//导出查询结果
jq(document).delegate("#exportQueryResults","click", function(){
	jq("span[class='export excel']").click();
});

//导出详情
jq(document).delegate("#exportDetails","click", function(){
	var tdate = jq("#tdate").val();
	var tcountry = jq("#tcountry").val();
	window.location.href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=exportDetails&reportFlag=acheck&country="+tcountry+"&sheetAcceptLimit="+tdate;
});

function text(){
	alert(jq("#totalAmountSpan").text());
	 				alert(jq("#poleNumSpan").text());
	 				alert(jq("#jointBoxNumSpan").text());
	 				alert(jq("#opticalCableLengthSpan").text());
}
</script>
<!-- 引入场景模板公用js代码  lookSceneJsUtil.jsp -->	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/lookSceneJsUtil.jsp"%>
<%@ include file="/common/footer_eoms.jsp"%>