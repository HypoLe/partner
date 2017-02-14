/**
 * DeliveryFeedbackPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.DeliveryFeedback;

public interface DeliveryFeedbackPortType extends java.rmi.Remote {
    public void deliveryFeedbackRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, int endStatusCode, java.lang.String endStatusDescription, java.util.Calendar sendTime) throws java.rmi.RemoteException;
}
