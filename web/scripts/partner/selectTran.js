  var MyForm = new Ext.form.FormPanel();
  var win = new Ext.Window();

function showSelect(prefix,nodeId,kpiId,taskId,partnerId,time,areaId,parNum) { 
 	MyForm=new Ext.form.FormPanel({
			xtype:"form",
			title:"",
			labelWidth:"400",
			labelAlign:"left",
			layout:"auto",
			formId:"MyForm",
			name:"MyForm",
			width:"400",
			height:220,
			autoHeight:false,
   		    autoScroll:true,
			frame:true,
			items:[],
			buttons : [{
					text:"确定",
					handler: function(){
                        //提交保存
                        MyForm.getForm().submit({   
                                            url : prefix+'AssSelectedInstances.do?method=save',//提交给后台。后台相应save方法。此处成功调用了该方法   
                                            method : 'post',   
                                            waitTitle : '请稍等……',   
                                            waitMsg : '正在上传数据……', 
                                            success : function(MyForm, action) {  
                                                //Ext.Msg.alert("成功", "操作成功！");
                                                win.close(); 
                                                //alert(action.result.value);
                                                //alert(action.result.nodeId+"_"+action.result.partnerId+"_sc");
                                                if(action.result.algorithm=='deductScore'){
                                                    document.getElementById(action.result.nodeId+"_"+action.result.partnerId+"_sc_btn").style.color = action.result.color;
                                                	document.getElementById(action.result.nodeId+"_"+action.result.partnerId+"_deductWeight").value =  action.result.value;
                                                }else{
                                                	document.getElementById(action.result.nodeId+"_"+action.result.partnerId+"_sc_btn").value =  action.result.value;
													document.getElementById(action.result.nodeId+"_"+action.result.partnerId+"_sc_btn").style.color = action.result.color;
                                                	document.getElementById(action.result.nodeId+"_"+action.result.partnerId+"_sc").value =  action.result.value;
                                                }
                                                
    										if(getDeduct()){//保证执行顺序
     											getTotal();
     											}
											},
                                            failure : function(MyForm, action) {   
                                                Ext.Msg.alert("出错啦", "数据保存失败！"+action.result.failMsg);  
                                                win.close();  
                                            }   
                                        });
                        //关闭时取得所有的items主键
                        
                        //Ext.getBody().unmask();
                    }
				},
				{
					text:"取消",
					handler: function(){
                        win.close();  
                        Ext.getBody().unmask();
                    }
				}]
		});
    
        // create the window on the first click and reuse on subsequent clicks
			win=new Ext.Window({
				title:"请选择分数项",
				width:417,
				height:223,
				autoHeight:true,
				autoScroll:false,
				minimizable:false,
				maximizable:false,
				closable:false,//在右上角显示小叉叉的关闭按钮，默认为true
				modal:true,//true为模式窗口，后面的内容都不能操作，默认为false
				plain:true,//true则主体背景透明，false则主体有小差别的背景色，默认为false
				closeAction:'hide'
			});
       
		getData(prefix,nodeId,kpiId,taskId,partnerId,time,areaId,parNum);
        win.add(MyForm);
       // Ext.getBody().mask();
                        
       
    };
 
function selectedConfig(prefix,nodeId,kpiId,taskId,partnerId,time,areaId,parNum) { 
 	MyForm=new Ext.form.FormPanel({
			xtype:"form",
			title:"",
			labelWidth:"400",
			labelAlign:"left",
			layout:"auto",
			formId:"MyForm",
			name:"MyForm",
			width:"400",
			height:220,
			autoHeight:false,
   		    autoScroll:true,
			frame:true,
			items:[],
			buttons : [{
					text:"关闭",
					handler: function(){
                        win.close();  
                        Ext.getBody().unmask();
                    }
				}]
		});
    
        // create the window on the first click and reuse on subsequent clicks
			win=new Ext.Window({
				title:"该指标打分数据细项",
				width:417,
				height:223,
				autoHeight:true,
				autoScroll:false,
				minimizable:false,
				maximizable:false,
				closable:false,//在右上角显示小叉叉的关闭按钮，默认为true
				modal:true,//true为模式窗口，后面的内容都不能操作，默认为false
				plain:true,//true则主体背景透明，false则主体有小差别的背景色，默认为false
				closeAction:'hide'
			});
       
		getData(prefix,nodeId,kpiId,taskId,partnerId,time,areaId,parNum);
        win.add(MyForm);
       // Ext.getBody().mask();
                        
       
    };
function getData(prefix,nodeId,kpiId,taskId,partnerId,time,areaId,parNum){     
    		var conn = new Ext.data.Connection();     
    		conn.request({     
        		url: prefix+'AssKpiConfigs.do?method=getConfigsByKpiId&nodeId='+nodeId+'&kpiId='+kpiId+'&taskId='+taskId+'&partnerId='+partnerId+'&time='+time+'&areaId='+areaId+'&parNum='+parNum,     
        		success: function(response) {   
            //Ext.util.JSON.decode(response.responseText); 
            		var obj = eval(response.responseText);
            		 for(i=0;i<obj.length;i++){
            			if(obj[i].xtype=='radio'){
            				var ss = new Ext.form.Radio({boxLabel:obj[i].boxLabel,name:obj[i].name,id:obj[i].id,inputValue:obj[i].inputValue,checked:obj[i].checked });
            				//alert(obj);
            				MyForm.add(ss);
            				MyForm.add(new Ext.Container());
            			}else if(obj[i].xtype=='checkbox'){
            				var ss = new Ext.form.Checkbox({boxLabel:obj[i].boxLabel,name:obj[i].name,id:obj[i].id,inputValue:obj[i].inputValue,checked:obj[i].checked });
            				MyForm.add(ss);
            				MyForm.add(new Ext.Container());
            			}else if(obj[i].xtype=='numberfield'){
            				var ss = new Ext.form.NumberField({value:obj[i].value,fieldLabel:obj[i].boxLabel,name:obj[i].name,id:obj[i].id});
            				MyForm.add(ss);
            				MyForm.add(new Ext.Container());
            			}else if(obj[i].xtype=='textField'){
            				var ss = new Ext.form.TextField({boxLabel:obj[i].boxLabel,name:obj[i].name,id:obj[i].id,hidden:obj[i].hidden,value:obj[i].value });
            				MyForm.add(ss);
            				MyForm.add(new Ext.Container());
            			}else if(obj[i].xtype=='label'){
            				var ss = new Ext.form.Label({text:obj[i].boxLabel,id:obj[i].id,style:obj[i].style });
            				MyForm.add(ss);
            				MyForm.add(new Ext.Container());
            			}
            		}
            		
            		win.show(this);
            			 
            return response.responseText;  
           		}     
        	});     
   };  
function  getDeduct()
    {
     var deduct = deductStr.split("|");
     var deductNodes = deductNodesStr.split("|");
     var deductArea = deductAreaStr.split("|");
     var sum = 0;
     for(i=0;i<deduct.length-1;i++){
     	var deductNodeForSum = deductNodes[i].split(",");
     	for(j=0;j<deductNodeForSum.length;j++){
     		var scName = deductNodeForSum[j] + '_' + deductArea[i] + '_sc';
     		var scValue = document.getElementById(scName).value;
     		if(document.getElementById(scName).value!=null&&scValue!=''){
     			sum = sum +parseFloat(scValue);
     		}
     	}
     	document.getElementById(deduct[i]+"_deductScore").innerHTML=fomatFloat(sum);
     	var deductValue = fomatFloat(sum*parseFloat(document.getElementById(deduct[i]+"_deductWeight").value))
     	document.getElementById(deduct[i]+"_sc_btn").value=deductValue;
     	document.getElementById(deduct[i]+"_sc").value=deductValue;
     	sum = 0;
     }

     return true;
    };
function  getTotal()
    {
     
     var total = totalStr.split("|");
     var totalNodes = totalNodesStr.split("|");
     var totalArea = totalAreaStr.split("|");
     var sum = 0;
     for(i=0;i<total.length-1;i++){
     	var totalNodeForSum = totalNodes[i].split(",");
     	for(j=0;j<totalNodeForSum.length;j++){
     		var scName = totalNodeForSum[j] + '_' + totalArea[i] + '_sc';
     		var scValue = document.getElementById(scName).value;
     		if(document.getElementById(scName).value!=null&&scValue!=''){
     			sum = sum +parseFloat(scValue);
     			var weight = totalNodeForSum[j] + '_' + totalArea[i] + '_weight';
     			var source = totalNodeForSum[j] + '_' + totalArea[i] + '_source';
     			var weightTotal = totalNodeForSum[j] + '_' + totalArea[i] + '_weightTotal';
     	   		var weightValue = document.getElementById(weight).innerHTML;
     	   		var sourceValue = document.getElementById(source).value;
     			document.getElementById(weight).innerHTML = fomatFloat(parseFloat(sourceValue)-parseFloat(scValue));
     		}
     	}
     	document.getElementById(total[i]+'Weight').innerHTML=fomatFloat(100-sum);
     	document.getElementById(total[i]).innerHTML=fomatFloat(sum);
     	sum = 0;
     }
    };
function fomatFloat(score)
	{ 
		return Math.round(score*Math.pow(10, 2))/Math.pow(10, 2); 
	};   

