<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="com.boco.eoms.base.model.PageModel"%>
<%@ page import="java.util.*,
com.boco.eoms.examonline.model.ExamConfig"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="pg"  uri="http://jsptags.com/tags/navigation/pager" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style type="text/css">
	.title {width:150px; line-height:25px; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;}
	*{margin:0;padding:0; font-family:"微软雅黑";}
	.table_1{margin:4px;}
	.tltle_bg{background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fff;}
	.inter{background:url(<%=basePath%>examonline/manage/images/table_bg.gif) repeat-x; color:#9d9d9f;line-height:22px; font-size:12px;padding-left:15px;}
	.table_2{background:#d7e1ed; border-top:1px solid #a2b3cf; border-bottom:1px solid #a2b3cf; line-height:27px;padding-left:15px;font-size:12px;color:#546c8e;}
	.txt_input{background:#f0f1f6; border:1px solid #c4ceda;height:19px;width:60%;}
	.a_table_1,.a_table_2{ border-top:1px solid #fff; border-bottom:1px solid #d2e2ef; line-height:27px;color:#546c8e;padding-left:5px;font-size:12px;}
	.a_table_2{background:#e9eef2; }
	.a_table_1{background:#f1f1f1;}
	.sel_input{height:18px;border:1px solid #c5cdda; width:60%; line-height:18px;font-size:12px;}
	.table_3{height:51px; border-top:1px solid #fff; border-bottom:1px solid #fff; background:#f5f5f5;}
	.table_4{background:url(<%=basePath%>examonline/manage/images/table_bg2.gif) repeat-x;height:20px;}
	.datalist{border-bottom:1px solid #aebdd4;}
	.datalist th{background:url(<%=basePath%>examonline/manage/images/table_bg3.gif) repeat-x;color:#546c8e;border-left:1px solid #fff; border-right:1px solid #a1a7bd; font-size:12px; font-weight:normal;}
	.datalist td{border-top:1px solid #aebdd4; padding-left:5px; font-size:12px; line-height:22px;}
	.datalist tr {background-color:expression((this.sectionRowIndex%2==0)?"#fff":"#f0f1f6")}
	.datalist tr:hover, .datalist tr.altrow{background-color:#d7e1ed;/* 动态变色 */}
	.page_style{background:url(<%=basePath%>examonline/manage/images/table_bg4.gif) repeat-x;height:27px; font-size:12px; padding-left:20px;}
	
	/*-- button 风格 begin ---*/
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_14,.add_btn_15,.add_btn_frist,.add_btn_prev,.add_btn_next,.add_btn_last{height:21px;border:0;font-size:12px;font-family:"宋体";line-height:21px;padding-left:3px;}
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_frist,.add_btn_prev,.add_btn_next,.add_btn_last{color:#dbffff;}
		.add_btn_14,.add_btn_15{color:#eee;}
		.add_btn_02{background:url(<%=basePath%>examonline/manage/img/pic_w2.png) no-repeat;width:52px;}
		.add_btn_03{background:url(<%=basePath%>examonline/manage/img/pic_w3.png) no-repeat;width:65px;}
		.add_btn_04{background:url(<%=basePath%>examonline/manage/img/pic_w4.png) no-repeat;width:77px;}
		.add_btn_05{background:url(<%=basePath%>examonline/manage/img/pic_w5.png) no-repeat;width:90px;}
		.add_btn_06{background:url(<%=basePath%>examonline/manage/img/pic_w6.png) no-repeat;width:101px;}
		.add_btn_14{background:url(<%=basePath%>examonline/manage/img/pic_1w4.png) no-repeat;width:78px;}
		.add_btn_15{background:url(<%=basePath%>examonline/manage/img/pic_1w5.png) no-repeat;width:87px;}
	/*-- 翻页按钮 --*/
		.add_btn_frist{background:url(<%=basePath%>examonline/manage/images/first.gif) no-repeat;width:15px;}
		.add_btn_prev{background:url(<%=basePath%>examonline/manage/images/previous.gif) no-repeat;width:12px;}
		.add_btn_next{background:url(<%=basePath%>examonline/manage/images/next.gif) no-repeat;width:12px;}
		.add_btn_last{background:url(<%=basePath%>examonline/manage/images/last.gif) no-repeat;width:15px;}
	/*-- 数字按钮 --*/	
		.num_btn_01{border:0;background:no-repeat;text-decoration:underline;}
	/*-- button 风格 end ---*/
</style>

<html>
<head>


<title>
考试试题发布配置页面
	</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${app}/scripts/base/prototype.js"></script>
	<script type="text/javascript">
		function getisseuUser(num,issueid){
			var url="<%=basePath%>examonline/gettestersbyIssueid.do";
			var parssf="issueid="+issueid;
			new Ajax.Updater("tesers"+num,url,{method:'post',parameters:parssf});
		}
	
		//指派参考人
		function designate(num,o,testerCount,issueid,cmpid){
	        var _sHeight = 600;
	        var _sWidth = 820;
	        var sTop=(window.screen.availHeight-_sHeight)/2;
	        var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	      	var url='<%=basePath%>examonline/designate.do?companyid='+cmpid+"&issueid="+issueid+"&testerCount="+testerCount;
	      	var chkk= window.showModalDialog(url,window,sFeatures);
	       	if(chkk){
	       		//o.disabled='disabled';
	       		//o.value='已经指派';
	      	 	o.style.display='none';
	      	 	getisseuUser(num,issueid);
	       		document.getElementById('tesers'+num).style.display='';
	        }
    	}
	</script>

  </head>
  
  <body>
    <tr>
    <td>
	<div class="table_1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg">
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>试卷汇总</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
      </table>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="datalist">
  <tr>
  <th width="17%">试卷名称</th>
  <th width="10%">参考单位</th>
  <th width="7%">参考人数</th>
  <th width="15%">开考时间</th>
  <th width="15%">结束时间</th>
  <th>指派任务</th>
  </tr>
  
    <%
    Object obj = request.getAttribute("pm");
  if(obj!=null){  
  PageModel pm = (PageModel)obj;
  List l=pm.getDatas();
  int j=0;
  for(int i=0;l!=null && i<l.size();i++)
  {
  j++;
  ExamConfig ec=(ExamConfig)l.get(i);
   %>
   
  <tr>
    	<td>
  		<div align="center">
  		<font title="<%=ec.getTitle() %>"><div class="title"><%=ec.getTitle() %></div></font>
  		</div>
  	</td>
    <td><%=com.boco.eoms.examonline.util.ExamUtil.examCompanyMap.get(ec.getCompanyId())%></td>
    <td><%=ec.getTesterCount()%></td>
    <td><%=ec.getStartTime()%></td>
    <td><%=ec.getEndTime()%></td>
    <td>
    
   <%
   if(ec.isAssignment()){
    %>
    <!-- <input type="button"  id="afterdesignate" disabled="disabled" class="btn"  value="已经指派"/><br> -->
     <%
    String t = ec.getTester();
    pageContext.setAttribute("t",t);
    request.setAttribute("t",t);
     %>
    <c:set var="str" value="<%=ec.getTester() %>"/><c:set var="array" value="${fn:split(str, ';')}"/>
    
     <textarea rows="2" style="width: 100%"  id="show_tesers<%=j%>" readonly  ><c:forEach var="token" items="${array}" varStatus="cou" step="1"><c:if test="${cou.index%4==0}" ></c:if><eoms:id2nameDB id="${token}" beanId="tawSystemUserDao"/><c:choose><c:when test="${cou.last}"></c:when><c:otherwise>,</c:otherwise></c:choose></c:forEach></textarea>
    <%} else{%>
      	<input name="button" type="button" class="add_btn_05"  value=" 指派参考人" onclick="designate('<%=j%>',this,<%=ec.getTesterCount()%>,'<%=ec.getIssueId()%>',<%=ec.getCompanyId()%>)"/>
      <br>
    <%} %>
    <textarea rows="2" style="width: 100%"  id="tesers<%=j%>" style="display:none;" readonly  ><c:forEach var="token" items="${array}" varStatus="cou" step="1"><c:if test="${cou.index%4==0}" ></c:if><eoms:id2nameDB id="${token}" beanId="tawSystemUserDao"/><c:choose><c:when test="${cou.last}"></c:when><c:otherwise>,</c:otherwise></c:choose></c:forEach></textarea>
    </td>
  </tr>
  <%} 
  }%>
</table>
<div class="page_style">
	  <table width="100%" height="27" border="0" cellpadding="0" cellspacing="0">
       <TBODY>
					<TR>
						<TD height=28 align=right vAlign=center noWrap>
							&nbsp;&nbsp;
							<!-- 可以在这里插入分页导航条 -->
							<pg:pager url="examConfigList.do" items="${pm.total }"
								export="currentPageNumber=pageNumber" maxPageItems="10">
								<pg:param name="examType" value="${examType}"/>
								<pg:first>
									<input name="button" type="button" class="add_btn_frist"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:first>
								<pg:prev>
									<input name="button" type="button" class="add_btn_prev"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:prev>
								<pg:pages>
									<c:choose>
										<c:when test="${currentPageNumber eq pageNumber }">
											<font color="red">${pageNumber }</font>
										</c:when>
										<c:otherwise>
											<input name="button" type="button" class="num_btn_01" value="${pageNumber}" onclick="window.location.href='${pageUrl}'" />
										</c:otherwise>
									</c:choose>
								</pg:pages>
								<pg:next>
									<input name="button" type="button" class="add_btn_next"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:next>
								<pg:last>
									<input name="button" type="button" class="add_btn_last"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:last>
							</pg:pager>
						</TD>
					</TR>
				</TBODY>
      </table>
</div>
	</div>
	</td>
  </tr>
</div>
  </body>
</html>
