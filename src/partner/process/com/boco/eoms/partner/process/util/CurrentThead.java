package com.boco.eoms.partner.process.util;

import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
/**
 * 与公客系统的接口调用，实现另一个线程通信
 * @author chenbing
 * Date:2014-05-28
 */
public class CurrentThead
  implements Runnable
{
  private String timeString;
  private String workOrderNo;
  private String methodName;
  private String msg;
  private TransferMaleGuestFlag maleGuestFlag;
  private TransferMaleGuestReturn maleGuestReturn;

  public TransferMaleGuestReturn getMaleGuestReturn() {
	return maleGuestReturn;
}

public void setMaleGuestReturn(TransferMaleGuestReturn maleGuestReturn) {
	this.maleGuestReturn = maleGuestReturn;
}

public TransferMaleGuestFlag getMaleGuestFlag() {
	return maleGuestFlag;
}

public void setMaleGuestFlag(TransferMaleGuestFlag maleGuestFlag) {
	this.maleGuestFlag = maleGuestFlag;
}

public String getTimeString()
  {
    return this.timeString; }

  public void setTimeString(String timeString) {
    this.timeString = timeString; }

  public String getWorkOrderNo() {
    return this.workOrderNo; }

  public void setWorkOrderNo(String workOrderNo) {
    this.workOrderNo = workOrderNo; }

  public String getMethodName() {
    return this.methodName; }

  public void setMethodName(String methodName) {
    this.methodName = methodName; }

  public String getMsg() {
    return this.msg; }

  public void setMsg(String msg) {
    this.msg = msg; }

  public CurrentThead(String timeString, String workOrderNo, String methodName, String msg) {
    this.timeString = timeString;
    this.workOrderNo = workOrderNo;
    this.methodName = methodName;
    this.msg = msg;
  }
  public CurrentThead(String timeString,String methodName,TransferMaleGuestFlag maleGuestFlag){
	  this.timeString = timeString;
	    this.methodName = methodName;
	    this.maleGuestFlag = maleGuestFlag;
  };
  public CurrentThead(String timeString,String methodName,TransferMaleGuestReturn maleGuestReturn){
	  this.timeString = timeString;
	    this.methodName = methodName;
	    this.maleGuestReturn = maleGuestReturn;
  };
  public void run()
  {
   String getMsg = CommonUtils.sendToGKInformation(this.timeString, this.workOrderNo, this.methodName, this.msg);
   
  }
}