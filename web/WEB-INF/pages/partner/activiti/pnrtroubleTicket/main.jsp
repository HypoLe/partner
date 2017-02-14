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
var specialty = "${pnrTroubleTicket.specialty}";
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
	if(specialty!="" || specialty!= null)
	{
	 setOptionValue("mainSpecialty",specialty,",");
	}
   });

   function changeType1(){
       var myDate=new Date();
       var b =$('sheetCompleteLimit').value*60;
     //  $('distributionType').value = 'distribution';
       myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
       $('dueDate').value = myDate.format('yyyy-MM-dd hh:mm:ss');
   }
   
 /*  function changeType2(){
       var myDate=new Date();
       var b =$('sheetCompleteLimit').value*60;
        $('distributionType').value = 'saveDraft';
       myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
       $('dueDate').value =  myDate.format('yyyy-MM-dd hh:mm:ss');
   }
   */
   /**
	*  打开选择资源页面
	*/
	function selectRes(){
		var url = '${app}/partner/res/PnrResConfig.do?method=searchResBySheet&region=${areaId}&executeDept=${executeDept}';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	
	/**
	* 设置资源名称和ID
	*/
	function setRes(returnValue){
		if(returnValue){
			var index = returnValue.indexOf(',');
			if(index != -1){
				var resId = returnValue.substring(0,index);
	        	var resName = returnValue.substring(index+1);
	        	document.getElementById('mainResId').value = resId;
	        	document.getElementById('mainResName').value = resName;
	        	setTaskAssignee(resId);
			}
        }
	}
	function setTaskAssignee(resId){
	
	var url="${app}/partner/res/PnrResConfig.do?method=getChargePerson&resId="+resId;
		Ext.Ajax.request({
							url : url ,
							method: 'POST',
							success: function ( result, request ) { 
								res = result.responseText;
								var json = eval(res);
								var u = json['0']['chargePerson'];
								var n = json['0']['personName'];
								if(u!=null && u!=''){
									setValue(u,n);				
								}
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}

	function setOptionValue(id,strings,split)
	{	
		var valueArray = strings.split(split);
		var arrLength = valueArray.length;
		
		var obj = document.getElementById(id);
		var length = obj.options.length;
		// 下拉列表中所有的项
		for(var i =0;i<length;i++)
		{	//默认选择数组项
			for(var j =0;j<arrLength;j++)
			{
				if(obj.options[i].getAttribute("value")==valueArray[j])
				{
					obj.options[i].selected=true;
				}
			}
		}
	}
</script>

	<html:form action="/pnrTroubleTicket.do?method=performAdd" styleId="theform" >
     <input id="dueDate" type="hidden" name="dueDate" value="${pnrTroubleTicket.endTime}">
     <input id="themeId" type="hidden" name="themeId" value="${pnrTroubleTicket.id}">
     <input id="maintainState" type="hidden" name="maintainState" value="handle">
     <input id="firstInitiator" type="hidden" name="firstInitiator" value="${pnrTroubleTicket.firstInitiator}">
     <input id="firstCreateTime" type="hidden" name="firstCreateTime" value="${pnrTroubleTicket.firstCreateTime}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTroubleTicket.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTroubleTicket.state}">
<table class="formTable">
	<tr>
	  <td class="label">工单主题*</td>
	  <td colspan="3">
	    <input type="text" name="title" class="text max" id="title"
			value="${pnrTroubleTicket.theme}" alt="allowBlank:false,maxLength:120,vtext:'请输入工单主题，最大长度为60个汉字！'"/>
	  </td>
	</tr>

	<tr>
	  <td class="label">操作人*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
	  <input type="hidden" name="initiator" value=${userId}>
	  </td>
	  
	  <td class="label">操作人部门*</td>
	  <td class="content"><eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao"/></td>
	</tr>

</table>
		
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>
		  
		  <td class="label">故障处理时限*</td>
		  <td class="content">
		    <input type="text" class="text" id="sheetCompleteLimit"  name="sheetCompleteLimit" value="${pnrTroubleTicket.processLimit}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>(单位:小时)
		  </td>	
		  <td class="label">故障站点*</td>
			<td class="content">
				<input class="text" type="text" name="mainResName" readonly="true" 
					id="mainResName" alt="allowBlank:false" value="${pnrTroubleTicket.failedSite}"/>
				<input type="hidden" name="mainResId" id="mainResId" value="${pnrTroubleTicket.mainResId}" />
				<input type="button" class="btn" value="选择" onclick="selectRes()" />
			</td>	  
		</tr>
		<tr>
			<td class="label">故障来源*</td>
			<td class="content">
				<eoms:comboBox name="faultSource" id="faultSource" defaultValue="${pnrTroubleTicket.faultSource}"
						initDicId="1010110" alt="allowBlank:false" styleClass="select"/>
			</td>
 			<td class="label">故障发生时间*</td>
			<td class="content">
				<input type="text" class="text" name="mainFaultOccurTime" readonly="readonly" 
					id="mainFaultOccurTime" value="${eoms:date2String(pnrTroubleTicket.createTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);" alt="allowBlank:false"/>   
			</td>
			
		</tr>
		
			<td class="label">是否集团客户*</td>
			<td class="content">
				<eoms:comboBox name="mainIsGroupBusi" id="mainIsGroupBusi" defaultValue="${pnrTroubleTicket.isCustomers}"
						initDicId="10301" alt="allowBlank:false" styleClass="select"/>
			</td>
 		
			<td class="label">工单子类型*</td>
			<td class="content">
				<eoms:comboBox name="mainFaultNet" id="mainFaultNet" defaultValue="${pnrTroubleTicket.subType}"
						initDicId="1012201" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
		
		<tr>
			<td class="label">涉及专业*</td>
			<td class="content">
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue=""
					initDicId="1010108" alt="allowBlank:false" multiple="true" styleClass="select"/>
			</td>
			
 			<td class="label">
				故障联系人
			</td>
			<td class="content">
				<textarea class="textarea max" name="mainpeople" 
					id="mainpeople" alt="allowBlank:true,maxLength:200,vtext:'请填入故障联系人，最多输入 200 字符'">${pnrTroubleTicket.connectPerson}</textarea>
			</td>
		</tr>
		
		<tr>
 			<td class="label">
				故障描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入故障描述，最多输入 2000 字符'">${pnrTroubleTicket.faultDescription}</textarea>
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
		            scope="request" idField="sheetAccessories" appCode="pnrActTroubleTicket" alt="allowBlank:false;请选择保存的附件"/> 
		          				
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
    <div class="x-form-item" >
		<eoms:chooser id="sendObject" panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDeptTask&dh=${country}',rootId:'${country}'}]"  config="{returnJSON:true,showLeader:true}"
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
<script type="text/javascript">

	function setValue(id,name){
		var content=[{"id":id,"text":name,"name":name,"categoryId":"taskAssignee","nodeType":"user"}];
		chooser_sendObject.add(content);
		chooser_sendObject.update();
	}	
</script>
<%@ include file="/common/footer_eoms.jsp"%>