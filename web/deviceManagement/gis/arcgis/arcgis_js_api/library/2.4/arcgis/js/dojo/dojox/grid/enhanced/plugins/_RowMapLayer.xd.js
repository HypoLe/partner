/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.grid.enhanced.plugins._RowMapLayer"],["require","dojox.grid.enhanced.plugins._StoreLayer"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.grid.enhanced.plugins._RowMapLayer"]){_4._hasResource["dojox.grid.enhanced.plugins._RowMapLayer"]=true;_4.provide("dojox.grid.enhanced.plugins._RowMapLayer");_4.require("dojox.grid.enhanced.plugins._StoreLayer");(function(){var _7=function(a){a.sort(function(v1,v2){return v1-v2;});var _8=[[a[0]]];for(var i=1,j=0;i<a.length;++i){if(a[i]==a[i-1]+1){_8[j].push(a[i]);}else{_8[++j]=[a[i]];}}return _8;},_9=function(_a,_b){return _b?_4.hitch(_a||_4.global,_b):function(){};};_4.declare("dojox.grid.enhanced.plugins._RowMapLayer",_6.grid.enhanced.plugins._StoreLayer,{tags:["reorder"],constructor:function(_c){this._map={};this._revMap={};this.grid=_c;this._oldOnDelete=_c._onDelete;var _d=this;_c._onDelete=function(_e){_d._onDelete(_e);_d._oldOnDelete.call(_c,_e);};this._oldSort=_c.sort;_c.sort=function(){_d.clearMapping();_d._oldSort.apply(_c,arguments);};},uninitialize:function(){this.grid._onDelete=this._oldOnDelete;this.grid.sort=this._oldSort;},setMapping:function(_f){this._store.forEachLayer(function(_10){if(_10.name()==="rowmap"){return false;}else{if(_10.onRowMappingChange){_10.onRowMappingChange(_f);}}return true;},false);var _11,to,_12,_13={};for(_11 in _f){_11=parseInt(_11,10);to=_f[_11];if(typeof to=="number"){if(_11 in this._revMap){_12=this._revMap[_11];delete this._revMap[_11];}else{_12=_11;}if(_12==to){delete this._map[_12];_13[to]="eq";}else{this._map[_12]=to;_13[to]=_12;}}}for(to in _13){if(_13[to]==="eq"){delete this._revMap[parseInt(to,10)];}else{this._revMap[parseInt(to,10)]=_13[to];}}},clearMapping:function(){this._map={};this._revMap={};},_onDelete:function(_14){var idx=this.grid._getItemIndex(_14,true);if(idx in this._revMap){var _15=[],r,i,_16=this._revMap[idx];delete this._map[_16];delete this._revMap[idx];for(r in this._revMap){r=parseInt(r,10);if(this._revMap[r]>_16){--this._revMap[r];}}for(r in this._revMap){r=parseInt(r,10);if(r>idx){_15.push(r);}}_15.sort(function(a,b){return b-a;});for(i=_15.length-1;i>=0;--i){r=_15[i];this._revMap[r-1]=this._revMap[r];delete this._revMap[r];}this._map={};for(r in this._revMap){this._map[this._revMap[r]]=r;}}},_fetch:function(_17){var _18=0,r;var _19=_17.start||0;for(r in this._revMap){r=parseInt(r,10);if(r>=_19){++_18;}}if(_18>0){var _1a=[],i,map={},_1b=_17.count>0?_17.count:-1;if(_1b>0){for(i=0;i<_1b;++i){r=_19+i;r=r in this._revMap?this._revMap[r]:r;map[r]=i;_1a.push(r);}}else{for(i=0;;++i){r=_19+i;if(r in this._revMap){--_18;r=this._revMap[r];}map[r]=i;_1a.push(r);if(_18<=0){break;}}}this._subFetch(_17,this._getRowArrays(_1a),0,[],map,_17.onComplete,_19,_1b);return _17;}else{return _4.hitch(this._store,this._originFetch)(_17);}},_getRowArrays:function(_1c){return _7(_1c);},_subFetch:function(_1d,_1e,_1f,_20,map,_21,_22,_23){var arr=_1e[_1f],_24=this;var _25=_1d.start=arr[0];_1d.count=arr[arr.length-1]-arr[0]+1;_1d.onComplete=function(_26){_4.forEach(_26,function(_27,i){var r=_25+i;if(r in map){_20[map[r]]=_27;}});if(++_1f==_1e.length){if(_23>0){_1d.start=_22;_1d.count=_23;_1d.onComplete=_21;_9(_1d.scope,_21)(_20,_1d);}else{_1d.start=_1d.start+_26.length;delete _1d.count;_1d.onComplete=function(_28){_20=_20.concat(_28);_1d.start=_22;_1d.onComplete=_21;_9(_1d.scope,_21)(_20,_1d);};_24.originFetch(_1d);}}else{_24._subFetch(_1d,_1e,_1f,_20,map,_21,_22,_23);}};_24.originFetch(_1d);}});})();}}};});