/**
 * LoadingQueryLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingQuery;

public class LoadingQueryLocator extends org.apache.axis.client.Service implements com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQuery {

    public LoadingQueryLocator() {
    }


    public LoadingQueryLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoadingQueryLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LoadingQuerySOAPport_http
    private java.lang.String LoadingQuerySOAPport_http_address = "http://localhost:1446";

    public java.lang.String getLoadingQuerySOAPport_httpAddress() {
        return LoadingQuerySOAPport_http_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LoadingQuerySOAPport_httpWSDDServiceName = "LoadingQuerySOAPport_http";

    public java.lang.String getLoadingQuerySOAPport_httpWSDDServiceName() {
        return LoadingQuerySOAPport_httpWSDDServiceName;
    }

    public void setLoadingQuerySOAPport_httpWSDDServiceName(java.lang.String name) {
        LoadingQuerySOAPport_httpWSDDServiceName = name;
    }

    public com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType getLoadingQuerySOAPport_http() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LoadingQuerySOAPport_http_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLoadingQuerySOAPport_http(endpoint);
    }

    public com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType getLoadingQuerySOAPport_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQuerySoapBindingStub _stub = new com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQuerySoapBindingStub(portAddress, this);
            _stub.setPortName(getLoadingQuerySOAPport_httpWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLoadingQuerySOAPport_httpEndpointAddress(java.lang.String address) {
        LoadingQuerySOAPport_http_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQuerySoapBindingStub _stub = new com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQuerySoapBindingStub(new java.net.URL(LoadingQuerySOAPport_http_address), this);
                _stub.setPortName(getLoadingQuerySOAPport_httpWSDDServiceName());
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
        if ("LoadingQuerySOAPport_http".equals(inputPortName)) {
            return getLoadingQuerySOAPport_http();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/LoadingQuery", "LoadingQuery");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/LoadingQuery", "LoadingQuerySOAPport_http"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LoadingQuerySOAPport_http".equals(portName)) {
            setLoadingQuerySOAPport_httpEndpointAddress(address);
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
