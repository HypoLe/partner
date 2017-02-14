/**
 * UserService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.message.interfacesclient;

public interface UserService extends javax.xml.rpc.Service {
    public java.lang.String getUserServiceHttpPortAddress();

    public com.boco.eoms.message.interfacesclient.UserServicePortType getUserServiceHttpPort() throws javax.xml.rpc.ServiceException;

    public com.boco.eoms.message.interfacesclient.UserServicePortType getUserServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
