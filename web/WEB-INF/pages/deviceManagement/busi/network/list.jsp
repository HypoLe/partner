<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

var jq=jQuery.noConflict();
Ext.onReady(function(){
});
 
function deleteInfo(id) {
			Ext.get("tabs-1").mask('正在删除，请稍候...');
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/hiddenTrouble/hiddenTrouble.do",
					params:{method:"delete",id:id},
					success:function(res,opt) {
						Ext.get("tabs-1").unmask();
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					},
					failure:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			} else {
						Ext.get("tabs-1").unmask();
			}
}

function openSearch(handler){
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
</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/layout_add.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openSearch(this);">快速查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="hiddenTrouble.do?method=list" method="post" id="searchForm" name="searchForm">
			<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">隐患类型:</td>
					<td colspan="3">
						<eoms:comboBox name="hiddenTroubleTypeStringEqual" defaultValue="${hiddenTrouble.hiddenTroubleType }"
							id="hiddenTroubleType" initDicId="1200201" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
				<tr>
					<td class="label">专业类型:</td>
					<td>
						<eoms:comboBox name="majorTypeStringEqual"
							id="majorTypeStringEqual" initDicId="11216" alt="allowBlank:false" styleClass="select" />
					</td>
					<td class="label">是否重要隐患:</td>
					<td>
						<eoms:comboBox name="isImportantStringEqual"
							id="isImportantStringEqual" initDicId="10301" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
			
				<tr>
					<td class="label">
						地区:
					</td>
					<td >
						<input class="text" name="textArea" id="textArea" type="text"  readonly="true"  alt="allowBlank:false" /> 
						<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
						<input type="hidden" name="areaIdStringEqual" id="areaIdStringEqual" />
						<eoms:xbox id="tree1" dataUrl="${app}/partner/baseinfo/xtree.do?method=area" 
			 				 rootId="10" 
			  				 rootText='黑龙江' 
			  				 valueField="areaIdStringEqual"
			  				 handler="areatree"
			  				 textField="textArea"
			 				 checktype="area" 
			 				 single="true"></eoms:xbox>
					</td>
					
					<td class="label">
						状态:
					</td>
					<td >
						<select name="processStatusStringEqual" id="processStatusStringEqual" alt="allowBlank:true" class="select">
							<option value="">请选择</option>
							<option value="0">未处理</option>
							<option value="1">已处理</option>
				</select>
					</td>
				</tr>
				
				<tr >
					<td class="label" colspan="4">上报时间：</td>
				</tr>
				<tr >
					<td class="label">从</td><td >
						<input readonly="readonly" type="text" class="text" name="reportTimeDateGreaterThan"
							onclick="popUpCalendar(this, this,null,null,null,true,-1);"
							alt="vtype:'lessThen',link:'finishTimeEnd',vtext:'开始时间不能晚于结束时间'"
							id="finishTimeStart" value=""/>
					</td>
					<td class="label">到</td>
					<td >
						<input readonly="readonly" type="text" class="text" name="reportTimeDateLessThan"
							onclick= "popUpCalendar(this, this,null,null,null,true,-1);"
                  	      	alt="vtype:'moreThen',link:'finishTimeStart',vtext:'结束时间不能早于开始时间'"
							id="finishTimeEnd" value=""/>
						<input type="hidden" name="reportTimeDateLessThan" value=""/>
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/hiddenTrouble/hiddenTrouble.do?method=list'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
<br/>
<br/>
<%--
	private String id; // 主键
	private String reportUser; //上报人，就是当前填写隐患信息的登录人员
	private String reportTime; //上报时间
	private String areaId;//地区
	private String hiddenTroubleType;//隐患类型
	private String isImportant;//是否重要隐患（0：否，1：是）
	private String majorType;//专业类型
	private String checkPoint;//巡检点
	private double longitude;//经度
	private double latitude;//纬度
	private String checkUser;//上报巡检员 pnr_user
	private String checkUserDept;//代维公司 （上报人所属代维公司）pnr_dept
	private String phone;//联系电话
	private String email;//email信息
	
	private String processStatus;//处理状态
	private String processUser;//处理人
	private String handlTime;//处理时间
	private String handleMsg;//处理信息
	
	private String deleted;//删除标记：0表示未删除，1表示逻辑删除
	private String deletedTime;//删除时间
	private String remark;//备注
--%>
<div id="tabs-1">
	</form>
	<!-- Information hints area end-->
	<logic:present name="hiddenTroubleList" scope="request">
		<display:table name="hiddenTroubleList" cellspacing="0" cellpadding="0"
			id="hiddenTroubleList" pagesize="${pagesize}"
			class="table hiddenTroubleList" export="true" requestURI="hiddenTrouble.do"
			sort="list" partialList="true" size="${size}">
			<display:column sortable="true" headerClass="sortable" title="地区">
				<eoms:id2nameDB id="${hiddenTroubleList.areaId}"	beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="专业类型">
				<eoms:id2nameDB id="${hiddenTroubleList.majorType}"
					beanId="IItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="上报隐患类型">
				<eoms:id2nameDB id="${hiddenTroubleList.hiddenTroubleType}"
					beanId="IItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="是否重要隐患">
				<eoms:id2nameDB id="${hiddenTroubleList.isImportant}"
					beanId="IItawSystemDictTypeDao" />
			</display:column>
				<display:column sortable="true" headerClass="sortable" title="上报时间">
				${hiddenTroubleList.reportTime}
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="上报巡检员">
				<%--<eoms:id2nameDB id="${hiddenTroubleList.checkUser}"
					beanId="partnerUserDao" />
				--%>
				<eoms:id2nameDB id="${hiddenTroubleList.checkUser}" beanId="tawSystemUserDao" /> 
			</display:column>
				<display:column sortable="true" headerClass="sortable" title="巡检点">
				<eoms:id2nameDB id="${hiddenTroubleList.checkPoint}"
					beanId="checkPointDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="处理状态">
				<c:if test="${hiddenTroubleList.processStatus == '0'}">
					未处理
				</c:if>
				<c:if test="${hiddenTroubleList.processStatus == '1'}">
					已处理
				</c:if>
			</display:column>
			<%--<display:column sortable="false" headerClass="sortable" title="编辑" media="html">
				<c:if test="${hiddenTroubleList.processStatus == '0'}">
					<a href="${app }/hiddenTrouble/hiddenTrouble.do?method=goToEdit&id=${hiddenTroubleList.id}"
						><img src="${app }/images/icons/edit.gif"></a>
				</c:if>
				<c:if test="${hiddenTroubleList.processStatus == '1'}">
					已处理，不能编辑！
				</c:if>
			</display:column>--%>

			<display:column sortable="false" headerClass="sortable" title="详情"
				media="html">
				<a id="${hiddenTroubleList.id }"
					href="${app }/hiddenTrouble/hiddenTrouble.do?method=goToDetail&id=${hiddenTroubleList.id}"
					target="blank" shape="rect"> <img
						src="${app }/images/icons/table.gif"> </a>
			</display:column>

			<display:column sortable="false" headerClass="sortable" title="删除"
				media="html">
				<a href="javascript:void(0)"
					onclick="deleteInfo('${hiddenTroubleList.id}')"> <img
						class="delete" src="${app }/images/icons/icon.gif" /> </a>
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
