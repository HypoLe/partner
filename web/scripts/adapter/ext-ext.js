Ext.BLANK_IMAGE_URL = eoms.appPath + '/images/s.gif';
Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';

/**
 * 修正表单项的actionEl,使隐藏时label也隐藏
 */
Ext.form.Field.prototype.getActionEl = function(){
    return this.el.findParent("div.x-form-item",5,true);
}
Ext.form.TriggerField.prototype.onHide = function(){
        this.getActionEl().setDisplayed(false);
        this.el.dom.disabled = true;
        if(this.hiddenField)this.hiddenField.disabled = true;
}
Ext.form.TriggerField.prototype.onShow = function(){
        this.getActionEl().setDisplayed(true);
        this.el.dom.disabled = false;
        if(this.hiddenField)this.hiddenField.disabled = false;
}
Ext.form.Field.prototype.onHide = function(){
        this.getActionEl().setDisplayed(false);
        this.el.dom.disabled = true;
}
Ext.form.Field.prototype.onShow = function(){
        this.getActionEl().setDisplayed(true);
        this.el.dom.disabled = false;
}

/**
 * Ext.form.HiddenField
 */
Ext.form.HiddenField = function(config) {
    config.type = 'hidden';
    config.inputType = 'hidden';
    Ext.form.HiddenField.superclass.constructor.call(this, config);
};
Ext.extend(Ext.form.HiddenField, Ext.form.TextField, {
    // private
    onRender : function(ct, position) {
        if (this.el) {
            this.el = Ext.get(this.el);
            if (!this.target) {
                ct.dom.appendChild(this.el.dom);
            }
        } else {
            var cfg = {
                tag : "input",
                type : "hidden"
            };
            if (!cfg.name) {
                cfg.name = this.name || this.id;
            }
            cfg.id = this.id;
            this.el = ct.createChild(cfg, position);
        }
        this.el.dom.readOnly = true;
        this.el.dom.parentNode.parentNode.style.display = 'none';
        this.initValue();
    }
});

/**
 * Ext.form.SimpleSelect 用于平台模块下拉框的简易写法
 */
Ext.form.SimpleSelect = function(config) {
    config.store = new Ext.data.SimpleStore({
        fields : ['text', 'value'],
        data : config.values
    });
    config.displayField = 'text';
    config.valueField = 'value';
    config.editable = false;
    config.mode = 'local';
    config.triggerAction = 'all';
    config.emptyText = '请选择';
    config.selectOnFocus = true;
    Ext.form.SimpleSelect.superclass.constructor.call(this, config);
};
Ext.extend(Ext.form.SimpleSelect, Ext.form.ComboBox, {});

/**
 * Ext.LayoutRegion.createToolButton 在region上创建一个按钮，可显示图标和文字
 * @param {Object} cfg 设置选项
 * @cfg {String} cls className
 * @cfg {Function} fn 点击时执行的函数
 * @cfg {Obj} scope 函数作用域
 * @cfg {String} text 显示的文本
 * @cfg {String} tips 鼠标说明文字
 */
Ext.LayoutRegion.prototype.createToolButton = function(cfg) {
    var _btn = this.createTool(this.tools.dom, cfg.cls);
    var icon = _btn.down('div.x-layout-tools-button-inner');
    icon.addClass('x-layout-panel-hd-tb');
    icon.dom.innerHTML = cfg.text || '';
    if(cfg.tips) icon.dom['qtip'] = cfg.tips;
    _btn.enableDisplayMode();
    _btn.on('click', cfg.fn, cfg.scope, true);
    return _btn;
};


/**
 * Ext.menu.BaseItem 扩展
 * 根据 typeMapping 和传入的type判断是否属于该type的菜单项
 * 用于菜单项的隐藏和显示
 * @param {String} type 菜单项类型
 */
Ext.menu.BaseItem.prototype.matchType = function(type) {
    if(!this.typeMapping) return true;
    if(type instanceof Array){
        return this.typeMapping.hasSubStrInArr(type,',');
    }
    else{
        return this.typeMapping.hasSubString(type,',');
    }
}

/**
 * + Ext.tree.TreePanel.resetRoot
 */
Ext.tree.TreePanel.prototype.resetRoot = function(url) {
    if (url == null || url == "")
        return;
    this.loader.dataUrl = url;
    this.root.reload();
};

/**
 * + Ext.tree.TreePanel.setParam
 */
Ext.tree.TreePanel.prototype.setParam = function(pname, pvalue) {
    eval("this.loader.baseParams." + pname + " = pvalue");
    this.root.reload();
};
/**
 * 添加树图节点的getCustomPath方法
 */
Ext.data.Node.prototype.getCustomPath = function(attr){
    attr = attr || "id";
    var p = this.parentNode;
    var b = [this.attributes[attr]];
    while(p && !p.isRoot){
        b.unshift(p.attributes[attr]);
        p = p.parentNode;
    }
    return b.join('-');
}
Ext.tree.TreeNode.prototype.render = function(bulkRender){
    this.ui.render(bulkRender);
    if(!this.isRoot){
        this.ui.ecNode.id = 'ecNode-'+this.attributes.id;
        this.ui.textNode.id = 'textNode-'+this.attributes.id;
    }
    if(!this.rendered){
        this.rendered = true;
        if(this.expanded){
            this.expanded = false;
            this.expand(false, false);
        }
    }
};

Ext.tree.TreeNodeUI.prototype.toggleCheck = function(value, fire) {
    var cb = this.checkbox;
    if (cb) {
        cb.checked = (value === undefined ? !cb.checked : value);
        this.node.attributes.checked = cb.checked;
        if(fire !== false)this.fireEvent('checkchange', this.node, cb.checked);
    }
};
/**
 * 去掉树节点双击时执行toggle
 */
Ext.tree.TreeNodeUI.prototype.onDblClick = function(e){
    e.preventDefault();
    if(this.disabled) return;
    if(this.fireEvent("dblclick", this.node, e) && this.checkbox){
        this.toggleCheck();
    }
};

/**
 * ctrl键多选时允许删除选中项
 */
Ext.override(Ext.tree.MultiSelectionModel,{
    select : function(node, e, keepExisting){
        if(keepExisting !== true){
            this.clearSelections(true);
        }
        if(this.isSelected(node)){
            this.unselect(node);
            return node;
        }
        this.selNodes.push(node);
        this.selMap[node.id] = node;
        this.lastSelNode = node;
        node.ui.onSelectedChange(true);
        this.fireEvent("selectionchange", this, this.selNodes);
        return node;
    }
});

Ext.namespace("eoms.panel");

/**
 * 创建面板
 */
eoms.panel.create = function(cfg){
    var t = cfg.type;
    if(!t || typeof eoms.panel[t] != "function") t = "tree";
    return new eoms.panel[t](Ext.id(),cfg);
}

/**
 * 对panel进行排序,pos="first"的排在前面，pos="last"的排在后面
 */
eoms.panel.sort = function(a,b){
    if(a.pos && a.pos == "first" && (!b.pos || b.pos != "first")) return -1;
    if(a.pos && a.pos == "last" && (!b.pos || b.pos != "last")) return 1;
    if(b.pos && b.pos == "last" && (!a.pos || a.pos != "last")) return -1;
    if(b.pos && b.pos == "first" && (!a.pos || a.pos != "first")) return 1;
    return 0;
}

/**
 * @class 树图面板
 * @extends Ext.ContentPanel
 * @param {String/Element/DOM} el
 * @param {Object} config 设置选项
 * @cfg text 面板名称
 * @cfg dataUrl 树图地址
 * @cfg rootId 树图根节点id
 */
eoms.panel.Tree = function(el, config) {
    if(/^\//.test(config.dataUrl) && config.dataUrl.indexOf(eoms.appPath) != 0){
        config.dataUrl = eoms.appPath + config.dataUrl;
    }
    config = Ext.apply({
        title : config.text || config.title,
        checktype : config.checktype || config.treeChkType,
        fitToFrame : true,
        autoScroll : true,
        autoCreate : true
    },config);
    
    eoms.panel.Tree.superclass.constructor.call(this, el, config);
    
    this.addEvents({
        'dblclick' : true, //当双击树图节点时
        'expand' : true, //当树图节点打开时
        'check' : true,   //当点击checkbox时
        'beforeappend' : true  //当插入子节点前
    });
    
    var treeLoader = new Ext.tree.TreeLoader({
        dataUrl : this.dataUrl,
        baseParams : this.params || {},
        baseAttrs : this.baseAttrs || {}
    });
        
    treeLoader.on('beforeload',function(treeLoader,node,callback){
        this.baseParams['nodeType'] = node.attributes.nodeType || '';
        if(node.parentNode){
            this.baseParams['parentNode'] = node.parentNode.id;
            this.baseParams['parentNodeType'] = node.parentNode.attributes.nodeType || '';
        }
        
        //attrParams中定义需要传到后台的节点属性，可以是字符串或数组
        if(config.attrParams){
            Ext.each([].concat(config.attrParams),function(p){
                if(typeof node.attributes[p] != 'undefined')
                  this.baseParams[p] =  node.attributes[p]; 
            },this);
        }
    });
        
    var tree = new Ext.tree.TreePanel(el, {
        animate : true,
        enableDD : false,
        containerScroll : true,
        rootVisible : true,
        loader : treeLoader,
        selModel : new Ext.tree.MultiSelectionModel()
    });
    
    tree.on('click', function(node, e) {
    	if(node.expanded){
			node.collapse(false, true);
		}else{
			node.expand(false, true);
		}
    });
    
    var root = new Ext.tree.AsyncTreeNode({
        id : this.rootId || '-1',
        text : this.title,
        nodeType : 'root',
        expanded : false
    });
    tree.setRootNode(root); 
    tree.render();
       
    tree.on({
       'dblclick' : this.fireEvent.createDelegate(this,['dblclick'], 0),
       'beforeappend' : this.fireEvent.createDelegate(this, ['beforeappend'], 0),
       'expand' : this.fireEvent.createDelegate(this,['expand'], 0),
       'checkchange' : this.fireEvent.createDelegate(this, ['check'], 0)
    });
    
    tree.checktype = this.checktype;
    this.tree = tree;
};

Ext.extend(eoms.panel.Tree, Ext.ContentPanel,{
    getSelection : function(){
        return this.tree.getSelectionModel().selNodes;
    },
    clearSelections : function(){
        this.tree.getSelectionModel().clearSelections();
    }
});

eoms.panel.tree = eoms.panel.Tree;

/**
 * xbox 弹出一个选择窗口
 * 
 * @e.g 例子
 * 
 * <pre>
 * Ext.onReady(function() {
 *     tree = new xbox({
 *         btnId : 'btnDlg2',
 *         treeDataUrl : treeAction
 *     });
 *     box = new xbox({
 *         btnId : 'btnDlg',
 *         treeDataUrl : treeAction,
 *         single : true,
 *         showChkFld : '',
 *         saveChkFld : ''
 *     });
 * });
 * </pre>
 * 
 * @cfg btnId 按钮ID
 * @cfg treeDataUrl 树图action地址
 * @cfg treeRootId 树图根节点id
 * @cfg treeRootText 树图根节点显示文字
 * @cfg single 是否单选,默认为多选
 * @cfg treeChkType 可供选择的节点类型(user,dept,...)
 * @cfg showChkFldId 选择后显示域的ID
 * @cfg saveChkFldId 选择后保存ID的隐藏域ID
 * @cfg returnJSON 为true返回JSON格式数据，默认返回逗号隔开的数据
 * @cfg callback 选择后执行的函数
 * @cfg dlgTitle 窗口文字
 * @cfg mode =clearPath 根据父子节点清除同路径其他选中节点; =clearPathById 根据id清除同路径其他选中节点
 **/
function xbox(cfg) {
    Ext.apply(this, cfg);
    this.defaultConfig = cfg;
    this.addEvents({
        'beforeselect' : true,  //当进行数据选择前
        'beforeshow' : true,    //当显示选择窗口前
        'afterhide' : true,      //当关闭选择窗口后
        'tree.dblclick' : true   //双击树图节点时
    });
    
    this.body = Ext.DomHelper.append(document.body,{cls:'x-layout-inactive-content'},true);
    this.listEl = this.body.createChild({cls:'x-layout-inactive-content'});
       
    if(this.viewer === true) this.createViewer();    
    if (this.viewer && this.viewer.jsonData) {
        this.data = this.viewer.jsonData;
    }

    this.createGrid();
    if(this.data && this.data.length>0){
        this.gridData.loadData(this.data);
        this.update();
    }
    
    this.single = this.single || (this.treeChkMode=="single");
    this.showBtn = document.getElementById(this.btnId);
    Ext.fly(this.btnId).on('click', this.onBtnClick, this);
    this.trees = []; 
    this.panels = [];
    this.panelsConfig = this.panelsConfig || [];
    this.panelsConfig = this.initPanelCfg(cfg);
    
    Ext.each(this.panelsConfig,function(cfg){
        var p = eoms.panel.create(cfg);
        if(p instanceof eoms.panel.Tree){
            p.on("beforeappend", this.createCheck, this);
            p.on("check", this.onCheck, this);
            p.on("expand", this.onTreeExpand, this);
            p.on("dblclick", this.onTreeDblclick, this);
            this.trees.push(p.tree);
            if(cfg.isDefault) this.defaultTree = p.tree;
        }
        else{
            p.on("dblclick", function(){
                var data = p.getSelection();
                this.onSelect(data, !this.single);
            }, this);     
        }     
        if(cfg.isDefault) this.defaultPanel = p;
        this.panels.push(p);
    },this);
       
    this.render();
    
    if(this.mode && typeof this[this.mode] == 'function') this[this.mode]();
};
Ext.extend(xbox, Ext.util.Observable, {
    /**
     * 添加默认面板(如果设置了)，并排序
     * @param {Object} cfg
     */
    initPanelCfg : function(cfg){
        if(cfg.dataUrl || cfg.treeDataUrl){
            this.panelsConfig.unshift({
                type : cfg.type || 'tree',
                checktype : cfg.checktype || cfg.treeChkType,
                dataUrl : cfg.dataUrl || cfg.treeDataUrl,
                rootId : cfg.rootId || cfg.treeRootId,
                title : cfg.rootText || cfg.treeRootText,
                params : cfg.params,
                attrParams : cfg.attrParams,
                baseAttrs : cfg.baseAttrs,
                isDefault : true
            });
        }
        return this.panelsConfig.sort(eoms.panel.sort);
    },
    /**
     * 创建viewer，即数据选择后在页面中的呈现
     */
    createViewer : function(){
        this.viewer = new Ext.JsonView("xbox_"+this.id+"_view",
            '<div class="viewlistitem-{nodeType}">{text}; </div>',
            {emptyText : '<div>没有选择项目</div>'}
        );
        this.viewer.refresh();
    },
    /**
     * 创建一个grid，呈现在选择窗口中选中后的数据
     */
    createGrid : function(){
        var gridData = new Ext.data.JsonStore({
            id : 'id',
            proxy : new Ext.data.MemoryProxy(),
            reader : new Ext.data.JsonReader({}, [{name : 'name'}, {name : 'id'}]),
            fields : ['id', 'name','text', 'nodeType', 'mobile']
        });
        
        var grid = new Ext.grid.Grid(this.listEl, {
            ds : gridData,
            cm : new Ext.grid.ColumnModel([ {header : "名称", dataIndex : 'name'} ]),
            enableColLock : true,
            autoWidth : true
        });
        
        // 增加双击名称列时执行删除的事件
        grid.on('celldblclick',function(grid, rowIndex, columnIndex, e) {
            if (grid.getColumnModel().getDataIndex(columnIndex) == 'name') {
                var record = gridData.getAt(rowIndex);
                var checkedNodes = this.getChecked();
                gridData.remove(gridData.getAt(rowIndex));
                for (var i = 0; i < checkedNodes.length; i++) {
                    if (checkedNodes[i].id == record.get('id')) {
                        checkedNodes[i].getUI().toggleCheck(false);
                    }
                }
            }
        },this);
        
        grid.render();
        this.grid = grid;
        this.gridData = gridData;
    },
    /**
     * 渲染对话窗口
     */
    render : function(){
        var dlg = new Ext.LayoutDialog(this.body, {
            title : this.dlgTitle || '选择',
            modal : true,
            autoTabs : true,
            width : this.width || 450,
            height : this.height || 400,
            shadow : false,
            minWidth : 300,
            minHeight : 300,
            proxyDrag : false
        },this);
        
        dlg.layout = Ext.BorderLayout.create({
            west : {
                split : true,
                initialSize : 250,
                autoScroll : true,
                titlebar : false,
                tabPosition : 'top',
                alwaysShowTabs : true,
                panels : this.panels
            },
            center : {
                titlebar : true,
                autoScroll : true,
                tabPosition : 'top',
                alwaysShowTabs : false,
                panels : [new Ext.GridPanel(this.grid, { title : '选择结果(双击名称删除)' })]
            }
        
        },dlg.body.dom);
        
        dlg.addKeyListener(27, this.hide, this);
        dlg.addButton('确定', this.output, this);
        dlg.addButton('关闭', this.hide, this);
        this.dialog = dlg;
        
        var layout = this.dialog.getLayout();
        this.onLayout(this,layout);
        this.west = layout.getRegion('west');
        this.west.showPanel(0);
    },
    /**
     * 点击按钮时执行事件
     */
    onBtnClick : function(){
        this.show(this.showBtn,this.defaultConfig.showChkFldId,this.defaultConfig.saveChkFldId);
    },
    /**
     * 渲染布局时事件
     * @override
     */
    onLayout : function(){},
    /**
     * 选中checkbox前
     * @override
     */
    onBeforeCheck : function(node, checked){return true},
    /**
     * beforeappend事件时创建checkbox
     * 当没有配置checktype时，每个节点都会有checkbox
     * 当配置的checktype中包含该类节点的nodetype时，该类节点会有checkbox
     * checktype中有多种nodetype，则用,分隔，如"dept,user"
     */
    createCheck : function(tree,parentNode,node){
        var checktype = tree.checktype;
        var nodetype = node.attributes.nodeType;  
        var showPartnerLevelType =  node.attributes.showPartnerLevelType;
        if(typeof(showPartnerLevelType)!="undefined" && showPartnerLevelType!=""){
	    		var level = showPartnerLevelType;
	    		if(null != level && '' != level && level.length >0){
	    			var arry=level.split("|"); 
	    			for(var i = 0;i<arry.length;i++){
	        			var tempLevel = arry[i];
        				if((node.attributes.deptLevel+"")==(tempLevel+"")){
	           		 		node.attributes.checked = false;
	            		}
	    			}
	    		}
	    	}else  if(!checktype || (nodetype && checktype.hasSubString(nodetype,","))){
	        node.attributes.checked = false;
        }
    },
    /**
     * 更新选中列表的数据
     */
    loadData : function(node,append){
        if(node instanceof Array){
            for(var i = 0, len = node.length; i < len; i++){
                this.loadData(node[i],append);
            }
        }
        else{
            var data = node.attributes ? node.attributes : node;
            this.gridData.loadData([{
                'id' : data.id,
                'name' : data.name || data.text,
                'text' : (this.mode=='clearPath' || this.mode=='clearPathById')? node.getCustomPath('text') : (data.text || data.name),
                'nodeType' : data.nodeType,
                'mobile' : data.mobile
            }], append);
            this.datachanged = true;
        }
    },
    /**
     * 选择数据
     * @param {Object/Array} data 选中的一个或一组数据
     * @param {Boolean} append 是否添加到当前的选择中
     */
    onSelect : function(data,append){
        if(this.fireEvent('beforeselect',data)===false) {
            this.unSelect(data);
            return;
        }
        if(this.single) {
            if(this.singleSelected) this.unSelect(this.singleSelected);
            this.singleSelected = data instanceof Array ? data[0] : data;
        }
        this.loadData(data, append);
    },
    /**
     * 去除单个节点的已选状态
     * @param {Node/Object} n
     */
    unSelect : function(n){
        if(n.getUI) n.getUI().toggleCheck(false, false);
    },
    /**
     * 当点击checkbox时
     */
    onCheck : function(node, checked){
        if(!this.onBeforeCheck(node, checked)){
          this.unSelect(node);
          return false;
        }
        // 单选
        if (this.single) {
            if (this.singleSelected != null) {
                var c = this.singleSelected;
                if (c==node) {// 删除
                    this.gridData.removeAll();
                    this.singleSelected = null;
                } else {
                    this.onSelect(node,false);
                }
                return;
            }
            this.onSelect(node,false);
            return;
        }
        // 多选
        if (checked) {
            if (this.gridData.indexOfId(node.id) == -1) {
                this.onSelect(node,true);
            }
        } else {
            if (this.gridData.indexOfId(node.id) >= 0) {
                var record = this.gridData.getById(node.id);
                if (typeof record == "object")
                    this.gridData.remove(record);
            }
        }
        this.onAfterCheck(node, checked);
    },
    /**
     * 当点击checkbox之后\
     * @override
     * @param {Node} node
     * @param {Boolean} checked
     */
    onAfterCheck : function(node, checked){},
    /**
     * 展开树图节点时
     */
    onTreeExpand : function(node){
        Ext.each(this.gridData.getJSONData(), function(item) {
            var cknode = node.findChild('id', item.id);
            if (cknode){
                if(cknode.attributes.nodeType && cknode.attributes.nodeType == item.nodeType){
                    if(this.single && this.singleSelected) return;
                    cknode.getUI().toggleCheck(true, false);
                }
            }
        },this);
    },
    /**
     * 双击树图节点时,按住shift会全选该节点的子节点
     * @param {Ext.TreeNode} node
     * @param {Ext.Event} e
     * @return {Boolean} 是否选中当前双击的节点
     */
    onTreeDblclick : function(node, e){
        var shift = false;
        if((e && e.shiftKey) || this.mode == "selectChildren") shift = true;
        if(shift && !node.attributes.leaf){
            node.toggleChild = !!!node.toggleChild;
            node.expand(false,false,function(node){
                node.eachChild(function(child){
                    child.getUI().toggleCheck(node.toggleChild);
                });
            });
            return false;
        }
        return this.fireEvent('tree.dblclick', node, e);
    },
    /**
     * 得到所有树图上被选中的节点数组
     * @return {Array}
     */
    getChecked : function(){
        var c = [];
        Ext.each(this.trees,function(t){
            c = c.concat(t.getChecked());
        },this);
        return c;
    },
    /**
     * 显示窗口，可在另外的按钮上绑定此事件，并设置显示和保存数据的容器id
     * 如：onclick="javascript:userTree.show(this,'show','save')"
     * @param dom btn 按钮节点
     * @param string showElId 显示选择数据的容器id
     * @param stinrg saveElId 保存选择数据的容器id
     */
    show : function(btn, showElId, saveElId){
        this.fireEvent("beforeshow", this);
        this.datachanged = false;
        if(showElId)this.showChkFldId = showElId;
        if(saveElId)this.saveChkFldId = saveElId;
        this.dialog.show();
    },
    /**
     * 关闭对话框
     */
    hide:function(){
        this.dialog.hide();
        if(this.datachanged){
            this.gridData.loadData(this.data || []);
            this.reloadTree();
        }
        this.fireEvent("afterhide", this);
    },
    /**
     * 点击确定时
     */
    output:function(){
        this.update();
        this.dialog.hide();
        if(typeof this.callback == 'function'){
            var list = [], jsonData = this.gridData.getJSONData();
            Ext.each(jsonData,function(item){
                list.push(item.id);
            },this);
              this.callback(jsonData, list.toString());
        }
        this.data = this.gridData.getJSONData();
    },
    /**
     * 更新view
     */
    update : function(){
        var jsonData = this.gridData.getJSONData();
        var chosenTextList = [],chosenDataList = [];

        Ext.each(jsonData,function(item){
            chosenTextList.push(item.text || item.name);
            chosenDataList.push(item.id);
        },this);

        if (this.viewer) {
            this.viewer.jsonData = jsonData;
            this.viewer.refresh();
        }

        if(this.returnJSON) chosenDataList = this.gridData.getJSON();
        this.onOutput(chosenTextList,chosenDataList,jsonData);
    },
    /**
     * 向输出的表单域中输出数据
     * @param {Array} textList 选中项的文本的数组
     * @param {Array} dataList 选中项的id的数组
     * @param {JSON} jsonData 选中项的JSON数据 
     */
    onOutput : function(textList,dataList,jsonData){
        if (this.showChkFldId) {
        	
            $(this.showChkFldId).update(textList.toString());
            
            try { //激活文本域的验证
                $(this.showChkFldId).focus();
                $(this.showChkFldId).blur();
            }
            catch(e){}
        }
        if (this.saveChkFldId) {
        	/**代维物资里的车辆管理修改此处主要目的是为了自动填写司机的电话 2012-11-14 modify by chenyuanshu begin **/
        	if(this.saveChkFldId.indexOf(",")!=-1){
        		var array = this.saveChkFldId.split(",");
        		for(i=0;i<array.length;i++){
        			var name = array[i];
        			if(i==0){
        				$(name).update(dataList.toString());
        			}else if(name =="driverContact"){/**代维物资里的车辆管理修改此处主要目的是为了自动填写司机的电话**/
        				var chosenMobileList = [];
        				  Ext.each(jsonData,function(item){
            					chosenMobileList.push(item.mobile);
       					 },this);
        				$(name).update(chosenMobileList);
        			}else{
        				$(name).update(dataList.toString());
        			}
        		}
        	}/**代维物资里的车辆管理修改此处主要目的是为了自动填写司机的电话 2012-11-14 modify by chenyuanshu end **/
        	else{
            	$(this.saveChkFldId).update(dataList.toString());
        	}
        }
        
        if(this.k=="1"){
        	 setMaste(dataList.toString(),textList.toString());
        }
    },
    /**
     * 重定树图地址并刷新(defaultTree)
     * @param {String} url 树图的新地址
     */
    resetRoot : function(url){
        if (url == null || url == "")return;
        this.defaultTree.loader.dataUrl = url;
        this.defaultTree.root.reload();
    },
    /**
     * 清空选中项列表、viewer及表单域，刷新所有树图
     */
    reset : function(){
        this.gridData.removeAll();
        if(this.viewer){
            this.viewer.jsonData = [];
            this.viewer.refresh();
        }
        this.onOutput("","",null);
        this.reloadTree();
    },
    /**
     * 刷新指定序号的树图或所有树图
     * @param {Number} i 树图序号
     */
    reloadTree : function(i){
        var trees = (i && this.trees[i]) ? [this.trees[i]] : this.trees;
        Ext.each(trees,function(tree){
            var r = tree.root;
            if(r.isExpanded())r.reload();
        });
    },
    /**
     * clearPath模式，根据父子节点清除同路径上的其他节点。
     */
    clearPath : function(){
        this.onAfterCheck = function(node,checked){
            if(checked===false) return;
            function clearcheck(n){
                if(!n.isRoot && n.id!=node.id && n.attributes.checked){
                    n.attributes.checked = false;
                    n.getUI().checkbox.checked = false;
                    var record = this.gridData.getById(n.id);
                    if (typeof record == "object") this.gridData.remove(record);
                }
            }
            node.bubble(clearcheck,this);
            node.cascade(clearcheck,this);
        };
    },
    /**
     * clearPathById模式，根据节点Id清除同路径上的其他节点。
     */
    clearPathById : function(){
        this.onAfterCheck = function(node,checked){
            if(checked===false) return;
            this.gridData.snapshot = this.gridData.data;
            this.gridData.filterBy(function(d){
                if(d.id!=node.id && (d.id.indexOf(node.id)==0 || node.id.indexOf(d.id)==0)){
                    var n = this.defaultTree.getNodeById(d.id);
                    if(n){
                        n.attributes.checked = false;
                        n.getUI().checkbox.checked = false;
                    }
                    return false;
                }
                return true;
            },this);
        };
    },
    /**
     * selectChildren模式，双击节点后全选子节点，再双击全取消。
     */
    selectChildren : function(){
        Ext.DomHelper.insertFirst(this.dialog.footer,{
            tag:'div',html:'小提示:双击节点可全选下级子节点，再双击取消.',style:'margin-bottom:3px'
        });
    }
});

/**
 * 用于选择派发对象的弹出窗口
 */
var Chooser = function(config) {
    var xt = Ext.tree;
    var CP = Ext.ContentPanel;
    this.showFilter = false; //是否显示条件过滤(主面板)
    this.returnJSON = false; //是否返回JSON数据格式
    this.showLeader = false; //是否设置组长
    this.chosen = new Ext.util.MixedCollection();    //已选择项,保存已选择节点的属性,即node.attributes
    this.defaultChildType = ''; //默认不限节点类型
    Ext.apply(this, config, config.config);

    this.panels = this.panels || [{text : '部门与人员',dataUrl : '/xtree.do?method=userFromDept'}];
    this.body = Ext.get(this.id);
    this.body.down("input[type='button']").on('click', this.show, this);
    this.totalJSON =this.body.createChild ({tag:'input',type:'hidden',name:this.id+'TotalJSON'});

    // 主选择框
    var dlg = new Ext.LayoutDialog(this.id+'-chooser', {
        title: this.title || '选择派发对象',
        width : 750,
        height : 450,
        modal : false,
        shadow : false,
        collapsible : false,
        proxyDrag : false,
        resizable : false
    });

    // 创建中间选择按钮容器
    var mainBtnEl = dlg.body.createChild({
        tag : 'table',
        style : 'width:100%;height:100%',
        cn : {
            tag : 'tr',
            cn : {
                tag : 'td',
                align : 'center',
                id : this.id + '-chooser-centercell'
            }
        }
    });

    // 设置布局
    dlg.layout = Ext.BorderLayout.create({
        west : {
            initialSize : 340,
            autoScroll : true,
            titlebar : true
        },
        center : {
            autoScroll : false,
            titlebar : false,
            panels : [new CP(mainBtnEl)]
        },
        east : {
            autoScroll : true,
            titlebar : true,
            tabPosition : 'top',
            initialSize : 340,
            panels : [
                new CP(this.id + 'chsTree', {
                    autoCreate : true,
                    title : '已选择项目:'
                })
            ]
        }

    },dlg.body.dom);

    dlg.addKeyListener(27, this.hide, this);
    dlg.addButton('确定', this.output, this);
    dlg.setDefaultButton(dlg.addButton('关闭', this.hide, this));
    this.dlg = dlg;
    this.layout = dlg.layout;
    this.west = this.layout.getRegion("west");
    this.center = this.layout.getRegion("center");
    this.east = this.layout.getRegion("east");

    // 修正选择按钮区域的样式
    this.center.el.addClass("chooser-bg");

    if(this.type=="role"){
        this.defaultChildType = 'subrole';
        this.panels.unshift({
            flag : 'rolePanel',
            text : '可选子角色',
            dataUrl : this.rolePanelUrl || '/role/tawSystemRoles.do?method=xGetSubRoleNodesFromArea',
            params : {roleId:this.roleId,flowId:this.flowId}
        });
        this.panels.push({
            flag:'search',
            text : '搜索子角色',
            dataUrl : this.searchPanelUrl || '/role/tawSystemRoles.do?method=xSearchSubRoleNodes',
            params : {roleId:this.roleId},
            hidden : true
        });
    }

    this.panels = this.panels.sort(eoms.panel.sort);

    
    
    // 通过panels创建备选面板
    Ext.each(this.panels,function(panelCfg){
        var newp;
        newp = eoms.panel.create(panelCfg);
        newp.on('dblclick', this.doChoose, this);
        //newp.on('click', this.doOpen, this);
        this.west.add(newp);

        if(panelCfg.hidden) this.west.hidePanel(newp);
        if(panelCfg.flag=="search") this.searchPanel = newp;
        if(panelCfg.flag=="rolePanel") this.rolePanel = newp;

    },this);
    this.west.showPanel(0);
    //this.west.getActivePanel().getSelection()
    // 创建右侧已选择树图
    this.category.tree = new xt.TreePanel(this.id + 'chsTree', {
        animate : true,
        enableDD : false,
        containerScroll : true,
        lines : false,
        rootVisible : false
    });
    this.category.root = new xt.TreeNode({nodeType : 'root', expanded:true});
    this.category.tree.setRootNode(this.category.root);
    this.category.tree.render();

    this.chosenCtxMenu = new Ext.menu.Menu();
    this.category.tree.on('contextmenu', this.onChosenCtx, this);
    this.category.tree.el.swallowEvent('contextmenu', true);
    this.category.tree.getSelectionModel().on('selectionchange', function(sm,node) {
        this.clearSelction('west');
        if(node)node.select();
    }, this);
    this.category.tree.on('dblclick', this.remove, this);
    //this.category.tree.on()

    this.categoryTreeSM = this.category.tree.getSelectionModel();

    // 根据category创建已选择项目分组的树图节点,创建选择按钮
    this.categoryNodes = {};
    var showCate = function(c){
        if(c.hidden === false)return;
        c.btn.show();
        c.node.getUI().show();
        c.node.attributes.hidden = false;
        c.hidden = false;
        c['hiddenEl'].dom.disabled = false;
    };
    var hideCate = function(c){
        if(c.hidden === true || this.category.root.childNodes.length<=1)return;
        c.btn.hide();
        c.node.getUI().hide();
        c.node.eachChild(function(node){this.remove(node)},this);
        c.node.attributes.hidden = true;
        c.hidden = true;
        c['hiddenEl'].dom.disabled = true;
    };
    var attrCate = function(c,attr,value){
        if(arguments.length==3){
            c.node.attributes[attr] = value;
        }
        else{
            return c.node.attributes[attr];
        }
    };
    Ext.each(this.category, function(item,index) {
        var node = this.createCNode(item);

        var cell = Ext.get(this.id + '-chooser-centercell');
        var btn = cell.createChild({
            tag : 'input',
            type : 'button',
            value : item.text,
            style : 'margin:20 0;display:block',
            cls : 'button'
        });
        btn.on('click', function() {
            this.doChoose(item.id)
        }, this);

        //chooser所属的页面form
        this.pageForm = btn.dom.form;

        item.node = node;
        item.btn = btn;
        item.index = index;
        item.show = showCate.createDelegate(this,[item]);
        item.hide = hideCate.createDelegate(this,[item]);
        item.attr = attrCate.createDelegate(this,[item],0);
        item.hidden = false;

        //根据category创建隐藏域
        var alt = item.allowBlank===false 
            ? "allowBlank:false" + (item.vtext ? ",vtext:'"+item.vtext+"'" : "")
            : "";
        item['hiddenEl'] = this.body.createChild({tag:'input',type:'hidden',alt:alt,name:item.id});
        item['hiddenEl_nodeType'] = this.body.createChild({tag:'input',type:'hidden',name:item.id+'Type'});
        item['hiddenEl_leaderId'] = this.body.createChild({tag:'input',type:'hidden',name:item.id+'Leader'});
        item['hiddenEl_JSON'] = this.body.createChild({tag:'input',type:'hidden',name:item.id+'JSON'});
    }, this);

    // 已选择项目确定后在父页面上的显示
    this.chosenView = new Ext.JsonView(
            this.id + '-chooser-show',
            '<div>{categoryName}:<div class="viewlistitem-{nodeType}" style="display:inline">{text} {info}</div></div>',
            {
                singleSelect : true,
                selectedClass : "",
                emptyText : '<div>未选择</div>',
                scope : this
            });
    this.chosenView.jsonData = [];
    this.chosenView.refresh();

    if(this.type=="role"){
        this.showFilter = true;
        if(this.showFilter)this.initFilterDlg(); //过滤条件窗口
        if(this.showLeader)this.initSetLeader(); //设置组长
    }

};
Ext.extend(Chooser, Ext.util.Observable, {
    show : function() {
        this.dlg.show();
    },
    hide : function() {
        this.dlg.hide();
        if(this.showFilter){
            this.filterDlg.hide();
        }
    },
    /**
     * 隐藏按钮
     */
    hideBtn : function(){
        this.body.down("input[type='button']").setDisplayed(false);
    },
    disable : function(){
        eoms.form.disableArea(this.id,true);
    },
    enable : function(){
        eoms.form.enableArea(this.id,true);
    },
    clearSelction : function(region){
        var ap = this.layout.getRegion(region).getActivePanel();
        if(ap.clearSelections) ap.clearSelections();
    },
    /**
     * 设置面板的树图url地址，并刷新
     * @param {String} url 树图action地址
     * @param {String} rootId 树图根节点id,默认为-1
     * @param {Number} index 面板序数，缺省为第一个面板
     */
    setDataUrl : function(url,rootId,index){
        index = index || 0;
        rootId = rootId || -1;
        var tree = this.west.getPanel(index).tree;
        var treeLoader = tree.getLoader();
        var root = tree.getRootNode();
        if((eoms.appPath+url)==treeLoader.dataUrl && root.id==rootId){
            return;
        }
        treeLoader.dataUrl = eoms.appPath + url;
        this.reset();
        tree.getRootNode().id = rootId;
        tree.getRootNode().reload();
    },
    // 已选择项目的右键菜单
    onChosenCtx : function(node, e) {
        node.select();
        //设置组长
        if (this.showLeader && node.attributes.nodeType == 'user' && node.parentNode.attributes.nodeType == 'subrole') {
            this.chosenCtxMenu.showAt(e.getXY());
        }
    },
    getKey : function(o){
        return o.nodeType + ':' + o.id;
    },
    createCNode : function(c){
        var nodeText, childType = c.childType || this.defaultChildType, childTypeText = '';

        if(childType==''){ // 不限节点类型
            nodeText = c.text;
        }
        else{
            var childTypeArr = [];
            Ext.each(childType.split(","),function(item){
                childTypeArr.push(Local.nodeType[item]);
            })
            childTypeText = childTypeArr.join("或");
            nodeText = c.text + ' (只能选择' + childTypeText + ')';
        }

        var node =  new Ext.tree.TreeNode({
            categoryId : c.id,
            categoryName : c.text,
            text : nodeText,
            iconCls : 'folder',
            cls : 'cmp',
            expanded : true,
            nodeType : 'category',
            childType : childType,
            childTypeText : childTypeText,
            limit : c.limit || 1
        });

        this.category.root.appendChild(node);
        this.categoryNodes[c.id] = node;
        return node;
    },
    /**
     * 执行选择动作
     * @param {String} categoryId 分类id
     */
    doChoose : function(categoryId,e){
        if(this.categoryTreeSM.selNode) {
            this.remove();
            return;
        }
        var nodes = this.west.getActivePanel().getSelection();
        if(!nodes || nodes.length==0) return;

        var parentNode = nodes[0];
        if(e && e.shiftKey && !parentNode.attributes.leaf){ //按住shift双击时，全选该节点的子节点
            var selModel = parentNode.getOwnerTree().getSelectionModel();
            parentNode.expand(false,false,function(node){
                node.eachChild(function(child){
                    selModel.select(child,null,true);
                });
            });
            parentNode.unselect();
            return;
        }

        var c = this.categoryNodes[categoryId] || this.category.root.firstChild;
        var cAttr = c.attributes;
        if(cAttr.hidden)return;
        if(cAttr.limit !== 'none' && cAttr.limit !== -1 && c.childNodes.length >= parseInt(cAttr.limit)){
            alert(cAttr.categoryName+"中最多可以选择"+cAttr.limit+"个");
            return;
        }

        this.validinfo = "";
        Ext.each(nodes,function(node){
            var data = Ext.apply({},node.attributes,node);
            if(this.beforeChoose(data,c)){
                data['categoryId'] = cAttr.categoryId;
                this.add(data, c, true);
                if(node.expanded){
        			node.collapse(false, false);
        		}else{
        			node.expand(false, false);
        		}
            };
        }, this);
        if(this.validinfo != "") alert(this.validinfo);
        this.clearSelction('west');
        this.refresh();
    },
    
    /**
     * 选择节点
     */
    doOpen : function(categoryId, e){
    	if(this.categoryTreeSM.selNode) {
            this.remove();
            return;
        }

        var nodes = this.west.getActivePanel().getSelection();
        if(!nodes || nodes.length==0) return;

        var parentNode = nodes[0];
        Ext.each(nodes,function(node){
            var data = Ext.apply({},node.attributes,node);
            if(node.expanded){
    			node.collapse(false, false);
    		}else{
    			node.expand(false, false);
    		};
        }, this);
    },
    /**
     * 选择前的验证
     * @param {Object} data 一个选择节点的数据
     * @param {Node} c 目标分类节点
     * @return {Boolean}
     */
    beforeChoose : function(data, c) {
        if (data.nodeType=='root') return false;

        if (this.chosen.containsKey(this.getKey(data))) {
            this.validinfo += data.text+': 该项目已被选择了。\n';
            return false;
        }

        var cAttr = c.attributes;
        if (cAttr.childType != '' && !cAttr.childType.hasSubString(data.nodeType,",")) {
            this.validinfo += data.text+": "+cAttr.categoryName+"中只能选择"+cAttr.childTypeText+"\n";
            return false;
        }

        if(cAttr.limit !== 'none' && cAttr.limit !== -1 && c.childNodes.length >= parseInt(cAttr.limit)){
            this.validinfo += data.text+": "+cAttr.categoryName+"中最多可以选择"+cAttr.limit+"个\n";
            return false;
        }

        return true;
    },
    /**
     * 添加已选节点数据
     * @param {Object/Array} nodeInfos 一个节点的信息或多个节点的信息数组
     * @param {Ext TreeNode} defaultCtgNode 缺省的categoryNode
     * @param {Boolean} isAsync 是否创建动态节点
     */
    add : function(nodeInfos, defaultCtgNode,isAsync){
        Ext.each([].concat(nodeInfos),function(nodeInfo){
            if(!this.categoryNodes[nodeInfo.categoryId] && !defaultCtgNode){
                return;
            }
            var cnode = this.categoryNodes[nodeInfo.categoryId] || defaultCtgNode;
            nodeInfo.categoryName = cnode.attributes.categoryName;
            nodeInfo.iconCls = nodeInfo.iconCls || nodeInfo.nodeType;
            var newNode = (isAsync && !nodeInfo.leaf) ? new Ext.tree.AsyncTreeNode(nodeInfo) : new Ext.tree.TreeNode(nodeInfo);
            if(nodeInfo.user){
                Ext.each(nodeInfo.user,function(child){
                    newNode.appendChild(new Ext.tree.TreeNode(child));
                });
            }
            newNode.expanded = false;
            cnode.appendChild(newNode);
            cnode.expand();
            this.chosen.add(this.getKey(nodeInfo), nodeInfo);
        },this);
    },
    remove : function() {
        var node = this.categoryTreeSM.clearSelections();
        if (node.parentNode.attributes.nodeType == 'category') {
            this.chosen.removeKey(this.getKey(node.attributes));
            node.parentNode.removeChild(node);
            this.refresh();
        }
    },
    refresh : function() {
    },
    /**
     * 将选中的结果更新到页面上，并把选中的值填入隐藏域
     */
    update : function(){
        this.chosenView.jsonData = [];
        this.chosen.each(function(nodeInfo) {
            //如果未指定组长，则设定leaderId为选中对象的id
            if(!nodeInfo.leaderId || nodeInfo.leaderId==""){
                nodeInfo.leaderId = nodeInfo.id;
            }
            //如果有leaderName，则info为组长信息
            nodeInfo.info = nodeInfo.leaderName ? '(组长:'+nodeInfo.leaderName+')' : '';

            this.chosenView.jsonData.push(nodeInfo);
        },this);

        this.chosenView.refresh();

        function toStr(o,isShowLeader){
            var str = '{'
                +'id:\''+o.id+'\''
                +',nodeType:\''+o.nodeType+'\''
                +',categoryId:\''+o.categoryId+'\''
            if(o.nodeType=='subrole' && isShowLeader && o.leaderId){
                str += ',leaderId:\''+o.leaderId+'\''+',leaderName:\''+o.leaderName+'\'';
            }
            str += '}';
            return str;
        }

        var totalJsonData =[];
        this.chosen.each(function(nodeInfo) {
            if(!nodeInfo.leaderId || nodeInfo.leaderId==""){
                nodeInfo.leaderId = nodeInfo.id;
            }
            nodeInfo.leaderName = nodeInfo.leaderName ? '(组长:'+nodeInfo.leaderName+')' : '';
            totalJsonData.push(toStr(nodeInfo,this.showLeader));
        },this);

        Ext.each(this.category, function(c) {
            var arrId = [],arrNodeType = [], arrLeaderId = [], arrJSON = [];
            this.chosen.each(function(nodeInfo) {
                if (nodeInfo.categoryId == c.id) {
                    arrId.push(nodeInfo.id);
                    arrNodeType.push(nodeInfo.nodeType);
                    arrLeaderId.push(nodeInfo.leaderId);
                    if(this.returnJSON){
                        arrJSON.push(toStr(nodeInfo,this.showLeader));
                    }
                }
            }, this);

            c['hiddenEl'].dom.value = arrId.toString();
            c['hiddenEl_nodeType'].dom.value = arrNodeType.toString();
            c['hiddenEl_leaderId'].dom.value = arrLeaderId.toString();
            if(this.returnJSON){
                c['hiddenEl_JSON'].dom.value = "[" + arrJSON.toString() + "]";
            }
        }, this);
        this.totalJSON.dom.value =  "[" + totalJsonData.toString() + "]";
    },
    /**
     * 向父页面输出选择结果
     * @private
     */
    output : function() {
        var valid = true;
        this.chosen.each(function(i){
        },this);

        if(!valid) return;

        this.update();
        this.hide();
    },
    //初始化设置组长
    initSetLeader : function(){
        this.chosenCtxMenu.add({
            id : 'newnode',
            text : '指定组长',
            cls : 'new-mi',
            scope : this,
            handler : this.setLeader
        });

        //this.chosenView.leaderStore 在view中保存已设置的组长信息：
        //{
        //   '子角色1id':{'id':'组长id','name':'组长name'},
        //   '子角色2id':{'id':'组长id','name':'组长name'},
        //   ...
        //}
        this.chosenView.leaderStore = {};
        this.chosenView.prepareData = function(data) {
            switch (data.nodeType) {
                case 'subrole':
                    Ext.applyIf(data,this.leaderStore[data.id]);
                    break;
                default:
                    break;
            }

            return data;
        };
    },
    // 设置组长
    setLeader : function() {
        var userNode = this.categoryTreeSM.selNode;
        var subroleNode = userNode.parentNode;
        var subroleNodeId = subroleNode.attributes.id;
        var key = this.getKey(subroleNode.attributes);

        if(userNode.attributes.nodeType!='user' || subroleNode.attributes.nodeType!='subrole')return;

        this.chosenView.leaderStore[subroleNodeId] = {
            leaderId : userNode.attributes.id,
            leaderName : userNode.attributes.name
        };

        Ext.apply(this.chosen.get(key),this.chosenView.leaderStore[subroleNodeId]);

        //刷新人员列表组长状态
        subroleNode.eachChild(function(node) {
            node.ui.iconNode.className ="x-tree-node-icon user";
        });
        userNode.eachChild(function(node) {
            node.ui.iconNode.className ="x-tree-node-icon user";
        });
        //userNode.ui.iconNode.className ="x-tree-node-icon leader";
    },
    /**
     * type=role 模式下重新设置大角色roleId,刷新可选子角色
     * @param {Number} roleId
     */
    setRoleId : function(roleId){
        if(this.type != 'role' || this.roleId==roleId)return;
        this.roleId = roleId;
        if(this.rolePanel){
            this.rolePanel.tree.getLoader().baseParams.roleId = roleId;
            this.rolePanel.tree.root.reload();
        }
        if(this.searchPanel){
            this.searchPanel.tree.getLoader().baseParams.roleId = roleId;
        }
        Ext.Ajax.request({
            method : 'post',
            url : eoms.appPath+"/role/tawSystemRoles.do?method=resetChooserRoleId",
            success : function(x) {
                  var obj = eoms.JSONDecode(x.responseText);
                Ext.getDom(this.id+"-chooser-deptList").innerHTML = obj.filterHTML;
            },
            scope:this,
            params : "chooserId=" + this.id + "&roleId=" + this.roleId
        });
        this.reset();
    },
    reset : function(){
        if(this.searchPanel){
            this.west.hidePanel(this.searchPanel);
        }
        this.west.showPanel(0);
        Ext.each(this.category.root.childNodes,function(cnode){
            while(cnode.firstChild){
                cnode.removeChild(cnode.firstChild);
            }
        });
        this.chosen.clear();
        this.chosenView.jsonData = [];
        this.chosenView.refresh();
        Ext.select("input[type=hidden]",false,this.id).each(function(f){
            f.dom.value="";
        });
        this.refresh();
    },
    // 初始化过滤器
    initFilterDlg : function() {
        var filterBtn = this.west.createToolButton({cls:'x-layout-search',fn:this.showFilterDlg,scope:this,text:'搜索'});

        if (!this.filterDlg) {
            this.filterDlg = new Ext.BasicDialog(this.id + "-chooser-filter-dlg", {
                shadow : false,
                modal : false,
                autoTabs : true,
                resizable : true,
                collapsible : false,
                width : 400,
                height : 450
            });
            this.filterDlg.addKeyListener(27, this.filterDlg.hide,
                    this.filterDlg);
            this.filterDlg.addButton('查找', this.doFilter, this);
            this.filterDlg.addButton('关闭', this.filterDlg.hide, this.filterDlg);
        }
    },
    // 显示过滤器窗口
    showFilterDlg : function() {
        this.filterDlg.show();
        this.filterDlg.toFront();
    },
    // 执行查询
    doFilter : function() {
        var p = {};
        Ext.select("input,select",false,this.id + "-chooser-filter-dlg").each(function(f){
            if(f.dom.type=="checkbox" && f.dom.checked){
                p[f.dom.name] = true;
            }
            else if(f.dom.tagName=="SELECT"){
                p[f.dom.name] = f.dom.value;
            }
        });
        this.west.showPanel(this.searchPanel);
        var st = this.searchPanel.tree;
        Ext.apply(st.getLoader().baseParams,p);
        st.root.reload();
        this.filterDlg.hide();
    }

});

/**
 * Ext.form.VTypes
 */
Ext.form.VTypes['numberVal'] = /^[0-9]*$/;
Ext.form.VTypes['numberMask'] = /^[0-9]*$/;
Ext.form.VTypes['numberText'] = '请输入数字';
Ext.form.VTypes['number'] = function(v) {
    return Ext.form.VTypes['numberVal'].test(v);
}

Ext.form.VTypes['lessThenText'] = '必须小于';
Ext.form.VTypes['lessThen'] = function(v,field){
  if(!Ext.getDom(field.link)){
      return;
  }
  var lv = Ext.getDom(field.link).value;
  var linkField = field.valider.fields.get(field.link);

  if(linkField){
    Ext.form.VTypes['lessThenText'] = field.vtext;
      v < lv ? linkField.clearInvalid() : linkField.markInvalid(linkField.vtext);
  }

  return v < lv;
};
Ext.form.VTypes['moreThenText'] = '必须大于';
Ext.form.VTypes['moreThen'] = function(v,field){
  if(!Ext.getDom(field.link)){
      return;
  }
  var lv = Ext.getDom(field.link).value;
  var linkField = field.valider.fields.get(field.link);

  if(linkField){
    Ext.form.VTypes['moreThenText'] = field.vtext;
      v > lv ? linkField.clearInvalid() : linkField.markInvalid(linkField.vtext);
  }

  return v > lv;
};
/**
 * 唯一性验证
 */
Ext.form.VTypes['uniqueText'] = '该名称已存在，请换一个';
Ext.form.VTypes['delText'] = '该名称已被管理员删除，请联系管理员';
Ext.form.VTypes['unique'] = function(v, field) {
    try {
        if (v == AppSimpleTree.savedFormData[field.name]) {
            return true;
        }
    } catch (e) {
    };

    Ext.lib.Ajax.request("post", eoms.appPath
            + "/sysuser/tawSystemUsers.do?method=checkUnique", {
        success : function(x) {
                if (x.responseText == "true") {
                    field.markInvalid(Ext.form.VTypes['uniqueText']);
                } else if(x.responseText == "falsedelete"){
                    field.markInvalid(Ext.form.VTypes['delText']);
                }
                else{
                    field.clearInvalid();
                }
            }
        }, "name=" + field.id + "&value=" + v);
    return true;
}

/**
 * 处理详细信息的显示和隐藏
 * @cfg {String} container 容器ID
 * @cfg {String} handleEl 标题元素的css查询字符串
 * @cfg {Boolean} showFirst 是否要打开第一项,默认为true
 * @example 例子
 *         switcher = new detailSwitcher(); 
 *         switcher.init({ container:'history',handleEl:'div.history-item-title'});
 */
var detailSwitcher = function() {
    var closedIcon = eoms.appPath + '/images/icons/closed.gif';
    var openedIcon = eoms.appPath + '/images/icons/opened.gif';
    var container, handleEl, group;
    var showFirst, allHide = true;
    return {
        init : function(cfg) {
            container = cfg.container;
            handleEl = cfg.handleEl;
            showFirst = cfg.showFirst===false ? false : true;
            group = Ext.select(handleEl, false, container);
            group.on('click', function(e,item){
                this.toggleItem(item);
            },this);

            if(showFirst){
                this.toggleIndex(0,true);
            }
        },
        toggle : function() {
            group.each(function(item) {
                this.toggleItem(item,allHide);
            }, this);
            allHide = !allHide;
        },
        toggleIndex : function(index,unhide){
            var item = group.elements[index];
            if(!item)return;
            this.toggleItem(item,unhide);
        },
        toggleItem : function(item,unhide){
            if(!item)return;
            if(item.tagName=="IMG") item = item.parentNode;
            item = Ext.get(item);
            var detail = Ext.get(item.getNextSibling());
            var img = item.down('img.switchIcon').dom;
            unhide == null ? detail.toggleClass('hide')
                :(unhide === true ? detail.removeClass('hide') : detail.addClass('hide'));
            img.src = detail.hasClass('hide') ? closedIcon : openedIcon;
        }
    };
}

/**
 * Ext.data.JsonStore.getJSON
 */
Ext.data.JsonStore.prototype.getJSON = function() {
    var _tempArr = [];
    if (this.data.items.length > 0) {
        this.each(function(r) {
            _tempArr.push(Ext.util.JSON.encode(r.data));
        });
        return "[" + _tempArr.toString() + "]";
    } else
        return "[]";
};

/**
 * Ext.data.JsonStore.getJSONData
 */
Ext.data.JsonStore.prototype.getJSONData = function() {
    var _tempArr = [];
    if (this.data.items.length > 0) {
        this.each(function(r) {
            _tempArr.push(r.data);
        });
    }
    return _tempArr;
};
