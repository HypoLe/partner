<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

  <script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
  <script type="text/javascript">
  var config = {
    /**************
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"${app}/kmmanager/KmrecordPer/getroom.do",
    treeRootId:'-1',
    treeRootText:"排班管理",
	pageFrameId:'formPanel-page',
	onClick:{url:"${app}/kmmanager/Kmassignwork/select_time.do?roomId=",text:"排班管理"},
	ctxMenu:[
	          {id:'Child', text:'排班管理',cls:'new-mi',url:'${app}/kmmanager/Kmassignwork/select_time.do?roomId='}
		    ],//end of ctxMenu 
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
  Ext.onReady(AppFrameTree.init, AppFrameTree);
  </script>
  <style type="text/css">
  	body{background-image:none;}
  </style>
<div id="headerPanel" class="x-layout-inactive-content">
    <h1>排班管理</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
	    <dt>选择值班小组</dt>
		<dd>列出来的值班小组都是管理员对用户配置的值班小组域，在选择的值班小组上点击右键，选择【排班管理】。或者【直接点击值班小组】</dd>
		<dt>排班</dt>
		<dd>在排班页面上有4种排班方式。【每天班数】是设置每天所排的班次数，【每班人数】是每个班次最多安排的人数，【起始人】就是定义值班的开始人是谁，在这里也有默认值班长的含义。不过要安排值班长到下个页面上可以选择。【开始时间】、【结束时间】是排班需要从哪个时间开安排到哪个时间结束。【周末是否要排班】只要在下面选择就可以了。如果当前没有安排值班可以【补排班】。点击【排班】进入选择页面。这里可以根据选择排班方式的不同出现不同的页面可以根据需求详细填写。【值班周期】的选择是以当前选择的天数乘以周期数来进行排班</dd>
	</dl>
</div>
<div id="treePanel" class="x-layout-inactive-content">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="formPanel-page" name="formPanel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

<%@ include file="/common/footer_eoms.jsp"%>