<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<jsp:directive.page import="com.boco.eoms.km.exam.util.KmExamSpecialtyConstants" />

<html:form action="/kmExamQuestionss.do?method=save" styleId="kmExamQuestionsForm" method="post" enctype="multipart/form-data">
	<fmt:bundle	basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<style type="text/css">
a {
	text-decoration: none;
	color: #3d7fb3;
}
a:hover {
	text-decoration: underline;
	color: #3d7fb3;
}
</style>

<script type="text/javascript">
var	t_rownum=${listLength};
var str=['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
var flag=true;

//动态增加附件选择框
function AddChoice(){
	var myTable = document.all.FileTable;
	//如果选项大于26，则提示不能再增加选项
	if(myTable.rows.length>=26){
		alert("选项数目达到最大，不能再增加选项");
		return;
	}
	//向表格中增加一行
	var myNewRow = myTable.insertRow(myTable.rows.length);
	//取得表格的总行数
	var aRows=myTable.rows;				
	//取得表格的总网格数
	var aCells=myNewRow.cells;
	//向新增行中增加2个网格
	var oCell1_1=aRows(myNewRow.rowIndex).insertCell(aCells.length);
	var oCell1_2=aRows(myNewRow.rowIndex).insertCell(aCells.length);
	var oCell1_3=aRows(myNewRow.rowIndex).insertCell(aCells.length);
	var oCell1_4=aRows(myNewRow.rowIndex).insertCell(aCells.length);			
	var rowId="rowId"+t_rownum;
	
	myNewRow.id=rowId;
	//设置2个网格的html文本
	if('${questionType}'==3)
		oCell1_4.innerHTML="<textarea rows='2' cols='50'  name='choice"+t_rownum +"' id='choice"+t_rownum+"' alt='allowBlank:false'>"+arguments[0]+"</textarea><input type='hidden' name='id"+ t_rownum +"' value=''>";
	else
		oCell1_4.innerHTML="<textarea rows='2' cols='50'  name='choice"+t_rownum +"' id='choice"+t_rownum+"' alt='allowBlank:false'></textarea><input type='hidden' name='id"+ t_rownum +"' value=''>";
	oCell1_3.innerHTML=	str[t_rownum];
	if('${questionType}'==1||'${questionType}'==3)
		oCell1_2.innerHTML="<input type='radio' indexed='true' id='checkall' name='checkall' value='"+str[t_rownum]+"'/>";
	else
    	oCell1_2.innerHTML="<input type='checkbox' indexed='true' id='checkall' name='checkall' value='"+str[t_rownum]+"'/>";
    if('${questionType}'!=3)
		oCell1_1.innerHTML="<input type='button' name=del"+t_rownum+" class='btn' value='删除' onclick='DeleteChoice(\""+rowId+"\");'>";	
	else 
		oCell1_1.innerHTML="<input type='hidden' name=del"+t_rownum+" class='btn' value='删除' onclick='DeleteChoice(\""+rowId+"\");'>";			   	
	if(t_rownum>0){
	    eoms.form.disableArea('del'+(t_rownum-1),true);
	}
	t_rownum=t_rownum+1;
	return;
}			
			
//动态删除附件选择框
function DeleteChoice(rowId){
	var myTable = document.all.FileTable;	
	t_rownum=t_rownum-1;
	if(t_rownum>0){	
	    eoms.form.enableArea('del'+(t_rownum-1),true);
	}
	myTable.deleteRow(eval(rowId).rowIndex);	
	return;
}

function DeleteChoiceDB(rowId,id){
	if(confirm('您确认删除吗？')){	   
	   hidden_submit_param(id);      
    }else
      return;
    DeleteChoice(rowId);
}

function change(doc){
    if(doc.value=="4"||doc.value=="5"||doc.value==""){
    	eoms.form.disableArea('choiceArea',true);
    	eoms.form.enableArea('answerArea',true);
    }else{
   		eoms.form.enableArea('choiceArea',true);
   		//eoms.form.disableArea('answerArea',true);
   		$('answerArea').style.display="none";
   		var initSize=4;   
   		if(doc.value=="3"){
       		initSize=2;       
        	while(t_rownum>2){
          		DeleteChoice("rowId"+(t_rownum-1));                   
       		}
       		if(flag){
          			AddChoice('正确');      
					AddChoice('错误'); 
     		}      
   		}else{
   			if(flag){
       			while(t_rownum<4){
          			AddChoice();      
       			}
     		}   
   		}
	}
}

function doSubmit(){
	var textareas = document.getElementsByTagName("textarea");
	for(var i=0;i<textareas.length;i++){
		if(textareas[i].name!="answer"&&textareas[i].value==""){
			alert("至少有一项未填写，请检查！");
			return;
		}
		if(textareas[i].name!="answer"&&textareas[i].value.length>500){
			alert("有一项内容填写过多，请检查！");
			textareas[i].style.color="red";
			return;
		}else{
			textareas[i].style.color="#000000";
		}
	}
	var questionType=$('questionType').value;
	if(questionType==1||questionType==2||questionType==3){
		showResult();
	}else{
		if(v.check()){
			if ($('specialtyID').value=="") {
				alert("请填写试题分类");
				return false;
			}
			$("kmExamQuestionsForm").submit();
		}
	}
}

function showResult(){
	var content;
	var orderBy;
	var choiceID;	
	var result = "[";							
	for(i=0;i<t_rownum;i++){			
	   orderBy=str[i];
	   content=$("choice"+i).value.replace(/\r/ig,"").replace(/\n/ig,"");	
	   choiceID=$("id"+i).value;
	   if(i>0){
	     result+=',';
	   }
	   result += '{content:\''+content+'\',orderBy:\''+orderBy+'\',id:\''+choiceID+'\'}';			
	}			
				
	result+="]"	;
	$('result').value=result;
	var checkedCount=0;
	var checks=document.getElementsByName('checkall');	  
	   var answer='';
	   for(var i =0; i<checks.length; i++){	   
	   if(checks[i].checked){	     
		 answer+=checks[i].value;
		 checkedCount++;
	   }
	}
	$('answer').value=answer;
	if(v.check()){
      if ($('specialtyID').value=="") {
         alert("请填写所属专业");
         return false;
       } 
      if ($('answer').value=="") {
         alert("请选择正确答案");
         return false;
       }else if($('questionType').value=="1"||$('questionType').value=="3"){
         if(checkedCount>1){
           alert("只能选择一个正确答案");
           return false;
         }
       }      	
	var url = 'kmExamQuestionss.do?method=save' ;
	$("kmExamQuestionsForm").action = url;
	$("kmExamQuestionsForm").submit();
	}		
}

function hidden_submit_param(id)
{	
	var ua      = navigator.userAgent;
    var opera   = /opera [56789]|opera\/[56789]/i.test(ua);
    var ie      = !opera && /msie [56789]/i.test(ua);       // preventing opera to be identified as ie
    var mozilla = !opera && /mozilla\/[56789]/i.test(ua);   // preventing opera to be identified as mz
	var submitURL='${app}/kmmanager/kmExamChoices.do?method=remove';
	var oGet = null;
	var oReq = null;			
		var param="id="+id;
		if (mozilla) {
			oReq = new XMLHttpRequest(); 
		} else {
			try { oReq=new ActiveXObject('MSXML2.XMLHTTP'); } catch(e) {
				try{ oReq=new ActiveXObject('Microsoft.XMLHTTP'); } catch(oc) { oReq=null }
			}
		}
		try {
			oReq.open("POST", submitURL, false);
			oReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			oReq.send(param);		
		} catch (e) {
			alert("隐含请求调用失败！");
			return oGet;
		}
		if (mozilla) {
			oGet = oReq.responseXML;
		} else {
			oGet = new ActiveXObject("MSXML2.DOMDocument");
			oGet.async=false;
			oGet.loadXML(oReq.responseText);
		}
		// 处理返回值
		//var retCodeNode = oGet.selectSingleNode("root/msg/content" );
		//var retCode = retCodeNode.text;
		//alert(retCode);
	
	}
	
	var tempTextRange = null;
	function showAddAccessory()
	{
		grin();
		var fileDiv = document.getElementById("addFile");
		var num = fileDiv.childNodes.length;
		//fileDiv.innerHTML+="<input type='file' name='uploadFile["+num+"].file' onchange='showAccessoryName(this)'>";
		var file = document.createElement("input");
		file.type = "file";
		file.name = "uploadFile["+num+"].file";
		file.onchange = function(){showAccessoryName(this);}
		fileDiv.appendChild(file);
	}
	function showAccessoryName(obj)
	{
		var path = obj.value;
		var type = path.substring(path.lastIndexOf(".")+1).toUpperCase();
		if(type!="JPEG"   &&   type!="JPEG"   &&   type!="JPG"   &&   type!="GIF")
		{
			alert("请上传图片类型(JPEG,JPEG,JPG,GIF)的附件");
			obj.parentNode.removeChild(obj);
			return;
		}
		var pos = path.lastIndexOf("/");
        if(pos == -1)
            pos = path.lastIndexOf("\\")
        var filename = path.substr(pos +1)
        
		var fileNameDiv = document.createElement("DIV");
		var deleteHander = document.createElement("A");
		deleteHander.href = "javascript:void(0)";
		deleteHander.onclick = function(){deleteAccessory(filename,obj,fileNameDiv)};
		deleteHander.innerHTML = "删除图片";
		fileNameDiv.innerHTML = filename;
		fileNameDiv.appendChild(deleteHander);
		document.getElementById("showName").appendChild(fileNameDiv);
		
		var myField = document.getElementById('question'); 
		//myField.value = myField.value.replace("[attachimg][/attachimg]","[attachimg]"+filename+"[/attachimg]");
		tempTextRange.text = "[attachimg]"+filename+"[/attachimg]";
		obj.style.display="none";
	}
	function deleteAccessory(filename,obj,fileNameDiv)
	{	
		if(obj!=null)
			obj.parentNode.removeChild(obj);
		document.getElementById("showName").removeChild(fileNameDiv);
		var str = document.getElementById('question').value;
		if(str.indexOf("[attachimg]")!=-1)
		{
			str = str.replace("[attachimg]"+filename+"[/attachimg]","");
			document.getElementById("question").value=str;
		}
	}
	function grin() {
		var myField; 
        myField = document.getElementById('question');
        
        if (document.selection) { 
                myField.focus();
                tempTextRange = document.selection.createRange(); 
   		 }
	} 
	function insertSpace() {
		var myField; 
        myField = document.getElementById('question');
        if (document.selection) { 
                myField.focus();
                tempTextRange = document.selection.createRange(); 
                tempTextRange.text = "________";
    	}
	}
</script>

<eoms:xbox id="tree1" 
    dataUrl="${app}/xtree.do?method=dept" 
    rootId="-1" 
    rootText='部门树' 
    valueField="deptId" 
    handler="deptName" 
    textField="deptName" 
    checktype="dept" 
    single="true">
</eoms:xbox>

<font color='red'>*</font>号为必填内容

<table class="formTable">
    <caption>
	    <div class="header center">
		    <fmt:message key="kmExamQuestions.form.heading" />
		</div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="kmExamQuestions.specialtyID" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" id="specialtyName" name="specialtyName" class="text" 
		        readonly="readonly"
				value='<eoms:id2nameDB id="${kmExamQuestionsForm.specialtyID}" beanId="kmExamSpecialtyDao" />'
				alt="allowBlank:false" />
				<input type="hidden" id=specialtyID name="specialtyID" value="${kmExamQuestionsForm.specialtyID}" />
		</td>
		<td class="label">
			<fmt:message key="kmExamQuestions.deptId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" id="deptName" name="deptName" class="text"
		        readonly="readonly"
				value='<eoms:id2nameDB id="${kmExamQuestionsForm.deptId}" beanId="tawSystemDeptDao" />'
				alt="allowBlank:false" />
			<input type="hidden" id=deptId name="deptId" value="${kmExamQuestionsForm.deptId}" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="kmExamQuestions.questionType" />&nbsp;<font color='red'>*</font>
		</td>
		
		<td class="content">
		    <eoms:dict key="dict-kmmanager" dictId="questionType" itemId="${questionType}" beanId="id2nameXML" />
		    <input type="hidden" id="questionType" name="questionType" value="${questionType}" />
		</td>
		<td class="label">
		    <fmt:message key="kmExamQuestions.difficulty" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="difficulty" isQuery="false" 
			    alt="allowBlank:false,vtext:'请选择问题难度'"
			    defaultId="${kmExamQuestionsForm.difficulty}"
			    selectId="difficulty" beanId="selectXML" />
		</td>
	</tr>

	<tr>
	    <td class="label">
			<fmt:message key="kmExamQuestions.question" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <textarea name="question" cols="50" id="question"
				class="textarea max" alt="allowBlank:false,maxLength:200">${kmExamQuestionsForm.question}</textarea>
			
			<div>
				<c:if test="${questionType==4}">
					<a href="javascript:void(0)" onclick="insertSpace();">插入填空</a><br/>
				</c:if>
			    <input type="hidden" id="accessoryHidden" name="accessory" value="${kmExamQuestionsForm.accessory}">
			    <a href="javascript:void(0)" onclick="showAddAccessory();">添加图片:</a>
				<div id="addFile"></div>
				<div id="showName"></div>
				<c:if test="${kmExamQuestionsForm.accessory!=null}">
					<script>
						var accessory = "${kmExamQuestionsForm.accessory}";
						var fileName = accessory.split(",");
						for(var i=0;i<fileName.length-1;i++)
						{
							 var fileNameDiv = document.createElement("DIV");
							 var deleteHander = document.createElement("A");
							 deleteHander.fileName = fileName[i];
							 deleteHander.href = "javascript:void(0)";
							 deleteHander.onclick = function(){
							     var question = document.getElementById("question");
							 	 var accessoryObj = document.getElementById("accessoryHidden");
							 	 question.value = question.value.replace("[attachimg]"+this.fileName+"[/attachimg]","");
							 	 accessoryObj.value = accessoryObj.value.replace(this.fileName+",",""); 
							 	 deleteAccessory(this.fileName,null,this.parentNode);
							 }
							 deleteHander.innerHTML = "删除图片";
							 fileNameDiv.innerHTML = fileName[i];
							 fileNameDiv.appendChild(deleteHander);
							 document.getElementById("showName").appendChild(fileNameDiv);
						}
					</script>
				</c:if>
			</div>
		</td>
	</tr>
	
	<tr id="choiceArea">
	    <td valign="bottom">
	    	<c:if test="${questionType!=3}">
	    		<c:if test="${empty kmExamQuestionsForm.questionID||kmExamQuestionsForm.createUser==sessionform.userid||sessionform.userid=='admin'}">
	        		<input type="button" class="btn" value="增加新选项" onclick="AddChoice();">
	        	</c:if>
	        </c:if>
	    </td>
	    <% int num = 0; %>
	    <td class="content" colspan="3">
	        <table id="FileTable" width="100%" border="1">
			<c:forEach items="${choiceList}" var="item">
			<tr id="rowId<%=num%>">
				<td>
					<c:if test="${empty kmExamQuestionsForm.questionID||kmExamQuestionsForm.createUser==sessionform.userid||sessionform.userid=='admin'}">
						<input type="button" name="del<%=num%>" class="btn" value="删除" onclick="DeleteChoiceDB('rowId<%=num%>','${item.choiceID}')">
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${questionType==1||questionType==3}">
							<input type="radio" indexed="true" id="checkall" name="checkall" value="${item.orderBy}" />
						</c:when>
						<c:otherwise>
							<input type="checkbox" indexed="true" id="checkall" name="checkall" value="${item.orderBy}" />
						</c:otherwise>
					</c:choose>
				</td>
				<td>
				    ${item.orderBy}
				</td>
				<td class="text" id="choice" width="90%">
					<textarea name="choice<%=num%>" rows="2" cols="50" id="choice<%=num%>" alt="allowBlank:false,maxLength:1000">${item.content}</textarea>
					<input type="hidden" name="id<%=num%>" id="id<%=num%>" value="${item.choiceID}">
				</td>				
			</tr>
			<% num++; %>
			</c:forEach>
	        </table>
	    </td>
	</tr>
	
	<tr id="answerArea">
		<td class="label">
			<fmt:message key="kmExamQuestions.answer" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			<textarea name="answer" cols="50" id="answer" class="textarea max" alt="allowBlank:false,maxLength:2000">${kmExamQuestionsForm.answer}</textarea>
		</td>
	</tr>

	<tr>
				<td class="label">
				</td>
				<td class="content" colspan="3">
				</td>
			</tr>
			<html:hidden property="orderBy" value="" />
			<html:hidden property="isDeleted" value="0" />
			<html:hidden property="questionName" value="-1" />
		</table>

		<script>
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExamQuestionsForm'});	
		
	var config = {
		btnId:'specialtyName',
		treeDataUrl:'${app}/kmmanager/kmExamSpecialtys.do?method=getNodesRadioTree',
		treeRootId:'<%=KmExamSpecialtyConstants.TREE_ROOT_ID%>',
		treeRootText:'<fmt:message key="kmExamQuestions.specialtyID" />',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'specialtyName',
		saveChkFldId:'specialtyID'
	}
	tree = new xbox(config);
	init();
});

 function init(){	
var checksinit=document.getElementsByName('checkall');	
var	answerinit='${kmExamQuestionsForm.answer}';
//alert(checksinit.length);
for(var i=0;i<checksinit.length;i++){
   var value=checksinit[i].value+'';
   if(answerinit.indexOf(value)>-1){      
      checksinit[i].checked=true;
   }
} 
var questionID='${kmExamQuestionsForm.questionID}';
if(questionID!=null&&questionID!=''){flag=false;}
change($('questionType'));
if(t_rownum>1){
	  for(var i=1;i<t_rownum;i++){
	    eoms.form.disableArea('del'+(i-1),true);
	  }  
	}
}	
</script>
	</fmt:bundle>

	<table>
		<tr>
			<td>
				<c:if test="${not empty kmExamQuestionsForm.questionID&&(kmExamQuestionsForm.createUser==sessionform.userid||sessionform.userid=='admin')}">
					<input type="button" class="btn"
						value="<fmt:message key="button.save"/>" onclick="doSubmit()" />
					<input type="button" class="btn"
						value="<fmt:message key="button.delete"/>"
						onclick="javascript:if(confirm('<fmt:message key="message.delMessage"/>')){
						var url='${app}/kmmanager/kmExamQuestionss.do?method=remove&questionID=${kmExamQuestionsForm.questionID}';
						location.href=url}" />
				</c:if>
				<c:if test="${empty kmExamQuestionsForm.questionID}">
					<input type="button" class="btn"
						value="<fmt:message key="button.save"/>" onclick="doSubmit()" />
				</c:if>
			</td>
		</tr>
	</table>
	<html:hidden property="questionID"
		value="${kmExamQuestionsForm.questionID}" />
	<html:hidden property="result" value="" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>