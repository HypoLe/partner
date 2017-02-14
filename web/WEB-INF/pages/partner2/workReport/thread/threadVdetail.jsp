<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.base.util.StaticMethod"%>

<%String status = StaticMethod.nullObject2String(request
					.getAttribute("status"));%>
<title>标题</title>
<content tag="heading"><b>

</content>

<script type="text/javascript">
	var readTree;
	Ext.onReady(function(){
	    //只有通过审核才可以被传阅,否则不可以
		if(document.forms[0].status.value != "4" && document.forms[0].status.value !="6" && document.forms[0].status.value !="1"){
			document.getElementById("showRead").style.display="none";
			document.getElementById("clkRead").style.display="none";
		}

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
		
		//显示审批人
		auditUserViewer = new Ext.JsonView("auditView",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
			emptyText : '<div></div>'
			}
		);
		var auditUser='${jsonAudit}';
		auditUserViewer.jsonData = eoms.JSONDecode(auditUser);
		auditUserViewer.refresh();

		//选择传阅范围
		readViewer = new Ext.JsonView("readview",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div></div>'
			}
		);
		var readerdata = '[]';
		readViewer.jsonData = eoms.JSONDecode(readerdata);
		readViewer.refresh();
		var	treeAction='${app}/xtree.do?method=getDeptSubRoleUserTree';
		readTree = new xbox({
			btnId:'clkRead',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'传阅信息',treeChkMode:'',treeChkType:'user,dept,role,subrole',
			showChkFldId:'showRead',saveChkFldId:'reader',viewer:readViewer,returnJSON:true
		});

			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('count-info', '${eoms:a2u("查阅信息")}');
		
    		var hsyPage = tabs.addTab('comments-info', '${eoms:a2u("评阅信息")}');

    		tabs.activate(0);
	});

	function onSubmit(flag){
	try{
		if(flag==0){		
     		//if(document.forms[0].isSecret.value == "-1"){
    		//	alert('${eoms:a2u("请选择是否机密传阅!")}');
    		//	return false; 
    		//}
    		if(document.forms[0].reader.value=="[]"){
    			alert('${eoms:a2u("请选择传阅范围!")}');
    			return false; 
    		}
        	document.forms[0].submit();
        	return true;
    	}else{
    	    var o = '${threadForm.reply }';
    	    if(o=="1"){
    	        if(document.forms[0].replyresult.value==""){
    			    alert('${eoms:a2u("必须要回复,请选择回复结果!")}');
    			    return false; 
    		    }
    		}
    		if(document.forms[0].comments.value==""){
    			alert('${eoms:a2u("请填写评阅意见!")}');
    			return false; 
    		}
        	document.forms[0].historyType.value="1";
        	document.forms[0].submit();
        	return true;
    	}
      }catch(e){}
	}
</script>
<html:form action="/thread.do?method=rotationRead" method="post" styleId="workReportForm">
  <table class="formTable middle">
    <caption>
      头条:
      <eoms:id2nameDB id="${threadForm.forumsId }" beanId="forumsDao" />&nbsp;/&nbsp;
      <bean:write name="threadForm" property="title" />
    </caption>
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadForm.createrId" />
      </td>
      <td class="content max">
        <eoms:id2nameDB id="${threadForm.createrId }" beanId="tawSystemUserDao" />
      </td>
    </tr>
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadForm.threadTypeId" />
      </td>
      <td class="content">
        <eoms:dict key="dict-workbench-infopub" dictId="threadType" itemId="${threadForm.threadTypeId }" beanId="id2nameXML" />
      </td>
    </tr>
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadForm.Reply" />
      </td>
      <td class="content">
        <eoms:dict key="dict-workbench-infopub" dictId="reply" itemId="${threadForm.reply }" beanId="id2nameXML" />
      </td>
    </tr>    
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadForm.Validity" />
      </td>
      <td class="content">
        <bean:write name="threadForm" property="validityDate" />
      </td>
    </tr>  
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadForm.num" />
      </td>
      <td class="content">
        <bean:write name="threadForm" property="threadCount" />
      </td>
    </tr> 
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadForm.createTime" />
      </td>
      <td class="content">
        <bean:write name="threadForm" property="createTime" />
      </td>
    </tr>  
    <tr>
      <td class="label">
        发布范围：
      </td>
      <td class="content">
        <div id="view" class="viewer-list"></div>
      </td>
    </tr>
    <%if (!status.equals("6") && !status.equals("7")&& !status.equals("4") && !status.equals("1")){%>
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="forumsForm.audit" />
      </td>
      <td class="content">
        <div id="auditView"></div>
      </td>
    </tr>
    <%}else{%>
    <tr>
      <td class="content" colspan="2">
        <div id="auditView"></div>
      </td>
    </tr>
    <%}%>
    <%if(status.equals("4") || status.equals("6") || status.equals("1")){%>
    <tr>
      <td class="label">
        <logic:notEqual name="threadForm" property="status" value="7">
				<!-- 非机密传阅时显示 -->
				<eoms:label styleClass="desc" key="threadForm.tree.rotationRead" />
		</logic:notEqual>
      </td>
      <td class="content">
        <input type="text" readonly id="showRead" name="showRead" display="none" />
		<input type="button" id="clkRead" name="clkRead" value="传阅" display="none" />
		<div id="readview" class="viewer-list"></div>
      </td>
    </tr>
    <%}else{%>
    <tr>
      <td class="content" colspan="2">
        <input type="text" readonly id="showRead" name="showRead" display="none" />
		<input type="button" id="clkRead" name="clkRead" value="传阅" display="none" />
		<div id="readview"></div>
      </td>
    </tr>
    <%}%>
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadForm.content" />
      </td>
      <td class="content">
        ${threadForm.content }
      </td>
    </tr>
    <tr>
      <td class="label">
        ${eoms:a2u("附件")}
      </td>
      <td class="content">
        <eoms:attachment name="threadForm" property="accessories" 
            scope="request" idField="accessories" appCode="9" viewFlag="Y"/> 
      </td>
    </tr>
    <%if (status.equals("4") || status.equals("6") || status.equals("1")) {%>
    <c:if test="${threadForm.reply=='1' }">
    <tr>
      <td class="label">
        <eoms:label styleClass="desc" key="threadHistoryList.replyresult" />
      </td>
      <td class="content">
        <eoms:dict key="dict-workbench-infopub" dictId="replyresult" selectId="replyresult" beanId="selectXML" alt="allowBlank:false" defaultId="${threadForm.replyresult}" />
      </td>
    </tr>
    </c:if>
    <%}%>
    <%if (status.equals("4") || status.equals("6") || status.equals("1")) {%>
    <tr>
      <td class="label">笔记
      </td>
      <td class="content">
        <html:textarea property="comments" styleId="comments" styleClass="textarea medium" />
        <p/>
        <input type="button" value="保存笔记" onclick="javascript:onSubmit(1);" class="button" />
		<input type="button" value="传阅" onClick="javascript:onSubmit(0);" class="button" />
      </td>
    </tr>
    <%}%>
    <tr>
      
    </tr>
  </table>
  
	<ul>
		<html:hidden property="id" />
		<html:hidden property="forumsId" />
		<html:hidden property="status" />
		<input type="hidden" id="reader" name="reader" />
		<input type="hidden" id="historyType" name="historyType" />
	</ul>
	<br>
	<div id="info-page">
		<div id="count-info" class="tabContent">
			 
				<display:table name="threadHistoryCountList" cellspacing="0" cellpadding="0" id="threadHistoryCountList" pagesize="${pageSizeCount}" class="table threadHistoryCountList" export="false"
					requestURI="${app}/partner2/workReport/threadHistory.do?method=search&threadId=${id}" sort="list" partialList="true" size="resultSizeCount" decorator="com.boco.partner2.workReport.displaytag.support.ThreadCountHistoryListDisplaytagDecorator">

					<display:column property="userId" sortable="false" headerClass="sortable" titleKey="infopub.threadForm.user" />

					<display:column property="count" sortable="false" headerClass="sortable" titleKey="infopub.threadForm.num" href="${app}/partner2/workReport/threadHistory.do?method=search&threadId=${threadForm.id}&userId=" paramId="userId" paramProperty="userId" />

					<display:setProperty name="paging.banner.item_name" value="threadHistory" />
					<display:setProperty name="paging.banner.items_name" value="threadHistorys" />
				</display:table>
			 
		</div>

		<div id="comments-info" class="tabContent">
			 
				<display:table name="threadHistoryCommentsList" cellspacing="0" cellpadding="0" id="threadHistoryCommentsList" pagesize="${pageSizeComments }" class="table threadHistoryCommentsList" export="false"
					requestURI="${app}/partner2/workReport/threadHistory.do?method=search&threadId=${threadForm.id}" sort="list" partialList="true" size="resultSizeComments" decorator="com.boco.partner2.workReport.displaytag.support.ThreadBrowseHistoryListDisplaytagDecorator">

					<display:column property="userId" sortable="false" headerClass="sortable" titleKey="infopub.threadForm.user" />

					<display:column property="ip" sortable="false" headerClass="sortable" titleKey="infopub.threadForm.ip" />

					<display:column property="readTime" sortable="false" headerClass="sortable" titleKey="infopub.threadForm.readTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

					<display:column property="comments" sortable="false" headerClass="sortable" titleKey="infopub.threadForm.comments" />
					
					<display:column property="replyresult" sortable="false" headerClass="sortable" titleKey="infopub.threadForm.replyresult" />
					<display:setProperty name="paging.banner.item_name" value="threadHistory" />
					<display:setProperty name="paging.banner.items_name" value="threadHistorys" />
				</display:table>
		</div>
	</div>
</html:form>


<%@ include file="/common/footer_eoms.jsp"%>
