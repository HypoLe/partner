<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<content tag="heading">网调信息子过程 列表</content>

<c:choose>
<c:when	test="${attemperForm.strutsAction==5}">
<html:form action="/attempers.do?method=sublist" styleId="attemperForm" method="post"> 
<html:hidden name="attemperForm" property="strutsAction" />
<table class="formTable">
	<tr>
		<td class="label">
			专业名称
			<eoms:dict key="dict-duty" dictId="attemperSpeciality" defaultId="${attemperForm.speciality}" beanId="selectXML" selectId="speciality"/>
			&nbsp;&nbsp;&nbsp;设备所属部门
			<eoms:comboBox name="netDeptList" id="netDeptList" defaultValue="${attemperForm.netDeptList}" initDicId="1040201"/> 
			&nbsp;&nbsp;&nbsp;状态
			<eoms:dict key="dict-duty" dictId="attemperStatus2" defaultId="${attemperForm.status}" beanId="selectXML" selectId="status"/>
			&nbsp;&nbsp;&nbsp; 时间段
			<eoms:dict key="dict-duty" dictId="attemperDays" defaultId="${attemperForm.days}" beanId="selectXML" selectId="days"/>
			&nbsp;&nbsp;&nbsp;<input type="submit" class="btn" value="查询" />
		</td>
	</tr>
</table>
</html:form>
</c:when>
</c:choose>

	<display:table name="attemperList" cellspacing="0" cellpadding="0"
		id="attemperList" pagesize="${pageSize}" class="table attemperList"
		export="true"
		requestURI="${app}/duty/attempers.do?method=list"
		sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.duty.displaytag.support.DutyAttemperDisplayDecorator">
		
		<display:column title="结束" media="html">
		<c:choose><c:when test="${attemperList.attemperSub.status=='2'||attemperForm.strutsAction!=5}">
			<html:link title="查看子过程" href="${app}/duty/attemperSubs.do?method=view" paramId="id" paramProperty="attemperSub.id" paramName="attemperList">
     		<img src="${app}/duty/images/an_xs.gif" border="0" >
       		</html:link>
		</c:when>
		<c:otherwise>	
    		<html:link title="结束子过程" href="${app}/duty/attemperSubs.do?method=finish" paramId="id" paramProperty="attemperSub.id" paramName="attemperList">
     			<img src="${app}/duty/images/an_bj.gif" border="0" >
       		</html:link>
       	</c:otherwise>	
       </c:choose>
	</display:column>
	
	<display:column property="attemperSub.intendBeginTime" sortable="true"
			headerClass="sortable" title="预计开始时间" paramId="id" paramProperty="id"/>
	
	<display:column property="sheetId" sortable="true" 
			headerClass="sortable" title="网调信息编号" paramId="id" paramProperty="id"/>	
	<display:column property="title" sortable="true" 
			headerClass="sortable" title="主题" href="${app}/duty/attempers.do?method=view" paramId="id" paramProperty="id"/>
	
	<!-- 对于传输专业,除本地网、时钟、时间同步网网调结果为：核实未完成(未影响业务),核实未完成(影响业务)外都要求生成对比表,请先生成对比表! -->
	<!-- speciality=9为传输专业，subSpecialit=4为本地网，subSpecialit=5为时钟、时间同步网 -->
	<display:column sortable="true" headerClass="sortable" title="专业类别" media="excel">
	<c:choose><c:when test="${attemperList.speciality=='9'}">
		<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperList.speciality}" beanId="id2nameXML" />
		(<eoms:dict key="dict-duty" dictId="attemperSpecialitySub" itemId="${attemperList.subSpeciality}" beanId="id2nameXML" />)
	</c:when>
	<c:otherwise>
		<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperList.speciality}" beanId="id2nameXML" />
	</c:otherwise>
	</c:choose>
    </display:column>
    
	<display:column sortable="true" headerClass="sortable" title="专业类别" media="html">
	<c:choose><c:when test="${attemperList.speciality=='9'}">
		<c:choose><c:when test="${attemperList.attemperSub.deptId==sessionform.deptid}">
		<a href='${app}/duty/attemperContrasts.do?method=add&subAttemperId=${attemperList.attemperSub.id}&attemperId=${attemperList.id}&beginTime=${attemperList.attemperSub.intendBeginTime}&endTime=${attemperList.attemperSub.intendEndTime}' 
		title='生成对比表,已有 ${attemperList.attemperSub.ifContrastReport} 个对比表!'>
			<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperList.speciality}" beanId="id2nameXML" />
			(<eoms:dict key="dict-duty" dictId="attemperSpecialitySub" itemId="${attemperList.subSpeciality}" beanId="id2nameXML" />)
		</a>
		</c:when>
		<c:otherwise>
			<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperList.speciality}" beanId="id2nameXML" />
			(<eoms:dict key="dict-duty" dictId="attemperSpecialitySub" itemId="${attemperList.subSpeciality}" beanId="id2nameXML" />)
		</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperList.speciality}" beanId="id2nameXML" />
	</c:otherwise>
	</c:choose>
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" title="是否影响业务" >
		<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${attemperList.attemperSub.isAppEffect}" beanId="id2nameXML" />
    </display:column>
    
    <display:column property="attemperSub.persistTimes" sortable="true"
			headerClass="sortable" title="持续时间" paramId="id" paramProperty="id"/>
			
	<display:column sortable="true" headerClass="sortable" title="状态" >
  		<eoms:dict key="dict-duty" dictId="attemperStatus" itemId="${attemperList.attemperSub.status}" beanId="id2nameXML" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" title="录入人" >
  		<eoms:id2nameDB id="${attemperList.attemperSub.cruser}" beanId="tawSystemUserDao" />
    </display:column>

    <display:column property="attemperSub.crtime" sortable="true"
			headerClass="sortable" title="录入时间" paramId="id" paramProperty="id"/>
	
	<display:column property="attemperSub.intendEndTime" sortable="true"
			headerClass="sortable" title="预计结束时间" paramId="id" paramProperty="id"/>
			
	<display:column property="planSheetId" sortable="true"
			headerClass="sortable" title="来文编号" paramId="id" paramProperty="id"/>
			
	<display:column property="recordUser" sortable="true"
			headerClass="sortable" title="联系人和联系方式" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" title="发起部门" >
	 	<eoms:id2nameDB id="${attemperList.recordDept}" beanId="tawSystemDictTypeDao" />
    </display:column>

	<display:column property="netNames" sortable="true"
			headerClass="sortable" title="涉及网元" paramId="id" paramProperty="id"/>

	<display:column property="netDeptName" sortable="true"
			headerClass="sortable" title="设备所属部门" paramId="id" paramProperty="id"/>
	
    <display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>
	<display:setProperty name="export.rtf" value="false"/>
	</display:table>

<%@ include file="/common/footer_eoms.jsp"%>