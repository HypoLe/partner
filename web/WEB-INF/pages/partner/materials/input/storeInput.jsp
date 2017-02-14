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
		<form action="storeInput.do?method=selectBy" method="post">
			
		<p><center><span style="font-size:20px">入库管理</span></center></p>
			<font color="red">${message}</font>
			<br/>
			<table class="formTable">
				<tr>
					<td class="label">
						相关仓库
					</td>
					<td>
						<select name="storeSid">
							<option value="">
								选择仓库
							</option>
							<c:forEach var="store" items="${storeList}" >
							<option value="${store.id}">
								${store.name}
							</option>
							</c:forEach>
						</select>
					</td>
					<td class="label">
						开单日期
					</td>
					<td>
						<input type="text" class="text" id="start" name="start" readonly="readonly"
							value=""
							onclick="popUpCalendar(this, this,null,null,null,false,-1);"
							alt="vtype:'lessThen',link:'end',vtext:'开始时间不能晚于结束时间',allowBlank:true" />
						~
						<input type="text" class="text" id="end" name="end" readonly="readonly"
							 value=""
							onclick="popUpCalendar(this, this,null,null,null,false,-1);"
							alt="vtype:'moreThen',link:'start',vtext:'结束时间不能早于开始时间',allowBlank:true" />
						<!-- <input type="text" class="text" name="${sheetPageName}mainPosetime" readonly="readonly" 
					            	id="${sheetPageName}mainPosetime" value="${sheetMain.mainPosetime}" 
					            	onclick="popUpCalendar(this, this)" alt="allowBlank:false,vtext:'请输入需求提出时间'"/>-->
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
				<tr>
					<td class="label">
						往来单位
					</td>
					<td>
						<input type="text" name="storeCompany">
					</td>
					<td class="label">
						备注
					</td>
					<td>
						<input type="text" name="storeRemark">
					</td>
				</tr>
			</table>
			<br />
			<input type="submit" value="查询">

			<input type="button" style="margin-right: 5px"
				onclick="location.href='<c:url value="/materials/storeInput.do?method=showAddStoreInputView"/>'"
				value="新增入库单" />
		</form>
		<br />
<div style="border-top:1px solid #000;width:100%;height:1px;"> </div>
<br />
		<display:table name="list" cellspacing="0" cellpadding="0" id="list"
			sort="list" class="table" pagesize="${pageSize}" export="true"
			requestURI="${app}/materials/storeInput.do?method=view"
			partialList="true" size="total">

			<display:column sortable="true" headerClass="sortable" title="操作"
				media="html">
				
			<!-- 管理员权限 -->
			<c:if test="${ '3' == mateper}">
				<a
					href="${app}/materials/storeInput.do?method=showAlterView&id=${list.id}" >修改</a>
				
				<a
					href="${app}/materials/storeInput.do?method=delete&id=${list.id}"
					onClick="if(confirm('确定要删除问题吗？'))return true;return false;">删除</a>
				<a
					href="${app}/materials/storeInput.do?method=detail&id=${list.id}">详情</a>
				<c:if test="${list.storeExamineStatus=='未审核'}">
				<a
					href="${app}/materials/storeInput.do?method=goAuditPage&id=${list.id}">审核</a>
				</c:if>
			</c:if>
			<!-- 财务人员 -->
			<c:if test="${ '2' == mateper}">
				<a
					href="${app}/materials/storeInput.do?method=showAlterView&id=${list.id}" >详情</a>
				
				<c:if test="${list.storeExamineStatus=='未审核'}">
					<a
						href="${app}/materials/storeInput.do?method=goAuditPage&id=${list.id}">审核</a>
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
					href="${app}/materials/storeInput.do?method=showAlterView&id=${list.id}" >修改</a>
				</c:if>
				<a
					href="${app}/materials/storeInput.do?method=delete&id=${list.id}"
					onClick="if(confirm('确定要删除吗？'))return true;return false;">删除</a>
				<a
					href="${app}/materials/storeInput.do?method=detail&id=${list.id}">详情</a>
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
				headerClass="sortable" title="仓库名称" />
			<display:column property="storeCompany" sortable="true"
				headerClass="sortable" title="往来单位" />
			<display:column property="storeDepartment" sortable="true"
				headerClass="sortable" title="部门" />
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