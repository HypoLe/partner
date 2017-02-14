<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.km.exam.webapp.form.KmExamRandomTestForm"%>
<jsp:directive.page
	import="com.boco.eoms.km.exam.util.KmExamTestSpecialtyConstants" />
<style>
#testDetail {
	
}

#mainTable {
	margin: 15px 15px 15px 15px;
	border: 0px #80CCFF solid;
	width: expression(document . body . clientWidth-75 +   'px');
	float: center;
}

#leftPart {
	margin: 15px 0px 15px 15px;
	border: 1px #80CCFF solid;
	width: 100px;
	height: expression(document . body . clientHeight-179 +   'px');
	float: left;
}

#leftPart td {
	line-height: 25px;
	cursor: pointer;
}

#mainPart {
	margin: 15px 15px 15px 15px;
	border: 1px #80CCFF solid;
	width: expression(document . body . clientWidth-250 +   'px');
	height: expression(document . body . clientHeight-179 +   'px');
}

#editPart {
	margin: 10px;
}

#part1 div,#part2 div,#part3 div,#part4 div,#part5 div {
	padding: 0px 20px 20px 20px;
}
</style>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExamRandomTestForm'});
});

function calculateTime()
{
	var beginTime = document.getElementById("testBeginTime").value;
	var endTime = document.getElementById("testEndTime").value;
	
	if(endTime!=""&&beginTime!="")
	{	
		var endDate = endTime.toDate();
		var beginDate = beginTime.toDate();	    
		var seconds = (endDate.getTime() - beginDate.getTime()) / 1000;
		var minutes = parseInt(seconds/60);

		if(minutes<5){
			alert("请检查试卷的生效和失效时间，考试时间至少为5分钟！");
		}

		document.getElementById("testDuration1").value=minutes;
	}
}

function synchronize(isSynchronized)
{
	if(isSynchronized==1){
	    calculateTime();
		document.getElementById("synchronizedTime").style.display="";
		document.getElementById("noSynchronizedTime").style.display="none";
	}
	else if(isSynchronized==0){
		document.getElementById("synchronizedTime").style.display="none";
		document.getElementById("noSynchronizedTime").style.display="";
	}
}

String.prototype.toDate = function(style) {
if (style == null) style = 'yyyy-MM-dd hh:mm:ss';
    var compare = {
         'y+' : 'y',
         'M+' : 'M',
         'd+' : 'd',
         'h+' : 'h',
         'm+' : 'm',
         's+' : 's'
    };

    var result = {
        'y' : '',
        'M' : '',
        'd' : '',
        'h' : '00',
        'm' : '00',
        's' : '00'
    };

    var tmp = style;
    for (var k in compare) {
        if (new RegExp('(' + k + ')').test(style)) {
              result[compare[k]] = this.substring(tmp.indexOf(RegExp.$1), tmp.indexOf(RegExp.$1) +RegExp.$1.length);
       }
    }
    return new Date(result['y'], result['M'] - 1, result['d'], result['h'], result['m'], result['s']);

}

cal.onAfterClose = function(value, el){
    if(el.id=="testBeginTime"){
        if(document.getElementById("testEndTime").value == ''){
        	alert("请选择“试卷失效时间”！");
        	return;
        }
  	}
    else if(el.id=="testEndTime"){
        if(document.getElementById("testBeginTime").value == ''){
        	alert("请选择“试卷生效时间”！");
        	return;
        }
  	}

  	calculateTime();
};
</script>

<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" rootId="-1"
	rootText='部门树' valueField="belongToDept" handler="deptName"
	textField="deptName" checktype="dept" single="false">
</eoms:xbox>

<html:form action="/kmExamRandomTests.do?method=save" styleId="kmExamRandomTestForm" method="post">
	<fmt:bundle	basename="com/boco/eoms/km/config/applicationResource-kmmanager">

		<font color='red'>*</font>号为必填内容
		<caption>
			<div class="header center">
				<b> 
					<fmt:message key="kmExamTest.form.heading" />
					<div style="width: 90%; text-align: right; margin-top: 5px; position: relative;">
						考试时间是否统一：
						<c:choose>
							<c:when test="${kmExamRandomTestForm.isSynchronized==1}">
						是<input type="radio" name="isSynchronized" value="1" checked
									onclick="synchronize(1)" />&nbsp;&nbsp;
						否<input type="radio" name="isSynchronized" value="0"
									onclick="synchronize(0)" />
							</c:when>
							<c:otherwise>
						是<input type="radio" name="isSynchronized" value="1"
									onclick="synchronize(1)" />&nbsp;&nbsp;
						否<input type="radio" name="isSynchronized" value="0" checked
									onclick="synchronize(0)" />
							</c:otherwise>
						</c:choose>
					</div>
				</b>
			</div>			
		</caption>

		<div id="container">
			<div id="testInfo">
				<div id="mainTable">
					<table class="formTable">
						<tr>
							<td class="label">
								<fmt:message key="kmExamTest.testName" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content" colspan="3">
								<html:text property="testName" styleId="testName"
									styleClass="text max"
									alt="allowBlank:false,vtext:'',maxLength:100"
									value="${kmExamRandomTestForm.testName}" />
							</td>
						</tr>

						<tr>
							<td class="label">
								<fmt:message key="kmExamTest.test_description" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content" colspan="3">
								<textarea name="testDescription" id="question"
									class="textarea max" alt="allowBlank:false,maxLength:500">${kmExamRandomTestForm.testDescription}</textarea>
							</td>
						</tr>

						<tr>
							<td class="label">
								<fmt:message key="kmExamTest.testBeginTime" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<input type="text" size="20" readonly="true" class="text"
									name="testBeginTime" id="testBeginTime"
									onclick="popUpCalendar(this, this);"
									alt="vtype:'lessThen',link:'testEndTime',allowBlank:false,vtext:'请选择试卷生效时间...'"
									value="${kmExamRandomTestForm.testBeginTime}" />
							</td>
							<td class="label">
								<fmt:message key="kmExamTest.testEndTime" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<input type="text" size="20" readonly="true" class="text"
									name="testEndTime" id="testEndTime"
									onclick="popUpCalendar(this, this);"
									alt="vtype:'moreThen',link:'testBeginTime',allowBlank:false,vtext:'请选择试卷失效时间...'"
									value="${kmExamRandomTestForm.testEndTime}" />
							</td>
						</tr>

						<tr>
							<td class="label">
								<fmt:message key="kmExamTest.testDuration" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<c:choose>
									<c:when test="${kmExamRandomTestForm.isSynchronized==1}">
										<div id="noSynchronizedTime" style="display: none">
											<html:select property="testDuration" styleId="testDuration"
												styleClass="text medium"
												alt="allowBlank:false,vtext:'请选择答题时间（分钟）...'"
												value="${kmExamRandomTestForm.testDuration}">
												<c:forEach var="x" begin="5" end="120" step="5">
													<html:option value="${x}">${x}</html:option>
												</c:forEach>
											</html:select>
										</div>
										<div id="synchronizedTime">
											<html:text property="testDuration" styleId="testDuration1"
												styleClass="text medium"
												alt="allowBlank:false,vtext:'',vtype:'number',maxLength:5"
												value="${kmExamRandomTestForm.testDuration==null?0:kmExamRandomTestForm.testDuration}" readonly="true"/>
										</div>
									</c:when>
									<c:otherwise>
										<div id="noSynchronizedTime">
											<html:select property="testDuration" styleId="testDuration"
												styleClass="text medium"
												value="${kmExamRandomTestForm.testDuration}">
												<c:forEach var="x" begin="5" end="120" step="5">
													<html:option value="${x}">${x}</html:option>
												</c:forEach>
											</html:select>
										</div>
										<div id="synchronizedTime" style="display: none">
											<html:text property="testDuration" styleId="testDuration1"
												styleClass="text medium"
												alt="allowBlank:false,vtext:'',vtype:'number',maxLength:5"
												value="${kmExamRandomTestForm.testDuration==null?0:kmExamRandomTestForm.testDuration}" readonly="true"/>
										</div>
									</c:otherwise>
								</c:choose>
							</td>

							<td class="label">
								<fmt:message key="kmExamTest.specialtyID" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<input type="text" id="specialtyName" name="specialtyName"
									class="text" readonly="readonly"
									value='<eoms:id2nameDB id="${kmExamRandomTestForm.specialtyID}" beanId="kmExamTestSpecialtyDao" />'
									alt="allowBlank:false" />
								<input type="hidden" id=specialtyID name="specialtyID"
									value="${kmExamRandomTestForm.specialtyID}" />
							</td>
						</tr>

						<tr>
							<td class="label">
								<fmt:message key="kmExamTest.totalScore" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<html:text property="totalScore" styleId="totalScore"
									styleClass="text medium"
									alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数',allowBlank:false,vtext:'',maxLength:5"
									value="${kmExamRandomTestForm.totalScore}" />
							</td>
							<td class="label">
								及格分数&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<html:text property="passScore" styleId="passScore"
									styleClass="text medium"
									alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数',allowBlank:false,vtext:'',maxLength:5"
									value="${kmExamRandomTestForm.passScore}" />
							</td>
						</tr>

						<tr>
							<td class="label">
								<fmt:message key="kmExamQuestions.deptId" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content" colspan="3">
								<textarea name="deptName" id="deptName" class="textarea max"
									style="height: 40px;" alt="allowBlank:false,vtext:'请选择部门...'"
									readonly="true"><%
										KmExamRandomTestForm kmExamRandomTestForm = (KmExamRandomTestForm) request.getAttribute("kmExamRandomTestForm");
										if (kmExamRandomTestForm != null && kmExamRandomTestForm.getBelongToDept() != null) {
											String[] depts = kmExamRandomTestForm.getBelongToDept().split(",");
											for (int i = 0; i < depts.length; i++) {
									%><eoms:id2nameDB id="<%=depts[i]%>" beanId="tawSystemDeptDao" />,<%}}%></textarea>
								<input type="hidden" id=belongToDept name="belongToDept"
									value="${kmExamRandomTestForm.belongToDept}" />
							</td>
						</tr>

						<tr>
							<td class="label">
								<fmt:message key="kmExamRandomTest.randomId" />&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<input type="text" id="randomSpecialtyName"
									name="randomSpecialtyName" class="text" readonly="readonly"
									value='<eoms:id2nameDB id="${kmExamRandomTestForm.randomId}" beanId="kmExamRandomSpcailityDao" />'
									alt="allowBlank:false" />
								<input type="hidden" id=randomId name="randomId" value="${kmExamRandomTestForm.randomId}" />
							</td>
							<td class="label">
								随机试卷数量&nbsp;<font color='red'>*</font>
							</td>
							<td class="content">
								<html:text property="totalPaper" styleId="totalPaper"
									styleClass="text medium"
									alt="allowBlank:false,vtext:'',vtype:'number',maxLength:5"
									value="${kmExamRandomTestForm.totalPaper}" />
							</td>
						</tr>
						<html:hidden property="deptID" value="-1" />
						<html:hidden property="isPublic" value="0" />
						<html:hidden property="isDeleted" value="0" />
						<html:hidden property="isRandom" value="1" />
						<!-- 是不是随机试卷 “0”不是随机试卷-->
					</table>
				</div>
			</div>

			<div id="testDetail">
				<div id="leftPart">
					<table border=0 width="100%" cellpadding="0" cellspacing="0">
						<tr align="center">
							<td>
								<div style="line-height: 25px; background-color: #E8F2FE; text-align: center; border-bottom: 1px #80CCFF solid;">
									试题类型选择
								</div>
							</td>
						</tr>
						<tr align="center"><td><div id="type1">选择单选题&nbsp;<b style="color: #80CCFF;">>></b></div></td></tr>
						<tr align="center"><td><div id="type2">选择多选题&nbsp;<b style="color: #80CCFF;">>></b></div></td></tr>
						<tr align="center"><td><div id="type3">选择判断题&nbsp;<b style="color: #80CCFF;">>></b></div></td></tr>
						<tr align="center"><td><div id="type4">选择填空题&nbsp;<b style="color: #80CCFF;">>></b></div></td></tr>
						<tr align="center"><td><div id="type5">选择简答题&nbsp;<b style="color: #80CCFF;">>></b></div></td></tr>
					</table>
				</div>
				<div id="mainPart">
					<div style="line-height: 25px; background-color: #E8F2FE; text-align: center; border-bottom: 1px #80CCFF solid;">
						试题配置信息列表
					</div>
					<div id="editPart">
						<div id="part1">
							<div style="margin-top: 20px;">
								大题题型：&nbsp;&nbsp;&nbsp;&nbsp;单选题
							</div>
							<div>
								每小题分数<font color='red'>*</font>：
								<html:text property="score1" styleId="score1"
									styleClass="text medium" onkeypress="var k=event.keyCode;return (k>=48&&k<=57||k==46);"
									value="${configure1.score}" />
							</div>
							<div>
								包含题数：&nbsp;&nbsp;&nbsp;&nbsp;
								简单题<font color='red'>*</font>：
								<html:text property="quantity11" styleId="quantity11"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"  
									 value="${configure1.number1}" />&nbsp;&nbsp; 
								一般题<font color='red'>*</font>：
								<html:text property="quantity12" styleId="quantity12" 
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									 value="${configure1.number2}" />&nbsp;&nbsp;
								困难题<font color='red'>*</font>：
								<html:text property="quantity13" styleId="quantity13"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									 value="${configure1.number3}" />
							</div>
							<div>
								题目描述<font color='red'>*</font>：<br /><br />
								<textarea name="description1" id="description1"
									class="textarea max" >${configure1.testDescription}</textarea>
							</div>
						</div>
						<div id="part2">
							<div style="margin-top: 20px;">
								大题题型：&nbsp;&nbsp;&nbsp;&nbsp;多选题
							</div>
							<div>
								每小题分数<font color='red'>*</font>：
								<html:text property="score2" styleId="score2"
									styleClass="text medium" onkeypress="var k=event.keyCode;return (k>=48&&k<=57||k==46);"
									value="${configure2.score}" />
							</div>
							<div>
								包含题数：&nbsp;&nbsp;&nbsp;&nbsp; 
								简单题<font color='red'>*</font>：
								<html:text property="quantity21" styleId="quantity21"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									 value="${configure2.number1}" />&nbsp;&nbsp; 
								一般题<font color='red'>*</font>：
								<html:text property="quantity22" styleId="quantity22"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									value="${configure2.number2}" />&nbsp;&nbsp; 
								困难题<font color='red'>*</font>：
								<html:text property="quantity23" styleId="quantity23"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									value="${configure2.number3}" />
							</div>
							<div>
								题目描述<font color='red'>*</font>：<br /><br />
								<textarea name="description2" id="description2"
									class="textarea max" >${configure2.testDescription}</textarea>
							</div>
						</div>
						<div id="part3">
							<div style="margin-top: 20px;">
								大题题型：&nbsp;&nbsp;&nbsp;&nbsp;判断题
							</div>
							<div>
								每小题分数<font color='red'>*</font>：
								<html:text property="score3" styleId="score3"
									styleClass="text medium" onkeypress="var k=event.keyCode;return (k>=48&&k<=57||k==46);"
									value="${configure3.score}" />
							</div>
							<div>
								包含题数：&nbsp;&nbsp;&nbsp;&nbsp;
								简单题<font color='red'>*</font>：
								<html:text property="quantity31" styleId="quantity31"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									 value="${configure3.number1}" />&nbsp;&nbsp; 
								一般题<font color='red'>*</font>：
								<html:text property="quantity32" styleId="quantity32"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									 value="${configure3.number2}" />&nbsp;&nbsp; 
								困难题<font color='red'>*</font>：
								<html:text property="quantity33" styleId="quantity33"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									 value="${configure3.number3}" />
							</div>
							<div>
								题目描述<font color='red'>*</font>：<br /><br />
								<textarea name="description3" id="description3"
									class="textarea max" >${configure3.testDescription}</textarea>
							</div>
						</div>

						<div id="part4">
							<div style="margin-top: 20px;">
								大题题型：&nbsp;&nbsp;&nbsp;&nbsp;填空题
							</div>
							<div>
								每小题分数<font color='red'>*</font>：
								<html:text property="score4" styleId="score4"
									styleClass="text medium" onkeypress="var k=event.keyCode;return (k>=48&&k<=57||k==46);"
									value="${configure4.score}" />
							</div>
							<div>
								包含题数：&nbsp;&nbsp;&nbsp;&nbsp; 
								简单题<font color='red'>*</font>：
								<html:text property="quantity41" styleId="quantity41"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									value="${configure4.number1}" />&nbsp;&nbsp;
								一般题<font color='red'>*</font>：
								<html:text property="quantity42" styleId="quantity42"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									 value="${configure4.number2}" />&nbsp;&nbsp;								
								困难题<font color='red'>*</font>：
								<html:text property="quantity43" styleId="quantity43"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									value="${configure4.number3}" />
							</div>
							<div>
								题目描述<font color='red'>*</font>：<br /><br />
								<textarea name="description4" id="description4"
									class="textarea max" >${configure4.testDescription}</textarea>
							</div>
						</div>

						<div id="part5">
							<div style="margin-top: 20px;">
								大题题型：&nbsp;&nbsp;&nbsp;&nbsp;简答题
							</div>
							<div>
								每小题分数<font color='red'>*</font>：
								<html:text property="score5" styleId="score5"
									styleClass="text medium" onkeypress="var k=event.keyCode;return (k>=48&&k<=57||k==46);"
									value="${configure5.score}" />
							</div>
							<div>
								包含题数：&nbsp;&nbsp;&nbsp;&nbsp;
								简单题<font color='red'>*</font>：
								<html:text property="quantity51" styleId="quantity51"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									value="${configure5.number1}" />&nbsp;&nbsp;								
								一般题<font color='red'>*</font>：
								<html:text property="quantity52" styleId="quantity52"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									value="${configure5.number2}" />&nbsp;&nbsp;
								困难题<font color='red'>*</font>：
								<html:text property="quantity53" styleId="quantity53"
									style="width:50px;" styleClass="text medium" onkeypress="var k=event.keyCode;return k!=46&&(k>=48&&k<=57);"
									value="${configure5.number3}" />
							</div>
							<div>
								题目描述<font color='red'>*</font>：<br /><br />
								<textarea name="description5" id="description5"
									class="textarea max" >${configure5.testDescription}</textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</fmt:bundle>

	<br>

	<table>
		<tr>
			<td>
				<c:if
					test="${kmExamRandomTestForm.isPublic=='0'||empty kmExamRandomTestForm.isPublic}">
					<input type="button" class="btn" onclick="submitForm()"
						value="<fmt:message key="button.save"/>" />
				</c:if>
				<c:if test="${not empty kmExamRandomTestForm.testID&&kmExamRandomTestForm.isPublic=='0'}">
					<input type="button" class="btn"
						value="<fmt:message key="button.delete"/>"
						onclick="javascript:if(confirm('确定要删除？')){
						var url='${app}/kmmanager/kmExamRandomTests.do?method=remove&id=${kmExamRandomTestForm.testID}';
						location.href=url}" />
				</c:if>
			</td>
		</tr>
	</table>
	<html:hidden property="testID" value="${kmExamRandomTestForm.testID}" />
</html:form>

<script type="text/javascript">
var questionTabs;
Ext.onReady(function(){
			var config = {
				btnId:'specialtyName',
				treeDataUrl:'${app}/kmmanager/kmExamTestSpecialtys.do?method=getNodesRadioTree',
				treeRootId:'<%=KmExamTestSpecialtyConstants.TREE_ROOT_ID%>',
				treeRootText:'所属分类',
				treeChkMode:'single',
				treeChkType:'forums',
				showChkFldId:'specialtyName',
				saveChkFldId:'specialtyID'
			}
			tree = new xbox(config);
			
			var config2 = {
				btnId:'randomSpecialtyName',
				treeDataUrl:'${app}/kmmanager/kmExamRandomSpcailitys.do?method=getNodesRadioTree',
				treeRootId:'<%=KmExamTestSpecialtyConstants.TREE_ROOT_ID%>',
				treeRootText:'随机题库',
				treeChkMode:'single',
				treeChkType:'forums',
				showChkFldId:'randomSpecialtyName',
				saveChkFldId:'randomId'
			}
			tree2 = new xbox(config2);
			
			var tabs = new Ext.TabPanel('container');
			tabs.addTab('testInfo', '试卷信息');
        	tabs.addTab('testDetail', '试题设置');
    		tabs.activate(0);
    		questionTabs = new Ext.TabPanel('editPart');
    		var typeName = ['','单选题','多选题','判断题','填空题','简答题'];
    		var configure = ['','${configure1}','${configure2}','${configure3}','${configure4}','${configure5}']
    		var flag = false;
    		for(var i=1;i<=5;i++){
	    		questionTabs.addTab('part'+i,typeName[i]);
        		if(configure[i]==null||configure[i]=="")
	    			questionTabs.hideTab('part'+i);
	    		else { 
		    		if(!flag) flag = i;
		    		eval("type"+i).coreDom.click();
		    	}
    		}
    		if(flag != false)
    			questionTabs.activate(flag-1);
});

function AddType(number){
	this.coreDom = document.getElementById("type"+number);
	this.coreDom.flag = false;
	this.coreDom.onclick = function(){
							var typeName = ['单选题','多选题','判断题','填空题','简答题'];
							if(this.flag){
								this.flag = false;
								this.innerHTML = '选择'+typeName[number-1]+'&nbsp;<b style="color:#80CCFF;">>></b>';
								document.getElementById('part'+number).style.display = "none";
								questionTabs.hideTab('part'+number);
								for(var i=0;i<5;i++)
								{
									var item = questionTabs.getTab(i);
									if(!item.isHidden()){
										item.activate();
										return;
									} 
								}
								//删除
							}else{
								this.flag = true;
								this.innerHTML = '取消'+typeName[number-1]+'&nbsp;<b style="color:#80CCFF;"><<</b>';
								document.getElementById('part'+number).style.display = "";
								questionTabs.unhideTab('part'+number,typeName[number-1]);
								questionTabs.activate('part'+number);
								//添加
							}		
						}
}

function submitForm(){
	if(!v.check()){
		return false;   
	}		
	var checkNot = true;
	if(document.getElementById("testName").value=="") checkNot = false;
	if(document.getElementById("question").value=="") checkNot = false;
	if(document.getElementById("testBeginTime").value=="") checkNot = false;
	if(document.getElementById("testEndTime").value=="") checkNot = false;
	if(document.getElementById("randomSpecialtyName").value=="") checkNot = false;
	if(document.getElementById("specialtyName").value=="") checkNot = false;
	if(document.getElementById("totalPaper").value=="") checkNot = false;
	if(document.getElementById("totalScore").value=="") checkNot = false;
	if(document.getElementById("deptName").value.replace(/\s*/g,"")=="") checkNot = false;
	var totalScore = 0;//424
	var flag = false;
	for(var i=1;i<=5;i++)
	{	
		if(!questionTabs.getTab(i-1).isHidden()){
			if(document.getElementById("score"+i).value=="") checkNot = false;
			if(document.getElementById("quantity"+i+"1").value=="") checkNot = false;
			if(document.getElementById("quantity"+i+"2").value=="") checkNot = false;
			if(document.getElementById("quantity"+i+"3").value=="") checkNot = false;
			if(document.getElementById("description"+i).value=="") checkNot = false;
			flag = true;
			var score = document.getElementById("score"+i).value;
			var quantity = parseInt(document.getElementById("quantity"+i+"1").value)+parseInt(document.getElementById("quantity"+i+"2").value)+parseInt(document.getElementById("quantity"+i+"3").value);
			if(score!=""&&quantity!="")
				totalScore += score * quantity;
			if(parseInt(quantity)<=0){
				alert("题目数量不能小于等于0！");
				return;
			}
			if(document.getElementById("description"+i).value.length>500){
				alert("请检查各项题目描述，长度不能大于500");
				return;
			}
		}
	}
	if(!checkNot){alert("请至少有一项未填，请检查");return;}
	if(!flag){alert("请至少选择一类型试题");return;}
	if(isNaN(totalScore)){
		alert("至少有一项分数填写不正确，请检查！");
		return;
	}
	if(document.getElementById("totalScore").value!=totalScore){
		alert("总分不等于各题之和，总分应为"+totalScore);
		return;
	}
	var passScore = document.getElementById("passScore").value;
	if(passScore>totalScore||passScore<=0){
		alert("请检查及格分数，必须大于0并且小于总分！");
		return;
	}
	var isRight = true;
	checkNumber(isRight);
	
}

function createRequest(){
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}

function checkNumber(isRight){
	var url = "${app}/kmmanager/kmExamRandomQuestions.do?method=searchCountBySpcaility&id="+document.getElementById("randomId").value;
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,false);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		
		     		var json = eval(httpRequest.responseText);
		     		var flag = false;
		     		var str = "";
		     		if(!questionTabs.getTab(0).isHidden()){
			     		var quantity = document.getElementById("quantity11").value;	
			     		if(quantity>parseInt(json[0].quantity11)){
			     			str += "单选题简单题共"+json[0].quantity11+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity12").value;	
			     		if(quantity>parseInt(json[0].quantity12)){
			     			str += "单选题一般题共"+json[0].quantity12+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity13").value;	
			     		if(quantity>parseInt(json[0].quantity13)){
			     			str += "单选题难题共"+json[0].quantity13+"道，";
			     			isRight=false;
			     		}
			     	}
			     	if(!questionTabs.getTab(1).isHidden()){
			     		quantity = document.getElementById("quantity21").value;
			     		if(quantity>parseInt(json[0].quantity21)){
			     			str += "多选题简单题共"+json[0].quantity21+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity22").value;
			     		if(quantity>parseInt(json[0].quantity22)){
			     			str += "多选题一般题共"+json[0].quantity22+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity23").value;
			     		if(quantity>parseInt(json[0].quantity23)){
			     			str += "多选题难题共"+json[0].quantity23+"道，";
			     			isRight=false;
			     		}
		     		}
		     		if(!questionTabs.getTab(2).isHidden()){
			     		quantity = document.getElementById("quantity31").value;
			     		if(quantity>parseInt(json[0].quantity31)){
			     			str += "判断题简单题共"+json[0].quantity31+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity32").value;
			     		if(quantity>parseInt(json[0].quantity32)){
			     			str += "判断题一般题共"+json[0].quantity32+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity33").value;
			     		if(quantity>parseInt(json[0].quantity33)){
			     			str += "判断题难题共"+json[0].quantity33+"道，";
			     			isRight=false;
			     		}
			     	}
			     	if(!questionTabs.getTab(3).isHidden()){
			     		quantity = document.getElementById("quantity41").value;
			     		if(quantity>parseInt(json[0].quantity41)){
			     			str += "填空题简单题共"+json[0].quantity41+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity42").value;
			     		if(quantity>parseInt(json[0].quantity42)){
			     			str += "填空题一般题共"+json[0].quantity42+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity43").value;
			     		if(quantity>parseInt(json[0].quantity43)){
			     			str += "填空题难题共"+json[0].quantity43+"道，";
			     			isRight=false;
			     		}
			     	}
			     	if(!questionTabs.getTab(4).isHidden()){
			     		quantity = document.getElementById("quantity51").value;
			     		if(quantity>parseInt(json[0].quantity51)){
			     			str += "简答题简单题共"+json[0].quantity51+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity52").value;
			     		if(quantity>parseInt(json[0].quantity52)){
			     			str += "简答题一般题共"+json[0].quantity52+"道，";
			     			isRight=false;
			     		}
			     		quantity = document.getElementById("quantity53").value;
			     		if(quantity>parseInt(json[0].quantity53)){
			     			str += "简答题难题共"+json[0].quantity53+"道，";
			     			isRight=false;
			     		}
			     	}
		     			
		     		if(!isRight){
		     			str = "题库中" +str.substring(0,str.length-1)+ " \r您填的数量大于题库数量，请检查后提交";
		     			alert(str);
		     		}else{
		     			var num = new Array();
						for(var i=0;i<5;i++)
						{
							var item = questionTabs.getTab(i);
							if(item.isHidden()){
								num.push(i);
							} 
						}
						for(var i=0;i<num.length;i++)
							questionTabs.removeTab("part"+(num[i]+1));
						if(document.getElementById("noSynchronizedTime").style.display=="none")
					   		document.getElementById("noSynchronizedTime").parentNode.removeChild(document.getElementById("noSynchronizedTime"));
					    else if(document.getElementById("synchronizedTime").style.display=="none")
					   		document.getElementById("synchronizedTime").parentNode.removeChild(document.getElementById("synchronizedTime"));
						document.forms[0].submit();
		     		
		     		}
				}	
	     }
	     httpRequest.send(null);
	}
}

var type1 = new AddType(1);
var type2 = new AddType(2);
var type3 = new AddType(3);
var type4 = new AddType(4);
var type5 = new AddType(5);
</script>
<%@ include file="/common/footer_eoms.jsp"%>