<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javascript">
Ext.onReady(function(){
	// 人员树
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
	userViewer = new Ext.JsonView("user-list",
		'<div id="user-{id}" class="viewlistitem-{nodeType}">{name}</div>',
		{ 
			multiSelect: true,
			emptyText : '<div>请选择质检人</div>'
		}
	);
	var userStr = '[]';
	userViewer.jsonData = eoms.JSONDecode(userStr);
	userViewer.refresh();
	
	userTree = new xbox({
		btnId:'userTreeBtn',dlgId:'dlg-user',
		treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'质检人',treeChkMode:'',treeChkType:'user',
		viewer:userViewer,saveChkFldId:'recieverOrgId', returnJSON:'true'
	});
})
</script>

<form action="../tawwpexecute/queryqualitylist.do" method="post">

  <table border="0"  width="600" class="formTable">
    <caption><bean:message key="querymonthplan.title.formTitle" /></caption>
      <tr>
	      <td width="100" class="label">
	         执行项名称 
	      </td>
	      <td width="500">
			<input type="text" name="executeName" id="executeName" size="30" readonly="true" value="" class="text">
	      </td>
      </tr>

      <tr>
	      <td width="100" class="label">
	        选择质检人 
	      </td>
    
	      <td width="500">
			<div id="user-list" class="viewer-box"></div>
			<input type="button" value="选择审核人" id="userTreeBtn" class="btn"/>
			<input type="hidden" name="recieverOrgId" id="recieverOrgId"/>
	      </td>
    	</tr>


      <td width="100" class="label">
        质检状态
      </td>
      <td width="500" >
        <select name="qualitystate" class="select">
          <option value="-1">请选择</option>
          <option value="0">待质检</option>
          <option value="1">质检通过</option>
          <option value="2">质检不通过</option>
        </select>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="查询" name="B1" class="submit">
</form>

<%@ include file="/common/footer_eoms.jsp"%>

