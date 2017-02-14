<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<logic:present name="pnrResMeteringDetail">
	<logic:notEmpty name="pnrResMeteringDetail">
		<logic:iterate id="map" name="pnrResMeteringDetail" type="java.util.HashMap" >     
			<table  cellpadding="0" class="listTable taskList" cellspacing="0" >
				<thead>
					<tr>
						<th>单价</th>
						<th>公式</th>
						<th>计算公式</th>
						<th>次数</th>
						<th>费用</th>
						<th>异常内容</th>
					</tr>
				</thead>
				<tbody>
					<tr class="odd">
						<td>${map.price }</td>
						<td>${map.formula }</td>
						<td>${map.formulavalue }</td>
						<td>${map.eventnumber }</td>
						<td>${map.charging }</td>
						<td>
							<c:forTokens items="${map.physicalproperty }" delims=";" var="array">
								<c:out value="${array}"></c:out>;<br/>
							</c:forTokens>
						
						</td>
					</tr>
				</tbody>
			</table>
		</logic:iterate>
	</logic:notEmpty>
</logic:present>
