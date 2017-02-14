var Measurement = function() { 
      return {
				initMeasurementToolbar : function(mymap){ 
			        var measurement = new esri.dijit.Measurement({ 
			          map: mymap 
			        }, dojo.byId('measurementDiv')); 
		        	measurement.startup(); 
		 			//measurement.setTool("area",true); 
	     	 	}
		} 
}();