/**
 * 搜索
 * @version 0.1.3 090907
 */

var AppSearch = function() {
	// private
	var config, cm, ds, grid;

	// public
	return {
		init : function(cfg) {
			config = cfg;
			cm = new Ext.grid.ColumnModel(config.cm);

			ds = new Ext.data.JsonStore({
				url : config.url,
				root : 'rows',
				fields : config.fields
			});

			gridSM = new Ext.grid.RowSelectionModel({
				singleSelect : true
			});
			grid = new Ext.grid.Grid('searchGrid', {
				ds : ds,
				cm : cm,
				enableColLock : true,
				autoWidth : true,
				selModel : gridSM
			});

			var layout = Ext.BorderLayout.create({
				center : {
					margins : {
						left : 3,
						top : 3,
						right : 3,
						bottom : 3
					},
					panels : [new Ext.GridPanel(grid)]
				}
			}, 'searchPanel');

			grid.on('dblclick', this.onDblClick, AppSimpleTree);
			grid.on('contextmenu', this.onCtxMenu, this);
			grid.getGridEl().swallowEvent('contextmenu', true);
			grid.render();
		},
		load : function() {
			AppSimpleTree.openPanelOfRegion('center', 'searchPanel');
			var params = {};
			for (var p in config.paramMapping) {
				params[p] = Ext.getDom(config.paramMapping[p]).value || '';
			}
			ds.load({params : params});
		},
        /**
         * 菜单权限处理同AppSimpleTree.js
         */
		onCtxMenu : function(e) {
            var data = gridSM.getSelected().data;
			AppSimpleTree.selected = data;
			var menu = AppSimpleTree.treeCtxMenu;
			var mis = menu.items;
			var mis_hidden = true;
	        mis.each(function(mi){
	            var idfix = (mi.id == "newnode"
	                ? "Child"
	                : (mi.id == "edtnode"
	                        ? "Edit"
	                        : (mi.id == "delnode"
	                                ? "Delete"
	                                : mi.id)));
	            var allow = data['allow' + idfix];
	            if(mi.nodeTypeMapping){
	                mi[mi.nodeTypeMapping == data.nodeType ? 'show' : 'hide']();
	            }
	            mi[allow ? 'show' : 'hide']();
	            if(!mi.hidden) mis_hidden = false;
	        });
            if(!mis_hidden){
                menu.showAt(e.getXY());
            }
			
		},
		onDblClick : function(e) {
            var data = gridSM.getSelected().data;
            data = Ext.apply(data, config.baseAttr || {});
            AppSimpleTree.view(data);
		}
	}
};
