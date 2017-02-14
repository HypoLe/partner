/**
 * DeliveryRequestPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.DeliveryRequest;

public interface DeliveryRequestPortType extends java.rmi.Remote {
    public void deliveryRequestRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, java.lang.String filter, java.lang.String dataReadyRequestUri) throws java.rmi.RemoteException;
}
