<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@page import="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintMain"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="com.boco.eoms.deviceManagement.common.utils.CommonUtils;"%>
<%@ include file="/common/taglibs.jsp"%>
 
 <%
  long localTimes=com.boco.eoms.base.util.StaticMethod.getLocalTime().getTime();
  String fromEoms = request.getParameter("fromEoms"); 
  //如果是从EOMS端来的工单
  if("true".equals(fromEoms)){
	  PnrComplaintMain main = (PnrComplaintMain)request.getAttribute("sheetMain");
	  if(main != null){
		  main.setMainEomsSheetId(request.getParameter("sheetId"));
		  main.setMainCustomerServiceNo(request.getParameter("parentCorrelation"));
		  main.setMainCustomerName(request.getParameter("customName"));
		  main.setMainCustomPhone(request.getParameter("customNum"));
		  main.setMainCity(request.getParameter("toDeptId")); //地市
		  main.setMainComplaintNum(request.getParameter("complaintNum")); //故障号码
		  //故障地点
		  String mainLocation = request.getParameter("faultSite");
		  mainLocation = java.net.URLDecoder.decode(mainLocation, "utf-8").replaceAll("<percentage>", "%").replaceAll("<plus>", "+");
		  main.setMainLocation(mainLocation);
		  //标题
		  String title = StaticMethod.null2String(request.getParameter("title"));
		  title = java.net.URLDecoder.decode(title, "utf-8").replaceAll("<percentage>", "%").replaceAll("<plus>", "+");
		  main.setTitle(title);
		  //默认处理时限比EOMS的工单处理时限减少2小时
		  String acceptLimit = StaticMethod.null2String(request.getParameter("acceptLimit"));
		  java.util.Date date = new DateTime(CommonUtils.stringToDate(acceptLimit)).minusHours(2).toDate();
		  main.setSheetAcceptLimit(date);   //受理时限
		  main.setSheetCompleteLimit(date); //处理时限和受理时限是一样的
		  
		  String faultGenerantTime = StaticMethod.null2String(request.getParameter("faultGenerantTime"));
		  main.setMainFaultOccurTime(CommonUtils.stringToDate(faultGenerantTime));  //故障产生时间	  
		  String complaintTime = StaticMethod.null2String(request.getParameter("complaintTime"));
		  main.setMainComplaintTime(CommonUtils.stringToDate(complaintTime));  //投诉时间
		  String mainComplaintType = StaticMethod.null2String(request.getParameter("netSortType")).trim();
		  main.setMainComplaintType(mainComplaintType);
		  String mainComplaintDesc = StaticMethod.null2String(request.getParameter("complaintDesc"));
		  mainComplaintDesc = java.net.URLDecoder.decode(mainComplaintDesc, "utf-8"); 
		  main.setMainComplaintDesc(mainComplaintDesc);//投诉内容
		  request.setAttribute("sheetMain",main);
	  }
  }else{
	  //如果是从巡检直接派发，回复需附件默认为否，是否大面积投诉默认为否
	  PnrComplaintMain complaintMain = (PnrComplaintMain)request.getAttribute("sheetMain");
	  if(complaintMain.getMainFileNeeded() == null || "".equals(complaintMain.getMainFileNeeded()) ){
		  complaintMain.setMainFileNeeded("1030102");
	  }
	  if(complaintMain.getMainLargeArea() == null || "".equals(complaintMain.getMainLargeArea()) ){
		  complaintMain.setMainLargeArea("1030102");
	  }
  }

%>
 
<%@ include file="/WEB-INF/pages/wfworksheet/pnrcomplaint/baseinputmainhtmlnew.jsp"%>

<script type="text/javascript">
	Ext.onReady(function() {
		initCountry();
	});

     	//selectLimit();
	function selectLimit(){
	    Ext.Ajax.request({
			method:"get",
			url: "pnrcomplaint.do?method=newShowLimit&flowName=PartnerFaultDealProcess",
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
	* 改变专业类型
	*/
	function changeSpecialty(selected){
		var specialty = selected.value;
		//专业类型如果选择集客家客，客户品牌默认未知， 客户级别默认其他
		if('1122505'==specialty && '${param.fromEoms}'== 'true'){
			var mainCustomBrand = document.getElementById('mainCustomBrand');
			mainCustomBrand.value = '101060408';
			var mainCustomLevel = document.getElementById('mainCustomLevel');
			mainCustomLevel.value = '101060304';
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
    
    <input type="hidden" name="sheetTaskTypeBySheetId" value="002"/><!-- 工单任务类型，生成工单流水号专用 -->
    <input type="hidden" name="beanId" value="iPnrComplaintMainManager"/> 
    <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintMain"/>	
    <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintLink"/>
    <br>

    <!-- 工单基本信息 --> 
	<table id="sheet" class="formTable" >
		<tr>
		  <td class="label">受理时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly" 
				id="sheetAcceptLimit" value="${eoms:date2String(sheetMain.sheetAcceptLimit)}" 
				onclick="popUpCalendar(this, this)" alt="allowBlank:false"/> 
		  </td>
		  <td class="label">完成时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly" 
				id="sheetCompleteLimit" value="${eoms:date2String(sheetMain.sheetCompleteLimit)}" 
				onclick="popUpCalendar(this, this)" alt="allowBlank:false"/>
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
 			<td class="label">故障产生时间*</td>
			<td class="content">
				<input type="text" class="text" name="mainFaultOccurTime" readonly="readonly" 
					id="mainFaultOccurTime" value="${eoms:date2String(sheetMain.mainFaultOccurTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);" alt="allowBlank:false"/>   
			</td>
			<td class="label">故障地点*</td>
			<td class="content">
				<input class="text" type="text" name="mainLocation"
					id="mainLocation" alt="allowBlank:false" value="${sheetMain.mainLocation }"/>
			</td>
		</tr>
		
 		<tr>
 			<td class="label">故障号码*</td>
			<td class="content">
				<input class="text" type="text" name="mainComplaintNum"
					id="mainComplaintNum" alt="allowBlank:false" value="${sheetMain.mainComplaintNum }"/>
			</td>
			<td class="label">专业类型*</td>
			<td class="content">
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue="${sheetMain.mainSpecialty}"
					initDicId="11225" alt="allowBlank:false" styleClass="select" onchange="changeSpecialty(this)" />
			</td>
		</tr>
		<tr>
 			<td class="label">投诉时间*</td>
			<td class="content">
				<input type="text" class="text" name="mainComplaintTime" readonly="readonly" 
					id="mainComplaintTime" value="${eoms:date2String(sheetMain.mainComplaintTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);" alt="allowBlank:false"/>   
			</td>
			<td class="label">紧急程度*</td>
			<td class="content">
				<eoms:comboBox name="mainUrgencyLevel" id="mainUrgencyLevel" defaultValue="${sheetMain.mainUrgencyLevel}"
						initDicId="1010102" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
 		<tr>
 			<td class="label">回复需附件*</td>
			<td class="content">
				<eoms:comboBox name="mainFileNeeded" id="mainFileNeeded" defaultValue="${sheetMain.mainFileNeeded}" 
					initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">是否大面积投诉*</td>
			<td class="content">
				<eoms:comboBox name="mainLargeArea" id="mainLargeArea" defaultValue="${sheetMain.mainLargeArea}"
					initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		<tr>
 			<td class="label">客户姓名*</td>
			<td class="content">
				<input class="text" type="text" name="mainCustomerName"
					id="mainCustomerName" alt="allowBlank:false" value="${sheetMain.mainCustomerName }"/>
			</td>
			<td class="label">客户电话*</td>
			<td class="content">
				<input class="text" type="text" name="mainCustomPhone"
					id="mainCustomPhone" alt="allowBlank:false" value="${sheetMain.mainCustomPhone }"/>
			</td>
		</tr>
		
		<tr>
 			<td class="label">客户品牌*</td>
			<td class="content">
				<eoms:comboBox name="mainCustomBrand" id="mainCustomBrand" defaultValue="${sheetMain.mainCustomBrand}"
					initDicId="1010604" alt="allowBlank:false" styleClass="select"/>
			</td>
			<td class="label">客户级别*</td>
			<td class="content">
				<eoms:comboBox name="mainCustomLevel" id="mainCustomLevel" defaultValue="${sheetMain.mainCustomLevel}"
					initDicId="1010603" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
		<c:if test="${param.fromEoms == 'true'}">
			<tr>
	 			<td class="label">EOMS工单流水号</td>
				<td class="content">
					<input class="text" type="text" name="mainEomsSheetId"
						id="mainEomsSheetId" alt="allowBlank:true" value="${sheetMain.mainEomsSheetId }"/>
				</td>
				<td class="label">EOMS客服流水号</td>
				<td class="content">
					<input class="text" type="text" name="mainCustomerServiceNo"
						id="mainCustomerServiceNo" alt="allowBlank:true" value="${sheetMain.mainCustomerServiceNo }"/>
				</td>
			</tr>
		</c:if>
		<tr>
 			<td class="label">
				投诉分类*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainComplaintType" 
					id="mainComplaintType" alt="allowBlank:false,maxLength:1000,vtext:'请填入内容，最多输入 1000 字符'">${sheetMain.mainComplaintType}</textarea>
			</td>
		</tr>
		<tr>
			<c:if test="${param.fromEoms == 'true'}" var="eomsResult">
	 			<td class="label">
					终端描述
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="mainTerminalType" 
						id="mainTerminalType" alt="allowBlank:true,maxLength:1000,vtext:'请填入内容，最多输入 1000 字符'">${sheetMain.mainTerminalType}</textarea>
				</td>
			</c:if>
			<c:if test="${!eomsResult}">
				<td class="label">
					终端描述*
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="mainTerminalType" 
						id="mainTerminalType" alt="allowBlank:false,maxLength:1000,vtext:'请填入内容，最多输入 1000 字符'">${sheetMain.mainTerminalType}</textarea>
				</td>
			</c:if>
		</tr>
		<tr>
 			<td class="label">
				投诉内容*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainComplaintDesc" 
					id="mainComplaintDesc" alt="allowBlank:false,maxLength:1000,vtext:'请填入内容，最多输入 1000 字符'">${sheetMain.mainComplaintDesc}</textarea>
			</td>
		</tr>
		<tr>
 			<td class="label">
				预处理情况*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainPreDealResult" 
					id="mainPreDealResult" alt="allowBlank:false,maxLength:1000,vtext:'请填入内容，最多输入 1000 字符'">${sheetMain.mainPreDealResult}</textarea>
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
		            scope="request" idField="sheetAccessories" appCode="pnrcomplaint" alt="allowBlank:true"/> 				
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
