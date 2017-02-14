<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

	<%--
		modify by mios:
		mark有4个状态： 0：半行， 1：整行， 2：左半行，右为空行， 3：右半行，左为空行,通过mark控制是否输出空列
		flag用来控制是否换行，每输出两列后，flag+1 
	--%>
	<tr>
	<c:set var="flag" value="${0}" />
	<c:forEach items="${CtTableColumnList}" var="item" varStatus="status">
		<c:if test="${item.mark == 3}">
			<%-- 有右半行，填充左半行为空行--%>
			<td class="label"></td>
			<td class="content" role="4"></td>
			<c:set var="flag" value="${flag+1}" />
		</c:if>

		<c:if test="${item.isOpen == 1}">
			<td class="label">
				${item.colChname}<c:if test="${item.isNullable == 0}"><font color='red'>*</font></c:if>
			</td>
			<td class="content" <c:if test="${item.mark == 1}">colspan="3"</c:if> role="${item.mark}">
				<c:choose>
					<c:when test="${item.colDictType == 0}">
						<c:choose>
							<c:when test="${item.colType == 1}">
								<%-- 不绑定_普通文本 --%>
								<input type="text" class="text medium"
									name="TableInfo/${item.colName}" 
									id="${item.colName}" 
									value="${item.colDefault}"
									maxLength="${item.colSize}" 
									alt="allowBlank:<c:if test="${item.isNullable == 1}">true</c:if><c:if test="${item.isNullable == 0}">false</c:if>,vtext:'${item.colChname}'" />
							</c:when>
							<c:when test="${item.colType == 2}">
								<%-- 不绑定_大文本域 --%>
								<textarea type="_moz" class="textarea max" 
									name="TableInfo/${item.colName}"
									id="${item.colName}"
									onkeyup="this.value=this.value.slice(0, 1800)" cols="50" 
									alt="allowBlank:<c:if test="${item.isNullable == 1}">true</c:if><c:if test="${item.isNullable == 0}">false</c:if>,vtext:'请输入${item.colChname}...'">${item.colDefault}</textarea>
							</c:when>
							<c:when test="${item.colType == 3}">
								<%-- 不绑定_数字类型 --%>
								<input type="text" class="text medium"
									name="TableInfo/${item.colName}"
									id="${item.colName}" 
									value="${item.colDefault}"
									alt="allowBlank:<c:if test="${item.isNullable == 1}">true</c:if><c:if test="${item.isNullable == 0}">false</c:if>,vtext:'请输入${item.colChname}(正整数类型)...',vtype:'number'" />
							</c:when>
							<c:when test="${item.colType == 4}">
								<%-- 不绑定_日期时间 --%>
								<input type="text" class="text medium"
									name="TableInfo/${item.colName}"
									id="${item.colName}" 
									value="${item.colDefault}" 									
									onclick="popUpCalendar(this, this,null,null,null,true,-1);" size="20" readonly="true" 					 
									alt="allowBlank:<c:if test="${item.isNullable == 1}">true</c:if><c:if test="${item.isNullable == 0}">false</c:if>,vtext:'请选择${item.colChname}...'" />
							</c:when>
							<c:when test="${item.colType == 7}">
								<%-- 不绑定_附件上传 --%>
								<eoms:attachment name="CtContents" property="${item.colName}" scope="request" idField="TableInfo/${item.colName}" appCode="contract" startsWith="0" viewFlag="N"/> 
							</c:when>
						</c:choose>
					</c:when>

					<c:when test="${item.colDictType == 1}">
						<c:choose>
							<c:when test="${item.colType == 5}">
								<%-- 有联动的子节点，没有父节点 --%>
								<c:if test="${item.subNode != '' and (item.parentNode == null or item.parentNode == '')}">
									<c:if test="${item.isNullable == 1}"><%-- 允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}" 
									 		id="${item.colName}" 
									 		initDicId="${item.colDictId}"
											sub="${item.subNode}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
									<c:if test="${item.isNullable == 0}"><%-- 不允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}"
											id="${item.colName}" 
											initDicId="${item.colDictId}"
											sub="${item.subNode}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
								</c:if>
								<%-- 有联动的子节点，有父节点 --%>
								<c:if test="${item.subNode != '' and item.parentNode != null and item.parentNode != ''}">
									<c:if test="${item.isNullable == 1}"><%-- 允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}"
											id="${item.colName}"
											initDicId="${CtContentsMap[item.parentNode]}"
											sub="${item.subNode}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
									<c:if test="${item.isNullable == 0}"><%-- 不允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}"
											id="${item.colName}"
											initDicId="${CtContentsMap[item.parentNode]}"
											sub="${item.subNode}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
								</c:if>
								<%-- 没有联动的子节点，有父节点 --%>
								<c:if test="${item.subNode == '' and item.parentNode != null and item.parentNode != ''}">
									<c:if test="${item.isNullable == 1}"><%-- 允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}"
											id="${item.colName}"
											initDicId="${CtContentsMap[item.parentNode]}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
									<c:if test="${item.isNullable == 0}"><%-- 不允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}"
											id="${item.colName}"
											initDicId="${CtContentsMap[item.parentNode]}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
								</c:if>								
								<%-- 没有联动的子节点，没有父节点 --%>
								<c:if test="${item.subNode == '' and (item.parentNode == null or item.parentNode == '')}">
									<c:if test="${item.isNullable == 1}"><%-- 允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}"
											id="${item.colName}" initDicId="${item.colDictId}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
									<c:if test="${item.isNullable == 0}"><%-- 不允许为空 --%>
										<eoms:comboBox name="TableInfo/${item.colName}"
											id="${item.colName}" initDicId="${item.colDictId}"
											defaultValue="${CtContentsMap[item.colName]}"
											alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
									</c:if>
								</c:if>
							</c:when>							
							<c:when test="${item.colType == 6}">
								<%-- 普通字典_多选字段 --%>
								<c:if test="${item.isNullable == 1}"><%-- 允许为空 --%>
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" 
										initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:true,vtext:'请选择${item.colChname}(多选字典)...'" 
										multiple="multiple" />
								</c:if>
								<c:if test="${item.isNullable == 0}"><%-- 不允许为空 --%>
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" 
										initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:false,vtext:'请选择${item.colChname}(多选字典)...'" 
										multiple="multiple" />
								</c:if>
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${item.colDictType == 2}">
						<c:choose>
							<c:when test="${item.colType == 5}">
								<%-- 文件字典_单选字段 --%>
								<c:if test="${item.isNullable == 1}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
								<c:if test="${item.isNullable == 0}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
							</c:when>
							<c:when test="${item.colType == 6}">
								<%-- 合同字典_多选字段 --%>
								<c:if test="${item.isNullable == 1}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
								<c:if test="${item.isNullable == 0}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${item.colDictType == 3}">
						<c:choose>
							<c:when test="${item.colType == 5}">
								<%-- 文件字典_单选字段 --%>
								<c:if test="${item.isNullable == 1}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
								<c:if test="${item.isNullable == 0}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
							</c:when>
							<c:when test="${item.colType == 6}">
								<%-- 文件字典_多选字段 --%>
								<c:if test="${item.isNullable == 1}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:true,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
								<c:if test="${item.isNullable == 0}">
									<eoms:comboBox name="TableInfo/${item.colName}"
										id="${item.colName}" initDicId="${item.colDictId}"
										defaultValue="${CtContentsMap[item.colName]}"
										alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'" />
								</c:if>
							</c:when>
						</c:choose>
					</c:when>
				</c:choose>
			</td>
		</c:if>
		<c:if test="${item.mark != 1}">
			<%-- 半行--%>
			<c:set var="flag" value="${flag+1}" />
		</c:if>
		<c:if test="${item.mark == 2}">
			<%-- 有左半行，填充右半行为空行--%>
			<td class="label"></td>
			<td class="content" role="4"></td>
			<c:set var="flag" value="${flag+1}" />
		</c:if>
		<c:if test="${item.mark == 1 || flag>=2}">
			<%-- 整行，可以换行--%>
			</tr>
			${!status.last?"<tr>":""}
			<c:set var="flag" value="${0}" />
		</c:if>
	</c:forEach>
	</tr>