<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/showNewDistributeAapproveBack.jsp"%>
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
  var values = "${photoList}";
	if (values!=null){
	
	var photoDiv =  document.getElementById("photoDiv");  	
	
		photoDiv.style.display = "block";
	}
  
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
	function savaDraft(){
		      document.getElementById("isDraft").value = "true";
   			  document.forms(0).submit() 
		 }
</script>


<html:form action="/pnrTransferArteryPrecheck.do?method=performAdd" styleId="theform" >
	<input type="hidden" id="viewFlag" name="viewFlag" value="newJob" /><!-- 保存标识 -->
	<input type="hidden" id="isDraft" name="isDraft" value="" /><!-- 保存草稿标示 -->
	<input id="dueDate" type="hidden" name="dueDate" value="${pnrTransferOffice.endTime}">
    <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTransferOffice.state}">
    <input id="themeInterface" type="hidden" name="themeInterface" value="interface">
    <input id="sheetId" type="hidden" name="sheetId" value="${pnrTransferOffice.sheetId}">
<table class="formTable">
	<tr>
	  <td class="label">工单名称*</td>
	  <td colspan="3">
	  <c:if test="${pnrTransferOffice.theme ==null}">
	  
	    <input type="text" name="title" class="text max" id="title"
			value="${pnrTransferOffice.theme}" alt="allowBlank:false,maxLength:120,vtext:'请输入工单名称，最大长度为60个汉字！'"/>
	  </c:if>
	  <c:if test="${pnrTransferOffice.theme !=null}">
	<input type="text" name="title" class="text max" value="${pnrTransferOffice.theme}" readOnly=true/>
	  </c:if>
	  </td>
	</tr>

	<tr>
	  <td class="label">工单发起人*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
	  <input type="hidden" name="initiator" value="${userId}" />
	  </td>
	  
	  <td class="label">发起人部门*</td>
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
				承载业务级别*
			</td>
			<td class="content">
					<eoms:comboBox name="bearService" id="bearService"
					defaultValue="${pnrTransferOffice.bearService}" initDicId="1012313"
					alt="allowBlank:false" styleClass="select" />
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
				应急/常规*
			</td>
			 <td class="content">
				<eoms:comboBox name="precheckFlag" id="precheckFlag"
					defaultValue="${pnrTransferOffice.precheckFlag}" initDicId="1012314"
					alt="allowBlank:false" styleClass="select" />(应急工单仅限应急资金解决工单)	    
			 </td>
		     <td class="label">
				预检预修申请提交时间*
			</td>
			<td class="content">
				<input type="text" class="text" name="mainFaultOccurTime"
					readonly="readonly" id="mainFaultOccurTime"
					value="${eoms:date2String(mainFaultOccurTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
					alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				项目金额估算*
			</td>
			 <td class="content">
		    <input type="text" class="text" id="projectAmount"  name="projectAmount" value="<fmt:formatNumber value='${pnrTransferOffice.projectAmount!=null&&pnrTransferOffice.projectAmount!=""?pnrTransferOffice.projectAmount:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" onblur="check(this.value);" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数,且小于50000'"/>(单位:元)
		     </td>
		    <td class="label">
					实物赔补*
			    </td>
				<td class="content">
			    	<input type="text" class="text" id="kindRestitution"  name="kindRestitution" value="<fmt:formatNumber value='${pnrTransferOffice.kindRestitution!=null&&pnrTransferOffice.kindRestitution!=""?pnrTransferOffice.kindRestitution:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" onblur="checkSumFormat(this.value);"/>(单位:元)
			    </td> 
			</tr>
			<tr>
				<td class="label">
					现金赔补*
			    </td>
				<td class="content">
			    	<input type="text" class="text" id="compensate"  name="compensate" value="<fmt:formatNumber value='${pnrTransferOffice.compensate!=null&&pnrTransferOffice.compensate!=""?pnrTransferOffice.compensate:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />"  onblur="checkSumFormat2(this.value);"/>(单位:元)
			    </td>
			    <td class="label">
					收支比
				</td>
				<td class="content">
					<div id="incomeRatioDiv"><fmt:formatNumber value='${pnrTransferOffice.calculateIncomeRatio!=null&&pnrTransferOffice.calculateIncomeRatio!=""?pnrTransferOffice.calculateIncomeRatio:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' /></div>
					<input type="hidden" id="incomeRatio" name="incomeRatio" value="<fmt:formatNumber value='${pnrTransferOffice.calculateIncomeRatio!=null&&pnrTransferOffice.calculateIncomeRatio!=""?pnrTransferOffice.calculateIncomeRatio:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" />
				</td>
			</tr>
			<tr>
			<td class="label">
				线路名称*
			</td>
			 <td class="content">
				<input type="text" class="text" id="lineName"  name="lineName" value="${pnrTransferOffice.lineName}" alt="allowBlank:false,maxLength:100,vtext:'请输入线路名称，最大长度为50个汉字！'" />
		   </td>
			<td class="label">
				项目起点*
			</td>
			 <td class="content">
		    	<input type="text" class="text" id="projectStartPoint"  name="projectStartPoint" value="${pnrTransferOffice.projectStartPoint}" alt="allowBlank:false,maxLength:100,vtext:'请输入项目起点，最大长度为50个汉字！'" />
		     </td>	
		</tr>
		<tr>
			<td class="label">
				项目终点*
			</td>
			 <td class="content">
				<input type="text" class="text" id="projectEndPoint"  name="projectEndPoint" value="${pnrTransferOffice.projectEndPoint}" alt="allowBlank:false,maxLength:100,vtext:'请输入项目终点，最大长度为50个汉字！'" />
		   </td>
		   <td class="label">
				具体地点（标石，杆号，人井号）*
			</td>
			 <td class="content">
				<input type="text" class="text" id="specificLocation"  name="specificLocation" value="${pnrTransferOffice.specificLocation}" alt="allowBlank:false,maxLength:200,vtext:'请输入具体地点，最大长度为100个汉字！'" />
		   </td>
		</tr>
			
			  <tr>
		    <td class="label">
		    	事前照片*<!--*不起实际意义, 实际上还是可以不上传 -->
			</td>	
			<td colspan="3">
			<div id="photoDiv" style="display:none">
				<table id="myPhotoTable">
					${photoList }
				</table>
			</div>
				<input type="hidden" name="mainResId" id="mainResId" value="" />
				<input type="button" class="btn" value="选择" onclick="selectPhoto()" id="sig"/>
				<input type="hidden" name="photoIds" id="photoIds" alt="allowBlank:true" value="${photoIds }"/>
		    </td>
		</tr>
		<tr>
			<td class="label">
				预检预修描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" id="mainRemark"
					alt="allowBlank:false,maxLength:2000,vtext:'请填入预检预修描述，最多输入 2000 字符'">${pnrTransferOffice.faultDescription}</textarea>
			</td>
		</tr>
		
		<!--<tr>
			<td class="label">
				经度
			</td>
			 <td class="content">
		    <input type="text" class="text" id="longitude"  name="longitude" value="${pnrTransferOffice.projectAmount}" />
		     </td>
			<td class="label">
				纬度
			</td>
			<td  class="content">
			<input type="text" class="text" id="latitude"  name="latitude" value="${pnrTransferOffice.projectAmount}" />
			</td>
		</tr>
--></table>
<!-- 附件-->
<table id="sheet" class="formTable">
	  <tr><!--
		    <td class="label">
		    	照片
			</td>	
			<td>
		    <eoms:attachment name="sheetMain" property="picture" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
		    --><td class="label">
		    	附件
			</td>	
			<td>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
	   </tr>			  
</table>

<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				派发
			</html:submit>
			
		<!-- 	<c:if test="${access eq '1'}">
				<html:button styleClass="btn" property="" onclick="return savaDraft();" >
					保存
				</html:button>
			</c:if> -->
		</div>
	</html:form>
	<script type="text/javascript">
	/**
	*  打开图片选择页面
	*/
	function selectPhoto(){
		var selectedPhotoIds = document.getElementById("photoIds").value;
		var photoType="transferArteryPrecheckProcess";	//干线预检预修
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=conditionSelectPhoto&selectedPhotoIds='+selectedPhotoIds+"&photoType="+photoType;
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	/**
	* 设置图片名称和ID
	*/
	function setPhoto(photoes,photoIds){
	var photoId = document.getElementById("photoIds");
	photoId.value=photoIds;
		if(photoes.length>0&&photoes.length<=2){
				alert("您选择的图片数量不足三张，请重新选择！");
				}else if(photoes.length>2){
				var photoDiv = document.getElementById("photoDiv");
					photoDiv.style.display="block";
					deleteAllChild();
				for(var i=0;i<photoes.length;i++){
					var id = photoes[i].id;
					var longitude = photoes[i].longitude;
					var latitude = photoes[i].latitude;
					var createtime = photoes[i].createtime;
					var userId = photoes[i].userId;
					addMess(longitude,latitude,createtime,userId);
					}
				}
			}
	
	
	function addMess(longitude,latitude,createtime,userId){
		
		var myTable = document.getElementById("myPhotoTable");
		var newTR = myTable.insertRow(myTable.rows.length);
		var myTdTime1=newTR.insertCell(0);
		var myTdTime2=newTR.insertCell(1);
			myTdTime1.innerHTML = "拍照时间：";
			myTdTime2.innerHTML = createtime;
		
		var myTdPerson1 = newTR.insertCell(2);
		var myTdPerson2 = newTR.insertCell(3);
			myTdPerson1.innerHTML = "拍照人：";
			myTdPerson2.innerHTML = userId;
			
		var myTdLongitude1 = newTR.insertCell(4);
		var myTdLongitude2 = newTR.insertCell(5);
			myTdLongitude1.innerHTML = "经度：";
			myTdLongitude2.innerHTML = longitude;
			
		var myTdLatitude1 = newTR.insertCell(6);
		var myTdLatitude2 = newTR.insertCell(7);
			myTdLatitude1.innerHTML = "纬度：";
			myTdLatitude2.innerHTML = latitude;
		
	}
	
	function deleteAllChild(){
		
		var myTable = document.getElementById("myPhotoTable");
		while(myTable.hasChildNodes()){
		myTable.removeChild(myTable.lastChild);
		}
		
	}
</script>
<!-- 引入场景模板公用js代码  sceneJsUtil.jsp -->	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferArteryPrecheck/util.jsp"%>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
</script>  
