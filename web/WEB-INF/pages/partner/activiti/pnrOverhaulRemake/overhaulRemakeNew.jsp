<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/showNewDistributeAapproveBack.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
	   v.custom = function(){ 
	     if(eoms.$('processType').value.trim()==""){
	       alert("大修/改造不能为空"); return false; 
	     } 
	     
	     if(eoms.$('areaCountyAccount').value.trim()==""){
	       alert("区县账号不能为空"); return false; 
	     } 
	     
	     //验证附件是否都上传了
	     if(jq(window.frames["UIFrame1-projectsProposals"].document).find("input:checkbox[name='files']").length < 1){
  			alert("请上传项目建议书");
  			return false;
		}
		
	   if(jq(window.frames["UIFrame1-cityMeetingMinutes"].document).find("input:checkbox[name='files']").length < 1){
  			alert("请上传市公司会议纪要");
  			return false;
		}
		
	   if(jq(window.frames["UIFrame1-designDescription"].document).find("input:checkbox[name='files']").length < 1){
  			alert("请上传设计说明");
  			return false;
		}
		
	   if(jq(window.frames["UIFrame1-budgetTable"].document).find("input:checkbox[name='files']").length < 1){
  			alert("请上传概预算表");
  			return false;
		}
		
	   if(jq(window.frames["UIFrame1-constructionDrawing"].document).find("input:checkbox[name='files']").length < 1){
  			alert("请上施工图纸(pdf格式)");
  			return false;
		}
		
		//if(jq(window.frames["UIFrame1-moveModifiedApply"].document).find("input:checkbox[name='files']").length < 1){
  			//alert("请上传用户发起线路迁改申请");
  			//return false;
		//}
	     
	     return true;
   }
  
  
    //回显 大修/改造
  	var targetValue="${pnrTransferOffice.themeInterface}";
  	var obj = document.getElementById("processType");
  	if(obj){
    var options = obj.options;
    if(options){
      var len = options.length;
      for(var i=0;i<len;i++){
        if(options[i].value == targetValue){
          options[i].defaultSelected = true;
          options[i].selected = true;
          return true;
        }
      }
    } else {
      alert('missing element(s)!');
    }
  } else {
    alert('missing element(s)!');
  }
  	
  
  
   });

   function changeType1(){
       var myDate=new Date();
       var b =$('sheetCompleteLimit').value*60;
       myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
       $('dueDate').value = myDate.format('yyyy-MM-dd hh:mm:ss');
   }
   
   function savaDraft(){
		
		      document.getElementById("isDraft").value = "true";
   			  document.forms(0).submit() 
		 }
   
</script>
<script type="text/javascript">
	function check(v){
		var a=/^(\d+)(\.\d+)?$/;
		if(v==''){
			alert("项目金额估算不可以为空!");
			return;
		}
		if(!a.test(v)){
			alert("项目金额估算请输入整数或小数");
			document.getElementById("projectAmount").value=""; 
			return;
		}
		/*if(v<50000){
			alert("项目金额估算不能小于5万元");
			document.getElementById("projectAmount").value="";
			return;
		}*/
	}
</script>
<script type="text/javascript">
	 //动态获取区县下拉选
	 jq(function(){
	 
	 		//获取类别下拉选的值
		var categoryUrl="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=getAreaCountyAccount";
		var tempCountryCSJ="${pnrTransferOffice.countryCSJ}";
		jq.getJSON(categoryUrl,function (data) {
                jq(data).each(function () {
                	if(this.id == tempCountryCSJ){
                		jq("<option/>").html(this.name).val(this.id).attr("selected","selected").appendTo("#areaCountyAccount");
                	}else{
                	 jq("<option/>").html(this.name).val(this.id).appendTo("#areaCountyAccount");
                	}
                	
                });

            });
       });
       
        function checkMainEngineeringQuantity(obj,name){
    	var _value=jq(obj).val();
    	if(_value!=null&&_value!=''){
    		var _expression=/^(\d+)(\.\d+)?$/;
    		if(!_expression.test(_value)){
    			alert(name+"-请输入整数或小数!");
    			jq(obj).val("");
    			return;
    		}
    	}
    } 
	
</script>

<html:form action="/pnrOverhaulRemake.do?method=performAdd"
	styleId="theform">
	
	
	<input type="hidden" id="viewFlag" name="viewFlag" value="newJob" /><!-- 保存标识 -->
	<input type="hidden" id="isDraft" name="isDraft" value="" /><!-- 保存草稿标示 -->	
	<input id="dueDate" type="hidden" name="dueDate" value="${pnrTransferOffice.endTime}">
    <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTransferOffice.state}">
    <!-- <input id="themeInterface" type="hidden" name="themeInterface" value="interface">  -->
    <input id="sheetId" type="hidden" name="sheetId" value="${pnrTransferOffice.sheetId}">
	<input type="hidden" name="initiator" value="${userId}" />
    
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			基本信息
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label"  style="width:10%">
					项目名称*
				</td>
				<td class="content" colspan="3" style="width:35%">
					<c:if test="${pnrTransferOffice.theme ==null}">
					
					<input type="text" id="theme" name="theme" value="${pnrTransferOffice.theme}" class="text max" alt="allowBlank:false,maxLength:120,vtext:'请输入项目名称，最大长度为60个汉字！'" />
					</c:if>
					
					 <c:if test="${pnrTransferOffice.theme !=null}">
					<input type="text" name="theme" class="text max" value="${pnrTransferOffice.theme}" style="width:100%" readOnly=true/>
	 				 </c:if>
				</td>
				<td class="label" style="width:10%">
					项目编号
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="projectName" name="projectName" class="text" value="${pnrTransferOffice.projectName}"/>
				</td>
				<td class="label" style="width:10%">
					区县账号*
				</td>
				<td class="content" style="width:15%">
				 <select id="areaCountyAccount" name="areaCountyAccount" class="select">
			  	<option value="">--请选择区县账号--</option>
			 	 </select>
				</td>
			</tr>
			<tr>
				<td class="label" style="width:10%">
					项目类型*
				</td>
				<td class="content" style="width:15%">
					<select id="processType" name="processType" class="select">
						<option value="overhaul">
							大修
						</option>
						<option value="reform">
							改造
						</option>
					</select>
				</td>
				<td class="label" style="width:10%">
					大修改造类别*
				</td>
				<td class="content" style="width:15%">
					<eoms:comboBox name="overhaulType" id="overhaulType"
					defaultValue="${pnrTransferOffice.overhaulType}" initDicId="101231701"
					alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label" style="width:10%">
					紧急程度*
				</td>
				<td class="content" style="width:15%">
					<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
					defaultValue="${pnrTransferOffice.workOrderDegree}" initDicId="1012309"
					alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label" style="width:10%">
					立项时间*
				</td>
				<td class="content" style="width:15%">
					<input type="text" class="text" name="mainFaultOccurTime"
					readonly="readonly" id="mainFaultOccurTime"
					value="${eoms:date2String(mainFaultOccurTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
					alt="allowBlank:false" />
				</td>
			</tr>
			<tr>
				<td class="label" style="width:10%">
					线路名称*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="lineName" name="lineName" class="text" value="${pnrTransferOffice.lineType}" alt="allowBlank:false" />
				</td>
				<td class="label" style="width:10%">
					线路级别*
				</td>
				<td class="content" style="width:15%">
					<eoms:comboBox name="lineType" id="lineType"
					defaultValue="${pnrTransferOffice.lineType}" initDicId="101231702"
					alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label" style="width:10%">
					承载业务级别
				</td>
				<td class="content" style="width:15%">
					<eoms:comboBox name="bearService" id="bearService"
					defaultValue="${pnrTransferOffice.bearService}" initDicId="1012313"
					alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label" style="width:10%">
					敷设方式*
				</td>
				<td class="content" style="width:15%">
					<eoms:comboBox name="layingType" id="layingType"
					defaultValue="${pnrTransferOffice.layingType}" initDicId="101231703"
					alt="allowBlank:false" styleClass="select" />
				</td>
			</tr>
			<tr>
				<td class="label" style="width:10%">
					中继段
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="middlePart" name="middlePart" class="text" value="${pnrTransferOffice.middlePart}"/>
				</td>
				<td class="label" style="width:10%">
					起止地点（标石、杆号、人井号）*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="specificLocation" name="specificLocation" value="${pnrTransferOffice.specificLocation}" class="text" alt="allowBlank:false"/>
				</td>
				<td class="label" style="width:10%">
					光（电）缆型号
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="cableType" name="cableType" class="text" value="${pnrTransferOffice.cableType}"/>
				</td>
				<td class="label" style="width:10%">
					芯数
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="coreNum" name="coreNum" class="text" value="${pnrTransferOffice.coreNum}" onblur="checkMainEngineeringQuantity(this,'芯数');"/>
				</td>
			</tr>
			<tr>
				<td class="label" style="width:10%">
					起点经度*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="createLongitude" name="createLongitude" class="text" value="${pnrTransferOffice.createLongitude}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
				</td>
				<td class="label" style="width:10%">
					起点纬度*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="createLatitude" name="createLatitude" class="text" value="${pnrTransferOffice.createLatitude}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"" />
				</td>
				<td class="label" style="width:10%">
					终点经度*
				</td >
				<td class="content" style="width:15%">
					<input type="text" id="endLongitude" name="endLongitude" class="text" value="${pnrTransferOffice.endLongitude}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
				</td>
				<td class="label" style="width:10%">
					终点纬度*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="endLatitude" name="endLatitude" class="text" value="${pnrTransferOffice.endLatitude}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
				</td>
			</tr>
			<tr>
				<td class="label" style="width:10%">
					项目主管签名*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="chargeName" name="chargeName" class="text" value="${pnrTransferOffice.chargeName}" alt="allowBlank:false"/>
				</td>
				<td class="label" style="width:10%">
					电话*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="chargeTel" name="chargeTel" class="text" value="${pnrTransferOffice.chargeTel}" alt="allowBlank:false"/>
				</td>
				<td class="label" style="width:10%">
					部门负责人*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="principal" name="principal" class="text" value="${pnrTransferOffice.deptHead}" alt="allowBlank:false"/>
				</td>
				<td class="label" style="width:10%">
					电话*
				</td>
				<td class="content" style="width:15%">
					<input type="text" id="principalTel" name="principalTel" class="text" value="${pnrTransferOffice.deptHeadMobilePhone}" alt="allowBlank:false"/>
				</td>
			</tr>
		</table>
	</fieldset>

	<!-- 预算及赔补金额 -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			预算及赔补金额
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width:10%">
					项目预算*
				</td>
				<td class="content" style="width:23%">
					<input type="text" class="text" id="projectAmount"  name="projectAmount" value="<fmt:formatNumber value='${pnrTransferOffice.projectAmount!=null&&pnrTransferOffice.projectAmount!=""?pnrTransferOffice.projectAmount:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" onblur="checkOverhaul(this.value);" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数,且小于50000'"/>(单位:元)
				</td>
				
				<td class="label" style="width: 10%">
					实物赔补*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" class="text" id="kindRestitution"  name="kindRestitution" value="<fmt:formatNumber value='${pnrTransferOffice.kindRestitution!=null&&pnrTransferOffice.kindRestitution!=""?pnrTransferOffice.kindRestitution:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />"  onblur="checkSumFormat(this.value);"/>(单位:元)
				</td>
				<td class="label" style="width: 10%">
					现金赔补*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" class="text" id="compensate"  name="compensate" value="<fmt:formatNumber value='${pnrTransferOffice.compensate!=null&&pnrTransferOffice.compensate!=""?pnrTransferOffice.compensate:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />"  onblur="checkSumFormat2(this.value);"/>(单位:元)
				</td>
			</tr>
			<tr>
				<td class="label" style="width: 10%">
					收支比
				</td>
				<td class="content" style="width: 23%">
					<div id="incomeRatioDiv"><fmt:formatNumber value='${pnrTransferOffice.calculateIncomeRatio!=null&&pnrTransferOffice.calculateIncomeRatio!=""?pnrTransferOffice.calculateIncomeRatio:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' /></div>
					<input type="hidden" id="incomeRatio" name="incomeRatio" value="<fmt:formatNumber value='${pnrTransferOffice.calculateIncomeRatio!=null&&pnrTransferOffice.calculateIncomeRatio!=""?pnrTransferOffice.calculateIncomeRatio:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" />
				</td>
				<td class="label" style="width:10%">
					赔补合同编号
				</td>
				<td class="content" style="width:24%" colspan="3">
					<input type="text" id="subsidyNumber" name="subsidyNumber" class="text" value="${pnrTransferOffice.subsidyNumber}"/>
				</td>
			</tr>
		</table>
	</fieldset>

	<!-- 项目描述 -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			项目描述
		</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label" style="width:15%">
						建设原因及必要性*
					</td>
					<td colspan="3" style="width:85%">
						<textarea id="constructionReasons" name="constructionReasons" class="textarea max" alt="allowBlank:false">${pnrTransferOffice.constructionReasons}</textarea>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:15%">
						网络现状描述*
					</td>
					<td colspan="3" style="width:85%">
						<textarea id="networkStatus" name="networkStatus" class="textarea max" alt="allowBlank:false">${pnrTransferOffice.networkStatus}</textarea>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:15%">
						主要建设内容及规模*
					</td>
					<td colspan="3" style="width:85%">
						<textarea id="constructionMainContent" name="constructionMainContent" class="textarea max" alt="allowBlank:false">${pnrTransferOffice.constructionMainContent}</textarea>
					</td>
				</tr>
			</table>

		<fieldset style="border-width: 2px; border-color: #98C0F4;">
			<legend>
				主要工程量
			</legend>
			<table class="formTable">
				<tr>
					<td class="label" style="width: 10%">
						敷设光缆
					</td>
					<td class="content" style="width: 23%">
						<input type="text" id="layingCable" name="layingCable" class="text" value="${pnrTransferOffice.layingCable}" onblur="checkMainEngineeringQuantity(this,'敷设光缆');"/>
						皮长公里
					</td>
					<td class="label" style="width: 10%">
						开挖缆沟
					</td>
					<td class="content" style="width: 23%">
						<input type="text" id="excavationTrench" name="excavationTrench" class="text" value="${pnrTransferOffice.excavationTrench}" onblur="checkMainEngineeringQuantity(this,'开挖缆沟');"/>
						公里
					</td>
					<td class="label" style="width: 10%">
						新建管道
					</td>
					<td class="content" style="width: 24%">
						<input type="text" id="repairPipeline" name="repairPipeline" class="text" value="${pnrTransferOffice.repairPipeline}" onblur="checkMainEngineeringQuantity(this,'新建管道');"/>
						孔公里
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 10%">
						敷设电杆
					</td>
					<td class="content" style="width: 23%">
						<input type="text" id="rightingDemolitionPole" name="rightingDemolitionPole" class="text" value="${pnrTransferOffice.rightingDemolitionPole}" onblur="checkMainEngineeringQuantity(this,'敷设电杆');"/>
						棵
					</td>
					<td class="label" style="width: 10%">
						敷设钢绞线
					</td>
					<td class="content" style="width: 23%">
						<input type="text" id="wireLaying" name="wireLaying" class="text" value="${pnrTransferOffice.wireLaying}" onblur="checkMainEngineeringQuantity(this,'敷设钢绞线');"/>
						公里
					</td>
					<td class="label" class="label" style="width: 10%">
						其它
					</td>
					<td class="content" style="width: 24%">
						<input type="text" id="mainQuantityOther" name="mainQuantityOther" class="text" value="${pnrTransferOffice.mainQuantityOther}"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</fieldset>

	<!--综合单价  -->
		<fieldset style="border-width: 2px; border-color: #98C0F4;">
			<legend>
				综合单价
			</legend>
			<table class="formTable">
				<tr>
					<td class="label" style="width: 15%">
						皮长公里造价*
					</td>
					<td class="content" style="width: 35%">
						<input type="text" id="longLeatherMoney" name="longLeatherMoney" class="text" value="${pnrTransferOffice.longLeatherMoney}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>
						元/皮长公里
					</td>
					<td class="label" style="width: 15%">
						 孔公里造价
					</td>
					<td class="content" style="width: 35%">
						<input type="text" id="holeMoney" name="holeMoney" class="text" value="${pnrTransferOffice.holeMoney}" onblur="checkMainEngineeringQuantity(this,'孔公里造价');"/>
						元/孔公里
					</td>
				</tr>
			</table>
		</fieldset>

	<!-- 附件 -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			附件
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width:33%">
					项目建议书*
				</td>
				<td class="label" style="width:33%">
					市公司会议纪要*
				</td>
				<td class="label" style="width:34%">
					设计说明*
				</td>
			</tr>
			<tr>
				<td style="width:33%">
					<eoms:attachment name="projectsProposals" property="sheetAccessories" 
		            scope="request" idField="projectsProposals" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>
				</td>
				<td style="width:33%">
					<eoms:attachment name="cityMeetingMinutes" property="sheetAccessories" 
		            scope="request" idField="cityMeetingMinutes" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>
				</td>
				<td style="margin:2px;width:34%">
					<eoms:attachment name="designDescription" property="sheetAccessories" 
		            scope="request" idField="designDescription" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width:33%">
					概预算表*
				</td>
				<td class="label" style="width:33%">
					施工图纸(pdf格式)*
				</td>
				<td class="label" style="width:34%">
					用户发起线路迁改申请
				</td>
			</tr>
			
			<tr>
				<td style="width:33%">
					<eoms:attachment name="budgetTable" property="sheetAccessories" 
		            scope="request" idField="budgetTable" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>
				</td>
				<td style="width:33%">
					<eoms:attachment name="constructionDrawing" property="sheetAccessories" 
		            scope="request" idField="constructionDrawing" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>					
				</td>
				<td style="margin:2px;width:34%">
					<eoms:attachment name="moveModifiedApply" property="sheetAccessories" 
		            scope="request" idField="moveModifiedApply" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width:33%">
					赔补合同
				</td>
				<td class="label" style="width:33%">
					其他
				</td>
				<td >&nbsp;</td>
			</tr>
			<tr>
				<td style="width:33%">
					<eoms:attachment name="payContract" property="sheetAccessories" 
		            scope="request" idField="payContract" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>
				</td>			
				<td style="width:33%">
					<eoms:attachment name="otherAccessories" property="sheetAccessories" 
		            scope="request" idField="otherAccessories" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</fieldset>

	<!--事前照片  -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			事前照片
		</legend>
		<table class="formTable">
			<tr>
				<td colspan="5" style="width:90%">
					<div id="photoDiv" style="display: none">
						<table id="myPhotoTable">
							${photoList }
						</table>
					</div>
					<input class="text" type="hidden" name="photoIds" readonly="true"
						id="photoIds" alt="allowBlank:true" value="${photoIds}" />
					<input type="hidden" name="mainResId" id="mainResId" value="" />
					<input type="button" class="btn" value="选择" onclick="selectPhoto()"
						id="sig" />
				</td>
			</tr>
		</table>
	</fieldset>
	
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
<script type="text/javascript"><!--
	/**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var selectedPhotoIds=document.getElementById("photoIds").value;
		var photoType="overHaulNewProcess";	//大修改造
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=conditionSelectPhoto&selectedPhotoIds='+selectedPhotoIds+"&photoType="+photoType;
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
	--></script>
<!--

//-->
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