<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style>
#dataList{margin-top:12px;}
.title td{background-color:#E8F2FE}
.notitle td{background-color:#fff}
a{text-decoration:none}
</style>
<script type="text/javascript">
var type = ['','单选题','多选题','判断题','填空题','简答题'];
</script>
<div id="container">
	<div style="font-size:15px;">
		<center><b><eoms:id2nameDB id="${testId}" beanId="kmExamTestDao" /></b></center>
	</div>
	
	<div id="dataList">
		<div style="text-align:left;margin-bottom:8px;"><b>试卷信息</b></div>
		<div style="text-align:left;margin-left:50px;">试卷分类：<b><eoms:id2nameDB id="${specialty }" beanId="kmExamTestSpecialtyDao" /></b>&nbsp;&nbsp;&nbsp;&nbsp;试卷名称：<b><eoms:id2nameDB id="${testId}" beanId="kmExamTestDao" /></b></div>
		<div style="margin-top:20px;">
			<table class="formTable">
				<caption><center>试题分析列表</center></caption>
				<tr class="title"><td>序号</td><td>试题类型</td><td>题目内容</td><td>正确率</td><td>部分正确率</td><td>错误率</td></tr>
				<c:forEach items="${statisticList}" var="item" varStatus="status">
					<tr onmousemove="this.className='title';" onmouseout="this.className='notitle';">
						<td>${status.count}</td>
						<td><script type="text/javascript">document.write(type[${item[0]}]);</script></td>
						<td>
						 	<script type="text/javascript">
						 		var question = "${item[1]}";
						 		if(question.length>30)
						 			question = question.substring(0,30)+"...";
						 		document.write("<div title='${item[1]}'>"+question+"</div>");</script>
						</td>
						<td>
							${item[2]}%
						</td>
						<td>
							${item[3]}%
						</td>
						<td>
							<script type="text/javascript">
								var num2 = '${item[2]}';
								var num3 = '${item[3]}';
								var num = 100-num2-num3;
								document.write(num.toFixed(2)+"%");
							</script>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>