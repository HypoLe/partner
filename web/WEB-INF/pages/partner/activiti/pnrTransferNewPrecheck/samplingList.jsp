<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<base target="_self" />
		<%@ include file="/common/header_eoms_form.jsp"%>
		<script language="javaScript" type="text/javascript"
			src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script type="text/javascript"
			src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
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
			document.getElementById("region").value="";	   //地市     
			document.getElementById("country").value=""; //区县 
			document.getElementById("wsNum").value=""; //工单号
			document.getElementById("wsTitle").value=""; //工单主题
			document.getElementById("samplState").value=""; //状态
			document.getElementById("processType").value=""; //流程类型
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
	//归档
	//取消审批的勾选
	function removeApproveSelect(){
		var tit = document.getElementById("approveAll");
		tit.checked = false;
		var inputs=document.getElementsByName("approveBox");
		for(var i = 0; i < inputs.length; i++) {
			inputs[i].checked = false; 
		}
	} 
	function selectArchiveAll(obj) {
		//取消审批的勾选
		removeApproveSelect();
	
        var temp = document.getElementsByName("Fruit"); 
        for (var i =0; i<temp.length; i++) 
        { 
            temp[i].checked = obj.checked; 
        } 
    } 
    
    function cancelArchive(obj,all) {
    	//取消审批的勾选
		removeApproveSelect();
     
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

	//点击批量归档按钮事件
	function batchReply(){
			var inputs =document.getElementsByName("Fruit");
			var total=0;
			for(var i=0;i<inputs.length;i++){
				if(inputs[i].checked){
					total++;
				}
			}
			
			if(total==0){
				alert("请选择要批量归档的工单！");
			}else{
		       	document.getElementById("testform").submit();
			}
		}



</script>


		<script type="text/javascript">
	//审批
	//取消归档的勾选
	function removeArchiveSelect(){
		var tit = document.getElementById("archiveAll");
		tit.checked = false;
		var inputs=document.getElementsByName("Fruit");
		for(var i = 0; i < inputs.length; i++) {
			inputs[i].checked = false; 
		}
	}
	
	function selectApproveAll(obj) { 
		//取消归档的勾选
		removeArchiveSelect();
	
        var temp = document.getElementsByName("approveBox"); 
        for (var i =0; i<temp.length; i++) 
        { 
            temp[i].checked = obj.checked; 
        } 
    } 
    
    function cancelApprove(obj,all) { 
   		//取消归档的勾选
		removeArchiveSelect();
    
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
				var inputs =document.getElementsByName("approveBox");
				var total=0;
				for(var i=0;i<inputs.length;i++){
					if(inputs[i].checked){
						total++;
					}
				}
				
				if(total==0){
					alert("请选择要批量审批的工单！");
					return false;
				}else{
					//alert("total="+total);
			       //ocument.getElementById("testform").submit();
			       //打开子窗口
				    var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openBatchApproveView';
					var _sHeight = 260;
				    var _sWidth = 820;
				    var sTop=(window.screen.availHeight-_sHeight)/2;
				    var sLeft=(window.screen.availWidth-_sWidth)/2;
					var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
				    window.showModalDialog(url,window,sFeatures);     		       
				}
			}
 
   //此刷新函数被弹出的子模态窗口调用
   function refresh(){
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=listBacklog";
	} 



</script>
		<div id="sheetform">
			<html:form
				action="/pnrTransferNewPrecheck.do?method=querySamplingList"
				styleId="theform">
				<table class="formTable" style="width: 100%">
					<!--时间 -->
					<tr>
						<td class="label" style="width: 10%">
							派单开始时间<font color="red">*</font>
						</td>
						<td class="content" style="width: 20%">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label" style="width: 10%">
							派单结束时间<font color="red">*</font>
						</td>
						<td class="content" style="width: 20%">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
						<td class="label" style="width: 10%">
							地市
						</td>
						<td class="content" style="width: 20%">
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
						<td class="label" style="width: 10%">
							区县
						</td>
						<td class="content" style="width: 20%">
							<select name="country" id="country" class="select" alt="allowBlank:false,vtext:'请选择所在县区'">
								<option value="">
								--请选择所在县区--
								</option>				
						   </select>
						</td>
						<td class="label" style="width: 10%">
							工单号
						</td>
						<td class="content" style="width: 20%">
							<input type="text" name="wsNum" class="text" id="wsNum" value="${wsNum}" />
						</td>
						<td class="label" style="width: 10%">
							工单主题
						</td>
						<td class="content" style="width: 20%">
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
					</tr>
					
					<tr>
						<td class="label" style="width: 10%">
							状态
						</td>
						<td class="content" style="width: 20%">
							<select id="samplState" name="samplState" class="select">
								<option value="">--请选择状态--</option>
								<option value="0">未处理</option>
								<option value="1">已标记</option>
								<option value="2">已抽检</option>
							</select>
						</td>
						<td class="label" style="width: 10%">
							流程类型
						</td>
						<td class="content" style="width: 20%">
							<select id="processType" name="processType" class="select">
								<option value="">--请选择流程类型--</option>
								<option value="interface">本地网</option>
								<option value="arteryPrecheck">干线</option>
							</select>
						</td>
					</tr>
				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save" styleId="method.save" onclick="return changeType1();">
                查询
                
                
            </html:submit>
			<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
			 <c:if test="${queryFlay ne '1'}">
				<span><font color="red">请选择查询条件，查询相对应的数据!</font></span>
			</c:if>
			</div>
			</html:form>
		</div>

		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="false" requestURI="pnrTransferNewPrecheck.do" sort="list"
			size="total" partialList="true">
			<display:column title="工单号">
				<c:if test="${taskList.themeInterface eq 'interface'}">
					<a href="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}&samplingFlag=1&condition=${condition}">${taskList.processInstanceId}</a>
				</c:if>
				<c:if test="${taskList.themeInterface eq 'arteryPrecheck'}">
					<a href="${app}/activiti/pnrTransferPrecheck/pnrTransferArteryPrecheck.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}&samplingFlag=1&condition=${condition}">${taskList.processInstanceId}</a>
				</c:if>
			</display:column>
			<display:column property="themeInterfaceName" title="流程类型"
				maxLength="15" />
			<display:column property="theme" title="工单名称" maxLength="15" />
			<display:column title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column title="关键字">
				<eoms:id2nameDB id="${taskList.keyWord}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column property="applicationTime" title="申请提交时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="紧急程度">
				<eoms:id2nameDB id="${taskList.workOrderDegree}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column property="projectAmount" title="项目金额" />
			<display:column title="状态">
				<span name="stateSpan"> <c:if
						test="${taskList.samplingState eq '0'}">
						未处理
					</c:if> <c:if test="${taskList.samplingState eq '1'}">
						已标记
					</c:if> <c:if test="${taskList.samplingState eq '2'}">
						已抽检
					</c:if> </span>
			</display:column>
			<display:column title="标记" style="text-align:center;">
				<c:if test="${taskList.samplingState eq '0'||taskList.samplingState eq '1'}">
					<input type="button" name="marked" value="标记" onclick="markSampling(this,&quot;${taskList.processInstanceId}&quot;,'marked','标记')" ${taskList.samplingState eq "0"? '':'style="display:none;"'}/>
					<input type="button" name="cancelled" value="取消" onclick="markSampling(this,&quot;${taskList.processInstanceId}&quot;,'cancelled','取消')" ${taskList.samplingState eq "1"? '':'style="display:none;"'}/>
				</c:if>
				<c:if test="${taskList.samplingState eq '2'}">
						-
				</c:if>
			</display:column>

			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
<script type="text/javascript">
jq(function(){
	jq("#colhide-tip").css("display","none");
	var samplState="${samplState}";
	var processType="${processType}";
	if(samplState != null && samplState != ''){
		jq("#samplState option[value='"+samplState+"']").attr("selected","selected");	
	}
	if(processType !=null && processType != ''){
		jq("#processType option[value='"+processType+"']").attr("selected","selected");
	}
});


	function markSampling(obj,processInstanceId,flag,flagName){
		if(confirm("确定要"+flagName+"吗?"))
        {
	   		var buttonObject = jq(obj); //按钮对象
			var buttonTdObj = buttonObject.parent("td"); //按钮所在列对象
			var stateObject = buttonObject.parents("tr").find("span[name=stateSpan]"); //状态对象
			var markedObject;
			var cancelledObject;
			if(flag == "marked"){
				cancelledObject = buttonObject.parents("tr").find("input[name=cancelled]"); //取消按钮
			}else if(flag == "cancelled"){
				markedObject = buttonObject.parents("tr").find("input[name=marked]"); //确定按钮
			}
			
    		var propertyUrl="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=markSampling&processInstanceId="+processInstanceId+"&flag="+flag;
			jq.ajax({
				type: "post",
		 		url: propertyUrl,
		 		async: false,//控制同步        
				contentType: "application/json; charset=utf-8",
		 		dataType: "json",
		 		cache: false,
		 		success: function (data) {
		 			if(data.result=='success'){
		 				buttonObject.hide(); //隐藏当前按钮
		 				if(flag == "marked"){
		 				    cancelledObject.show();
						    stateObject.text("已标记");
		 				}else if(flag == "cancelled"){
		 					markedObject.show();
							stateObject.text("未处理");
		 				}
		 			}else{
		 				alert(data.content);
		 			}   
		 		},
		 		error: function (err) {
		           alert(err);
		        }		
			});
    }
}

//提交时对查询条件进行校验
function changeType1(){
	//判断开始时间是否为空		
	var sheetAcceptLimit = jq("#sheetAcceptLimit").val();
	if(sheetAcceptLimit==""){
		alert("开始时间不能为空，请选择！");
		return false;
	}
	//判断结束时间是否为空
	var sheetCompleteLimit = jq("#sheetCompleteLimit").val();
	if(sheetCompleteLimit==""){
		alert("结束时间不能为空，请选择！");
			return false;
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>