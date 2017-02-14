<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function selectRes(){
		var url = '${app}/partner/res/PnrResConfig.do?method=searchResBySheetOil';
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
			var list = returnValue.split(',');
	        	var resId = list[0];
	        	var resName = list[1];
	        	var resLongitude = list[2];
	        	var resLatitude = list[3];
	        	document.getElementById('siteId').value = resId;
	        	document.getElementById('siteName').value = resName;
	        	document.getElementById('longitude').value = resLongitude;
	        	document.getElementById('latitude').value = resLatitude;
        }
	}
</script>
<form action="${app}/partner/resourceInfo/oilEngine.do?method=update"    id="oilEngineForm" method="post"  >
	<table class="formTable">
			<caption>
					<div class="header center">
						修改油机信息
					</div>
			</caption>
		<tr>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${oilEngine.area}',name:'<eoms:id2nameDB id="${oilEngine.area}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area_name" id="area_name"    class="text medium" alt="allowBlank:false" readonly="readonly"/>
					<input type="hidden" name="area" id="area"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="area" handler="area_name" textField="area_name"
					checktype="" data="${boxData}" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${oilEngine.maintainCompany}',name:'<eoms:id2nameDB id="${oilEngine.maintainCompany}" beanId="tawSystemDeptDao"/>'}]</c:set>
					<input type="text" name="company" id="company" class="text max"  alt="allowBlank:false" />
			  		<input type="hidden" name="maintainCompany" id="maintainCompany"/>
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf" rootId=""
					rootText='代维公司组织' valueField="maintainCompany" handler="company" textField="company"
					checktype="dept" single="true" data="${boxData}">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					油机编号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="oilEngineNumber" class="text medium" 
					value="${oilEngine.oilEngineNumber}" id="oilEngineNumber" alt="allowBlank:false"/>
				</td>
				<td class="label">
					油机状态&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="oilEngineStatus" id="oilEngineStatus" 	initDicId="1230303"
					defaultValue="${oilEngine.oilEngineStatus}" styleClass="input select" 		alt="allowBlank:false" />
				</td>
		</tr>
		<tr>
				<td class="label">
					油机类型&nbsp;*
				</td>
				<td class="content">
						<eoms:comboBox name="oilEngineType" id="oilEngineType"  initDicId="1230302"  
					defaultValue="${oilEngine.oilEngineType}" alt="allowBlank:false" styleClass="input select">
					</eoms:comboBox> 
				</td>
				<td class="label">
					规格型号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="oilEngineModel" id="oilEngineModel" 
					value="${oilEngine.oilEngineModel}" class="text medium" 	alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					额定功率(KW)&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="powerRating" id="powerRating"  class="text medium" value="${oilEngine.powerRating}"
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>
				</td>
				<td class="label">
					标准油耗(L/小时)&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="standardFuelConsumption" value="${oilEngine.standardFuelConsumption}"
					id="standardFuelConsumption" class="text medium" 	alt="allowBlank:false" />
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类&nbsp;*
				</td>
				<td class="content">
						<eoms:comboBox name="fuleType" id="fuleType" 	initDicId="1230305"  styleClass="input select" 
						 defaultValue="${oilEngine.fuleType}" alt="allowBlank:false" 	/>
				</td>
				<td class="label">
					存放地点&nbsp;
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${oilEngine.place}',name:'<eoms:id2nameDB id="${oilEngine.place}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="place_name" id="place_name"   readonly="readonly" class="text medium" alt="allowBlank:true"/>
					<input type="hidden" name="place" id="place"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="place" handler="place_name" textField="place_name"
					checktype=""  data="${boxData}" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					生产厂家&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="oilEngineFactory" class="text medium" 
					value="${oilEngine.oilEngineFactory}" id="oilEngineFactory" alt="allowBlank:false"/>
				</td>
				<td class="label">
					产权归属&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="oilEngineProperty" id="oilEngineProperty"  initDicId="1230304"  
					defaultValue="${oilEngine.oilEngineProperty}" alt="allowBlank:false" styleClass="input select">
					</eoms:comboBox> 
				</td>
		</tr>
		</tr>
			<%--<tr>
				<td class="label">
					经度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="longitude"   id="longtitude"   class="text"  value="${oilEngine.longitude}"
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'">
				</td>
				<td class="label">
					纬度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="latitude"    id="latitude"    class="text"  value="${oilEngine.latitude}"
					 alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'">
				</td>
		</tr>
		--%>
		<tr>
				<td class="label">
					责任人&nbsp;*
				</td>
				<td class="content">
					<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
							rootText='审核人' valueField="auditId_temp" handler="auditName_temp" textField="auditName_temp"
							checktype="user" single="true">
					</eoms:xbox>
					 <input type='text' id="auditName_temp" name="auditName_temp"  
								   readonly="true" value="${responseManName}"  
								   alt="allowBlank:false,vtext:'审批人不能为空！'" />
					<input  type="hidden"  name='responseMan' id="auditId_temp" value="${oilEngine.responseMan}"/>					</td>
				<td class="label">
					联系电话&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="telephone" id="telephone"  class="text medium"  value="${oilEngine.telephone}"	
					alt="allowBlank:false,vtext:'请输入联系电话'"/>
				</td>
		</tr>		
		<tr>
				<td class="label">
					归属站点&nbsp;*
				</td>
			<td class="content">
				<input class="text" type="text" name="siteName" readonly="true" 
					id="siteName" alt="allowBlank:true" value="${oilEngine.siteId}"/>
				<input type="hidden" name="siteId" id="siteId" value="" />
				 <input type="button" class="btn" value="选择" onclick="selectRes()" />
			</td>
				<td class="label">
					上报时间&nbsp;*
				</td>
				<td class="content">
		    <input type="text" class="text" name="reportTime" readonly="readonly" 
				id="reportTime" value="${oilEngine.reportTime}"
				onclick="popUpCalendar(this, this)" />   
	</td>
		</tr>		
		<tr>
				<td class="label">
					当前经度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="longitude" id="longitude"  class="text medium" 	value="${oilEngine.longitude}"	
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>
				</td>
				<td class="label">
					当前纬度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="latitude" id="latitude"  class="text medium" 	value="${oilEngine.latitude}"	
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>
				</td>
		</tr>		
		<tr>
				<td class="label">
					维护专业&nbsp;*
				</td>
			<td class="content">
				<eoms:comboBox  name="speciality" id="speciality" sub="resourceTeype" defaultValue="${oilEngine.specialty}"  
	        			initDicId="11225" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>			
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
						<textarea class="textarea max"  name="notes" id="notes" >${oilEngine.notes}</textarea>
				</td>
		</tr>
			<input type="hidden" name="id" value="${oilEngine.id }">
	</table>
	<input type="submit" value="保存"> 		
	<%--<input type="button" value="删除" onclick="removeoilEngine('${oilEngine.id }')"> 		
	--%><input type="button" value="返回" onclick="goBack()">
</form>
<script type="text/javascript">
function removeoilEngine(id){
	window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/oilEngine.do?method=delete&id="+id;
}
function goBack(){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/oilEngine.do?method=search";
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
