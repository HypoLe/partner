/**
 * @class eoms.sheet 工单相关函数
 * @version 0.1
 */
eoms.Sheet = function() {
	return {
		/**
		 * 设置一个下拉框为ajax页面载入器
		 * 
		 * @param {String}
		 *            selectId 下拉框id或下拉框元素
		 * @param {String}
		 *            divId 载入页面的容器id
		 * @param {Function}
		 *            fn 载入后执行的函数
		 */
		setPageLoader : function(select, divId, fn) {
			var sel = Ext.get(select);
			if (!sel)
				return;
			sel.on("change", function(e) {
				sel.dom.disabled = true;
				sel.blur();
				document.body.focus(); // for IE6

				function onUpdate() {
					window.scrollTo(sel.getX(), sel.getY() - 30);
					sel.dom.disabled = false;
					if (typeof fn == "function")
						fn();
				}

				eoms.util.appendPage(divId, sel.dom.value, true, onUpdate);
			});

		},
		/**
		 * 保存工单模板
		 * @param {HTML　Element}
		 * 		el 按钮对象(this)
		 * @param {Object}
		 * 		validater eoms.form.Validation 对象(v)
		 * @param {Boolean}
		 * 		onManage 是否在模板编辑模式
		 */
		saveTemplate : function(el, validater, onManage) {
			var frm = el.form;
			if (!frm)
				return;
			validater.passing = true;
			if (validater.check()) {
				if (confirm("确认保存模板吗？")) {
					if (onManage) {
						frm.action = "${app}/sheet/commonfault/commonfault.do?method=saveTemplate";
						frm.submit();
					} else {
						Ext.Ajax.request({
							form : frm,
							method : "post",
							url : "commonfault.do?method=saveTemplate",
							success : function() {
								alert("保存模板成功！");
							}
						});
					}
				}
			}
			validater.passing = false;
		},
		/**
		 * 删除工单模板
		 * @param {HTML　Element}
		 * 　	el 按钮对象(this)
		 */
		removeTemplate : function(el) {
			if(!el.form)return;
			if (confirm('确认要删除此模板吗？')) {
				el.form.action = "${app}/sheet/commonfault/commonfault.do?method=removeTemplate";
				el.form.submit();
			}
		}

	}
}();