<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
<!--
Date.prototype.getWeekOfYear = function(weekStart) { // weekStart：每周开始于周几：周日：0，周一：1，周二：2 ...，默认为周日  
    weekStart = (weekStart || 0) - 0;  
    if(isNaN(weekStart) || weekStart > 6)  
        weekStart = 0;    
  
    var year = this.getFullYear();  
    var firstDay = new Date(year, 0, 1);  
    var firstWeekDays = 7 - firstDay.getDay() + weekStart;  
    var dayOfYear = (((new Date(year, this.getMonth(), this.getDate())) - firstDay) / (24 * 3600 * 1000)) + 1;  
    return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;  
} 

var date = new Date(2013, 4, 7); // 注意：JS 中月的取值范围为 0~11  
var weekOfYear = date.getWeekOfYear(); // 当前日期是本年度第几周
//-->

Ext.onReady(function(){
 	v = new eoms.form.Validation({form:'qualityMainForm'});
xbox_dutyManTree = new xbox({"id":"dutyManTree","single":true,"showChkFldId":"auditName_temp","saveChkFldId":"auditId_temp","treeDataUrl":"/partner/xtree.do?method=userFromDept","btnId":"auditName_temp","treeRootId":"","checktype":"user","treeRootText":"审核人"});
});

function save(){
   if(v.check()){
       var frm = document.forms["qualityMainForm"];
       frm.submit();
   }
};
function audit(){

   if(v.check()){
       var frm = document.forms["qualityMainForm"];
       frm.action = "../quality/qualityAction.do?method=audit"
       frm.submit();
   }
};

window.onload = function(){
	//修改时，自动加载原来的地市县区显示在修改页面
    var type = '${pnrQualityMain.type}';
		
	if(type!=''){
 		document.getElementById("type").value = type;
	}
}

</script>

<form action="../quality/qualityAction.do?method=save" id="qualityMainForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">新增质量报告</div>
	</caption>
	<tr>
	    <td class="label">
	       标题
	    </td>
	    <td colspan="3">
	       <input  type="text" class="text" name="title" id="title" style="width: 90%"
					alt="allowBlank:false,vtext:'标题不能为空或者超过100个汉字！',maxLength:200"
					value="${pnrQualityMain.title}"/>
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
	    <td class="content">
           <select name="type" id="type" value= "${pnrQualityMain.type}" onchange="chooseType();" alt="allowBlank:false,vtext:'请选择周期'">
				<option value="">
					--请选择周期--
				</option>
				<option value="week">周</option>
				<option value="month">月</option>
				<option value="quarter">季度</option>
				<option value="year">年</option>				
			</select>	
	    </td>
	    <td class="label">
	       报告周期
	    </td>
	    <td class="content">
	       <input  type="text" class="text" name="cycle" id="cycle"
					value="${pnrQualityMain.cycle}" readonly="true"/><font color="red" > 选择类型以后自动生成</font>
	    </td>	    
	</tr>	
	<tr>
	    <td class="label">
	       概述
	    </td>
	    <td colspan="3">
	       <input  type="text" class="text" name="summary" id="summary"  style="width: 90%"
					alt="allowBlank:false,vtext:'规则名称 不能超出1000个汉字！',maxLength:2000"
					value="${pnrQualityMain.summary }"/>
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       关键字
	    </td>
	    <td colspan="3">
	       <input  type="text" class="text" name="keyWord" id="keyWord"  style="width: 90%"
					alt="allowBlank:false,vtext:'规则名称 不能超出1000个汉字！',maxLength:2000"
					value="${pnrQualityMain.keyWord }"/>
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       报告
	    </td>
        <td class="content" colspan="3" height="100px">
               <iframe id="report" name="report" class="uploadframe" frameborder="0" scrolling="auto" src="/partner/accessories/pages/upload.jsp?appId=pnrcontact&filelist=null&idField=file&startsWith=0" style="height:80%;width:100%"></iframe><input type="hidden" name="report" id="report" value="null" alt="blankText:'请上传附件。',iframeId:'IFrame1-file',allowBlank:true,vtext:'证书附件'"/>
        </td> 
	</tr>
	<tr>
	    <td class="label">
	       发布对象
	    </td>
	    <td colspan="3">
         <input type='text' id="auditName_temp" name="auditName_temp"  readonly="true" value="${pnrQualityMain.auditUser }" class="text" />
         <input type="hidden" name='auditUser' id="auditId_temp" value="${pnrQualityMain.auditUser }"/> 
	    </td>
	</tr>
	
</table>
</form>

<table>
  <tr>
    <td>
      <input type="button" class="btn" value="保存草稿" onclick="save();"/>
      
      <input type="button" class="btn" value="提交审核" onclick="audit();"/>
    </td>
  </tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>