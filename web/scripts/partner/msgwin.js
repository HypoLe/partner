var msgWinTop,msgWinLeft,msgWinWidth,msgWinHeight,mainWinHeight,mainWinWidth;
var msgLoginTop,msgLoginLeft,msgLoginWidth,msgLoginHeight,mainLoginHeight,mainLoginWidth;
var oTimer;

var DEFAULT_MSGTITLE = "您好!";
var DEFAULT_MSGICON  = "gt_bbs/bss_images/hello.gif";
var DEFAULT_MSGCONTENT  = "现在没有提示信息!";
var DEFAULT_MSGTIME  = 5000;

//var eclipse=0;
var tipTime;

var dW;		// dest window object
var dWDoc;	// dest window's document object

function popUpMsg(dstWin, msgTitle, msgIcon, msgContent, msgTime) {
  //alert(msgContent);
	try {
		if (eval(dstWin).document.readyState!="complete") {
			//alert("popUpMsg(\"" + dstWin + "\", \"" + msgTitle + "\", \"" + msgIcon + "\", \"" + msgContent + "\", " + msgTime + ")");
			oTimer = window.setTimeout("popUpMsg(\"" + dstWin + "\", \"" + msgTitle + "\", \"" + msgIcon + "\", \"" + msgContent + "\", " + msgTime + ")", 5);
		} else {
			if(oTimer) window.clearTimeout(oTimer);

			dstWin = eval(dstWin);
			if (dstWin==null) return;

			dW = dstWin.window;
			dWDoc = dW.document;
                        if(dWDoc.getElementById("msgWin")){
                          dWDoc.getElementById("msgWin").outerHTML = "";
                        }
			dWDoc.body.onresize = resizeMainWin;

			if (msgTitle=="") 	msgTitle = DEFAULT_MSGTITLE;
			if (msgIcon=="") 	msgIcon  = DEFAULT_MSGICON;
			if (msgContent=="") 	msgContent = DEFAULT_MSGCONTENT;
			tipTime = (msgTime==null)?DEFAULT_MSGTIME:msgTime;
			var ostr="<div id=msgWin ; style='background-color:#ffffff;z-index:99999; left: 0px; visibility:  hidden; width: 153px; position: absolute; top: 0px; height: 20px;'><table style=\"border: 1 solid  #6D93C8\"  width=\"241\" height=\"15\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"   >";
			ostr += "<tr><td style='font-size:12px;color:#035c80;'><b>" + msgTitle + "</b></td>";
			ostr += "<td width=17 valign=bottom><img align=right src='images/icons/list-delete.gif' alt='关闭' onclick='javascript:top.msgFreq.closeMsgWin();'/></td>";
                        ostr+="<tr><td align=\"center\"><table width=\"90%\" height=\"15\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
			ostr+="<tr><td valign=\"top\" style=\"font-size:12px; color: #000000; face: Tahoma\">"+msgContent+"</td></tr></table></td></tr></table></div>";

			dWDoc.body.insertAdjacentHTML("beforeEnd", ostr);

			msgWinTop = parseInt(dWDoc.getElementById("msgWin").style.top,10);
			msgWinLeft = parseInt(dWDoc.getElementById("msgWin").style.left,10);
			msgWinHeight = parseInt(dWDoc.getElementById("msgWin").offsetHeight,10);
			msgWinWidth = parseInt(dWDoc.getElementById("msgWin").offsetWidth,10);
			mainWinWidth = dWDoc.body.clientWidth;
			mainWinHeight = dWDoc.body.clientHeight;
			dWDoc.getElementById("msgWin").style.top = parseInt(dWDoc.body.scrollTop,10) + mainWinHeight + 10;//  msgWinHeight
			dWDoc.getElementById("msgWin").style.left = parseInt(dWDoc.body.scrollLeft,10) + mainWinWidth - msgWinWidth;
			dWDoc.getElementById("msgWin").style.visibility="visible";
			oTimer = window.setInterval("moveUpMsgWin()",1);
		}
	} catch(e){}
}

	function resizeMainWin() {
		try{
			msgWinHeight = parseInt(dWDoc.getElementById("msgWin").offsetHeight,10);
			msgWinWidth = parseInt(dWDoc.getElementById("msgWin").offsetWidth,10);
			mainWinWidth = dWDoc.body.clientWidth;
			mainWinHeight = dWDoc.body.clientHeight;
			dWDoc.getElementById("msgWin").style.top = mainWinHeight - msgWinHeight + parseInt(dWDoc.body.scrollTop,10);
			dWDoc.getElementById("msgWin").style.left = mainWinWidth - msgWinWidth + parseInt(dWDoc.body.scrollLeft,10);
		} catch(e){alert(e)}
	}

	function moveUpMsgWin() {
		try {
			if(parseInt(dWDoc.getElementById("msgWin").style.top,10) <= (mainWinHeight - msgWinHeight + parseInt(dWDoc.body.scrollTop,10))) {
				window.clearInterval(oTimer);
				//oTimer = window.setInterval("holdOnMsgWin()",1);
				if(oTimer != -1){
				oTimer = window.setTimeout("holdOnMsgWin()", tipTime);
				}
			}
			msgWinTop = parseInt(dWDoc.getElementById("msgWin").style.top,10);
			dWDoc.getElementById("msgWin").style.top = msgWinTop - 2;
	}catch(e){}
}

function moveDownMsgWin() {
	try {
		if(parseInt(dWDoc.getElementById("msgWin").style.top,10) >= (mainWinHeight + parseInt(dWDoc.body.scrollTop,10))) {
			dWDoc.getElementById('msgWin').style.visibility='hidden';
			dWDoc.getElementById("msgWin").style.top = 0;
			if(oTimer) window.clearTimeout(oTimer) ;
		}
		msgWinTop = parseInt(dWDoc.getElementById("msgWin").style.top,10);
		dWDoc.getElementById("msgWin").style.top = msgWinTop + 2;
	} catch(e){}
}

function holdOnMsgWin() {
	/*
	eclipse += 1;
	if ( eclipse > tipTime ) {
		window.clearInterval(oTimer);
		oTimer = window.setInterval("moveDownMsgWin()",1);
	}
	*/
	window.clearInterval(oTimer);
	oTimer = window.setInterval("moveDownMsgWin()",1);
}

function closeMsgWin() {
	dWDoc.getElementById('msgWin').style.visibility='hidden';
	dWDoc.getElementById("msgWin").style.top = 0;
	if(oTimer) window.clearInterval(oTimer);
}
