<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
String partnerNodeType = StaticMethod.nullObject2String(request.getParameter("partnerNodeType"));
String changeTemplateMethod = "changeTemplateTypeOnLeaf";
String actionMethod = "/evaReportInfos.do?method=partnerListFindArea";
String refReturnId = "templateId";
if("templateType".equals(partnerNodeType)){
	changeTemplateMethod = "changeTemplateType";
	actionMethod = "/evaReportInfos.do?method=partnerList";
	refReturnId = "taskId";
}
%>
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaKpiInstanceForm'});
})

 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

function changeTemplateType()
		{   
		    delAllOption("<%=refReturnId %>");//考核模版类型改变后，需要把考核模版处的选项清除，不然选项会乱
			var templateTypeId = document.getElementById("templateTypeId").value;
			
			var url="<%=request.getContextPath()%>/partner/eva/evaReportInfos.do?method=<%=changeTemplateMethod %>&templateTypeId="+templateTypeId;
			var fieldName = "<%=refReturnId %>";
			 
			
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
								    results =  result.responseText;
								    var arrOptions=results.split(",");
										var obj=$(fieldName);
										var i=0;
										var j=0;
										for(i=0;i<arrOptions.length;i++){
											var opt=new Option(arrOptions[i+1],arrOptions[i]);
											obj.options[j]=opt;
											j++;
											i++;
										}
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
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
function isSubmit(){
    return true;//document.getElementById("pnrEvaKpiInstanceForm").submit();
}
function check(){
     return true;
  }
</script>

<html:form action="<%=actionMethod %>" styleId="pnrEvaKpiInstanceForm" method="post" > 
<table class="formTable" id="form">
	<caption>
		<div class="header center">同一月份不同厂商查看</div>
	</caption>
	<input type="hidden" id="queryType" name="queryType" value="run"/>
	<tr>
		<td class="label">
			选择考核模板
		</td>
		<td class="content">
			<select name="templateTypeId" id="templateTypeId"
					alt="allowBlank:false,vtext:'请选择考核模板分类'"
					onchange="changeTemplateType();">
					<option value="">
						--请选择模板分类--
					</option>
					<logic:iterate id="templateType" name="templateType">
						<option value="${templateType.nodeId}">
							<eoms:id2nameDB id="${templateType.nodeId}" beanId="pnrEvaTreeDao" />
						</option>
					</logic:iterate>
				</select>
		</td>
		<td class="label">
			考核模板
		</td>
		<td class="content">
			<select name="<%=refReturnId %>" id="<%=refReturnId %>" 
					alt="allowBlank:false,vtext:'请选择考核模板'">
					<option value="">
						--请选择模板--
					</option>
			</select>
		</td>

	</tr>
	<tr>
		<td class="label">
			时间周期类型
		</td>
		<td class="content">
			月度
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
		</td>
	</tr>
</table>
<table id="submit-btn" align="left">
	<tr>
		<td valign="top">
			<input type="submit" class="btn" value="查看" onClick="return check();"/>
		</td>
		<td>
			<div id="tree" style="overflow:auto; border:1px solid #c3daf9;"></div>
		</td>
	</tr>
</table>
<html:hidden property="areaId" value="${areaId}" />
</html:form>
<script type="text/javascript">
	eoms.loadCSS(eoms.appPath+"/styles/eva/style.css");
	var tree, treeLoader, root;
	Ext.onReady(function(){
		treeLoader = new Ext.tree.TreeLoader({
		dataUrl : "${app}/partner/eva/evaTrees.do?method=getTemplateNodes&areaId=${areaId}"
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
			id : "<%=PnrEvaConstants.TREE_ROOT_ID%>",
			text : "查看考核树",
			nodeType : 'root'
		});
		tree.setRootNode(root);	
		tree.render();
	});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
