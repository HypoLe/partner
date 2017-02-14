/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.xmpp.TransportSession"],["require","dojox.xmpp.bosh"],["require","dojox.xmpp.util"],["require","dojox.data.dom"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.xmpp.TransportSession"]){_4._hasResource["dojox.xmpp.TransportSession"]=true;_4.provide("dojox.xmpp.TransportSession");_4.require("dojox.xmpp.bosh");_4.require("dojox.xmpp.util");_4.require("dojox.data.dom");_6.xmpp.TransportSession=function(_7){this.sendTimeout=(this.wait+20)*1000;if(_7&&_4.isObject(_7)){_4.mixin(this,_7);if(this.useScriptSrcTransport){this.transportIframes=[];}}};_4.extend(_6.xmpp.TransportSession,{rid:0,hold:1,polling:1000,secure:false,wait:60,lang:"en",submitContentType:"text/xml; charset=utf=8",serviceUrl:"/httpbind",defaultResource:"dojoIm",domain:"imserver.com",sendTimeout:0,useScriptSrcTransport:false,keepAliveTimer:null,state:"NotReady",transmitState:"Idle",protocolPacketQueue:[],outboundQueue:[],outboundRequests:{},inboundQueue:[],deferredRequests:{},matchTypeIdAttribute:{},open:function(){this.status="notReady";this.rid=Math.round(Math.random()*1000000000);this.protocolPacketQueue=[];this.outboundQueue=[];this.outboundRequests={};this.inboundQueue=[];this.deferredRequests={};this.matchTypeIdAttribute={};this.keepAliveTimer=setTimeout(_4.hitch(this,"_keepAlive"),10000);if(this.useScriptSrcTransport){_6.xmpp.bosh.initialize({iframes:this.hold+1,load:_4.hitch(this,function(){this._sendLogin();})});}else{this._sendLogin();}},_sendLogin:function(){var _8=this.rid++;var _9={content:this.submitContentType,hold:this.hold,rid:_8,to:this.domain,secure:this.secure,wait:this.wait,"xml:lang":this.lang,"xmpp:version":"1.0",xmlns:_6.xmpp.xmpp.BODY_NS,"xmlns:xmpp":"urn:xmpp:xbosh"};var _a=_6.xmpp.util.createElement("body",_9,true);this.addToOutboundQueue(_a,_8);},_sendRestart:function(){var _b=this.rid++;var _c={rid:_b,sid:this.sid,to:this.domain,"xmpp:restart":"true","xml:lang":this.lang,xmlns:_6.xmpp.xmpp.BODY_NS,"xmlns:xmpp":"urn:xmpp:xbosh"};var _d=_6.xmpp.util.createElement("body",_c,true);this.addToOutboundQueue(_d,_b);},processScriptSrc:function(_e,_f){var _10=_6.xml.parser.parse(_e,"text/xml");if(_10){this.processDocument(_10,_f);}else{}},_keepAlive:function(){if(this.state=="wait"||this.isTerminated()){return;}this._dispatchPacket();this.keepAliveTimer=setTimeout(_4.hitch(this,"_keepAlive"),10000);},close:function(_11){var rid=this.rid++;var req={sid:this.sid,rid:rid,type:"terminate"};var _12=null;if(_11){_12=new _6.string.Builder(_6.xmpp.util.createElement("body",req,false));_12.append(_11);_12.append("</body>");}else{_12=new _6.string.Builder(_6.xmpp.util.createElement("body",req,false));}this.addToOutboundQueue(_12.toString(),rid);this.state=="Terminate";},dispatchPacket:function(msg,_13,_14,_15){if(msg){this.protocolPacketQueue.push(msg);}var def=new _4.Deferred();if(_13&&_14){def.protocolMatchType=_13;def.matchId=_14;def.matchProperty=_15||"id";if(def.matchProperty!="id"){this.matchTypeIdAttribute[_13]=def.matchProperty;}}this.deferredRequests[def.protocolMatchType+"-"+def.matchId]=def;if(!this.dispatchTimer){this.dispatchTimer=setTimeout(_4.hitch(this,"_dispatchPacket"),600);}return def;},_dispatchPacket:function(){clearTimeout(this.dispatchTimer);delete this.dispatchTimer;if(!this.sid){console.debug("TransportSession::dispatchPacket() No SID, packet dropped.");return;}if(!this.authId){console.debug("TransportSession::dispatchPacket() No authId, packet dropped [FIXME]");return;}if(this.transmitState!="error"&&(this.protocolPacketQueue.length==0)&&(this.outboundQueue.length>0)){return;}if(this.state=="wait"||this.isTerminated()){return;}var req={sid:this.sid,xmlns:_6.xmpp.xmpp.BODY_NS};var _16;if(this.protocolPacketQueue.length>0){req.rid=this.rid++;_16=new _6.string.Builder(_6.xmpp.util.createElement("body",req,false));_16.append(this.processProtocolPacketQueue());_16.append("</body>");delete this.lastPollTime;}else{if(this.lastPollTime){var now=new Date().getTime();if(now-this.lastPollTime<this.polling){this.dispatchTimer=setTimeout(_4.hitch(this,"_dispatchPacket"),this.polling-(now-this.lastPollTime)+10);return;}}req.rid=this.rid++;this.lastPollTime=new Date().getTime();_16=new _6.string.Builder(_6.xmpp.util.createElement("body",req,true));}this.addToOutboundQueue(_16.toString(),req.rid);},redispatchPacket:function(rid){var env=this.outboundRequests[rid];this.sendXml(env,rid);},addToOutboundQueue:function(msg,rid){this.outboundQueue.push({msg:msg,rid:rid});this.outboundRequests[rid]=msg;this.sendXml(msg,rid);},removeFromOutboundQueue:function(rid){for(var i=0;i<this.outboundQueue.length;i++){if(rid==this.outboundQueue[i]["rid"]){this.outboundQueue.splice(i,1);break;}}delete this.outboundRequests[rid];},processProtocolPacketQueue:function(){var _17=new _6.string.Builder();for(var i=0;i<this.protocolPacketQueue.length;i++){_17.append(this.protocolPacketQueue[i]);}this.protocolPacketQueue=[];return _17.toString();},sendXml:function(_18,rid){if(this.isTerminated()){return false;}this.transmitState="transmitting";var def=null;if(this.useScriptSrcTransport){def=_6.xmpp.bosh.get({rid:rid,url:this.serviceUrl+"?"+encodeURIComponent(_18),error:_4.hitch(this,function(res,io){this.setState("Terminate","error");return false;}),timeout:this.sendTimeout});}else{def=_4.rawXhrPost({contentType:"text/xml",url:this.serviceUrl,postData:_18,handleAs:"xml",error:_4.hitch(this,function(res,io){return this.processError(io.xhr.responseXML,io.xhr.status,rid);}),timeout:this.sendTimeout});}def.addCallback(this,function(res){return this.processDocument(res,rid);});return def;},processDocument:function(doc,rid){if(this.isTerminated()||!doc.firstChild){return false;}this.transmitState="idle";var _19=doc.firstChild;if(_19.nodeName!="body"){}if(this.outboundQueue.length<1){return false;}var _1a=this.outboundQueue[0]["rid"];if(rid==_1a){this.removeFromOutboundQueue(rid);this.processResponse(_19,rid);this.processInboundQueue();}else{var gap=rid-_1a;if(gap<this.hold+2){this.addToInboundQueue(doc,rid);}else{}}return doc;},processInboundQueue:function(){while(this.inboundQueue.length>0){var _1b=this.inboundQueue.shift();this.processDocument(_1b["doc"],_1b["rid"]);}},addToInboundQueue:function(doc,rid){for(var i=0;i<this.inboundQueue.length;i++){if(rid<this.inboundQueue[i]["rid"]){continue;}this.inboundQueue.splice(i,0,{doc:doc,rid:rid});}},processResponse:function(_1c,rid){if(_1c.getAttribute("type")=="terminate"){var _1d=_1c.firstChild.firstChild;var _1e="";if(_1d.nodeName=="conflict"){_1e="conflict";}this.setState("Terminate",_1e);return;}if((this.state!="Ready")&&(this.state!="Terminate")){var sid=_1c.getAttribute("sid");if(sid){this.sid=sid;}else{throw new Error("No sid returned during xmpp session startup");}this.authId=_1c.getAttribute("authid");if(this.authId==""){if(this.authRetries--<1){console.error("Unable to obtain Authorization ID");this.terminateSession();}}this.wait=_1c.getAttribute("wait");if(_1c.getAttribute("polling")){this.polling=parseInt(_1c.getAttribute("polling"))*1000;}this.inactivity=_1c.getAttribute("inactivity");this.setState("Ready");}_4.forEach(_1c.childNodes,function(_1f){this.processProtocolResponse(_1f,rid);},this);if(this.transmitState=="idle"){this.dispatchPacket();}},processProtocolResponse:function(msg,rid){this.onProcessProtocolResponse(msg);var key=msg.nodeName+"-"+msg.getAttribute("id");var def=this.deferredRequests[key];if(def){def.callback(msg);delete this.deferredRequests[key];}},setState:function(_20,_21){if(this.state!=_20){if(this["on"+_20]){this["on"+_20](_20,this.state,_21);}this.state=_20;}},isTerminated:function(){return this.state=="Terminate";},processError:function(err,_22,rid){if(this.isTerminated()){return false;}if(_22!=200){if(_22>=400&&_22<500){this.setState("Terminate",_23);return false;}else{this.removeFromOutboundQueue(rid);setTimeout(_4.hitch(this,function(){this.dispatchPacket();}),200);return true;}return false;}if(err&&err.dojoType&&err.dojoType=="timeout"){}this.removeFromOutboundQueue(rid);if(err&&err.firstChild){if(err.firstChild.getAttribute("type")=="terminate"){var _24=err.firstChild.firstChild;var _23="";if(_24&&_24.nodeName=="conflict"){_23="conflict";}this.setState("Terminate",_23);return false;}}this.transmitState="error";setTimeout(_4.hitch(this,function(){this.dispatchPacket();}),200);return true;},onTerminate:function(_25,_26,_27){},onProcessProtocolResponse:function(msg){},onReady:function(_28,_29){}});}}};});