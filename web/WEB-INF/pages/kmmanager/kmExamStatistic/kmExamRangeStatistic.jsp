<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<jsp:directive.page import="com.boco.eoms.km.exam.util.KmExamTestSpecialtyConstants" />
<script type="text/javascript">

Ext.onReady(function() {
	var config = {
		btnId:'testName',
		treeDataUrl:'${app}/kmmanager/kmExamTestSpecialtys.do?method=getTest',
		treeRootId:'<%=KmExamTestSpecialtyConstants.TREE_ROOT_ID%>',
		treeRootText:'所属分类',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'testName',
		saveChkFldId:'randomTestId'
	}
	tree = new xbox(config);
	
	var tabs = new Ext.TabPanel('container');
	tabs.addTab('part1', '成绩分布列表');
	tabs.addTab('part2', '0分-及格线下20%');
    tabs.addTab('part3', '及格线下20%-10%');
	tabs.addTab('part4', '及格线下10%-及格线');
    tabs.addTab('part5', '及格线上10%');
	tabs.addTab('part6', '及格线上10%-20%');
	tabs.addTab('part7', '及格线上20%-30%');
	tabs.addTab('part8', '及格线上30%-满分');
	
  	tabs.activate(0);
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
</script>
<style>

#dataList{margin-top:12px;}
.title td{background-color:#E8F2FE}
.notitle td{background-color:#fff}
</style>

<div id="container" style="width:1024px;">
	<div id="form" >
		<form id="kmExamTestForm" action="${app}/kmmanager/kmExamStatistics.do?method=rangeStatistic" method="post">
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
				<b>${testName}&nbsp;成绩分布分析&nbsp;&nbsp;满分：${totalScore}&nbsp;及格分：${passScore}</b>
			</div>

			<table class="formTable">
				<tr align=center class="title">
					<td rowspan="3">部门名称</td>
					<td colspan="6">及格线下</td>
					<td colspan="8">及格线上</td>
				</tr>
				
				<tr align=center class="title">
					<td colspan="2">
						0分-及格线下20%
						<c:if test="${passScore != null}"><br>（0-${passScore - totalScore*0.2}）</c:if>
					</td>
					<td colspan="2">
						及格线下20%-10%
						<c:if test="${passScore != null}"><br>（${passScore - totalScore*0.2}-${passScore - totalScore*0.1}）</c:if>
					</td>
					<td colspan="2">
						及格线下10%-及格线
						<c:if test="${passScore != null}"><br>（${passScore - totalScore*0.1}-${passScore}）</c:if>
					</td>
					
					<td colspan="2">
						及格线上10%
						<c:if test="${passScore != null}"><br>（${passScore}-${passScore + totalScore*0.1}）</c:if>
					</td>
					<td colspan="2">
						及格线上10%-20%
						<c:if test="${passScore != null}"><br>（${passScore+totalScore*0.1}-${passScore+totalScore*0.2}）</c:if>
					</td>
					<td colspan="2">
						及格线上20%-30%
						<c:if test="${passScore != null}"><br>（${passScore+totalScore*0.2}-${passScore+totalScore*0.3}）</c:if>
					</td>
					<td colspan="2">
						及格线上30%-满分
						<c:if test="${passScore != null}"><br>（${passScore+totalScore*0.3}-${totalScore}）</c:if>
					</td>
				</tr>
								
				<tr align=center class="title">
					<td>人数</td>
					<td>百分比</td>
					
					<td>人数</td>
					<td>百分比</td>
					
					<td>人数</td>
					<td>百分比</td>

					<td>人数</td>
					<td>百分比</td>

					<td>人数</td>
					<td>百分比</td>

					<td>人数</td>
					<td>百分比</td>

					<td>人数</td>
					<td>百分比</td>
				</tr>
				
				<c:forEach items="${statisticList}" var="item">				
				<tr>
					<td>${item[2]}</td>

					<td>${item[5]}</td>
					<td>${item[6]}%</td>

					<td>${item[7]}</td>
					<td>${item[8]}%</td>
					
					<td>${item[9]}</td>
					<td>${item[10]}%</td>
					
					<td>${item[11]}</td>
					<td>${item[12]}%</td>

					<td>${item[13]}</td>
					<td>${item[14]}%</td>

					<td>${item[15]}</td>
					<td>${item[16]}%</td>

					<td>${item[17]}</td>
					<td>${item[18]}%</td>
				</tr>				
				</c:forEach>
			</table>
		</div>
	</div>

	<div id="part2">
		<c:if test="${fileName1!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName1 }" alt="图片丢失">
		</c:if>
	</div>
	<div id="part3">
		<c:if test="${fileName2!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName2 }" alt="图片丢失">
		</c:if>
	</div>
	<div id="part4">
		<c:if test="${fileName3!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName3 }" alt="图片丢失">
		</c:if>
	</div>
	<div id="part5">
		<c:if test="${fileName4!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName4 }" alt="图片丢失">
		</c:if>
	</div>
	<div id="part6">
		<c:if test="${fileName5!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName5 }" alt="图片丢失">
		</c:if>
	</div>
	<div id="part7">
		<c:if test="${fileName6!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName6 }" alt="图片丢失">
		</c:if>
	</div>
	<div id="part8">
		<c:if test="${fileName7!=null }">
			<img src="${app }/kmpictures/kmExamStatistic/${fileName7 }" alt="图片丢失">
		</c:if>
	</div>

<%@ include file="/common/footer_eoms.jsp"%>