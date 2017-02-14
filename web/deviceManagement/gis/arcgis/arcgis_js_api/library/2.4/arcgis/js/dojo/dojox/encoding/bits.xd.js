/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.encoding.bits"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.encoding.bits"]){_4._hasResource["dojox.encoding.bits"]=true;_4.provide("dojox.encoding.bits");_4.getObject("encoding.bits",true,_6);_6.encoding.bits.OutputStream=function(){this.reset();};_4.extend(_6.encoding.bits.OutputStream,{reset:function(){this.buffer=[];this.accumulator=0;this.available=8;},putBits:function(_7,_8){while(_8){var w=Math.min(_8,this.available);var v=(w<=_8?_7>>>(_8-w):_7)<<(this.available-w);this.accumulator|=v&(255>>>(8-this.available));this.available-=w;if(!this.available){this.buffer.push(this.accumulator);this.accumulator=0;this.available=8;}_8-=w;}},getWidth:function(){return this.buffer.length*8+(8-this.available);},getBuffer:function(){var b=this.buffer;if(this.available<8){b.push(this.accumulator&(255<<this.available));}this.reset();return b;}});_6.encoding.bits.InputStream=function(_9,_a){this.buffer=_9;this.width=_a;this.bbyte=this.bit=0;};_4.extend(_6.encoding.bits.InputStream,{getBits:function(_b){var r=0;while(_b){var w=Math.min(_b,8-this.bit);var v=this.buffer[this.bbyte]>>>(8-this.bit-w);r<<=w;r|=v&~(~0<<w);this.bit+=w;if(this.bit==8){++this.bbyte;this.bit=0;}_b-=w;}return r;},getWidth:function(){return this.width-this.bbyte*8-this.bit;}});}}};});