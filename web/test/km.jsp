<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
  <link rel="stylesheet" type="text/css" href="${app}/styles/common/reset-grids-base.css" />
  <style type="text/css">
body{overflow-x:hidden;overflow-y:auto;}
.bd{}
.grid{margin:5px 0 0 5px;}
.col-main{float:left;width:100%;}
.main-wrap{margin-right:215px;}
.col-sub{float:left;width:190px;margin-left:-205px;}
.box .hd h3{background:none no-repeat scroll 2px center;padding-left:20px;margin-left:3px;}
ol.order {padding-left:40px;}
ol.order li{list-style-type: decimal;}
.viewt {border:0;}
.viewt th{border:0;padding:6px;border-bottom:1px solid #6B95BF;font-weight:bold;color:#336699;}
.viewt td{background-color:#DFF3FF;border:0;padding:6px;}
</style>
<script type="text/javascript">
function on_off(e){
	e=e.parentNode.parentNode;
	var open="dir_item_open";
	e.className="dir_item_"+(e.className==open?"close":"open");
} 
</script>
</head>

<body>
<div id="page">
  <div id="content" class="clearfix">
    <div id="main"><br/>
    
<div class="grid-c3 skin-blue image-disabled">

 <div class="col-main">
    <div class="main-wrap">
    <!-- 搜索 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="bd" style="padding:10px;">
       <form>
       搜索:
       <select class="select">
         <option>知识库</option>
         <option>文档</option>
         <option>专家</option>
       </select>
       <input type="text" size="20" class="text"/>
       <input type="submit" class="submit" value="搜索"/>
       </form>
      </div>
      <span class="rc-bt"><span></span></span>
    </div>
   
    <!-- 工单 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="msg">工单</h3></div>
      <div class="bd" style="padding:10px;">
      
<table cellpadding="0" class="viewt" cellspacing="0" style="width:95%">
<thead>
<tr>
<th class="sortable">工单流水号</th>
<th class="sortable">工单主题</th>
<th class="sortable">创建时间</th>
<th class="sortable">处理环节</th></tr>
</thead>

<tbody>

<tr class="odd">
<td><a  href='#' >SC-051-090818-1-10012</a></td>
<td>null-ZMC2-alarmiiii74</td>
<td>2009-08-18 14:36:02</td>
<td>草稿</td></tr>

<tr class="even">
<td><a  href='#' >SC-051-090818-1-10011</a></td>
<td>null-ZMC2-主机设备链路通断键控告警测试告警标题</td>
<td>2009-08-18 14:23:25</td>
<td>草稿</td></tr>

<tr class="odd">
<td><a  href='#' >SC-051-090818-1-10010</a></td>
<td>null-ZMC2-主机设备链路通断键控告警测试告警标题</td>
<td>2009-08-18 14:22:23</td>
<td>草稿</td></tr>

<tr class="even">
<td><a  href='#' >SC-051-090818-1-10009</a></td>
<td>null-ZMC2-alarmiiii186</td>
<td>2009-08-18 11:24:06</td>
<td>草稿</td></tr>

<tr class="odd">
<td><a  href='#' >SC-051-090818-1-10008</a></td>
<td>null-ZMC2-alarmiiii25</td>
<td>2009-08-18 11:03:22</td>
<td>草稿</td></tr>

<tr class="even">
<td><a  href='#' >SC-051-090818-1-10007</a></td>
<td>null-ZMC2-alarmiiii25</td>
<td>2009-08-18 11:02:37</td>
<td>草稿</td></tr>

<tr class="odd">
<td><a  href='#' >SC-051-090818-1-10006</a></td>
<td>null-ZMC2-alarmiiii25</td>
<td>2009-08-18 11:01:32</td>
<td>草稿</td></tr>

<tr class="even">
<td><a  href='#' >SC-051-090818-1-10005</a></td>
<td>null-ZMC2-alarmiiii25</td>
<td>2009-08-18 10:59:48</td>
<td>草稿</td></tr>

<tr class="odd">
<td><a  href='#' >SC-051-090818-1-10004</a></td>
<td>null-ZMC2-alarmiiii25</td>
<td>2009-08-18 10:59:21</td>
<td>草稿</td></tr></tbody>
</table>
      
      </div>
  
      <div class="ft">
        <ul class="act">
          <li><a href="#" class="more">更多</a></li>
        </ul>
      </div> 
      <span class="rc-bt"><span></span></span>
    </div> 
  
    <!-- 最新更新 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="msg">最新更新</h3></div>
      <div class="bd" style="padding:10px;">

<table cellpadding="0" class="viewt" cellspacing="0" style="width:95%">
<thead>
<tr>
<th class="sortable">所属类别</th>
<th class="sortable">文档主题</th>
<th class="sortable">创建时间</th>
<th class="sortable">创建人</th></tr>
</thead>

<tbody>

<tr class="odd">
<td>知识库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 14:36:02</td>
<td>张波</td></tr>

<tr class="even">
<td>知识库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 14:23:25</td>
<td>张波</td></tr>

<tr class="odd">
<td>知识库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 14:22:23</td>
<td>刘世振</td></tr>

<tr class="even">
<td>知识库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 11:24:06</td>
<td>刘世振</td></tr>

<tr class="odd">
<td>知识库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 11:03:22</td>
<td>刘世振</td></tr>

<tr class="even">
<td>经验库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 11:02:37</td>
<td>冯小峰</td></tr>

<tr class="odd">
<td>经验库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 11:01:32</td>
<td>冯小峰</td></tr>

<tr class="even">
<td>经验库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 10:59:48</td>
<td>张波</td></tr>

<tr class="odd">
<td>经验库</td>
<td><a  href='#' >null-ZMC2-alarmiiii74</a></td>
<td>2009-08-18 10:59:21</td>
<td>张波</td></tr></tbody>
</table>

      </div>
  
      <div class="ft">
        <ul class="act">
          <li><a href="#" class="more">更多</a></li>
        </ul>
      </div> 
      <span class="rc-bt"><span></span></span>
    </div>
    
    </div>
  </div>
  
  <!-- 左列 -->
  <div class="col-sub">
  
    <!-- 用户登录信息 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="msg">用户登录信息</h3></div>
      <div class="bd" style="padding:5px;text-align:center;">
      	<div style="width:120px;text-align:center;">${sessionScope.sessionform.username}</div>
      	
		<div style="width:120px;height:120px;border:2px solid #6699cc;background-image:url(${app}/images/head/man.gif)">
		</div>
      	<div style="width:120px;text-align:left;">
      	知识贡献度积分:5<br/>
		专家积分：12<br/>
		继续努力!
		</div>
		
      </div>
      <div class="ft"><ul class="act"></ul></div>
      <span class="rc-bt"><span></span></span>
    </div>

    <!-- 导航树 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="tool">导航树</h3></div>
      <div class="bd" id="tools" style="padding:5px">
        <div class="dir_tree">
          <div class="dir_item_open">
            <div class="dir_folder">
              <a class="dir_turn_sym" onclick="on_off(this)"></a>
              <p class="now"><a class="dir_turn" title="">热点问题</a></p>
            </div>
            <div class="dir_tree_content">
              <p><a href="">热点问题1</a></p>
              <p><a href="">热点问题2</a></p>
              <p><a href="">热点问题3</a></p>
            </div>
          </div>
          <div class="dir_item_open">
            <div class="dir_folder">
              <a class="dir_turn_sym" onclick="on_off(this)"></a>
              <p class="now"><a class="dir_turn" title="">技术规范库</a></p>
            </div>
            <div class="dir_tree_content">
              <p><a href="">集团公司</a></p>
              <p><a href="">区公司</a></p>
            </div>
          </div>
          <div class="dir_item_open">
            <div class="dir_folder">
              <a class="dir_turn_sym" onclick="on_off(this)"></a>
              <p class="now"><a class="dir_turn" title="">其他部门资源库</a></p>
            </div>
            <div class="dir_tree_content">
              <p><a href="">网络部</a></p>
              <p><a href="">工建</a></p>
              <p><a href="">计划部</a></p>
            </div>
          </div>
          <div class="dir_item_open">
            <div class="dir_folder">
              <a class="dir_turn_sym" onclick="on_off(this)"></a>
              <p class="now"><a class="dir_turn" title="">经验库</a></p>
            </div>
            <div class="dir_tree_content">
              <p><a href="">故障经验库</a></p>
              <p><a href="">投诉经验库</a></p>
            </div>
          </div>
          <div class="dir_item_open">
            <div class="dir_folder">
              <a class="dir_turn_sym" onclick="on_off(this)"></a>
              <p class="now"><a class="dir_turn" title="">学习资料</a></p>
            </div>
            <div class="dir_tree_content">
              <p><a href="">培训</a></p>
            </div>
          </div>
        </div>
        <div class="clear"></div>
      </div>
 
      <div class="ft"><ul class="act"></ul></div>
      <span class="rc-bt"><span></span></span>
    </div>
      
  </div> 
   
  <!-- 右列 -->
  <div class="col-extra">
  
    <!-- 知识排名 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="msg">知识排名</h3></div>
      <div class="bd" style="padding:5px">
 
  <iframe id="knowledgeUsedOrder" name="knowledgeUsedOrder" frameborder="no" scrolling="no" width="200" height="150"
	src="../kmmanager/scoreOrderStatistic.do?method=knowledgeUsedOrder&orderNumber=5&type=main" ></iframe>

      </div>
 
      <div class="ft"><ul class="act"></ul></div> 
      <span class="rc-bt"><span></span></span>
    </div>
    
        <!-- 知识使用排名 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="msg">知识使用排行</h3></div>
      <div class="bd" style="padding:5px">
 
  <iframe id="knowledgeMonthUsedOrder" name="knowledgeMonthUsedOrder" frameborder="no" scrolling="no" width="200" height="150"
	src="../kmmanager/scoreOrderStatistic.do?method=knowledgeMonthUsedOrder&orderNumber=5&type=main" ></iframe>

      </div>
 
      <div class="ft"><ul class="act"></ul></div> 
      <span class="rc-bt"><span></span></span>
    </div>
        <!-- 员工贡献排行 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="msg">员工贡献排行</h3></div>
      <div class="bd" style="padding:5px">
 
  <iframe id="monthUserScoreOrder" name="monthUserScoreOrder" frameborder="no" scrolling="no" width="200" height="150"
	src="../kmmanager/scoreOrderStatistic.do?method=monthUserScoreOrder&orderNumber=5&type=main" ></iframe>

      </div>
 
      <div class="ft"><ul class="act"></ul></div> 
      <span class="rc-bt"><span></span></span>
    </div>
        <!-- 知识总量排行 -->
    <div class="box">
      <span class="rc-tp"><span></span></span>
      <div class="hd"><h3 class="msg">知识总量排行</h3></div>
      <div class="bd" style="padding:5px">
 
  <iframe id="monthKnowledgeCountOrder" name="monthKnowledgeCountOrder" frameborder="no" scrolling="no" width="200" height="150"
	src="../kmmanager/scoreOrderStatistic.do?method=monthKnowledgeCountOrder&orderNumber=5&type=main" ></iframe>

      </div>
 
      <div class="ft"><ul class="act"></ul></div> 
      <span class="rc-bt"><span></span></span>
    </div>
    
    
    
    
    
    
    
  </div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>