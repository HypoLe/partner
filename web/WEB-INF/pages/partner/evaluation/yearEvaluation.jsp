<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>



<div class="message" id="msgDiv" style="width: 300;display: none;font: bold;">${msg }</div>

<!-- 如果存在考核指标的话就显示列表，否则给出提示。 -->

<table id="sheet" class="formTable">
			<td colspan="4">
				<div class="ui-widget-header">
					可使用的模板
				</div>
			</td>
	<display:table name="templateList" cellspacing="0" cellpadding="0"
		id="templateList" pagesize="${pagesize}"
		requestURI=""
		class="table templateList" export="false"
		size="${size}">
		<display:column media="html" title="${myCheckboxAllBtn}">
			<input type="checkbox" class="checkAble" value="${templateList.id}" id="${templateList.id}" />
		</display:column>
		<display:column property="tmplnm"   
			headerClass="sortable" title="考核模板名" />

		<display:column property="creator"  
			headerClass="sortable" title="考核模板创建人" >
			</display:column>
		<display:column property="crtdttm"  format="{0,date,yyyy-MM-dd}" 
			headerClass="sortable" title="考核模板创建时间" />
			
		
				
		<display:column title="查看" headerClass="sortable">
			<a href="${app}/partner/evaluation/evaluation.do?method=templateOnlyView&nodeId=${templateList.id }">
				<img src="${app}/images/icons/search.gif"/>
			</a>
		</display:column>
		
	</display:table>
</table>
<br />


<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%;" id="eomsUserBoxDiv">

<form id="evaluationEntity" action="${app}/partner/evaluation/evaluationEntity.do?method=saveStartEvaluation" method="post">
<span style="color:red;font-size:12pt">${msg}</span>
<input type="hidden" id="tmpltyp" name="tmpltyp" value="${tmpltyp}" />
<table class="formTable" >
<tr>
			<td class="label">考核年度</td>
			<td class="content"><select size='1'
				name='year' id='year'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011' >2011年</option>
				<option value='2012' selected="selected">2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>

		</tr>
		<tr>
		   <td class="label">被考核单位</td>
			<td class="content">
				<input type="text" id="evaluationTarget" name="evaluationTarget" class="text" alt="allowBlank:false" readonly="true" value="" />
				<input type="hidden" id="monitorCompany" name="monitorCompany" />
			</td>
			<!--  
			<td class="label">考核执行人</td>
			<td class="content">
				<input type="text" id="executor" name="executor" class="text" alt="allowBlank:false" readonly="true" value="" />
				<input type="hidden" id="iUser" name="iUser" />
			</td>
			-->
		</tr>
</table>

<br/>

<input type="button" id="showDetail" value="查询当前可发起的类型" class="btn" name="showDetail" onclick="loadDetail()";/>
<div id="detail"></div>

	<input type="hidden" name="templateId" id="templateId" />

	<input type="button" id="startAppraisal" value="发起考核" class="btn" name="startAppraisal" style="display:none;"/>

</form>
</div>
<!--  
<eoms:xbox id="executor" dataUrl="${app}/xtree.do?method=userFromDept"
	rootId="1" rootText='联通公司人员' valueField="iUser"
	handler="executor" textField="executor" checktype="user" single="true">
</eoms:xbox>
-->
<eoms:xbox id="evaluationTarget" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
	rootId="" rootText='${sessionScope.sessionform.rootAreaName}' valueField="monitorCompany"
	handler="evaluationTarget" textField="evaluationTarget" checktype="dept"
	single="true"></eoms:xbox>

<script type="text/javascript">
var currentKey="";
function createXmlHttpRequest(){
	var xmlHttp = null;
	try{
		//Firefox, Opera 8.0+, Safari
	     xmlHttp=new XMLHttpRequest();
	}catch(e){
		//IEIE7.0以下的浏览器以ActiveX组件的方式来创建XMLHttpRequest对象
		 var MSXML = ['MSXML2.XMLHTTP.6.0','MSXML2.XMLHTTP.5.0',
		'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
		'MSXML2.XMLHTTP','Microsoft.XMLHTTP'];
		 
		 for(var n = 0; n < MSXML.length; n ++){
		    try{
		    	xmlHttp = new ActiveXObject(MSXML[n]);
		      break;
		    }catch(e){
		    }
		  }
	}
	return xmlHttp;
}
 var myJ=$.noConflict();
 myJ(function(){
	    v = new eoms.form.Validation({form:'evaluationEntity'});
        myJ('input.checkAble:checkbox').unbind('click');
 		myJ('input#useLastTemplate').click(function(){
 			if(myJ(this).attr('checked')==true){
 				myJ("input.checkAble:checkbox[id!="+'${appraialTemplateId}'+"]").removeAttr('checked');
	 			myJ('input#'+'${appraialTemplateId}').attr('checked','checked');
	 			_TemplateId='${appraialTemplateId}';
	 		}else{
	 			myJ('input.checkAble:checkbox').removeAttr('checked');
 				_TemplateId='';
	 		}
 		});
 		myJ('input.checkAble:checkbox').click(function(e){
			if(myJ(this).attr('checked')==true){
				_TemplateId=e.target.value;
				myJ('input#useLastTemplate').removeAttr('checked');
				myJ("input.checkAble:checkbox[id!="+e.target.id+"]").removeAttr('checked');
			}else{
				_TemplateId='';
			}
		});
 	
	myJ('input[type=button]').bind('click',function(event){
  			var buttonId= event.target.id;
  			var month  = myJ('#month').val();
  			var year  = myJ('#year').val();
  		//	var iUser  = myJ('#iUser').val();
  			var monitorCompanyName  = myJ('#monitorCompanyName').val();
  			var monitorCompany = myJ('#monitorCompany').val();
  			
  			//提交选中的模板
  			 if(buttonId=='startAppraisal'){
				var id = "";
				myJ(':checked.checkAble').each(	
					function(){
						id += myJ(this).val();
					}
				);
				if(month==""){
					alert("请选择考核月度");
					return false;
				}
			
				if(monitorCompanyName==""){
					alert("请选择被考核单位");
					return false;
				}
				if(monitorCompany.trim()+"_"+year.trim()!=currentKey){
					alert("请重新查询可发起的类型");
					return false;
				}
//				if(iUser==""){
//					alert("请选择考核执行人");
//					return false;
//				}
				if(id==""){
					alert("请选择需要关联的模板");
					return false;
				}
				if(confirm('确认发起考核吗?')){
					myJ('#templateId').val(id);
					myJ('#evaluationEntity').submit();
					
					
				}else{
					return false;
				}
  			}
	});
 });
 function loadDetail(){
 var monitorCompany=document.getElementById("monitorCompany");
 var year=document.getElementById("year");
 var div=document.getElementById("detail");
 var button=document.getElementById("startAppraisal");
 if(monitorCompany==null||monitorCompany.value.trim()==""){
 alert("请选择被考核的公司");
 return;
 }
 if(year==null||year.value.trim()==""){
 alert("请选择被考核的年");
 return;
 }

 currentKey=monitorCompany.value.trim()+"_"+year.value.trim();
 button.style.display="none";
	var ajax = createXmlHttpRequest();
	ajax.open("POST","${app}/partner/evaluation/evaluationEntity.do?method=showYearDetail",true);
	ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4 && ajax.status==200){
			var result = ajax.responseText;
          div.innerHTML=result;
          var ableInitiate=document.getElementById("ableInitiate");
          
          if(ableInitiate.value.trim()=="true"){
          
          button.style.display="block";
          
          
          
          }
		}
	};
	ajax.send("id="+monitorCompany.value.trim()+"&year="+year.value.trim());

}


</script>

<%@ include file="/common/footer_eoms.jsp"%>