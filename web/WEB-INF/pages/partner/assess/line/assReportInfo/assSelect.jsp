<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<jsp:directive.page import="java.util.List"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assTaskViewForm'});
})
</script>

<html:form action="/lineAssReportInfo.do?method=assList" styleId="assTaskViewForm" method="post" > 
<table class="formTable" id="form">
	<caption>
		<div class="header center">代维后评估确认信息查询(季度)</div>
	</caption>
	<input type="hidden" id="queryType" name="queryType" value="run"/>			
	<input type='hidden' id='timeType' name='timeType' value='month'/>
	<tr>
		<td class="label">
			后评估模板
		</td>
		<td class="content">
			<select name="taskId" id="taskId" 
						alt="allowBlank:false,vtext:'请选择后评估模板'" >
				<logic:iterate id="taskList" name="taskList">
				<option value="${taskList.id}">
					<eoms:id2nameDB id="${taskList.templateId}" beanId="lineAssTemplateDao" />
				</option>
				</logic:iterate>
			</select>
		</td>
		<td class="label">
			合作伙伴
		</td>
		<td class="content">
			<select name="partnerId" id="partnerId" 
						alt="allowBlank:false,vtext:'请选择合作伙伴'">
				<c:forEach items="${partnerList}" var="partner"> 
					<option value="${partner}">
						<eoms:id2nameDB id="${partner}" beanId="partnerDeptDao" />
					</option>
				</c:forEach>
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
			<select name="quarter" id="quarter" alt="allowBlank:false,vtext:'请选择季度'">
				<option value="">请选择</option>
				<option value="01">1</option>
				<option value="02">2</option>
				<option value="03">3</option>
				<option value="04">4</option>
			</select>季度
		</td>
	</tr>
</table>
<table id="submit-btn" align="left">
	<tr>
		<td valign="top">
			<input type="submit" class="btn" value="查看" />
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
		treeLoader = new Ext.tree.TreeLoader({
		dataUrl : "${app}/partner/assess/lineAssTrees.do?method=getTemplateNodes&areaId=${areaId}"
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
