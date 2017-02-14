Ext.ux.Timer = function(config){
    config = config || {
    	//表格刷新周期	
		cycle : 1,
		//步长
		interval : 1,
		//累计刷新次数
		count : 1
    }
    
    startClock = function(cycle,handler){
 	  	config.cycle = config.cycle-config.interval;
	 	  setTimeout("startClock("+cycle+","+handler+")", 1000);
	 	  if(config.cycle==0){ 
	 	   	config.cycle=cycle;
	 	   	config.count++;
	 	   	handler.call(this,config.count);
	 	  }
    };
    
    Ext.apply(this, config);
    
    return {
    	start : function start(interval,handler){  	  
	 	  if( interval && handler){
	 	  	startClock(interval,handler);
	 	  }
	 	}
    };
};
