/*!
  PING.JS
  VERSION 2.51 (23-OCT-2012)
  @REQUIRES JQUERY V1.3 OR LATER
  @AUTHOR HUHAO
  @COMPANY BOCO
 */var intStartTime;var arrDelays=[];var intSent;var bolIsRunning=false;var bolIsTimeout;var strUrlFlag=connetFlag;var strURL;var intTimeout;var intTimerID;var urlNet="http://211.137.243.1";var urlNet1="http://10.110.180.166";var $=function(v){return document.getElementById(v)};var objIMG=new Image();objIMG.onload=objIMG.onerror=function(){clearTimeout(intTimerID);if(!bolIsRunning||bolIsTimeout)
return;var delay=new Date()-intStartTime;println("Reply from "+strURL+" time"
+((delay<1)?("<1"):("="+delay))+"ms");arrDelays.push(delay);setTimeout(ping,delay<1000?(1000-delay):1000);}
function ping(){intStartTime=+new Date();intSent++;objIMG.src=strURL+"/"+intStartTime;bolIsTimeout=false;intTimerID=setTimeout(timeout,intTimeout);}
function timeout(){if(!bolIsRunning)
return;bolIsTimeout=true;objIMG.src="X:\\";println("无响应...");ping();}
function handleBtnClick(){var objBtn=document.getElementById("btnSwitch");var objTxtURL;if(strUrlFlag){objTxtURL=urlNet;}else{objTxtURL=urlNet1;}
if(bolIsRunning){var intRecv=arrDelays.length;var intLost=intSent-intRecv;var sum=0;for(var i=0;i<intRecv;i++)
sum+=arrDelays[i];objBtn.value="开始网络测试";bolIsRunning=false;println("　");println("请求连接地址为："+strURL);println("　　信息: 发送 = "+intSent+", 接收 = "+intRecv+", 丢失 = "
+intLost+" ("+Math.floor(intLost/intSent*100)
+"% 丢失率),");if(intRecv==0)
return;println("Approximate round trip times in milli-seconds:");println("　　最小连接时长 = "+Math.min.apply(this,arrDelays)
+"ms, 最大连接时长 = "+Math.max.apply(this,arrDelays)
+"ms, 平均连接时长 = "+Math.floor(sum/intRecv)+"ms");if(Math.floor(sum/intRecv)>200||Math.floor(intLost/intSent*100)>10){alert("网络较差延迟为："+Math.floor(sum/intRecv)+"ms 丢包率为"
+Math.floor(intLost/intSent*100)+"%，可能导致地图无法加载！");}else{alert("网络正常延迟为："+Math.floor(sum/intRecv)+"ms 丢包率为"
+Math.floor(intLost/intSent*100)+"%");}}else{setTimeout(function(){document.getElementById("btnSwitch").click();},5000);strURL=objTxtURL;if(strURL.length==0)
return;if(strURL.substring(0,7).toLowerCase()!="http://")
strURL="http://"+strURL;intTimeout=parseInt(document.getElementById("txtTimeout").value,10);if(isNaN(intTimeout))
intTimeout=2000;if(intTimeout<1000)
intTimeout=1000;objBtn.value="停止 ";bolIsRunning=true;arrDelays=[];intSent=0;cls();println("　");println("已开始网络测试，请勿终止！");println("　");println("Pinging "+strURL);println("　");ping();}}
function println(str){var objDIV=document.createElement("div");var objContent=document.getElementById("divContent");if(objDIV.innerText!=null)
objDIV.innerText=str;else
objDIV.textContent=str;objContent.appendChild(objDIV);objContent.scrollTop=objContent.scrollHeight;}
function cls(){var objContent=document.getElementById("divContent");objContent.innerHTML="";}