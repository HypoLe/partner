/**
 * LoadingPreparationPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingPreparation;

public interface LoadingPreparationPortType extends java.rmi.Remote {
    public void loadingPreparationRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, int suggestWorkMode, int suggestFileFormat, int suggestCharSet, javax.xml.rpc.holders.IntHolder workMode, javax.xml.rpc.holders.IntHolder charSet, javax.xml.rpc.holders.StringHolder connectionString, javax.xml.rpc.holders.StringHolder path, javax.xml.rpc.holders.BooleanHolder isCompressed) throws java.rmi.RemoteException;
}
