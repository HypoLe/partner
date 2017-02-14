<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var jq=jQuery.noConflict();
    
    Ext.onReady(function(){
		var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
				new xbox({
					btnId:'name',dlgId:'dlg-audit',dlgTitle:'请选择人员',
					treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'所有人员',treeChkMode:'single',treeChkType:'user',
					showChkFldId:'name',saveChkFldId:'userId'
				}); 
				
				
		new xbox({
			btnId:'areatree',
			treeDataUrl:'${app}/xtree.do?method=area',treeRootId:'${province}',treeRootText:'云南',treeChkMode:'',treeChkType:'area',
			showChkFldId:'areaNames',saveChkFldId:'areaId',returnJSON:false
		});
				
	})
   
	Ext.onReady(function(){
		var check=document.getElementById("checkedList");
		if(check&&check.value!=""){
			checkV=check.value
			var checks=checkV.toString().split(";");
			for(var i=0;i<checks.length ;i++){
			//alert(checks[i].toString());
			checkValue ='#'+checks[i].toString();
			jq(checkValue).attr('checked',true);
			}
		}
	});
	
	
	
</script>


<form id="checkAndSearchFrom"
	action="oilEngine.do?method=statistics" method="post">
	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
					所属区域
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${areaStringLike}',name:'<eoms:id2nameDB id="${areaStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area2" id="area2"    class="text medium" alt="allowBlank:false" onblur="changeCheckBox()"/>
					<input type="hidden" name="area_id" id="area_id"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1" data="${boxData}"
					rootText='所属区域' valueField="area_id" handler="area2" textField="area2" checktype="" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${maintainCompanyStringLike}',name:'<eoms:id2nameDB id="${maintainCompanyStringLike}" beanId="tawSystemDeptDao"/>'}]</c:set>
					<input type="text" name="maintainCompany" class="text medium" id="maintainCompany" alt="allowBlank:false" onblur="changeCheckBox()"/>
					<input type="hidden" name="maintainCompany_id" id="maintainCompany_id"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf&showPartnerLevelType=3" rootId=""
					rootText='代维公司组织' valueField="maintainCompany_id" handler="maintainCompany" textField="maintainCompany"
					checktype="dept" single="true" data="${boxData}">
					</eoms:xbox>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>
			请选择统计项目
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
					区域
				</td>
				<td>
					<input id="area" type="checkbox" name="statisticsItem" 	value="areaTypeLikeArea" />
				</td>
				<td class="label">
					代维公司
				</td>
				<td>
					<input id="maintain_company" type="checkbox" name="statisticsItem"	value="maintain_companyTypeLikeDept" />
				</td>
				<td class="label">
					燃料种类
				</td>
				<td>
					<input id="fule_type" type="checkbox" name="statisticsItem" value="fule_type" />
				</td>
			</tr>
			<tr>
				<td class="label">
					油机类型
				</td>
				<td>
					<input id="oilEngine_type" type="checkbox" name="statisticsItem" value="oilEngine_typeTypeLikedict" />
				</td>
				<td class="label">
					产权属性
				</td>
				<td>
					<input id="oilEngine_property" type="checkbox" name="statisticsItem" value="oilEngine_propertyTypeLikedict" />
				</td>
				<td class="label">
					油机状态
				</td>
				<td>
					<input id="oilEngine_status" type="checkbox" name="statisticsItem" value="oilEngine_statusTypeLikedict" />
				</td>
			</tr>
			<tr>
				<td class="label">
					额定功率
				</td>
				<td colspan="5">
					<input id="power_rating" type="checkbox" name="statisticsItem" value="power_ratingTypeLikedict" />
				</td>
			</tr>
			<input type="hidden" name="statisticsItems" id="statisticsItems" />
			<input type="hidden" name="checkedIds" id="checkedIds" />
			<input type="hidden" name="checkedList" id="checkedList" value="${checkedList}" />
		</table>
	</fieldset>

	<input type="button" name="统计" value="统计" onclick="sendBox()" />
	<!-- This hidden area is for batch deleting. -->
	<form>
		<div>
			<table cellpadding="0" class="table contentStaffList" cellspacing="0">
				<thead>
					<tr>
						<logic-el:present name="headList">
							<c:forEach items="${headList}" var="headlist">
								<th>
									${headlist}
								</th>
							</c:forEach>
						</logic-el:present>
					</tr>
				</thead>
				<logic-el:present name="tableList">
					<tbody>
						<c:forEach items="${tableList}" var="tdList">
							<tr>
								<c:forEach items="${tdList}" var="td">
									<c:if test="${td.show}">
										<td rowspan="${td.rowspan} }">
											<c:if test="${!empty td.url}">
												<a href="javascript:void(0);"
													onclick="window.open('${app}${td.url}');">${td.name}</a>
											</c:if>
											<c:if test="${empty td.url}">
										    	${td.name}
										    </c:if>
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</logic-el:present>
			</table>
		</div>






		<%@ include file="/common/footer_eoms.jsp"%>