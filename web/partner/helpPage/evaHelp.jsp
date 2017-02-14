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
		<div class="header center">考核模块功能描述</div>
	</caption>

	<tr>
		<td >
		
			<div id="helpPanel" class="app-panel">
				<dl>
		
		<!-- 指标模板管理 -->
		<%if("evaTemplates".equals(flag)){%>
					<dt>功能说明</dt>
					<dd>提供应用于合同的服务质量考核模型的增、删、查、改功能。 
考核模型是对代维公司进行考核的依据，主要对KPI进行考核，以树形结构展示，增加用户的可操作性。 
内容包括考核模板分类、考核模板、考核指标、任务激活等模块。 
按照树节点的层次关系从上到下分为考核模板分类、考核模板、考核指标，其中指标支持无限级。 
只有激活的考核模板才能进行考核执行。 
</dd>
					
					<dt>考核模板管理</dt>
					<dd>考核模板管理</dd>
					
					<dt>模板待审核</dt>
					<dd>模板待审核</dd>
					
		<%} %>		
		<!-- 考核执行 -->
		<%if("evaTasks".equals(flag)){%>
					<dt>功能说明</dt>
					<dd>基站考核数据手工填报。根据生成的考核任务用户进行手工填写，完成考核对象的评分、增扣分原因、备注填写。包括考核执行和考核结果查询模块。 </dd>

					<dt>考核执行</dt>
					<dd>考核执行</dd>
					
					<dt>草稿</dt>
					<dd>草稿</dd>
					
					<dt>任务待审核</dt>
					<dd>任务待审核</dd>
					
					
		<%} %>
		<!-- 考核报表 -->
		<%if("evaReportInfos".equals(flag)){%>
					<dt>功能说明</dt>
					<dd>对考核执行的评估结果进行不同形式的统计报表展示，包括“不同月份、同一厂商”、“同一月份、不同厂商”2张报表的统计。 </dd>

					<dt>考核表查询</dt>
					<dd>考核表查询。</dd>
					
					<dt>同一月份不同厂商</dt>
					<dd>填写查询条件进行搜索，点击后面的查看图标 ，进入记录详细信息的查看。</dd>

					<dt>不同月份同一厂商</dt>
					<dd>考核表查询。</dd>
			
		<%} %>		

		<!-- 管理视图- 合作伙伴考核情况 -->
		<%if("evaCase".equals(flag)){%>
					<dt>功能说明</dt>
					<dd>合作伙伴考核情况 </dd>

					<dt>年考核情况</dt>
					<dd>年考核情况</dd>
					
					<dt>月考核情况</dt>
					<dd>月考核情况</dd>

			
		<%} %>		
			
					
				</dl>
			</div>
		</td>
	</tr>
	
</table>


<%@ include file="/common/footer_eoms.jsp"%>