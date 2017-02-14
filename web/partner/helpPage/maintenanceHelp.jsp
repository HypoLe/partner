<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

</script>

<% 
 String flag=request.getParameter("flag");
 %>
<table >
	<caption>
		<div class="header center">日常维护模块功能描述</div>
	</caption>

	<tr>
		<td >
		
			<div id="helpPanel" class="app-panel">
				<dl>
					<dt>功能说明</dt>
					<dd>日常维护、管理。</dd>
		
		<!-- 抽查记录 -->
		<%if("check".equals(flag)){%>
					
					<dt>抽查记录上报</dt>
					<dd>填写上报信息，点击弹出基站列表窗口，可以多选基站，可以查询基站缩小范围后选择基站，选择后点击确定，
						此页面自动关闭，选择基站名称填入抽查对象文本框。填写完抽查记录上报表单后点击保存。
					</dd>
					
					<dt>抽查记录查询</dt>
					<dd>填写查询条件进行搜索，点击抽查对象记录后面的查看图标 ，进入记录详细信息的查看。</dd>
					
		<%} %>		
		<!-- 网格维护 -->
		<%if("gridWeihu".equals(flag)){%>
					<dt>网格KPI月报</dt>
					<dd>网格KPI月报</dd>
					
					<dt>网格KPI年报</dt>
					<dd>网格KPI年报</dd>
					
					<dt>网格综合满意度</dt>
					<dd>网格综合满意度</dd>
					
					<dt>代维响应速度</dt>
					<dd>代维响应速度</dd>
					
		<%} %>
		<!-- 网格KPI月报 -->
		<%if("gridKPIMonths".equals(flag)){%>
					<dt>网格KPI月报上报</dt>
					<dd>填写信息后点击保存即可保存信息。</dd>
					
					<dt>网格KPI月报查询</dt>
					<dd>填写查询条件进行搜索，点击后面的查看图标 ，进入记录详细信息的查看。</dd>
			
		<%} %>		
		<!-- 网格KPI年报 -->
		<%if("gridKPIYears".equals(flag)){%>
					<dt>网格KPI年报上报</dt>
					<dd>填写信息后点击保存即可保存信息。</dd>
					
					<dt>网格KPI年报查询</dt>
					<dd>填写查询条件进行搜索，点击后面的查看图标 ，进入记录详细信息的查看。</dd>
			
		<%} %>
		<!-- 网格综合满意度 -->
		<%if("gridSatisfactions".equals(flag)){%>
					<dt>网格综合满意度报上报</dt>
					<dd>填写信息后点击保存即可保存信息。</dd>
					
					<dt>网格综合满意度查询</dt>
					<dd>填写查询条件进行搜索，点击后面的查看图标 ，进入记录详细信息的查看。</dd>
			
		<%} %>
		<!-- 代维响应速度 -->
		<%if("serviceSpeeds".equals(flag)){%>
					<dt>代维响应速度上报</dt>
					<dd>填写信息后点击保存即可保存信息。</dd>
					
					<dt>代维响应速度查询</dt>
					<dd>填写查询条件进行搜索，点击后面的查看图标 ，进入记录详细信息的查看。</dd>
			
		<%} %>
		
		<!-- 问题跟踪 -->
		<%if("problems".equals(flag)){%>
					<dt>问题跟踪上报</dt>
					<dd>填写信息后点击保存即可保存信息。</dd>
					
					<dt>问题跟踪查询</dt>
					<dd>填写查询条件进行搜索，点击后面的查看图标 ，进入记录详细信息的查看。</dd>
					
			<%} %>
		<!-- 异常故障 -->
		<%if("malfunctions".equals(flag)){%>
					<dt>异常故障上报</dt>
					<dd>填写信息后点击保存即可保存信息。</dd>
					
					<dt>异常故障查询</dt>
					<dd>填写查询条件进行搜索，点击后面的查看图标 ，进入记录详细信息的查看。</dd>
					
			<%} %>
			
					
				</dl>
			</div>
		</td>
	</tr>
	
</table>


<%@ include file="/common/footer_eoms.jsp"%>