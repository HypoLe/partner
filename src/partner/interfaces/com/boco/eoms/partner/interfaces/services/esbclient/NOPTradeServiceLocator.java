/**
 * NOPTradeServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.esbclient;

public class NOPTradeServiceLocator extends org.apache.axis.client.Service implements com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeService {

    public NOPTradeServiceLocator() {
    }


    public NOPTradeServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NOPTradeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NOPTradeServiceHttpPort
    private java.lang.String NOPTradeServiceHttpPort_address = "http://10.168.68.179:7800/NOPTrade/services/NOPTradeService";

    public java.lang.String getNOPTradeServiceHttpPortAddress() {
        return NOPTradeServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NOPTradeServiceHttpPortWSDDServiceName = "NOPTradeServiceHttpPort";

    public java.lang.String getNOPTradeServiceHttpPortWSDDServiceName() {
        return NOPTradeServiceHttpPortWSDDServiceName;
    }

    public void setNOPTradeServiceHttpPortWSDDServiceName(java.lang.String name) {
        NOPTradeServiceHttpPortWSDDServiceName = name;
    }

    public com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeServicePortType getNOPTradeServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NOPTradeServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNOPTradeServiceHttpPort(endpoint);
    }

    public com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeServicePortType getNOPTradeServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeServiceHttpBindingStub _stub = new com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getNOPTradeServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNOPTradeServiceHttpPortEndpointAddress(java.lang.String address) {
        NOPTradeServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeServiceHttpBindingStub _stub = new com.boco.eoms.partner.interfaces.services.esbclient.NOPTradeServiceHttpBindingStub(new java.net.URL(NOPTradeServiceHttpPort_address), this);
                _stub.setPortName(getNOPTradeServiceHttpPortWSDDServiceName());
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
        if ("NOPTradeServiceHttpPort".equals(inputPortName)) {
            return getNOPTradeServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("cn.com.boco.NOPTradeService", "NOPTradeService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("cn.com.boco.NOPTradeService", "NOPTradeServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("NOPTradeServiceHttpPort".equals(portName)) {
            setNOPTradeServiceHttpPortEndpointAddress(address);
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
