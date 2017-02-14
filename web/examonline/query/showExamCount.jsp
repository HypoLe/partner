<%@ page language="java" pageEncoding="UTF-8"  %>
<html>
   <head>
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

   <title>${form.pagetitle}</title>
	<!--link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css" --> 
	<script type="text/javascript">
	function showsub(url)
    {
        var _sHeight = 200;
        var _sWidth = 420;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
      ///var url='${app}/examonline/designate.do?deptid='+deptid+"&issueid="+issueid+"&testerCount="+testerCount;
        
      var chkk= window.showModalDialog(url,window,sFeatures);
         //if(chkk){
            //window.location.reload();
        //}
    }
	</script>
 </head>
	<div id="tabs">
	<body>
		<center>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
	        <tr>
	          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
	          <td>题库统计</td>
	          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
	        </tr>
</table>
		
<div id="sheet-list">
<table width="100%"  cellspacing="1" cellpadding="1" class="datalist" align=center border="0"> 
    <tr class="a_table_2">
    <!-- 此处写标题 -->
     <td width="63"  nowrap="nowrap" valign="bottom"><p align="left">专业</p></td>
     <td width="63"  nowrap="nowrap" valign="bottom">单选题</td>
     <td width="63" nowrap="nowrap" valign="bottom">多选题</td>
     <td width="63"  nowrap="nowrap" valign="bottom">判断题</td>
     <td width="63" nowrap="nowrap" valign="bottom">简答题</td>
     <td width="63" nowrap="nowrap" valign="bottom">论述题</td>
  </tr>
  <!-- 以下的 showlist为固定参数,表示从数据库取出的数据列表,row表示showlist列表中的每一条数据的别名,这个名字自己自己取 -->
   <c:forEach items="${list}" var="map">
	  <tr class="a_table_1">
			  <!-- 此处写从数据库取出的数据列表 -->
			  
			   <td nowrap="nowrap" >
			   	${map.specialtyName}
			   </td>
			   
			  <td nowrap="nowrap" >
			   <a onclick="showsub('examCountQuery.do?method=findManufacturerCount&contype=1${map.url}')">${map.key1}</a>
			 </td>
			 
			  <td nowrap="nowrap">
			   <a onclick="showsub('examCountQuery.do?method=findManufacturerCount&contype=2${map.url}')">${map.key2}</a>
			  </td>
			  
			  <td nowrap="nowrap">
			   <a onclick="showsub('examCountQuery.do?method=findManufacturerCount&contype=3${map.url}')">${map.key3}</a>
			</td>
			   <td nowrap="nowrap">
			   <a onclick="showsub('examCountQuery.do?method=findManufacturerCount&contype=4${map.url}')">${map.key4}</a>
			 </td>
			   <td nowrap="nowrap">
			   <a onclick="showsub('examCountQuery.do?method=findManufacturerCount&contype=5${map.url}')">${map.key5}</a>
			 </td>
	   </tr>
    </c:forEach>
</table>

	</div>
		</center>
		<iframe src="examCountQuery.do?method=goExamDetailListPage" width="100%" height="330"></iframe>

	<input name="button" type="button" class="add_btn_04"  value=" 试题导入" onclick="window.open('<%=basePath%>examonline/import_word.do')" />
	</body>
</html>