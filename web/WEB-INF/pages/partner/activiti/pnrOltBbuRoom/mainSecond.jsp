<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript">
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
   
   	}

</script>

	<html:form action="/pnrOltBbuRoom.do?method=mainSecond" styleId="theform" >
     <input id="dueDate" type="hidden" name="dueDate" value="${eoms:date2String(pnrTransferOffice.endTime)}">
     <input id="taskId" type="hidden" name="taskId" value="${taskId}">
     <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
     <!--  <input id="createTime" type="hidden" name="createTime" value="${pnrTransferOffice.createTime}"> -->
     <input id="createTime" type="hidden" name="createTime" value="${pnrTransferOffice.sendTime}">
     <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
     <input type="hidden" id="nextTaskLinkName" name="nextTaskLinkName" value="施工队施工回单"><!-- 发短信指定派发到的环节名 -->
    
    
    <input type="hidden" id="condition" name="condition" value="${condition}" />
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOltBbuRoom/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOltBbuRoom/showAapprove_basis.jsp"%>
<!--派单树-->
<br/>
  <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			审批结果
		</legend>
			<table id="sheet" class="formTable" >
			 		<tr>
						<td class="label">工单申请时间</td>
						<td class="content" >
							${eoms:date2String(pnrTransferOffice.sendTime)}
						</td>
			 			<td class="label">
							处理时限*
						</td>
						<td class="content">
							<input type="text" class="text" id="sheetCompleteLimit"	name="sheetCompleteLimit" value="${pnrTransferOffice.processLimit}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
							(单位:小时)
						</td>						
					</tr>		
			</table>
			<fieldset>
 				<legend>
		 			<span id="roleName">
		 				 工单接收人
		 			</span>
  				</legend>
			    <div class="x-form-item" >
					<eoms:chooser id="sendObject" panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDept',rootId:'${country}'}]"  config="{returnJSON:true,showLeader:true}"
					   category="[{id:'handleWorker',text:'接收',allowBlank:false,childType:'user',limit:1,vtext:'请选择接收对象'}]"
					  data="" />
				</div>	
			</fieldset>
				
		</fieldset>

<c:if test="${reason ne '' && reason ne null}">
<fieldset>
上次审批未通过原因：<font color="red">${reason}</font>
</fieldset>
</c:if>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				派发
			</html:submit>
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveSwitcher= new detailSwitcher();
 	 approveSwitcher.init({
	container:'approveHistory',
  	handleEl:'div.history-item-title'
  });
 </script>   