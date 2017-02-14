/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.grid.enhanced.plugins.filter.FilterStatusTip"],["requireLocalization","dojox.grid.enhanced","Filter",null,"ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hr,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw","ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hr,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw"],["require","dijit.TooltipDialog"],["require","dijit._base.popup"],["require","dijit.form.Button"],["require","dojo.string"],["require","dojo.date.locale"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.grid.enhanced.plugins.filter.FilterStatusTip"]){_4._hasResource["dojox.grid.enhanced.plugins.filter.FilterStatusTip"]=true;_4.provide("dojox.grid.enhanced.plugins.filter.FilterStatusTip");_4.require("dijit.TooltipDialog");_4.require("dijit._base.popup");_4.require("dijit.form.Button");_4.require("dojo.string");_4.require("dojo.date.locale");(function(){var _7="",_8="",_9="",_a="",_b="dojoxGridFStatusTipOddRow",_c="dojoxGridFStatusTipHandle",_d="dojoxGridFStatusTipCondition",_e="dojoxGridFStatusTipDelRuleBtnIcon",_f="</tbody></table>";_4.declare("dojox.grid.enhanced.plugins.filter.FilterStatusTip",null,{constructor:function(_10){var _11=this.plugin=_10.plugin;this._statusHeader=["<table border='0' cellspacing='0' class='",_7,"'><thead><tr class='",_8,"'><th class='",_9,"'><div>",_11.nls["statusTipHeaderColumn"],"</div></th><th class='",_9," lastColumn'><div>",_11.nls["statusTipHeaderCondition"],"</div></th></tr></thead><tbody>"].join("");this._removedCriterias=[];this._rules=[];this.statusPane=new _6.grid.enhanced.plugins.filter.FilterStatusPane();this._dlg=new _5.TooltipDialog({"class":"dojoxGridFStatusTipDialog",content:this.statusPane,autofocus:false,onMouseLeave:_4.hitch(this,function(){this.closeDialog();})});this._dlg.connect(this._dlg.domNode,"click",_4.hitch(this,this._modifyFilter));},destroy:function(){this._dlg.destroyRecursive();},showDialog:function(_12,_13,_14){this._pos={x:_12,y:_13};_5.popup.close(this._dlg);this._removedCriterias=[];this._rules=[];this._updateStatus(_14);_5.popup.open({popup:this._dlg,parent:this.plugin.filterBar,x:_12-12,y:_13-3});},closeDialog:function(){_5.popup.close(this._dlg);if(this._removedCriterias.length){this.plugin.filterDefDialog.removeCriteriaBoxes(this._removedCriterias);this._removedCriterias=[];this.plugin.filterDefDialog.onFilter();}},_updateStatus:function(_15){var res,p=this.plugin,nls=p.nls,sp=this.statusPane,fdg=p.filterDefDialog;if(fdg.getCriteria()===0){sp.statusTitle.innerHTML=nls["statusTipTitleNoFilter"];sp.statusRel.innerHTML=sp.statusRelPre.innerHTML=sp.statusRelPost.innerHTML="";var _16=p.grid.layout.cells[_15];var _17=_16?"'"+(_16.name||_16.field)+"'":nls["anycolumn"];res=_4.string.substitute(nls["statusTipMsg"],[_17]);}else{sp.statusTitle.innerHTML=nls["statusTipTitleHasFilter"];sp.statusRelPre.innerHTML=nls["statusTipRelPre"]+"&nbsp;";sp.statusRelPost.innerHTML="&nbsp;"+nls["statusTipRelPost"];sp.statusRel.innerHTML=fdg._relOpCls=="logicall"?nls["all"]:nls["any"];this._rules=[];var i=0,c=fdg.getCriteria(i++);while(c){c.index=i-1;this._rules.push(c);c=fdg.getCriteria(i++);}res=this._createStatusDetail();}sp.statusDetailNode.innerHTML=res;this._addButtonForRules();},_createStatusDetail:function(){return this._statusHeader+_4.map(this._rules,function(_18,i){return this._getCriteriaStr(_18,i);},this).join("")+_f;},_addButtonForRules:function(){if(this._rules.length>1){_4.query("."+_c,this.statusPane.statusDetailNode).forEach(_4.hitch(this,function(nd,idx){(new _5.form.Button({label:this.plugin.nls["removeRuleButton"],showLabel:false,iconClass:_e,onClick:_4.hitch(this,function(e){e.stopPropagation();this._removedCriterias.push(this._rules[idx].index);this._rules.splice(idx,1);this.statusPane.statusDetailNode.innerHTML=this._createStatusDetail();this._addButtonForRules();})})).placeAt(nd,"last");}));}},_getCriteriaStr:function(c,_19){var res=["<tr class='",_a," ",(_19%2?_b:""),"'><td class='",_9,"'>",c.colTxt,"</td><td class='",_9,"'><div class='",_c,"'><span class='",_d,"'>",c.condTxt,"&nbsp;</span>",c.formattedVal,"</div></td></tr>"];return res.join("");},_modifyFilter:function(){this.closeDialog();var p=this.plugin;p.filterDefDialog.showDialog(p.filterBar.getColumnIdx(this._pos.x));}});_4.declare("dojox.grid.enhanced.plugins.filter.FilterStatusPane",[_5._Widget,_5._Templated],{templateString:_4.cache("dojox.grid","enhanced/templates/FilterStatusPane.html","<div class=\"dojoxGridFStatusTip\">\r\n\t<div class=\"dojoxGridFStatusTipHead\">\r\n\t\t<span class=\"dojoxGridFStatusTipTitle\" dojoAttachPoint=\"statusTitle\"></span\r\n\t\t><span class=\"dojoxGridFStatusTipRelPre\" dojoAttachPoint=\"statusRelPre\"></span\r\n\t\t><span class=\"dojoxGridFStatusTipRel\" dojoAttachPoint=\"statusRel\"></span\r\n\t\t><span class=\"dojoxGridFStatusTipRelPost\" dojoAttachPoint=\"statusRelPost\"></span>\r\n\t</div>\r\n\t<div class=\"dojoxGridFStatusTipDetail\" dojoAttachPoint=\"statusDetailNode\"></div>\r\n</div>\r\n")});})();}}};});