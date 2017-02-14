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
});
 </script>
 	<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>
	<content tag="heading">
	<c:out value="网络设备隐患处理页面" />
	</content><br/><br/>
<form action="hiddenTrouble.do?method=troubleProcess" method="post"
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
        <input type="hidden" name="areaId"             value="${hiddenTrouble.areaId}" />                                                         
        <input type="hidden" name="hiddenTroubleType"  value="${hiddenTrouble.hiddenTroubleType}" />                                              
        <input type="hidden" name="isImportant"        value="${hiddenTrouble.isImportant}" />                                                    
        <input type="hidden" name="majorType"          value="${hiddenTrouble.majorType}" />                                                      
        <input type="hidden" name="checkPoint"         value="${hiddenTrouble.checkPoint}" />                                                     
        <input type="hidden" name="longitude"          value="${hiddenTrouble.longitude}" />                                                      
        <input type="hidden" name="latitude"           value="${hiddenTrouble.latitude}" />                                                       
        <input type="hidden" name="checkUser"          value="${hiddenTrouble.checkUser}" />                                                      
        <input type="hidden" name="checkUserDept"      value="${hiddenTrouble.checkUserDept}" />                                                  
        <input type="hidden" name="phone"              value="${hiddenTrouble.phone}" />                                                          
        <input type="hidden" name="email"              value="${hiddenTrouble.email}" />                                                          
        
        <input type="hidden" name="deleted"            value="${hiddenTrouble.deleted}" />                                                        
        <input type="hidden" name="deletedTime"        value="${hiddenTrouble.deletedTime}" />                                                    
        <input type="hidden" name="remark"             value="${hiddenTrouble.remark}" />   
	<!-- hidden area end -->
		<table id="sheet" class="formTable">
			<tr>
				<td colspan="4"><div class="ui-widget-header" >隐患处理</div></td>
			</tr>
			<tr>
				<td class="label">
					处理信息
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="handleMsg" id="remark" alt="allowBlank:false">
					</textarea>
				</td>
			</tr>
		</table>
		<br/>
		<input type="submit" class="btn"  value="处理" />
		<input type="reset" class="btn"  value="重置" />
</form>

<%@ include file="/common/footer_eoms.jsp"%>