<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree.js"></script>
<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree_search.js"></script>
<%
com.boco.eoms.commons.system.session.form.TawSystemSessionForm sessionForm = (com.boco.eoms.commons.system.session.form.TawSystemSessionForm) request.getSession().getAttribute("sessionform");
String rootPnrDeptId = sessionForm.getRootPnrDeptId();
%>
<script type="text/javascript">
	var config = {
		canViewNodeType : "dept",
		hideSearchPanel : false,
			
		/**************
		* Tree Configs
		**************/
		treeGetNodeUrl:"tawSystemDepts.do?method=getNodes2",
		treeRootId:'-1',
		treeRootText:"所有部门",
		ctxMenu:{
			newNode:{text:"新建下级部门"},
			delNode:{text:"删除这个部门"}
		},//end of ctxMenu
		
		/**************
		* Form Configs
		**************/
		actions:{
			newNode : {
				btnText:"保存",
				url:"tawSystemDepts.do?method=xsave",
				init:function(){
					AppSimpleTree.setField("parentDeptid","id");
					AppSimpleTree.setField("parentDeptName","text");
				}
			},
			getNode : {
				url:"tawSystemDepts.do?method=xget"
			},
			edtNode : {
				btnText:"保存修改",
				url:"tawSystemDepts.do?method=xedit",
				success : function(){
					AppSimpleTree.refreshTree();
				}
			},
			delNode : {
				url:"tawSystemDepts.do?method=xdelete",
				customData:function(selNode){
					return "id="+selNode.id+"&parentDeptid="+selNode.parentDeptid;
				}
			}
		},
		fieldOptions : { width:150 },
		fields : [
		    new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.deptName'/>",
		        name: 'deptName',
		        allowBlank:false
		    }),
		    new Ext.form.TextField({
		        fieldLabel: "父部门",
		        name: 'parentDeptName',
		        id: 'parentDeptName',
	            readOnly:'true'
		    }),
		     new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.deptmanager'/>",
		        name: 'deptmanager', id: 'deptmanager',
		        readOnly:'true'
		    }),
		       new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.tmporaryManager'/>",
		        name: 'tmporaryManager',id: 'tmporaryManager',
		        readOnly:'true'
		    }),
		     <%--
		     new Ext.form.DateField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.tmporarybegintime'/>",
		        name: 'tmporarybegintime',
		        format:'Y-m-d'
		    }),
		    
		     new Ext.form.DateField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.tmporarystopTime'/>",
		        name: 'tmporarystopTime',
		        format:'Y-m-d'
		    }),--%>
		     new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.deptemail'/>",
		        name: 'deptemail',
		        vtype:'email'
		    }),
		        new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.deptfax'/>",
		        name: 'deptfax',
		        vtype:'number'
		    }),
		     new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.deptmobile'/>",
		        name: 'deptmobile',
		        vtype:'number'
		    }),
		     new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.deptphone'/>",
		        name: 'deptphone',
		        vtype:'number'
		    }),

		  new Ext.form.TextField({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.regionflag'/>",
		        name: 'areaid',id:'areaid',
		        readOnly:'true'
		        //allowBlank:false
		    }),
		    new Ext.form.TextArea({
		        fieldLabel: "<fmt:message key='tawSystemDeptForm.remark'/>",
		        id: 'remark',
		        name: 'remark',
		        grow: true,
		        preventScrollbars:true
		    }),
		    /* Hidden Field
		    */
		    new Ext.form.HiddenField({name: 'id'}),
		    new Ext.form.HiddenField({name: 'deptId'}),
		    new Ext.form.HiddenField({name: 'linkid'}),
		    new Ext.form.HiddenField({name: 'opertime'}),
		    new Ext.form.HiddenField({name: 'operuserid'}),
		    new Ext.form.HiddenField({name: 'parentDeptid',id:'parentDeptid'}),
		    new Ext.form.HiddenField({name: 'ordercode'}),
		    new Ext.form.HiddenField({name: 'leaf'}),
		    new Ext.form.HiddenField({name: 'tempid',id:'tempid'}),
		    new Ext.form.HiddenField({name: 'newAreaId',id:'newAreaId'}),
		    new Ext.form.HiddenField({name: 'tempid2',id:'tempid2'}),
		    new Ext.form.HiddenField({name: 'regionflag',id:'regionflag'}),
		    new Ext.form.HiddenField({name: 'isPartners',id:'isPartners',value:'0'})
		    	    
		], // end of fields
		
		/************************
		* Custom onLoad Functions
		*************************/	
		onLoadFunctions:function(){
			var	treeAction2='${app}/xtree.do?method=userFromDept';
			var treeArea='${app}/xtree.do?method=areaTree';
			var treeDept = '${app}/xtree.do?method=dept';
			new xbox({
				btnId:'deptmanager',
				treeDataUrl:treeAction2,treeRootId:'-1',treeRootText:'所有人员',treeChkMode:'single',treeChkType:'user',
				showChkFldId:'deptmanager',saveChkFldId:'tempid'
			});
			new xbox({
				btnId:'tmporaryManager',
				treeDataUrl:treeAction2,treeRootId:'-1',treeRootText:'所有人员',treeChkMode:'single',treeChkType:'user',
				showChkFldId:'tmporaryManager',saveChkFldId:'tempid2'
			});
			new xbox({
				btnId:'areaid',
				treeDataUrl:treeArea,treeRootId:'-1',treeRootText:'所有地市',treeChkMode:'single',treeChkType:'area',
				showChkFldId:'areaid',saveChkFldId:'newAreaId'
			});
			new xbox({
				btnId:'parentDeptName',dlgTitle:'请选择新的父部门',
				treeDataUrl:treeDept,treeRootId:'-1',treeRootText:'所有部门',treeChkMode:'single',treeChkType:'dept',
				showChkFldId:'parentDeptName',saveChkFldId:'parentDeptid'
			});
		}
	}; // end of config
	//Ext.onReady(AppSimpleTree.init, AppSimpleTree);
	
	//search
	var searcher;
	var	searchConfig = {
		url:'tawSystemDepts.do?method=xsearch',
		paramMapping : {deptName:'sDeptName'},
		baseAttr : {nodeType:'dept'},
		fields : [
			{name: 'id', mapping: 'id'},
			{name: 'text', mapping: 'deptName'},
			{name: 'name', mapping: 'deptName'},
			{name: 'nodeType', value:'dept'},
			'parentDeptid','deptmanager','deptphone','allowEdit','allowChild','allowDelete'
		],
		cm : [
				{header : "部门名称",	dataIndex : 'name'},
				{header : "部门负责人",dataIndex : 'deptmanager'},
				{header : "电话",dataIndex : 'deptphone'}
			]
		};
		
	function doSearch(){
		if($('sDeptName').value.trim()==""){
			alert("请输入搜索关键字");
			return;
		}
		searcher.load();
	}
	
	
	Ext.onReady(function(){
		AppSimpleTree.on('treebeforeload',function(treeLoader,node,callback){
			treeLoader.baseParams['hasRight'] = node.attributes['hasRight'] || '0';
		});
		AppSimpleTree.init();
		
		searcher = new AppSearch();
		searcher.init(searchConfig);
	});	
</script>


<div id="headerPanel" class="app-header">
	<img src="${app}/styles/default/images/header-dept.gif" align="left">
	<div style="padding-top:6px">
	<form onsubmit="javascript:doSearch();return false;">
	查询部门名称: <input type="text" name="deptName" id="sDeptName" class="text">
	<input type="button" value="查询" class="button" onclick="javascript:doSearch();" style="vertical-align:middle">
	</form>
	</div>
</div>
<div id="helpPanel" class="app-panel">
	<dl>
		<dt>功能说明</dt>
		<dd>
			管理公司中的所有部门，呈树状结构，可实现增删改查等功能。只有超级管理员有此操作的权限。
		</dd>
		<dt style="color:red">查看部门信息</dt>
		<dd>在部门节点上双击，可查看部门信息</dd>
		<dt>添加一个下级部门</dt>
		<dd>在树图中的部门上点击右键，并选择"新建下级部门"</dd>
		<dt>修改一个部门的信息</dt>
		<dd>在树图中的部门上点击右键，并选择"修改"</dd>
		<dt>删除部门</dt>
		<dd>在树图中的部门上点击右键，并选择"删除"</dd>
		<dt style="color:red">权限</dt>
		<dd>
			<div class="viewer-list">
			  <div class="viewlistitem-dept">表示有管理权限</div>
			  <div class="viewlistitem-dept-readonly">表示仅有查看权限</div>			  
			</div>
		</dd>
	</dl>
</div>
<div id="treePanel">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel">
	<div id="formPanel-body" class="app-panel"></div>
</div>

<div id="searchPanel"></div>
<div id="searchGrid"></div>
<%@ include file="/common/footer_eoms.jsp"%>
