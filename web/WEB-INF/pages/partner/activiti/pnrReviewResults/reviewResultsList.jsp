<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	var myjs=jQuery.noConflict();
    var checkflag = "false";
    
    /**
	*  删除查询出所有项
	*/
	function relateQuery(){
		var handleMarkFlag = myjs("#handleMarkFlag").val();
		if(!handleMarkFlag || handleMarkFlag != 0){
			alert('删除查询出所有项时，处理情况必须为处理中');
			return;
		}else{
			var queryStr = "${queryStr}";
			if(!queryStr || queryStr!='OK'){
				alert('请首先进行查询操作');
				return;
			}else{
				if (confirm("是否删除查询出所有项?")==true){
					var form = document.getElementById('resCheckForm');
				    form.action = "${app}/activiti/pnrTransferOffice/pnrReviewResults.do?method=deletePnrReviewResults&deleteType=ALL&whereStr=${whereStr}";
				    form.submit();
				}
			}
		}
	}
	
	/**
	*  删除勾选项
	*/
	function relateChecked(){
		var handleMarkFlag = myjs("#handleMarkFlag").val();
		if(!handleMarkFlag || handleMarkFlag != 0){
			alert('删除勾选项时，处理情况必须为处理中');
			return;
		}else{
			var queryStr = "${queryStr}";
			if(!queryStr || queryStr!='OK'){
				alert('请首先进行查询操作');
				return;
			}else{
				var objs = document.getElementsByName("checkboxId");
				var flag = false;
		    	for(var i=0; i<objs.length; i++){
			       	if(objs[i].checked==true){
			      	     flag=true;
			           break;
			       }
		   		 }
				
			   if(flag==true){
			   		if (confirm("是否删除勾选项?")==true){
						//取第2个form表单
						var form = document.getElementById('resCheckForm');
					    form.action = "${app}/activiti/pnrTransferOffice/pnrReviewResults.do?method=deletePnrReviewResults&deleteType=SOME&whereStr=${whereStr}";
					    form.submit();
				    }
				}else if(flag==false){
				    alert("请选择要删除的项！");
				    return;
			    }
			}
	}
	
}

	
	function selectArchiveAll(obj) {
        var temp = document.getElementsByName("checkboxId"); 
        for (var i =0; i<temp.length; i++) 
        { 
            temp[i].checked = obj.checked; 
        } 
    } 
    
    function cancelArchive(obj,all) {
        var flag = 0; 
        var all = document.getElementsByName(all)[0]; 
        if (!obj.checked) 
        { 
            all.checked = false; 
        } 
        else 
        { 
            for (var i=0; i<document.getElementsByName(obj.name).length; i++) 
            { 
                if (!document.getElementsByName(obj.name)[i].checked) 
                { 
                    all.checked = false; 
                } 
                else 
                { 
                    flag++; 
                } 
            } 
            if (flag == document.getElementsByName(obj.name).length) 
            { 
                all.checked = true; 
            } 
        } 
    } 

   //重置
   function refresh(){
	    window.location.href = "${app}/activiti/pnrTransferOffice/pnrReviewResults.do?method=queryReviewResultsList";
   } 
   
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img src="${app}/images/icons/search.gif"
	 align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="">查询</span>
</div>
<div id="listQueryObject" style="display:true;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<html:form  action="pnrReviewResults.do?method=queryReviewResultsList" styleId="gridForm" method="post"> 
		<input type="hidden" value="${planId }" name="id"/>
		<center> 
			<table class="listTable">
				<!--时间 -->
					<tr>
						<td class="label">
							导入开始时间
						</td>
						<td class="content">
							<input type="text" class="text" name="importStartTime"
								readonly="readonly" id="importStartTime" value="${importStartTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'importEndTime',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							导入结束时间
						</td>
						<td class="content">
							<input type="text" class="text" name="importEndTime"
								readonly="readonly" id="importEndTime" value="${importEndTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'importStartTime',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
					</tr>
				<tr>
					<td class="label">处理情况</td> 
					<td class="content" colspan="3">
						<select name="handleMarkFlag" id="handleMarkFlag" class="select">
							<option value=""  <c:if test="${handleMarkFlag == ''}">selected="true"</c:if>>所有</option>
							<option value="0" <c:if test="${handleMarkFlag == '0'}">selected="true"</c:if>>处理中</option>
							<option value="1" <c:if test="${handleMarkFlag == '1'}">selected="true"</c:if>>已处理</option>
						</select>
					</td>
				</tr>
			</table>
		</center> 
		<table>
		    <tr>
			    <td>
			    	<input type="submit" class="btn" value="查询" />&nbsp;&nbsp;
			   		<input type="button" class="btn" value="删除勾选项" onclick="relateChecked()"/>&nbsp;&nbsp;
			    	<input type="button" class="btn" value="删除查询出所有项" id="queryItemBtn" onclick="relateQuery()"/>&nbsp;&nbsp;
			    	<input type="button" class="btn" value="重置"  onclick="refresh()"/>&nbsp;&nbsp;
			   <!-- <input type="button" class="btn" value="取消关联勾选项" onclick="relateCacel()"/>&nbsp;&nbsp; -->
			   <!-- <input type="button" class="btn" value="返回" onclick="backPlanList();"> -->
				</td>
			</tr>
		</table>	
	</html:form>
	<br/>
</div>

<html:form action="pnrReviewResults" method="post" styleId="resCheckForm">
	<input type="hidden" id="queryStr" name="queryStr" value="${queryStr}"/>
	<input type="hidden" id="whereStr" name="whereStr" value="${whereStr}" />
	
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="pnrReviewResults.do?method=queryReviewResultsList"
		sort="list" partialList="true" size="resultSize">
		<c:if test="${queryStr eq 'OK'}">
			<display:column  title="<input type='checkbox' id='archiveAll' name='archiveAll' onclick='javascript:selectArchiveAll(this);'>" >
				<input type="checkbox" name="checkboxId" onclick="cancelArchive(this,'archiveAll')" value="${list.id}" />
		    </display:column>
		</c:if>
		<display:column property="processInstanceId" sortable="false" headerClass="sortable" title="工单号" />
		<display:column title="回填环节">
			<c:if test="${list.backfillLink eq 'cityManageExamineAgency'}">
				市运维主管待办
			</c:if>
			<c:if test="${list.backfillLink eq 'cityManageExamineToReply'}">
				市运维主管待回复
			</c:if>
			<c:if test="${list.backfillLink eq 'provinceLineExamineAgency'}">
				省线路主管待办
			</c:if>
			<c:if test="${list.backfillLink eq 'provinceLineExamineToReply'}">
				省线路主管待回复
			</c:if>	
			<c:if test="${list.backfillLink eq 'waitingCallInterface'}">
				等待接口调用
			</c:if>	
		</display:column>
		<display:column title="会审结果">
			<c:if test="${list.reviewResult eq 'YES'}">
				合格
			</c:if>
			<c:if test="${list.reviewResult eq 'NO'}">
				不合格
			</c:if>
		</display:column>
		<display:column property="reviewTime" sortable="false" headerClass="sortable" title="会审时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column title="处理情况">
			<c:if test="${list.handleMark eq '0'}">
				处理中
			</c:if>
			<c:if test="${list.handleMark eq '1'}">
				已处理
			</c:if>
		</display:column>
		<display:column property="expertOpinion" sortable="false" headerClass="sortable" title="专家意见" />
		<display:column property="importTime" sortable="false" headerClass="sortable" title="导入时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column title="是否同意实施">
			<c:if test="${list.isAgreeStriking eq 'YES'}">
				同意
			</c:if>
			<c:if test="${list.isAgreeStriking eq 'NO'}">
				不同意
			</c:if>
		</display:column>		
				
	</display:table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>