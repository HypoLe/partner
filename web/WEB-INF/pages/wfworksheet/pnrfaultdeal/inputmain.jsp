<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@page import="com.boco.eoms.partner.sheet.faultdeal.model.PnrFaultDealMain"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="com.boco.eoms.deviceManagement.common.utils.CommonUtils;"%>
<%@ include file="/common/taglibs.jsp"%>
<%
 long localTimes=com.boco.eoms.base.util.StaticMethod.getLocalTime().getTime();
 %>

<%
  String fromEoms = request.getParameter("fromEoms"); 
  //如果是从EOMS端来的工单
  if("true".equals(fromEoms)){
	  PnrFaultDealMain main = (PnrFaultDealMain)request.getAttribute("sheetMain");
	  if(main != null){
		  main.setMainEomsSheetId(request.getParameter("sheetId"));
		  main.setMainEomsAlarmNo(request.getParameter("alarmId"));
		  main.setMainCity(request.getParameter("toDeptId"));
		  main.setMainCountry(request.getParameter("mainMaintainUnit"));
		  String title = StaticMethod.null2String(request.getParameter("title"));
		  title = java.net.URLDecoder.decode(title, "utf-8").replaceAll("<percentage>", "%").replaceAll("<plus>", "+");
		  main.setTitle(title);
		  String acceptLimit = StaticMethod.null2String(request.getParameter("acceptLimit"));
		  main.setSheetAcceptLimit(CommonUtils.stringToDate(acceptLimit));   //受理时限
		  String completeLimit = StaticMethod.null2String(request.getParameter("completeLimit"));
		  main.setSheetCompleteLimit(CommonUtils.stringToDate(completeLimit));  //处理时限
		  String faultGenerantTime = StaticMethod.null2String(request.getParameter("faultGenerantTime"));
		  main.setMainFaultOccurTime(CommonUtils.stringToDate(faultGenerantTime));  //故障产生时间
		  String mainNetTypeThree = StaticMethod.null2String(request.getParameter("netSortType")).trim();//1010104020501
		  String mainNetTypeTwo = "";
		  String mainNetTypeOne = "";
		  if(!"".equals(mainNetTypeThree) && mainNetTypeThree.length() == 13){
			  mainNetTypeTwo = mainNetTypeThree.substring(0,mainNetTypeThree.length() -2);
			  mainNetTypeOne = mainNetTypeThree.substring(0,mainNetTypeThree.length() -4);
			  main.setMainNetType(mainNetTypeOne);
			  main.setMainNetTypeTwo(mainNetTypeTwo);
			  main.setMainNetTypeThree(mainNetTypeThree);
		  }
		  String mainRemark = StaticMethod.null2String(request.getParameter("dealDesc"));
		  mainRemark = java.net.URLDecoder.decode(mainRemark, "utf-8"); 
		  main.setMainRemark(mainRemark);//巡检任务描述
		  main.setMainFaultNet(request.getParameter("netName"));
		  request.setAttribute("sheetMain",main);
	  }
  }
%>
<%@ include file="/WEB-INF/pages/wfworksheet/pnrfaultdeal/baseinputmainhtmlnew.jsp"%>

<script type="text/javascript">
	Ext.onReady(function() {
		initCountry();
	});

     	//selectLimit();
	function selectLimit(){
	    Ext.Ajax.request({
			method:"get",
			url: "pnrfaultdeal.do?method=newShowLimit&flowName=PartnerFaultDealProcess",
			success: function(x){
	        	var o = eoms.JSONDecode(x.responseText);
	        	if((o.acceptLimit == null || o.acceptLimit == "")&&(o.replyLimit == null || o.replyLimit == "")){
	        	   // $("sheetAcceptLimit").value = "";
	        	   // $('sheetCompleteLimit').value = "";
	           	}else{
	           	    var times=<%=localTimes%>;
		        	var dt1 = new Date().add(Date.MINUTE,parseInt(o.acceptLimit,10));
		        	var dt2 = dt1.add(Date.MINUTE,parseInt(o.replyLimit,10));
		           	$("sheetAcceptLimit").value = dt1.format('Y-m-d H:i:s');
		          	$('sheetCompleteLimit').value = dt2.format('Y-m-d H:i:s');
	        	}
	 		}
	    });
   }
   
   function delAllOption(elementid){
        var ddlObj = document.getElementById(elementid);//获取对象
        for(var i=ddlObj.length-1;i>=0;i--){
             ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
        }
    }

	//地市区县联动
	function changeCity(con){    
		    delAllOption("country");//地市选择更新后，重新刷新县区
			var city = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
			Ext.Ajax.request({
				url : url,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					var json = eval(res);
					var countyName = "country";
					var arrOptions = json[0].cb.split(",");
					var obj=$(countyName);
					var i=0;
					var j=0;
					for(i=0;i<arrOptions.length;i++){
						var opt=new Option(arrOptions[i+1],arrOptions[i]);
						obj.options[j]=opt;
						j++;
						i++;
					}
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}
	
	/**	
	* 页面初始化地市下的区县
	*/
	function initCountry(){
		delAllOption("country");
		var city = document.getElementById("city").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
		Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) { 
				res = result.responseText;
				if(res.indexOf("[{")!=0){
					res = "[{"+result.responseText;
				}
				if(res.indexOf("<\/SCRIPT>")>0){
			  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
				}
				var json = eval(res);
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					var country = "${sheetMain.mainCountry}";
					if(arrOptions[i]==country){
						obj.options[j].selected=true;
					}
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
	}
	
	/**
	*  打开选择资源页面
	*/
	function selectRes(){
		var url = '${app}/partner/res/PnrResConfig.do?method=searchResBySheet';
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
			}
        }
	}
	
   </script>
	<input type="hidden" name="processTemplateName" value="PartnerFaultDealProcess" />
	<input type="hidden" name="operateName" value="newWorkSheet" />
	<input type="hidden" name="gatherPhaseId" value="HoldTask" />
	<c:if test="${status!=1}">
	   <input type="hidden" name="phaseId" id="phaseId" value="AcceptTask" />
       <input type="hidden" id="operateType" name="operateType" value="0" />
       <input type="hidden" name="gatherPhaseId" id="gatherPhaseId" value="HoldTask" />
    </c:if>
    
    <c:if test="${status==1}">
	   <input type="hidden" name="phaseId" id="phaseId" value="OverTask" />
    </c:if>
    
    <input type="hidden" name="sheetTaskTypeBySheetId" value="001"/><!-- 工单任务类型，生成工单流水号专用 -->
    <input type="hidden" name="beanId" value="iPnrFaultDealMainManager"/> 
    <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.faultdeal.model.PnrFaultDealMain"/>	
    <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.faultdeal.model.PnrFaultDealLink"/>
    <br>

    <!-- 工单基本信息 --> 
	<table id="sheet" class="formTable" >
		<tr>
		  <td class="label">受理时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly" 
				id="sheetAcceptLimit" value="${eoms:date2String(sheetMain.sheetAcceptLimit)}" 
				onclick="popUpCalendar(this, this)" alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'受理时限不能晚于处理时限',allowBlank:false"/>  
		  </td>
		  <td class="label">完成时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly" 
				id="sheetCompleteLimit" value="${eoms:date2String(sheetMain.sheetCompleteLimit)}" 
				onclick="popUpCalendar(this, this)" alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'完成时限不能早于受理时限',allowBlank:false"/>   
		  </td>		  
		</tr>
		<tr>
 			<td class="label">故障产生时间*</td>
			<td class="content">
				<input type="text" class="text" name="mainFaultOccurTime" readonly="readonly" 
					id="mainFaultOccurTime" value="${eoms:date2String(sheetMain.mainFaultOccurTime) }"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);" alt="allowBlank:false"/>   
			</td>
			<td class="label">专业类型*</td>
			<td class="content">
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue="${sheetMain.mainSpecialty}"
					initDicId="11225" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
		<tr>
 			<td class="label">地市*</td>
			<td class="content" >
				<!-- 地市 -->			
				<select name="mainCity" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:iterate id="city" name="city">
						<c:if test="${sheetMain.mainCity==city.areaid}" var="result">
							<option value="${city.areaid}" selected="selected" >
								${city.areaname}
							</option>
						</c:if>
						<c:if test="${!result}">
							<option value="${city.areaid}" >
								${city.areaname}
							</option>
						</c:if>
					</logic:iterate>
					
				</select>
			</td>
 			<td class="label">区县*</td>
			<td class="content" >
				<!-- 区县 -->			
				<select name="mainCountry" id="country" class="select"
					alt="allowBlank:false,vtext:'请选择所在县区'">
					<option value="">
						--请选择所在县区--
					</option>				
				</select>
			</td>
 		</tr>
 		<tr>
			<td class="label">是否计费</td>
			<td class="content">
				<select name="mainIfcharging" class="select">
					<option value="1" <c:if test="${sheetMain.mainIfcharging eq '1' }">selected="selected"</c:if> >是</option>
					<option value="0" >否</option>
			
				</select>
			</td>
 			<td class="label">区域特征*</td>
			<td class="content">
				<eoms:comboBox  name="mainRegiontype" id="mainRegiontype"  defaultValue="${sheetMain.mainRegiontype}" alt="allowBlank:false"
	        			initDicId="11230"  styleClass="select" />
			</td>
		</tr>
 		<tr>
 			<td class="label">故障位置</td>
			<td class="content">
				<input class="text" type="text" name="mainLocation"
					id="mainLocation" alt="allowBlank:true" value="${sheetMain.mainLocation }"/>
			</td>
			<td class="label">紧急程度*</td>
			<td class="content">
				<eoms:comboBox name="mainUrgencyLevel" id="mainUrgencyLevel" defaultValue="${sheetMain.mainUrgencyLevel}"
						initDicId="1010102" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
 		<tr>
			<td class="label">是否集团业务*</td>
			<td class="content">
				<eoms:comboBox name="mainIsGroupBusi" id="mainIsGroupBusi" defaultValue="${sheetMain.mainIsGroupBusi}"
						initDicId="10301" alt="allowBlank:false" styleClass="select"/>
			</td>
 			<td class="label">回复需附件*</td>
			<td class="content">
				<eoms:comboBox name="mainFileNeeded" id="mainFileNeeded" defaultValue="${sheetMain.mainFileNeeded}"
					initDicId="10301" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
		<tr>
			<td class="label">网络分类一级*</td>
			<td class="content">
				<eoms:comboBox name="mainNetType" id="mainNetType" styleClass="select" 
			  	      sub="mainNetTypeTwo" initDicId="1010104" defaultValue="${sheetMain.mainNetType}" alt="allowBlank:false" />
			</td>
 			<td class="label">网络分类二级*</td>
			<td class="content">
				<eoms:comboBox name="mainNetTypeTwo" id="mainNetTypeTwo" styleClass="select"  
			  	      sub="mainNetTypeThree" initDicId="${sheetMain.mainNetType}" defaultValue="${sheetMain.mainNetTypeTwo}" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
 			<td class="label">网络分类三级*</td>
			<td class="content">
				<eoms:comboBox name="mainNetTypeThree" id="mainNetTypeThree" styleClass="select" 
			  	      initDicId="${sheetMain.mainNetTypeTwo}" defaultValue="${sheetMain.mainNetTypeThree}" alt="allowBlank:false" />
			</td>
			<td class="label">故障网元*</td>
			<td class="content">
				<input class="text" type="text" name="mainFaultNet"
					id="mainFaultNet" alt="allowBlank:false" value="${sheetMain.mainFaultNet }"/>
			</td>
		</tr>
		
		<tr>
			<td class="label">业务影响*</td>
			<td class="content">
				<input class="text" type="text" name="mainBusiEffect"
					id="mainBusiEffect" alt="allowBlank:false" value="${sheetMain.mainBusiEffect }"/>
			</td>
			<td class="label">站点</td>
			<td class="content">
				<input class="text" type="text" name="mainResName" readonly="true" 
					id="mainResName" alt="allowBlank:true" value="${sheetMain.mainResName }"/>
				<input type="hidden" name="mainResId" id="mainResId" value="" />
				<input type="button" class="btn" value="选择" onclick="selectRes()" />
			</td>
		</tr>
		<c:if test="${param.fromEoms == 'true'}">
			<tr>
	 			<td class="label">EOMS工单流水号</td>
				<td class="content">
					<input class="text" type="text" name="mainEomsSheetId"
						id="mainEomsSheetId" alt="allowBlank:true" value="${sheetMain.mainEomsSheetId }"/>
				</td>
				<td class="label">EOMS告警流水号</td>
				<td class="content">
					<input class="text" type="text" name="mainEomsAlarmNo"
						id="mainEomsAlarmNo" alt="allowBlank:true" value="${sheetMain.mainEomsAlarmNo }"/>
				</td>
			</tr>
		</c:if>
		
		<tr>
 			<td class="label">
				巡检任务描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入巡检任务描述，最多输入 2000 字符'">${sheetMain.mainRemark}</textarea>
			</td>
		</tr>
</table>
	
<!-- 附件 -->
<table id="sheet" class="formTable">
	  <tr>
		    <td class="label">
		    	<bean:message bundle="sheet" key="mainForm.accessories"/>
			</td>	
			<td colspan="3">					
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrfaultdeal" alt="allowBlank:true"/> 				
		    </td>
	   </tr>			  
</table>


<!--派单树-->
<br/>
<fieldset>
 	<legend>
     	 <bean:message bundle="sheet" key="mainForm.toOperateRoleName"/>：
		 <span id="roleName">
		 	 处理人员
		 </span>
  	</legend>
    <div class="x-form-item" >
		<eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
		   category="[{id:'dealPerformer',text:'派发',allowBlank:false,limit:1,vtext:'请选择派发对象'},{id:'copyPerformer',childType:'user',limit:'none',text:'抄送'}]" 
		  data="${sheetMain.sendObjectTotalJSON}" />
	</div>	
</fieldset>
