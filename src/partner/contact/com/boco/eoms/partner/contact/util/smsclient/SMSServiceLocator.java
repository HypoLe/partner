/**
 * SMSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Aug 08, 2005 (11:49:10 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.contact.util.smsclient;

public class SMSServiceLocator extends org.apache.axis.client.Service implements com.boco.eoms.partner.contact.util.smsclient.SMSService {

    public SMSServiceLocator() {
    }


    public SMSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SMSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SMSServiceHttpSoap11Endpoint
    private java.lang.String SMSServiceHttpSoap11Endpoint_address = "http://10.101.16.53:8081/newSmsWebService/services/SMSService.SMSServiceHttpSoap11Endpoint/";

    public java.lang.String getSMSServiceHttpSoap11EndpointAddress() {
        return SMSServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SMSServiceHttpSoap11EndpointWSDDServiceName = "SMSServiceHttpSoap11Endpoint";

    public java.lang.String getSMSServiceHttpSoap11EndpointWSDDServiceName() {
        return SMSServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setSMSServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        SMSServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SMSServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSMSServiceHttpSoap11Endpoint(endpoint);
    }

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap11BindingStub _stub = new com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getSMSServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSMSServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        SMSServiceHttpSoap11Endpoint_address = address;
    }


    // Use to get a proxy class for SMSServiceHttpSoap12Endpoint
    private java.lang.String SMSServiceHttpSoap12Endpoint_address = "http://10.101.16.53:8081/newSmsWebService/services/SMSService.SMSServiceHttpSoap12Endpoint/";

    public java.lang.String getSMSServiceHttpSoap12EndpointAddress() {
        return SMSServiceHttpSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SMSServiceHttpSoap12EndpointWSDDServiceName = "SMSServiceHttpSoap12Endpoint";

    public java.lang.String getSMSServiceHttpSoap12EndpointWSDDServiceName() {
        return SMSServiceHttpSoap12EndpointWSDDServiceName;
    }

    public void setSMSServiceHttpSoap12EndpointWSDDServiceName(java.lang.String name) {
        SMSServiceHttpSoap12EndpointWSDDServiceName = name;
    }

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SMSServiceHttpSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSMSServiceHttpSoap12Endpoint(endpoint);
    }

    public com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType getSMSServiceHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap12BindingStub _stub = new com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap12BindingStub(portAddress, this);
            _stub.setPortName(getSMSServiceHttpSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSMSServiceHttpSoap12EndpointEndpointAddress(java.lang.String address) {
        SMSServiceHttpSoap12Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap11BindingStub _stub = new com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap11BindingStub(new java.net.URL(SMSServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getSMSServiceHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
            if (com.boco.eoms.partner.contact.util.smsclient.SMSServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap12BindingStub _stub = new com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap12BindingStub(new java.net.URL(SMSServiceHttpSoap12Endpoint_address), this);
                _stub.setPortName(getSMSServiceHttpSoap12EndpointWSDDServiceName());
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
        if ("SMSServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getSMSServiceHttpSoap11Endpoint();
        }
        else if ("SMSServiceHttpSoap12Endpoint".equals(inputPortName)) {
            return getSMSServiceHttpSoap12Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.apache.org/axis2", "SMSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.apache.org/axis2", "SMSServiceHttpSoap11Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://ws.apache.org/axis2", "SMSServiceHttpSoap12Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SMSServiceHttpSoap11Endpoint".equals(portName)) {
            setSMSServiceHttpSoap11EndpointEndpointAddress(address);
        }
        else 
if ("SMSServiceHttpSoap12Endpoint".equals(portName)) {
            setSMSServiceHttpSoap12EndpointEndpointAddress(address);
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
