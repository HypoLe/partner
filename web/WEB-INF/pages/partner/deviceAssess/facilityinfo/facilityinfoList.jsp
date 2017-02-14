<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
        /*v = new eoms.form.Validation({form:'searchForm'});
        
        jq("#statusStringEqual").bind("change",function() {
        	jq("#searchForm").submit();
        });
       	jq("#statusStringEqual option[value='${statusStringEqual}']").attr("selected",true);
        */
        
});

function deleteInfo(id) {
			Ext.get("tabs-1").mask('正在删除，请稍候...');
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/partner/deviceAssess/facilityinfos.do",
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

</script>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/facilityinfos.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>
<div id="tabs-1" name="tabs-1">
<logic:notEmpty name="facilityinfoList" scope="request">
<content tag="heading">
厂家设备问题事件信息
</content>
	<display:table name="facilityinfoList" cellspacing="0" cellpadding="0"
		id="facilityinfoList" pagesize="${pageSize}" class="table facilityinfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/facilityinfos.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<display:column  sortable="false" title="问题名称" media="html">
		<a  href="${app}/partner/deviceAssess/facilityinfos.do?method=goToDetail&id=${facilityinfoList.id }"
			target="blank" shape="rect">
			${facilityinfoList.issueName }
		</a> 
	</display:column>

	<display:column sortable="true"
			headerClass="sortable" title="问题类别" >
		<eoms:id2nameDB id="${facilityinfoList.issueType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" title="级别" >
		<eoms:id2nameDB id="${facilityinfoList.facilityLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>

	<display:column property="province" sortable="true"
			headerClass="sortable" title="发生省份" />

	<display:column property="city" sortable="true"
			headerClass="sortable" title="发生地市" />

	<display:column sortable="true"
			headerClass="sortable" title="厂家" >
			<eoms:id2nameDB id="${facilityinfoList.factory}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" title="专业" >
			<eoms:id2nameDB id="${facilityinfoList.speciality}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" title="设备类型" >
			<eoms:id2nameDB id="${facilityinfoList.equipmentType}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column property="equipmentName" sortable="true"
			headerClass="sortable" title="设备名称" />

	<display:column property="equipmentVersion" sortable="true"
			headerClass="sortable" title="设备版本" />

	<display:column property="occurTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="发生时间" />

	<display:column sortable="true"
			headerClass="sortable" title="状态">
			<eoms:id2nameDB id="${facilityinfoList.state}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	
	<display:column property="solveTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="解决时间" />

	<display:column property="total" sortable="true"
			headerClass="sortable" title="计数" />

	<display:column sortable="false" media="html" title="状态">
		<c:if test="${facilityinfoList.deviceAssessApprove.state=='0'}">
			驳回
		</c:if>
		<c:if test="${facilityinfoList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
		<c:if test="${facilityinfoList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
	</display:column>
	<display:column sortable="false" media="html" title="操作">
		<c:if test="${facilityinfoList.deviceAssessApprove.state=='0'}">
			<a  href="${app}/partner/deviceAssess/facilityinfos.do?method=edit&id=${facilityinfoList.id }"
				 shape="rect">
				编辑并提交
			</a> 
		</c:if>
		<c:if test="${facilityinfoList.deviceAssessApprove.state=='1'}">
			<a  href="${app}/partner/deviceAssess/facilityinfos.do?method=goToDetail&id=${facilityinfoList.id }"
				target="blank" shape="rect">
				详情查看
			</a> 
		</c:if>
		<c:if test="${facilityinfoList.deviceAssessApprove.state=='2'}">
			<a  href="${app}/partner/deviceAssess/facilityinfos.do?method=goToDetail&id=${facilityinfoList.id }"
				target="blank" shape="rect">
				详情查看
			</a> 
		</c:if>
	</display:column>
	<display:column sortable="false" title="删除">
		<c:if test="${facilityinfoList.deviceAssessApprove.state!='2'}">
			<a href="javascript:void(0)"
					onclick="deleteInfo('${facilityinfoList.id}')"> <img
						class="delete" src="${app }/images/icons/icon.gif" /> </a>
		</c:if>
		<c:if test="${facilityinfoList.deviceAssessApprove.state=='2'}">
				已提交，待审批中！
		</c:if>
	</display:column>
	
		<display:setProperty name="paging.banner.item_name" value="facilityinfo" />
		<display:setProperty name="paging.banner.items_name" value="facilityinfos" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
</logic:notEmpty>
</div>
<logic:empty name="facilityinfoList" scope="request">
	没有数据！
</logic:empty>
<%@ include file="/common/footer_eoms.jsp"%>