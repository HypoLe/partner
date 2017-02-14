/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {defineResource:function(_4,_5,_6){require.paths.unshift("/opt/less/lib","C:/less/lib");var fs=require("fs"),_7=require("path"),_8=require("less");var _9={compress:false,optimization:1,silent:false};var _a=[].concat(fs.readdirSync("."),fs.readdirSync("form").map(function(_b){return "form/"+_b;}),fs.readdirSync("layout").map(function(_c){return "layout/"+_c;})),_d=_a.filter(function(_e){return _e&&_e!="variables.less"&&/\.less$/.test(_e);});_d.forEach(function(_f){console.log("=== "+_f);fs.readFile(_f,"utf-8",function(e,_10){if(e){console.error("lessc: "+e.message);process.exit(1);}new (_8.Parser)({paths:[_7.dirname(_f)],optimization:_9.optimization,filename:_f}).parse(_10,function(err,_11){if(err){_8.writeError(err,_9);process.exit(1);}else{try{var css=_11.toCSS({compress:_9.compress}),_12=_f.replace(".less",".css");fd=fs.openSync(_12,"w");fs.writeSync(fd,css,0,"utf8");}catch(e){_8.writeError(e,_9);process.exit(2);}}});});});}};});