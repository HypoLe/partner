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
       document.getElementById("faultSource").disabled=false;
   }
   function savaDraft(){

      document.getElementById("isDraft").value = "true";
 			  document.forms(0).submit() 
 	}
 	/**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var pid='${pnrTransferOffice.processInstanceId}';
		//alert(pid);
		var url = '${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=showCreateWorkPhoto&pid='+pid;
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>

<html:form action="/pnrTransferOffice.do?method=performAdd" styleId="theform" >
	<input type="hidden" id="isDraft" name="isDraft" value="" /><!-- 保存草稿标示 -->	
	<input id="dueDate" type="hidden" name="dueDate" value="${pnrTransferOffice.endTime}">
    <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTransferOffice.state}">
    <input id="netResInspectId" type="hidden" name="netResInspectId" value="${pnrTransferOffice.netResInspectId}">
<table class="formTable">
	<tr>
	  <td class="label">工单主题*</td>
	  <td colspan="3">
	    <input type="text" name="title" class="text max" id="title"
			value="${pnrTransferOffice.theme}" alt="allowBlank:false,maxLength:200,vtext:'请输入工单主题，最大长度为100个汉字！'"/>
	  </td>
	</tr>

	<tr>
	  <td class="label">操作人*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
	  <input type="hidden" name="initiator" value="${userId}"/>
	  </td>
	  
	  <td class="label">操作人部门*</td>
	  <td class="content"><eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao"/></td>
	</tr>

</table>
		
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>

			<td class="label">
				故障处理时限*
			</td>
			<td class="content">
				<input type="text" class="text" id="sheetCompleteLimit"
					name="sheetCompleteLimit" value="${pnrTransferOffice.processLimit}"
					alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
				(单位:小时)
			</td>
			<td class="label">
				故障来源*
			</td>
			<td class="content">
				<eoms:comboBox name="faultSource" id="faultSource"
					defaultValue="${pnrTransferOffice.faultSource}" initDicId="1012302"
					alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		<tr>
			<td class="label">
				故障发生时间*
			</td>
			<td class="content">
				<input type="text" class="text" name="mainFaultOccurTime"
					readonly="readonly" id="mainFaultOccurTime"
					value="${eoms:date2String(pnrTransferOffice.createTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				工单子类型*
			</td>
			<td class="content">
				<eoms:comboBox name="mainFaultNet" id="mainFaultNet"
					defaultValue="${pnrTransferOffice.subType}" initDicId="1012301"
					alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				故障联系人+手机号*
			</td>
			<td class="content">
				<textarea class="textarea max" name="mainpeople" id="mainpeople"
					alt="allowBlank:false,maxLength:200,vtext:'请填入故障联系人，最多输入 200 字符'">${pnrTransferOffice.connectPerson}</textarea>
			</td>
			<td class="label">
				故障描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" id="mainRemark"
					alt="allowBlank:false,maxLength:2000,vtext:'请填入故障描述，最多输入 2000 字符'">${pnrTransferOffice.faultDescription}</textarea>
			</td>
		</tr>
		<c:if test="${pnrTransferOffice.faultSource =='101230208'}">
				
				<tr>
					<td class="label">照片清单</td>
				  <td class="content" >
				  	<input type="button" class="btn" id="showPhotos" name="showPhotos" value="查看照片" onclick="selectPhoto()">
				  	<!-- <input type="button" class="btn" id="showMap" name="showMap" value="地图"> -->
				 </td>
					<td class="label">
						关联巡检众筹转派工单号
					</td>
					<td class="content" colspan="3">
						${pnrTransferOffice.netResInspectId}
					</td>
				</tr>
			</c:if>
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
		<eoms:chooser id="sendObject" panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDeptTask&dh=${country}&level=1',rootId:'${country}'}]"  config="{returnJSON:true,showLeader:true}"
		   category="[{id:'taskAssignee',text:'接收',allowBlank:false,childType:'user',limit:1,vtext:'请选择接收对象'}]"
		  data="" />
	</div>	
</fieldset>

		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				派发
			</html:submit>
			
		<!-- 	<c:if test="${access eq '1'}">
				<html:button styleClass="btn" property="" onclick="return savaDraft();" >
					保存
				</html:button>
			</c:if>  -->	
		</div>
	</html:form>
<script type="text/javascript">
	function setdisable(){
		var faultSource='${pnrTransferOffice.faultSource}';
		if(faultSource=='101230208'){
			document.getElementById("faultSource").disabled=true;
		}else{
			var obj=document.getElementById('faultSource'); 
			var index;
			for(var i=0;i<obj.length;i++){
				var val = obj.options[i].value;
				if(val=='101230208'){
					index=i;
				}
			}
			obj.options.remove(index); 
		}
	}
	setdisable();
</script>
<%@ include file="/common/footer_eoms.jsp"%>