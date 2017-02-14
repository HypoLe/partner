jQuery.noConflict(); 
jQuery(function($){
	var str="";    
	$("[name='rowname']").change(function(){  
		str="";    
		$("[name='rowname']").each(function(){    
			if($(this).attr("checked"))
				str+=$(this).val()+",";    
		})    
	});
	$("#exportDialog").click(function(){
		 var X = $("#exportDialog").offset().left;
      	var Y = $("#exportDialog").offset().top;
      	var left=X+240;
      	var top=-Y+500;
		$("#eomsExcelExport").dialog({
			hide:"fold",
   			show:"fold",
			position:[left,top],
			minWidth:"150px",
			minHeight:"550px",
			title:"导出数据为Excel文档",
			modal:true ,
			buttons: [
			          			{ id:"exportAllSubmit", text: "导出全部列",click: exportAllClick  },
			          			{ id:"exportSubmit", text: "导出选中列",click: exportClick  }
						]
		});
	})
	
	//全部导出
	function exportAllClick(){
		$("[name='rowname']").attr("checked",'checked');
		$("[name='rowname']").change();
		exportClick();
	}
	//部分导出
	function exportClick(){
		if(str==""){
			alert("请选择 你要导出的列名");
			return false;
		}
		else{
			str="";
			$("#tagExcelExportForm").submit();
			$("[name='rowname']").attr("checked",'');
			$("#eomsExcelExport").dialog("close"); 
		}
	}
})
