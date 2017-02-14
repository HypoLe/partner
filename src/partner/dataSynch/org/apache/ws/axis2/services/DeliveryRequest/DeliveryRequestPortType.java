/**
 * DeliveryRequestPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Aug 08, 2005 (11:49:10 PDT) WSDL2Java emitter.
 */

package org.apache.ws.axis2.services.DeliveryRequest;

public interface DeliveryRequestPortType extends java.rmi.Remote {
    public void deliveryRequestRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, java.lang.String filter, java.lang.String dataReadyRequestUri) throws java.rmi.RemoteException;
}
