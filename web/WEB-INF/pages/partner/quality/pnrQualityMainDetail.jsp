<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">



function edit(id){
       location.href= "../quality/qualityAction.do?method=edit&id=" +id;
};
function remove(id){

       location.href= "../quality/qualityAction.do?method=remove&id=" +id;
};

</script>

<table class="formTable">
	<caption>
		<div class="header center">质量报告信息</div>
	</caption>
	<tr>
	    <td class="label">
	       标题
	    </td>
	    <td colspan="3">
	    ${pnrQualityMain.title}
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       发布人
	    </td>
	    <td class="content">
		<eoms:id2nameDB id="${pnrQualityMain.publishUser}" beanId="tawSystemUserDao" />
	    </td>
	    <td class="label">
	       联系电话
	    </td>
	    <td class="content">
		  ${pnrQualityMain.publishTel}
	    </td>
	</tr>

	<tr>
	    <td class="label">
	       发布部门
	    </td>
	    <td colspan="3">
	       <eoms:id2nameDB id="${pnrQualityMain.publishDept}" beanId="tawSystemDeptDao" />
	    </td>
	</tr>	

	<tr>
	    <td class="label">
	       报告类型
	    </td>
<%
    String type = (String)request.getAttribute("type");
    if(type.equals("week")){
        type = "周";
    }else if(type.equals("month")){
        type = "月";
    }else if(type.equals("quarter")){
        type = "季度";
    }else if(type.equals("year")){
        type = "年";
    }
    
 %>	    
	    <td class="content">
           <%=type %>
	    </td>
	    <td class="label">
	       报告周期
	    </td>
	    <td class="content">
	    ${pnrQualityMain.cycle}
	    </td>
	</tr>	
	<tr>
	    <td class="label">
	       概述
	    </td>
	    <td colspan="3">
	       ${pnrQualityMain.summary }
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       关键字
	    </td>
	    <td colspan="3">
	       ${pnrQualityMain.keyWord }
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       报告
	    </td>
        <td class="content" colspan="3"> 
        					<eoms:download ids="${pnrQualityMain.report}"></eoms:download>
		 </td> 
	</tr>
	<tr>
	    <td class="label">
	       发布对象
	    </td>
	    <td colspan="3">
             <eoms:id2nameDB id="${pnrQualityMain.auditUser}" beanId="tawSystemUserDao" />
	    </td>
	</tr>
	
</table>
<table>
	<tr>
		<td>	
		    <c:if test="${pnrQualityMain.state=='1'}">      
		    <input type="button" class="btn" value="修改" onclick="edit('${pnrQualityMain.id}');"/>
      
      <input type="button" class="btn" value="删除" onclick="remove('${pnrQualityMain.id}');"/>		
      </c:if>
		</td>
	</tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>