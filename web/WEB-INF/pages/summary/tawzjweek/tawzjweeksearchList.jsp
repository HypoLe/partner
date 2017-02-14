<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
  	body{background-image:none;}
</style>

<div class="list-title">
 <FONT assign="center"> 查询结果</FONT>
</div>
<div class="list">
 
 
<display:table name="TawzjWeekList" cellspacing="0" cellpadding="0"
    id="TawzjWeekList" pagesize="15" class="table TawzjWeekList"
    export="true"  sort="list" partialList="true" size="resultSize"
    requestURI="${app}/summary/tawzjweek.do?method=search"  >

	<display:column property="name" sortable="true" headerClass="sortable"
         title="模板名称" href="${app}/summary/tawzjweek.do?method=listview" paramId="id" paramProperty="id"/>
         
    <display:column property="weekid" sortable="true" headerClass="sortable"
         title="周期"/>
    <display:column property="cruser" sortable="true" headerClass="sortable"
         title="申请人"/>
    <display:column property="auditer" sortable="true" headerClass="sortable"
         title="审核人"/>
     <display:column property="state" sortable="true" headerClass="sortable"
         title="审核人"/>   
 
    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
</display:table>
 
<%@ include file="/common/footer_eoms.jsp"%>