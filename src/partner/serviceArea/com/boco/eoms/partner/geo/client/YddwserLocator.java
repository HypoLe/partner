/**
 * YddwserLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.geo.client;

public class YddwserLocator extends org.apache.axis.client.Service implements com.boco.eoms.partner.geo.client.Yddwser {

    public YddwserLocator() {
    }


    public YddwserLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public YddwserLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for yddwserHttpPort
    private java.lang.String yddwserHttpPort_address = "http://218.207.217.119/pdagis/services/yddwser";

    public java.lang.String getyddwserHttpPortAddress() {
        return yddwserHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String yddwserHttpPortWSDDServiceName = "yddwserHttpPort";

    public java.lang.String getyddwserHttpPortWSDDServiceName() {
        return yddwserHttpPortWSDDServiceName;
    }

    public void setyddwserHttpPortWSDDServiceName(java.lang.String name) {
        yddwserHttpPortWSDDServiceName = name;
    }

    public com.boco.eoms.partner.geo.client.YddwserPortType getyddwserHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(yddwserHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getyddwserHttpPort(endpoint);
    }

    public com.boco.eoms.partner.geo.client.YddwserPortType getyddwserHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.boco.eoms.partner.geo.client.YddwserHttpBindingStub _stub = new com.boco.eoms.partner.geo.client.YddwserHttpBindingStub(portAddress, this);
            _stub.setPortName(getyddwserHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setyddwserHttpPortEndpointAddress(java.lang.String address) {
        yddwserHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.boco.eoms.partner.geo.client.YddwserPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.boco.eoms.partner.geo.client.YddwserHttpBindingStub _stub = new com.boco.eoms.partner.geo.client.YddwserHttpBindingStub(new java.net.URL(yddwserHttpPort_address), this);
                _stub.setPortName(getyddwserHttpPortWSDDServiceName());
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
        if ("yddwserHttpPort".equals(inputPortName)) {
            return getyddwserHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://appfun33_yddw.appfun_pack", "yddwser");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://appfun33_yddw.appfun_pack", "yddwserHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("yddwserHttpPort".equals(portName)) {
            setyddwserHttpPortEndpointAddress(address);
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
