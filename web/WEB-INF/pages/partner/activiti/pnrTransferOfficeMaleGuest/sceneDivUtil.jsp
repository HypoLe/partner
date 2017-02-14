<%@ page language="java" pageEncoding="utf-8"%>
<div id="sceneDiv" >
	<table id="sceneConTable" class="formTable">
			<tr>
				<td class="label">
					主场景
				</td>
				<td>
					<div id="mainSceneCon">
						<!-- 主场景 回显开始 -->
						<c:forEach  items="${mainSceneList}" var="mainScene" >
							<c:if test="${mainScene.checked eq 'true' }">
						 		<input type="checkbox" value="${mainScene.mainSceneValue}" name="mainScene" checked=true ><span>${mainScene.mainSceneName}</span>
						 	</c:if>
						 	<c:if test="${mainScene.checked eq 'false' }">
						 		<input type="checkbox" value="${mainScene.mainSceneValue}" name="mainScene" ><span>${mainScene.mainSceneName}</span>
						 	</c:if>
						</c:forEach>
						<!-- 主场景 回显结束 -->
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">
					子场景
				</td>
				<td>
					<div id="childSceneCon">
						<c:forEach  items="${childSceneWholeModelList}" var="childSceneWhole" >
							<div id="${childSceneWhole.mainSceneValue}-cond">
								${childSceneWhole.mainSceneName}:
								<c:forEach items="${childSceneWhole.childSceneList}" var="childScene" >
									<c:if test="${childScene.checked eq 'true' }">
										<input type="checkbox" value="${childScene.childSceneValue}" name="${childSceneWhole.mainSceneValue}-check" checked=true>${childScene.childSceneName}
									</c:if>
									<c:if test="${childScene.checked eq 'false' }">
										<input type="checkbox" value="${childScene.childSceneValue}" name="${childSceneWhole.mainSceneValue}-check" >${childScene.childSceneName}
									</c:if>
								</c:forEach>
							</div>
						</c:forEach>
					
					</div>
				</td>
			</tr>
		</table>
		<!-- 子场景展示区 -->
		<div id="childSceneShow">
			<c:if test="${!empty sceneDetailWholeModelList}"><!-- 先判断一下是否有明细数据 -->
				<c:forEach  items="${sceneDetailWholeModelList}" var="sceneDetailWhole" >
					<c:set var ="size" value="${sceneDetailWhole.sceneDetailListSize}"/><!-- 实际的数据条数 -->
					<c:set var ="mainValue" value="${sceneDetailWhole.mainSceneValue}"/><!-- 主场景的值 -->
					<c:set var ="childValue" value="${sceneDetailWhole.childSceneValue}"/><!-- 子场景的值 -->
					<div class="outlayer" id="${sceneDetailWhole.mainSceneValue}-${sceneDetailWhole.childSceneValue}-outlayer">
						<div class="photolayer" id="${sceneDetailWhole.mainSceneValue}-${sceneDetailWhole.childSceneValue}-photolayer">
        					<img src="/partner/images/icons/delete.gif" class="imgcss" name="${sceneDetailWhole.mainSceneValue}-${sceneDetailWhole.childSceneValue}-img">
      					</div>
                        <table class="formTable" name="${sceneDetailWhole.mainSceneValue}-${sceneDetailWhole.childSceneValue}-childTable">
                        	<c:if test="${!empty sceneDetailWhole.properties}">
			                    <tr><!-- 表头 -->
									<c:forEach  items="${sceneDetailWhole.properties}" var="propertie" >
										<td>${propertie}</td>
									</c:forEach>
			 					</tr>
		 					</c:if>
		 					<c:if test="${!empty sceneDetailWhole.firstLineSceneDetail}">
		 						<c:set var ="firstLine" value="${sceneDetailWhole.firstLineSceneDetail}"/>
		 						<tr>
									 <td rowspan="${size}">${firstLine.mainSceneName}<input type="hidden" value="${firstLine.mainSceneName}" name="${mainValue}-${childValue}-sceneName" /></td>
									 <td rowspan="${size}">${firstLine.childSceneName}<input type="hidden" value="${firstLine.childSceneName}" name="${mainValue}-${childValue}-copyName" /></td>
									 <td rowspan="${size}">${firstLine.treatmentMeasures}<input type="hidden" value="${firstLine.treatmentMeasures}" name="${mainValue}-${childValue}-dispose" /></td>
                                     <td rowspan="${size}">
                                     	<c:if test="${sceneDetailWhole.isHave eq '1'}">
                                     		<input type="text" name="${mainValue}-${childValue}-unit" value="${firstLine.unitNum}">
              								<input type="hidden" name="${mainValue}-${childValue}-oldunit" value="${firstLine.unitNum}">
                                     	</c:if>
                                     	${firstLine.totalUnit}
              							<input type="hidden" name="${mainValue}-${childValue}-unitname" value="${firstLine.totalUnit}">
                                     </td>
                                     <td>
                                     	<select name="${mainValue}-${childValue}-${firstLine.itemNo}-material">
                                     		<c:forEach  items="${firstLine.materialList}" var="material" >
	                                     		<c:if test="${material.selected eq 'true' }">
	                                     			<option value="${material.materialValue}" selected>${material.materialName}</option>
	                                     		</c:if>
	                                     		<c:if test="${material.selected eq 'false' }">
	                                     			<option value="${material.materialValue}" >${material.materialName}</option>
	                                     		</c:if>
                                     		</c:forEach>
                                     	</select>
                                     </td>
                                     <td class="cnum">
                                     	<input type="text" value="${firstLine.num}" name="${mainValue}-${childValue}-${firstLine.itemNo}-num">
                                        <input type="hidden" value="${firstLine.oldNum}" name="${mainValue}-${childValue}-${firstLine.itemNo}-oldnum">
                                        <input type="hidden" value="${firstLine.quotaNum}" name="${mainValue}-${childValue}-${firstLine.itemNo}-quotanum">
                                        <input type="hidden" value="${firstLine.oldQuotaNum}" name="${mainValue}-${childValue}-${firstLine.itemNo}-oldquotanum">
                                     </td>
	                                 <td >${firstLine.unit}<input type="hidden" value="${firstLine.unit}" name="${mainValue}-${childValue}-${firstLine.itemNo}-cunit" /></td>
	                                 <td class="cprice">
	                                 	<input type="text" value="${firstLine.price}" name="${mainValue}-${childValue}-${firstLine.itemNo}-price">
	                                 	<input type="hidden" value="${firstLine.oldPrice}" name="${mainValue}-${childValue}-${firstLine.itemNo}-oldprice">
	                                 	<input type="hidden" value="${firstLine.initialPrice}" name="${mainValue}-${childValue}-${firstLine.itemNo}-initialprice">
	                                 </td>
	                                 <td class="ctotal">${firstLine.total}<input type="hidden" value="${firstLine.total}" name="${mainValue}-${childValue}-${firstLine.itemNo}-ctotal" /></td>
	                                 <td class="cutilize"><!-- 是否利旧 -->
                                     	<select name="${mainValue}-${childValue}-${firstLine.itemNo}-utilize">
                                     		<c:forEach  items="${firstLine.utilizeList}" var="utilize" >
	                                     		<c:if test="${utilize.selected eq 'true' }">
	                                     			<option value="${utilize.utilizeValue}" selected>${utilize.utilizeName}</option>
	                                     		</c:if>
	                                     		<c:if test="${utilize.selected eq 'false' }">
	                                     			<option value="${utilize.utilizeValue}" >${utilize.utilizeName}</option>
	                                     		</c:if>
                                     		</c:forEach>
                                     	</select>
                                     </td>
		 						</tr>
		 					</c:if>
		 					<c:if test="${!empty sceneDetailWhole.sceneDetailList}">
		 						<c:forEach  items="${sceneDetailWhole.sceneDetailList}" var="sceneDetail" >
			 						<tr>
	                                    <td>
	                                     	<select name="${mainValue}-${childValue}-${sceneDetail.itemNo}-material">
	                                     		<c:forEach  items="${sceneDetail.materialList}" var="material" >
	                                     			<c:if test="${material.selected eq 'true' }">
	                                     				<option value="${material.materialValue}" selected>${material.materialName}</option>
	                                     			</c:if>
	                                     			<c:if test="${material.selected eq 'false' }">
	                                     				<option value="${material.materialValue}" >${material.materialName}</option>
	                                     			</c:if>
	                                     		</c:forEach>
	                                     	</select>
                                         </td>
	                                     <td class="cnum">
	                                     	<input type="text" value="${sceneDetail.num}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-num">
	                                        <input type="hidden" value="${sceneDetail.oldNum}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-oldnum">
	                                        <input type="hidden" value="${sceneDetail.quotaNum}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-quotanum">
	                                        <input type="hidden" value="${sceneDetail.oldQuotaNum}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-oldquotanum">
                                     	 </td>
		                                 <td >${sceneDetail.unit}<input type="hidden" value="${sceneDetail.unit}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-cunit" /></td>
		                                 <td class="cprice">
		                                 	<input type="text" value="${sceneDetail.price}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-price">
		                                 	<input type="hidden" value="${sceneDetail.oldPrice}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-oldprice">
		                                 	<input type="hidden" value="${sceneDetail.initialPrice}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-initialprice">
	                                     </td>
		                                 <td class="ctotal">${sceneDetail.total}<input type="hidden" value="${sceneDetail.total}" name="${mainValue}-${childValue}-${sceneDetail.itemNo}-ctotal"/></td>
		                                  <td class="cutilize"><!-- 是否利旧 -->
	                                     	<select name="${mainValue}-${childValue}-${sceneDetail.itemNo}-utilize">
	                                     		<c:forEach  items="${sceneDetail.utilizeList}" var="utilize" >
		                                     		<c:if test="${utilize.selected eq 'true' }">
		                                     			<option value="${utilize.utilizeValue}" selected>${utilize.utilizeName}</option>
		                                     		</c:if>
		                                     		<c:if test="${utilize.selected eq 'false' }">
		                                     			<option value="${utilize.utilizeValue}" >${utilize.utilizeName}</option>
		                                     		</c:if>
	                                     		</c:forEach>
	                                     	</select>
                                     	</td>
			 						</tr>
		 						</c:forEach>
		 					</c:if>
                        </table>        				
					</div>
				</c:forEach>
			</c:if>
		</div>
		
		<table id="totalAmountTable" class="formTable">
			<tr> 
				<td class="label">
					总金额
				</td>
				<td>
					<span id="totalAmountSpan"><fmt:formatNumber value='${totalAmount!=null&&totalAmount!=""?totalAmount:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' /></span>元
					<input type="hidden" id="totalAmount" name="totalAmount" value="<fmt:formatNumber value='${totalAmount!=null&&totalAmount!=""?totalAmount:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" />
				</td>
			</tr>
		</table>
</div>
