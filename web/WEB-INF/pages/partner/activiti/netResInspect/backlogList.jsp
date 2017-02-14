<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>

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
			document.getElementById("region").value="";		// 地市
			document.getElementById("wsNum").value="";		// 工单号
			document.getElementById("wsTitle").value="";	        // 工单主题
			document.getElementById("country").value="";	        // 区县
			document.getElementById("resourceType").value="";	//资源类型       
			document.getElementById("questionType").value="";	    //问题类型    
			document.getElementById("specialty").value="";	 //专业      
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
    
    //点击处理按钮弹出子窗口
    function dealWith(taskid,rollbackFlag){
			var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openDealWithView&taskId='+taskid+'&rollbackFlag='+rollbackFlag;
			var _sHeight = 200;
		    var _sWidth = 820;
		    var sTop=(window.screen.availHeight-_sHeight)/2;
		    var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(url,window,sFeatures); 
    }   
    
    //批量处理是在市运维主管审核环节的工单，进行项目金额进行判断	
	function validateBudgetAmountForBatchApprove(str){
		var rel=false;
		jq.ajax({ 
			type:"POST",
			url:"${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=validateBudgetAmountForBatchApprove",  
			async: false,//控制同步
			data:{"cityManageStr":str},
			dataType:"json",
			success:function(data){
				if(data.result=="failure"){//审核不通过
				   alert(data.content);
				   rel= false;
				}
				else if(data.result=="success"){//审核通过
					rel= true;
				}
			},
	 		error: function (err) {
	           alert(err);
	           rel= false;
	        }	
		});
		return rel;
	}  
    
    //点击批量审批按钮，弹出子窗口
	 function batchApprove(){
		//var batchApprovalFlag =document.getElementById("batchApprovalFlag").value;
		var inputs =document.getElementsByName("Fruit");
		var total=0;
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].checked){
					total++;
				}
			}		
		if(total==0){
			alert("请勾选要批量处理的工单！");
		}else if(total > 50){
			//alert(total);
			alert("请勾选不超过50个的工单进行批量处理,当前已勾选"+total+"条!");
		}else{
			var tishiStr="";
			var cityManageStr="";//在市运维主管审核环节的工单的信息
			var strs= new Array(); //定义一数组
			for(var i=0;i<inputs.length;i++){
				if(inputs[i].checked){
					strs=inputs[i].value.split(","); //字符分割
					var tempVal=strs[2].trim();
					//alert(tempVal);
					if(tempVal!="cityManageExamine"&&tempVal!="cityManageDirectorAudit"&&tempVal!="cityViceAudit"&&tempVal!="provinceLineExamine"&&tempVal!="provinceLineViceAudit"&&tempVal!="provinceManageExamine"&&tempVal!="provinceManageViceAudit"){
						tishiStr+=strs[1]+",";
					}else{
						if(tempVal=="cityManageExamine"){//找出在市运维主管审核环节的工单
							cityManageStr+=inputs[i].value+"#";
						}
					}
				}
			}
			
			//alert(tishiStr);
			if(tishiStr==""){
				if(cityManageStr!=""){//有市运维主管审核的工单
					var result=validateBudgetAmountForBatchApprove(cityManageStr);
					if(!result){//金额审核不通过，不弹出批量处理窗口
						return;
					}
				}
				
				//打开批量处理窗口
				var wsStatusFlag =document.getElementById("wsStatusFlag").value;
				var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openBatchApproveView';
				var _sHeight = 260;
			    var _sWidth = 820;
			    var sTop=(window.screen.availHeight-_sHeight)/2;
			    var sLeft=(window.screen.availWidth-_sWidth)/2;
				var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
				window.showModalDialog(url,window,sFeatures); 
			}else{
				alert(tishiStr+"不在市运维主管审核,市运维主任审核,市公司副总审核,省线路主管审核,省线路总经理审核,省运维主管审核,省运维总经理审批环节，请重新进行选择！");
				return;
			}
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
		}else if(total > 50){
			//alert(total);
			alert("请勾选不超过50个的工单进行批量回退,当前已勾选"+total+"条!");
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
   		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=listBacklog"+condition;
	} 
	
   //分页设置
   function changePage(v) {
		location.href = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=listBacklog&pagesize='+v;
	}

   function archive(){
       if(confirm("确定归档处理吗？")){
		 	var url = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=doOneWorkOrderArchiving&taskId="+arguments[0]+"&processInstanceId="+arguments[1];
		     window.location.href = url;
	       }	 	
	 } 

</script>
		
<script type="text/javascript">
	//由于displaytabel不支持多表头，所以用jquery动态封装表头
	/*jq(function(){
		if(${!empty taskList}){
			var firstTR=jq("#taskList thead").find("tr:eq(0)");
			firstTR.find("th:lt(21)").attr("rowspan","2");
			firstTR.find("th:eq(31)").attr("rowspan","2");
			
			//主要工程量
			var _layingCable=firstTR.find("th:eq(21)");
			var _excavationTrench=firstTR.find("th:eq(22)");
			var _repairPipeline=firstTR.find("th:eq(23)");
			var _rightingDemolitionPole=firstTR.find("th:eq(24)");
			var _wireLaying=firstTR.find("th:eq(25)");
			var _fiberOpticCableConnector=firstTR.find("th:eq(26)");
			var _mainQuantityOther=firstTR.find("th:eq(27)");
			
			//项目描述
			
			var _constructionReasons=firstTR.find("th:eq(28)");
			var _networkStatus=firstTR.find("th:eq(29)");
			var _constructionMainContent=firstTR.find("th:eq(30)");
			
			//追加到第二行的串
			var newTr="<tr><th class='sortable'>"+_layingCable.html()+"</th><th class='sortable'>"+_excavationTrench.html()+"</th><th class='sortable'>"+_repairPipeline.html()+"</th><th class='sortable'>"+_rightingDemolitionPole.html()+"</th><th class='sortable'>"+_wireLaying.html()+"</th><th class='sortable'>"+_fiberOpticCableConnector.html()+"</th><th class='sortable'>"+_mainQuantityOther.html()+"</th><th class='sortable'>"+_constructionReasons.html()+"</th><th class='sortable'>"+_networkStatus.html()+"</th><th class='sortable'>"+_constructionMainContent.html()+"</th></tr>"
			
			//从第一行移除
			_layingCable.remove();
			_excavationTrench.remove();
			_repairPipeline.remove();
			_rightingDemolitionPole.remove();
			_wireLaying.remove();
			_fiberOpticCableConnector.remove();
			_mainQuantityOther.remove();
			_constructionReasons.remove();
			_networkStatus.remove();
			_constructionMainContent.remove();

			//追加到第一行			
			firstTR.find("th:eq(20)").after("<th class='sortable' colspan='7' style='text-align:center;' >主要工程量</th><th class='sortable' colspan='3' style='text-align:center;'>项目描述</th>");
			
			//追加到第二行
			firstTR.after(newTr);
		}

	});*/
	
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

<script type="text/javascript">
	function checkChildSceneForDetails(processInstanceId,linkType){
		//alert(processInstanceId);
		
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&processInstanceId='+processInstanceId+'&linkType='+linkType;
		var _sHeight = 300;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
		
	}
	
	//function checkChildSceneForDetails(processInstanceId){
		//alert(processInstanceId);
		
		//var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&processInstanceId='+processInstanceId;
		//var _sHeight = 300;
	    //var _sWidth = 820;
	    //var sTop=(window.screen.availHeight-_sHeight)/2;
	    //var sLeft=(window.screen.availWidth-_sWidth)/2;
		//var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		//window.showModalDialog(url,window,sFeatures);
		
	//}
	
	
	 /**
	*  打开图片选择页面
	*/
	function selectPhoto(processInstanceId){	
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=showCreateWorkPhoto&pid='+processInstanceId;
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>

 
 

		<div id="sheetform">
			<html:form action="/netResInspect.do?method=listBacklog"
				styleId="theform">
				<input type="hidden" id="batchApprovalFlag" name="batchApprovalFlag" value="${batchApprovalFlag}" /><!-- 是否能进行批量处理标识 -->
				<input type="hidden" id="wsStatusFlag" name="wsStatusFlag" value="${wsStatus}" /><!-- 工单状态标识 -->
				<!-- <input type="hidden" name="pagesize" value="${pageSize}">  -->
				<input type="hidden" id="condition" name="condition" value="${condition}" />
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					<tr>
						<td class="label" style="width:10%">
							派单开始时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label" style="width:10%">
							派单结束时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
						
						<td class="label" style="width:10%">
							地市
						</td>
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
					</tr>

					<tr>
						<!-- 工单号  -->
						<td class="label" style="width:10%">
							工单号
						</td>
						<td style="width:20%">
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${wsNum}" />
						</td>
						<!-- 工单主题 -->
						<td class="label" style="width:10%">
							工单名称
						</td>
						<td style="width:20%">
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
						<!-- 当前状态 -->
						<td class="label" style="width:10%">
							区县
						</td>
						<td style="width:20%">
							<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
						</td>
					</tr>

                  
                  <tr>
					<td class="label" style="width:10%">资源类型</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="resourceType" id="resourceType" defaultValue="${resourceType}" initDicId="12811" alt="allowBlank:false" styleClass="select" />
					</td>
					<td class="label" style="width:10%">问题类型</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="questionType" id="questionType" defaultValue="${questionType}" initDicId="12803" alt="allowBlank:false" styleClass="select" />
					</td>
					 <td class="label" style="width:10%">专业</td>
					  <td class="content">
						  <eoms:comboBox name="specialty" id="specialty" defaultValue="${specialty}" initDicId="12801" alt="allowBlank:false" styleClass="select" />
					 </td>
				</tr>
				
					
				
			<tr>
				
				<td colspan="6" class="content">				
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
										</option>
										<option value="500" ${pageSize== "500" ? 'selected="selected"':'' } >
											500
										</option>
										<option value="1000" ${pageSize== "1000" ? 'selected="selected"':'' } >
											1000
										</option>
										<option value="5000" ${pageSize== "5000" ? 'selected="selected"':'' } >
											5000
										</option>
									</select>
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
				</div>
			</html:form>
		</div>
	<!-- 	<br/>
		
		<br/>  -->
		<bean:define id="url" value="pnrTransferNewPrecheck.do" />
		<c:if test="${carApprove ne 'yes'}">
			<c:set var="export" value="false"></c:set>
		</c:if>
		<!-- 新增批量-start -->
	 <!-- 	<c:if test="${!empty taskList && taskList ne null}">
			<center>
				<div>
					<table>
						<tr>
							<td>
								设置每页显示条数
							</td>
							<td>
								<select id="pagesize" name="pagesize" style="width: 58px"
									onchange=changePage(this.value)>
									<option value="20" ${pageSize== "20" ? 'selected="selected"':'' } >
										20
									</option>
									<option value="50" ${pageSize== "50" ? 'selected="selected"':'' } >
										50
									</option>
								</select>
							</td>
						</tr>
					</table>
				</div>
			</center>
		</c:if>   -->
		
		<form id="testform" name="testform" action="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=doMoreWorkOrderArchiving" method="post">	
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="false" requestURI="netResInspect.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="true" headerClass="sortable" title="工单号">
				<a href="${app}/activiti/netResInspect/netResInspect.do?method=todo&taskId=${taskList.taskId}">${taskList.processInstanceId} </a>
			</display:column>
			<display:column property="theme" sortable="false" headerClass="sortable" title="&nbsp;&nbsp;&nbsp;工单名称&nbsp;&nbsp;&nbsp;" maxLength="10" />
			<display:column property="reportedDate" sortable="true"	headerClass="sortable" title="派发时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.county}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="专业">
				<eoms:id2nameDB id="${taskList.specialty}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="资源类型">
				<eoms:id2nameDB id="${taskList.resourceType}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="问题类型">
				<eoms:id2nameDB id="${taskList.questionType}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="环节" property="taskDefKeyName" />
			<display:column sortable="false" headerClass="sortable" title="子流程" property="subProcessName" />
			<display:column sortable="false" headerClass="sortable" title="子流程号" property="subProcessInstanceId" />
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
<c:if test="${!empty taskList}">
	<eoms:excelExportToProcess modelName="com.boco.activiti.partner.process.po.NetResInspectModel" serviceBeanId="netResInspectService" title="待回复工单" queryFlag="listBacklog" processKey="netResInspect" flag="backlog">		
			<eoms:rowToProcess name="工单号" value="processInstanceId"/>
			<eoms:rowToProcess name="工单名称" value="theme"/>
			<eoms:rowToProcess name="派发时间" value="reportedDate"/>
			<eoms:rowToProcess name="地市" value="city" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="区县" value="county" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="专业" value="specialty" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="资源类型" value="resourceType" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="问题类型" value="questionType" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="环节" value="taskDefKeyName" />
			<eoms:rowToProcess name="子流程" value="subProcessName" />
			<eoms:rowToProcess name="子流程号" value="subProcessInstanceId" />
	</eoms:excelExportToProcess>
</c:if>

<%@ include file="/common/footer_eoms.jsp"%>