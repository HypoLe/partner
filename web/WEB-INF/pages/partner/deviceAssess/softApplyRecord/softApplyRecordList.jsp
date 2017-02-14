<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
        v = new eoms.form.Validation({form:'searchForm'});
        
        /*jq("#statusStringEqual").bind("change",function() {
        	jq("#searchForm").submit();
        });
       	jq("#statusStringEqual option[value='${statusStringEqual}']").attr("selected",true);
        */
});

function deleteInfo(id) {
			Ext.get("tabs-1").mask('正在删除，请稍候...');
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/partner/deviceAssess/softApplyRecord.do",
					params:{method:"remove",id:id},
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

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
	            }
    }
	//专业 联动 设备类型	
	function changeFacility(con){
		
		    delAllOption("equipmentType");
			var speciality = document.getElementById("speciality").value;
			var url="<%=request.getContextPath()%>/partner/deviceAssess/backEstimates.do?method=changeFacility&speciality="+speciality;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									var equipmentType = "equipmentType";
									var arrOptions = json[0].facility.split(",");
									var obj=$(equipmentType);
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									if(con==1){
										var equipmentType = '${softApplyRecord.equipmentType}';
										if(equipmentType!=''){
											document.getElementById("equipmentType").value = equipmentType;
										}	
									
									}	
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
	}
</script>

<div class="header center">
<b>联通内部处理的设备故障事件信息</b>
</div>
<br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"
	class="x-layout-panel-hd">
	<img src="${app}/images/icons/layout_add.png" align="absmiddle"
		style="cursor: pointer" />
	<span id="openQuery" style="cursor: pointer"
		onclick="openSearch(this);">快速查询</span>
</div>

<div id="listQueryObject"
	style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
	<form action="softApplyRecord.do?method=search" method="post"
		id="searchForm" name="searchForm">
		<fieldset>
			<legend>
				快速查询
			</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">
						升级事件名称:
					</td>
					<td>
						<input type="text" class="text" name="affairNameStringLike" alt="allowBlank:true"/>
					</td>
					<td class="label">
						状态:
					</td>
					<td>
						<select name="state"
							id="state" alt="allowBlank:true"
							class="select">
							<option value="">
								请选择
							</option>
							<option value="0">
								驳回
							</option>
							<option value="1">
								通过
							</option>
							<option value="2">
								待审批
							</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="label">
						厂家:
					</td>
					<td colspan="3">
						<eoms:comboBox name="factoryStringEqual" id="factoryStringEqual"  initDicId="1121502" styleClass="select max"
								alt="allowBlank:true,vtext:'请选择厂家...'" />
					</td>
				</tr>
				
				<tr>
					<td class="label">
							专业:
						</td>
						<td class="content">
							<eoms:comboBox name="specialityStringEqual" id="speciality"
								initDicId="11216"  styleClass="select max" alt="allowBlank:true,vtext:'请选择专业...'"
								onchange="changeFacility(0);" />
						</td>
					<td class="label">
						设备类型:
					</td>
					<td>
						<select name="equipmentTypeStringEqual" id="equipmentType" class="select max"
								alt="allowBlank:true,vtext:'请选择设备类型'">
								<option value="">
									请选择设备类型
								</option>
							</select>
					</td>
				</tr>

				<tr>
					<td class="label" colspan="4">
						升级申请时间：
					</td>
				</tr>
				<tr>
					<td class="label">
						从
					</td>
					<td>
						<input readonly="readonly" type="text" class="text"
							name="createTimeDateGreaterThan"
							onclick="popUpCalendar(this, this,null,null,null,true,-1);"
							alt="vtype:'lessThen',link:'finishTimeEnd',vtext:'开始时间不能晚于结束时间'"
							id="finishTimeStart" value="" />
					</td>
					<td class="label">
						到
					</td>
					<td>
						<input readonly="readonly" type="text" class="text"
							name="createTimeDateLessThan"
							onclick="popUpCalendar(this, this,null,null,null,true,-1);"
							alt="vtype:'moreThen',link:'finishTimeStart',vtext:'结束时间不能早于开始时间'"
							id="finishTimeEnd" value="" />
						<input type="hidden" name="reportTimeDateLessThan" value="" />
					</td>
				</tr>

				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有"
								onclick="window.location.href='${app}/partner/deviceAssess/softApplyRecord.do?method=search'" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<div id="tabs-1" name="tabs-1">
	<logic:notEmpty name="softApplyRecordList" scope="request">
		<display:table name="softApplyRecordList" cellspacing="0"
			cellpadding="0" id="softApplyRecordList" pagesize="${pageSize}"
			class="table softApplyRecordList" export="false"
			requestURI="${app}/partner/deviceAssess/softApplyRecord.do?method=search"
			sort="list" partialList="true" size="resultSize">

			<display:column sortable="true" media="html" headerClass="sortable"
				title="事件名称">
				<a
					href="${app}/partner/deviceAssess/softApplyRecord.do?method=goToDetail&id=${softApplyRecordList.id }"
					target="blank" shape="rect"> ${softApplyRecordList.affairName }
				</a>
			</display:column>

			<display:column property="createTime" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" headerClass="sortable"
				title="申请时间" />

			<display:column sortable="true" headerClass="sortable" title="厂家">
				<eoms:id2nameDB id="${softApplyRecordList.factory}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="专业">
				<eoms:id2nameDB id="${softApplyRecordList.speciality}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="设备类型">
				<eoms:id2nameDB id="${softApplyRecordList.equipmentType}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>

			<display:column property="licenseProblem" sortable="true"
				headerClass="sortable" title="许可证问题" />

			<display:column property="schemeProblem" sortable="true"
				headerClass="sortable" title="方案问题" />

			<display:column property="amount" sortable="true"
				headerClass="sortable" title="计数" />

			<display:column sortable="false" media="html" title="状态">
				<c:if test="${softApplyRecordList.deviceAssessApprove.state=='0'}">
			驳回
		</c:if>
				<c:if test="${softApplyRecordList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
				<c:if test="${softApplyRecordList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
			</display:column>
			<display:column sortable="false" media="html" title="操作">
				<c:if test="${softApplyRecordList.deviceAssessApprove.state=='0'}">
					<a
						href="${app}/partner/deviceAssess/softApplyRecord.do?method=edit&id=${softApplyRecordList.id }"
						target="blank" shape="rect"> 编辑并提交 </a>
				</c:if>
				<c:if test="${softApplyRecordList.deviceAssessApprove.state=='1'}">
					<a
						href="${app}/partner/deviceAssess/softApplyRecord.do?method=goToDetail&id=${softApplyRecordList.id }"
						target="blank" shape="rect"> 详情查看 </a>
				</c:if>
				<c:if test="${softApplyRecordList.deviceAssessApprove.state=='2'}">
					<a
						href="${app}/partner/deviceAssess/softApplyRecord.do?method=goToDetail&id=${softApplyRecordList.id }"
						target="blank" shape="rect"> 详情查看 </a>
				</c:if>
			</display:column>
			<display:column sortable="false" media="html" title="详情">
				<a
					href="${app}/partner/deviceAssess/softApplyRecord.do?method=goToDetail&id=${softApplyRecordList.id }"
					target="blank" shape="rect"> 查看 </a>
			</display:column>
			<display:column sortable="false" title="删除">
				<c:if test="${softApplyRecordList.deviceAssessApprove.state!='2'}">
					<a href="javascript:void(0)"
						onclick="deleteInfo('${softApplyRecordList.id}')"> <img
							class="delete" src="${app }/images/icons/icon.gif" /> </a>
				</c:if>
				<c:if test="${softApplyRecordList.deviceAssessApprove.state=='2'}">
				已提交，待审批中！
		</c:if>
			</display:column>

			<display:setProperty name="paging.banner.item_name"
				value="insideDispose" />
			<display:setProperty name="paging.banner.items_name"
				value="insideDisposes" />
		</display:table>

		<c:out value="${buttons}" escapeXml="false" />
	</logic:notEmpty>
</div>
<logic:empty name="softApplyRecordList" scope="request">
	没有数据！
</logic:empty>
<%@ include file="/common/footer_eoms.jsp"%>