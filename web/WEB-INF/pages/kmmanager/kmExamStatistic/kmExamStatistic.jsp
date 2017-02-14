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
	
	var tabs = new Ext.TabPanel('container');
	tabs.addTab('part1', '统计结果');
    tabs.addTab('part2', '参加人数');
	tabs.addTab('part3', '平均成绩');
    tabs.addTab('part4', '及格率');
	tabs.addTab('part5', '最高分');
	tabs.addTab('part6', '最低分');
  	tabs.activate(0);
	
});
function checkName()
{
	var testName = document.getElementById("testName").value
	if(testName==""){
		alert("请选择考试名称！");
	   	return false;
	 }
	var startDate = document.getElementById("beginTime").value;
    var endDate = document.getElementById("endTime").value;
    if(startDate!=""&&startDate!=""&&startDate > endDate){
        alert("开始时间要小于结束时间！");
        return false;
    }
	return true;
}
</script>

<style>
#dataList{margin-top:12px;}
.title td{background-color:#E8F2FE}
.notitle td{background-color:#fff}
</style>

<div id="container">
	<div id="form" >
		<form id="kmExamTestForm" action="${app}/kmmanager/kmExamStatistics.do" method="post">
			<table>
				<tr>
					<td>
						考试名称：<input id="testName" name="testName" class="text" value="${testName }">&nbsp;&nbsp;
						<input type="hidden" id="beginTime" name="beginTime" value="">
						<input type="hidden" id="endTime" name="endTime" value="">
						<input type="hidden" id="randomTestId" name="randomTestId" value="${randomTestId }">
						<input type="hidden" id="curPage" name="curPage" value="${curPage }">
						<input type="submit" class="btn" onclick="return checkName();" value="统计">&nbsp;
						<input type="reset" class="btn" value="重置">
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div id="part1">
		<div id="dataList">
			<div style="text-align:center;margin-bottom:8px;">
				<b>${testName}&nbsp;考试成绩分析</b>
			</div>

			<table class="formTable">
				<tr align=center class="title">
					<td>部门名称</td>
					<td>本部门参考人数</td>
					<td>本部门平均成绩</td>
					<td>本部门及格率</td>
					<td>本部门最高分</td>
					<td>本部门最低分</td>
					<td>部门名次</td>
				</tr>
				
				<c:forEach items="${statisticList}" var="item" varStatus="status">
					<tr onmousemove="this.className='title';" onmouseout="this.className='notitle';">
						<td>${item[2]}</td>
						<td>${item[3]}</td>
						<td>${item[4]}</td>
						<td>${item[5]}%</td>
						<td>${item[6]}</td>
						<td>${item[7]}</td>
						<td>${status.count}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	<div id="part2">
		<c:if test="${fileName1!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName1 }" alt="图片">
		</c:if>
	</div>
	<div id="part3">
		<c:if test="${fileName2!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName2 }" alt="图片">
		</c:if>
	</div>
	<div id="part4">
		<c:if test="${fileName3!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName3 }" alt="图片">
		</c:if>
	</div>
	<div id="part5">
		<c:if test="${fileName4!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName4 }" alt="图片">
		</c:if>
	</div>
	<div id="part6">
		<c:if test="${fileName5!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName5 }" alt="图片">
		</c:if>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>