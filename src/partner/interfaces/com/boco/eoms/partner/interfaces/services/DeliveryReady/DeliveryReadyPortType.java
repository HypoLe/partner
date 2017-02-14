/**
 * DeliveryReadyPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.DeliveryReady;

public interface DeliveryReadyPortType extends java.rmi.Remote {
    public java.lang.String deliveryReadyRequest(javax.xml.rpc.holders.StringHolder eventID, java.util.Calendar sendTime, int readyStatusCode, java.lang.String readyStatusDescription, int workMode, int fileFormat, int charSet, int lineSeparator, int fieldSeparator, java.lang.String fieldNameList, java.lang.String xmlSchema, java.lang.String dataInfo, java.lang.String connectionString, java.lang.String path, boolean isCompressed) throws java.rmi.RemoteException;
}
