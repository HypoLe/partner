/**
 * BulletinServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.huawei.csp.si.service;

import com.boco.eoms.util.BulletinMgrLocator;

public class BulletinServiceLocator extends org.apache.axis.client.Service implements com.huawei.csp.si.service.BulletinService {

    public BulletinServiceLocator() {
    }


    public BulletinServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BulletinServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Bulletin
    private java.lang.String Bulletin_address = BulletinMgrLocator.getAttributes().getBulletinHttpPortAddress();

    public java.lang.String getBulletinAddress() {
        return Bulletin_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BulletinWSDDServiceName = "Bulletin";

    public java.lang.String getBulletinWSDDServiceName() {
        return BulletinWSDDServiceName;
    }

    public void setBulletinWSDDServiceName(java.lang.String name) {
        BulletinWSDDServiceName = name;
    }

    public com.huawei.csp.si.service.Bulletin_PortType getBulletin() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	System.out.println("Bulletin_address=====>"+Bulletin_address);
            endpoint = new java.net.URL(Bulletin_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBulletin(endpoint);
    }

    public com.huawei.csp.si.service.Bulletin_PortType getBulletin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.huawei.csp.si.service.BulletinSoapBindingStub _stub = new com.huawei.csp.si.service.BulletinSoapBindingStub(portAddress, this);
            _stub.setPortName(getBulletinWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBulletinEndpointAddress(java.lang.String address) {
        Bulletin_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.huawei.csp.si.service.Bulletin_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.huawei.csp.si.service.BulletinSoapBindingStub _stub = new com.huawei.csp.si.service.BulletinSoapBindingStub(new java.net.URL(Bulletin_address), this);
                _stub.setPortName(getBulletinWSDDServiceName());
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
        if ("Bulletin".equals(inputPortName)) {
            return getBulletin();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.168.27.221:8099/axis/services/Bulletin", "BulletinService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.168.27.221:8099/axis/services/Bulletin", "Bulletin"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Bulletin".equals(portName)) {
            setBulletinEndpointAddress(address);
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
