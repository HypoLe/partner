/**
 * EomsPlatFormServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.commons.system.user.webClient;

public class EomsPlatFormServiceServiceLocator extends
		org.apache.axis.client.Service implements
		com.boco.eoms.commons.system.user.webClient.EomsPlatFormServiceService {

	public EomsPlatFormServiceServiceLocator() {
	}

	public EomsPlatFormServiceServiceLocator(
			org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public EomsPlatFormServiceServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for EomsPlatFormService
	private java.lang.String EomsPlatFormService_address = "http://10.168.87.187:8080/partner/services/FriendService";

	public java.lang.String getEomsPlatFormServiceAddress() {
		return EomsPlatFormService_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String EomsPlatFormServiceWSDDServiceName = "EomsPlatFormService";

	public java.lang.String getEomsPlatFormServiceWSDDServiceName() {
		return EomsPlatFormServiceWSDDServiceName;
	}

	public void setEomsPlatFormServiceWSDDServiceName(java.lang.String name) {
		EomsPlatFormServiceWSDDServiceName = name;
	}

	public com.boco.eoms.commons.system.user.webClient.EomsPlatFormService_PortType getEomsPlatFormService()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(EomsPlatFormService_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getEomsPlatFormService(endpoint);
	}

	public com.boco.eoms.commons.system.user.webClient.EomsPlatFormService_PortType getEomsPlatFormService(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.boco.eoms.commons.system.user.webClient.EomsPlatFormServiceSoapBindingStub _stub = new com.boco.eoms.commons.system.user.webClient.EomsPlatFormServiceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getEomsPlatFormServiceWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setEomsPlatFormServiceEndpointAddress(java.lang.String address) {
		EomsPlatFormService_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (com.boco.eoms.commons.system.user.webClient.EomsPlatFormService_PortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				com.boco.eoms.commons.system.user.webClient.EomsPlatFormServiceSoapBindingStub _stub = new com.boco.eoms.commons.system.user.webClient.EomsPlatFormServiceSoapBindingStub(
						new java.net.URL(EomsPlatFormService_address), this);
				_stub.setPortName(getEomsPlatFormServiceWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("EomsPlatFormService".equals(inputPortName)) {
			return getEomsPlatFormService();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"http://localhost:8087/eoms/services/EomsPlatFormService",
				"EomsPlatFormServiceService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://localhost:8087/eoms/services/EomsPlatFormService",
					"EomsPlatFormService"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("EomsPlatFormService".equals(portName)) {
			setEomsPlatFormServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
