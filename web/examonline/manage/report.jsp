<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
题目报批
</title>
<link rel="stylesheet" type="text/css" href="css.css" />
<script language="javascript">
  function selectSpecialty(){
    document.all.specialtySel.value = "true";
  }

  function del(){
    document.forms[0].action = "delImport.do";
    var form = document.forms[0];
    if( form.specialtySel.value != "true" && form.action != "delImport.do" ){
      alert("必须选择专业类型");
      return false;
    }

      for (var i = 0; i < form.elements.length; i++){
        var obj = form.elements[i];
        if ( obj.type == 'checkbox' ){
          if ( obj.checked ){
              form.checkSel.value = form.checkSel.value + ",'" + obj.name + "'";
            }
        }
      }
      form.checkSel.value = form.checkSel.value.substring(1);
    var msg = "";
    if( form.action == "delImport.do" )
       msg = "删除";
    else
       msg = "报批";
    if( form.checkSel.value == "" ){
      alert(msg + "项尚未选择");
      return false;
    }
    if( !confirm( "是否确认"+ msg +"操作" ) )
      return false;
    document.forms[0].submit();
  }

  function reportDO(){
    document.forms[0].action = "reportDO.do";
  }

  function checkform(){
    var form = document.forms[0];
    if( form.specialtySel.value != "true" && form.action != "delImport.do" ){
      alert("必须选择专业类型");
      return false;
    }

      for (var i = 0; i < form.elements.length; i++){
        var obj = form.elements[i];
        if ( obj.type == 'checkbox' ){
          if ( obj.checked ){
              form.checkSel.value = form.checkSel.value + ",'" + obj.name + "'";
            }
        }
      }
      form.checkSel.value = form.checkSel.value.substring(1);
    var msg = "";
    if( form.action == "delImport.do" )
       msg = "删除";
    else
       msg = "报批";
    if( form.checkSel.value == "" ){
      alert(msg + "项尚未选择");
      return false;
    }
    if( !confirm( "是否确认"+ msg +"操作" ) )
      return false;
  }
</script>



<%int i = 1;%>

</head>
<body bgcolor="#ffffff">
<form action="${pageContext.request.contextPath}/examonline/reportDO.do" >
<input type="hidden" name="checkSel" />
<input type="hidden" name="specialtySel" />
<table width="100%"    cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
   <tr>
    <td width="6%" class="td_label" align="center">&nbsp;
    </td>
    <td width="6%" class="td_label" align="center">
      序号
    </td>
    <td class="td_label" align="center" width="17%">
      入库人
    </td>
    <td class="td_label" align="center" width="23%">
      入库时间
    </td>
     
    <td class="td_label" align="center" width="10%">
      专业
    </td>
     <td class="td_label" align="center" width="10%">
      厂家
    </td>
     <td class="td_label" align="center" width="10%">
      难度
    </td>
    <td class="td_label" align="center" width="10%">
      题型
    </td>
    <td class="td_label" align="center" width="10%">
      修改
    </td>
    <!--  
    <td class="td_label" align="center" width="28%">
      批复意见
    </td>
    -->
  </tr>


  <logic:iterate id="onlineInfo" name="INFOLIST" type="com.boco.eoms.examonline.model.ExamInfo">
    <tr class="tr_show" align="center">
      <td width="6%" align="center">
        <input type="checkbox" name="<%=onlineInfo.getImportId()%>">
      </td>
      <td width="6%" align="center">
        <%=i++%>
      </td>
      <td width="17%">
      ${onlineInfo.importUser}
      
      </td>
      <td width="23%">
      ${fn:substringBefore(onlineInfo.importTime,".")} 
       
      </td>

      <td width="10%">
      <%--<%=ExamUtil.getDictNameByDictid(onlineInfo.getSpecialty())
       %>--%>
      <c:set var="array" value="<%=onlineInfo.getSpecialty() %>"/>
       <eoms:id2nameDB id="${array}" beanId="ItawSystemDictTypeDao"/>
      </td>
       <td width="10%">
        <c:set var="array" value="<%=onlineInfo.getManufacturer() %>"/>
       <eoms:id2nameDB id="${array}" beanId="ItawSystemDictTypeDao"/>
      </td>
      <td width="10%">
 
     
     <c:if test="${onlineInfo.difficulty eq 1}">
     初级
     </c:if>
      <c:if test="${onlineInfo.difficulty eq 2}">
     中级
     </c:if>
      <c:if test="${onlineInfo.difficulty eq 3}">
     高级
     </c:if>
       
      </td>
        <td width="10%">
       <c:if test="${onlineInfo.difficulty eq 1}">
      单选题
       </c:if>
      <c:if test="${onlineInfo.difficulty eq 2}">
      多选题
       </c:if>
      <c:if test="${onlineInfo.difficulty eq 3}">
      判断题
       </c:if>
      <c:if test="${onlineInfo.difficulty eq 4}">
      简答题
       </c:if>
       <c:if test="${onlineInfo.difficulty eq 5}">
      论述题
      </c:if>
      </td>
<%
  String url = "reportDetail.do";
  java.util.HashMap map = new java.util.HashMap();
  map.put( "importId" , onlineInfo.getImportId() );
  pageContext.setAttribute( "map" , map );
%>
      <td width="10%">
        <html:link href="<%=url%>"  name="map" target="_blank">
          <img src="./manage/image/bottom/an_bj.gif" border="0">
        </html:link>
      </td>
      
    </tr>
  </logic:iterate>
  </table>
</div>
<br>
    <input name="button" type="button" class="add_btn_02"  value=" 删除" onclick="return del()" />
</center>

</form>

  <table width="100%" class="table_2" height="20" border="0" cellpadding="0" cellspacing="0">
       <TBODY>
					<TR>
						<TD height=28 align=right vAlign=center noWrap>
							&nbsp;&nbsp;
							<!-- 可以在这里插入分页导航条 -->
							<pg:pager url="report.do" items="${resultSize }"
								export="currentPageNumber=pageNumber" maxPageItems="10">
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
</body>
</html>
