/**
 * 快速查询面板
 * <p>在xbox或chooser组件中使用type:'query'进行调用，
 * 通过用户输入的关键字，即时进行模糊查询，作为树图的一种补充。</p>
 * @class 
 * @extends Ext.ContentPanel
 * 相关的设置项：
 * @cfg {String} [dataUrl] 查询地址，默认为查询人员
 * @cfg {String} [qparam=q] 查询关键字参数名，默认为"q"
 * @cfg {String} [tips=查询人员名称...] 输入框中的提示文字
 * @example
 *  可在eoms:xbox标签的panels属性中使用type:'query'来调用这个查询面板
 *  <eoms:xbox 
 *      ...
 *      panels="[{type:'query',pos:'first'}]"
 *  />
 * 
 *  后台返回的标准JSON格式：
 *  [
 *      ["username0","userid0"],
 *      ["username1","userid1"],
 *      ...
 *  ]
 */
eoms.panel.Query = function(el, config) {
    config = Ext.apply({
        dataUrl : eoms.appPath + '/sysuser/tawSystemUsers.do?method=xquery&tpl=view-query',
        qparam : 'q',
        tips : '查询人员名称...',
        title : config.text || '查询人员',
        adjustments : [0,-30],
        fitToFrame : true,
        fitContainer : true,
        autoScroll : true,
        autoCreate : true,
        preInput : '',
        timer : false,
        cache : [],
        cacheSize : 0,
        maxCacheSize : 100,
        datatype : 'user'
    },config);
    
    if(/^\//.test(config.dataUrl) && config.dataUrl.indexOf(eoms.appPath) != 0){
        config.dataUrl = eoms.appPath + config.dataUrl;
    }
    
    eoms.panel.Query.superclass.constructor.call(this, el, config);
    
    this.addEvents({
        /**
         * @event 当双击项目时
         */
        'dblclick' : true
    });

    this.el.dom.style.overflow = "hidden";
    var qEl = this.el.createChild({
        tag:'input',type:'text',maxlength:'30',autocomplete:'off',
        cls:'text',style:'width:237px;height:26px;margin:5px;padding-top:4px;padding-left:4px;'
    });   
    qEl.dom.value = qEl.dom.title = this.tips;
    qEl.on({
        'keyup' : this.processKey,
        'keydown' : function(event,el){if(event.getKey()==event.ENTER)event.stopEvent();}, //阻止表单提交
        'focus' : function(event,el){ if(el.value==el.title) el.value=""; },      
        'blur' : function(event,el){ if(el.value=='') el.value = el.title; },      
        scope:this
    });
    this.qEl = qEl;
    
    this.resizeEl = this.el.createChild({cls:'viewer-list',style:'padding-left:5px;overflow-y:auto'});
    this.view = new Ext.JsonView(this.resizeEl, this.getViewTpl(), {
        multiSelect : true,
        emptyText : '<div>没有找到查询结果</div>'
    });
    
    this.updater = this.view.el.getUpdateManager();
    
    this.view.on({
        'dblclick' : this.onDblClick,
        'load' : function(vw, data, x){
            if(data && data.length) this.addToCache(vw.q, data, data.length);
        },
        scope:this
    });
};

Ext.extend(eoms.panel.Query, Ext.ContentPanel,{
    /**
     * 查询结果的view模板
     * @override
     */
    getViewTpl : function(){
        return '<div class="viewlistitem-'+this.datatype+'" unselectable="on">{0}</div>';
    },
    
    /**
     * 得到选择的数据
     * @public
     */
    getSelection : function(){
        var nodes = this.view.getSelectedNodes();
        var data = [], d;
        for(var i = 0, len = nodes.length; i < len; i++){
            d = this.view.getNodeData(nodes[i]);
            data.push(this.getData(d));
        }
        return data;
    },
    
    /**
     * 清除所选数据
     * @public 
     */
    clearSelections : function(){
        this.view.clearSelections();
    },
    
    /**
     * 格式化所需的数据
     * @override
     * @param {Object} item 选择的一个原始数据
     * @return {Object} 格式化后的数据
     */
    getData : function(item){
        return {
            id : item[1],
            text : item[0],
            nodeType : this.datatype,
            leaf : 1
        };
    },
    
    /**
     * 查询数据上双击时执行的函数
     * @param {View} vw
     * @param {Number} index
     * @param {Dom} node
     * @param {Evnet} e
     */
    onDblClick : function(vw, index, node, e){
        vw.select(node);
        var data = vw.getNodeData(node);
        this.fireEvent("dblclick");
    },
    
    //private 键盘事件处理
    processKey : function(e,el) {
        if (e.isNavKeyPress()){
            e.stopEvent();
            switch(e.getKey()) {
                case e.ENTER:
                    this.start();
                    break;
            }
        }
        else if (el.value != this.preInput) {
            this.updater.abort();
            if (this.timer) clearTimeout(this.timer);
            this.timer = this.start.defer(200,this);
            this.preInput = el.value;
        }
    },
    
    //private 启动查询
    start : function (){
        var q = this.qEl.dom.value.trim();
        if(q==""){
            this.view.jsonData = [];
            this.view.refresh();
            return;
        }
        q = encodeURI(q);
        this.view.q = q;
        var cached = this.checkCache(q);
        if(cached){
            this.view.jsonData = cached.data;
            this.view.refresh();
            return;
        }
        this.view.load({
            url: this.dataUrl,
            params: this.qparam + "=" + q,
            text: "搜索中..."
        });
    },
    
    //private 是否有cache,有则返回cache记录
    checkCache : function(q) {
        for (var i = 0, len = this.cache.length; i < len; i++)
            if (this.cache[i]['q'] == q) {
                this.cache.unshift(this.cache.splice(i, 1)[0]);
                return this.cache[0];
            }
        return false;     
    },
    
    //private 添加到cache
    addToCache : function(q, data, size){
        while (this.cache.length && (this.cacheSize + size > this.maxCacheSize)) {
            var cached = this.cache.pop();
            this.cacheSize -= cached['size'];
        }
            
        this.cache.push({
            q: q,
            size: size,
            data: data
        });
                
        this.cacheSize += size;
    }
});

eoms.panel.query = eoms.panel.Query;