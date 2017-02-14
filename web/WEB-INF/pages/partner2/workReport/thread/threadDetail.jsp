<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<div id="qucikNavRef" style="margin-bottom: 15px">

	<!--  如果listType有值的话，表示该页面是来自于"我的报告"列表 -->
	<c:if test="${empty listType }">
		<a class="linkDraft" href="${app }/partner2/workReport/thread.do?method=list&forumsId=${currentWorkReportForm.id}">
		返回报告专题<font color="red">${currentWorkReportForm.title }</font>
		</a>
	</c:if>
	<c:if test="${ not empty listType }">
		<a class="linkDraft" href="${app}/partner2/workReport/thread.do?method=listUnread&listType=${listType }">
		返回我的报告列表
		</a>
	</c:if>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"工作报告详细信息"</span>	
</div>

<div class="ui-widget-header">报告基本信息</div>
<div class="ui-widget-content">
  <table class="formTable">
    <tr>
      <td class="label">
       上报人:
      </td>
      <td >
        <eoms:id2nameDB id="${currentWorkReport.createrId }" beanId="tawSystemUserDao" />
      </td>
      <td class="label">
       阅读次数：
      </td>
      <td class="content">${currentWorkReport.threadCount }  </td>
    </tr> 
    <tr>
      <td class="label">
        上报时间：
      </td>
      <td class="content">${currentWorkReport.createTime }
      </td>
      <td class="label">
        发布范围：
      </td>
      <td class="content">
        <div id="view" class="viewer-list"></div>
      </td>
    </tr>
    <tr>
      <td class="label">
        报告内容：
      </td>
      <td class="content" colspan="3">
        ${currentWorkReport.content }
      </td>
    </tr>
    <tr>
      <td class="label" >
       报告附件：
      </td>
      <td class="content" colspan="3">
        <eoms:attachment name="currentWorkReport" property="accessories" 
            scope="request" idField="accessories" appCode="9" viewFlag="Y"/> 
      </td>
    </tr>
  </table>
 </div>

<!-- 当报告状态为草稿时，不显示改评阅报告块 -->

<c:if test="${currentWorkReport.status != '2' }">
<br/> 
<div class="ui-widget-header" id="showOption">评阅该报告<input type="image" class="detailView" src="${app}/nop3/images/icon-del.gif" value="del" /></div>
<div class="ui-widget-content" id="writeOption">
<html:form action="/thread.do?method=rotationRead" method="post" styleId="workReportForm">
<html:hidden property="id" />
		<html:hidden property="forumsId" />
		<html:hidden property="status" />
		<input type="hidden" id="reader" name="reader" />
		<input type="hidden" id="historyType" name="historyType" />
      	<textarea class="textarea max" name="comments" id="comments" alt="allowBlank:false" ></textarea>
        <input type="button" value="保存我的评阅意见" onclick="javascript:onSubmit(1);" class="button" />
</html:form>
</div>

<br/> 
<div class="ui-widget-header" id="showOption2">报告查看与评阅详细信息</div>
<div id="info-page">
		<div id="comments-info" class="tabContent">
				<display:table name="threadHistoryCountList" cellspacing="0" cellpadding="0" id="threadHistoryCountList" pagesize="${pageSizeCount}" class="table" export="false"
					requestURI="${app}/partner2/workReport/threadHistory.do?method=search&threadId=${id}" sort="list" partialList="true" size="resultSizeCount" decorator="com.boco.partner2.workReport.displaytag.support.ThreadCountHistoryListDisplaytagDecorator">
					<display:column property="userId" sortable="false" headerClass="sortable" title="阅读人" />
					<display:column property="count" sortable="false" headerClass="sortable" title="阅读次数" href="${app}/partner2/workReport/threadHistory.do?method=search&threadId=${currentWorkReport.id}&userId=" paramId="userId" paramProperty="userId" />
				</display:table>
		</div>

		<div id="count-info" class="tabContent">
				<display:table name="threadHistoryCommentsList" cellspacing="0" cellpadding="0" id="threadHistoryCommentsList" pagesize="${pageSizeComments }" class="table" export="false"
					requestURI="${app}/partner2/workReport/threadHistory.do?method=search&threadId=${currentWorkReport.id}" sort="list" partialList="true" size="resultSizeComments" decorator="com.boco.partner2.workReport.displaytag.support.ThreadBrowseHistoryListDisplaytagDecorator">
					<display:column property="userId" sortable="false" headerClass="sortable" title="评阅人" />
					<display:column property="readTime" sortable="false" headerClass="sortable" title="评阅时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
					<display:column property="comments" sortable="false" headerClass="sortable" title="评阅内容" />
				</display:table>
		</div>
	</div>
</c:if>



<script type="text/javascript">
	var readTree;
	var myJ = jQuery.noConflict();
	myJ(function() {
		myJ('div#showOption').bind('click',function(event){
			var myIcon = myJ('input.detailView');
			if(myIcon.val() =='add'){
				myIcon.attr('src','${app}/nop3/images/icon-del.gif').val('del');
			}else{
				myIcon.attr('src','${app}/nop3/images/icon-add.gif').val('add');
			}
			myJ('div#writeOption').fadeToggle("fast", "linear");
		});

		//显示发布范围
		deptViewer = new Ext.JsonView("view",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div></div>'
			}
		);
		var s='${jsonOrgs}';
		deptViewer.jsonData = eoms.JSONDecode(s);
		deptViewer.refresh();
		
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('count-info', "报告查看信息");
		
    		var hsyPage = tabs.addTab('comments-info', "报告评阅信息");

    		tabs.activate(0);
	});
	
	function onSubmit(flag){
	try{
		if(flag==0){		
    		if(document.forms[0].reader.value==""){
    			alert('请选择传阅范围');
    			return false; 
    		}
        	document.forms[0].submit();
        	return true;
    	}else{
    	    var o = '${currentWorkReport.reply }';
    	    if(o=="1"){
    	        if(document.forms[0].replyresult.value==""){
    			    alert("必须要回复,请选择回复结果!");
    			    return false; 
    		    }
    		}
    		if(document.forms[0].comments.value==""){
    			alert("请填写评阅意见!");
    			return false; 
    		}
        	document.forms[0].historyType.value="1";
        	document.forms[0].submit();
        	return true;
    	}
      }catch(e){}
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
