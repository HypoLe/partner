<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<content tag="heading">对比表 列表</content>

<html:form action="/attemperContrasts.do?method=list" styleId="attemperContrastForm" method="post"> 
<table class="formTable">
	<tr>
		<td class="label">
			开始时间
			<input type="text" name="fromBeginTime" size="20"
						value="${attemperContrastForm.fromBeginTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						readonly="true" class="text">
			&nbsp;&nbsp;--到--&nbsp;&nbsp;
			<input type="text" name="toBeginTime" size="20"
						value="${attemperContrastForm.toBeginTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						readonly="true" class="text">&nbsp;&nbsp;&nbsp;
			<eoms:dict key="dict-duty" dictId="attemperDays" defaultId="${attemperForm.days}" beanId="selectXML" selectId="days"/>
			&nbsp;&nbsp;&nbsp;<input type="submit" class="btn" value="查询" />
		</td>
	</tr>
</table>
</html:form>

	<display:table name="attemperContrastList" cellspacing="0" cellpadding="0" 
		id="attemperContrastList" pagesize="${pageSize}" class="table attemperContrastList"
		export="true"
		requestURI="${app}/duty/attemperContrasts.do?method=search"
		sort="list" partialList="true" size="resultSize" 
		decorator="com.boco.eoms.duty.displaytag.support.DutyAttemperDisplayDecorator">
    
    <display:column title="显示" media="html">
    	<html:link title="${eoms:a2u('æ¥çå¯¹æ¯è¡¨ä¿¡æ¯')}" href="${app}/duty/attemperContrasts.do?method=view" paramId="id" paramProperty="id" paramName="attemperContrastList">
     		<img src="${app}/duty/images/an_xs.gif" border="0" >
       </html:link>
	</display:column>
	
	<display:column title="网调信息编号" property="attemper.sheetId" media="excel"/>
	
	<display:column title="预计开始时间" property="attemper.attemperSub.intendBeginTime" media="excel"/>
	
	<display:column title="网调信息编号" property="viewAttemperInfo" sortable="true" headerClass="sortable" media="html"/>
	
	<display:column title="预计开始时间" property="viewAttemperSubInfo" sortable="true" headerClass="sortable" media="html"/>
	
	<display:column property="crtime" sortable="true" headerClass="sortable" title="录入时间" />
			
	<display:column sortable="true" headerClass="sortable" title="录入部门" >
		<eoms:id2nameDB id="${attemperContrastList.deptId}" beanId="tawSystemDeptDao" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" title="录入人" >
		<eoms:id2nameDB id="${attemperContrastList.cruser}" beanId="tawSystemUserDao" />
	</display:column>

	<!--display:column property="attemperId" sortable="true"
			headerClass="sortable" titleKey="attemperContrast.attemperId" /-->

	<!--display:column property="subAttemperId" sortable="true"
			headerClass="sortable" titleKey="attemperContrast.subAttemperId" /-->

	<display:column property="beginTime" sortable="true" headerClass="sortable" title="开始时间" />
       
	<display:column property="endTime" sortable="true" headerClass="sortable" title="结束时间" />

	<display:column property="persistTimes" sortable="true" headerClass="sortable" title="割接历时(小时)" />

	<display:column sortable="true" headerClass="sortable" title="责任分公司" >
	 	<eoms:dict key="dict-duty" dictId="faultfilialeId" itemId="${attemperContrastList.subCompany}" beanId="id2nameXML" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" title="代维公司" >
		<eoms:id2nameDB id="${attemperContrastList.friendCompany}" beanId="tawSystemDictTypeDao" />
	</display:column>

	<display:column property="cableClass" sortable="true"
			headerClass="sortable" title="光缆级别" />

	<display:column property="subRing" sortable="true"
			headerClass="sortable" title="子环" />

	<display:column property="mainCable" sortable="true"
			headerClass="sortable" title="主用光缆" />

	<display:column property="protectCable" sortable="true"
			headerClass="sortable" title="保护光缆" />
      
	<display:column property="netNameA" sortable="true"
			headerClass="sortable" title="A端网元名称" />

	<display:column property="netNameB" sortable="true"
			headerClass="sortable" title="B端网元名称" />

	<display:column sortable="true" headerClass="sortable" title="SDH是否正常倒换" >
	 	<eoms:dict key="dict-duty" dictId="contrastIfSwitch" itemId="${attemperContrastList.ifNormalSwitch}" beanId="id2nameXML" />
    </display:column>

	<display:column property="beforeIntoA" sortable="true"
			headerClass="sortable" title="割接前A端收功率" />

	<display:column property="beforeOuterA" sortable="true"
			headerClass="sortable" title="割接前A端发功率" />

	<display:column property="beforeIntoB" sortable="true"
			headerClass="sortable" title="割接前B端收功率" />
         
	<display:column property="beforeOuterB" sortable="true"
			headerClass="sortable" title="割接前B端发功率" />

	<display:column property="afterIntoA" sortable="true"
			headerClass="sortable" title="割接后A端收功率" />

	<display:column property="afterOuterA" sortable="true"
			headerClass="sortable" title="割接后A端发功率" />

	<display:column property="afterIntoB" sortable="true"
			headerClass="sortable" title="割接后B端收功率" />

	<display:column property="afterOuterB" sortable="true"
			headerClass="sortable" title="割接后B端发功率" />

	<display:column property="finishResult" sortable="true"
			headerClass="sortable" title="完成情况" />

	<display:column property="adjustReason" sortable="true"
			headerClass="sortable" title="割接原因" />

	<display:column sortable="true" headerClass="sortable" title="是否影响业务" >
	 	<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${attemperContrastList.isAppEffect}" beanId="id2nameXML" />
    </display:column>

	<display:column property="remark" sortable="true"
			headerClass="sortable" title="备注" />

	<!--display:column property="serial" sortable="true"
			headerClass="sortable" titleKey="attemperContrast.serial" /-->

	<!--display:column property="deleted" sortable="true"
			headerClass="sortable" titleKey="attemperContrast.deleted" /-->

	<!--display:column property="days" sortable="true"
			headerClass="sortable" titleKey="attemperContrast.days" /-->

	<display:setProperty name="paging.banner.item_name" value="attemperContrast" />
	<display:setProperty name="paging.banner.items_name" value="attemperContrasts" />
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>
	<display:setProperty name="export.rtf" value="false"/>
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
<%@ include file="/common/footer_eoms.jsp"%>