<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript">
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}

function showQuestions()
{
	var url = "../kmmanager/kmExamRandomQuestions.do?method=searchBySpcaility";
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);		     			
		     		return json;
				}	
	     }
	    // httpRequest.send(null);
	}
}

function QuestionList()
{
	this.coreDom = document.getElementById("part1");
	this.coreDom.part1 = document.getElementById("part1");
	this.coreDom.part2 = document.getElementById("part2");
	this.coreDom.part3 = document.getElementById("part3");
	this.coreDom.part4 = document.getElementById("part4");
	this.coreDom.part5 = document.getElementById("part5");
	
	
	this.coreDom.part1.onclick = function()
								{
									alert("part1");
								}
	this.coreDom.part2.onclick = function()
								{
									alert("part2");
								}
	this.coreDom.part3.onclick = function()
								{
									alert("part3");
								}
	this.coreDom.part4.onclick = function()
								{
									alert("part4");
								}
	this.coreDom.part5.onclick = function()
								{
									alert("part5");
								}
}
function AddChoice() {    
     window.open('${app}/kmmanager/kmExamRandomQuestions.do?method=searchForChoice&id=${id}',null,'left=200,top=100,height=600,width=900,scrollbars=yes,resizable=yes');
  }
function initDomain(questionIDStr,contentIDStr,scoreStr,questionType,flagValue){
alert(questionIDStr);
alert(contentIDStr);
alert(scoreStr);
alert(questionType);
     //AddSpan(questionType);     
     //loadDomain(questionIDStr,contentIDStr,scoreStr,questionType,flagValue);
}
</script>
<c:set var="buttons">
	<br/>
	<input type="button" class="btn"
		onclick="AddChoice();"
		value="添加试题" />
</c:set>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
<content tag="heading">
	<center><b><fmt:message key="kmExamRandomQuestion.list.heading" /></b></center>
</content>

<div id="testContent"></div>
<c:out value="${buttons}" escapeXml="false" />
</fmt:bundle>
<script type="text/javascript">
 var _tabs;
  Ext.onReady(function(){
      var tabConfig = {
          items:[
              {id:'part1', text:'单选题', url:'${app}/kmmanager/kmExamRandomQuestions.do?method=searchBySpcaility&questionType=1&id=${id}', isIframe : true},
              {id:'part2', text:'多选题', url:'${app}/kmmanager/kmExamRandomQuestions.do?method=searchBySpcaility&questionType=2&id=${id}', isIframe : true},
              {id:'part3', text:'判断题', url:'${app}/kmmanager/kmExamRandomQuestions.do?method=searchBySpcaility&questionType=3&id=${id}', isIframe : true},
              {id:'part4', text:'填空题', url:'${app}/kmmanager/kmExamRandomQuestions.do?method=searchBySpcaility&questionType=4&id=${id}', isIframe : true},
              {id:'part5', text:'简答题', url:'${app}/kmmanager/kmExamRandomQuestions.do?method=searchBySpcaility&questionType=5&id=${id}', isIframe : true}
          ]
      };
      _tabs = new eoms.TabPanel('testContent', tabConfig);
  });
	
</script>
<%@ include file="/common/footer_eoms.jsp"%>