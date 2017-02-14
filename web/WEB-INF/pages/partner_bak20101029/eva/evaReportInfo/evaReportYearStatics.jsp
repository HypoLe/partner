<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="com.boco.eoms.partner.eva.model.PnrEvaTemplate"/>
<jsp:directive.page import="java.util.List"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<html:form action="/evaReportInfos.do?method=getReportYearStaticsByTime" styleId="pnrReportYearStaticsForm" method="post" > 
<center> 
<div style="width:400px">
<table class="formTable" id="form" >
	<caption>
		<div class="header center">考核年报表管理视图</div>
	</caption>
	<input type="hidden" id="queryType" name="queryType" value="run"/>
	<tr>
		<td class="label">
			分类
		</td>
		<td class="content">
			<select name="templateTypeId" id="templateTypeId"
					alt="allowBlank:false,vtext:'请选择考核模板分类'"
					onchange="changeTemplateType();">
					<option value="">
						--请选择分类--
					</option>
					<logic:iterate id="templateType" name="templateType">
						<option value="${templateType.nodeId}">
							<eoms:id2nameDB id="${templateType.nodeId}" beanId="pnrEvaTreeDao" />
						</option>
					</logic:iterate>
				</select>
		</td>
		</tr>
		<tr>
		<td class="label">
			选择专业
		</td>
		<td class="content">
			<select name="belongNode" id="belongNode"
					alt="allowBlank:false,vtext:'请选择专'"">
					<option value="">
						--请选择专业--
					</option>
					
				</select>
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
</div>
</center>
</html:form>
<script type="text/javascript">
<!--
 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
function changeTemplateType()
		{   
		    delAllOption("belongNode");//考核模版类型改变后，需要把考核模版处的选项清除，不然选项会乱
			var templateTypeId = document.getElementById("templateTypeId").value;
			
			var url="<%=request.getContextPath()%>/partner/eva/evaReportInfos.do?method=changeTemplateTypeForStatics&templateTypeId="+templateTypeId;
			var fieldName = "belongNode";
			 

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

//-->
</script>
<%@ include file="/common/footer_eoms.jsp"%>