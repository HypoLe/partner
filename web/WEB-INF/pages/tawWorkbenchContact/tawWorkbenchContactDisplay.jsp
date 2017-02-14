<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

  <script type="text/javascript">
  
var dialogName = "ahello-dlg";
var btnName = "";

function changeContext(aimDiv, aimbtn, index){
  var linenames = document.getElementById("hello-dlg_names");
  var linename = document.getElementById("hello-dlg_name");
  var linepart = document.getElementById("hello-dlg_part");
  var linemail = document.getElementById("hello-dlg_mail");
  var lineaddr = document.getElementById("hello-dlg_addr");
  var linetel = document.getElementById("hello-dlg_tel");
  var linepos = document.getElementById("hello-dlg_pos");
  linenames.innerHTML = document.getElementById("backname" + index).value;
  linename.innerHTML = "姓名：" + document.getElementById("backname" + index).value;
  linepart.innerHTML = "部门：" + document.getElementById("backdept" + index).value;
  linemail.innerHTML = "邮箱：" + document.getElementById("backmail" + index).value;
  lineaddr.innerHTML = "地址：" + document.getElementById("backaddr" + index).value;
  linetel.innerHTML = "联系电话：" + document.getElementById("backtele" + index).value;
  linetel.innerHTML = "职务：" + document.getElementById("backpos" + index).value;
  btnName = aimbtn;
};



function setDialog(name, bn){
    //changeContext(dialogName, name);
	dialogName = name;
	btnName = bn;
	//LayoutExample.showBtn = 
	//Ext.EventManager.onDocumentReady(LayoutExample.init, LayoutExample, true);
	//alert(dialogName);
};

var LayoutExample = function(){
    // everything in this space is private and only accessible in the HelloWorld block
    
    // define some private variables
    var dialog, showBtn;
    var inited = false;
    
    var toggleTheme = function(){
        Ext.get(document.body, true).toggleClass('xtheme-gray');
    };
    // return a public interface
    

    return {
        init : function(){
             if(!inited){
               inited = true;
               //var thisDialog = this.showDialog;
               for (i=0;i<${resultSize};i++)
               {
               		setDialog('hello-dlg', 'show-dialog-btn' + i);
               		showBtn = Ext.get('show-dialog-btn' + i);
               		// attach to click event
               		showBtn.on('click', this.showDialog, this);
               }
             }
        },
        
        showDialog : function(){
            //dialog = null;
            //alert(dialogName);
            if(!dialog){ // lazy initialize the dialog and only create it once
            //alert(document.getElementById(dialogName).innerHTML);
                dialog = new Ext.LayoutDialog("hello-dlg", { 
                        modal:true,
                        width:600,
                        height:400,
                        shadow:true,
                        minWidth:300,
                        minHeight:300,
                        proxyDrag: true,
                        west: {
	                        split:true,
	                        initialSize: 150,
	                        k: 100,
	                        maxSize: 250,
	                        titlebar: true,
	                        collapsible: true,
	                        animate: true
	                    },
	                    center: {
	                        autoScroll:true,
	                        tabPosition: 'top',
	                        closeOnTab: true,
	                        alwaysShowTabs: true
	                    }
                });
                dialog.addKeyListener(27, dialog.hide, dialog);
                //dialog.addButton('确定', this.destroy, dialog);
                dialog.addButton('确认', dialog.hide, dialog);
                
                var layout = dialog.getLayout();
                layout.beginUpdate();
                layout.add('west', new Ext.ContentPanel('west', {title: '联系人'}));
	            layout.add('center', new Ext.ContentPanel('center', {title: '联系人基本信息'}));
                //generate some other tabs
	            layout.endUpdate();
            }
            //Ext.EventManager.onDocumentReady(LayoutExample.init, LayoutExample, true);
            showBtn = Ext.get(btnName);
            dialog.show(showBtn.dom);
            //dialog.hide();
            //dialog.show(showBtn.dom);
            //dialog.show(Ext.get(dialogName));
        }
    };
}();

// using onDocumentReady instead of window.onload initializes the application
// when the DOM is ready, without waiting for images and other resources to load
Ext.EventManager.onDocumentReady(LayoutExample.init, LayoutExample, true);
  </script>

	<display:table name="unAuditList" cellspacing="0" cellpadding="0"
		id="unAuditList" pagesize="${pageSize}" class="table unAuditList"
		export="false"
		requestURI="${app}/workbench/contact/tawWorkbenchContactMain.do?method=getDisplay"
		sort="list" partialList="true" size="pageSize">
		<display:column property="contactName" sortable="true" headerClass="sortable" title="姓名" />
		<display:column property="position" sortable="true"	headerClass="sortable" title="职务" />
		
	<display:column property="tele" sortable="true" headerClass="sortable" title="电话" />
	<display:column property="email" sortable="true"	headerClass="sortable" title="邮件" />
	
	<display:column title="查看" headerClass="imageColumn">
	<input id="backname${unAuditList.id}" type="hidden" value="${unAuditList.contactName}" />
	<input id="backtele${unAuditList.id}" type="hidden" value="${unAuditList.tele}" />
	<input id="backaddr${unAuditList.id}" type="hidden" value="${unAuditList.address}" />
	<input id="backmail${unAuditList.id}" type="hidden" value="${unAuditList.email}" />
	<input id="backpos${unAuditList.id}" type="hidden" value="${unAuditList.position}" />
	<input id="backdept${unAuditList.id}" type="hidden" value="${unAuditList.deptName}" />
	<!--  
	<div id="hello-dlg${unAuditList.id}" style="visibility:hidden;">
	    <div class="x-dlg-hd">个人详细信息</div>
	    <div class="x-dlg-bd">
	        <div id="west" class="x-layout-inactive-content">
		        ${unAuditList.contactName }
		    </div>
		    <div id="center" class="x-layout-inactive-content" style="padding:10px;">
                <p>姓名：${unAuditList.contactName }</p>
                <p>部门：${unAuditList.deptName }</p>
                <p>邮箱：${unAuditList.email }</p>
                <p>部门：${unAuditList.position }</p>
                <p>地址：${unAuditList.address }</p>
                <p>联系电话：${unAuditList.tele }</p>
		    </div>
	    </div>
	</div>
	-->
		<a href="#">
			<img id="show-dialog-btn${unAuditList.id }" onclick="changeContext('hello-dlg','show-dialog-btn${unAuditList.id}',${unAuditList.id});" src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</display:table>
	
	<div id="hello-dlg" style="visibility:hidden;">
	    <div class="x-dlg-hd">个人详细信息</div>
	    <div id="contentDiv" class="x-dlg-bd">
	        <div id="west" class="x-layout-inactive-content">
		        <p id="hello-dlg_names">姓名</p>
		    </div>
		    <div id="center" class="x-layout-inactive-content" style="padding:10px;">
                <p id ="hello-dlg_name">姓名：</p>
                <p id ="hello-dlg_pos">职务：</p>
                <p id ="hello-dlg_tel">联系电话：</p>
                <p id ="hello-dlg_mail">邮箱：</p>
                <p id ="hello-dlg_part">部门：</p>
                <p id ="hello-dlg_addr">地址：</p>
                
		    </div>
	    </div>
	</div>
	
<!--  
<input type="button" id="show-dialog-btn" value="Show Dialog" />
-->


<input type="button" id="show-dialog-btn" style="visibility:hidden;" /><br />
<%@ include file="/common/footer_eoms.jsp"%>