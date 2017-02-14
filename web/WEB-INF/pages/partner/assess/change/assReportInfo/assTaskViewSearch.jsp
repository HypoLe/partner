<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<jsp:directive.page import="java.util.List"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assTaskViewForm'});
})

function getCycle()
		{    
			var taskId = document.getElementById("taskId").value;
			var url="<%=request.getContextPath()%>/partner/assess/changeAssExecute.do?method=getCycle&taskId="+taskId;
			var timeType=getResponseText(url);
			var cycleName='月';
			document.getElementById("timeType").value = timeType;
			document.getElementById('year').value ='${year}';
			if(timeType == 'year'){
			 cycleName = '年';
			 document.getElementById('monthSpan').style.display='none';
			 document.getElementById('month').disabled=true; 
			 document.getElementById('quarterSpan').style.display='none';
			 document.getElementById('quarter').disabled=true; 
			}else if(timeType == 'quarter'){
			 cycleName = '季度';
			 document.getElementById('monthSpan').style.display='none';
			 document.getElementById('month').disabled=true; 
			 document.getElementById('quarterSpan').style.display='';
			 document.getElementById('quarter').disabled=false; 
			 document.getElementById('quarter').value ='${quarter}';
			 document.getElementById('month').value ='';			 
			}else if(timeType == 'month'){
			 cycleName = '月';
			 document.getElementById('monthSpan').style.display='';
			 document.getElementById('month').disabled=false; 
			 document.getElementById('quarterSpan').style.display='none';
			 document.getElementById('quarter').disabled=true; 
			 document.getElementById('month').value ='${month}';
			 document.getElementById('quarter').value ='';			 
			}
			document.getElementById('cycleName').innerHTML = cycleName;
			
		}
function getResponseText(url) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
   
  
}
function checkEvaTime(){
    var task = document.getElementById("taskId").options[document.getElementById("taskId").selectedIndex].text;
    var year = document.getElementById("year").value;
    var month = document.getElementById("month").value;
    var time = year+"-"+month+"-01";//Date.parse(startDate)
    if(year==""||month=="")return true;
    else{
        var length = task.length;
        task = task.substring(length-11,length-3)+"01";
        if(task<=time)return true;
        else if(task>time)return false; 
    }
}
function isSubmit(){
    //if(checkEvaTime())return true;
    //else if(!checkEvaTime()){
       //alert("后评估月份在模板激活时间之后！");
       //return false;
    }
</script>

<html:form action="/changeAssReportInfo.do?method=taskList" styleId="assTaskViewForm" method="post" > 
<table class="formTable" id="form">
	<caption>
		<div class="header center">后评估实例查询</div>
	</caption>
	<input type="hidden" id="queryType" name="queryType" value="run"/>			
	<input type='hidden' id='timeType' name='timeType' value='month'/>
	<tr>
		<td class="label">
			地市
		</td>
		<td class="content">
			<select name="areaId" id="areaId" 
						alt="allowBlank:false,vtext:'请选择后评估模板'">
				<c:forEach items="${areaList}" var="areaId"> 
				${areaId}
					<option value="${areaId}">
						<eoms:id2nameDB id="${areaId}" beanId="tawSystemAreaDao" />
					</option>
				</c:forEach>
			</select>
		</td>
		<td class="label">
			后评估模板
		</td>
		<td class="content">
			<select name="taskId" id="taskId" 
						alt="allowBlank:false,vtext:'请选择后评估模板'" onchange="getCycle()">
				<logic:iterate id="taskList" name="taskList">
				<option value="${taskList.id}">
					<eoms:id2nameDB id="${taskList.templateId}" beanId="lineAssTemplateDao" />
				</option>
				</logic:iterate>
			</select>
		</td>
	</tr>
	<tr>
		<td class="label">
			时间周期类型
		</td>
		<td class="content">
			<span id='cycleName'>月<span>
		</td>
		
		<td class="label">
			选择时间
		</td>
		<td class="content">
			<select name="year" id="year" alt="allowBlank:false,vtext:'请选择年份'">
				<option value="">请选择</option>
				<option value="2005">2005</option>
				<option value="2006">2006</option>
				<option value="2007">2007</option>
				<option value="2008">2008</option>
				<option value="2009">2009</option>
				<option value="2010">2010</option>
				<option value="2011">2011</option>
				<option value="2012">2012</option>
				<option value="2013">2013</option>
				<option value="2014">2014</option>
				<option value="2015">2015</option>
			</select>年
			<span id='monthSpan'>
			<select name="month" id="month" alt="allowBlank:false,vtext:'请选择月份'">
				<option value="">请选择</option>
				<option value="01">1</option>
				<option value="02">2</option>
				<option value="03">3</option>
				<option value="04">4</option>
				<option value="05">5</option>
				<option value="06">6</option>
				<option value="07">7</option>
				<option value="08">8</option>
				<option value="09">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>月
			</span>
			<span id='quarterSpan'>
			<select name="quarter" id="quarter" alt="allowBlank:false,vtext:'请选择季度'">
				<option value="">请选择</option>
				<option value="01">1</option>
				<option value="02">2</option>
				<option value="03">3</option>
				<option value="04">4</option>
			</select>季度
			</span>
		</td>
	</tr>
</table>
<table id="submit-btn" align="left">
	<tr>
		<td valign="top">
			<input type="submit" class="btn" value="查询后评估" />
		</td>
		<td>
		<div id="tree" style="overflow:auto; border:1px solid #c3daf9;"></div>
		</td>
	</tr>
</table>
</html:form>
<script type="text/javascript">
	eoms.loadCSS(eoms.appPath+"/styles/assess/style.css");
	var tree, treeLoader, root;
	Ext.onReady(function(){
		getCycle();
		treeLoader = new Ext.tree.TreeLoader({
		dataUrl : "${app}/partner/assess/changeAssTrees.do?method=getTemplateNodes&areaId=${areaId}"
		});
		
		treeLoader.on('beforeload',function(treeLoader,node,callback){
			treeLoader.baseParams['nodeType'] = node.attributes.nodeType || '';
			treeLoader.baseAttrs = this.baseAttrs;
		},this);
	
		tree = new Ext.tree.TreePanel("tree", {
			animate : true,
			enableDD : false,
			containerScroll : true,
			loader : treeLoader
		});
	
		root = new Ext.tree.AsyncTreeNode({
			id : "<%=AssConstants.TREE_ROOT_ID%>",
			text : "查看后评估树",
			nodeType : 'root'
		});
		tree.setRootNode(root);	
		tree.render();
	});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
