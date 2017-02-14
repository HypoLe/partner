/**
 * 以树图为主要导航元素的布局基类
 * @class eoms.layout.baseTreeLayout
 * @extends Ext.util.Observable
 */
eoms.layout.baseTreeLayout = function(){
    eoms.layout.baseTreeLayout.superclass.constructor.call(this);
    this.addEvents({
        /**
         * @event treebeforeload
         * Fires before tree node load
         * @param see Ext.tree.treePanel "beoreload" event
         */
        "treebeforeload" : true,
        
        /**
         *@event end
         * Fires when end of init
         *@param {treeLayout} this
         */
        "end" : true
    });
}
Ext.extend(eoms.layout.baseTreeLayout, Ext.util.Observable, {
    /**
     * 初始化ToolBar工具栏
     */
    initToolBar : function(){
        var tb = new Ext.Toolbar('treePanel-tb');
        tb.add({
            id:'expand', text:'展开树图', cls:'x-btn-text-icon expand',
            handler:this.expandAll, tooltip:'展开树图',scope:this
        },{
            id:'collapse', text:'收起树图', cls:'x-btn-text-icon collapse',
            handler:this.collapseAll, tooltip:'收起树图',scope:this
        },{
            id:'refresh', text:'刷新树图', cls:'x-btn-text-icon refresh',
            handler:this.refreshTree, tooltip:'刷新树图',scope:this
        });
        this.tb = tb;
    },
    /**
     * 初始化布局
     */
    initLayout : function(){
        this.layout = new Ext.BorderLayout(document.body, {        
            north: {
                initialSize: 40, titlebar: false,      
                margins:{left:5,right:5,bottom:-1,top:5}
            },
            east: {
                split:true,titlebar: false, tabPosition:'top', alwaysShowTabs: true,
                initialSize: 300, minSize: 200, maxSize: 400,
                margins:{left:0,right:5,bottom:5,top:0}
            },
            center: {
                titlebar: false, autoScroll:true,tabPosition:'top', hideTabs:this.hideSearchPanel!=false,
                margins:{left:5,right:0,bottom:5,top:0}
            }
        });
    },
    /**
     * 初始化树图
     */
    initTree : function(){
        var xt = Ext.tree;
        var treeLoader = new xt.TreeLoader({
                dataUrl:this.treeGetNodeUrl
            });
        
        var tree = new xt.TreePanel('treePanel-body', {
            animate:true,
            enableDD:false,
            containerScroll: true,
            lines:true,
            rootVisible:true                
        });
            
        var treeroot = new xt.AsyncTreeNode({
            id:this.treeRootId,
            allowDrag:false,allowDrop:false,
            allowChild:true,allowEdit:false,allowDelete:false,
            text:this.treeRootText,
            loader: treeLoader
        });
        tree.setRootNode(treeroot);
        tree.render();
        treeroot.expand();
        
        var treesm = tree.getSelectionModel();
        
        treeLoader.on('beforeload',function(treeLoader,node,callback){
            treeLoader.baseParams['nodeType'] = (node.attributes.nodeType || '');
            this.fireEvent("treebeforeload", treeLoader,node,callback);
        },this);
        tree.el.on('keypress', function(e){
            if(e.isNavKeyPress()){
                e.stopEvent();
            }
        });
        this.tree = tree;
        this.treeroot = treeroot
        this.treesm = treesm;
    },
    /**
     * 初始化布局区域
     * @param {Region} r 要初始化的区域对象
     * @param {Panel} defaultPanel 默认显示的面板对象
     */
    initRegion : function(r,defaultPanel){
        var _region = this.layout.getRegion(r);
        var _panel = _region.getPanel(defaultPanel);
        _region.panels.each(function(p){
            if(p!=_panel){
                _region.hidePanel(p);
            }
        });
        _region.showPanel(_panel);          
    },
    /**
     * 打开指定的面板(在默认的east区域中)
     * @param {Panel} p 需要打开的面板对象
     * @param {String} title 面板打开后的title
     */
    openPanel : function(p,title){
        var _region = this.layout.getRegion('east');
        var _panel = _region.getPanel(p);
        this.initRegion('east','helpPanel');
        
        _region.showPanel(_panel);
        if(title!=null) _panel.setTitle(title);
        _panel.fireEvent('activate');           
    },
    /**
     * 打开指定区域的指定面板
     * @param {Region} region 指定区域对象
     * @param {Panel} panel 指定的面板对象
     * @param {String} title 面板打开后的title
     */
    openPanelOfRegion : function(region,panel,title){
        var _region = this.layout.getRegion(region);
        var _panel = _region.getPanel(panel);           
        _region.showPanel(_panel);
        if(title) _panel.setTitle(title);
        _panel.fireEvent('activate');           
    },
    /**
     * 收起树图
     */
    collapseAll : function(){
        this.treeroot.eachChild(function(n){
           n.collapse(false, false);
        });
    },
    /**
     * 展开树图
     */
    expandAll : function(){
        this.treeroot.eachChild(function(n){
           n.expand(false, false);
        });
    },
    /**
     * 刷新树图
     */
    refreshTree : function(){
        this.treeroot.reload();
    },
    /**
     * 刷新节点(重新载入父节点)
     * @param {Node} node
     */
    reloadNode : function(node){
        if(node) {
            node = node.parentNode || node;
            this.treesm.select(node);
            node.reload();
        }
    }
});


/** 
 * 简单的树图+异步表单提交布局
 * @class eoms.layout.treeLayout
 * @extends eoms.layout.baseTreeLayout
 * @version 0.1 090902 
 * @author mios
 */
eoms.layout.treeLayout = function(){
    eoms.layout.treeLayout.superclass.constructor.call(this);
    this.addEvents({
        /**
         *@event formloaded
         * Fires when form's data loaded
         *@param {Object} data 表单数据
         */
        "formloaded" : true
    });    
};

Ext.extend(eoms.layout.treeLayout, eoms.layout.baseTreeLayout, {
    /**
     * 初始化
     * @param {Object} cfg config options
     */
    init : function(cfg){
        var cfg = cfg || window.config;
        Ext.apply(this,cfg);
        Ext.get(document.body).setStyle("background-image","none");
        Ext.QuickTips.init();
        this.initToolBar();
        this.initLayout();
        this.initPanels();
        this.initTree();
        this.initTreeCtx();
        this.initForm();
        this.reloadStatus = {deleted : true};
        this.selected = null;
        this.selectedNode = null;
        this.on('end',function(){
            if(typeof this.onLoadFunctions == "function") this.onLoadFunctions();
        },this);
        this.fireEvent("end", this);
    },
    /**
     * 添加面板定义
     */
    initPanels : function(){
        var L = this.layout, CP = Ext.ContentPanel;
        L.beginUpdate();
        L.add('north', new CP('headerPanel'));
        var formPanel = L.add('east', new CP('formPanel', {
            id:'formPanel', title: '信息', fitToFrame:true, autoScroll:true
        }));
        formPanel.on('activate', function(){this.form.reset();}, this);
        
        L.add('center', new CP('searchPanel', {
            id:'searchPanel',
            autoCreate : true,
            title : '搜索结果',
            fitToFrame : true,
            background : true
        }));
        L.add('center', new CP('treePanel', {
            toolbar: this.tb,
            title:'树图'
        }));
        L.add('east', new CP('helpPanel', {
            title:'帮助',fitToFrame:true, autoScroll:true
        }));
        this.initRegion("east","helpPanel");
        L.endUpdate();
        L.getRegion("center").hidePanel('searchPanel');
    },
    /**
     * 初始化树图，调用父类中的initTree方法，并添加事件
     */
    initTree : function(){
        this.constructor.superclass.initTree.call(this);
        this.tree.on('contextmenu', this.prepareCtx,this);
        this.tree.el.swallowEvent('contextmenu', true);
        this.tree.el.addKeyListener(Ext.EventObject.DELETE, this.doDelNode);
        this.tree.on('dblclick',this.view,this);
    },
    /**
     * 初始化表单
     */
    initForm : function(){
        var form = new Ext.form.Form({
            labelWidth: 50, labelAlign: 'top', buttonAlign: 'left'
        });
        
        Ext.each(this.fields,function(f){
            form.add(f);
        });
        
        form.applyToFields(Ext.applyIf(this.fieldOptions,{width:150, disabledClass :'x-ux-readonly'}));
        
        var sbmBtn = form.addButton('保存',function(){
            if (form.isValid()) {
                form.submit({
                    failure: function(form,x) {
                        var response = eoms.JSONDecode(x.response.responseText);
                        Ext.MessageBox.alert('操作执行时出现问题!', response.message || "操作执行失败");
                    },
                    success: function(form,y) {
                        Ext.MessageBox.alert('提示', "操作执行成功");
                        this.initRegion("east","helpPanel");
                        if(form.url == this.actions.edtNode.url){
                            if(this.actions.edtNode.success){
                                this.actions.edtNode.success();
                            }
                            else {
                                this.reloadNode(this.selectedNode);
                            }
                        }
                        else if(form.url == this.actions.newNode.url){
                            if(this.actions.newNode.success){
                                this.actions.newNode.success();
                            }
                            else {
                                this.reloadNode(this.selectedNode);
                            }
                        }
                    },
                    scope: this,
                    waitMsg:'保存中...'
                });
            }else{
                Ext.MessageBox.alert('提示',"表单数据填写不正确，请检查。");
            }
        }, this);

        form.render('formPanel-body');
        
        var formData = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:this.actions.getNode.url}),
            reader: new Ext.data.JsonReader({}, []),
            listener: {
            exception:function( response) {
                               var o = response.responseText;
                                
                                  Ext.Msg.alert('错误提示',o);
                                 
        }
        }
        });
        formData.on('load', function() {
            var data = formData.reader.jsonData;
            try{
            if(data.success === false){
            	Ext.Msg.alert('错误提示',data.message);
            	this.initRegion("east","helpPanel");
            	return;
            }
            }catch(e){}
            
            this.savedFormData = data;
            form.setValues(data);
            this.fireEvent("formloaded", data);
        },this);
        
        this.form = form;
        this.formData = formData;
        this.sbmBtn = sbmBtn; 
    },
    /**
     * 初始化树图右键菜单
     */
    initTreeCtx : function(){
        var miNewNode = {
            id:'newnode', text:'新建子节点',cls:'new-mi',scope:this,
            handler: function(){
            	this.doNewNode(this.selected);
            }
        }
        Ext.apply(miNewNode, this.ctxMenu.newNode);
        
        var miEdtNode = {
            id:'edtnode', text:'修改',cls:'edit-mi',scope:this,
            handler:function(){
                this.doEdtNode(this.selected);
            }
        };
        Ext.apply(miEdtNode, this.ctxMenu.edtNode);
        
        var miDelNode = {
            id:'delnode', text:'删除',cls:'remove-mi',scope:this,
            handler:function(){
                this.doDelNode(this.selected, this.selectedNode);
            }
        };
        Ext.apply(miDelNode, this.ctxMenu.delNode);
        
        this.treeCtxMenu = new Ext.menu.Menu();
        this.treeCtxMenu.add(miNewNode,miEdtNode,miDelNode);
    },
    /**
     * 右键菜单呈现前的处理，根据节点信息定义每个菜单项的显示
     * @param {Node} node 右键点击的节点
     * @param {Event} e 事件
     */
    prepareCtx : function(node, e){
        node.select();
        this.selected = node.attributes;
        this.selectedNode = node;
        var mis = this.treeCtxMenu.items;
        var mis_hidden = true;
        mis.get('newnode')[node.attributes.allowChild ? 'show' : 'hide']();
        mis.get('delnode')[node.attributes.allowDelete ? 'show' : 'hide']();
        mis.get('edtnode')[(node.isRoot || node.attributes.allowEdit===false) ? 'hide' : 'show']();
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
    },
    getSelNode : function(a){
        if(a)
            return this.selected[a];
        else
            return this.selectedNode;
    },
    /**
     * 在数图右键菜单添加跟节点类型对应的菜单项
     * @param {String} nodeType 对应的节点类型
     * @param {Object} config 菜单项设置
     * @param (Number) index 添加到第几项
     */
    addmenu : function(nodeType,config,index){
        var newmi = index>=0 
            ? this.treeCtxMenu.insert(index, new Ext.menu.Item(config)) 
            : this.treeCtxMenu.add(config);
        newmi.nodeTypeMapping = nodeType;
    },
    /**
     * 执行新建节点动作
     */
    doNewNode : function(nodeData){
        this.openPanel('formPanel','在'+this.selected.text+' 下新建');
        this.form.url = this.actions.newNode.url;
        this.sbmBtn.setText(this.actions.newNode.btnText);
        this.sbmBtn.show();
        try{this.actions.newNode.init();}catch(e){};
        this.savedFormData = null;
        this.form.items.each(function(f){
            f.enable();
        });
    },
    /**
     * 执行修改节点动作(打开表单修改面板)
     * @param {Object} nodeData
     */
    doEdtNode : function(nodeData){
        this.openPanel('formPanel','修改'+nodeData.text+'的信息');
        this.form.url = this.actions.edtNode.url;
        this.sbmBtn.setText(this.actions.edtNode.btnText);
        this.sbmBtn.show();
        try{this.actions.edtNode.init();}catch(e){};
        this.formData.load({params:{id:nodeData.id}});
        this.form.items.each(function(f){
            f.enable();
        });
    },
    /**
     * 执行查看节点信息动作
     * @param {Node} nodeData
     * @param {Event} e
     */
    view : function(node, e){
        var data = node.attributes || node; //从查询过来的数据没有attributes
    	if(!this.canViewNodeType || !this.canViewNodeType.hasSubString(data.nodeType,",")) return;
        this.openPanel('formPanel','查看'+data.text+'的信息');
        this.formData.load({params:{id:data.id}});
        this.form.items.each(function(f){
            f.disable();
        });
        this.sbmBtn.hide();
    },
    /**
     * 执行删除节点动作
     * @param {Object} nodeData
     * @param {Node} node
     */
    doDelNode : function(nodeData,node){
        var params = "id="+nodeData.id;
        
        if(typeof this.actions.delNode.customData == "function"){
            params = this.actions.delNode.customData(nodeData);
        }
        
        var myId = nodeData.id;
            	//if(myId.indexOf('2')==0){
            	//	Ext.MessageBox.alert('提示',"合作伙伴下的部门无法被删除");
            	//	return false;
         //}
        
        //弹出一个确认框
        Ext.MessageBox.confirm('确认:', '您确定删除这个项目吗', 
            function(btn){
                if(btn=="yes"){
                    Ext.Ajax.request({
                        method : 'post',
                        url : this.actions.delNode.url,
                        success: function(resp){
                        try{
	                        var szReturn = eoms.JSONDecode(resp.responseText);
	                        if(szReturn.success===false){
	                        Ext.MessageBox.alert('操作执行时出现问题!', szReturn.message || "操作执行失败");
	                        return;
                        }
                        }catch(e){}
                       
                            if(node){
                                this.treesm.selectPrevious();
                                node.parentNode.removeChild(node);
                            }
                            this.initRegion('east','helpPanel');
                            this.reloadStatus.deleted = true;
                            Ext.MessageBox.alert('提示',"您成功删除了一个节点。");
                        },
                        failure: function(x) {
	                        var response = x.responseText;
	                        Ext.MessageBox.alert('操作执行时出现问题!', response || "操作执行失败");
                        },
                        scope:this,
                        params:params
                    });                 
                }
            },this);
    },
    /**
     * 设置表单项的值
     * @param {String} fid 表单项id
     * @param {Object} attr 当前选中节点的属性名，用此属性的值填充表单项
     */
    setField : function(fid,attr){
        this.form.findField(fid).setValue(this.selected[attr]);
    }
});

var AppSimpleTree = new eoms.layout.treeLayout();


