<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form_self.jsp"%>
<head>
<base target="_self" />
</head>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
var pid;         //计划id
var curPage=1;   //当前是第几页
var total=6;     //总共有多少张照片

</script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>

	<caption>
		<div class="header center">资产详单列表</div>
	</caption>
			
<br/><br/><br/>
	<display:table name="list" cellspacing="0" cellpadding="0"
			id="list" class="table list" export="true" requestURI="roomDemolition.do">
			<display:column sortable="false"
			headerClass="sortable" title="地市" property="city"/>
			<display:column sortable="false"
			headerClass="sortable" title="机房名称" property="roomName"/>
			<display:column sortable="false"
			headerClass="sortable" title="机房类型" property="roomType"/>
			<display:column sortable="false"
			headerClass="sortable" title="资产名称" property="assetName"/>
			<display:column sortable="false"
			headerClass="sortable" title="资产标签号"  property="assetNumbers"/>
			<display:column sortable="false"
			headerClass="sortable" title="资产类别"  property="assetsSort"  maxLength="15"/>
			<display:column sortable="false"
			headerClass="sortable" title="资产型号"  property="modelVersion"/>
			 <display:column property="assetsStartTime" sortable="false"
			headerClass="sortable" title="资产启用日期" format="{0,date,yyyy-MM-dd}" />
			<display:column sortable="false"
			headerClass="sortable" title="资产使用月数"  property="assetsMonthNum"/>
			<display:column sortable="false"
			headerClass="sortable" title="原值"  property="originalValue"/>
			<display:column sortable="false"
			headerClass="sortable" title="累计折扣"  property="accumulatedDepreciation"/>
			<display:column sortable="false"
			headerClass="sortable" title="资产净值"  property="assetsNet"/>
			<display:column sortable="false"
			headerClass="sortable" title="累计减值准备"  property="assetsDevalue"/>
			<display:column sortable="false"
			headerClass="sortable" title="退网使用方向"  property="direction"/>
			 
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" /> 
	</display:table> 
	
<div id="comments-info" class="tabContent"></div>

<%@ include file="/common/footer_eoms.jsp"%>