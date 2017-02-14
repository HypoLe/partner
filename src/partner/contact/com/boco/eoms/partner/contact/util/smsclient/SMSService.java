/**
 * SMSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Aug 08, 2005 (11:49:10 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.contact.util.smsclient;

public interface SMSService extends javax.xml.rpc.Service {
    public java.lang.String getSMSServiceHttpSoap11EndpointAddress();

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException;

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getSMSServiceHttpSoap12EndpointAddress();

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException;

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
