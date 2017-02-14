<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/partner/baseinfo/pnrSourceConfig.do",
					params:{method:"deletePnrSourceStandardConfig",id:id},
					success:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					},
					failure:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			}
}
</script>

<script type="text/javascript">
function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
function res(){
		var formElement=document.getElementById("select_from")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var areaid=document.getElementById('areaIdStringEqual');
	   	 var companyid=document.getElementById('companyIdStringEqual');
	   	 areaid.value="";
	   	 companyid.value=""
	   	 var selects =formElement.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value ='';
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
	}
</script>
<div align="center"><b>巡检资源配置标准列表</b></div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA" class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/partner/baseinfo/pnrSourceConfig.do?method=gotoPnrSourceStandardConfigListPage" method="post" id="select_from">
			<table id="sheet" class="listTable">
				<tr>
						<td class="label">
						 	区域
						</td>
						<td class="content" >
							<input type="text" name="areaName" id="areaName"  value="${areaName}"  class="text medium"  readonly="readonly"/>
							<input type="hidden" name="areaIdStringEqual" id="areaIdStringEqual"    value="${areaIdStringEqual}" class="text medium"/>
							<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
							rootText='所属区域' valueField="areaIdStringEqual" handler="areaName" textField="areaName" 	checktype="" single="true">
							</eoms:xbox>
						</td>
						<td class="label">
						 	巡检单位
						</td>
						<td class="content" >
							<input type="text" name="companyNameStringEqual"  value="${companyNameStringEqual}" class="text max"  readonly="readonly"    id="companyNameStringEqual" />
							<input type="hidden" name="companyIdStringEqual" id="companyIdStringEqual" value="${companyIdStringEqual}" maxLength="32" 	class="text medium" />
							<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf" rootId=""
							rootText='巡检单位组织' valueField="companyIdStringEqual" handler="companyNameStringEqual" textField="companyNameStringEqual"  checktype="dept" single="true">
							</eoms:xbox>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	巡检专业
						</td>
						<td class="content" >
							<eoms:comboBox id="professionalStringEqual" name="professionalStringEqual" initDicId="11225" defaultValue="${professionalStringEqual}" styleClass="input select"   />
						</td>
						<td class="label">
						 	配置类型
						</td>
						<td class="content" >
								<eoms:comboBox id="configTypeStringEqual" name="configTypeStringEqual" initDicId="12402" defaultValue="${configTypeStringEqual}" styleClass="input select"   />
						</td>
				</tr>
				</table>
				<table>
				<tr>
					<td colspan="4">
							<input type="submit" class="btn" value="查询" />&nbsp;
							<input type="button" class="btn" value="重置" onclick="res();"/>
						</div>
					</td>
				</tr>
			</table>
	</form>
</div>
<logic:present name="pnrSourceStandardConfigList" scope="request">
	<display:table id="pnrSourceStandardConfigList" name="pnrSourceStandardConfigList" pagesize="${pagesize}" size="${size}"
					requestURI="${app}/partner/baseinfo/pnrSourceConfig.do?method=gotoPnrSourceStandardConfigListPage" 
					export="false" sort="list" cellspacing="0" cellpadding="0" class="table pnrSourceStandardConfigList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="年">
						${pnrSourceStandardConfigList.addTimeY}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="月">
						${pnrSourceStandardConfigList.addTimeM}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="区域">
						<eoms:id2nameDB id="${pnrSourceStandardConfigList.areaId}" beanId="tawSystemAreaDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="巡检单位" >
						${pnrSourceStandardConfigList.companyName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="巡检专业" >
						<eoms:id2nameDB id="${pnrSourceStandardConfigList.professional}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="配置类型">
						<eoms:id2nameDB id="${pnrSourceStandardConfigList.configType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="配置要求">
						${pnrSourceStandardConfigList.standardConfig}${pnrSourceStandardConfigList.configDw}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="资源点数量">
						${pnrSourceStandardConfigList.sourceAmount}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="应配数量">
						${pnrSourceStandardConfigList.standardAmout}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="实配数量">
						${pnrSourceStandardConfigList.actualConfig}
				</display:column>
				<display:column sortable="false" headerClass="sortable" title="详情" media="html">
					<a href="${app}/partner/baseinfo/pnrSourceConfig.do?method=gotoPnrSourceStandardConfigDetailPage&id=${pnrSourceStandardConfigList.id}" target="blank" shape="rect">
						<img src="${app}/images/icons/table.gif">
					</a>
				</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
