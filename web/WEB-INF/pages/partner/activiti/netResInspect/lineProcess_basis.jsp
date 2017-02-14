<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

  Ext.onReady(function()
  {
   var tabConfig = {
		items : [{
			id : 'sheetinfo',
			text : '基本信息'
		}, {
			text : '流转信息',
			url : 'netResInspect.do?method=viewHistory&processInstanceId=${processInstanceId}',
			isIframe : true
		}, {
			text : '流程图',
			url : 'netResInspect.do?method=graphHistoryProcessInstance&processInstanceId=${processInstanceId}',
			isIframe : true
			}
		]
	};
   
	var tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
	
   }
   );
   /**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var pid='${netResInspect.processInstanceId}';
		var url = '${app}/activiti/netResInspect/netResInspect.do?method=showCreateWorkPhoto&pid='+pid;
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	function showMap(){
		var pid='${netResInspect.processInstanceId}';
		var url = '${app}/activiti/netResInspect/netResInspect.do?method=showMap&pid='+pid;
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>
<!-- Sheet Tabs Start -->
<div id="sheet-detail-page">
  <!-- Sheet Detail Tab Start -->
  <div id="sheetinfo">
  	<table class="formTable">
  			<caption>工单基本信息</caption>
  	</table>
  	<table id="sheet" class="formTable">
  		<tr>
			<td class="label" style="width:10%">
				工单号
			</td>
			<td class="content" style="width:20%">
				${netResInspect.processInstanceId}
			</td>
			<td class="label">
				工单名称
			</td>
			<td class="content" colspan="3">
				${netResInspect.theme}
			</td>
		</tr>
		<tr>
			<td class="label" style="width:10%">资源类型</td>
			  <td class="content"  style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.resourceType}" beanId="ItawSystemDictTypeDao" />
			  </td>
			  
			  <td class="label" style="width:10%">问题类型</td>
			  <td class="content" style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.questionType}" beanId="ItawSystemDictTypeDao" />
			  </td>
			  <td class="label" style="width:10%">上报日期</td>
			  <td class="content" style="width:20%">
			  	${eoms:date2String(netResInspect.reportedDate)}
			  </td>
		</tr>
		<tr>
			<td class="label" style="width:10%">发现人员</td>
			  <td class="content"  style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.reportedUserId}" beanId="tawSystemUserDao" />
			  </td>
			  
			  <td class="label" style="width:10%">手机号</td>
			  <td class="content" style="width:20%">
			  	${netResInspect.reportedPhoneNum}
			  </td>
			  <td class="label" style="width:10%">发现部门</td>
			  <td class="content" style="width:20%">
			  	<eoms:id2nameDB id="${netResInspect.reportedDeptId}" beanId="tawSystemDeptDao"/>
			  </td>
		</tr>
		<tr>
		  <td class="label" style="width:10%">资源地址</td>
		  <td colspan="5" style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.city}" beanId="tawSystemAreaDao" />市
		  	<eoms:id2nameDB id="${netResInspect.county}" beanId="tawSystemAreaDao" />
		  	${netResInspect.street}乡镇
		  </td>
		</tr>
		
		<tr>
		  <td class="label" style="width:10%">描述</td>
		  <td colspan="5" style="width:20%">
		  	${netResInspect.reportedDescribe}
		  </td>
		</tr>
	</table>
	<br/>
  	<table class="formTable">
  			<caption>现场核实信息</caption>
  		</table>
  		<table id="sheet" class="formTable">
   		<tr>
		  <td class="label" style="width:10%">资源名称*</td>
		  <td colspan="5" style="width:20%">
		    ${netResInspect.resourceName}
		  </td>
		</tr>
		<tr>
		  <td class="label" style="width:10%">是否我方资源*</td>
		  <td class="content"  style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.isOurResources}" beanId="ItawSystemDictTypeDao" />
		  </td>
		  <td class="label" style="width:10%">是否现场施工作业*</td>
		  <td class="content" style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.isSiteOperation}" beanId="ItawSystemDictTypeDao" />
		  </td>
		  <td class="label" style="width:10%">紧急程度*</td>
		  <td class="content" style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.degree}" beanId="ItawSystemDictTypeDao" />
		  </td>
		</tr>
		<tr>
		  <td class="label" style="width:10%">有效性*</td>
		  <td class="content"  style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.validity}" beanId="ItawSystemDictTypeDao" />
		  </td>
		  <td class="label" style="width:10%">重要程度*</td>
		  <td class="content" style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.importance}" beanId="ItawSystemDictTypeDao" />
		  </td>
		</tr>
		<tr>
			<td class="label" style="width:10%">
				照片清单
			</td>
			<td colspan="5" style="width:90%">
				<input type="button" class="btn" id="showPhotos" name="showPhotos" value="查看照片" onclick="selectPhoto()">
	  			<!-- <input type="button" class="btn" id="showMap" name="showMap" value="地图" onclick="showMap()"> -->
			</td>
		</tr>
		<tr>
		  <td class="label" style="width:10%">是否隐患</td>
		  <td class="content"  style="width:20%">
		  	<c:choose>
				<c:when test="${netResInspect.isHiddenDanger eq '1'}">
					是
				</c:when>
				<c:otherwise>
					否
				</c:otherwise> 
			</c:choose>
		  </td>
		  <td class="label" style="width:10%">专业</td>
		  <td class="content" style="width:20%">
		  	<eoms:id2nameDB id="${netResInspect.specialty}" beanId="ItawSystemDictTypeDao" />
		  </td>
		  <c:choose>
				<c:when test="${netResInspect.specialty eq '1280103'}">
					<td class="label" style="width:10%">是否建设</td>
					  <td class="content" style="width:20%">
					  	<c:choose>
							<c:when test="${netResInspect.isBuild eq '1'}">
								是
							</c:when>
							<c:otherwise>
								否
							</c:otherwise> 
						</c:choose>
					  </td>
				</c:when>
				<c:otherwise>
					<td class="label" style="width:10%">是否线路隐患</td>
					<td class="content" style="width:20%">
						<c:choose>
							<c:when test="${netResInspect.isLineHiddenDanger eq '1'}">
								是
							</c:when>
							<c:otherwise>
								否
							</c:otherwise> 
						</c:choose>
					</td>
				</c:otherwise> 
			</c:choose>
		  
		</tr>
		<c:choose>
			<c:when test="${netResInspect.specialty eq '1280102'}">
				<tr>
		 		  <td class="label" style="width:10%">派发流程</td>
				  <td class="content"  style="width:20%">
				  	<c:choose>
						<c:when test="${netResInspect.autoSendProcess eq '1'}">
							预检预修流程
						</c:when>
						<c:otherwise>
							线路抢修流程
						</c:otherwise> 
					</c:choose>
				  </td>
				  <td class="label" style="width:10%">关联转派流程工单号</td>
				  <td class="content"  style="width:20%">
				  	${netResInspect.subProcessInstanceId}
				  </td>
				</tr>
			</c:when>
		</c:choose>
		
  	</table>
  	<c:choose>
		<c:when test="${!empty netResInspect.isFinish}">
			<br/>
		  	<table class="formTable">
				<caption>工单处理信息</caption>
			</table>
			<table id="sheet" class="formTable">
				<tr>
				  <td class="label" style="width:3%">是否处理完成*</td>
				  <td class="content" style="width:20%">
				  	<c:choose>
						<c:when test="${netResInspect.isFinish eq '1'}">
							是
						</c:when>
						<c:otherwise>
							否
						</c:otherwise> 
					</c:choose>
				  </td>
				</tr>
				<tr>
				  <td class="label" style="width:3%">描述</td>
				  <td colspan="5" style="width:20%">
				  	${netResInspect.handleDescribe}
				  </td>
				</tr>
			</table>
		</c:when>
	</c:choose>
  	
  </div>
   
</div>
<!-- Sheet Tabs End -->