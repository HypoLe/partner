<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style>
/*设置除附件的样式*/
.baseFieldset{
	border: 1px solid #BBBBBB;
	/*border: 1px solid #dfdfdf;*/
    padding: 10px;
}

.baseFieldset legend {
    color: #444;
    font-size: 13px;
    line-height: 22px;
    margin: 0 0 5px;
}

.baseFormTable {
	border-collapse: collapse;
	font-size: 12px;
	width: 100%;
}

.baseFormTable td {
	background-color: #fff;
	border: 1px solid #dcdcdc;
	padding: 6px;
}

.baseFormTable td.showTabel {
    background-color: #f7f7f7;
}

.baseFormTable td input.text,input.txt, textarea.textarea {
	background: url("images/text-bg.gif") repeat-x scroll 0 0 #ffffff;
	border: 1px solid #b5b8c8;
	color: #333;
	height: 22px;
	line-height: 18px;
	padding: 2px 3px 0;
	vertical-align: middle;
	width: 145px;
}

.baseFormTable td textarea.textarea {
    width:100%;
	height: 100px;
}


.baseFormTable td input.attachment {
	background: url("images/text-bg.gif") repeat-x scroll 0 0 #ffffff;
	border: 1px solid #b5b8c8;
	color: #333;
	height: 22px;
	line-height: 18px;
	padding: 2px 3px 0;
	vertical-align: middle;
}

.baseFormTable td select {
	border: 1px solid #b5b8c8;
	padding: 1px;
	vertical-align: middle;
	width: 145px;
}




</style>

<html:form action="/pnrOverhaulRemake.do?method=performAdd"
	styleId="theform">
	<fieldset class="baseFieldset">
		<legend>
			基本信息
		</legend>
		<table class="baseFormTable">
			<tr>
				<td width="10%" class="showTabel">
					项目名称*
				</td>
				<td colspan="3">
				    <c:if test="${pnrTransferOffice.theme ==null}">
					
					<input type="text" id="theme" name="theme" value="${pnrTransferOffice.theme}" class="text" style="width:100%" alt="allowBlank:false,maxLength:120,vtext:'请输入工单名称，最大长度为60个汉字！'" />
					</c:if>
					
					 <c:if test="${pnrTransferOffice.theme !=null}">
					<input type="text" name="theme" class="text" value="${pnrTransferOffice.theme}" readOnly=true/>
	 				 </c:if>
				</td>
				<td width="10%" class="showTabel">
					项目编号
				</td>
				<td width="15%">
					<input type="text" id="projectName" name="projectName" class="text" value="${pnrTransferOffice.projectName}"/>
				</td>
				<td width="10%" class="showTabel">
					区县账号*
				</td>
				<td width="15%">
				 <select id="areaCountyAccount" name="areaCountyAccount" class="select">
			  	<option value="">--请选择区县账号--</option>
			 	 </select>
				</td>
			</tr>
			<tr>
				<td class="showTabel">
					项目类型*
				</td>
				<td width="15%">
					<select id="processType" name="processType" class="text">
						<option value="overhaul">
							大修
						</option>
						<option value="reform">
							改造
						</option>
					</select>
				</td>
				<td width="10%" class="showTabel">
					大修改造类别*
				</td>
				<td width="15%">
					<select id="overhaulType" name="overhaulType" class="text">
						<option value="">请选择</option>
					</select>
				</td>
				<td class="showTabel">
					紧急程度*
				</td>
				<td>
					<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
					defaultValue="${pnrTransferOffice.workOrderDegree}" initDicId="1012309"
					alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="showTabel">
					立项时间*
				</td>
				<td>
					<input type="text" class="text" name="mainFaultOccurTime"
					readonly="readonly" id="mainFaultOccurTime"
					value="${eoms:date2String(mainFaultOccurTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
					alt="allowBlank:false" />
				</td>
			</tr>
			<tr>
				<td class="showTabel">
					线路名称*
				</td>
				<td>
					<input type="text" id="lineName" name="lineName" class="text" value="${pnrTransferOffice.lineType}" alt="allowBlank:false,maxLength:100,vtext:'请输入线路名称，最大长度为50个汉字！'"/>
				</td>
				<td class="showTabel">
					线路级别*
				</td>
				<td>
					<select id="lineType" name="lineType" class="text">
						<option value="">请选择</option>
					</select>
				</td>
				<td class="showTabel">
					承载业务级别
				</td>
				<td>
					<eoms:comboBox name="bearService" id="bearService"
					defaultValue="${pnrTransferOffice.bearService}" initDicId="1012313"
					alt="allowBlank:true" styleClass="select" />
				</td>
				<td class="showTabel">
					敷设方式*
				</td>
				<td>
					<select id="layingType" name="layingType" class="">
						<option value="">直埋</option>
						<option value="">管道</option>
						<option value="">架空</option>
						<option value="">其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="showTabel">
					中继段
				</td>
				<td>
					<input type="text" id="middlePart" name="middlePart" class="text" value="${pnrTransferOffice.middlePart}" alt="allowBlank:true" />
				</td>
				<td class="showTabel">
					起止地点（标石、杆号、人井号）*
				</td>
				<td>
					<input type="text" id="specificLocation" name="specificLocation" value="${pnrTransferOffice.specificLocation}" class="text" alt="allowBlank:false"/>
				</td>
				<td class="showTabel">
					光（电）缆型号
				</td>
				<td>
					<input type="text" id="cableType" name="cableType" class="text" value="${pnrTransferOffice.cableType}"/>
				</td>
				<td class="showTabel">
					芯数
				</td>
				<td>
					<input type="text" id="coreNum" name="coreNum" class="text" value="${pnrTransferOffice.coreNum}"/>
				</td>
			</tr>
			<tr>
				<td class="showTabel">
					起点经度*
				</td>
				<td>
					<input type="text" id="createLongitude" name="createLongitude" class="text" value="${pnrTransferOffice.createLongitude}" />
				</td>
				<td class="showTabel">
					起点纬度*
				</td>
				<td>
					<input type="text" id="createLatitude" name="createLatitude" class="text" value="${pnrTransferOffice.createLatitude}"/>
				</td>
				<td class="showTabel">
					终点经度*
				</td>
				<td>
					<input type="text" id="endLongitude" name="endLongitude" class="text" value="${pnrTransferOffice.endLongitude}"/>
				</td>
				<td class="showTabel">
					终点纬度*
				</td>
				<td>
					<input type="text" id="endLatitude" name="endLatitude" class="text" value="${pnrTransferOffice.endLatitude}" />
				</td>
			</tr>
			<tr>
				<td class="showTabel">
					项目主管签名*
				</td>
				<td>
					<input type="text" id="chargeName" name="chargeName" class="text" value="${pnrTransferOffice.chargeName}"/>
				</td>
				<td class="showTabel">
					电话*
				</td>
				<td>
					<input type="text" id="chargeTel" name="chargeTel" class="text" value="${pnrTransferOffice.chargeTel}"/>
				</td>
				<td class="showTabel">
					部门负责人*
				</td>
				<td>
					<input type="text" id="principal" name="principal" class="text" value="${pnrTransferOffice.deptHead}"/>
				</td>
				<td class="showTabel">
					电话*
				</td>
				<td>
					<input type="text" id="principalTel" name="principalTel" class="text" value="${pnrTransferOffice.deptHeadMobilePhone}"/>
				</td>
			</tr>
		</table>
	</fieldset>

	<!-- 预算及赔补金额 -->
	<fieldset class="baseFieldset">
		<legend>
			预算及赔补金额
		</legend>
		<table class="baseFormTable" width="75%">
			<tr>
				<td width="10%" class="showTabel">
					项目预算*
				</td>
				<td width="15%">
					<input type="text" id="budgetMoney" name="budgetMoney" class="text" value="${pnrTransferOffice.budgetMoney}"/>
					万元
				</td>
				<td width="10%" class="showTabel">
					赔补金额
				</td>
				<td width="15%">
					<input type="text" id="subsidyMoney" name="subsidyMoney" class="text" value="${pnrTransferOffice.compensate}" />
					万元
				</td>
				<td width="10%" class="showTabel">
					赔补合同编号
				</td>
				<td width="15%">
					<input type="text" id="subsidyNumber" name="subsidyNumber" class="text" value="${pnrTransferOffice.subsidyNumber}"/>
				</td>
			</tr>
		</table>
	</fieldset>

	<!-- 项目描述 -->
	<fieldset class="baseFieldset">
		<legend>
			项目描述
		</legend>
			<table class="baseFormTable">
				<tr>
					<td width="20%" class="showTabel">
						建设原因及必要性*
					</td>
					<td colspan="3">
						<textarea id="constructionReasons" name="constructionReasons" class="textarea"></textarea>
					</td>
				</tr>
				<tr>
					<td class="showTabel">
						网络现状描述*
					</td>
					<td colspan="3">
						<textarea id="networkStatus" name="networkStatus" class="textarea"></textarea>
					</td>
				</tr>
				<tr>
					<td class="showTabel">
						主要建设内容及规模*
					</td>
					<td colspan="3">
						<textarea id="constructionMainContent" name="constructionMainContent" class="textarea"></textarea>
					</td>
				</tr>
			</table>

		<fieldset class="baseFieldset">
			<legend>
				主要工程量
			</legend>
			<table class="baseFormTable" width="76%">
				<tr>
					<td width="10%" class="showTabel">
						敷设光缆
					</td>
					<td width="16%">
						<input type="text" id="layingCable" name="layingCable" class="text" value="${pnrTransferOffice.layingCable}"/>
						皮长公里
					</td>
					<td width="10%" class="showTabel">
						开挖缆沟
					</td>
					<td width="15%">
						<input type="text" id="excavationTrench" name="excavationTrench" class="text" value="${pnrTransferOffice.excavationTrench}"/>
						公里
					</td>
					<td width="10%" class="showTabel">
						新建管道
					</td>
					<td width="15%">
						<input type="text" id="repairPipeline" name="repairPipeline" class="text" value="${pnrTransferOffice.repairPipeline}"/>
						孔公里
					</td>
				</tr>
				<tr>
					<td class="showTabel">
						敷设电杆
					</td>
					<td>
						<input type="text" id="rightingDemolitionPole" name="rightingDemolitionPole" class="text" value="${pnrTransferOffice.rightingDemolitionPole}"/>
						棵
					</td>
					<td class="showTabel">
						敷设钢绞线
					</td>
					<td>
						<input type="text" id="wireLaying" name="wireLaying" class="text" value="${pnrTransferOffice.wireLaying}"/>
						公里
					</td>
					<td class="showTabel">
						其它
					</td>
					<td>
						<input type="text" id="mainQuantityOther" name="mainQuantityOther" class="text" value="${pnrTransferOffice.mainQuantityOther}"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</fieldset>

	<!--综合单价  -->
		<fieldset class="baseFieldset">
			<legend>
				综合单价
			</legend>
			<table class="baseFormTable" width="50%">
				<tr>
					<td width="10%" class="showTabel">
						皮长公里造价*
					</td>
					<td width="15%">
						<input type="text" id="longLeatherMoney" name="longLeatherMoney" class="text" value="${pnrTransferOffice.longLeatherMoney}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>
						万元/皮长公里
					</td>
					<td width="10%" class="showTabel">
						 孔公里造价
					</td>
					<td width="15%">
						<input type="text" id="holeMoney" name="holeMoney" class="text" value="${pnrTransferOffice.holeMoney}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>
						万元/孔公里
					</td>
				</tr>
			</table>
		</fieldset>

	<!-- 附件 -->
	<fieldset class="baseFieldset">
		<legend>
			附件
		</legend>
		<table class="baseFormTable" width="75%">
			<tr>
				<td width="10%" class="showTabel">
					项目建议书*
				</td>
				<td width="15%">
					<input type="file" id="" name="" class="attachment" />
				</td>
				<td width="10%" class="showTabel">
					市公司会议纪要*
				</td>
				<td colspan="3">
					<input type="file" id="" name="" class="attachment" />
				</td>
			</tr>
			<tr>
				<td class="showTabel">
					设计说明*
				</td>
				<td>
					<input type="file" id="" name="" class="attachment" />
				</td>
				<td class="showTabel">
					概预算表*
				</td>
				<td width="15%">
					<input type="file" id="" name="" class="attachment" />
				</td>
				<td width="10%" class="showTabel">
					施工图纸(pdf格式)*
				</td>
				<td width="15%">
					<input type="file" id="" name="" class="attachment" />					
				</td>
			</tr>
			<tr>
				<td class="showTabel">
					赔补合同
				</td>
				<td>
					<input type="file" id="" name="" class="attachment" />
				</td>
				<td class="showTabel">
					其他
				</td>
				<td colspan="3">
					<input type="file" id="" name="" class="attachment"/>
				</td>
			</tr>
		</table>
	</fieldset>

	<!--事前照片  -->
	<fieldset class="baseFieldset">
		<legend>
			事前照片
		</legend>
		<table class="baseFormTable">
			<tr>
				<td colspan="5" style="width:90%">
					<div id="photoDiv" style="display: none">
						<table id="myPhotoTable">
							${photoList }
						</table>
					</div>
					<input class="text" type="hidden" name="photoIds" readonly="true"
						id="photoIds" alt="allowBlank:true" value="${photoIds }" />
					<input type="hidden" name="mainResId" id="mainResId" value="" />
					<input type="button" class="btn" value="选择" onclick="selectPhoto()"
						id="sig" />
				</td>
			</tr>
		</table>
	</fieldset>
	
	<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				派发
			</html:submit>
		</div>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>