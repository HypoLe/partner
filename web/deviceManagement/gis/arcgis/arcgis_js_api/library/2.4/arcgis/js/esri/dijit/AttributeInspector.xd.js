/*
 COPYRIGHT 2009 ESRI

 TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
 Unpublished material - all rights reserved under the
 Copyright Laws of the United States and applicable international
 laws, treaties, and conventions.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts and Legal Services Department
 380 New York Street
 Redlands, California, 92373
 USA

 email: contracts@esri.com
 */

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.AttributeInspector"],["require","dojo.fx"],["require","dojox.gfx"],["require","dijit._Widget"],["require","dijit._Templated"],["require","dijit.Editor"],["require","dijit._editor.plugins.LinkDialog"],["require","dijit._editor.plugins.TextColor"],["require","esri.dijit.editing.AttachmentEditor"],["require","esri.dijit.editing.Util"],["require","dijit.form.DateTextBox"],["require","dijit.form.TextBox"],["require","dijit.form.NumberTextBox"],["require","dijit.form.FilteringSelect"],["require","dijit.form.NumberSpinner"],["require","dijit.form.Button"],["require","dijit.form.SimpleTextarea"],["require","dijit.Tooltip"],["require","dojo.data.ItemFileReadStore"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.AttributeInspector"]){_4._hasResource["esri.dijit.AttributeInspector"]=true;_4.provide("esri.dijit.AttributeInspector");_4.require("dojo.fx");_4.require("dojox.gfx");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.require("dijit.Editor");_4.require("dijit._editor.plugins.LinkDialog");_4.require("dijit._editor.plugins.TextColor");_4.require("esri.dijit.editing.AttachmentEditor");_4.require("esri.dijit.editing.Util");_4.require("dijit.form.DateTextBox");_4.require("dijit.form.TextBox");_4.require("dijit.form.NumberTextBox");_4.require("dijit.form.FilteringSelect");_4.require("dijit.form.NumberSpinner");_4.require("dijit.form.Button");_4.require("dijit.form.SimpleTextarea");_4.require("dijit.Tooltip");_4.require("dojo.data.ItemFileReadStore");(function(){var _7=document.createElement("link");_7.type="text/css";_7.rel="stylesheet";_7.href=_4.moduleUrl("esri","dijit/css/AttributeInspector.css");document.getElementsByTagName("head").item(0).appendChild(_7);})();_4.declare("esri.dijit.AttributeInspector",[_5._Widget,_5._Templated],{widgetsInTemplate:true,templateString:"<div class=\"esriAttributeInspector\">\r\n    <div class=\"atiLayerName\" dojoAttachPoint=\"layerName\"></div>\r\n    <div class=\"atiAttributes\" dojoAttachPoint=\"attributeTable\"></div>\r\n    <div dojoAttachPoint=\"attachmentEditor\"></div>\r\n    <div class=\"atiButtons\" dojoAttachPoint=\"editButtons\">\r\n        <button  dojoType=\"dijit.form.Button\" class=\"atiButton atiDeleteButton\"  dojoAttachPoint=\"deleteBtn\" dojoAttachEvent=\"onClick: onDeleteBtn\" showLabel=\"true\" type=\"button\">${NLS_deleteFeature}</button>\r\n        <div class=\"atiNavButtons\" dojoAttachPoint=\"navButtons\">\r\n            <div class=\"atiNavMessage\" dojoAttachPoint=\"navMessage\"></div>\r\n            <button  dojoType=\"dijit.form.Button\" iconClass=\"atiButton atiFirstIcon\" dojoAttachPoint=\"firstFeatureButton\" dojoAttachEvent=\"onClick: onFirstFeature\" showLabel=\"false\" type=\"button\">${NLS_first}</button>\r\n            <button  dojoType=\"dijit.form.Button\" iconClass=\"atiButton atiPrevIcon\" dojoAttachPoint=\"prevFeatureButton\" dojoAttachEvent=\"onClick: onPreviousFeature\" showLabel=\"false\" type=\"button\">${NLS_previous}</button>\r\n            <button  dojoType=\"dijit.form.Button\" iconClass=\"atiButton atiNextIcon\" dojoAttachPoint=\"nextFeatureButton\" dojoAttachEvent=\"onClick: onNextFeature\" showLabel=\"false\" type=\"button\">${NLS_next}</button>\r\n            <button  dojoType=\"dijit.form.Button\" iconClass=\"atiButton atiLastIcon\" dojoAttachPoint=\"lastFeatureButton\" dojoAttachEvent=\"onClick: onLastFeature\" showLabel=\"false\" type=\"button\">${NLS_last}</button>\r\n        </div>\r\n    </div>\r\n</div>\r\n",_navMessage:"( ${idx} ${of} ${numFeatures} )",onUpdate:function(){},onDelete:function(){},onAttributeChange:function(){},onNext:function(){},onReset:function(){},onCancel:function(){},constructor:function(_8,_9){_4.mixin(this,esri.bundle.widgets.attributeInspector);_8=_8||{};if(!_8.featureLayer&&!_8.layerInfos){console.error("esri.AttributeInspector: please provide correct parameter in the constructor");}this._layerInfos=_8.layerInfos||[{featureLayer:_8.featureLayer,options:_8.options||[]}];this._aiConnects=[];this._selection=[];this._toolTips=[];this._numFeatures=0;this._featureIdx=0;this._currentLInfo=null;this._currentFeature=null;this._hideNavButtons=_8.hideNavButtons||false;},postCreate:function(){this._initLayerInfos();this._createAttachmentEditor();this.onFirstFeature();},destroy:function(){this._destroyAttributeTable();_4.forEach(this._aiConnects,_4.disconnect);delete this._aiConnects;if(this._attachmentEditor){this._attachmentEditor.destroy();delete this._attachmentEditor;}delete this._layerInfos;this._selection=this._currentFeature=this._currentLInfo=this._attributes=this._layerInfos=null;this.inherited(arguments);},refresh:function(){this._updateSelection();},first:function(){this.onFirstFeature();},last:function(){this.onLastFeature();},next:function(){this.onNextFeature();},previous:function(){this.onPreviousFeature();},onLayerSelectionChange:function(_a,_b,_c){this._featureIdx=(_c===esri.layers.FeatureLayer.SELECTION_NEW)?0:this._featureIdx;this._updateSelection();this._updateUI();},onLayerSelectionClear:function(){if(!this._selection||this._selection.length<=0){return;}this._numFeatures=0;this._featureIdx=0;this._selection=[];this._currentFeature=null;this._currentLInfo=null;this._updateUI();},onLayerEditsComplete:function(_d,_e,_f,_10){_10=_10||[];if(_10.length){var _11=this._selection;var _12=_d.featureLayer.objectIdField;_4.forEach(_10,_4.hitch(this,function(del){_4.some(_11,_4.hitch(this,function(_13,idx){if(_13.attributes[_12]!==del.objectId){return false;}this._selection.splice(idx,1);return true;}));}));}_e=_e||[];if(_e.length){var _14=this._selection=esri.dijit.editing.Util.LayerHelper.findFeatures(_e,_d.featureLayer);this._featureIdx=0;}var _15=this._numFeatures=this._selection?this._selection.length:0;if(_e.length||_10.length){var _16=_15?this._selection[this._featureIdx]:null;this._showFeature(_16);}this._updateUI();},onFieldValueChange:function(_17,_18){var _19=_17.field;if(_19.type==="esriFieldTypeDate"){_18=(_18&&_18.getTime)?_18.getTime():_18;}if(this._currentFeature.attributes[_19.name]===_18){return;}var _1a=this._currentLInfo;var _1b=this._currentFeature;var _1c=_19.name;if(_1c===_1a.typeIdField){var _1d=this._findFirst(_1a.types,"id",_18);var _1e=_1a.fieldInfos;_4.forEach(_1e,function(_1f){_19=_1f.field;if(!_19||_19.name===_1a.typeIdField){return;}var _20=_1f.dijit;var _21=this._setFieldDomain(_20,_1d,_19);if(_21&&_20){this._setValue(_20,_1b.attributes[_19.name]+"");}},this);}this.onAttributeChange(_1b,_1c,_18);},onDeleteBtn:function(evt){this._deleteFeature();},onNextFeature:function(evt){this._onNextFeature(1);},onPreviousFeature:function(evt){this._onNextFeature(-1);},onFirstFeature:function(evt){this._onNextFeature(this._featureIdx*-1);},onLastFeature:function(evt){this._onNextFeature((this._numFeatures-1)-this._featureIdx);},_initLayerInfos:function(){var _22=this._layerInfos;_4.forEach(_22,this._initLayerInfo,this);},_initLayerInfo:function(_23){var _24=_23.featureLayer;this._connect(_24,"onSelectionComplete",_4.hitch(this,"onLayerSelectionChange",_23));this._connect(_24,"onSelectionClear",_4.hitch(this,"onLayerSelectionClear",_23));this._connect(_24,"onEditsComplete",_4.hitch(this,"onLayerEditsComplete",_23));_23.showAttachments=_24.hasAttachments?(esri._isDefined(_23.showAttachments)?_23.showAttachments:true):false;_23.hideFields=_23.hideFields||[];_23.htmlFields=_23.htmlFields||[];_23.isEditable=_24.isEditable()?(esri._isDefined(_23.isEditable)?_23.isEditable:true):false;_23.typeIdField=_24.typeIdField;_23.layerId=_24.layerId;_23.types=_24.types;if(!_23.showGlobalID&&_24.globalIdField){_23.hideFields.push(_24.globalIdField);}if(!_23.showObjectID){_23.hideFields.push(_24.objectIdField);}var _25=this._getFields(_23.featureLayer);if(!_25){return;}var _26=_23.fieldInfos||[];_26=_4.map(_26,function(_27){return _4.mixin({},_27);});if(!_26.length){_25=_4.filter(_25,_4.hitch(this,function(_28){return !this._isInFields(_28.name,_23.hideFields);}));_23.fieldInfos=_4.map(_25,_4.hitch(this,function(_29){var _2a=(this._isInFields(_29.name,_23.htmlFields)?esri.dijit.AttributeInspector.STRING_FIELD_OPTION_RICHTEXT:esri.dijit.AttributeInspector.STRING_FIELD_OPTION_TEXTBOX);return {"fieldName":_29.name,"field":_29,"stringFieldOption":_2a};}));}else{_23.fieldInfos=_4.filter(_4.map(_26,_4.hitch(this,function(_2b){var _2c=_2b.stringFieldOption||(this._isInFields(_2b.fieldName,_23.htmlFields)?esri.dijit.AttributeInspector.STRING_FIELD_OPTION_RICHTEXT:esri.dijit.AttributeInspector.STRING_FIELD_OPTION_TEXTBOX);return _4.mixin(_2b,{"field":this._findFirst(_25,"name",_2b.fieldName),"stringFieldOption":_2c});})),"return item.field;");}},_createAttachmentEditor:function(){this._attachmentEditor=null;var _2d=this._layerInfos;var _2e=_4.filter(_2d,"return item.showAttachments");if(!_2e||!_2e.length){return;}this._attachmentEditor=new esri.dijit.editing.AttachmentEditor({"class":"atiAttachmentEditor"},this.attachmentEditor);this._attachmentEditor.startup();},_setCurrentLInfo:function(_2f){var _30=this._currentLInfo?this._currentLInfo.featureLayer:null;var _31=_2f.featureLayer;if(_30&&_30.layerId===_31.layerId){return;}this._currentLInfo=_2f;this._createTable();},_updateSelection:function(){this._selection=[];var _32=this._layerInfos;_4.forEach(_32,this._getSelection,this);var _33=this._numFeatures=this._selection.length;var _34=_33?this._selection[this._featureIdx]:null;this._showFeature(_34);},_getSelection:function(_35){var _36=_35.featureLayer.getSelectedFeatures();this._selection=this._selection.concat(_36);},_updateUI:function(){var _37=this._numFeatures;var _38=this._currentLInfo;this.layerName.innerHTML=(!_38||_37===0)?this.NLS_noFeaturesSelected:(_38.featureLayer?_38.featureLayer.name:"");_4.style(this.attributeTable,"display",_37?"":"none");_4.style(this.editButtons,"display",_37?"":"none");_4.style(this.navButtons,"display",(!this._hideNavButtons&&(_37>1)?"":"none"));this.navMessage.innerHTML=esri.substitute({idx:this._featureIdx+1,of:this.NLS_of,numFeatures:this._numFeatures},this._navMessage);if(this._attachmentEditor){_4.style(this._attachmentEditor.domNode,"display",((_38&&_38.showAttachments)&&_37)?"":"none");}var _39=((_38&&_38.showDeleteButton===false)||(_38&&_38.isEditable===false))?false:true;_4.style(this.deleteBtn.domNode,"display",_39?"":"none");if(this.domNode.parentNode&&this.domNode.parentNode.scrollTop>0){this.domNode.parentNode.scrollTop=0;}},_onNextFeature:function(_3a){this._featureIdx+=_3a;if(this._featureIdx<0){this._featureIdx=this._numFeatures-1;}else{if(this._featureIdx>=this._numFeatures){this._featureIdx=0;}}var _3b=this._selection.length?this._selection[this._featureIdx]:null;this._showFeature(_3b);this._updateUI();this.onNext(_3b);},_deleteFeature:function(){this.onDelete(this._currentFeature);},_showFeature:function(_3c){if(!_3c){return;}this._currentFeature=_3c;var _3d=this._getLInfoFromFeature(_3c);if(!_3d){return;}this._setCurrentLInfo(_3d);var _3e=_3c.attributes;var _3f=this._findFirst(_3d.types,"id",_3e[_3d.typeIdField]);var _40,_41=null;var _42=_3d.fieldInfos;_4.forEach(_42,function(_43){_41=_43.field;_40=_43.dijit||null;if(!_40){return;}var _44=this._setFieldDomain(_40,_3f,_41);var _45=_3e[_41.name];_45=(_45&&_44&&_44.codedValues&&_44.codedValues.length)?(_44.codedValues[_45]?_44.codedValues[_45].name:_45):_45;if(!esri._isDefined(_45)){_45="";}if(_40.declaredClass==="dijit.form.DateTextBox"){_45=(_45==="")?null:new Date(_45);}else{if(_40.declaredClass==="dijit.form.FilteringSelect"){_40._lastValueReported=null;_45=_3e[_41.name]+"";}}try{this._setValue(_40,_45);}catch(error){_40.set("displayedValue",this.NLS_errorInvalid,false);}},this);if(this._attachmentEditor&&_3d.showAttachments){this._attachmentEditor.showAttachments(this._currentFeature);}},_setFieldDomain:function(_46,_47,_48){if(!_46){return null;}var _49=_48.domain;if(_47&&_47.domains){if(_47.domains[_48.name]&&_47.domains[_48.name] instanceof esri.layers.InheritedDomain===false){_49=_47.domains[_48.name];}}if(!_49){return null;}if(_49.codedValues&&_49.codedValues.length>0){_46.store=this._toStore(_4.map(_49.codedValues,"return { id: item.code += '', name: item.name };"));this._setValue(_46,_49.codedValues[0].code);}else{_46.constraints={min:esri._isDefined(_49.minValue)?_49.minValue:Number.MIN_VALUE,max:esri._isDefined(_49.maxValue)?_49.maxValue:Number.MAX_VALUE};this._setValue(_46,_46.constraints.min);}return _49;},_setValue:function(_4a,_4b){if(!_4a.set){return;}_4a._onChangeActive=false;_4a.set("value",_4b,true);_4a._onChangeActive=true;},_getFields:function(_4c){var _4d=_4c._getOutFields();if(!_4d){return null;}var _4e=_4c.fields;return (_4d=="*")?_4e:_4.filter(_4.map(_4d,_4.hitch(this,"_findFirst",_4e,"name")),esri._isDefined);},_isInFields:function(_4f,_50){if(!_4f||!_50&&!_50.length){return false;}return _4.some(_50,function(_51){return _51.toLowerCase()===_4f.toLowerCase();});},_findFirst:function(_52,_53,_54){var _55=_4.filter(_52,function(_56){return _56.hasOwnProperty(_53)&&_56[_53]===_54;});return (_55&&_55.length)?_55[0]:null;},_getLInfoFromFeature:function(_57){var _58=_57.getLayer()?_57.getLayer().layerId:null;return this._findFirst(this._layerInfos,"layerId",_58);},_createTable:function(){this._destroyAttributeTable();this.attributeTable.innerHTML="";this._attributes=_4.create("table",{cellspacing:"0",cellpadding:"0"},this.attributeTable);var _59=_4.create("tbody",null,this._attributes);var _5a=this._currentFeature;var _5b=this._currentLInfo;var _5c=this._findFirst(_5b.types,"id",_5a.attributes[_5b.typeIdField]);var _5d=_5b.fieldInfos;_4.forEach(_5d,_4.hitch(this,"_createField",_5c,_59),this);},_createField:function(_5e,_5f,_60){var _61=this._currentLInfo;var _62=_60.field;if(this._isInFields(_62.name,_61.hideFields)){return;}var _63=_4.create("tr",null,_5f);var _64=_60.stringFieldOption===esri.dijit.AttributeInspector.STRING_FIELD_OPTION_RICHTEXT;var td=_4.create("td",{innerHTML:_60.label||_62.alias||_62.name,"class":"atiLabel"},_63);td=_4.create("td",null,_63);var _65=null;var _66=false;if(_60.customField){_4.place(_60.customField.domNode||_60.customField,_4.create("div",null,td),"first");_65=_60.customField;}else{if(_61.isEditable===false||_62.isEditable===false||_60.isEditable===false||_62.type==="esriFieldTypeOID"||_62.type==="esriFieldTypeGlobalID"){_66=true;}}if(!_65&&(_61.typeIdField&&_62.name.toLowerCase()==_61.typeIdField.toLowerCase())){_65=this._createTypeField(_62,_60,td);}else{if(!_65){_65=this._createDomainField(_62,_60,_5e,td);}}if(!_65){switch(_62.type){case "esriFieldTypeString":_65=this._createStringField(_62,_60,td);break;case "esriFieldTypeDate":_65=this._createDateField(_62,_60,td);break;case "esriFieldTypeInteger":case "esriFieldTypeSmallInteger":_65=this._createIntField(_62,_60,td);break;case "esriFieldTypeSingle":case "esriFieldTypeDouble":_65=this._createFltField(_62,_60,td);break;default:_65=this._createStringField(_62,_60,td);break;}}if(_60.tooltip&&_60.tooltip.length){this._toolTips.push(new _5.Tooltip({connectId:[_65.id],label:_60.tooltip}));}_65.onChange=_4.hitch(this,"onFieldValueChange",_60);_65.set("disabled",_66);_60.dijit=_65;},_createTypeField:function(_67,_68,_69){return new _5.form.FilteringSelect({"class":"atiField",name:_67.alias||_67.name,store:this._toStore(_4.map(this._currentLInfo.types,"return { id: item.id, name: item.name };")),searchAttr:"name"},_4.create("div",null,_69));},_createDomainField:function(_6a,_6b,_6c,_6d){var _6e=_6a.domain;if(_6c&&_6c.domains){if(_6c.domains[_6a.name]&&_6c.domains[_6a.name] instanceof esri.layers.InheritedDomain===false){_6e=_6c.domains[_6a.name];}}if(!_6e){return null;}if(_6e.codedValues){return new _5.form.FilteringSelect({"class":"atiField",name:_6a.alias||_6a.name,store:null,searchAttr:"name"},_4.create("div",null,_6d));}else{return new _5.form.NumberSpinner({"class":"atiField"},_4.create("div",null,_6d));}},_createStringField:function(_6f,_70,_71){var _72={"class":"atiField",trim:true,maxLength:_6f.length};if(_70.stringFieldOption===esri.dijit.AttributeInspector.STRING_FIELD_OPTION_TEXTAREA){_72["class"]+=" atiTextAreaField";return new _5.form.SimpleTextarea(_72,_4.create("div",null,_71));}else{if(_70.stringFieldOption===esri.dijit.AttributeInspector.STRING_FIELD_OPTION_RICHTEXT){_72["class"]+=" atiRichTextField";_72.height="100%";_72.width="100%";_72.plugins=_70.richTextPlugins||["bold","italic","underline","foreColor","hiliteColor","|","justifyLeft","justifyCenter","justifyRight","justifyFull","|","insertOrderedList","insertUnorderedList","indent","outdent","|","createLink"];return new _5.Editor(_72,_4.create("div",null,_71));}else{return new _5.form.TextBox(_72,_4.create("div",null,_71));}}},_createDateField:function(_73,_74,_75){return new _5.form.DateTextBox({"class":"atiField",trim:true},_4.create("div",null,_75));},_createIntField:function(_76,_77,_78){return new _5.form.NumberTextBox({"class":"atiField",constraints:{places:0},invalidMessage:this.NLS_validationInt,trim:true},_4.create("div",null,_78));},_createFltField:function(_79,_7a,_7b){return new _5.form.NumberTextBox({"class":"atiField",trim:true,invalidMessage:this.NLS_validationFlt},_4.create("div",null,_7b));},_toStore:function(_7c){return new _4.data.ItemFileReadStore({data:{identifier:"id",label:"name",items:_7c}});},_connect:function(_7d,evt,_7e){this._aiConnects.push(_4.connect(_7d,evt,_7e));},_destroyAttributeTable:function(){var _7f=this.layerInfos;_4.forEach(_7f,function(_80){var _81=_80.fieldInfos;_4.forEach(_81,function(_82){var _83=_82.dijit;if(_83){_83._onChangeHandle=null;if(_82.customField){return;}if(_83.destroyRecursive){_83.destroyRecursive();}else{if(_83.destroy){_83.destroy();}}}_82.dijit=null;},this);},this);var _84=this._toolTips;_4.forEach(_84,"item.destroy(); delete item;");this._toolTips=[];if(this._attributes){_4.destroy(this._attributes);}}});_4.mixin(esri.dijit.AttributeInspector,{STRING_FIELD_OPTION_RICHTEXT:"richtext",STRING_FIELD_OPTION_TEXTAREA:"textarea",STRING_FIELD_OPTION_TEXTBOX:"textbox"});}}};});