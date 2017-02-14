<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page contentType="text/html;charset=utf-8"%>

 <script type="text/javascript" >
	var message='${message}';
	if(null!=message && ""!=message ){
		alert(message);
	}
	//alert(aa);
</script>

<html>

	<body>
		<form action="storeAllot.do?method=selectBy" method="post">
			
		<p><center><span style="font-size:20px">调拨管理</span></center></p>
			<font color="red">${message}</font>
			<br/>
			<table class="formTable">
		     	<tr>
					<td class="label" >
						开单日期
					</td>
					<td colspan="3">
						<input type="text" class="text" name="start" readonly="readonly"
							id="" value=""
							onclick="popUpCalendar(this, this,null,null,null,false,-1);"
							alt="allowBlank:false,vtext:'请输入需求提出时间'" />
						~
						<input type="text" class="text" name="end" readonly="readonly"
							id="" value=""
							onclick="popUpCalendar(this, this,null,null,null,false,-1);"
							alt="allowBlank:false,vtext:'请输入需求提出时间'" />
						<!-- <input type="text" class="text" name="${sheetPageName}mainPosetime" readonly="readonly" 
					            	id="${sheetPageName}mainPosetime" value="${sheetMain.mainPosetime}" 
					            	onclick="popUpCalendar(this, this)" alt="allowBlank:false,vtext:'请输入需求提出时间'"/>-->
					</td>
				</tr>
				<tr>
					<td class="label">
						调出仓库
					</td>
					<td>
						<select name="storeSname">
							<option value="">
								选择仓库
							</option>
							<c:forEach var="store" items="${storeList}" >
							<option value="${store.name}">
								${store.name}
							</option>
							</c:forEach>
						</select>
					</td>
					
					<td class="label">
						调入仓库
					</td>
					<td>
						<select name="inputStoreName">
							<option value="">
								选择仓库
							</option>
							<c:forEach var="store" items="${storeList}" >
							<option value="${store.name}">
								${store.name}
							</option>
							</c:forEach>
						</select>
					</td>
					
					</tr>
					
					
					
				<tr>
					<td class="label">
						单号
					</td>
					<td>
						<input type="text" name="storeNum">
					</td>
					<td class="label">
						原始单号
					</td>
					<td>
						<input type="text" name="storeOriginalNum">
					</td>
				</tr>
				<tr>
					<td class="label">
						经办人
					</td>
					<td>
						<input type="text" name="storeRequisitioner">
					</td>
					<td class="label">
						制单人
					</td>
					<td>
						<input type="text" name="storeMakeBillPeople">
					</td>
				</tr>

			</table>
			<br />
			<input type="submit" value="查询">

			<input type="button" style="margin-right: 5px"
				onclick="location.href='<c:url value="/materials/storeAllot.do?method=showAddStoreAllotView"/>'"
				value="新增调拨单" />
		</form>
		<br />
			<div style="border-top:1px solid #000;width:100%;height:1px;"> </div>
		<br />
		<display:table name="list" cellspacing="0" cellpadding="0" id="list"
			sort="list" class="table" pagesize="${pageSize}" export="true"
			requestURI="${app}/materials/storeAllot.do?method=view"
			partialList="true" size="total">

			<display:column sortable="true" headerClass="sortable" title="操作"
				media="html">
				<!-- <html:link
					href="${app}/materials/storeAllot.do?method=showAlterView"
					paramId="id" paramProperty="id" paramName="list">
						修改
					</html:link>
				<html:link href="${app}/materials/storeAllot.do?method=delete"
					paramId="id" paramProperty="id" paramName="list">
						删除
					</html:link>
				<html:link href="${app}/materials/storeAllot.do?method=detail"
					paramId="id" paramProperty="id" paramName="list">
						详情
					</html:link>
				<html:link href="${app}/materials/storeAllot.do?method=goAuditPage"
					paramId="id" paramProperty="id" paramName="list">
						审核
					</html:link>
				 	<html:link href="${app}/materials/storeAllot.do?method=goDeAuditPage"
					paramId="id" paramProperty="id" paramName="list">
						反审
					</html:link> -->
					
						
			<!-- 管理员权限 -->
			<c:if test="${ '3' == mateper}">
				<a
					href="${app}/materials/storeAllot.do?method=showAlterView&id=${list.id}" >修改</a>
				
				<a
					href="${app}/materials/storeAllot.do?method=delete&id=${list.id}"
					onClick="if(confirm('确定要删除问题吗？'))return true;return false;">删除</a>
				<a
					href="${app}/materials/storeAllot.do?method=detail&id=${list.id}">详情</a>
				<c:if test="${list.storeExamineStatus=='未审核'}">
				<a
					href="${app}/materials/storeAllot.do?method=goAuditPage&id=${list.id}">审核</a>
				</c:if>
			</c:if>
			<!-- 财务人员 -->
			<c:if test="${ '2' == mateper}">
				<a
					href="${app}/materials/storeAllot.do?method=detail&id=${list.id}" >详情</a>
				
				<c:if test="${list.storeExamineStatus=='未审核'}">
					<a
						href="${app}/materials/storeAllot.do?method=goAuditPage&id=${list.id}">审核</a>
				</c:if>
				<!--<html:link href="${app}/materials/storeInput.do?method=goDeAuditPage"
					paramId="id" paramProperty="id" paramName="list">
						反审
				</html:link>-->
			</c:if>
			
			<!-- 仓库管理人 -->
			<c:if test="${ '1' == mateper}">
				<c:if test="${list.storeExamineStatus=='未审核'}">
				<a
					href="${app}/materials/storeAllot.do?method=showAlterView&id=${list.id}" >修改</a>
				</c:if>
				<a
					href="${app}/materials/storeAllot.do?method=delete&id=${list.id}"
					onClick="if(confirm('确定要删除吗？'))return true;return false;">删除</a>
				<a
					href="${app}/materials/storeAllot.do?method=detail&id=${list.id}">详情</a>
			</c:if>
			</display:column>
			<display:column property="storeNum" sortable="true"
				headerClass="sortable" title="单号" />
			<display:column property="storeOriginalNum" sortable="true"
				headerClass="sortable" title="原始单号" />

			<display:column property="storeRequisitioner" sortable="true"
				headerClass="sortable" title="经办人" />
			<display:column property="storeExamineStatus" sortable="true"
				headerClass="sortable" title="审核状态" />
			<display:column property="storeExaminePeople" sortable="true"
				headerClass="sortable" title="审核人" />
			<display:column property="storeMakeBillDate" sortable="true"
				headerClass="sortable" title="制单日期" format="{0,date,yyyy-MM-dd }" />
			<display:column property="storeBillingDate" sortable="true"
				headerClass="sortable" title="开单日期" format="{0,date,yyyy-MM-dd }" />
			<display:column property="storeMakeBillPeople" sortable="true"
				headerClass="sortable" title="制单人" />
			<display:column property="storeSname" sortable="true"
				headerClass="sortable" title="调出仓库" />
			<display:column property="inputStoreName" sortable="true"
				headerClass="sortable" title="调入仓库" />
			<display:column property="storeBillType" sortable="true"
				headerClass="sortable" title="单据类型" />

			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
			<display:setProperty name="export.rtf" value="false" />
		</display:table>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>