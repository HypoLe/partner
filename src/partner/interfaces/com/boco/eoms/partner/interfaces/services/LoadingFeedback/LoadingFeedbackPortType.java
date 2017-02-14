/**
 * LoadingFeedbackPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingFeedback;

public interface LoadingFeedbackPortType extends java.rmi.Remote {
    public java.lang.String loadingFeedbackRequest(javax.xml.rpc.holders.StringHolder eventID, int endStatusCode, java.lang.String endStatusDescription, java.util.Calendar sendTime) throws java.rmi.RemoteException;
}
