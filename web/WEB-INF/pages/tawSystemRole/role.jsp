<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<style type="text/css">
.preHeader{
	font-size:15px;
}
.preHelp{
	background:#EFFAFF;
	border:1px solid #8EACCF;
	margin:5px 0pt;
	padding:0 15px;
	vertical-align:top;
	line-height:1.6em;
}
</style>
<!-- loading -->
<div id="loading">
<div class="loading-indicator">数据载入中，请稍候</div>
</div>
<script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
<script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
<script type="text/javascript" src="${app}/scripts/layout/panel-query.js"></script>
<script type="text/javascript">
  
var treeRootId='1', treeRootText='所有角色',
    treeAction='tawSystemRoles.do?method=getNodes',
    treeAction_new = 'tawSystemRoles.do?method=xsave',
    treeAction_get = 'tawSystemRoles.do?method=xget&id=',
    treeAction_edt = 'tawSystemRoles.do?method=xedit&id=',
    treeAction_del = 'tawSystemRoles.do?method=xdel&id=',
    wfNodesAction = '${app}/role/tawSystemRoleGetAll.do?method=getAllWorkflow',
    treeAction_getUsers = 'tawSystemSubRoles.do?method=xGetUsers&id=',
    treeAction_saveUsers='tawSystemSubRoles.do?method=xUpdateUsers',
    crtSubRolesAction='tawSystemSubRoles.do?method=xsave';

var gridAction='tawSystemRoles.do?method=xGetSubRoleList',
    gridAction_getUsers='tawSystemSubRoles.do?method=xGetUsers&id=',
    gridAction_saveUsers='tawSystemSubRoles.do?method=xUpdateUsers',
    gridAction_del='tawSystemSubRoles.do?method=xDelete&subRoleIds=',
    gridAction_setName='tawSystemSubRoles.do?method=xSetName';

var userTreeAction='${app}/xtree.do?method=userFromDept';

var roleType_subRole = 1, roleType_role = 2;
var form, pageSize=15;

var gridColumns = ['id','subRoleName','deptId','deptName'];
var columnModel = new Ext.grid.ColumnModel([
	{id:'name',header: "项目名称(双击查看人员)",dataIndex: 'subRoleName'},
	{header: "所属部门",dataIndex: 'deptName'}
]);

var subRolesPrestore,subRoleGrid;

Ext.onReady(function(){
	form = new Ext.form.Form({labelWidth: 50, labelAlign: 'top', buttonAlign: 'left'});
    form.add(
        new Ext.form.TextField({
            fieldLabel: '角色名称',
            id:'roleName',
            name: 'roleName',
            allowBlank:false,
            width:150
        }),
        // Hidden Fields
        new Ext.form.HiddenField({name: 'roleTypeId',id:'roleTypeId'}),
        new Ext.form.HiddenField({name: 'workflowFlag',id:'workflowFlag'}),
        new Ext.form.HiddenField({name: 'leaf',id:'leaf',value:'1'}),
        new Ext.form.HiddenField({name: 'deleted',id:'deleted',value:'0'}),
        new Ext.form.HiddenField({name: 'roleId',id:'roleId'}),
        new Ext.form.HiddenField({name: 'parentId',id:'parentId'})
    );  // end of form
    
	
	new xbox({
		btnId:'depttree',
		treeDataUrl:'${app}/xtree.do?method=dept',treeRootId:'-1',treeRootText:'部门',treeChkMode:'',treeChkType:'dept',
		showChkFldId:'deptname',saveChkFldId:'deptid',returnJSON:true
	});
	
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/xtree.do?method=area',treeRootId:'-1',treeRootText:'地域',treeChkMode:'',treeChkType:'area',
		showChkFldId:'areaname',saveChkFldId:'areaid',returnJSON:true
	});
	
	//Role User Viewer
    roleUserViewer = new Ext.JsonView("role-user-list",
		'<div class="viewlistitem-user">{text}{teamLeader}</div>',
		{ 
    		singleSelect: true,
    		sortInfo:['grouptype','desc'],
			emptyText : '<div>此角色下没有分配用户。</div>'
		}
	);
	roleUserViewer.jsonData = [];
	roleUserViewer.on('load',function(){
		Ext.each(this.jsonData,function(obj){
			obj.nodeType = 'user';
		});
		roleUserTree.data = this.jsonData;
		roleUserTree.gridData.loadData(this.jsonData,false);
		roleUserTree.trees[0].root.reload();
		
	},roleUserViewer);
	//Role User Tree
	roleUserTree = new xbox({
		btnId:'role-userTreeBtn',
		treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'用户',treeChkMode:'',treeChkType:'user',viewer:roleUserViewer,
		panelsConfig:[{type:'query',pos:'first'}],
		callback:function(json,ids){
			Ext.Ajax.request({
	    		method:"post",
	    		url:gridAction_saveUsers,
	    		success: function(){roleUserViewer.load(gridAction_getUsers+selectNode.attributes.id);},
				params : "id="+selectNode.attributes.id+"&roleType="+roleType_role+"&userId="+ids
	    	});
		}
	});
	
	//SubRole User Tree
    userViewer = new Ext.JsonView("user-list",
    	'<div class="viewlistitem-{iconCls}">{text}{teamLeader}</div>',
    	{ 
    		singleSelect: true,
    		sortInfo:['grouptype','desc'],
    		emptyText : '<div>此角色下没有分配用户。</div>'
    	}
	);
	//修改树图与viewer未正确关联的问题。
	userViewer.jsonData = [];
	userViewer.on('load',function(){
		Ext.each(this.jsonData,function(obj){
			obj.nodeType = 'user';
		});
		subRoleUserTree.data = this.jsonData;
		subRoleUserTree.gridData.loadData(this.jsonData,false);
		subRoleUserTree.trees[0].root.reload();
		
	},userViewer);
	
	subRoleUserTree = new xbox({
		btnId:'userTreeBtn',
		treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'用户',treeChkMode:'',treeChkType:'user',viewer:userViewer,
		panelsConfig:[{type:'query',pos:'first'}],
		callback:function(json,ids){
			Ext.Ajax.request({    		
	    		method:"post",
	    		url:gridAction_saveUsers,
	    		success: function(){userViewer.load(gridAction_getUsers+$V('subRoleId'));},
	    		params : "id="+$V('subRoleId')+"&userId="+ids
	    	});
		}
	});
	
	subRolesPrestore = new Ext.data.JsonStore({
	    	fields: ['area','dept','class1','class2','class3','class4','class5','class6','class7','class8','deptName','subRoleName']
		});
	var subRolesPreCM = new Ext.grid.ColumnModel([
			{id:'subrolename',header: "名称",dataIndex: 'subRoleName',sortable: true}
		]);
	subRoleGrid = new Ext.grid.Grid('subpregrid', {
            ds: subRolesPrestore,
            cm: subRolesPreCM,
			autoExpandColumn:'subrolename'
        });
	subRoleGrid.on('keypress',function(e){
		if(e.getKey() == e.DELETE){
			var rows = subRoleGrid.getSelectionModel().getSelections();
			Ext.each(rows,function(r){
				subRoleGrid.dataSource.remove(r);
			});	
		}
        	
	});

	subRoleGrid.render();		
	
}); // end of Ext.onReady

</script>

<script type="text/javascript" src="${app}/scripts/layout/role.js"></script>

<div id="headerPanel" class="x-layout-inactive-content">
	<img src="${app}/styles/${theme}/images/header-role.gif">
</div>

<div id="rolePanel" class="x-layout-inactive-content panel">
	<div id="rolePanel-form"></div>
	<div id="rolePanel-users" class="viewer-box">
		<div id="rolePanel-users-title" class="viewlistitem-role" style="display:inline;padding-top:0"></div> 的人员列表:
		<div id="role-user-list" class="viewer-list" style="margin:15px"></div>
	  	<input type="button" value="更改人员列表" id="role-userTreeBtn" class="btn"/>
	</div>
	
</div>

<div id="treePanel">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>

<div id="helpPanel" class="x-layout-inactive-content panel">
	<dl>
		<dt>功能说明</dt>
		<dd>相关操作集合称为角色，为了防止管理分配混乱，对角色的管理只有超级管理员有权限。可对角色进行增删改查等操作。</dd>
	</dl>
	<br/>
	<dl>
		<dt>添加一个下级角色</dt>
		<dd>在树图中的角色上点击右键，并选择"新建子节点"</dd>
		<dt>修改一个角色的信息</dt>
		<dd>在树图中的角色上点击右键，并选择"修改"</dd>
		<dt>删除角色</dt>
		<dd>在树图中的角色上点击右键，并选择"删除"</dd>
		<dt>查看角色的子角色</dt>
		<dd>在树图中的角色上点击右键，并选择"子角色列表"</dd>
		<dt>批量添加子角色</dt>
		<dd>在树图中的角色上点击右键，并选择"添加子角色"</dd>
		<dt>给角色添加人员</dt>
		<dd>如果是大角色，在树图中的角色上点击右键，并选择"修改"，如果是子角色，打开子角色列表，双击你要添加人员的子角色</dd>
	</dl>
</div>

<div id="gridPanel" class="x-layout-inactive-content panel">
	<div id="subRoleList" class="gridPanel-grid"></div>
	<input type="button" value="修改项目名称" class="button" onclick="javascript:subrole_chgName();">
	<input type="button" value="删除选中项目" id="delSubRoleBtn" 
		class="btn" onclick="javascript:delSubRole();">
	
	<div id="subRoleUsers" class="viewer-box">
	  <input type="hidden" id="subRoleId" name="subRoleId">
	  <div id="user-subrolename" class="viewlistitem-role" style="display:inline;padding-top:0"></div> 的人员列表:
	  <div id="user-list" class="viewer-list" style="margin:15px"></div>
	  <input type="button" value="更改人员列表" id="userTreeBtn" class="btn">  
	  <input type="button" value="设置组长" id="setLeaderBtn" class="btn" onclick="javascript:setTeamLeader();"> 
	</div>
</div>

<div id="crtSubRolesPanel" class="x-layout-inactive-content panel">
	<form id="subRolesForm" name="subRolesForm">
		<input type="hidden" name="roleId" id="subRolesForm-roleId"/>
		<input type="hidden" name="roleName" id="subRolesForm-roleName"/>
		<input type="hidden" name="subRoles" id="subRoles"/>

	<table width="100%" cellspacing="10" border="0">
		<tr>
			<td class="preHelp">
			<div class="preHeader">1. 选择区分度:</div>
			
			<div id="distinction0-group" style="margin:10px 0">
				<label>名称:</label><br/>
				<input type="text" class="txt" id="group-name">
			</div>
			
			<div id="distinctionWorkFlow" style="margin:10px 0" >
				<input type="button" name="areatree" id="areatree" value="选择地域" class="btn"/><br/>
				<textarea class="textarea" readonly="readonly" name="areaname" id="areaname"></textarea>					
				<input type="hidden" name="areaid" id="areaid"/>	
			</div>
			
			<div id="distinctionSystem" style="margin:10px 0" >
				<input type="button" name="depttree" id="depttree" value="选择部门" class="btn"/><br/>
				<textarea class="textarea" readonly="readonly" name="deptname" id="deptname"></textarea>					
				<input type="hidden" name="deptid" id="deptid"/>			
			</div>
			
			<div id="workflow-filters"></div>

		
		</td>
		<td class="preHelp">
			<div class="preHeader">2. <span id="preHeader">生成子角色:</span></div>
			<input type="button" value="生成预览" onclick="javascript:pregrid();" class="btn"/>
			
			<div id="subpregrid" style="margin:10px 0;width:450px"></div>
			
			<div id="subpregrid-btns" style="display:none">
			  <input type="button" value="提交" onclick="javascript:crtSubRoles();" class="btn"/>
			  <input type="button" value="重置预览" onclick="javascript:resetPreGrid();" class="btn"/>
			</div>
			<!--
			<input type="button" style="display:none" value="删除选中的记录" id="delBtn" onclick="javascript:delPreRoles();" class="btn"/>
			-->
		</td>
		</tr>
	</table>
	</form>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
