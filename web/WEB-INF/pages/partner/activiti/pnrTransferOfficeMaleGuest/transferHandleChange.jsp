<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/sceneCssUtil.jsp"%><!-- 引入场景模板公用css代码  sceneCssUtil.jsp -->

<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
var roleTree;
var v;
  Ext.onReady(function()
  {
   v = new eoms.form.Validation({form:'theform'});
  }
   );

  //提交时执行的方法
  function changeType1()
  {
	jq("#sceneDiv input").not("input[name$='-num']").not("input[name$='-price']").attr('disabled',false); //取消禁用场景模板中除了数量和单价的文本框
	jq("#sceneDiv select").attr('disabled',false); //取消禁用场景模板中所有的下拉选
  }
  
//jq("input[name=1-check]").bind("click", function () {
			//alert("33333");		
		//});


</script>

<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/transfer_basis.jsp"%>

<html:form action="/pnrTransferOfficeMaleGuest.do?method=transferHandleChange" styleId="theform" >
	
            <input type="hidden" name="taskId" value="${taskId}">
            <input type="hidden" name="processInstanceId" value="${processInstanceId}">
            
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" name="title" value="${pnrTransferOffice.theme}">
            <input type="hidden" name="titleId" value="${pnrTransferOffice.id}">
            <input type="hidden" name="handleState" value="handle">
            <!--下一步接收人-->
            <input type="hidden" name="acheckAssignee" value="${acheckAssignee}">
            <input type="hidden" name="auditor" value="${auditor}">
            <!--公客接口时使用-->
            <input type="hidden" name="audit" value="${auditor}">
            
            <input type="hidden" id="queryFlag" name="queryFlag" value="${queryFlag}" />

<!-- 审批结果 -->
    <fieldset style="border:1px solid red;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			是否补录施工照片
		</legend>
		<!-- 是否补录施工队照片 -->
		<label><input name="makeupFlag" type="radio" value="0" checked />不补录</label>
		<label><input name="makeupFlag" type="radio" value="1" />补录</label>
	</fieldset>
<br />
<!-- 原始场景模板DOM结构备份
<div id="sceneDiv" >
	<table id="sceneConTable" class="formTable">
			<tr>
				<td class="label">
					主场景
				</td>
				<td>
					<div id="mainSceneCon"></div>
				</td>
			</tr>
			<tr>
				<td class="label">
					子场景
				</td>
				<td>
					<div id="childSceneCon"></div>
				</td>
			</tr>
		</table>
		<!-- 子场景展示区 
		<div id="childSceneShow">
		</div>
		
		<table id="totalAmountTable" class="formTable">
			<tr> 
				<td class="label">
					总金额
				</td>
				<td>
					<span id="totalAmountSpan">0</span>元
					<input type="hidden" id="totalAmount" name="totalAmount" value="0" />
				</td>
			</tr>
		</table>
</div>  -->
 
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/sceneDivUtil.jsp"%><!-- 引入场景模板公用div代码  sceneDivUtil.jsp -->

<table id="sheet" class="formTable" >
				
 	<!-- 	<tr>
 				<td class="label">
 					故障原因*
 				</td>
				<td class="content">
						<select name="faultCause"  id="faultCause">
						<option value="2043">主干电缆障碍-宽带</option>
						<option value="2042">交接分线设备障碍-宽带</option>
						<option value="2044">光缆故障-宽带</option>
						<option value="2048">电缆被盗-宽带</option>
						<option value="2046">线路割接影响-宽带</option>
						<option value="2041">配线电缆故障-宽带</option>
						<option value="2111">主干、配线铜线距离超长-宽带</option>
						<option value="507">分光器-宽带</option>
						<option value="509">光电缆-宽带</option>
						<option value="391">主干故障-固话</option>
						<option value="392">配线故障-固话</option>
						<option value="743">电缆被盗-固话</option>
						<option value="9">光电缆-固话</option>
						<option value="7">分光器-固话</option>
						<option value="2">非本区县传输局故障</option>
						<option value="3">非光电缆原因故障</option>
						<option value="87">其他</option>
						</select>
				</td>
		</tr>	 -->	
		<!-- <tr>
 			<td class="label">
				故障原因*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="faultHandle" 
					id="faultHandle" alt="allowBlank:false,maxLength:200,vtext:'请填入故障原因，最多输入 200字符'"></textarea>
			</td>
		</tr> -->
		<!-- <tr>
 			<td class="label">
				处理描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入处理描述，最多输入 2000 字符'"></textarea>
			</td>
		</tr> -->
		
		<tr>
 			<td class="label">
				故障类型*
			</td>
			<td class="content" colspan="3">
				<select id="faultType" name="faultType">
					<option value="10123190101">故障工单</option>
					<option value="10123190102">非故障工单</option>
				</select>
			</td>
		</tr>
		<tr>
 			<td class="label">
				故障原因*
			</td>
			<td class="content" colspan="3">
				<select id="troubleReason1" name="troubleReason1">
					<option value="10123190201">车挂</option>
					<option value="10123190202">外力施工</option>
					<option value="10123190203">火烧</option>
					<option value="10123190204">被盗或人为破坏</option>
					<option value="10123190205">自然断纤</option>
					<option value="10123190206">接头盒进水</option>
					<option value="10123190207">尾纤故障</option>
					<option value="10123190208">鸟啄</option>
					<option value="10123190209">鼠咬</option>
					<option value="10123190210">自然灾害</option>
					<option value="10123190211">其他</option>
				</select>
				
				<select id="troubleReason2" name="troubleReason2" style="display:none">
					<option value="10123190212">更换电杆</option>
					<option value="10123190213">更换吊线</option>
					<option value="10123190214">更换拉线</option>
					<option value="10123190215">光缆脱落整治</option>
					<option value="10123190216">电力线防护</option>
					<option value="10123190217">光交箱整治</option>
					<option value="10123190218">人井盖增补</option>
					<option value="10123190219">其他安全隐患处理</option>
				</select>
			</td>
		</tr> 
		<tr>
		
		 <td class="label">
				故障处理人+手机号*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:false,maxLength:500,vtext:'请填入处理人'"></textarea>
	     </td>
		</tr>
</table>

	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="return changeType1();" styleId="method.save">
				回复
			</html:submit>&nbsp;&nbsp;
			
			<html:button property="" styleClass="btn" onclick="selectRes()">驳回</html:button>&nbsp;&nbsp;
			
			<html:button property="" styleClass="btn" onclick="javascript:history.go(-1);">
				返回
			</html:button>
		</div>
	</html:form>
	
<c:if test="${auditorSceneExistFlag ne 'Y'}"><!-- 进入施工队环节，没有施工队场景模板数据，需要单独的加载主场景信息 -->
	<script>
		 jq(function(){
		 		//动态加载主场景的多选框
				var mainSceneUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=getMainScene";
				jq.getJSON(mainSceneUrl,function (data) {
		                jq(data).each(function () {
		                	jq("#mainSceneCon").append("<input name='mainScene' type='checkbox' value='"+this.id+"' /><span>"+this.name+"</span>");
		                });
		        });
		});
	</script>
</c:if>

<c:if test="${auditorSceneExistFlag eq 'Y'}"><!-- 进入施工队环节，存在施工队场景模板数据，代表是驳回到施工队。驳回施工队的场景模板只允许修改数量和单价，其他的不允许修改。 -->
	<script>
		jq(function(){
		 	jq("#sceneDiv input").not("input[name$='-num']").not("input[name$='-price']").attr('disabled',"true"); //禁用场景模板中除了数量和单价的文本框
		 	jq("#sceneDiv select").attr('disabled',"true"); //禁用场景模板中所有的下拉选
			jq(".photolayer").css('display','none'); //隐藏删除按钮
		});
	</script>
</c:if>

<script type="text/javascript">
function selectRes(){
	var queryFlag = jq("#queryFlag").val();
	var url = '${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=showRejectPage&queryFlag='+queryFlag;
	//var _sHeight = 180;
	var _sHeight = 200;
    var _sWidth = 820;
    var sTop=(window.screen.availHeight-_sHeight)/2;
    var sLeft=(window.screen.availWidth-_sWidth)/2;
	var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	window.showModalDialog(url,window,sFeatures);
}

//此刷新函数被弹出的子模态窗口调用
function refresh(queryFlag){
	if(queryFlag == 'single'){
		window.location.href = "${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=querySingleImputationList";
	}else if(queryFlag == 'noSingle'){
		window.location.href = "${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=queryNoSingleImputationList";
	}
} 

</script>


<!-- 引入场景模板公用js代码  sceneJsUtil.jsp -->	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/sceneJsUtil.jsp"%>

<script type="text/javascript">
jq(document).delegate("#faultType", "change", function(){
	var faultTypeVal = jq(this).val();
	if(faultTypeVal == '10123190101'){ //故障工单
		jq("#troubleReason1").css("display","block");
		jq("#troubleReason2").css("display","none");
	}else if(faultTypeVal == '10123190102'){ //故障原因
		jq("#troubleReason1").css("display","none");
		jq("#troubleReason2").css("display","block");
	}
});	
</script>
<%@ include file="/common/footer_eoms.jsp"%>