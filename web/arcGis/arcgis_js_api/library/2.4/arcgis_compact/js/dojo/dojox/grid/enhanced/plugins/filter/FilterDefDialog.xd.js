/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.grid.enhanced.plugins.filter.FilterDefDialog"],["require","dijit.dijit"],["require","dijit.Tooltip"],["require","dojox.grid.enhanced.plugins.Dialog"],["require","dijit.form.ComboBox"],["require","dijit.form.Select"],["require","dijit.form.TextBox"],["require","dijit.form.CheckBox"],["require","dijit.form.NumberTextBox"],["require","dijit.form.DateTextBox"],["require","dijit.form.TimeTextBox"],["require","dijit.form.Button"],["require","dijit.layout.AccordionContainer"],["require","dijit.layout.ContentPane"],["require","dojo.date.locale"],["require","dojo.string"],["require","dojox.grid.enhanced.plugins.filter.FilterBuilder"],["require","dojox.grid.cells.dijit"],["require","dojox.html.ellipsis"],["require","dojox.html.metrics"],["require","dojo.window"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.grid.enhanced.plugins.filter.FilterDefDialog"]){_4._hasResource["dojox.grid.enhanced.plugins.filter.FilterDefDialog"]=true;_4.provide("dojox.grid.enhanced.plugins.filter.FilterDefDialog");_4.require("dijit.dijit");_4.require("dijit.Tooltip");_4.require("dojox.grid.enhanced.plugins.Dialog");_4.require("dijit.form.ComboBox");_4.require("dijit.form.Select");_4.require("dijit.form.TextBox");_4.require("dijit.form.CheckBox");_4.require("dijit.form.NumberTextBox");_4.require("dijit.form.DateTextBox");_4.require("dijit.form.TimeTextBox");_4.require("dijit.form.Button");_4.require("dijit.layout.AccordionContainer");_4.require("dijit.layout.ContentPane");_4.require("dojo.date.locale");_4.require("dojo.string");_4.require("dojox.grid.enhanced.plugins.filter.FilterBuilder");_4.require("dojox.grid.cells.dijit");_4.require("dojox.html.ellipsis");_4.require("dojox.html.metrics");_4.require("dojo.window");(function(){var _7=_6.grid.enhanced.plugins.filter,_8={relSelect:60,accordionTitle:70,removeCBoxBtn:-1,colSelect:90,condSelect:95,valueBox:10,addCBoxBtn:20,filterBtn:30,clearBtn:40,cancelBtn:50};_4.declare("dojox.grid.enhanced.plugins.filter.FilterDefDialog",null,{curColIdx:-1,_relOpCls:"logicall",_savedCriterias:null,plugin:null,constructor:function(_9){var _a=this.plugin=_9.plugin;this.builder=new _7.FilterBuilder();this._setupData();this._cboxes=[];this.defaultType=_a.args.defaultType||"string";(this.filterDefPane=new _7.FilterDefPane({"dlg":this})).startup();(this._defPane=new _6.grid.enhanced.plugins.Dialog({"refNode":this.plugin.grid.domNode,"title":_a.nls.filterDefDialogTitle,"class":"dojoxGridFDTitlePane","iconClass":"dojoxGridFDPaneIcon","content":this.filterDefPane})).startup();this._defPane.connect(_a.grid.layer("filter"),"filterDef",_4.hitch(this,"_onSetFilter"));_a.grid.setFilter=_4.hitch(this,"setFilter");_a.grid.getFilter=_4.hitch(this,"getFilter");_a.grid.getFilterRelation=_4.hitch(this,function(){return this._relOpCls;});_a.connect(_a.grid.layout,"moveColumn",_4.hitch(this,"onMoveColumn"));},onMoveColumn:function(_b,_c,_d,_e,_f){if(this._savedCriterias&&_d!=_e){if(_f){--_e;}var min=_d<_e?_d:_e;var max=_d<_e?_e:_d;var dir=_e>min?1:-1;_4.forEach(this._savedCriterias,function(sc){var idx=parseInt(sc.column,10);if(!isNaN(idx)&&idx>=min&&idx<=max){sc.column=String(idx==_d?idx+(max-min)*dir:idx-dir);}});}},destroy:function(){this._defPane.destroyRecursive();this._defPane=null;this.filterDefPane=null;this.builder=null;this._dataTypeMap=null;this._cboxes=null;var g=this.plugin.grid;g.setFilter=null;g.getFilter=null;g.getFilterRelation=null;this.plugin=null;},_setupData:function(){var nls=this.plugin.nls;this._dataTypeMap={"number":{valueBoxCls:{dft:_5.form.NumberTextBox},conditions:[{label:nls.conditionEqual,value:"equalto",selected:true},{label:nls.conditionNotEqual,value:"notequalto"},{label:nls.conditionLess,value:"lessthan"},{label:nls.conditionLessEqual,value:"lessthanorequalto"},{label:nls.conditionLarger,value:"largerthan"},{label:nls.conditionLargerEqual,value:"largerthanorequalto"},{label:nls.conditionIsEmpty,value:"isempty"}]},"string":{valueBoxCls:{dft:_5.form.TextBox,ac:_7.UniqueComboBox},conditions:[{label:nls.conditionContains,value:"contains",selected:true},{label:nls.conditionIs,value:"equalto"},{label:nls.conditionStartsWith,value:"startswith"},{label:nls.conditionEndWith,value:"endswith"},{label:nls.conditionNotContain,value:"notcontains"},{label:nls.conditionIsNot,value:"notequalto"},{label:nls.conditionNotStartWith,value:"notstartswith"},{label:nls.conditionNotEndWith,value:"notendswith"},{label:nls.conditionIsEmpty,value:"isempty"}]},"date":{valueBoxCls:{dft:_5.form.DateTextBox},conditions:[{label:nls.conditionIs,value:"equalto",selected:true},{label:nls.conditionBefore,value:"lessthan"},{label:nls.conditionAfter,value:"largerthan"},{label:nls.conditionRange,value:"range"},{label:nls.conditionIsEmpty,value:"isempty"}]},"time":{valueBoxCls:{dft:_5.form.TimeTextBox},conditions:[{label:nls.conditionIs,value:"equalto",selected:true},{label:nls.conditionBefore,value:"lessthan"},{label:nls.conditionAfter,value:"largerthan"},{label:nls.conditionRange,value:"range"},{label:nls.conditionIsEmpty,value:"isempty"}]},"boolean":{valueBoxCls:{dft:_7.BooleanValueBox},conditions:[{label:nls.conditionIs,value:"equalto",selected:true},{label:nls.conditionIsEmpty,value:"isempty"}]}};},setFilter:function(_10,_11){_10=_10||[];if(!_4.isArray(_10)){_10=[_10];}var _12=function(){if(_10.length){this._savedCriterias=_4.map(_10,function(_13){var _14=_13.type||this.defaultType;return {"type":_14,"column":String(_13.column),"condition":_13.condition,"value":_13.value,"colTxt":this.getColumnLabelByValue(String(_13.column)),"condTxt":this.getConditionLabelByValue(_14,_13.condition),"formattedVal":_13.formattedVal||_13.value};},this);this._criteriasChanged=true;if(_11==="logicall"||_11==="logicany"){this._relOpCls=_11;}var _15=_4.map(_10,this.getExprForCriteria,this);_15=this.builder.buildExpression(_15.length==1?_15[0]:{"op":this._relOpCls,"data":_15});this.plugin.grid.layer("filter").filterDef(_15);this.plugin.filterBar.toggleClearFilterBtn(false);}this._closeDlgAndUpdateGrid();};if(this._savedCriterias){this._clearWithoutRefresh=true;var _16=_4.connect(this,"clearFilter",this,function(){_4.disconnect(_16);this._clearWithoutRefresh=false;_12.apply(this);});this.onClearFilter();}else{_12.apply(this);}},getFilter:function(){return _4.clone(this._savedCriterias)||[];},getColumnLabelByValue:function(v){var nls=this.plugin.nls;if(v.toLowerCase()=="anycolumn"){return nls["anyColumnOption"];}else{var _17=this.plugin.grid.layout.cells[parseInt(v,10)];return _17?(_17.name||_17.field):"";}},getConditionLabelByValue:function(_18,c){var _19=this._dataTypeMap[_18].conditions;for(var i=_19.length-1;i>=0;--i){var _1a=_19[i];if(_1a.value==c.toLowerCase()){return _1a.label;}}return "";},addCriteriaBoxes:function(cnt){if(typeof cnt!="number"||cnt<=0){return;}var cbs=this._cboxes,cc=this.filterDefPane.cboxContainer,_1b=this.plugin.args.ruleCount,len=cbs.length,_1c;if(_1b>0&&len+cnt>_1b){cnt=_1b-len;}for(;cnt>0;--cnt){_1c=new _7.CriteriaBox({dlg:this});cbs.push(_1c);cc.addChild(_1c);}cc.startup();this._updatePane();this._updateCBoxTitles();cc.selectChild(cbs[cbs.length-1]);this.filterDefPane.criteriaPane.scrollTop=1000000;if(cbs.length===4){if(_4.isIE<=6&&!this.__alreadyResizedForIE6){var _1d=_4.position(cc.domNode);_1d.w-=_6.html.metrics.getScrollbar().w;cc.resize(_1d);this.__alreadyResizedForIE6=true;}else{cc.resize();}}},removeCriteriaBoxes:function(cnt,_1e){var cbs=this._cboxes,cc=this.filterDefPane.cboxContainer,len=cbs.length,_1f=len-cnt,end=len-1,_20,_21=_4.indexOf(cbs,cc.selectedChildWidget.content);if(_4.isArray(cnt)){var i,_22=cnt;_22.sort();cnt=_22.length;for(i=len-1;i>=0&&_4.indexOf(_22,i)>=0;--i){}if(i>=0){if(i!=_21){cc.selectChild(cbs[i]);}for(i=cnt-1;i>=0;--i){if(_22[i]>=0&&_22[i]<len){cc.removeChild(cbs[_22[i]]);cbs.splice(_22[i],1);}}}_1f=cbs.length;}else{if(_1e===true){if(cnt>=0&&cnt<len){_1f=end=cnt;cnt=1;}else{return;}}else{if(cnt instanceof _7.CriteriaBox){_20=cnt;cnt=1;_1f=end=_4.indexOf(cbs,_20);}else{if(typeof cnt!="number"||cnt<=0){return;}else{if(cnt>=len){cnt=end;_1f=1;}}}}if(end<_1f){return;}if(_21>=_1f&&_21<=end){cc.selectChild(cbs[_1f?_1f-1:end+1]);}for(;end>=_1f;--end){cc.removeChild(cbs[end]);}cbs.splice(_1f,cnt);}this._updatePane();this._updateCBoxTitles();if(cbs.length===3){cc.resize();}},getCriteria:function(idx){if(typeof idx!="number"){return this._savedCriterias?this._savedCriterias.length:0;}if(this._savedCriterias&&this._savedCriterias[idx]){return _4.mixin({relation:this._relOpCls=="logicall"?this.plugin.nls.and:this.plugin.nls.or},this._savedCriterias[idx]);}return null;},getExprForCriteria:function(_23){if(_23.column=="anycolumn"){var _24=_4.filter(this.plugin.grid.layout.cells,function(_25){return !(_25.filterable===false||_25.hidden);});return {"op":"logicany","data":_4.map(_24,function(_26){return this.getExprForColumn(_23.value,_26.index,_23.type,_23.condition);},this)};}else{return this.getExprForColumn(_23.value,_23.column,_23.type,_23.condition);}},getExprForColumn:function(_27,_28,_29,_2a){_28=parseInt(_28,10);var _2b=this.plugin.grid.layout.cells[_28],_2c=_2b.field||_2b.name,obj={"datatype":_29||this.getColumnType(_28),"args":_2b.dataTypeArgs,"isColumn":true},_2d=[_4.mixin({"data":this.plugin.args.isServerSide?_2c:_2b},obj)];obj.isColumn=false;if(_2a=="range"){_2d.push(_4.mixin({"data":_27.start},obj),_4.mixin({"data":_27.end},obj));}else{if(_2a!="isempty"){_2d.push(_4.mixin({"data":_27},obj));}}return {"op":_2a,"data":_2d};},getColumnType:function(_2e){var _2f=this.plugin.grid.layout.cells[parseInt(_2e,10)];if(!_2f||!_2f.datatype){return this.defaultType;}var _30=String(_2f.datatype).toLowerCase();return this._dataTypeMap[_30]?_30:this.defaultType;},clearFilter:function(_31){if(!this._savedCriterias){return;}this._savedCriterias=null;this.plugin.grid.layer("filter").filterDef(null);try{this.plugin.filterBar.toggleClearFilterBtn(true);this.filterDefPane._clearFilterBtn.set("disabled",true);this.removeCriteriaBoxes(this._cboxes.length-1);this._cboxes[0].load({});}catch(e){}if(_31){this.closeDialog();}else{this._closeDlgAndUpdateGrid();}},showDialog:function(_32){this._defPane.show();this.plugin.filterStatusTip.closeDialog();this._prepareDialog(_32);},closeDialog:function(){this._defPane.hide();},onFilter:function(e){if(this.canFilter()){this._defineFilter();this._closeDlgAndUpdateGrid();this.plugin.filterBar.toggleClearFilterBtn(false);}},onClearFilter:function(e){if(this._savedCriterias){if(this._savedCriterias.length>1){this.plugin.clearFilterDialog.show();}else{this.clearFilter(this._clearWithoutRefresh);}}},onCancel:function(e){var sc=this._savedCriterias;var cbs=this._cboxes;if(sc){this.addCriteriaBoxes(sc.length-cbs.length);this.removeCriteriaBoxes(cbs.length-sc.length);_4.forEach(sc,function(c,i){cbs[i].load(c);});}else{this.removeCriteriaBoxes(cbs.length-1);cbs[0].load({});}this.closeDialog();},onRendered:function(_33){if(!_4.isFF){var _34=_5._getTabNavigable(_4.byId(_33.domNode));_5.focus(_34.lowest||_34.first);}else{var dp=this._defPane;dp._getFocusItems(dp.domNode);_5.focus(dp._firstFocusItem);}},_onSetFilter:function(_35){if(_35===null&&this._savedCriterias){this.clearFilter();}},_prepareDialog:function(_36){var sc=this._savedCriterias,cbs=this._cboxes,i,_37;this.curColIdx=_36;if(!sc){if(cbs.length===0){this.addCriteriaBoxes(1);}else{for(i=0;(_37=cbs[i]);++i){_37.changeCurrentColumn();}}}else{if(this._criteriasChanged){this.filterDefPane._relSelect.set("value",this._relOpCls==="logicall"?"0":"1");this._criteriasChanged=false;var _38=sc.length>cbs.length;this.addCriteriaBoxes(sc.length-cbs.length);this.removeCriteriaBoxes(cbs.length-sc.length);this.filterDefPane._clearFilterBtn.set("disabled",false);if(_38){_4.forEach(sc,function(c,i){var _39=_4.connect(this,"onRendered",function(_3a){if(_3a==cbs[i]){_4.disconnect(_39);_3a.load(c);}});},this);}else{for(i=0;i<sc.length;++i){cbs[i].load(sc[i]);}}}}this.filterDefPane.cboxContainer.resize();},_defineFilter:function(){var cbs=this._cboxes,_3b=function(_3c){return _4.filter(_4.map(cbs,function(_3d){return _3d[_3c]();}),function(_3e){return !!_3e;});},_3f=_3b("getExpr");this._savedCriterias=_3b("save");_3f=_3f.length==1?_3f[0]:{"op":this._relOpCls,"data":_3f};_3f=this.builder.buildExpression(_3f);this.plugin.grid.layer("filter").filterDef(_3f);this.filterDefPane._clearFilterBtn.set("disabled",false);},_updateCBoxTitles:function(){for(var cbs=this._cboxes,i=cbs.length;i>0;--i){cbs[i-1].updateRuleIndex(i);cbs[i-1].setAriaInfo(i);}},_updatePane:function(){var cbs=this._cboxes,_40=this.filterDefPane;_40._addCBoxBtn.set("disabled",cbs.length==this.plugin.args.ruleCount);_40._filterBtn.set("disabled",!this.canFilter());},canFilter:function(){return _4.filter(this._cboxes,function(_41){return !_41.isEmpty();}).length>0;},_closeDlgAndUpdateGrid:function(){this.closeDialog();var g=this.plugin.grid;g.showMessage(g.loadingMessage);setTimeout(_4.hitch(g,g._refresh),this._defPane.duration+10);}});_4.declare("dojox.grid.enhanced.plugins.filter.FilterDefPane",[_5._Widget,_5._Templated],{templateString:_4.cache("dojox.grid","enhanced/templates/FilterDefPane.html","<div class=\"dojoxGridFDPane\">\r\n\t<div class=\"dojoxGridFDPaneRelation\">${_relMsgFront}\r\n\t<span class=\"dojoxGridFDPaneModes\" dojoAttachPoint=\"criteriaModeNode\">\r\n\t\t<select dojoAttachPoint=\"_relSelect\" dojoType=\"dijit.form.Select\" dojoAttachEvent=\"onChange: _onRelSelectChange\">\r\n\t\t\t<option value=\"0\">${_relAll}</option>\r\n\t\t\t<option value=\"1\">${_relAny}</option>\r\n\t\t</select>\r\n\t</span>\r\n\t${_relMsgTail}\r\n\t</div>\r\n\t<div dojoAttachPoint=\"criteriaPane\" class=\"dojoxGridFDPaneRulePane\"></div>\r\n\t<div dojoAttachPoint=\"_addCBoxBtn\" dojoType=\"dijit.form.Button\" \r\n\t\tclass=\"dojoxGridFDPaneAddCBoxBtn\" iconClass=\"dojoxGridFDPaneAddCBoxBtnIcon\"\r\n\t\tdojoAttachEvent=\"onClick:_onAddCBox\" label=\"${_addRuleBtnLabel}\" showLabel=\"false\">\r\n\t</div>\r\n\t<div class=\"dojoxGridFDPaneBtns\" dojoAttachPoint=\"buttonsPane\">\r\n\t\t<span dojoAttachPoint=\"_cancelBtn\" dojoType=\"dijit.form.Button\" \r\n\t\t\tdojoAttachEvent=\"onClick:_onCancel\" label=\"${_cancelBtnLabel}\">\r\n\t\t</span>\r\n\t\t<span dojoAttachPoint=\"_clearFilterBtn\" dojoType=\"dijit.form.Button\" \r\n\t\t\tdojoAttachEvent=\"onClick:_onClearFilter\" label=\"${_clearBtnLabel}\" disabled=\"true\">\r\n\t\t</span>\r\n\t\t<span dojoAttachPoint=\"_filterBtn\" dojoType=\"dijit.form.Button\" \r\n\t\t\tdojoAttachEvent=\"onClick:_onFilter\" label=\"${_filterBtnLabel}\" disabled=\"true\">\r\n\t\t</span>\r\n\t</div>\r\n</div>\r\n"),widgetsInTemplate:true,dlg:null,postMixInProperties:function(){this.plugin=this.dlg.plugin;var nls=this.plugin.nls;this._addRuleBtnLabel=nls.addRuleButton;this._cancelBtnLabel=nls.cancelButton;this._clearBtnLabel=nls.clearButton;this._filterBtnLabel=nls.filterButton;this._relAll=nls.relationAll;this._relAny=nls.relationAny;this._relMsgFront=nls.relationMsgFront;this._relMsgTail=nls.relationMsgTail;},postCreate:function(){this.inherited(arguments);this.connect(this.domNode,"onkeypress","_onKey");(this.cboxContainer=new _7.AccordionContainer({nls:this.plugin.nls})).placeAt(this.criteriaPane);this._relSelect.set("tabIndex",_8.relSelect);this._addCBoxBtn.set("tabIndex",_8.addCBoxBtn);this._cancelBtn.set("tabIndex",_8.cancelBtn);this._clearFilterBtn.set("tabIndex",_8.clearBtn);this._filterBtn.set("tabIndex",_8.filterBtn);var nls=this.plugin.nls;_5.setWaiState(this._relSelect.domNode,"label",nls.waiRelAll);_5.setWaiState(this._addCBoxBtn.domNode,"label",nls.waiAddRuleButton);_5.setWaiState(this._cancelBtn.domNode,"label",nls.waiCancelButton);_5.setWaiState(this._clearFilterBtn.domNode,"label",nls.waiClearButton);_5.setWaiState(this._filterBtn.domNode,"label",nls.waiFilterButton);this._relSelect.set("value",this.dlg._relOpCls==="logicall"?"0":"1");},uninitialize:function(){this.cboxContainer.destroyRecursive();this.plugin=null;this.dlg=null;},_onRelSelectChange:function(val){this.dlg._relOpCls=val=="0"?"logicall":"logicany";_5.setWaiState(this._relSelect.domNode,"label",this.plugin.nls[val=="0"?"waiRelAll":"waiRelAny"]);},_onAddCBox:function(){this.dlg.addCriteriaBoxes(1);},_onCancel:function(){this.dlg.onCancel();},_onClearFilter:function(){this.dlg.onClearFilter();},_onFilter:function(){this.dlg.onFilter();},_onKey:function(e){if(e.keyCode==_4.keys.ENTER){this.dlg.onFilter();}}});_4.declare("dojox.grid.enhanced.plugins.filter.CriteriaBox",[_5._Widget,_5._Templated],{templateString:_4.cache("dojox.grid","enhanced/templates/CriteriaBox.html","<div class=\"dojoxGridFCBox\">\r\n\t<div class=\"dojoxGridFCBoxSelCol\" dojoAttachPoint=\"selColNode\">\r\n\t\t<span class=\"dojoxGridFCBoxField\">${_colSelectLabel}</span>\r\n\t\t<select dojoAttachPoint=\"_colSelect\" dojoType=\"dijit.form.Select\" \r\n\t\t\tclass=\"dojoxGridFCBoxColSelect\"\r\n\t\t\tdojoAttachEvent=\"onChange:_onChangeColumn\">\r\n\t\t</select>\r\n\t</div>\r\n\t<div class=\"dojoxGridFCBoxCondition\" dojoAttachPoint=\"condNode\">\r\n\t\t<span class=\"dojoxGridFCBoxField\">${_condSelectLabel}</span>\r\n\t\t<select dojoAttachPoint=\"_condSelect\" dojoType=\"dijit.form.Select\" \r\n\t\t\tclass=\"dojoxGridFCBoxCondSelect\"\r\n\t\t\tdojoAttachEvent=\"onChange:_onChangeCondition\">\r\n\t\t</select>\r\n\t\t<div class=\"dojoxGridFCBoxCondSelectAlt\" dojoAttachPoint=\"_condSelectAlt\" style=\"display:none;\"></div>\r\n\t</div>\r\n\t<div class=\"dojoxGridFCBoxValue\" dojoAttachPoint=\"valueNode\">\r\n\t\t<span class=\"dojoxGridFCBoxField\">${_valueBoxLabel}</span>\r\n\t</div>\r\n</div>\r\n"),widgetsInTemplate:true,dlg:null,postMixInProperties:function(){this.plugin=this.dlg.plugin;this._curValueBox=null;var nls=this.plugin.nls;this._colSelectLabel=nls.columnSelectLabel;this._condSelectLabel=nls.conditionSelectLabel;this._valueBoxLabel=nls.valueBoxLabel;this._anyColumnOption=nls.anyColumnOption;},postCreate:function(){var dlg=this.dlg,g=this.plugin.grid;this._colSelect.set("tabIndex",_8.colSelect);this._colOptions=this._getColumnOptions();this._colSelect.addOption([{label:this.plugin.nls.anyColumnOption,value:"anycolumn",selected:dlg.curColIdx<0},{value:""}].concat(this._colOptions));this._condSelect.set("tabIndex",_8.condSelect);this._condSelect.addOption(this._getUsableConditions(dlg.getColumnType(dlg.curColIdx)));this._showSelectOrLabel(this._condSelect,this._condSelectAlt);this.connect(g.layout,"moveColumn","onMoveColumn");},_getColumnOptions:function(){var _42=this.dlg.curColIdx>=0?String(this.dlg.curColIdx):"anycolumn";return _4.map(_4.filter(this.plugin.grid.layout.cells,function(_43){return !(_43.filterable===false||_43.hidden);}),function(_44){return {label:_44.name||_44.field,value:String(_44.index),selected:_42==String(_44.index)};});},onMoveColumn:function(){var tmp=this._onChangeColumn;this._onChangeColumn=function(){};var _45=this._colSelect.get("selectedOptions");this._colSelect.removeOption(this._colOptions);this._colOptions=this._getColumnOptions();this._colSelect.addOption(this._colOptions);var i=0;for(;i<this._colOptions.length;++i){if(this._colOptions[i].label==_45.label){break;}}if(i<this._colOptions.length){this._colSelect.set("value",this._colOptions[i].value);}var _46=this;setTimeout(function(){_46._onChangeColumn=tmp;},0);},onRemove:function(){this.dlg.removeCriteriaBoxes(this);},uninitialize:function(){if(this._curValueBox){this._curValueBox.destroyRecursive();this._curValueBox=null;}this.plugin=null;this.dlg=null;},_showSelectOrLabel:function(sel,alt){var _47=sel.getOptions();if(_47.length==1){alt.innerHTML=_47[0].label;_4.style(sel.domNode,"display","none");_4.style(alt,"display","");}else{_4.style(sel.domNode,"display","");_4.style(alt,"display","none");}},_onChangeColumn:function(val){this._checkValidCriteria();var _48=this.dlg.getColumnType(val);this._setConditionsByType(_48);this._setValueBoxByType(_48);this._updateValueBox();},_onChangeCondition:function(val){this._checkValidCriteria();var f=(val=="range");if(f^this._isRange){this._isRange=f;this._setValueBoxByType(this.dlg.getColumnType(this._colSelect.get("value")));}this._updateValueBox();},_updateValueBox:function(_49){this._curValueBox.set("disabled",this._condSelect.get("value")=="isempty");},_checkValidCriteria:function(){setTimeout(_4.hitch(this,function(){this.updateRuleTitle();this.dlg._updatePane();}),0);},_createValueBox:function(cls,arg){var _4a=_4.hitch(arg.cbox,"_checkValidCriteria");return new cls(_4.mixin(arg,{tabIndex:_8.valueBox,onKeyPress:_4a,onChange:_4a,"class":"dojoxGridFCBoxValueBox"}));},_createRangeBox:function(cls,arg){var _4b=_4.hitch(arg.cbox,"_checkValidCriteria");_4.mixin(arg,{tabIndex:_8.valueBox,onKeyPress:_4b,onChange:_4b});var div=_4.create("div",{"class":"dojoxGridFCBoxValueBox"}),_4c=new cls(arg),txt=_4.create("span",{"class":"dojoxGridFCBoxRangeValueTxt","innerHTML":this.plugin.nls.rangeTo}),end=new cls(arg);_4.addClass(_4c.domNode,"dojoxGridFCBoxStartValue");_4.addClass(end.domNode,"dojoxGridFCBoxEndValue");div.appendChild(_4c.domNode);div.appendChild(txt);div.appendChild(end.domNode);div.domNode=div;div.set=function(_4d,_4e){if(_4.isObject(_4e)){_4c.set("value",_4e.start);end.set("value",_4e.end);}};div.get=function(){var s=_4c.get("value"),e=end.get("value");return s&&e?{start:s,end:e}:"";};return div;},changeCurrentColumn:function(_4f){var _50=this.dlg.curColIdx;this._colSelect.removeOption(this._colOptions);this._colOptions=this._getColumnOptions();this._colSelect.addOption(this._colOptions);this._colSelect.set("value",_50>=0?String(_50):"anycolumn");this.updateRuleTitle(true);},curColumn:function(){return this._colSelect.getOptions(this._colSelect.get("value")).label;},curCondition:function(){return this._condSelect.getOptions(this._condSelect.get("value")).label;},curValue:function(){var _51=this._condSelect.get("value");if(_51=="isempty"){return "";}return this._curValueBox?this._curValueBox.get("value"):"";},save:function(){if(this.isEmpty()){return null;}var _52=this._colSelect.get("value"),_53=this.dlg.getColumnType(_52),_54=this.curValue(),_55=this._condSelect.get("value");return {"column":_52,"condition":_55,"value":_54,"formattedVal":this.formatValue(_53,_55,_54),"type":_53,"colTxt":this.curColumn(),"condTxt":this.curCondition()};},load:function(obj){var tmp=[this._onChangeColumn,this._onChangeCondition];this._onChangeColumn=this._onChangeCondition=function(){};if(obj.column){this._colSelect.set("value",obj.column);}if(obj.condition){this._condSelect.set("value",obj.condition);}if(obj.type){this._setValueBoxByType(obj.type);}else{obj.type=this.dlg.getColumnType(this._colSelect.get("value"));}var _56=obj.value||"";if(_56||(obj.type!="date"&&obj.type!="time")){this._curValueBox.set("value",_56);}this._updateValueBox();setTimeout(_4.hitch(this,function(){this._onChangeColumn=tmp[0];this._onChangeCondition=tmp[1];}),0);},getExpr:function(){if(this.isEmpty()){return null;}var _57=this._colSelect.get("value");return this.dlg.getExprForCriteria({"type":this.dlg.getColumnType(_57),"column":_57,"condition":this._condSelect.get("value"),"value":this.curValue()});},isEmpty:function(){var _58=this._condSelect.get("value");if(_58=="isempty"){return false;}var v=this.curValue();return v===""||v===null||typeof v=="undefined"||(typeof v=="number"&&isNaN(v));},updateRuleTitle:function(_59){var _5a=this._pane._buttonWidget.titleTextNode;var _5b=["<div class='dojoxEllipsis'>"];if(_59||this.isEmpty()){_5a.title=_4.string.substitute(this.plugin.nls.ruleTitleTemplate,[this._ruleIndex||1]);_5b.push(_5a.title);}else{var _5c=this.dlg.getColumnType(this._colSelect.get("value"));var _5d=this.curColumn();var _5e=this.curCondition();var _5f=this.formatValue(_5c,this._condSelect.get("value"),this.curValue());_5b.push(_5d,"&nbsp;<span class='dojoxGridRuleTitleCondition'>",_5e,"</span>&nbsp;",_5f);_5a.title=[_5d," ",_5e," ",_5f].join("");}_5a.innerHTML=_5b.join("");if(_4.isMoz){var tt=_4.create("div",{"style":"width: 100%; height: 100%; position: absolute; top: 0; left: 0; z-index: 9999;"},_5a);tt.title=_5a.title;}},updateRuleIndex:function(_60){if(this._ruleIndex!=_60){this._ruleIndex=_60;if(this.isEmpty()){this.updateRuleTitle();}}},setAriaInfo:function(idx){var dss=_4.string.substitute,nls=this.plugin.nls;_5.setWaiState(this._colSelect.domNode,"label",dss(nls.waiColumnSelectTemplate,[idx]));_5.setWaiState(this._condSelect.domNode,"label",dss(nls.waiConditionSelectTemplate,[idx]));_5.setWaiState(this._pane._removeCBoxBtn.domNode,"label",dss(nls.waiRemoveRuleButtonTemplate,[idx]));this._index=idx;},_getUsableConditions:function(_61){var _62=this.dlg._dataTypeMap[_61].conditions;var _63=(this.plugin.args.disabledConditions||{})[_61];var _64=parseInt(this._colSelect.get("value"),10);var _65=isNaN(_64)?(this.plugin.args.disabledConditions||{})["anycolumn"]:this.plugin.grid.layout.cells[_64].disabledConditions;if(!_4.isArray(_63)){_63=[];}if(!_4.isArray(_65)){_65=[];}var arr=_63.concat(_65);if(arr.length){var _66={};_4.forEach(arr,function(c){if(_4.isString(c)){_66[c.toLowerCase()]=true;}});return _4.filter(_62,function(_67){return !(_67.value in _66);});}return _62;},_setConditionsByType:function(_68){var _69=this._condSelect;_69.removeOption(_69.options);_69.addOption(this._getUsableConditions(_68));this._showSelectOrLabel(this._condSelect,this._condSelectAlt);},_setValueBoxByType:function(_6a){if(this._curValueBox){this.valueNode.removeChild(this._curValueBox.domNode);try{this._curValueBox.destroyRecursive();}catch(e){}delete this._curValueBox;}var _6b=this.dlg._dataTypeMap[_6a].valueBoxCls[this._getValueBoxClsInfo(this._colSelect.get("value"),_6a)],_6c=this._getValueBoxArgByType(_6a);this._curValueBox=this[this._isRange?"_createRangeBox":"_createValueBox"](_6b,_6c);this.valueNode.appendChild(this._curValueBox.domNode);_5.setWaiState(this._curValueBox.domNode,"label",_4.string.substitute(this.plugin.nls.waiValueBoxTemplate,[this._index]));this.dlg.onRendered(this);},_getValueBoxArgByType:function(_6d){var g=this.plugin.grid,_6e=g.layout.cells[parseInt(this._colSelect.get("value"),10)],res={cbox:this};if(_6d=="string"){if(_6e&&(_6e.suggestion||_6e.autoComplete)){_4.mixin(res,{store:g.store,searchAttr:_6e.field||_6e.name,fetchProperties:{sort:[{"attribute":_6e.field||_6e.name}]}});}}else{if(_6d=="boolean"){_4.mixin(res,this.dlg.builder.defaultArgs["boolean"]);}}if(_6e&&_6e.dataTypeArgs){_4.mixin(res,_6e.dataTypeArgs);}return res;},formatValue:function(_6f,_70,v){if(_70=="isempty"){return "";}if(_6f=="date"||_6f=="time"){var opt={selector:_6f},fmt=_4.date.locale.format;if(_70=="range"){return _4.string.substitute(this.plugin.nls.rangeTemplate,[fmt(v.start,opt),fmt(v.end,opt)]);}return fmt(v,opt);}else{if(_6f=="boolean"){return v?this._curValueBox._lblTrue:this._curValueBox._lblFalse;}}return v;},_getValueBoxClsInfo:function(_71,_72){var _73=this.plugin.grid.layout.cells[parseInt(_71,10)];if(_72=="string"){return (_73&&(_73.suggestion||_73.autoComplete))?"ac":"dft";}return "dft";}});_4.declare("dojox.grid.enhanced.plugins.filter.AccordionContainer",_5.layout.AccordionContainer,{nls:null,addChild:function(_74,_75){var _76=arguments[0]=_74._pane=new _5.layout.ContentPane({content:_74});this.inherited(arguments);this._modifyChild(_76);},removeChild:function(_77){var _78=_77,_79=false;if(_77._pane){_79=true;_78=arguments[0]=_77._pane;}this.inherited(arguments);if(_79){this._hackHeight(false,this._titleHeight);var _7a=this.getChildren();if(_7a.length===1){_4.style(_7a[0]._removeCBoxBtn.domNode,"display","none");}}_78.destroyRecursive();},selectChild:function(_7b){if(_7b._pane){arguments[0]=_7b._pane;}this.inherited(arguments);},resize:function(){this.inherited(arguments);_4.forEach(this.getChildren(),this._setupTitleDom);},startup:function(){if(this._started){return;}this.inherited(arguments);if(parseInt(_4.isIE,10)==7){_4.some(this._connects,function(_7c){if(_7c[0][1]=="onresize"){this.disconnect(_7c);return true;}},this);}_4.forEach(this.getChildren(),function(_7d){this._modifyChild(_7d,true);},this);},_onKeyPress:function(e,_7e){if(this.disabled||e.altKey||!(_7e||e.ctrlKey)){return;}var k=_4.keys,c=e.charOrCode,ltr=_4._isBodyLtr(),_7f=null;if((_7e&&c==k.UP_ARROW)||(e.ctrlKey&&c==k.PAGE_UP)){_7f=false;}else{if((_7e&&c==k.DOWN_ARROW)||(e.ctrlKey&&(c==k.PAGE_DOWN||c==k.TAB))){_7f=true;}else{if(c==(ltr?k.LEFT_ARROW:k.RIGHT_ARROW)){_7f=this._focusOnRemoveBtn?null:false;this._focusOnRemoveBtn=!this._focusOnRemoveBtn;}else{if(c==(ltr?k.RIGHT_ARROW:k.LEFT_ARROW)){_7f=this._focusOnRemoveBtn?true:null;this._focusOnRemoveBtn=!this._focusOnRemoveBtn;}else{return;}}}}if(_7f!==null){this._adjacent(_7f)._buttonWidget._onTitleClick();}_4.stopEvent(e);_4.window.scrollIntoView(this.selectedChildWidget._buttonWidget.domNode.parentNode);if(_4.isIE){this.selectedChildWidget._removeCBoxBtn.focusNode.setAttribute("tabIndex",this._focusOnRemoveBtn?_8.accordionTitle:-1);}_5.focus(this.selectedChildWidget[this._focusOnRemoveBtn?"_removeCBoxBtn":"_buttonWidget"].focusNode);},_modifyChild:function(_80,_81){if(!_80||!this._started){return;}_4.style(_80.domNode,"overflow","hidden");_80._buttonWidget.connect(_80._buttonWidget,"_setSelectedAttr",function(){this.focusNode.setAttribute("tabIndex",this.selected?_8.accordionTitle:"-1");});var _82=this;_80._buttonWidget.connect(_80._buttonWidget.domNode,"onclick",function(){_82._focusOnRemoveBtn=false;});(_80._removeCBoxBtn=new _5.form.Button({label:this.nls.removeRuleButton,showLabel:false,iconClass:"dojoxGridFCBoxRemoveCBoxBtnIcon",tabIndex:_8.removeCBoxBtn,onClick:_4.hitch(_80.content,"onRemove"),onKeyPress:function(e){_82._onKeyPress(e,_80._buttonWidget.contentWidget);}})).placeAt(_80._buttonWidget.domNode);var i,_83=this.getChildren();if(_83.length===1){_80._buttonWidget.set("selected",true);_4.style(_80._removeCBoxBtn.domNode,"display","none");}else{for(i=0;i<_83.length;++i){if(_83[i]._removeCBoxBtn){_4.style(_83[i]._removeCBoxBtn.domNode,"display","");}}}this._setupTitleDom(_80);if(!this._titleHeight){for(i=0;i<_83.length;++i){if(_83[i]!=this.selectedChildWidget){this._titleHeight=_4.marginBox(_83[i]._buttonWidget.domNode.parentNode).h;break;}}}if(!_81){this._hackHeight(true,this._titleHeight);}},_hackHeight:function(_84,_85){var _86=this.getChildren(),dn=this.domNode,h=_4.style(dn,"height");if(!_84){dn.style.height=(h-_85)+"px";}else{if(_86.length>1){dn.style.height=(h+_85)+"px";}else{return;}}this.resize();},_setupTitleDom:function(_87){var w=_4.contentBox(_87._buttonWidget.titleNode).w;if(_4.isIE<8){w-=8;}_4.style(_87._buttonWidget.titleTextNode,"width",w+"px");}});_4.declare("dojox.grid.enhanced.plugins.filter.UniqueComboBox",_5.form.ComboBox,{_openResultList:function(_88){var _89={},s=this.store,_8a=this.searchAttr;arguments[0]=_4.filter(_88,function(_8b){var key=s.getValue(_8b,_8a),_8c=_89[key];_89[key]=true;return !_8c;});this.inherited(arguments);},_onKey:function(evt){if(evt.charOrCode===_4.keys.ENTER&&this._opened){_4.stopEvent(evt);}this.inherited(arguments);}});_4.declare("dojox.grid.enhanced.plugins.filter.BooleanValueBox",[_5._Widget,_5._Templated],{templateString:_4.cache("dojox.grid","enhanced/templates/FilterBoolValueBox.html","<div class=\"dojoxGridBoolValueBox\">\r\n\t<div class=\"dojoxGridTrueBox\">\r\n\t\t<input dojoType=\"dijit.form.RadioButton\" type='radio' name='a1' id='${_baseId}_rbTrue' checked=\"true\" \r\n\t\t\tdojoAttachPoint=\"rbTrue\" dojoAttachEvent=\"onChange: onChange\"/>\r\n\t\t<div class=\"dojoxGridTrueLabel\" for='${_baseId}_rbTrue'>${_lblTrue}</div>\r\n\t</div>\r\n\t<div class=\"dojoxGridFalseBox\">\r\n\t\t<input dojoType=\"dijit.form.RadioButton\" dojoAttachPoint=\"rbFalse\" type='radio' name='a1' id='${_baseId}_rbFalse'/>\r\n\t\t<div class=\"dojoxGridTrueLabel\" for='${_baseId}_rbFalse'>${_lblFalse}</div>\r\n\t</div>\r\n</div>\r\n"),widgetsInTemplate:true,constructor:function(_8d){var nls=_8d.cbox.plugin.nls;this._baseId=_8d.cbox.id;this._lblTrue=_8d.trueLabel||nls.trueLabel||"true";this._lblFalse=_8d.falseLabel||nls.falseLabel||"false";this.args=_8d;},postCreate:function(){this.onChange();},onChange:function(){},get:function(_8e){return this.rbTrue.get("checked");},set:function(_8f,v){this.inherited(arguments);if(_8f=="value"){this.rbTrue.set("checked",!!v);this.rbFalse.set("checked",!v);}}});})();}}};});