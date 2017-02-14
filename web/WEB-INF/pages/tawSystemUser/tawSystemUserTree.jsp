<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree.js"></script>
<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree_search.js"></script>

<script type="text/javascript">
	var config = {
		canViewNodeType : "user",
		hideSearchPanel : false,
		/**************
		* Tree Configs
		**************/
		treeGetNodeUrl:"tawSystemUsers.do?method=getNodesNoPartner",
		treeRootId:'-1',
		treeRootText:"所有部门",
		ctxMenu:{
			newNode:{text:"新建人员"},
			delNode:{text:"删除此人员"}
		},//end of ctxMenu
		
		/**************
		* Form Configs
		**************/
		actions:{
			newNode : {
				btnText:"保存",
				url:"tawSystemUsers.do?method=xsave",
				init:function(){
					AppSimpleTree.setField("deptid","id");
					AppSimpleTree.setField("deptname","text");
				}
			},
			getNode : {
				url:"tawSystemUsers.do?method=xget"
			},
			edtNode : {
				btnText:"保存修改",
				url:"tawSystemUsers.do?method=xedit"
			}, 
			delNode : {
				url:"tawSystemUsers.do?method=xdelete"
			}
		},
		fieldOptions : {
			width:150
		},
		fields : [
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.username'/>",
	            name: 'username',
	            allowBlank:false
	        }),
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.userid'/>",
	            name: 'userid',
	            id:'userid',
	            allowBlank:false,
	            validationEvent:'blur',
	            vtype:'unique'
	        }),
	         new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.passwd'/>",
				inputType:'password',
	            name: 'newPassword',
	            inputType:'password',
	            allowBlank:true
	        }),
	        new Ext.form.TextField({
	            fieldLabel: "所属部门",
	            name: 'deptname',
	            id:'deptname',
	            readOnly:'true',
	            allowBlank:false
	        }),
	          new Ext.form.SimpleSelect({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.accountLocked'/>",
	            hiddenName:'accountLocked',
	            values : [
	        				['true', 'true'],
	        				['false', 'false']
	        			   ],
	            allowBlank:false,
	            value:'false'
	        }),
	         new Ext.form.SimpleSelect({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.enabled'/>",
	            hiddenName:'enabled',
	            values : [
	        				['true', 'true'],
	        				['false', 'false']
	        			   ],
	            allowBlank:false,
	            value:'true'
	        }),
	        /**new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.rolename'/>",
	            name: 'portalrolename',
	            allowBlank:true
	        }),**/
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.cptroomname'/>",
	            id:'cptroomname',
	            name: 'cptroomname',
	            readOnly:'true'
	        }),   
			    
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.email'/>",
	            name: 'email',
	            vtype:'email'
	        }),
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.familyaddress'/>",
	            name: 'familyaddress'
	        }),
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.fax'/>",
	            name: 'fax',
	            vtype:'number'
	        }),
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.mobile'/>",
	            name: 'mobile'
	        }),
	        new Ext.form.TextField({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.phone'/>",
	            name: 'phone'
	        }),	      
	        new Ext.form.SimpleSelect({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.sex'/>",
	            hiddenName:'sex',
	            values : [
	        				['男', '1'],
	        				['女', '0']
	        			   ],
	            allowBlank:false,
	            value:'1'
	        }),
	         new Ext.form.SimpleSelect({
            fieldLabel: "<fmt:message key='tawSystemUserForm.userdegree'/>",
            hiddenName:'userdegree',
            values : [
        				['是', '1'],
        				['否', '0']
        			   ],
            allowBlank:false,
            value:'0',
            width:150
        }),
        new Ext.form.SimpleSelect({
            fieldLabel: "<fmt:message key='tawSystemUserForm.isfullemploy'/>",
            hiddenName:'isfullemploy',
            values : [
        				['是', '1'],
        				['否', '0']
        			   ],
            allowBlank:false,
            value:'1',
            width:150
        }),
        
        new Ext.form.SimpleSelect({
            fieldLabel: "<fmt:message key='tawSystemUserForm.isrest'/>",
            hiddenName:'isrest',
            values : [
        				['是', '1'],
        				['否', '0']
        			   ],
            allowBlank:false,
            value:'1',
            width:150
        }),
	        new Ext.form.TextArea({
	            fieldLabel: "<fmt:message key='tawSystemUserForm.remark'/>",
	            name: 'remark',
	            grow: true,
	            preventScrollbars:true
	        }),
	        /* Hidden Field
	        */
	      	new Ext.form.HiddenField({name: 'id'}),
	      	new Ext.form.HiddenField({name: 'olduserid'}),
	      	new Ext.form.HiddenField({name: 'password'}),
	        new Ext.form.HiddenField({name: 'updatetime'}),
	        new Ext.form.HiddenField({name: 'savetime'}),
	        new Ext.form.HiddenField({name: 'deleted'}),
	        new Ext.form.HiddenField({name: 'operremotip'}),
	        new Ext.form.HiddenField({name: 'operuserid'}),
	        new Ext.form.HiddenField({name: 'deptid',id:'deptid'}),
	        //new Ext.form.HiddenField({name: 'enabled'}),
	        new Ext.form.HiddenField({name: 'cptroomid',id:'cptroomid'})
		], // end of fields
		
		/************************
		* Custom onLoad Functions
		*************************/	
		onLoadFunctions:function(){
			var treeAction2='${app}/xtree.do?method=getCptroomTree';
		    var deptAction = '${app}/xtree.do?method=dept';
		    var userTree = '${app}/xtree.do?method=userFromDept';
		    var subRoleTree = '${app}/role/tawSystemSubRoles.do?method=xgetAllRolesAndSubRoles'
			new xbox({
				btnId:'cptroomname',dlgTitle:'请选择该人员所属机房',
				treeDataUrl:treeAction2,treeRootId:'-1',treeRootText:'所有机房',treeChkMode:'',treeChkType:'cptroom',
				showChkFldId:'cptroomname',saveChkFldId:'cptroomid'
			});
			new xbox({
				btnId:'deptname',dlgTitle:'请选择该人员所属部门',
				treeDataUrl:deptAction,treeRootId:'-1',treeRootText:'所有部门',treeChkMode:'single',treeChkType:'dept',
				showChkFldId:'deptname',saveChkFldId:'deptid'
			});
			// subrole moveto user select tree
			new xbox({
				btnId:'selMoveTgt',dlgTitle:'请选择移交人员',
				treeDataUrl:userTree,treeRootId:'-1',treeRootText:'所有人员',treeChkMode:'',treeChkType:'user',
				showChkFldId:'moveToName',saveChkFldId:'userId'
			});
			// adjust subrole select tree
			new xbox({
				btnId:'selSubRole',dlgTitle:'请选子角色',
				treeDataUrl:subRoleTree,treeRootId:'-1',treeRootText:'所有角色',treeChkMode:'',treeChkType:'subrole',
				showChkFldId:'subRoles',saveChkFldId:'subRoleId'
			});
			 
		}
	}; // end of config


	
	//subroleList dialog	
	var dlg;	
	var t = new Ext.Template(
    	'<tr>',
        	'<td class=\"icon\"><input type=\"checkbox\" checked=\"true\" class=\"checkbox\" name=\"subRoleIdArray\" value=\"{id}\"></td>',
        	'<td>{text}</td>',
    	'</tr>'
	);
		
	function subroleList(nodeData){
		$('moveToName').update();
		$('userId').update();
		var userId = nodeData.id;
		var table = $('subroleList-table');
		while(table.rows.length>0)
    	{
        	table.deleteRow(0);
    	} 
		Ext.Ajax.request({
			method:'post',
			url: 'tawSystemUserRefRoles.do?method=xgetSubRolsByUser&id='+userId,
			success: function(x){
				var data = eoms.JSONDecode(x.responseText);
				Ext.each(data,function(d){
					t.append('subroleList-table',d);
				});
				eoms.util.colorRows('subroleList-table',0);
				dlg.show();
			}
        });		
	}
	
	// adjustSubRole dialog
	var dlg_adjustSubRole;	
	var template = new Ext.Template(
    	'<tr>',
        	'<td class=\"icon\"><input type=\"checkbox\" checked=\"true\" class=\"checkbox\" name=\"subRoleIds\" value=\"{id}\"></td>',
        	'<td>{text}</td>',
    	'</tr>'
	);
	function adjustSubRole(nodeData){
		$('subRoles').update();
		$('subRoleId').update();
		var userId = nodeData.id;
		var table = $('adjustSubRole-table');
		while(table.rows.length>0)
    	{
        	table.deleteRow(0);
    	} 
		Ext.Ajax.request({
			method:'post',
			url: 'tawSystemUserRefRoles.do?method=xgetSubRolsByUser&id='+userId,
			success: function(x){
				var data = eoms.JSONDecode(x.responseText);
				Ext.each(data,function(d){
					template.append('adjustSubRole-table',d);
				});
				eoms.util.colorRows('adjustSubRole-table',0);
				dlg_adjustSubRole.show();
			}
        });		
	}

	
	//search
	var searcher;
	var searchConfig = {
		url:'tawSystemUsers.do?method=xsearchNoPartner',
		paramMapping : {username:'sUserName',post:'sDeptName',userid:'sUserId'},
		baseAttr : {nodeType:'user'},
		fields : ['id',{name: 'name', mapping: 'username'},{name: 'text', mapping: 'username'},'deptname','mobile','phone','post','allowEdit','allowChild','allowDelete','allowsubroleList-mi','allowadjustsubrole-mi'],
		cm : [
				{header : "用户名", dataIndex : 'name'},
				{header : "所属部门",	dataIndex : 'deptname'},
				{header : "手机", dataIndex : 'mobile'},
				{header : "电话", dataIndex : 'phone'}
			]
		};
		
	Ext.onReady(function(){
		AppSimpleTree.prepareCtx = function(node, e){
	        node.select();
	        this.selected = node.attributes;
	        this.selectedNode = node;
			if(node.isRoot) {
				return;
			}
	        var mis = this.treeCtxMenu.items;
	        var mis_hidden = true;
	        mis.get('newnode')[node.attributes.allowChild ? 'show' : 'hide']();
	        mis.get('delnode')[node.attributes.allowDelete ? 'show' : 'hide']();
	        mis.get('edtnode')[(node.isRoot || node.attributes.allowEdit===false) ? 'hide' : 'show']();
	        
	        var isPartnerUser = this.selectedNode.attributes.isPartnerUser;
	    	//if(isPartnerUser===true) {
	    		mis.get('edtnode').setText("查看");
	    	//}
	        mis.each(function(mi){
	            var idfix = (mi.id == "newnode"
	                ? "Child"
	                : (mi.id == "edtnode"
	                        ? "Edit"
	                        : (mi.id == "delnode"
	                                ? "Delete"
	                                : mi.id)));
	            var allow = node.attributes['allow' + idfix];
	            if(mi.nodeTypeMapping){
	                mi[mi.nodeTypeMapping == node.attributes.nodeType ? 'show' : 'hide']();
	            }
	            if(idfix!='Child'&&idfix!='Edit'&&idfix!='Delete')mi[allow ? 'show' : 'hide']();
	            if(!mi.hidden)
	                mis_hidden = false;
	        });
	        if(!mis_hidden){
	            this.treeCtxMenu.showAt(e.getXY());
	        }  
	    }
	
		/** 执行新建节点动作 */
	    AppSimpleTree.doNewNode = function(nodeData){
	    	this.selected = this.selectedNode.attributes;
	    	var isPartnerDept = this.selected.isPartnerDept;
	    	var isPartnerUser = this.selected.isPartnerUser;
	    	
	    	if(isPartnerDept===true) {
	    		Ext.MessageBox.alert('提示', "合作伙伴部门下不能新建人员!");
		    	this.initRegion("east","helpPanel");
		    	return;
	    	}
	    	
	        this.openPanel('formPanel','在'+this.selected.text+' 下新建');
	        this.form.url = this.actions.newNode.url;
	        this.sbmBtn.setText(this.actions.newNode.btnText);
	        this.sbmBtn.show();
	        try{this.actions.newNode.init();}catch(e){};
	        this.savedFormData = null;
	        this.form.items.each(function(f){
	            f.enable();
	        });
	    }
	    /** 执行修改节点动作(打开表单修改面板)
	     * @param {Object} nodeData
	     */
	    AppSimpleTree.doEdtNode = function(nodeData){
	    	this.selected = this.selectedNode.attributes;
	    	var isPartnerUser = this.selected.isPartnerUser;
	    	
	   //   if(isPartnerUser===true) {
		 //      this.openPanel('formPanel','查看'+nodeData.text+'的信息');
		   //    this.form.url = this.actions.edtNode.url;
		     //  this.sbmBtn.setText(this.actions.edtNode.btnText);
		       //this.sbmBtn.hide();
	    	//} else {
		      // this.openPanel('formPanel','修改'+nodeData.text+'的信息');
		        //this.form.url = this.actions.edtNode.url;
		        //this.sbmBtn.setText(this.actions.edtNode.btnText);
		        //this.sbmBtn.show();
	    	//}
	    	
	    	  this.openPanel('formPanel','查看'+nodeData.text+'的信息');
		       this.form.url = this.actions.edtNode.url;
		       this.sbmBtn.setText(this.actions.edtNode.btnText);
		       this.sbmBtn.hide();
	    	
	        try{this.actions.edtNode.init();}catch(e){};
	        this.formData.load({params:{id:nodeData.id}});
	        this.form.items.each(function(f){
	            f.enable();
	        });
	    }
	
		/** 执行删除节点动作
	     * @param {Object} nodeData
	     * @param {Node} node
	     */
	    AppSimpleTree.doDelNode = function(nodeData){
	    	this.selected = this.selectedNode.attributes;
	    	var isPartnerUser = this.selected.isPartnerUser;
	    	
	    	if(isPartnerUser===true) {
           		Ext.MessageBox.alert('提示',"合作伙伴人员无法被删除!");
           		return;
	    	}
	    
	        var params = "id="+nodeData.id;
	        
	        if(typeof this.actions.delNode.customData == "function"){
	            params = this.actions.delNode.customData(nodeData);
	        }
	        
	        var myId = nodeData.id;
	            	if(myId.indexOf('2')==0){
	            		Ext.MessageBox.alert('提示',"合作伙伴下的部门无法被删除");
	            		return false;
	         }
	        
	        //弹出一个确认框
	        Ext.MessageBox.confirm('确认:', '您确定删除这个项目吗', 
	            function(btn){
	                if(btn=="yes"){
	                    Ext.Ajax.request({
	                        method : 'post',
	                        url : this.actions.delNode.url,
	                        success: function(resp){
	                            if(node){
	                                this.treesm.selectPrevious();
	                                node.parentNode.removeChild(node);
	                            }
	                            this.initRegion('east','helpPanel');
	                            this.reloadStatus.deleted = true;
	                            Ext.MessageBox.alert('提示',"您成功删除了一个节点。");
	                        },
	                        failure: function() {
	                            Ext.MessageBox.alert('提示',"删除失败");
	                        },
	                        scope:this,
	                        params:params
	                    });                 
	                }
	            },this);
	    }
	
	    AppSimpleTree.on('treebeforeload',function(treeLoader,node,callback){
			treeLoader.baseParams['hasRight'] = node.attributes['hasRight'] || '0';
		});
		AppSimpleTree.init();
		
        // custom menuitem
	    AppSimpleTree.addmenu('user',{
		    id:'subroleList-mi', text:'复制子角色',cls:'edit-mi',
		    handler:function(){
			    subroleList(AppSimpleTree.selected);
		    }
	    });
	    AppSimpleTree.addmenu('user',{
		    id:'adjustsubrole-mi', text:'调整子角色',cls:'edit-mi',
		    handler:function(){
		    	adjustSubRole(AppSimpleTree.selected);
		    }
		});
	    
		searcher = new AppSearch();
		searcher.init(searchConfig);
		
		//subroleList dialog
		dlg = new Ext.BasicDialog("subroleList-dlg", {
    		height: 350,
    		width: 400,
    		minHeight: 300,
    		minWidth: 300,
    		autoTabs:true,
    		modal: false,
    		proxyDrag: false,
    		shadow: false
		});
		dlg.addKeyListener(27, dlg.hide, dlg);
		dlg.addButton('执行复制', function(){
			doCopySubRoles('subroleList-form');
		});
		dlg.addButton('关闭', dlg.hide, dlg);
		
		// adjustSubRole dialog
		dlg_adjustSubRole = new Ext.BasicDialog("adjustSubRole-dlg", {
    		height: 350,
    		width: 400,
    		minHeight: 300,
    		minWidth: 300,
    		autoTabs:true,
    		modal: false,
    		proxyDrag: false,
    		shadow: false
		});
		dlg_adjustSubRole.addKeyListener(27, dlg_adjustSubRole.hide, dlg_adjustSubRole);
		dlg_adjustSubRole.addButton('保存调整', function(){
			doAdjustSubRoles('adjustSubRole-form');
		});
		dlg_adjustSubRole.addButton('关闭', dlg_adjustSubRole.hide, dlg_adjustSubRole);
		
		// subrole viewer
		subRoleViewer = new Ext.JsonView("subRoles",
		'<div id="role-user-{id}" class="viewlistitem-user">{name}</div>',
		{ 
			multiSelect: true,
			emptyText : '<div>${eoms:a2u("尚未选择任何子角色。")}</div>'
		}
		);
		var s = '[]';
		subRoleViewer.jsonData = eoms.JSONDecode(s);
		subRoleViewer.refresh();

	});
	
	function doSearch(){
		
		searcher.load();
	}
	function doCopySubRoles(form){
		Ext.Ajax.request({
			method:'post',
			url: 'tawSystemUserRefRoles.do?method=xsaveUserRefRole',
			success: function(x){
				eoms.log(x.responseText);
				var rt = eoms.JSONDecode(x.responseText);
				Ext.MessageBox.alert('提示', rt.message,dlg.hide,dlg);
			},
			params: Ext.Ajax.serializeForm(form)
        });		
	}
	function doAdjustSubRoles(form){
		Ext.Ajax.request({
			method:'post',
			url: 'tawSystemUserRefRoles.do?method=xsaveAdjustSubRoles&id=' + AppSimpleTree.selected.id,
			success: function(x){
				eoms.log(x.responseText);
				var rt = eoms.JSONDecode(x.responseText);
				Ext.MessageBox.alert('提示', rt.message);
				// a judgement needed at this place for output rt.message when fail
				// refresh dialog after save
				adjustSubRole(AppSimpleTree.selected);
			},
			params: Ext.Ajax.serializeForm(form)
        });		
	}
	

	
</script>



<div id="headerPanel" class="app-header">
	<img src="${app}/styles/default/images/header-user.gif" align="left">
	<div style="padding-top:6px">
	<form onsubmit="javascript:doSearch();return false;">
	查询用户名: <input type="text" name="username" id="sUserName" class="text">
	查询部门: <input type="text" name="post" id="sDeptName" class="text">
	查询ID：<input type="text" name="userid" id="sUserId" class="text">
	<input type="button" value="查询" class="button" onclick="javascript:doSearch();" style="vertical-align:middle">
	</form>
	</div>
</div>
<div id="helpPanel" class="app-panel">
	<dl>
		<dt>功能说明</dt>
		<dd>管理用户的基本信息，以及配置该用户所属部门、机房等；同时可分配或修改用户所拥有的权限。超级管理员和二级管理员有权限对用户管理操作。</dd>
		<dt>在部门下添加一个人员</dt>
		<dd>在树图中的部门上点击右键，并选择"新建人员"</dd>
		<dt style="color:red">查看人员信息</dt>
		<dd>在人员节点上双击，可查看人员信息</dd>
		<dt>修改一个人员的信息</dt>
		<dd>在树图中的人员上点击右键，并选择"修改"</dd>
		<dt>删除人员</dt>
		<dd>在树图中的人员上点击右键，并选择"删除此人员"</dd>
		<dt style="color:red">权限</dt>
		<dd>		
			<div class="viewer-list">
			  <div class="viewlistitem-dept">表示对部门下的人员有管理权限;</div>
			  <div class="viewlistitem-dept-readonly">表示对部门下的人员仅有查看权限</div>			  
			</div>
		</dd>
	</dl>
</div>

<div id="searchPanel"></div>
<div id="searchGrid"></div>

<div id="treePanel">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>

<div id="formPanel">
	<div id="formPanel-body" class="app-panel"></div>
</div>

<div id="subroleList-dlg" style="visibility:hidden;">
    <div class="x-dlg-hd">将子角色复制给其他人员</div>
    <div class="x-dlg-bd">
        <div class="x-dlg-tab" title="所属子角色列表">
            <div class="inner-tab">
            	<form id="subroleList-form" onsubmit="javascript:doCopySubRoles(this);return false;">
            	<br/>
            	复制给 : <input type="text" name="moveToName" class="text" id="moveToName" readonly/>
        		<input type="hidden" name="userId" id="userId"/>
        		<input type="button" class="button" id="selMoveTgt" value="选择目标人员"/><br/><br/>
            	  <table class="listTable" id="subroleList-table">
            	    <tbody></tbody>
            	  </table>
            	</form>
            </div>
        </div>

    </div>
</div>

<div id="adjustSubRole-dlg" style="visibility:hidden;">
    <div class="x-dlg-hd">调整人员所属子角色</div>
    <div class="x-dlg-bd">
        <div class="x-dlg-tab" title="所属子角色列表">
            <div class="inner-tab">
            	<form id="adjustSubRole-form" onsubmit="javascript:doAdjustSubRoles(this);return false;">
            	<br/>
            	需要添加的子角色列表 : <div id="subRoles" class="viewer-list"></div>
        		<input type="hidden" name="subRoleId" id="subRoleId"/>
        		<input type="button" class="button" id="selSubRole" value="选择子角色"/><br/><br/>
            	  <table class="listTable" id="adjustSubRole-table">
            	    <tbody></tbody>
            	  </table>
            	</form>
            </div>
        </div>

    </div>
</div>


<%@ include file="/common/footer_eoms.jsp"%>
