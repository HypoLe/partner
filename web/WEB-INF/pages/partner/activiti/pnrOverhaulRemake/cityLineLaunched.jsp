<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突

Date.prototype.format =function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
                RegExp.$1.length==1? o[k] :
                        ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
var roleTree;
var v;

  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   
   });

   function changeType1(){
       var myDate=new Date();
       var b =$('sheetCompleteLimit').value*60;
       myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
       $('dueDate').value = myDate.format('yyyy-MM-dd hh:mm:ss');
   }
</script>
<script type="text/javascript">
	//派发
	function distributeOrder(){		
		window.location.href="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=performAdd&initiator=${pnrTransferOffice.initiator}&taskAssignee=${pnrTransferOffice.taskAssignee}&processInstanceId=${pnrTransferOffice.processInstanceId}&processType=${pnrTransferOffice.themeInterface}";
	}
	
	//查看详情
	function queryDetail(){
		var url = "http://www.baidu.com";//请求连接未知
		var _sHeight = 460;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	    window.showModalDialog(url,window,sFeatures);
	}
	
	//修改
	jq(function(){
		jq("#updateButton").click(function(){
			var urlStr = "${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=updateProjectAmount";
			jq("#distributeButton").hide();
			var jq_processInstanceId=jq("#processInstanceId").val();
			var jq_theme=jq("#theme").val();
			var jq_themeId=jq("#themeId").val();
			var jq_processType=jq("#processType").val();
			jq.ajax({
				type : "POST",
				url : urlStr, 
				data : {"processInstanceId":jq_processInstanceId,"theme":jq_theme,"themeId":jq_themeId,"processType":jq_processType},
				success : function(msg){ // 回调函数
					if(msg=="success"){
						alert("调用接口成功！");
						jq("#updateButton").hide();
					}else{
						alert(msg);
					}		
			
				}	
			});
		});
	});
	
</script>



<html:form action="/pnrOverhaulRemake.do?method=performSave" styleId="theform" >
	<!-- 保存操作标识 -->
	<input type="hidden" id="viewFlag" name="viewFlag" value="updateJob" />
	
	<input id="dueDate" type="hidden" name="dueDate" value="${pnrTransferOffice.endTime}">
    <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTransferOffice.state}">
    
    <input id="themeInterface" type="hidden" name="themeInterface" value="interface">
	
<table class="formTable">
	<tr>
	  <td class="label">工单名称*</td>
	  <td colspan="3">
		<input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}" />
			${pnrTransferOffice.theme}
		<!-- <input type="text" name="title" class="text max" value="" disabled="disabled" />  -->
	  </td>
	</tr>

	<tr>
	  <td class="label">操作人*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
	  <input type="hidden" name="initiator" value="${userId}" />
	  </td>
	  
	  <td class="label">操作人部门*</td>
	  <td class="content"><eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao"/></td>
	</tr>

</table>
		
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>
		<td class="label">
				工单类型*
			</td>
			<td class="content">
					<input type="text" class="text max" name="provName" id="provName"
						value="${pnrTransferOffice.workOrderTypeName}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="workOrderType" id="workOrderType" value="${pnrTransferOffice.workOrderType}" type="hidden" />
					<eoms:xbox id="provTree2"
						dataUrl="${app}/xtree.do?method=dictXbox&level=3"
						rootId="1012307" rootText="工单类型" valueField="workOrderType" handler="provName"
						textField="provName" checktype="dict" />
				</td>
			
			
			
			
			<td class="label">
				工单子类型*
			</td>
			<td class="content">
					<input type="text" class="text max" name="subTypeName" id="subTypeName"
						value="${pnrTransferOffice.subTypeName}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="subType" id="subType" value="${pnrTransferOffice.subType}" type="hidden" />
					<eoms:xbox id="provTree"
						dataUrl="${app}/xtree.do?method=dictXbox&level=4"
						rootId="1012307" rootText="工单子类型" valueField="subType" handler="subTypeName"
						textField="subTypeName" checktype="dict" />
				</td>
			
		</tr>
		<tr>
			<td class="label">
				线路属性*
			</td>
			<td class="content">
			<eoms:comboBox name="workType" id="workType"
					defaultValue="${pnrTransferOffice.workType}" initDicId="1012310"
					alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				预检预修申请提交时间*
			</td>
			<td class="content">
				<input type="text" class="text" name="mainFaultOccurTime"
					readonly="readonly" id="mainFaultOccurTime"
					value="${eoms:date2String(pnrTransferOffice.submitApplicationTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
					alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				项目金额估算*
			</td>
			 <td class="content"> 
			    <input type="hidden" id="projectAmount" name="projectAmount" value="${pnrTransferOffice.projectAmount}"  />
			    <input type="text" class="text" value="${pnrTransferOffice.projectAmount}" disabled="disabled" />(单位:元) 
			    <c:if test="${pnrTransferOffice.state eq '9'}">
			    	<br /><br />
					<input type="button" class="btn" id="detailButton" name="detailButton" value="查看详情" onclick="queryDetail();" />
					<input type="button" class="btn" id="updateButton" name="updateButton" value="修改"  />
			    </c:if>
			    
		     </td>
			<td class="label">
				预检预修描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" id="mainRemark"
					alt="allowBlank:false,maxLength:2000,vtext:'请填入预检预修描述，最多输入 2000 字符'">${pnrTransferOffice.faultDescription}</textarea>
			</td>
		</tr>
		<tr>
			
			<td class="label">
				关键字*
			</td>
			<td class="content">
				<eoms:comboBox name="keyWord" id="keyWord"
					defaultValue="${pnrTransferOffice.keyWord}" initDicId="1012308"
					alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				紧急程度*
			</td>
			<td class="content">
			<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
					defaultValue="${pnrTransferOffice.workOrderDegree}" initDicId="1012309"
					alt="allowBlank:false" styleClass="select" />
			</td>
			
		</tr>
		
		<tr>
			
			<td class="label">
				流程类型*
			</td>
			<td class="content">
				<select id="processType" name="processType" class="select">
					<option value="">请选择</option>
					<option value="overhaul" ${pnrTransferOffice.themeInterface eq 'overhaul' ? 'selected="selected"':'' }>大修</option>
					<option value="reform" ${pnrTransferOffice.themeInterface eq 'reform' ? 'selected="selected"':'' } >改造</option>
				<!-- 	<option value="interface" ${pnrTransferOffice.themeInterface eq 'interface' ? 'selected="selected"':'' } >改造1111</option> -->
				</select>							
			</td>
			<td class="label">
					人员*
				</td>
				<td class="content">
					<input type="text" class="text" name="personnelName" id="personnelName"
							value="<eoms:id2nameDB id='${pnrTransferOffice.countryCSJ}' beanId='tawSystemUserDao'/>"
							alt="allowBlank:false" readonly="readonly" />
						<input name="personnelId" id="personnelId" value="${pnrTransferOffice.countryCSJ}" type="hidden" />
						<eoms:xbox id="personnelTree"
							dataUrl="${app}/xtree.do?method=userFromDeptTask&dh=${country}&level=1"
							rootId="${country}" rootText="部门与人员" valueField="personnelId" handler="personnelName"
							textField="personnelName" checktype="user" single="true"/>
				</td>			
		</tr>  
		
</table>
<!-- 附件-->
<table id="sheet" class="formTable">
	  <tr>
		    <td class="label">
		    	附件
			</td>	
			<td colspan="3">
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
	   </tr>			  
</table>

<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				保存
			</html:submit>
			<c:if test="${pnrTransferOffice.state eq '9'}">
				<input type="button" class="btn" id="distributeButton" name="distributeButton" value="派发" onclick="distributeOrder();">
			</c:if>
		</div>
	</html:form>
<%@ include file="/common/footer_eoms.jsp"%>