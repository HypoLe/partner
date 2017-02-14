<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
</script>

<script type="text/javascript">
function openImport(){
	var handler = document.getElementById("openQuery");
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

<div align="center">
	查询页面
</div><br><br/>

<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery">查询</span>
</div>


<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;display:block">
	<form action="${app}/netresource/synchExceRecord.do?method=inspectFeeDetailSearch" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">
					 	年度
					</td>
					<td class="content" >
							<select name="s_year" class="input select">
								<option value="" selected="selected">请选择...</option>
								<c:forEach begin="2000" end="2020" var="year">
									<c:choose>
										<c:when test="${year eq s_year}">
											<option value="${year }" selected="selected">${year }年</option>
										</c:when>
										<c:otherwise>
											<option value="${year }">${year }年</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
					</td>
					<td class="label">
					 	月份
					</td>
					<td class="content" >
						<select name="s_mon" class="input select">
							<option value="" selected="selected">请选择...</option>
							<c:forEach begin="1" end="12" var="mon">
									<c:choose>
										<c:when test="${mon eq s_mon}">
											<c:if test="${mon < 10}">
												<option value="0${mon }" selected="selected">${mon }月</option>
											</c:if>
											<c:if test="${mon > 9}">
												<option value="${mon }" selected="selected">${mon }月</option>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${mon < 10}">
												<option value="0${mon }">${mon }月</option>
											</c:if>
											<c:if test="${mon > 9}">
												<option value="${mon }">${mon }月</option>
											</c:if>
										</c:otherwise>
									</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">
					 	专业
					</td>
					<td class="content" >
							<input class="text" type="text" name="special_name" value="${special_name}" />
					</td>
					<td class="label">
					 	地区代维公司名
					</td>
					<td class="content" >
						<input class="text" type="text" name="region_m_name" value="${region_m_name}" />
					</td>
				</tr>
				<tr>
					<td class="label">
					 	区县维护中心名
					</td>
					<td class="content" colspan="3">
							<input class="text" type="text" name="city_m_name" value="${city_m_name}" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/synchExceRecord.do?method=inspectFeeDetailSearch'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
<logic:present name="inspectFeeDetailList" scope="request">
	<display:table id="inspectFeeDetailList"
					name="inspectFeeDetailList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table inspectFeeDetailList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="省代维公司名">
						${inspectFeeDetailList['province_m_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="地区代维公司名">
						${inspectFeeDetailList['region_m_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="区县维护中心名">
						${inspectFeeDetailList['city_m_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="年度">
						${inspectFeeDetailList['s_year']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="月份">
						${inspectFeeDetailList['s_mon']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="专业">
						${inspectFeeDetailList['special_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="计划完成巡检的站点数">
						${inspectFeeDetailList['should_num']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="实际完成巡检的站点数">
						${inspectFeeDetailList['complete_num']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
