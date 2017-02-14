/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.sql._base"],["require","dojox.sql._crypto"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.sql._base"]){_4._hasResource["dojox.sql._base"]=true;_4.provide("dojox.sql._base");_4.require("dojox.sql._crypto");_4.mixin(_6.sql,{dbName:null,debug:(_4.exists("dojox.sql.debug")?_6.sql.debug:false),open:function(_7){if(this._dbOpen&&(!_7||_7==this.dbName)){return;}if(!this.dbName){this.dbName="dot_store_"+window.location.href.replace(/[^0-9A-Za-z_]/g,"_");if(this.dbName.length>63){this.dbName=this.dbName.substring(0,63);}}if(!_7){_7=this.dbName;}try{this._initDb();this.db.open(_7);this._dbOpen=true;}catch(exp){throw exp.message||exp;}},close:function(_8){if(_4.isIE){return;}if(!this._dbOpen&&(!_8||_8==this.dbName)){return;}if(!_8){_8=this.dbName;}try{this.db.close(_8);this._dbOpen=false;}catch(exp){throw exp.message||exp;}},_exec:function(_9){try{this._initDb();if(!this._dbOpen){this.open();this._autoClose=true;}var _a=null;var _b=null;var _c=null;var _d=_4._toArray(_9);_a=_d.splice(0,1)[0];if(this._needsEncrypt(_a)||this._needsDecrypt(_a)){_b=_d.splice(_d.length-1,1)[0];_c=_d.splice(_d.length-1,1)[0];}if(this.debug){this._printDebugSQL(_a,_d);}var _e;if(this._needsEncrypt(_a)){_e=new _6.sql._SQLCrypto("encrypt",_a,_c,_d,_b);return null;}else{if(this._needsDecrypt(_a)){_e=new _6.sql._SQLCrypto("decrypt",_a,_c,_d,_b);return null;}}var rs=this.db.execute(_a,_d);rs=this._normalizeResults(rs);if(this._autoClose){this.close();}return rs;}catch(exp){exp=exp.message||exp;console.debug("SQL Exception: "+exp);if(this._autoClose){try{this.close();}catch(e){console.debug("Error closing database: "+e.message||e);}}throw exp;}return null;},_initDb:function(){if(!this.db){try{this.db=google.gears.factory.create("beta.database","1.0");}catch(exp){_4.setObject("google.gears.denied",true);if(_6.off){_6.off.onFrameworkEvent("coreOperationFailed");}throw "Google Gears must be allowed to run";}}},_printDebugSQL:function(_f,_10){var msg="dojox.sql(\""+_f+"\"";for(var i=0;i<_10.length;i++){if(typeof _10[i]=="string"){msg+=", \""+_10[i]+"\"";}else{msg+=", "+_10[i];}}msg+=")";console.debug(msg);},_normalizeResults:function(rs){var _11=[];if(!rs){return [];}while(rs.isValidRow()){var row={};for(var i=0;i<rs.fieldCount();i++){var _12=rs.fieldName(i);var _13=rs.field(i);row[_12]=_13;}_11.push(row);rs.next();}rs.close();return _11;},_needsEncrypt:function(sql){return /encrypt\([^\)]*\)/i.test(sql);},_needsDecrypt:function(sql){return /decrypt\([^\)]*\)/i.test(sql);}});_4.declare("dojox.sql._SQLCrypto",null,{constructor:function(_14,sql,_15,_16,_17){if(_14=="encrypt"){this._execEncryptSQL(sql,_15,_16,_17);}else{this._execDecryptSQL(sql,_15,_16,_17);}},_execEncryptSQL:function(sql,_18,_19,_1a){var _1b=this._stripCryptoSQL(sql);var _1c=this._flagEncryptedArgs(sql,_19);var _1d=this;this._encrypt(_1b,_18,_19,_1c,function(_1e){var _1f=false;var _20=[];var exp=null;try{_20=_6.sql.db.execute(_1b,_1e);}catch(execError){_1f=true;exp=execError.message||execError;}if(exp!=null){if(_6.sql._autoClose){try{_6.sql.close();}catch(e){}}_1a(null,true,exp.toString());return;}_20=_6.sql._normalizeResults(_20);if(_6.sql._autoClose){_6.sql.close();}if(_6.sql._needsDecrypt(sql)){var _21=_1d._determineDecryptedColumns(sql);_1d._decrypt(_20,_21,_18,function(_22){_1a(_22,false,null);});}else{_1a(_20,false,null);}});},_execDecryptSQL:function(sql,_23,_24,_25){var _26=this._stripCryptoSQL(sql);var _27=this._determineDecryptedColumns(sql);var _28=false;var _29=[];var exp=null;try{_29=_6.sql.db.execute(_26,_24);}catch(execError){_28=true;exp=execError.message||execError;}if(exp!=null){if(_6.sql._autoClose){try{_6.sql.close();}catch(e){}}_25(_29,true,exp.toString());return;}_29=_6.sql._normalizeResults(_29);if(_6.sql._autoClose){_6.sql.close();}this._decrypt(_29,_27,_23,function(_2a){_25(_2a,false,null);});},_encrypt:function(sql,_2b,_2c,_2d,_2e){this._totalCrypto=0;this._finishedCrypto=0;this._finishedSpawningCrypto=false;this._finalArgs=_2c;for(var i=0;i<_2c.length;i++){if(_2d[i]){var _2f=_2c[i];var _30=i;this._totalCrypto++;_6.sql._crypto.encrypt(_2f,_2b,_4.hitch(this,function(_31){this._finalArgs[_30]=_31;this._finishedCrypto++;if(this._finishedCrypto>=this._totalCrypto&&this._finishedSpawningCrypto){_2e(this._finalArgs);}}));}}this._finishedSpawningCrypto=true;},_decrypt:function(_32,_33,_34,_35){this._totalCrypto=0;this._finishedCrypto=0;this._finishedSpawningCrypto=false;this._finalResultSet=_32;for(var i=0;i<_32.length;i++){var row=_32[i];for(var _36 in row){if(_33=="*"||_33[_36]){this._totalCrypto++;var _37=row[_36];this._decryptSingleColumn(_36,_37,_34,i,function(_38){_35(_38);});}}}this._finishedSpawningCrypto=true;},_stripCryptoSQL:function(sql){sql=sql.replace(/DECRYPT\(\*\)/ig,"*");var _39=sql.match(/ENCRYPT\([^\)]*\)/ig);if(_39!=null){for(var i=0;i<_39.length;i++){var _3a=_39[i];var _3b=_3a.match(/ENCRYPT\(([^\)]*)\)/i)[1];sql=sql.replace(_3a,_3b);}}_39=sql.match(/DECRYPT\([^\)]*\)/ig);if(_39!=null){for(i=0;i<_39.length;i++){var _3c=_39[i];var _3d=_3c.match(/DECRYPT\(([^\)]*)\)/i)[1];sql=sql.replace(_3c,_3d);}}return sql;},_flagEncryptedArgs:function(sql,_3e){var _3f=new RegExp(/([\"][^\"]*\?[^\"]*[\"])|([\'][^\']*\?[^\']*[\'])|(\?)/ig);var _40;var _41=0;var _42=[];while((_40=_3f.exec(sql))!=null){var _43=RegExp.lastMatch+"";if(/^[\"\']/.test(_43)){continue;}var _44=false;if(/ENCRYPT\([^\)]*$/i.test(RegExp.leftContext)){_44=true;}_42[_41]=_44;_41++;}return _42;},_determineDecryptedColumns:function(sql){var _45={};if(/DECRYPT\(\*\)/i.test(sql)){_45="*";}else{var _46=/DECRYPT\((?:\s*\w*\s*\,?)*\)/ig;var _47=_46.exec(sql);while(_47){var _48=new String(RegExp.lastMatch);var _49=_48.replace(/DECRYPT\(/i,"");_49=_49.replace(/\)/,"");_49=_49.split(/\s*,\s*/);_4.forEach(_49,function(_4a){if(/\s*\w* AS (\w*)/i.test(_4a)){_4a=_4a.match(/\s*\w* AS (\w*)/i)[1];}_45[_4a]=true;});_47=_46.exec(sql);}}return _45;},_decryptSingleColumn:function(_4b,_4c,_4d,_4e,_4f){_6.sql._crypto.decrypt(_4c,_4d,_4.hitch(this,function(_50){this._finalResultSet[_4e][_4b]=_50;this._finishedCrypto++;if(this._finishedCrypto>=this._totalCrypto&&this._finishedSpawningCrypto){_4f(this._finalResultSet);}}));}});(function(){var _51=_6.sql;_6.sql=new Function("return dojox.sql._exec(arguments);");_4.mixin(_6.sql,_51);})();}}};});