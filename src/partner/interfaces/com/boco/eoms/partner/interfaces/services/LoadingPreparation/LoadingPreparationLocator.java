/**
 * LoadingPreparationLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingPreparation;

public class LoadingPreparationLocator extends org.apache.axis.client.Service implements com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparation {

    public LoadingPreparationLocator() {
    }


    public LoadingPreparationLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoadingPreparationLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LoadingPreparationSOAPport_http
    private java.lang.String LoadingPreparationSOAPport_http_address = "http://localhost:1443";

    public java.lang.String getLoadingPreparationSOAPport_httpAddress() {
        return LoadingPreparationSOAPport_http_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LoadingPreparationSOAPport_httpWSDDServiceName = "LoadingPreparationSOAPport_http";

    public java.lang.String getLoadingPreparationSOAPport_httpWSDDServiceName() {
        return LoadingPreparationSOAPport_httpWSDDServiceName;
    }

    public void setLoadingPreparationSOAPport_httpWSDDServiceName(java.lang.String name) {
        LoadingPreparationSOAPport_httpWSDDServiceName = name;
    }

    public com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType getLoadingPreparationSOAPport_http() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LoadingPreparationSOAPport_http_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLoadingPreparationSOAPport_http(endpoint);
    }

    public com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType getLoadingPreparationSOAPport_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationSoapBindingStub _stub = new com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationSoapBindingStub(portAddress, this);
            _stub.setPortName(getLoadingPreparationSOAPport_httpWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLoadingPreparationSOAPport_httpEndpointAddress(java.lang.String address) {
        LoadingPreparationSOAPport_http_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationSoapBindingStub _stub = new com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationSoapBindingStub(new java.net.URL(LoadingPreparationSOAPport_http_address), this);
                _stub.setPortName(getLoadingPreparationSOAPport_httpWSDDServiceName());
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
        if ("LoadingPreparationSOAPport_http".equals(inputPortName)) {
            return getLoadingPreparationSOAPport_http();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/LoadingPreparation", "LoadingPreparation");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/LoadingPreparation", "LoadingPreparationSOAPport_http"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LoadingPreparationSOAPport_http".equals(portName)) {
            setLoadingPreparationSOAPport_httpEndpointAddress(address);
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
