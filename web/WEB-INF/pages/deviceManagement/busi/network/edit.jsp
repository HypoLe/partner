<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
       v = new eoms.form.Validation({form:'hiddenTroubleForm'});
       jq("#checkPoint option[value='${hiddenTrouble.checkPoint}']").attr("selected",true);
       
       jq("#longitude").bind("change",numberValueCheck);
        jq("#latitude").bind("change",numberValueCheck);
        
        function numberValueCheck(e) {
        	var value = e.target.value;
        	var regStr = "^(\\d{1,}|\\d{1,}.\\d{1,4})$";
        	var right = value.match(regStr);
        	if(right == null) {
        		alert("请输入正确的数据,支持精确小数点后四位！");
        		e.target.value = "";
        	} else {
        		e.target.value = Number(e.target.value).toFixed(4);
        	}
        }
});
 </script>
 	<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>
	<content tag="heading">
	<c:out value="网络设备隐患修改页面" />
	</content><br/><br/>
<form action="hiddenTrouble.do?method=edit" method="post"
	id="hiddenTroubleForm" name="hiddenTroubleForm">
<%--
	private String id; // 主键
	private String reportUser; //上报人，就是当前填写隐患信息的登录人员
	private String reportTime; //上报时间
	private String areaId;//地区
	private String hiddenTroubleType;//隐患类型
	private String isImportant;//是否重要隐患（0：否，1：是）
	private String majorType;//专业类型
	private String checkPoint;//巡检点
	private double longitude;//经度
	private double latitude;//纬度
	private String checkUser;//上报巡检员 pnr_user
	private String checkUserDept;//代维公司 （上报人所属代维公司）pnr_dept
	private String phone;//联系电话
	private String email;//email信息
	
	private String processStatus;//处理状态
	private String processUser;//处理人
	private String handlTime;//处理时间
	private String handleMsg;//处理信息
	
	private String deleted;//删除标记：0表示未删除，1表示逻辑删除
	private String deletedTime;//删除时间
	private String remark;//备注
--%>
	<!-- hidden area start -->
	<input type="hidden" name="id"                 value="${hiddenTrouble.id}" />               
	<input type="hidden" name="reportUser"         value="${hiddenTrouble.reportUser}" />       
	<input type="hidden" name="reportTime"         value="${hiddenTrouble.reportTime}" />       
	
	<input type="hidden" name="processStatus"      value="${hiddenTrouble.processStatus}" />    
	<input type="hidden" name="processUser"        value="${hiddenTrouble.processUser}" />      
	<input type="hidden" name="handlTime"          value="${hiddenTrouble.handlTime}" />        
	<input type="hidden" name="handleMsg"          value="${hiddenTrouble.handleMsg}" />        
	<input type="hidden" name="deleted"            value="${hiddenTrouble.deleted}" />          
	<input type="hidden" name="deletedTime"        value="${hiddenTrouble.deletedTime}" />      
	<!-- hidden area end -->
		<table id="sheet" class="formTable">
			<tr>
				<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
			</tr>
			
			<tr>
				<td class="label">
				 地区* 
				</td>
				<td class="content">
					<input class="text" name="textArea" id="textArea" type="text"  readonly="true"  alt="allowBlank:false" value="<eoms:id2nameDB id="${hiddenTrouble.areaId }"	beanId="tawSystemAreaDao" />"/> 
					<input type="button" name="areatree" id="areatree" value="选择地点" class="btn"  alt="allowBlank:false"/> 
					<input type="hidden" name="areaId" id="areaId" value="${hiddenTrouble.areaId }" />
					<eoms:xbox id="tree1" dataUrl="${app}/partner/baseinfo/xtree.do?method=area" 
		 				 rootId="-1" 
		  				 rootText='区域树' 
		  				 valueField="areaId"
		  				 handler="areatree"
		  				 textField="textArea"
		 				 checktype="area" 
		 				 single="true"></eoms:xbox>
					<script type="text/javascript">
					/*new xbox({
						btnId:'areatree',
						treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
						treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
						showChkFldId:'advertisementCity',saveChkFldId:'advertisementArea',returnJSON:false
					});*/
					</script>
				</td>
				<td class="label">
				巡检点*
				</td>
				<td class="content">
					<select name="checkPoint" id="checkPoint" alt="allowBlank:false" class="select">
							<option value="">请选择</option>
						<c:forEach items="${cpList}" var="cp">
							<option value="${cp.id }">${cp.resourceName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="label">
				 上报隐患类型* 
				</td>
				<td class="content">
					<eoms:comboBox name="hiddenTroubleType" defaultValue="${hiddenTrouble.hiddenTroubleType }"
						id="hiddenTroubleType" initDicId="1200201" alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label">
				是否重要隐患*
				</td>
				<td class="content">
					<eoms:comboBox name="isImportant" defaultValue="${hiddenTrouble.isImportant}"
						id="isImportant" initDicId="10301" alt="allowBlank:false" styleClass="select" />
				</td>
			</tr>
			
			<tr>
				<td class="label">
				 专业类型* 
				</td>
				<td class="content">
					<eoms:comboBox name="majorType" defaultValue="${hiddenTrouble.majorType }"
						id="majorType" initDicId="11216" alt="allowBlank:false" styleClass="select" />
				</td>
			</tr>
			
			<tr>
				<td colspan="4"><div class="ui-widget-header" >GPS信息</div></td>
			</tr>
			
			<tr>
				<td class="label">
				 经度* 
				</td>
				<td class="content">
					<input class="text" type="text" name="longitude" value="${hiddenTrouble.longitude }"
						id="longitude" alt="allowBlank:false" />
				</td>
				<td class="label">
					纬度*
				</td>
				<td class="content">
					<input class="text" type="text" name="latitude" value="${hiddenTrouble.latitude }"
						id="latitude" alt="allowBlank:false" />
				</td>
			</tr>
			
			<tr>
				<td colspan="4"><div class="ui-widget-header" >其它信息</div></td>
			</tr>
			
			<tr>
				<td class="label">
				 上报巡检员* 
				</td>
				<td class="content" colspan="3">
					<!-- input type="text" class="text" name="checkUser" id="checkUser" readonly value="${hiddenTrouble.checkUser }"/> -->
					<input type="button" name="userButton" id="userButton" value="请选择审核人" class="btn" alt="allowBlank:false"/>
					<input type="text" name="textReviewer" id="textReviewer" class="text" readonly="readonly" alt="allowBlank:false"
							value="<eoms:id2nameDB id="${hiddenTrouble.checkUser }"	beanId="partnerUserDao" />"/>
		  			<input type="hidden" name="checkUser" id="reviewer" value="${hiddenTrouble.checkUser }"/>
	 		
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" 
	 					rootId="-1" 
	  					rootText='用户树' 
	  				 	valueField="reviewer" handler="userButton"
	  				 	textField="textReviewer"
	 					checktype="user" single="true"></eoms:xbox>
				</td>
			</tr>
			
			<tr>
				<td class="label">
				 联系电话
				</td>
				<td class="content">
					<input class="text" type="text" name="phone" id="phone" value="${hiddenTrouble.phone }"
						alt="allowBlank:true,vtype:'number',vtext:'请输入电话号码'" />
				</td>
				<td class="label">
				Email信息
				</td>
				<td class="content">
					<input class="text" type="text" name="email" id="email" value="${hiddenTrouble.email }"
						alt="allowBlank:true,vtype:'email'" />
				</td>
			</tr>
			
			<tr>
				<td class="label">
					备注
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="remark" id="remark" alt="allowBlank:true">
						${hiddenTrouble.remark }
					</textarea>
				</td>
			</tr>
		</table>
		<br/>
		<input type="submit" class="btn"  value="保存修改" />
		<input type="reset" class="btn"  value="重置" />
</form>

<%@ include file="/common/footer_eoms.jsp"%>