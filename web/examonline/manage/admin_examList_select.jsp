<%@ page language="java" import="java.util.*,com.boco.eoms.gz.util.TimeUtil,com.boco.eoms.common.util.StaticMethod" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.boco.eoms.examonline.model.ExamConfig"%>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript" src="<%=basePath%>examonline/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript">
  function openQuery(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}
	
	function submitQuery(){
		document.forms[0].submit();
	}
  
  </script>
    <base href="<%=basePath%>">
    
    <title>实时联考统计子页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  

<html:form action="getAdminSelectExamList.do" method="post">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	<div class="table_1">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg">
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>查询统计</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
      </table>
	  
	  <div class="table_2">
	  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr >
    <td width="33%" class="a_table_1">
    	<img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 
    		开始日期
        <input class="Wdate text" type="text"  name="starttime" id="starttime" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
    </td>
    <td width="33%" class="a_table_1">
    	<img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 
    		结束日期
        <input class="Wdate text" type="text"  name="starttime" id="endtime" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
    </td>
    <td width="33%" class="a_table_1"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 
    	考试级别<html:select size='1' property="provtype"
					styleClass='select'>
					<html:option value=''>全部</html:option>
					<html:option value='1'>省级</html:option>
					<html:option value='2'>网络部</html:option>
					<html:option value='3'>地级</html:option>
				</html:select>
    </td>
   </tr>
   <tr>
    <td width="33%" class="a_table_1">
    <img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />
    	地州名称
    		<html:select size='1' property="company" styleClass="select">
					<html:option value=''>全部</html:option>
					<html:option value="13">监控室</html:option>
					<html:option value="14">贵阳分公司</html:option>
					<html:option value="15">遵义分公司</html:option>
					<html:option value="16">安顺分公司</html:option>
					<html:option value="17">黔南分公司</html:option>
					<html:option value="18">黔东南分公司</html:option>
					<html:option value="19">铜仁分公司</html:option>
					<html:option value="20">毕节分公司</html:option>
					<html:option value="21">六盘水分公司</html:option>
					<html:option value="22">黔西南分公司</html:option>
					<html:option value="115">代维公司</html:option>
					<html:option value="1721">交换维护室</html:option>
					<html:option value="1722">支撑室</html:option>
					<html:option value="1723">无线网优室</html:option>
					<html:option value="1301">数据传输室</html:option>
					<html:option value="10103">综合分析室</html:option>
				</html:select>
				</td>
    <td width="33%" class="a_table_1"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />
    	专业类型
    	
   		<!--<html:select size='1' property="specialty" styleClass='select'>
			<html:option value=''>全部</html:option>
			${specialtySelList }
		</html:select>-->
		<select name="specialtySel" id="specialtySel" class="sel_input"">
         		${specialtySelList}
            </select>
	</td>
	<td width="33%" class="a_table_1">
	</td>
  </tr>


</table>

	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_3">
        <tr>
          <td align="center">
          	<input name="button" type="button" class="add_btn_05"  value="查询" onclick="submitQuery()" />
          </td>
        </tr>
      </table>
	  <div class="table_4"></div>
	</div>
	
	</td>
  </tr>
  
<tr>
    <td height="6" bgcolor="#cacaca"></td>
  </tr>
</table>
</html:form>

  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>实时联考</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
  	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datalist">
	  <tr>
		  <th width="3%">序号</th>
		  <th width="20%">考试名称</th>
		  <th width="10%">参考单位</th>
		  <th width="25%">考试时间</th>
		  <th width="9%">规定考试人数</th>
		  <th width="7%">缺考人数</th>
	  </tr>
  
   <% 
   		List list = new ArrayList();
   		if(request.getAttribute("examlist") !=null){
   			list = (List)request.getAttribute("examlist");
   		}
   		for(int j=0;j<list.size();j++){
   			ExamConfig ec=(ExamConfig)list.get(j);
   	%>		
	   	 <tr height="35">
		      <td><%=j+1%></td>
		      <td><%=ec.getTitle()%></td>
		      <td><%=com.boco.eoms.examonline.util.ExamUtil.examCompanyMap.get(ec.getCompanyId())%></td>
		      <td><%=ec.getStartTime()%>--<%=ec.getEndTime()%></td>
      		  <td><%=ec.getTesterCount()%></td>
      		  <td><%=ec.getNotestedCount()%></td>
    	 </tr>
   	<%
   	}
   	if(list.size() != 0){
    %>
  	  
	  <table width="100%" class="table_2" height="20" border="0" cellpadding="0" cellspacing="0">
       <TBODY>
					<TR>
						<TD height="28" align=right vAlign=center noWrap>
							&nbsp;&nbsp;
							<!-- 可以在这里插入分页导航条 -->
							<pg:pager url="getAdminSelectExamList.do" items="${resultSize }"
								export="currentPageNumber=pageNumber" maxPageItems="10">
								
								<pg:param name="starttime"/>
								<pg:param name="provtype"/>
								<pg:param name="company"/>
								<pg:param name="specialty"/>
								
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
  	<%} %>
  
  </body>
</html>
