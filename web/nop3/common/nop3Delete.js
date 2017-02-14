var myjs=jQuery.noConflict();
 function dele(path){

		myjs( "#dialog-confirm" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: {
				"确定": function() {
					myjs( this ).dialog( "close" );					
					myjs.ajax({
					  type:"POST",
					  url:path,
					 success:deleteResult()					 	  
					 });	
				},
				"取消": function() {
					myjs( this ).dialog( "close" );
				}
			}
		
		});
   };
	function deleteResult() {
	  
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		//myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		myjs( "#dialog-message" ).dialog({
			modal: true,
			buttons: {
				Ok: function() {
				    window.location.reload();
					myjs( this).dialog( "close" );
				}
			}
			
		});
	};