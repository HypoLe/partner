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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.tasks.gp"],["require","esri.tasks._task"],["require","esri.layers.agsdynamic"],["require","dojo.date.locale"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.tasks.gp"]){_4._hasResource["esri.tasks.gp"]=true;_4.provide("esri.tasks.gp");_4.require("esri.tasks._task");_4.require("esri.layers.agsdynamic");_4.require("dojo.date.locale");_4.declare("esri.tasks.Geoprocessor",esri.tasks._Task,{constructor:function(_7){this._jobUpdateHandler=_4.hitch(this,this._jobUpdateHandler);this._getJobStatus=_4.hitch(this,this._getJobStatus);this._getResultDataHandler=_4.hitch(this,this._getResultDataHandler);this._getResultImageHandler=_4.hitch(this,this._getResultImageHandler);this._executeHandler=_4.hitch(this,this._executeHandler);this._updateTimers=[];},updateDelay:1000,processSpatialReference:null,outputSpatialReference:null,outSpatialReference:null,setUpdateDelay:function(_8){this.updateDelay=_8;},setProcessSpatialReference:function(sr){this.processSpatialReference=sr;},setOutputSpatialReference:function(sr){this._setOutSR(sr);},setOutSpatialReference:function(sr){this._setOutSR(sr);},__msigns:[{n:"execute",c:3,a:[{i:0,p:["*"]}],e:2,f:1},{n:"submitJob",c:4,a:[{i:0,p:["*"]}],e:3}],_setOutSR:function(sr){this.outSpatialReference=this.outputSpatialReference=sr;},_getOutSR:function(){return this.outSpatialReference||this.outputSpatialReference;},_gpEncode:function(_9,_a,_b){for(var i in _9){var _c=_9[i];if(_4.isArray(_c)){_9[i]=_4.toJson(_4.map(_c,function(_d){return this._gpEncode({item:_d},true).item;},this));}else{if(_c instanceof Date){_9[i]=_c.getTime();}}}return this._encode(_9,_a,_b);},_decode:function(_e){var _f=_e.dataType,_10,_11=new esri.tasks.ParameterValue(_e);if(_4.indexOf(["GPBoolean","GPDouble","GPLong","GPString"],_f)!==-1){return _11;}if(_f==="GPLinearUnit"){_11.value=new esri.tasks.LinearUnit(_11.value);}else{if(_f==="GPFeatureRecordSetLayer"||_f==="GPRecordSet"){_11.value=new esri.tasks.FeatureSet(_11.value);}else{if(_f==="GPDataFile"){_11.value=new esri.tasks.DataFile(_11.value);}else{if(_f==="GPDate"){_10=_11.value;if(_4.isString(_10)){_11.value=new esri.tasks.Date({date:_10});}else{_11.value=new Date(_10);}}else{if(_f==="GPRasterData"||_f==="GPRasterDataLayer"){var _12=_e.value.mapImage;if(_12){_11.value=new esri.layers.MapImage(_12);}else{_11.value=new esri.tasks.RasterData(_11.value);}}else{if(_f.indexOf("GPMultiValue:")!==-1){var _13=_f.split(":")[1];_10=_11.value;_11.value=_4.map(_10,function(_14){return this._decode({paramName:"_name",dataType:_13,value:_14}).value;},this);}else{console.log(this.declaredClass+" : "+esri.bundle.tasks.gp.gpDataTypeNotHandled+" : "+_11.dataType);_11=null;}}}}}}return _11;},submitJob:function(_15,_16,_17,_18,_19){var _1a=this._getOutSR();var _1b=_19.assembly,_1c=this._gpEncode(_4.mixin({},this._url.query,{f:"json","env:outSR":(_1a?(_1a.wkid||_4.toJson(_1a.toJson())):null),"env:processSR":(this.processSpatialReference?(this.processSpatialReference.wkid||_4.toJson(this.processSpatialReference.toJson())):null)},_15),null,_1b&&_1b[0]),_1d=this._jobUpdateHandler,_1e=this._errorHandler;return esri.request({url:this._url.path+"/submitJob",content:_1c,callbackParamName:"callback",load:function(r,i){_1d(r,i,false,_16,_17,_19.dfd);},error:function(r){_1e(r,_18,_19.dfd);}});},_jobUpdateHandler:function(_1f,io,_20,_21,_22,dfd){var _23=_1f.jobId,_24=new esri.tasks.JobInfo(_1f);this._successHandler([_24],"onStatusUpdate",_22,_20&&dfd);if(!_20){clearTimeout(this._updateTimers[_23]);this._updateTimers[_23]=null;if(dfd){dfd.progress(_24);}switch(_1f.jobStatus){case esri.tasks.JobInfo.STATUS_SUBMITTED:case esri.tasks.JobInfo.STATUS_EXECUTING:case esri.tasks.JobInfo.STATUS_WAITING:case esri.tasks.JobInfo.STATUS_NEW:var _25=this._getJobStatus;this._updateTimers[_23]=setTimeout(function(){_25(_23,_20,_21,_22,dfd);},this.updateDelay);break;default:this._successHandler([_24],"onJobComplete",_21,dfd);}}},_getJobStatus:function(_26,_27,_28,_29,dfd){var _2a=this._jobUpdateHandler;esri.request({url:this._url.path+"/jobs/"+_26,content:_4.mixin({},this._url.query,{f:"json"}),callbackParamName:"callback",load:function(){_2a(arguments[0],arguments[1],_27,_28,_29,dfd);},error:this._errorHandler});},_getResultDataHandler:function(_2b,io,_2c,_2d,dfd){try{var _2e=this._decode(_2b);this._successHandler([_2e],"onGetResultDataComplete",_2c,dfd);}catch(err){this._errorHandler(err,_2d,dfd);}},getResultData:function(_2f,_30,_31,_32){var _33=this._getResultDataHandler,_34=this._errorHandler;var dfd=new _4.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path+"/jobs/"+_2f+"/results/"+_30,content:_4.mixin({},this._url.query,{f:"json",returnType:"data"}),callbackParamName:"callback",load:function(r,i){_33(r,i,_31,_32,dfd);},error:function(r){_34(r,_32,dfd);}});return dfd;},checkJobStatus:function(_35,_36,_37){var _38=this._jobUpdateHandler,_39=this._errorHandler;var dfd=new _4.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path+"/jobs/"+_35,content:_4.mixin({},this._url.query,{f:"json"}),callbackParamName:"callback",load:function(r,i){_38(r,i,true,null,_36,dfd);},error:function(r){_39(r,_37,dfd);}});return dfd;},execute:function(_3a,_3b,_3c,_3d){var _3e=this._getOutSR();var _3f=_3d.assembly,_40=this._gpEncode(_4.mixin({},this._url.query,{f:"json","env:outSR":(_3e?(_3e.wkid||_4.toJson(_3e.toJson())):null),"env:processSR":(this.processSpatialReference?(this.processSpatialReference.wkid||_4.toJson(this.processSpatialReference.toJson())):null)},_3a),null,_3f&&_3f[0]),_41=this._executeHandler,_42=this._errorHandler;return esri.request({url:this._url.path+"/execute",content:_40,callbackParamName:"callback",load:function(r,i){_41(r,i,_3b,_3c,_3d.dfd);},error:function(r){_42(r,_3c,_3d.dfd);}});},_executeHandler:function(_43,io,_44,_45,dfd){try{var _46=_43.results,i,il,_47=_43.messages;for(i=0,il=_46.length;i<il;i++){_46[i]=this._decode(_46[i]);}for(i=0,il=_47.length;i<il;i++){_47[i]=new esri.tasks.GPMessage(_47[i]);}this._successHandler([_46,_47],"onExecuteComplete",_44,dfd);}catch(err){this._errorHandler(err,_45,dfd);}},_getResultImageHandler:function(_48,io,_49,_4a,dfd){try{var _4b=this._decode(_48);this._successHandler([_4b],"onGetResultImageComplete",_49,dfd);}catch(err){this._errorHandler(err,_4a,dfd);}},getResultImage:function(_4c,_4d,_4e,_4f,_50){var _51=this._getResultImageHandler,_52=this._errorHandler,_53=this._gpEncode(_4.mixin({},this._url.query,{f:"json"},_4e.toJson()));var dfd=new _4.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path+"/jobs/"+_4c+"/results/"+_4d,content:_53,callbackParamName:"callback",load:function(r,i){_51(r,i,_4f,_50,dfd);},error:function(r){_52(r,_50,dfd);}});return dfd;},cancelJobStatusUpdates:function(_54){clearTimeout(this._updateTimers[_54]);this._updateTimers[_54]=null;},getResultImageLayer:function(_55,_56,_57,_58){var url=this._url.path+"/jobs/"+_55+"/results/"+_56;if(this._url.query){url+="?"+_4.objectToQuery(this._url.query);}var _59=new esri.tasks._GPResultImageLayer(url,{imageParameters:_57},true);this.onGetResultImageLayerComplete(_59);if(_58){_58(_59);}return _59;},onStatusUpdate:function(){},onJobComplete:function(){},onExecuteComplete:function(){},onGetResultDataComplete:function(){},onGetResultImageComplete:function(){},onGetResultImageLayerComplete:function(){}});esri._createWrappers("esri.tasks.Geoprocessor");_4.declare("esri.tasks.JobInfo",null,{constructor:function(_5a){this.messages=[];_4.mixin(this,_5a);var _5b=this.messages;for(var i=0,il=_5b.length;i<il;i++){_5b[i]=new esri.tasks.GPMessage(_5b[i]);}},jobId:"",jobStatus:""});_4.mixin(esri.tasks.JobInfo,{STATUS_CANCELLED:"esriJobCancelled",STATUS_CANCELLING:"esriJobCancelling",STATUS_DELETED:"esriJobDeleted",STATUS_DELETING:"esriJobDeleting",STATUS_EXECUTING:"esriJobExecuting",STATUS_FAILED:"esriJobFailed",STATUS_NEW:"esriJobNew",STATUS_SUBMITTED:"esriJobSubmitted",STATUS_SUCCEEDED:"esriJobSucceeded",STATUS_TIMED_OUT:"esriJobTimedOut",STATUS_WAITING:"esriJobWaiting"});_4.declare("esri.tasks.GPMessage",null,{constructor:function(_5c){_4.mixin(this,_5c);}});_4.mixin(esri.tasks.GPMessage,{TYPE_INFORMATIVE:"esriJobMessageTypeInformative",TYPE_PROCESS_DEFINITION:"esriJobMessageTypeProcessDefinition",TYPE_PROCESS_START:"esriJobMessageTypeProcessStart",TYPE_PROCESS_STOP:"esriJobMessageTypeProcessStop",TYPE_WARNING:"esriJobMessageTypeWarning",TYPE_ERROR:"esriJobMessageTypeError",TYPE_EMPTY:"esriJobMessageTypeEmpty",TYPE_ABORT:"esriJobMessageTypeAbort"});_4.declare("esri.tasks.LinearUnit",null,{constructor:function(_5d){if(_5d){_4.mixin(this,_5d);}},distance:0,units:null,toJson:function(){var _5e={};if(this.distance){_5e.distance=this.distance;}if(this.units){_5e.units=this.units;}return _5e;}});_4.declare("esri.tasks.DataFile",null,{constructor:function(_5f){if(_5f){_4.mixin(this,_5f);}},url:null,toJson:function(){if(this.url){return {url:this.url};}return null;}});_4.declare("esri.tasks.RasterData",null,{constructor:function(_60){if(_60){_4.mixin(this,_60);}},url:null,format:null,toJson:function(){var _61={};if(this.url){_61.url=this.url;}if(this.format){_61.format=this.format;}return _61;}});_4.declare("esri.tasks.Date",null,{constructor:function(_62){if(_62){if(_62.format){this.format=_62.format;}this.date=_4.date.locale.parse(_62.date,{selector:"date",datePattern:this.format});}},date:new Date(),format:"EEE MMM dd HH:mm:ss zzz yyyy",toJson:function(){return {date:_4.date.locale.format(this.date,{selector:"date",datePattern:this.format}),format:this.format};}});_4.declare("esri.tasks.ParameterValue",null,{constructor:function(_63){_4.mixin(this,_63);}});_4.declare("esri.tasks._GPResultImageLayer",esri.layers.ArcGISDynamicMapServiceLayer,{constructor:function(url,_64){if(_64&&_64.imageParameters&&_64.imageParameters.extent){this.initialExtent=(this.fullExtent=_64.imageParameters.extent);this.spatialReference=this.initialExtent.spatialReference;}this.getImageUrl=_4.hitch(this,this.getImageUrl);this.loaded=true;this.onLoad(this);},getImageUrl:function(_65,_66,_67,_68){var _69=this._url.path+"?",_6a=this._params,sr=_65.spatialReference.wkid;_68(_69+_4.objectToQuery(_4.mixin(_6a,{f:"image",bbox:_4.toJson(_65.toJson()),bboxSR:sr,imageSR:sr,size:_66+","+_67})));}});}}};});