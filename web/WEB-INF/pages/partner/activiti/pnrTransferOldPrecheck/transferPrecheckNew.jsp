<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
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
       var myDate=new Date();
       var b =$('sheetCompleteLimit').value*60;
       myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
       $('dueDate').value = myDate.format('yyyy-MM-dd hh:mm:ss');
   }
</script>

<html:form action="/pnrTransferOldPrecheck.do?method=performAdd" styleId="theform" >
	<input id="dueDate" type="hidden" name="dueDate" value="${pnrTransferOffice.endTime}">
    <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTransferOffice.state}">
    <input id="themeInterface" type="hidden" name="themeInterface" value="interface">
<table class="formTable">
	<tr>
	  <td class="label">工单主题*</td>
	  <td colspan="3">
	    <input type="text" name="title" class="text max" id="title"
			value="${pnrTransferOffice.theme}" alt="allowBlank:false,maxLength:120,vtext:'请输入工单主题，最大长度为60个汉字！'"/>
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
				关键字*
			</td>
			<td class="content">
				<eoms:comboBox name="keyWord" id="keyWord"
					defaultValue="${pnrTransferOffice.keyWord}" initDicId="1012308"
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
				紧急程度*
			</td>
			<td class="content">
			<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
					defaultValue="${pnrTransferOffice.workOrderDegree}" initDicId="1012309"
					alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				预检预修描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" id="mainRemark"
					alt="allowBlank:false,maxLength:2000,vtext:'请填入故障描述，最多输入 2000 字符'">${pnrTransferOffice.faultDescription}</textarea>
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
<!-- -->
<!--派单树-->
<br/>
<fieldset>
 	<legend>
		 <span id="roleName">
		 	 工单接收人
		 </span>
  	</legend>
    <div class="x-form-item">
		<eoms:chooser id="sendObject" panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDeptTask&dh=${deptId}',rootId:'${deptId}'}]"  config="{returnJSON:true,showLeader:true}"
		   category="[{id:'taskAssignee',text:'接收',allowBlank:false,childType:'user',limit:1,vtext:'请选择接收对象'}]"
		  data="" />
	</div>	
</fieldset>

		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				派发
			</html:submit>
		</div>
	</html:form>
<%@ include file="/common/footer_eoms.jsp"%>