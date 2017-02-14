/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.string.BidiComplex"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.string.BidiComplex"]){_4._hasResource["dojox.string.BidiComplex"]=true;_4.provide("dojox.string.BidiComplex");_4.experimental("dojox.string.BidiComplex");(function(){var _7=[];_6.string.BidiComplex.attachInput=function(_8,_9){_8.alt=_9;_4.connect(_8,"onkeydown",this,"_ceKeyDown");_4.connect(_8,"onkeyup",this,"_ceKeyUp");_4.connect(_8,"oncut",this,"_ceCutText");_4.connect(_8,"oncopy",this,"_ceCopyText");_8.value=_6.string.BidiComplex.createDisplayString(_8.value,_8.alt);};_6.string.BidiComplex.createDisplayString=function(_a,_b){_a=_6.string.BidiComplex.stripSpecialCharacters(_a);var _c=_6.string.BidiComplex._parse(_a,_b);var _d="‪"+_a;var _e=1;_4.forEach(_c,function(n){if(n!=null){var _f=_d.substring(0,n+_e);var _10=_d.substring(n+_e,_d.length);_d=_f+"‎"+_10;_e++;}});return _d;};_6.string.BidiComplex.stripSpecialCharacters=function(str){return str.replace(/[\u200E\u200F\u202A-\u202E]/g,"");};_6.string.BidiComplex._ceKeyDown=function(_11){var _12=_4.isIE?_11.srcElement:_11.target;_7=_12.value;};_6.string.BidiComplex._ceKeyUp=function(_13){var LRM="‎";var _14=_4.isIE?_13.srcElement:_13.target;var _15=_14.value;var _16=_13.keyCode;if((_16==_4.keys.HOME)||(_16==_4.keys.END)||(_16==_4.keys.SHIFT)){return;}var _17,_18;var _19=_6.string.BidiComplex._getCaretPos(_13,_14);if(_19){_17=_19[0];_18=_19[1];}if(_4.isIE){var _1a=_17,_1b=_18;if(_16==_4.keys.LEFT_ARROW){if((_15.charAt(_18-1)==LRM)&&(_17==_18)){_6.string.BidiComplex._setSelectedRange(_14,_17-1,_18-1);}return;}if(_16==_4.keys.RIGHT_ARROW){if(_15.charAt(_18-1)==LRM){_1b=_18+1;if(_17==_18){_1a=_17+1;}}_6.string.BidiComplex._setSelectedRange(_14,_1a,_1b);return;}}else{if(_16==_4.keys.LEFT_ARROW){if(_15.charAt(_18-1)==LRM){_6.string.BidiComplex._setSelectedRange(_14,_17-1,_18-1);}return;}if(_16==_4.keys.RIGHT_ARROW){if(_15.charAt(_18-1)==LRM){_6.string.BidiComplex._setSelectedRange(_14,_17+1,_18+1);}return;}}var _1c=_6.string.BidiComplex.createDisplayString(_15,_14.alt);if(_15!=_1c){window.status=_15+" c="+_18;_14.value=_1c;if((_16==_4.keys.DELETE)&&(_1c.charAt(_18)==LRM)){_14.value=_1c.substring(0,_18)+_1c.substring(_18+2,_1c.length);}if(_16==_4.keys.DELETE){_6.string.BidiComplex._setSelectedRange(_14,_17,_18);}else{if(_16==_4.keys.BACKSPACE){if((_7.length>=_18)&&(_7.charAt(_18-1)==LRM)){_6.string.BidiComplex._setSelectedRange(_14,_17-1,_18-1);}else{_6.string.BidiComplex._setSelectedRange(_14,_17,_18);}}else{if(_14.value.charAt(_18)!=LRM){_6.string.BidiComplex._setSelectedRange(_14,_17+1,_18+1);}}}}};_6.string.BidiComplex._processCopy=function(_1d,_1e,_1f){if(_1e==null){if(_4.isIE){var _20=document.selection.createRange();_1e=_20.text;}else{_1e=_1d.value.substring(_1d.selectionStart,_1d.selectionEnd);}}var _21=_6.string.BidiComplex.stripSpecialCharacters(_1e);if(_4.isIE){window.clipboardData.setData("Text",_21);}return true;};_6.string.BidiComplex._ceCopyText=function(_22){if(_4.isIE){_22.returnValue=false;}return _6.string.BidiComplex._processCopy(_22,null,false);};_6.string.BidiComplex._ceCutText=function(_23){var ret=_6.string.BidiComplex._processCopy(_23,null,false);if(!ret){return false;}if(_4.isIE){document.selection.clear();}else{var _24=_23.selectionStart;_23.value=_23.value.substring(0,_24)+_23.value.substring(_23.selectionEnd);_23.setSelectionRange(_24,_24);}return true;};_6.string.BidiComplex._getCaretPos=function(_25,_26){if(_4.isIE){var _27=0,_28=document.selection.createRange().duplicate(),_29=_28.duplicate(),_2a=_28.text.length;if(_26.type=="textarea"){_29.moveToElementText(_26);}else{_29.expand("textedit");}while(_28.compareEndPoints("StartToStart",_29)>0){_28.moveStart("character",-1);++_27;}return [_27,_27+_2a];}return [_25.target.selectionStart,_25.target.selectionEnd];};_6.string.BidiComplex._setSelectedRange=function(_2b,_2c,_2d){if(_4.isIE){var _2e=_2b.createTextRange();if(_2e){if(_2b.type=="textarea"){_2e.moveToElementText(_2b);}else{_2e.expand("textedit");}_2e.collapse();_2e.moveEnd("character",_2d);_2e.moveStart("character",_2c);_2e.select();}}else{_2b.selectionStart=_2c;_2b.selectionEnd=_2d;}};var _2f=function(c){return (c>="0"&&c<="9")||(c>"ÿ");};var _30=function(c){return (c>="A"&&c<="Z")||(c>="a"&&c<="z");};var _31=function(_32,i,_33){while(i>0){if(i==_33){return false;}i--;if(_2f(_32.charAt(i))){return true;}if(_30(_32.charAt(i))){return false;}}return false;};_6.string.BidiComplex._parse=function(str,_34){var _35=-1,_36=[];var _37={FILE_PATH:"/\\:.",URL:"/:.?=&#",XPATH:"/\\:.<>=[]",EMAIL:"<>@.,;"}[_34];switch(_34){case "FILE_PATH":case "URL":case "XPATH":_4.forEach(str,function(ch,i){if(_37.indexOf(ch)>=0&&_31(str,i,_35)){_35=i;_36.push(i);}});break;case "EMAIL":var _38=false;_4.forEach(str,function(ch,i){if(ch=="\""){if(_31(str,i,_35)){_35=i;_36.push(i);}i++;var i1=str.indexOf("\"",i);if(i1>=i){i=i1;}if(_31(str,i,_35)){_35=i;_36.push(i);}}if(_37.indexOf(ch)>=0&&_31(str,i,_35)){_35=i;_36.push(i);}});}return _36;};})();}}};});