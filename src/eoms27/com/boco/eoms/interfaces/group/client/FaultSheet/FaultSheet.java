/**
 * FaultSheet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.boco.eoms.interfaces.group.client.FaultSheet;

public interface FaultSheet extends java.rmi.Remote {
    public java.lang.String isAlive(java.lang.String serSupplier) throws java.rmi.RemoteException;
    public java.lang.String cancelWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String sendCorp, java.lang.String sendPerson, java.lang.String sendDepart, java.lang.String sendContact, java.lang.String sendTime, java.lang.String cancelAdvice) throws java.rmi.RemoteException;
    public java.lang.String checkinWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String closeCorp, java.lang.String closePerson, java.lang.String closeDepart, java.lang.String closeContact, java.lang.String closeTime, int satisfaction, java.lang.String closeAdvice) throws java.rmi.RemoteException;
    public java.lang.String confirmWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String replyCorp, java.lang.String replyPerson, java.lang.String replyDepart, java.lang.String replyContact, java.lang.String replyTime, int urgent, java.lang.String deadline) throws java.rmi.RemoteException;
    public java.lang.String newWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String sendCorp, java.lang.String sendPerson, java.lang.String sendDepart, java.lang.String sendContact, java.lang.String sendTime, java.lang.String acceptCorp, java.lang.String coordCorp, int urgent, java.lang.String deadline, int faultType, java.lang.String faultTitle, java.lang.String faultOccurTime, java.lang.String faultDescription, java.lang.String initialResult, java.lang.String faultAdvice, java.lang.String alarmId) throws java.rmi.RemoteException;
    public java.lang.String notifyWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String replyCorp, java.lang.String replyPerson, java.lang.String replyDepart, java.lang.String replyContact, java.lang.String replyTime, java.lang.String stepResult) throws java.rmi.RemoteException;
    public java.lang.String renewWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String sendCorp, java.lang.String sendPerson, java.lang.String sendDepart, java.lang.String sendContact, java.lang.String sendTime, java.lang.String acceptCorp, java.lang.String coordCorp, int urgent, java.lang.String deadline, java.lang.String resendReason, int faultType, java.lang.String faultTitle, java.lang.String faultOccurTime, java.lang.String faultDescription, java.lang.String initialResult, java.lang.String faultAdvice, java.lang.String alarmId) throws java.rmi.RemoteException;
    public java.lang.String replyWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String replyCorp, java.lang.String replyPerson, java.lang.String replyDepart, java.lang.String replyContact, java.lang.String replyTime, java.lang.String recoverTime, java.lang.String outOfServiceTime, int newWorkType, int serviceType, java.lang.String endResult) throws java.rmi.RemoteException;
    public java.lang.String suggestWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String sendCorp, java.lang.String sendPerson, java.lang.String sendDepart, java.lang.String sendContact, java.lang.String sendTime, java.lang.String suggestion) throws java.rmi.RemoteException;
    public java.lang.String syncAlarmWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String alarmId, java.lang.String alarmStatus, java.lang.String statusTime) throws java.rmi.RemoteException;
    public java.lang.String urgeWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String sendCorp, java.lang.String sendPerson, java.lang.String sendDepart, java.lang.String sendContact, java.lang.String sendTime, java.lang.String urgeAdvice) throws java.rmi.RemoteException;
    public java.lang.String withdrawWorkSheet(int sheetType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callTime, java.lang.String callerPwd, java.lang.String attachRef, java.lang.String replyCorp, java.lang.String replyPerson, java.lang.String replyDepart, java.lang.String replyContact, java.lang.String replyTime, java.lang.String withdrawReason) throws java.rmi.RemoteException;
}
