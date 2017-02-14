<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	var config = {
		btnId:'testName',
		treeDataUrl:'${app}/kmmanager/kmExamTestSpecialtys.do?method=getTest',
		treeRootId:'1',
		treeRootText:'所属分类',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'testName',
		saveChkFldId:'randomTestId'
	}
	tree = new xbox(config);
});
function checkName()
{
	var testName = document.getElementById("testName").value
	if(testName==""){
		alert("请选择考试名称！");
	   	return false;
	 }
	return true;
}
function resetName()
{
	document.getElementById("testName").value="";
}
</script>

<style>
#dataList{margin-top:12px;}
.title td{background-color:#E8F2FE}
.notitle td{background-color:#fff}
a{text-decoration:none}
</style>

<div id="container">
	<div id="form" >
		<form id="kmExamTestForm" action="${app}/kmmanager/kmExamStatistics.do?method=paperAnalyzeList" method="post">
			<table>
				<tr>
					<td>
						考试名称：<input id="testName" name="testName" class="text" value="${testName }">&nbsp;&nbsp;
						<input type="hidden" id="randomTestId" name="randomTestId" value="${randomTestId }">
						<input type="hidden" id="curPage" name="curPage" value="${curPage }">
						<input type="submit" class="btn" onclick="return checkName();" value="统计">&nbsp;
						<input type="button" class="btn" value="重置" onclick="resetName()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dataList">
		<div style="text-align:center;margin-bottom:8px;"><b>试卷整体分析</b></div>
		<table class="formTable">
			<tr class="title"><td>序号</td><td>所属分类</td><td>考试名称</td><td>试卷分数</td><td>操作</td></tr>
			<c:forEach items="${statisticList}" var="item" varStatus="status">
				<tr onmousemove="this.className='title';" onmouseout="this.className='notitle';">
					<td>${status.count}</td>
					<td><eoms:id2nameDB id="${item[0]}" beanId="kmExamTestSpecialtyDao" /></td>
					<td>${item[1]}</td>
					<td>${item[2]}</td>
					<td><a href="${app}/kmmanager/kmExamStatistics.do?method=paperAnalyze&testId=${item[3] }&specialty=${item[0]}">分析试卷</a> </td>
				</tr>
			</c:forEach>
		</table>
	</div>
<div>
<%@ include file="/common/footer_eoms.jsp"%>