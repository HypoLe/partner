<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<style>
#dataList{margin-top:12px;}
#title td{background-color:#E8F2FE}
</style>
<div id="container">
	<div id="form" >
		<form action="">
			<table >
				<tr><td>部门</td><td>试卷分类</td><td>开始时间</td><td>结束时间</td><td>考试名称</td><td>设置及格分数线</td><td></td><td></td><td></td></tr>
				<tr>
					<td><input class="text"></td>
					<td><input class="text"></td>
					<td><input class="text"></td>
					<td><input class="text"></td>
					<td><input class="text"></td>
					<td><input class="text"></td>
					<td><input type="submit" class="btn" value="统计"></td>
					<td><input type="reset" class="btn" value="重置"></td>
					<td><input type="button" class="btn" value="导出数据"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dataList">
		<div style="text-align:center;margin-bottom:8px;"><b>试卷统计结果列表</b></div>
		<table class="formTable">
			<tr id="title"><td>所属分类</td><td>考试名称</td><td>部门名称</td><td>总参考人数</td><td>总平均成绩</td><td>总体及格率</td><td>最高分</td><td>最低分</td><td>本部门参考人数</td><td>本部门平均成绩</td><td>本部门及格率</td><td>本部门最高分</td><td>本部门最低分</td><td>部门名次</td></tr>
			<c:forEach begin="0" end="10">
				<tr><td>a</td><td></td><td></td><td></td><td></td><td></td><td></td><td>a</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
			</c:forEach>
		</table>
	</div>
<div>
<%@ include file="/common/footer_eoms.jsp"%>