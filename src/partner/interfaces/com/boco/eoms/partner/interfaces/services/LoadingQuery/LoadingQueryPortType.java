/**
 * LoadingQueryPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingQuery;

public interface LoadingQueryPortType extends java.rmi.Remote {
    public void loadingQueryRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, javax.xml.rpc.holders.IntHolder statusCode, javax.xml.rpc.holders.StringHolder statusDescription) throws java.rmi.RemoteException;
}
