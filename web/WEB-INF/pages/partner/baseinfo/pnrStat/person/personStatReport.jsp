
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.webapp.form.BaseinfoStatForm" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
	
	window.onload = function(){
								//合作伙伴
								var providerName = "${providerBuffer}"; 
								var arrOptionsP=providerName.split(",");
								var objp=$("provider");					
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
								}
		}	
	
	function sub(){
	
	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	
		if(year!="" && month==""){
			alert("请选择要统计的月份！");
			return false;
		}else if(year=="" && month!=""){
			alert("请选择要统计的年份！");
			return false;
		}
	    return true;  
	}

</script>
<html:form action="/pnrStats.do?method=getPersonReport&first=fir" styleId="SiteReportFrom" method="post" onsubmit="return sub();"> 

<table class="formTable">
	<caption>
		<div class="header center">合作伙伴人力信息统计</div>
	</caption>
<!-- 年 -->
	<tr>
		<td class="label">
			年份、&nbsp;
			月份：&nbsp;
		</td>
		<td class="content">
			<select id="year" name="year" >
					<option id="0" value="">--请选择年份--</option>
				<c:forEach begin="2008" end="2025" var="num">
						<option id="${num}" value="${num}">${num}年</option>
				</c:forEach>
			</select>
			<select id="month" name="month" >
					<option id="0" value="">--请选择月份--</option>
				<c:forEach begin="1" end="12" var="num">
						<option id="${num}" value="${num}">${num}月</option>
				</c:forEach>
			</select>
		</td>
<!-- 区域地市 -->
		<td class="label">
			所属地市：&nbsp;
		</td>
		<td class="content">
					<!-- 地市 -->			
					<select name="region" id="region"
						alt="allowBlank:false,vtext:'请选择所在地市'">
						<option value="">
							--请选择所在地市--
						</option>
						<logic:iterate id="regionBuffer" name="regionBuffer">
							<option value="${regionBuffer.areaid}">
								${regionBuffer.areaname}
							</option>
						</logic:iterate>
						
					</select>
		</td>
	</tr>
	<tr>	
<!-- 维护公司 -->
		<td class="label" >
			合作伙伴名称：&nbsp;
		</td>
		<td class="content" >
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" 
						alt="allowBlank:false,vtext:'请选择合作伙伴'">
						<option value="">
							--请选择合作伙伴--
						</option>	
															
					</select>
		</td>
		<td class="label" >
			维护专业：&nbsp;
		</td>
		<td class="content">
			
			<eoms:comboBox name="serviceProfession" id="serviceProfession" initDicId="1121201" defaultValue=""
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			
		</td>
	
	</tr>

</table>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>


<table class="formTable">
<%
Map rowMap = (Map)request.getAttribute("rowMap");
Map partnerNumMap = (Map)request.getAttribute("partnerNumMap");
List listSiteStat = (List)request.getAttribute("listPersonStat");
List statFormList = (List)request.getAttribute("statFormList");
int professionSize = StaticMethod.nullObject2int(request.getAttribute("professionSize"));
%>
			<tr>
				<td class="label">地市</td>
				<td class="label">合作伙伴名称</td>
				<td class="label">维护专业</td>
				<td class="label">合作伙伴应配人数</td>
				<td class="label">合作伙伴实配人数</td>
			</tr>
				<%
				if(statFormList!=null){	
					String areaId = "";
					String partnerId = "";
					BaseinfoStatForm statForm = null;
					int areaRowNum = 0;
					int partnerRowNum = 0;
					for(int i=0;i<statFormList.size();i++,areaId = statForm.getAreaId(),partnerId = statForm.getPartnerId()){
						statForm = (BaseinfoStatForm)statFormList.get(i);
						areaRowNum = StaticMethod.nullObject2int(partnerNumMap.get(statForm.getAreaId()+"_num"));
						partnerRowNum = areaRowNum*professionSize;
				%>
			<tr>
					<%
					if(!areaId.equals(statForm.getAreaId())){
						
					%>
						<td rowspan="<%=partnerRowNum%>">
							<eoms:id2nameDB id="<%=statForm.getAreaId() %>" beanId="tawSystemAreaDao" /> 
						</td>
				
					<%	
					}
					if(!areaId.equals(statForm.getAreaId())||!partnerId.equals(statForm.getPartnerId())){
					%>
						<td rowspan="<%=professionSize%>">
						<%=statForm.getPartnerName() %> 
						</td>
					<%
					}
					%>
				<td >
				<%=statForm.getProfessionName() %>   
				   
				</td>
				<td >
				<%=statForm.getUserConfig() %>  
				   
				</td>
				<td >
				<%=statForm.getUserNum() %>  
				   
				</td>
			</tr>
			<%} }%>

</table>



<%@ include file="/common/footer_eoms.jsp"%>