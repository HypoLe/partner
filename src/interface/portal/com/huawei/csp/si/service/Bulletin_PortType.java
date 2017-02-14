/**
 * Bulletin_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.huawei.csp.si.service;

public interface Bulletin_PortType extends java.rmi.Remote {
    public java.lang.String isAlive(java.lang.String serSupplier, java.lang.String callTime) throws java.rmi.RemoteException;
    public java.lang.String newBulletin(java.lang.Integer sheetType, java.lang.Integer serviceType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callerPwd, java.lang.String callTime, java.lang.String attachRef, java.lang.String opPerson, java.lang.String opCorp, java.lang.String opDepart, java.lang.String opContact, java.lang.String opTime, java.lang.String opDetail) throws java.rmi.RemoteException;
    public java.lang.String confirmBulletin(java.lang.Integer sheetType, java.lang.Integer serviceType, java.lang.String serialNo, java.lang.String serSupplier, java.lang.String serCaller, java.lang.String callerPwd, java.lang.String callTime, java.lang.String attachRef, java.lang.String opPerson, java.lang.String opCorp, java.lang.String opDepart, java.lang.String opContact, java.lang.String opTime, java.lang.String opDetail) throws java.rmi.RemoteException;
}
