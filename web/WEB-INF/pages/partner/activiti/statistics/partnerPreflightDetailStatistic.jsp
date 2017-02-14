<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%response.setHeader("cache-control","public"); %>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript">
function query(){
	if(document.getElementById('year').value == ''){
		alert('请选择年份');
		return false;
	}
	if(document.getElementById('month').value == ''){
		alert('请选择月度');
		return false;
	}
	var curDate = new Date(); 
	if((document.getElementById('year').value>curDate.getYear())){
		alert('只能查询当年的历史月份，不能查询当月');
		return false;
	}
	if((document.getElementById('year').value==curDate.getYear()) && (document.getElementById('month').value> curDate.getMonth())){
		alert('只能查询当年的历史月份，不能查询当月');
		return false;
	}
	
}
</script>
<html:form   action="/pnrStatistics.do?method=findPreflightDetailPartnerStatistic" styleId="theform" > 
<table class="formTable">
	<tr>
			<td class="label">年份*</td>
			<td class="content">
				<eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" 
					beanId="selectXML" cls="select"/>
			</td>
			<td class="label">月度*</td>
			<td class="content" >
				<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" 
					beanId="selectXML" cls="select" />
			</td>
		</tr>
		<tr>
					<td class="label">报表类型</td>
					<td class="content">
						<select name="excelType"  id="excelType" class="select"
							alt="allowBlank:false,vtext:'请选择报表类型'">
							<option value="">
								--请选择报表类型--
							</option>
							<option value="0">
								全部项目
							</option>
							<option value="1">
								合格并同意实施
							</option>
							<option value="2">
								合格但不同意实施
							</option>
							<option value="3">
								不合格项目
							</option>
						</select>
					</td>
					<td class="label"></td>
					<td class="content"></td>
				</tr>
</table>	
<table>					
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" onclick="return query()"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</html:form>
<br/>

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
        requestURI="/activiti/statistics/pnrStatistics.do?method=findPreflightDetailPartnerStatistic"
		id="list" class="table list" export="false" sort="list"  pagesize="${pageSize}" size="${total}" partialList="true">
		<display:column   title="地市"  sortable="true">
				<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao"/>
		</display:column>	
		<display:column  title="所属县区" sortable="true">	
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao"/>
		</display:column>
		<display:column property="processinstanceid" title="工单号" sortable="true"/>
		<display:column property="sheetid" title="项目编号"  sortable="true"/>
		<display:column property="theme" title="项目名称" sortable="true"/>
		<display:column  title="承载业务级别" sortable="true">
			<eoms:id2nameDB id="${list.bearService}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
		<display:column property="workorderTypeName" title="工单类型" sortable="true"/>
		<display:column property="subTypeName" title="工单子类型" sortable="true"/>
		<display:column title="关键字" sortable="true">
			<eoms:id2nameDB id="${list.keyWord}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
		<display:column title="紧急程度" sortable="true">
			<eoms:id2nameDB id="${list.workOrderDegree}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
		<display:column property="faultDescription" title="项目实施内容描述" sortable="true" maxLength="15" />
		<display:column property="projectAmount" title="项目金额（元）" sortable="true"/>
		<display:column property="submitApplicationTime" title="申请提交时间"  sortable="true"/>
		<display:column property="name" title="审批环节" sortable="true"/>
		<display:column property="endTime" title="市分公司副总批复日期"  sortable="true"/>
		<display:column property="reviewResult" title="会审结果（合格/不合格）" sortable="true" />
		<display:column property="expertOpinion" title="专家组意见" sortable="true"/>
		<display:column property="practice" title="是否同意实施（同意/不同意）" sortable="true"/>
		<display:column property="distributedInterfaceTime" title="省公司批复日期" sortable="true"/>
	</display:table>
</logic:present>
	</br>
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
