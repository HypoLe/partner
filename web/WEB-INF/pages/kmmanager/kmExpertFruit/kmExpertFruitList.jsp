<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language=javascript>
<!--  
	Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExpertFruitForm'});
	});

  function validateForm(){
      var frm = document.forms[0];
      if( frm.courseName.value =="" && frm.courseContent.value =="" && frm.participant.value =="" && frm.beginTime.value == "" && frm.endTime.value == "" ){
      		alert("请至少填写一项查询信息");
      		return false;
      }
      
      if( frm.beginTime.value == "" && frm.endTime.value != "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      if( frm.beginTime.value != "" && frm.endTime.value == "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      
      return true;
  }
 
  
  	function sub(){
		
		var validate = validateForm();
		
		if(validate){
			if(v.check())
			 $("kmExpertFruitForm").submit();
		}else{
			return false;
			
		}
		
	}
  
//-->
</script>



<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/kmExpertFruits.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">



	<html:form action="/kmExpertFruits.do?method=searchX" styleId="kmExpertFruitForm" method="post">
		<table align="center">

			<tr>
				<!-- 课题名称 -->
				<td>
					<fmt:message key="kmExpertFruit.courseName" />:
				</td>
				<td style="width:85px">
					<input type="text" id=""courseName"" name="courseName" class="text" value="${kmExpertFruitForm.courseName}" maxlength="32">&nbsp;&nbsp;
				</td>
				<!-- 课题内容 -->
				<td>
					<fmt:message key="kmExpertFruit.courseContent" />:
				</td>
				<td style="width:85px">
					<input type="text" id="courseContent" name="courseContent" class="text" value="${kmExpertFruitForm.courseContent}" maxlength="255">&nbsp;&nbsp;
				</td>
				
				<!-- 参与人 -->
				<td>
					<fmt:message key="kmExpertFruit.participant" />:
				</td>
				<td style="width:85px">
					<input type="text" id="participant" name="participant" class="text" value="${kmExpertFruitForm.participant}" maxlength="64">
				</td>
			</tr>


			<!-- 课题开始时间段 -->
			<tr>
			  	<td>
					课题开始时间段：&nbsp;
				</td>
				<td class="content" style="width:85px">
			          <input type="text" size="20" readonly="true" class="text" 
			               name="beginTime" id="beginTime"
			               onclick="popUpCalendar(this,this,null,null,null,true,-1);"
			               alt=" vtype:'lessThen',link:'endTime', allowBlank:true,vtext:'结束时间要大于开始时间'"
			               
			               value="${beginTime}" />
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;至:
				</td>
				<td class="content" style="width:85px">
			          <input type="text" size="20" readonly="true" class="text" 
		                name="endTime" id="endTime"
		                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
		                alt=" vtype:'moreThen',link:'beginTime', allowBlank:true,vtext:'结束时间要大于开始时间'" 

		                value="${endTime}" />
				</td>

			</tr>
			
			<tr>
				<!-- 提交按钮 -->
				<td>
					<input type="button" class="btn" value="<fmt:message key="button.search"/>" onclick="sub();" />
				</td>
			
			</tr>
			
		</table>
	</html:form>




<content tag="heading">
	<fmt:message key="kmExpertFruit.list.heading" />
</content>

	<display:table name="kmExpertFruitList" cellspacing="0" cellpadding="0"
		id="kmExpertFruitList" pagesize="${pageSize}" class="table kmExpertFruitList"
		export="false"
		requestURI="${app}/kmmanager/kmExpertFruits.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="courseName" sortable="true"
			headerClass="sortable" titleKey="kmExpertFruit.courseName" href="${app}/kmmanager/kmExpertFruits.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="courseContent" sortable="true"
			headerClass="sortable" titleKey="kmExpertFruit.courseContent"  paramId="id" paramProperty="id"/>

	<display:column property="participant" sortable="true"
			headerClass="sortable" titleKey="kmExpertFruit.participant"  paramId="id" paramProperty="id"/>

	<display:column property="beginTime" sortable="true"
			headerClass="sortable" titleKey="kmExpertFruit.beginTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="kmExpertFruit.endTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" paramId="id" paramProperty="id"/>
 

		<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
			<a href="javascript:var id = '${kmExpertFruitList.id }';
		                        var url='${app}/kmmanager/kmExpertFruits.do?method=detail';
		                        url=url + '&id=' + id;
		                        location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		


		<display:setProperty name="paging.banner.item_name" value="kmExpertFruit" />
		<display:setProperty name="paging.banner.items_name" value="kmExpertFruits" />
	</display:table>
	
	

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>