<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<script type="text/javascript">

Ext.onReady(function(){
 	v = new eoms.form.Validation({form:'qualityAuditForm'});
xbox_dutyManTree = new xbox({"id":"dutyManTree","single":true,"showChkFldId":"auditName_temp","saveChkFldId":"auditId_temp","treeDataUrl":"/partner/xtree.do?method=userFromDept","btnId":"auditName_temp","treeRootId":"","checktype":"user","treeRootText":"审核人"});
});

function save(){
   if(v.check()){
       var frm = document.forms["qualityAuditForm"];
       frm.submit();
   }
};
function audit(){

   if(v.check()){
       var frm = document.forms["qualityAuditForm"];
       frm.action = "../quality/qualityAction.do?method=audit&state=1"
       frm.submit();
   }
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
           ${type }
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

<form action="../quality/qualityAction.do?method=audit" id="qualityAuditForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">质量报告审核</div>
	</caption>
    <input type=hidden name="mainId" value="${pnrQualityMain.id}"> 
	<tr>
	    <td class="label">
	       审核人
	    </td>
	    <td class="content">
		<eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao" />
	    </td>
	    <td class="label">
	       联系电话
	    </td>
	    <td class="content">
		  ${telphone}
	    </td>
	</tr>

	<tr>
	    <td class="label">
	       审核部门
	    </td>
	    <td colspan="3">
	       <eoms:id2nameDB id="${deptid}" beanId="tawSystemDeptDao" />
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       是否超时
	   </td>	
	   <td colspan="3">
           <select name="isOvertime" id="isOvertime" onchange="chooseType();" alt="allowBlank:false,vtext:'请选择周期'">
				<option value="">
					--请选择--
				</option>
				<option value="1">是</option>
				<option value="0">否</option>		
			</select>		   
	   
	   </td>
	</tr>
	<tr>
	    <td class="label">
	       是否合格
	   </td>	
	   <td colspan="3">
           <select name="isQualified" id="isQualified" onchange="chooseType();" alt="allowBlank:false,vtext:'请选择周期'">
				<option value="">
					--请选择--
				</option>
				<option value="1">是</option>
				<option value="0">否</option>		
			</select>	
	   </td>
	</tr>
	<tr>
	    <td class="label">
	       质量报告评分
	   </td>	

	    <td class="content" colspan="3">
	       <input  type="text" class="text" name="score" id="score"
					value=""/><font color="red" > （备注：根据报告质量进行评分，评分标准0-10分）</font>
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       备注
	   </td>	
	    <td class="content" colspan="3">
	       <input  type="text" class="text" name="remark" id="remark"  style="width: 90%"
					value=""/>
	    </td>
	</tr>
</table>

<table>
  <tr>
    <td>
      <input type="button" class="btn" value="通过" onclick="save();"/>
      
      <input type="button" class="btn" value="驳回" onclick="audit();"/>
    </td>
  </tr>
</table>
</form>
<%@ include file="/common/footer_eoms.jsp"%>