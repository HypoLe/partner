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
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
});
	
 </script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>

<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
 
	<content tag="heading">
	<c:out value="网络设备隐患详情页面" />
	</content><br/><br/>
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
	<table id="sheet" class="formTable">
			<tr>
				<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
			</tr>
			
			<tr>
				<td class="label">
				 地区 
				</td>
				<td class="content">
				<eoms:id2nameDB id="${hiddenTrouble.areaId }"	beanId="tawSystemAreaDao" />
				</td>
				<td class="label">
				巡检点
				</td>
				<td class="content">
					<eoms:id2nameDB id="${hiddenTrouble.checkPoint }"	beanId="checkPointDao" />
				</td>
			</tr>
			
			<tr>
				<td class="label">
				 上报隐患类型 
				</td>
				<td class="content">
					<eoms:id2nameDB id="${hiddenTrouble.hiddenTroubleType}" beanId="IItawSystemDictTypeDao" /> 
				</td>
				<td class="label">
				是否重要隐患
				</td>
				<td class="content">
					<eoms:id2nameDB id="${hiddenTrouble.isImportant}" beanId="IItawSystemDictTypeDao" /> 
				</td>
			</tr>
			
			<tr>
				<td class="label">
				 专业类型 
				</td>
				<td class="content" colspan="3">
					<eoms:id2nameDB id="${hiddenTrouble.majorType }" beanId="IItawSystemDictTypeDao" /> 
				</td>
			</tr>
			
			<tr>
				<td colspan="4"><div class="ui-widget-header" >GPS信息</div></td>
			</tr>
			
			<tr>
				<td class="label">
				 经度
				</td>
				<td class="content">
				 ${hiddenTrouble.longitude } 
				</td>
				<td class="label">
					纬度
				</td>
				<td class="content">
				 ${hiddenTrouble.latitude } 
				</td>
			</tr>
			
			<tr>
				<td colspan="4"><div class="ui-widget-header" >其它信息</div></td>
			</tr>
			
			<tr>
				<td class="label">
				 上报巡检员
				</td>
				<td class="content" colspan="3">
				 <eoms:id2nameDB id="${hiddenTrouble.checkUser }" beanId="tawSystemUserDao" /> 
				</td>
			</tr>
			
			<tr>
				<td class="label">
				 联系电话
				</td>
				<td class="content">
				 ${hiddenTrouble.phone } 
				</td>
				<td class="label">
				Email信息
				</td>
				<td class="content">
				 ${hiddenTrouble.email } 
				</td>
			</tr>
			
			<tr>
				<td class="label">
					备注
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="remark" id="remark" alt="allowBlank:true" readonly="readonly">
						${hiddenTrouble.remark}
					</textarea>
				</td>
			</tr>
			<c:if test="${hiddenTrouble.processStatus == '1'}">
				<tr>
					<td colspan="4"><div class="ui-widget-header" >处理信息</div></td>
				</tr>
				
				<tr>
					<td class="label">
					 处理时间
					</td>
					<td class="content">
					 ${hiddenTrouble.handlTime}
					</td>
					<td class="label">
					处理人
					</td>
					<td class="content">
						<eoms:id2nameDB id="${hiddenTrouble.processUser } " beanId="tawSystemUserDao" />
					</td>
				</tr>
				
				<tr>
					<td class="label">
					 处理信息
					</td>
					<td class="content" colspan="3">
					<textarea class="textarea max" name="remark" id="remark" alt="allowBlank:true" readonly="readonly">
						${hiddenTrouble.handleMsg}
					</textarea>
				</td>
				</tr>
			</c:if>
		</table>


</form>

<%@ include file="/common/footer_eoms.jsp"%>