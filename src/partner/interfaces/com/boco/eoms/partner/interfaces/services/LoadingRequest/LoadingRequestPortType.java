/**
 * LoadingRequestPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingRequest;

public interface LoadingRequestPortType extends java.rmi.Remote {
    public void loadingRequestRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, java.lang.String feedbackUri, int loadingFlag, int workMode, int fileFormat, int charSet, java.lang.String lineSeparator, java.lang.String fieldSeparator, java.lang.String fieldNameList, java.lang.String xmlSchema, java.lang.String dataInfo) throws java.rmi.RemoteException;
}
