/** 
 * 角色管理界面
 * @version 0.4 081011 (添加class4)
 * @version 0.5 090820 (class支持自由组合，不必按顺序)
 * @author mios
 */

var selectNode;
var layout;
var userViewer;
var roleUserViewer;
var gridds;
var grid;
var curNodeType = "role";
var oText = {
	"role" : {
		"name" : "子角色",
		"newNodeTitle" : "新建角色",
		"modifyNodeTitle" : "修改角色信息",
		"ctxMenuChildList" : "子角色列表",
		"ctxMenuAddChild" : "添加子角色"
	},
	"group" : {
		"name" : "虚拟组",
		"newNodeTitle" : "新建虚拟组",
		"modifyNodeTitle" : "修改虚拟组信息",
		"ctxMenuChildList" : "虚拟组列表",
		"ctxMenuAddChild" : "添加虚拟组"
	}
};

Ext.onReady(function(){
    var xt = Ext.tree;	
    Ext.QuickTips.init();
    
    var tb = new Ext.Toolbar('treePanel-tb');
    tb.add({
        id:'expand',
        text:'展开',
        handler:expandAll,
        cls:'x-btn-text-icon expand',
        tooltip:'展开所有树图'
    },{
        id:'collapse',
        text:'关闭',
        handler:collapseAll,
        cls:'x-btn-text-icon collapse',
        tooltip:'关闭所有打开的树'
    },{
        id:'refresh',
        text:'刷新',
        handler:refreshTree,
        cls:'x-btn-text-icon refresh',
        tooltip:'刷新整个树图'
    });
    
    layout = new Ext.BorderLayout(document.body, {
        north: {
            initialSize: 50,
            titlebar: false
        },
        west: {
            split:true,
            initialSize: 250,
            minSize: 200,
            maxSize: 300,
            titlebar: false,
            autoScroll:true,
            margins:{left:5,right:0,bottom:5,top:5}
        },
        center: {
            titlebar: false,
            tabPosition:'top',
            margins:{left:0,right:5,bottom:5,top:5}
        }
    });
	
    layout.batchAdd({
       center: {
           id: 'detail-ct',
           autoCreate:true,
           title:'角色信息',
           autoScroll:true,
           margins:{left:15}
           
       },
       north: {
           el: 'headerPanel',
           fitToFrame:true         
       },
       west : {
           el: 'treePanel',
           toolbar: tb
       }
    });
	
	var eastRegion = layout.getRegion('center')
	eastRegion.hide();
	
	var helpPanel = layout.add('center',new Ext.ContentPanel('helpPanel', 
		{id:'helpPanel',title: '帮助',autoScroll:true, fitToFrame:true}));
		
	var gridPanel = layout.add('center', new Ext.ContentPanel('gridPanel', 
		{id:'gridPanel',title: '子角色列表',autoScroll:true, fitToFrame:true}));

    gridPanel.on('activate', function() {
		gridds.load({params:{id:selectNode.id,start:0, limit:pageSize}});
    });
	var rolePanel = layout.add('center',new Ext.ContentPanel('rolePanel',
		{id:'rolePanel',title: '新建角色',autoScroll:true, fitToFrame:true}));
		
	var crtSubRolesPanel = layout.add('center', new Ext.ContentPanel('crtSubRolesPanel', 
		{id:'crtSubRolesPanel',title: '批量生成子角色', autoScroll:true,fitToFrame:true}));
	           
    var formEl = Ext.get("rolePanel-form");
	
	initRegion("center",helpPanel);
	
	var loading = Ext.get('loading');
	loading.fadeOut({duration:.2,remove:true});
	
	/**
	 * 定义表单按钮
	 */
    var newNodeBtn = form.addButton({text:'保存子节点',hidden:true}, function(){
    	if(!form.isValid())	{
    		return;
    	}
		
    	var data = Ext.lib.Ajax.serializeForm(form.el.dom);
    	layout.el.mask('保存数据中，请稍候。', 'x-mask-loading');
        var unmask = layout.el.unmask.createDelegate(layout.el);
    	Ext.lib.Ajax.request(
    		"post",
    		treeAction_new,
    		{success: function(){
    			reloadTree(selectNode);unmask();
    			Ext.MessageBox.alert('提示', "创建成功");openPanel('helpPanel');
    		 },
    		 failure: function(){unmask();Ext.MessageBox.alert('提示', "创建失败")}
    		},
    		data
    	);
    });
    var modifybtn = form.addButton({text:'保存修改',hidden:true}, function(){
    	if(!form.isValid())	{
    		return;
    	}
    	var data = Ext.lib.Ajax.serializeForm(form.el.dom);
    	form.reset();
    	
    	layout.el.mask('保存数据中，请稍候。', 'x-mask-loading');
        var unmask = layout.el.unmask.createDelegate(layout.el);
    	Ext.lib.Ajax.request(
    		"post",
    		treeAction_edt+selectNode.id,
    		{success: function(){
    			reloadTree(selectNode.parentNode);unmask();
    			Ext.MessageBox.alert('提示', "修改成功");openPanel('helpPanel');
    		 },
    		 failure: function(){unmask();Ext.MessageBox.alert('提示', "修改失败")}
    		},
    		data
    	);
    });

    form.render('rolePanel-form');
        
	/**
	 * 树图
	 */
    var mainTree = new xt.TreePanel('treePanel-body', {
        animate:true,
        enableDD:false,
        containerScroll: true,
        lines:true,
        rootVisible:false
    });

	var root = new xt.TreeNode({});
	var sysRoot = new xt.AsyncTreeNode({
        id:treeRootId,
        text:'系统角色',
        nodeType:'root',
        roleTypeId:2,
        workflowFlag:0,
        loader: new Ext.tree.TreeLoader({
            dataUrl:treeAction+'&roleTypeId=2'
        })
    });
        
    var flowNodeLoader = new Ext.tree.TreeLoader({
            dataUrl:wfNodesAction,
            applyLoader : false, //子节点载入不使用默认loader
			baseAttrs : {
				roleTypeId:1,
				loader : new Ext.tree.TreeLoader({
					dataUrl:treeAction+'&roleTypeId=1',
					baseAttrs : {allownewnode:false,allowNewVirtual:false} //流程角色不能新建角色和虚拟组
    			})
			}          
    });
        
    var flwRoot = new xt.AsyncTreeNode({
        id:treeRootId,
        text:'流程角色',
        nodeType:'root',
        noCtx:true, //无右键菜单
        loader: flowNodeLoader
    });
	root.appendChild(sysRoot);
	root.appendChild(flwRoot);
    mainTree.setRootNode(root);
    mainTree.render();
    root.expand();  
       
    //子角色列表
    gridds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: gridAction
        }),

        reader: new Ext.data.JsonReader({
            root: 'rows',
            totalProperty: 'total',
            id: 'id'
        }, gridColumns),
        
        remoteSort: true
    });
    gridds.on('beforeload', function() {
    	Ext.apply(gridds.baseParams,{
            'id': selectNode.id
        });
    });
        
    grid = new Ext.grid.Grid('subRoleList', {
        ds: gridds,
        cm: columnModel,
        autoExpandColumn:'name',
        autoSizeColumns: true,
        trackMouseOver: true,
        loadMask: true
    });
    

	Ext.get('subRoleUsers').hide();
    grid.addListener('rowdblclick', function(){
    	var record = grid.getSelectionModel().getSelected().json;
    	Ext.get('user-subrolename').update(record.subRoleName);
    	Ext.get('subRoleId').dom.value = record.id;
    	userViewer.load(gridAction_getUsers+record.id);
    	Ext.get('subRoleUsers').show();
    	Ext.get('setLeaderBtn').setDisplayed((curNodeType=='group'));
    });
    
    grid.render();
    
    var gridFoot = grid.getView().getFooterPanel(true);

    //分页
    var paging = new Ext.PagingToolbar(gridFoot, gridds, {
        pageSize: pageSize,
        displayInfo: true,
        displayMsg: '显示 {0} - {1}项 共 {2}项',
        emptyMsg: "没有找到记录"
    });
         
    //子角色名称搜索
    var defaultText = "查询子角色名称...";
    var filterInput = Ext.DomHelper.insertBefore('subRoleList', {
		tag:'input', type:'text', cls:'text',value:defaultText,style:'margin:0 5px 5px 0'}, true);
	Ext.DomHelper.insertAfter(filterInput, 
		'<input id="filterBtn" type="button" class="button" value="查询" style="margin:0 5px 5px 0"/><span id="filterInfo"></span>'
	);
	var filter = function(){
		filterInput.focus();
		if(filterInput.dom.value.trim()!="" && filterInput.dom.value != defaultText){
        	gridds.baseParams['q'] = filterInput.dom.value.trim();
			gridds.reload();
		}
	};
	var resetfilter = function(){
		gridds.baseParams['q'] = '';
		filterInput.dom.value = defaultText;
		Ext.get("filterInfo").update('');
	}
	filterInput.on('focus', function(e,dom){if(dom.value==defaultText)dom.value="";});
	filterInput.on('blur', function(e,dom){if(dom.value=="")dom.value=defaultText;});	
	filterInput.on('keyup', function(e,dom){
		if(e.getKey()==e.BACKSPACE && dom.value.trim()==""){
			gridds.baseParams.q = "";
			gridds.reload();
		}
    });	
	filterInput.addKeyListener(Ext.EventObject.ENTER, filter);
	Ext.get("filterBtn").on('click', filter);
    
    gridds.on('load',function(){
    	var q = gridds.baseParams['q'];
    	Ext.get("filterInfo").update(q && q!="" ? "查询 '"+q+"' 共找到"+gridds.getTotalCount()+"个子角色" : "");   	 	
    });
    
 	/** 
 	 * 选择节点时的处理
 	 */
    var sm = mainTree.getSelectionModel();
    sm.on('selectionchange', function(){
        selectNode = sm.getSelectedNode();
    });    

 	/**
     * 右键菜单
 	 */
    var ctxMenu = new Ext.menu.Menu({
        id:'copyCtx',
        items: [{
                id:'newnode',
                cls:'new-mi',
                text:'新建角色',
                handler:function(){
                	form.reset();
                	afterSelect('newSubNode');
                	doNewSub();
                }
            },{
                id:'NewVirtual',
                cls:'new-mi',
                text:'新建虚拟组',
                handler:function(){
                	form.reset();
                	afterSelect('newSubNode');
                	var nodeAttr = selectNode.attributes;
					form.findField("roleTypeId").setValue(3);
                	doNewSub();
                }
            },{
                id:'modify',
                handler:modify,
                cls:'edit-mi',
                text:'修改',
                typeMapping:'role,group'
                
            },{
                id:'remove',
                handler:dodel,
                cls:'remove-mi',
                text: '删除',
                typeMapping:'role,group'
        	},{
                id:'sublist',
                handler:function(){
                	resetfilter();
    				setCurNodeType();	
                	afterSelect("subRoleList");
			    	openPanel(gridPanel,selectNode.text+'的'+oText[curNodeType].name+'列表');
                },
                cls:'list-mi',
                text:'子角色列表',
                typeMapping:'role,group'
            },{
                id:'newsubrole',
                handler:function(){
                	setCurNodeType();
                	afterSelect("newSubRole");
                	var title = '在'+selectNode.text+'下添加'+oText[curNodeType].name;
                	Ext.get('preHeader').update('生成'+oText[curNodeType].name);
                	$('group-name').value = selectNode.attributes.name;               	
               		$('group-name').readOnly = isGroup(selectNode) ? false : true;
                	openPanel(crtSubRolesPanel,title);

                },
                cls:'new-mi',
                text:'添加子角色',
                typeMapping:'role,group'
            }]
    });
    
	/** 
	 * 树图事件处理
	 */	 
    mainTree.on('contextmenu', prepareCtx);
    mainTree.el.swallowEvent('contextmenu', true);
    mainTree.el.addKeyListener(Ext.EventObject.DELETE, dodel);
    mainTree.el.on('keypress', function(e){
        if(e.isNavKeyPress()){
            e.stopEvent();
        }
    });
    
    /**
     * 修改节点信息
     */
    function modify(){ 
    	roleUserViewer.load(gridAction_getUsers+selectNode.id);
    	setCurNodeType();
    	openPanel(rolePanel,oText[curNodeType].modifyNodeTitle);
    	var panel = Ext.get("rolePanel");
    	panel.hide();
    	Ext.get("rolePanel-users-title").update(selectNode.text);
		var nodeData = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({url:treeAction_get+selectNode.id}),
        	reader: new Ext.data.JsonReader({}, [])
		});
    	nodeData.on('load', function() {
    		//填充表单
		    form.setValues(nodeData.reader.jsonData);
		    //刷新用户列表
    		roleUserViewer.jsonData = nodeData.reader.jsonData.users;
    		roleUserViewer.refresh();
    		//切换按钮
    		newNodeBtn.hide();modifybtn.show();
    	    //显示表单
	    	panel.fadeIn({duration:.4});
    		Ext.get("rolePanel-users").show();
		}); 		
		nodeData.load();
    }
    
    /**
     * 设置当前节点类型, nodeType='group'时为虚拟组，缺省为角色
     */
    function setCurNodeType(){
    	if(selectNode.attributes.nodeType=='group'){
    		curNodeType = "group";
    	}
    	else{
    		curNodeType = "role";
    	}
    }
    /**
     * 新建子节点
     */
    function doNewSub(){
    	setCurNodeType();
    	openPanel(rolePanel,oText[curNodeType].newNodeTitle);
    	var panel = Ext.get("rolePanel");
    	panel.hide();
    	//重置用户列表
    	Ext.get("rolePanel-users").hide();
    	newNodeBtn.show();modifybtn.hide();
    	
    	panel.fadeIn({duration:.4});
    	
    }
           
    /**
     * 弹出右键菜单前的处理
     */
    function prepareCtx(node, e){
        node.select();
        if(node.attributes.noCtx)
        	return;
        var mis = ctxMenu.items;
        mis.each(function(mi){
        	mi[mi.matchType(node.attributes.nodeType) ? 'show' : 'hide']();
        	var allow = node.attributes['allow' + mi.id];
        	if(allow===true) mi.show();
        	if(allow===false) mi.hide();
        });
        ctxMenu.showAt(e.getXY());
    }
	function isGroup(node){
		return node.attributes['nodeType']=='group';
	}
    function collapseAll(){
        ctxMenu.hide();
        setTimeout(function(){
            root.eachChild(function(n){
               n.collapse(false, false);
            });
        }, 10);
    }
    
    function expandAll(){
        ctxMenu.hide();
        setTimeout(function(){
            root.eachChild(function(n){
               n.expand(false, false);
            });
        }, 10);
    }

    
	/** 
	 * 删除节点
	 */
	function dodel(){
	    //弹出一个确认框
    	Ext.MessageBox.confirm(
    		'确认:', 
    		'您确定删除这个项目吗', 
    		function(btn){
    			if(btn=="yes"){
					var n = sm.getSelectedNode();
			    	Ext.lib.Ajax.request(
			    		"post",
			    		treeAction_del+n.id,
			    		{success: removeNode}
			    	);
    				
    			}
    		}
    	);
	}
    function removeNode(){
        var n = sm.getSelectedNode();
        if(n){
            mainTree.getSelectionModel().selectPrevious();
            n.parentNode.removeChild(n);
        }
        Ext.MessageBox.alert('提示', "您成功删除了一个节点。");
    }
        
    /**
     * 刷新指定的节点
     */
    function reloadTree(node){
    	if(node) {
    		//console.log(node.id+":"+node.isLeaf());
    		//TODO NODE RELOAD ITSELF
			node = node.attributes.leaf=="1" ? node.parentNode : node;
			sm.select(node);
			node.reload();
		}
    }
    function refreshTree(){
    	sysRoot.reload();
    	flwRoot.reload();  	
    } 

});

/**
 * 初始化布局区域
 * @param {Reigin} r 区域对象
 * @param {Panel} defaultPanel 要默认显示的面板对象
 */
function initRegion(r,defaultPanel){
	var _region = layout.getRegion(r);
	var _panel = _region.getPanel(defaultPanel);
	_region.panels.each(function(p){
		if(p!=_panel){
			_region.hidePanel(p);
		}
	});
	_region.showPanel(_panel);
}

/**
 * 切换面板
 * @param {Panel} p 要切换到的面板
 * @param {String} title 切换后显示的面板文字
 */
function openPanel(p,title){
	var _region = layout.getRegion('center');
	var _panel = _region.getPanel(p);
	initRegion('center','helpPanel');
	
	_region.showPanel(_panel);
	if(title!=null) _panel.setTitle(title);
}

var totalRecords = [], tempRecords = [], dupeRecords = [];

/**
 * 根据选择，批量创建出子角色的预览记录
 */
function pregrid(){	
	tempRecords.length = 0;
	dupeRecords.length = 0;
	
	var isVirtual = selectNode.attributes.nodeType=='group'; // 是否是虚拟组
	
	if(isVirtual && $V('group-name').trim()==""){
		alert("请输入虚拟组名称");
		return;
	}

	var subroleName = isVirtual ? $V('group-name') : $V('subRolesForm-roleName');	
	var data = [], deptField = Ext.getDom("deptid"), areaField = Ext.getDom("areaid");
	
    if(areaField.value != "" && areaField.value != "[]"){       
        data.push(eoms.JSONDecode(areaField.value));
    }
	if(selectNode.attributes.workflowFlag=="0" && deptField.value != "" && deptField.value != "[]"){
		data.push(eoms.JSONDecode(deptField.value));
	}
	data = data.concat(selects2arr('class1','class2','class3','class4','class5','class6','class7','class8'));
	
	//组合，并创建预览记录
	combinationer(data, new Array(data.length),0);
	
	if(tempRecords.length>0){
		subRolesPrestore.loadData(Ext.util.JSON.decode("["+tempRecords.toString()+"]"),true);
		totalRecords = totalRecords.concat(tempRecords);
	}
	if(dupeRecords.length>0){
		Ext.MessageBox.alert('提示', dupeRecords.length+"条重复的记录已被覆盖:"+dupeRecords.toString());
	}
	
	$("subpregrid-btns").show();
	
	/**
	 * 根据传入的下拉框元素id，创建出已选中数据的数组
	 * @param {String} 下拉框元素id
	 * @return {Array}
	 */
	function selects2arr(){
		var a = [];
		Ext.each(arguments,function(el){
			var o = [], el = Ext.getDom(el);
			if(!el || el.disabled || !el.options || el.options.length==0) return;
		
			var ops = el.options, l = el.options.length;
			for(var i=0;i<l;i++){
				if(ops[i].selected && ops[i].value!="" ) {
					o.push({"id":ops[i].value,"text":ops[i].text,"nodeType":el.id});
				}
			};
			if(o.length>0) a.push(o);
		});
		return a;
	};
	
	/**
	 * 根据用户的选择，重新组合创建出子角色的分类参数二维数组,并添加记录
	 * @param {Array} a 下拉框等原始数据数组
	 * @param {Array} c 组合后的数组
	 * @param {Number} n 计数器
	 */
	function combinationer(a, c, n){			
	    if (n >= a.length) {
	      //for (var i = 0; i < a.length; i++){}
	      //console.log(c);
	      addRecord(c);
	      return;
	    }
	    for (var i = 0; i < a[n].length; i++) {
	      c[n] = a[n][i];
	      combinationer(a, c, n + 1);
	    }
	}
	
	/**
	 * 增加一条子角色创建的预览记录
	 * @param {Array} params 包含创建参数的数组，每个成员为一个参数的对象:{id:,text:,nodeType:}
	 */
	function addRecord(params){
		var record = {}, name = [];
		var str = '{subRoleName:\"$name\"';
		for(var i=0;i<params.length;i++){
            var type = (params[i].nodeType == 'area' ? 'dept' : (params[i].nodeType == 'dept' ? 'area' : params[i].nodeType));           
			name.push(params[i].text);
			str += ',' + type + ':\"'+params[i].id+'\"';
		}
		str += '}'
		
		var newname = subroleName+"("+name.join("-")+")";
		str = str.replace('$name',newname);
		
		if(totalRecords.indexOf(str)<0) {
			tempRecords.push(str);
		}
		else{
			dupeRecords.push("<br/>"+newname);
		}	
	}
}

/**
 * 提交创建子角色
 */
function crtSubRoles(){
	if(subRolesPrestore.data.items.length==0){
		Ext.MessageBox.alert('提示', "请先生成你要创建的子角色预览，再提交。");
		return;
	}
	else{
		var json = subRolesPrestore.getJSON();
		Ext.get("subRoles").dom.value = json;
	}

    var data = Ext.lib.Ajax.serializeForm("subRolesForm");
	layout.el.mask("保存数据中，请稍候。", 'x-mask-loading');
    var unmask = layout.el.unmask.createDelegate(layout.el);
	Ext.lib.Ajax.request(
		"post",
		"tawSystemSubRoles.do?method=jsonSubRoles",
		{success: function(){
				unmask();
				Ext.MessageBox.alert('提示', "子角色提交成功");
				openPanel('gridPanel',selectNode.text+"的子角色列表");
				Ext.get("subRoleUsers").hide();
			},
		 failure: function(){unmask();Ext.MessageBox.alert('提示', "提交失败")}
		},
		data
	);
}

/**
 * 删除子角色
 */
function delSubRole(){
	var sm = grid.getSelectionModel();
	if(!sm.hasSelection()){alert("您至少要选中一个项目");return;}
	Ext.MessageBox.confirm("确认:","您确定删除项目吗？",function(btn){
		if(btn=="yes"){
			var tempArr = [];
			var rows = sm.getSelections();
			Ext.each(rows,function(r){
				tempArr.push(r.id);
			});
			Ext.Ajax.request({
				 method:"get",
				 url:gridAction_del+tempArr.toString(),
				 success: function(){gridds.reload();Ext.MessageBox.alert('提示', "删除子角色成功。");},
				 failure: function(){alert("fail")}
			});			
		}
	});
}

/**
 * 子角色改名
 */
function subrole_chgName(){
	var sm = grid.getSelectionModel();
	if(!sm.hasSelection()){alert("您至少要选中一个项目");return;}	
	if(sm.getCount()>1){alert("您同时只能修改一个子角色的名称");return;}
	Ext.MessageBox.prompt("修改子角色名称:","请输入子角色的新名称",function(btn,text){
		if(btn=="ok"){
			var subroleId = sm.getSelected().id;
			Ext.Ajax.request({
				 method:"post",
				 url:gridAction_setName,
				 success: function(){gridds.reload();},
				 failure: function(){Ext.MessageBox.alert('提示', "修改名称失败。");},
				 params:"id="+subroleId+"&newName="+encodeURI(encodeURI(text))
			});
		}
	});	
}

/**
 * 重置子角色创建面板
 */
function resetDistincter(){
    var node = selectNode.attributes, filters = Ext.get('workflow-filters');
    if(node.workflowFlag=="0"){
       eoms.form.enableArea("distinctionSystem");
       filters.update("");
    }
    else{
       eoms.form.disableArea("distinctionSystem",true);
	   var mgr = filters.getUpdateManager();
        mgr.update('tawSystemSubRoles.do?method=getRoleFiler&id='+node.workflowFlag);
    }
	$("subRolesForm").reset();
	$("subRolesForm-roleId").value = node.id;
	$("subRolesForm-roleName").value = node.text;
	subRolesPrestore.removeAll();
	totalRecords.length = 0;
	$("subpregrid-btns").hide();
}

/**
 * 重置子角色预览表格
 */
function resetPreGrid(){
	subRolesPrestore.removeAll();
	totalRecords.length = 0;
	$("subpregrid-btns").hide();
}

/**
 * 删除预览的子角色
 */
function delPreRoles(){
	var rows = subRoleGrid.getSelectionModel().getSelections();
	Ext.each(rows,function(r){
		subRoleGrid.dataSource.remove(r);
	});		
}

/**
 * 设置组长
 */
function setTeamLeader(){
	if(userViewer.getSelectionCount()==0){
		alert("请点击选择一个您要设置为组长的人员。");
		return;
	}
	var subRoleId = Ext.getDom('subRoleId').value;
	var node = userViewer.getSelectedNodes()[0];
	var jsondata = userViewer.getNodeData(node);
		Ext.Ajax.request({
    		method:"get",
    		url:eoms.appPath+"/role/tawSystemSubRoles.do?method=xSetLeader&id="+subRoleId+"&userId="+jsondata.id,
    		success: function(){
				userViewer.load(gridAction_getUsers+subRoleId);
			}
    	});
}

/**
 * 各操作对应的处理
 * @param {String} a action名称
 */
function afterSelect(a)
{
	switch (a) {
		case "newSubNode":
			var nodeAttr = selectNode.attributes;
			form.findField("roleTypeId").setValue(nodeAttr.roleTypeId);
			// 如果是系统角色parentId为当前节点的id，如果是流程角色parentId为0
			form.findField("parentId").setValue(nodeAttr.roleTypeId == 2 ? nodeAttr.id : 0);
			// 如果是系统角色workflowFlag为0，若是流程角色则为当前流程节点的id(workflowFlag)
			form.findField("workflowFlag").setValue(nodeAttr.roleTypeId == 2 ? 0 : nodeAttr.id);
			break;
		case "subRoleList":
			Ext.get('subRoleUsers').hide();
			break;
		case "newSubRole":
			resetDistincter();
			break;
		default:
			break;
	}
}


