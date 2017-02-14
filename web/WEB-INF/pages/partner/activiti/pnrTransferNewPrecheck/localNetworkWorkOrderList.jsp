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
			document.getElementById("lineType").value="";	       
			//document.getElementById("provName").value="";	        
			document.getElementById("precheckFlag").value="";	        
			document.getElementById("mainSceneId").value="";	 //场景       
			document.getElementById("keyWord").value="";	 //关键字   
			document.getElementById("workOrderDegree").value="";	 //紧急程度     
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
		}else{
			var tishiStr="";
			var strs= new Array(); //定义一数组
			for(var i=0;i<inputs.length;i++){
				if(inputs[i].checked){
					strs=inputs[i].value.split(","); //字符分割
					var tempVal=strs[2].trim();
					//alert(tempVal);
					if(tempVal!="workOrderCheck"&&tempVal!="cityLineExamine"&&tempVal!="cityLineDirectorAudit"&&tempVal!="cityManageExamine"&&tempVal!="cityManageDirectorAudit"){
						tishiStr+=strs[1]+",";
					}
				}
			}
			
			//alert(tishiStr);
			if(tishiStr==""){
				var wsStatusFlag =document.getElementById("wsStatusFlag").value;
				var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openBatchApproveView';
				var _sHeight = 260;
			    var _sWidth = 820;
			    var sTop=(window.screen.availHeight-_sHeight)/2;
			    var sLeft=(window.screen.availWidth-_sWidth)/2;
				var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
				window.showModalDialog(url,window,sFeatures); 
			}else{
				alert(tishiStr+"不在工单发起审核,市线路主管审核,市线路主任审核,市运维主管审核,市运维主任审核环节，请重新进行选择！");
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
			var _constructionMainclass="text"=firstTR.find("th:eq(30)");
			
			//追加到第二行的串
			var newTr="<tr><th class='sortable'>"+_layingCable.html()+"</th><th class='sortable'>"+_excavationTrench.html()+"</th><th class='sortable'>"+_repairPipeline.html()+"</th><th class='sortable'>"+_rightingDemolitionPole.html()+"</th><th class='sortable'>"+_wireLaying.html()+"</th><th class='sortable'>"+_fiberOpticCableConnector.html()+"</th><th class='sortable'>"+_mainQuantityOther.html()+"</th><th class='sortable'>"+_constructionReasons.html()+"</th><th class='sortable'>"+_networkStatus.html()+"</th><th class='sortable'>"+_constructionMainclass="text".html()+"</th></tr>"
			
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
			_constructionMainclass="text".remove();

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
	function checkChildSceneForDetails(processInstanceId){
		//alert(processInstanceId);
		
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&processInstanceId='+processInstanceId;
		var _sHeight = 300;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
		
	}
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
			<html:form action="/pnrTransferNewPrecheck.do?method=localNetworkWorkOrderList"
				styleId="theform">
				<input type="hidden" id="queryAllowed" name="queryAllowed" value="Y" /><!-- 能查询标识 -->
				<input type="hidden" id="batchApprovalFlag" name="batchApprovalFlag" value="${batchApprovalFlag}" /><!-- 是否能进行批量处理标识 -->
				<input type="hidden" id="wsStatusFlag" name="wsStatusFlag" value="${wsStatus}" /><!-- 工单状态标识 -->
				<!-- <input type="hidden" name="pagesize" value="${pageSize}">  -->
				<input type="hidden" id="condition" name="condition" value="${condition}" />
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							派单开始时间
						</td>
						<td class="class="text"">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							派单结束时间
						</td>
						<td class="class="text"">
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
							工单名称
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
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
				</tr>
				<tr>
			<td class="label">
				线路级别
			</td>
			<td class="class="text"">
			<eoms:comboBox name="lineType" id="lineType"
					defaultValue="${lineType}" initDicId="1012310"
					alt="allowBlank:false" styleClass="select" />
			</td>
			  <td class="label">主场景</td>
			  <td class="class="text"">
				  <select id="mainSceneId" name="mainSceneId" class="select">
				  	<option value="">--请选择场景--</option>
				  </select>
			 </td>
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
								<option value="need" ${wsStatus == "need" ? 'selected="selected"':'' }>
									工单发起
								</option>
								<option value="workOrderCheck" ${wsStatus == "workOrderCheck" ? 'selected="selected"':'' }>
									工单发起审核
								</option>
								<option value="cityLineExamine" ${wsStatus == "cityLineExamine" ? 'selected="selected"':'' }>
									市线路主管审核
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
									市公司副总审核
								</option>
								<option value="provinceLineExamine" ${wsStatus == "provinceLineExamine" ? 'selected="selected"':'' }>
									省线路主管审核
								</option>
								<option value="provinceLineViceAudit" ${wsStatus == "provinceLineViceAudit" ? 'selected="selected"':'' }>
									省线路总经理审核
								</option>
								<option value="provinceManageExamine" ${wsStatus == "provinceManageExamine" ? 'selected="selected"':'' }>
									省运维主管审核
								</option>
								<option value="usertask11" ${wsStatus == "usertask11" ? 'selected="selected"':'' }>
									专家会审
								</option>
								<option value="provinceManageViceAudit" ${wsStatus == "provinceManageViceAudit" ? 'selected="selected"':'' }>
									省运维总经理审批
								</option>
								<option value="sendOrder" ${wsStatus == "sendOrder" ? 'selected="selected"':'' }>
									工单派发
								</option>
								<option value="worker" ${wsStatus == "worker" ? 'selected="selected"':'' }>
									工单处理
								</option>
								<option value="daiweiAudit" ${wsStatus == "daiweiAudit" ? 'selected="selected"':'' }>
									派单审核
								</option>
							 	<option value="orderAudit" ${wsStatus == "orderAudit" ? 'selected="selected"':'' }>
									归档/抽查
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
		</tr>
					<tr>
				
			<td class="label">
				应急/常规
			</td>
			 <td class="class="text"">
				<eoms:comboBox name="precheckFlag" id="precheckFlag"
					defaultValue="${precheckFlag}" initDicId="1012314"
					alt="allowBlank:false" styleClass="select" />
			 </td>
				
			<td class="label">
				紧急程度
			</td>
			
				<td class="class="text"">
					<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
						defaultValue="${workOrderDegree}" initDicId="1012309"
						alt="allowBlank:false" styleClass="select" />
				 </td>
			
			<td class="label">
				关键字
			</td>
			 <td class="class="text"">
				<eoms:comboBox name="keyWord" id="keyWord"
					defaultValue="${keyWord}" initDicId="1012308"
					alt="allowBlank:false" styleClass="select" />
			 </td>
		</tr>		
				
			<tr>
				<td colspan="7" class="content">
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
			export="false" requestURI="pnrTransferNewPrecheck.do" sort="list"
			size="total" partialList="true">
		
	
			
			<display:column sortable="true" headerClass="sortable" title="工单号">
				
				<a href="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
					${taskList.processInstanceId}
					</a>
				
			</display:column>
			
			<%--<display:column property="sheetId" sortable="true" headerClass="sortable" title="工单编码" />--%>
			<display:column property="theme" sortable="false"
				headerClass="sortable" title="工单名称" maxLength="10" />
				
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="类别">
				<eoms:id2nameDB id="${taskList.precheckFlag}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<%--
			<display:column sortable="false" headerClass="sortable" title="线路级别">
				<eoms:id2nameDB id="${taskList.workType}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			--%>
			<display:column sortable="false" headerClass="sortable" title="主场景" property="workorderTypeName" maxLength="5"/>
			<display:column sortable="false" headerClass="sortable" title="子场景" property="subTypeName" maxLength="5"/>
			<display:column sortable="false" headerClass="sortable" title="项目详情" media="html">
				<a href="javascript:void(0);" onclick="checkChildSceneForDetails(&quot;${taskList.processInstanceId}&quot;)">查看</a>
			</display:column>

			<display:column sortable="false" headerClass="sortable" title="事前照片">
							<a href="javascript:void(0);" onclick="selectPhoto(&quot;${taskList.processInstanceId}&quot;)">查看</a>
			
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="工费" >
				<fmt:formatNumber value="${taskList.totalFee}" pattern="##.##" maxFractionDigits="0"  />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="材料费" >
				<fmt:formatNumber value="${taskList.totalMaterialCost}" pattern="##.##" maxFractionDigits="0"  />
			</display:column>
			<display:column sortable="true"  headerClass="sortable" title="项目预算">
				<fmt:formatNumber value="${taskList.projectAmount}" pattern="##.##" maxFractionDigits="0"  />
			</display:column>
			<display:column sortable="true"  headerClass="sortable" title="施工队回单项目预算">
				<fmt:formatNumber value="${taskList.workerProjectAmount}" pattern="##.##" maxFractionDigits='2' minFractionDigits='0'  />
			</display:column>
			<display:column sortable="true"  headerClass="sortable" title="抽查项目预算">
				<fmt:formatNumber value="${taskList.orderauditProjectAmount}" pattern="##.##" maxFractionDigits='2' minFractionDigits='0'  />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="实物赔补">
				<fmt:formatNumber value="${taskList.kindRestitution}" pattern="##.##" maxFractionDigits='2' minFractionDigits='0'  />
			</display:column>
			<display:column sortable="true" property="compensate" headerClass="sortable" title="现金赔补"/>
			<%--
			<display:column sortable="true" headerClass="sortable" title="收支比"><fmt:formatNumber value="${taskList.projectAmount !=0 ?taskList.compensate/taskList.projectAmount :0}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" ></fmt:formatNumber></display:column>
			--%>
			<display:column sortable="false" headerClass="sortable" title="关键字">
				<eoms:id2nameDB id="${taskList.keyWord}" beanId="ItawSystemDictTypeDao" />
			</display:column>
				<display:column sortable="true" headerClass="sortable" title="紧急程度">
				<eoms:id2nameDB id="${taskList.workOrderDegree}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="false" headerClass="sortable" maxLength="5" title="新建">${taskList.createStr}</display:column>
			<display:column sortable="false" headerClass="sortable" maxLength="5" title="拆除">${taskList.deleteStr}</display:column>
			<display:column property="distributedInterfaceTime" sortable="true"	headerClass="sortable" title="省公司批复时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<%--<display:column property="applicationTime" sortable="true"	headerClass="sortable" title="申请提交时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />--%>
			<%--<display:column sortable="false" headerClass="sortable" title="工单状态">
				${taskList.statusName}
			</display:column>--%>
			
			<%--<display:column sortable="false"  headerClass="sortable" title="敷设光缆">
			<c:if test="${taskList.layingCable!=null&&taskList.layingCable!=''}">
						<fmt:formatNumber value="${taskList.layingCable}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;皮长公里
					</c:if>
			</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="开挖揽沟">
				<c:if test="${taskList.excavationTrench!=null&&taskList.excavationTrench!=''}">
						<fmt:formatNumber value="${taskList.excavationTrench}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;公里
					</c:if>
			</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="整修管道">
				<c:if test="${taskList.repairPipeline!=null&&taskList.repairPipeline!=''}">
						<fmt:formatNumber value="${taskList.repairPipeline}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;孔公里
					</c:if>
			</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="扶正（拆除）电杆">
				<c:if test="${taskList.rightingDemolitionPole!=null&&taskList.rightingDemolitionPole!=''}">
						<fmt:formatNumber value="${taskList.rightingDemolitionPole}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />
					</c:if>
			</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="敷设钢绞线">
				<c:if test="${taskList.wireLaying!=null&&taskList.wireLaying!=''}">
								<fmt:formatNumber value="${taskList.wireLaying}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;公里
						</c:if>
			</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="光缆接头">
				<c:if test="${taskList.fiberOpticCableConnector!=null&&taskList.fiberOpticCableConnector!=''}">
								<fmt:formatNumber value="${taskList.fiberOpticCableConnector}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" />&nbsp;个
						</c:if>
			</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="其它">
						${taskList.mainQuantityOther}
			</display:column>--%>
			
			
			<%--<display:column sortable="false"  headerClass="sortable" title="建设原因及必要性">${taskList.constructionReasons}</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="网络现状描述">${taskList.networkStatus}</display:column>--%>
			<%--<display:column sortable="false"  headerClass="sortable" title="主要建设内容及规模">${taskList.constructionMainclass="text"}</display:column>--%>
			
			
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
<c:if test="${!empty taskList}">
	<eoms:excelExportToProcess modelName="com.boco.activiti.partner.process.po.TaskModel" serviceBeanId="pnrTransferNewPrecheckService" title="本地网预检预修工单查询" queryFlag="localNetworkWorkOrder" processKey="transferNewPrechechProcess" flag="">		
			<eoms:rowToProcess name="工单号" value="processInstanceId"/>
			<eoms:rowToProcess name="工单名称" value="theme"/>
			<eoms:rowToProcess name="地市" value="city" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="区县" value="country" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="类别" value="precheckFlag" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="主场景" value="workorderTypeName" />
			<eoms:rowToProcess name="子场景" value="subTypeName" />
			<eoms:rowToProcess name="工费" value="totalFee" />
			<eoms:rowToProcess name="材料费" value="totalMaterialCost"/>
			<eoms:rowToProcess name="项目预算" value="projectAmount" />
			<eoms:rowToProcess name="施工队回单-项目预算" value="workerProjectAmount" />
			<eoms:rowToProcess name="抽查-项目预算" value="orderauditProjectAmount" />
			<eoms:rowToProcess name="实物赔补" value="kindRestitution" />
			<eoms:rowToProcess name="现金赔补" value="compensate" />
			<eoms:rowToProcess name="关键字" value="keyWord" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="紧急程度" value="workOrderDegree" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="新建" value="createStr" />
			<eoms:rowToProcess name="拆除" value="deleteStr" />
			<eoms:rowToProcess name="省公司批复时间" value="distributedInterfaceTime" />
	</eoms:excelExportToProcess>
</c:if>	

		

		<%@ include file="/common/footer_eoms.jsp"%>