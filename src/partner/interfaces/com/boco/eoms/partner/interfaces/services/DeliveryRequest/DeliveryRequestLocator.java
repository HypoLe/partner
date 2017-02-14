/**
 * DeliveryRequestLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.DeliveryRequest;

public class DeliveryRequestLocator extends org.apache.axis.client.Service implements com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequest {

    public DeliveryRequestLocator() {
    }


    public DeliveryRequestLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DeliveryRequestLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DeliveryRequestSOAPport_http
    private java.lang.String DeliveryRequestSOAPport_http_address = "http://localhost:1443";

    public java.lang.String getDeliveryRequestSOAPport_httpAddress() {
        return DeliveryRequestSOAPport_http_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DeliveryRequestSOAPport_httpWSDDServiceName = "DeliveryRequestSOAPport_http";

    public java.lang.String getDeliveryRequestSOAPport_httpWSDDServiceName() {
        return DeliveryRequestSOAPport_httpWSDDServiceName;
    }

    public void setDeliveryRequestSOAPport_httpWSDDServiceName(java.lang.String name) {
        DeliveryRequestSOAPport_httpWSDDServiceName = name;
    }

    public com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestPortType getDeliveryRequestSOAPport_http() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DeliveryRequestSOAPport_http_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDeliveryRequestSOAPport_http(endpoint);
    }

    public com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestPortType getDeliveryRequestSOAPport_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestSoapBindingStub _stub = new com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestSoapBindingStub(portAddress, this);
            _stub.setPortName(getDeliveryRequestSOAPport_httpWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDeliveryRequestSOAPport_httpEndpointAddress(java.lang.String address) {
        DeliveryRequestSOAPport_http_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestSoapBindingStub _stub = new com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestSoapBindingStub(new java.net.URL(DeliveryRequestSOAPport_http_address), this);
                _stub.setPortName(getDeliveryRequestSOAPport_httpWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DeliveryRequestSOAPport_http".equals(inputPortName)) {
            return getDeliveryRequestSOAPport_http();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/DeliveryRequest", "DeliveryRequest");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/DeliveryRequest", "DeliveryRequestSOAPport_http"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DeliveryRequestSOAPport_http".equals(portName)) {
            setDeliveryRequestSOAPport_httpEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
