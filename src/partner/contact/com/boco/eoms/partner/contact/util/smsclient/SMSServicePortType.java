/**
 * SMSServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Aug 08, 2005 (11:49:10 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.contact.util.smsclient;

public interface SMSServicePortType extends java.rmi.Remote {
    public java.lang.String smsSend(java.lang.String telnum, java.lang.String msgcontent, java.lang.String username, java.lang.String password, java.lang.String licence) throws java.rmi.RemoteException;
}
