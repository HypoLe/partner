<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
var v;
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   });
function pictureId(id){
		
	   var url = "${app}/activiti/android/androidWorkOrderAction.do?method=showPicture&id="+id;
       
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";

		window.showModalDialog(url,window,sFeatures);

}


</script>  

	<form action="${app}/activiti/android/androidWorkOrderAction.do?method=gotoDetail" method="post" name="theform">
		<fieldset>
			
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	故障地点
						</td>
						<td class="content">
								<input type="text" class="text" name="faultLocation" value="${faultLocation}"/>
						</td>
						<td class="label">
						 	故障描述
						</td>
						<td class="content">
								<input type="text"  class="text" name="faultDescription" value="${faultDescription}"/>
						</td>
						</tr>
						<tr>
						<td class="label">
						 	故障开始时间
						</td>
						<td class="content" >
								<input type="text" class="text" name="beginTime" readonly="readonly" id="beginTime" value="${beginTime}" 
				onclick="popUpCalendar(this, this,null,null,null,null,-1)" alt="vtype:'lessThen',link:'endTime',vtext:'故障开始时间不能晚于故障结束时间',allowBlank:true"/>
						</td>
						<td class="label">
						 	故障结束时间
						</td>
						<td class="content" >
								<input type="text" class="text" name="endTime" readonly="readonly" id="endTime" value="${endTime}" 
				onclick="popUpCalendar(this, this,null,null,null,null,-1)" alt="vtype:'moreThen',link:'beginTime',vtext:'故障开始时间不能早于故障结束时间',allowBlank:true"/>
						</td>
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<div style="height:10px;"></div>
	<display:table name="pnrAndroidPhotoFileList" cellspacing="0" cellpadding="0"
		id="pnrAndroidPhotoFileList" pagesize="${pageSize}" class="table businessdesignMain"
		export="flase" requestURI="androidWorkOrderAction.do"
		sort="list" size="listsize" partialList="true">
			<display:column  sortable="true"
			headerClass="sortable" title="序号">
			${pnrAndroidPhotoFileList_rowNum}
			</display:column>
			<display:column property="faultLocation" sortable="true"
			headerClass="sortable" title="故障地点"/>
			<display:column property="faultDescription" sortable="true"
			headerClass="sortable" title="故障描述"/>
			<display:column property="longitude" sortable="true"
			headerClass="sortable" title="经度"/>
			<display:column property="latitude" sortable="true"
			headerClass="sortable" title="纬度"/>
			<display:column sortable="true"
			headerClass="sortable" title="处理人">
			<eoms:id2nameDB id="${pnrAndroidPhotoFileList.userId}" beanId="tawSystemUserDao"/> 
			</display:column>
			<display:column  sortable="true"
			headerClass="sortable" title="处理人所在的班">
			<eoms:id2nameDB id="${pnrAndroidPhotoFileList.deptId}" beanId="tawSystemDeptDao"/>
			</display:column>
			<display:column property="createTime" sortable="true"
			headerClass="sortable" title="创建时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
		    <display:column sortable="true" headerClass="sortable" title="图片查看">			
					 		<input type="button" value="查看"  onclick="pictureId('${pnrAndroidPhotoFileList.id}')" class="btn"/>
			</display:column>
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>

	</display:table>

<%@ include file="/common/footer_eoms.jsp"%>