<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
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
function selectSheet(){
		var objName= document.getElementsByName("radio1");
		var string = '';
		 for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string = objName[i].value.trim();
                break;
                }
        } 
		window.returnValue=string;
		window.close();
	}
</script>
<script type="text/javascript">
	//重置
	function newReset(){
		document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
		document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
		document.getElementById("wsNum").value="";		// 工单号
		document.getElementById("wsTitle").value="";	        // 工单主题
		//document.getElementById("status").value="";	        // 工单状态
	}
	
	 //提交时对查询条件进行校验
	function changeType1(){
		var sheetAcceptLimit = document.getElementById("sheetAcceptLimit").value; 
		var sheetCompleteLimit = document.getElementById("sheetCompleteLimit").value;
		var wsNum = document.getElementById("wsNum").value;
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
			<html:form action="/pnrTransferOffice.do?method=listInvolvedFinishedProcessInstances"
				styleId="theform">
				<input type="hidden" id="queryAllowed" name="queryAllowed" value="Y" /><!-- 能查询标识 -->
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					<tr>
						<td class="label"  style="width:10%">
							开始时间
						</td>
						<td class="content"  style="width:23%">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label"  style="width:10%">
							结束时间
						</td>
						<td class="content"  style="width:23%">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1,0)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
						<!-- 工单号  -->
						<td class="label"  style="width:10%">
							工单号
						</td>
						<td  style="width:23%">
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${wsNum}" />
						</td>
					</tr>

					<tr>
						
						<!-- 工单主题 -->
						<td class="label"  style="width:10%">
							工单名称
						</td>
						<td colspan="5"  style="width:23%">
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
					</tr>
			<!--  	<tr>
						<td class="label">
							工单状态
						</td>
						<td colspan="3">
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="">
									所有
								</option>
								<option value="archived" ${wsStatus == "archived" ? 'selected="selected"':'' }>
									已归档
								</option>
								<option value="delete" ${wsStatus == "delete" ? 'selected="selected"':'' }>
									已删除
								</option>
							</select>
						</td>
					</tr> -->	
			
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
      

<bean:define id="url" value="pnrTransferOffice.do"/>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="false" requestURI="pnrTransferNewPrecheck.do"
		sort="list" size="total" partialList="true" >
   		
   		  <display:column sortable="true"
			headerClass="sortable" title="工单号">
			
				<c:choose>
			<c:when test="${taskList.statusName != '已删除'}">
			<a href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
			${taskList.processInstanceId}
			</a>
			</c:when>
			<c:otherwise>
				${taskList.processInstanceId}
			</c:otherwise>
			</c:choose>		
			</display:column>
		<display:column property="theme" sortable="false"
				headerClass="sortable" title="工单名称" maxLength="15" />				
		<display:column sortable="true"
			headerClass="sortable" title="工单生成人">
	    <eoms:id2nameDB id="${taskList.oneInitiator}" beanId="tawSystemUserDao"/>            
			
	    </display:column>
		<display:column sortable="true"
			headerClass="sortable" title="所属部门">
	    <eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>            
			
	    </display:column>


		<display:column property="sendTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column property="statusName" sortable="true"
            headerClass="sortable" title="当前状态"/>
        <display:column property="recentMainScenesName" sortable="true"headerClass="sortable" title="主场景"/>
        <display:column property="recentCopyScenesName" sortable="true"headerClass="sortable" title="子场景"/>
        <display:column sortable="true"headerClass="sortable" title="场景查看">
        	<a href="javascript:void(0);" onclick="viewUpdateSceneTemplate(this,'transferOffice',&quot;${taskList.processInstanceId}&quot;,'auditReport','yes','Y')">查看</a>
        </display:column>
        <display:column sortable="true"headerClass="sortable" title="照片">
        	<a href="javascript:void(0);" onclick="viewLiveCamera(&quot;${taskList.processInstanceId}&quot;)">查看</a>
        </display:column>

		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>
	</br>
	<c:if test="${carApprove eq 'yes'}">
		<input type="button" class="btn" value="确定" onclick="selectSheet();"/>
	</c:if>
	
<!-- 引入场景模板公用js代码  lookSceneJsUtil.jsp -->	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/lookSceneJsUtil.jsp"%>
<%@ include file="/common/footer_eoms.jsp"%>