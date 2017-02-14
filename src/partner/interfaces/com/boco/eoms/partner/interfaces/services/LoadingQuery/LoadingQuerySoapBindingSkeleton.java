/**
 * LoadingQuerySoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingQuery;

public class LoadingQuerySoapBindingSkeleton implements com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType, org.apache.axis.wsdl.Skeleton {
    private com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EventID"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SystemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SendTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "StatusCode"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "StatusDescription"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("loadingQueryRequest", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/LoadingQuery/types", "LoadingQueryRequest"));
        _oper.setSoapAction("LoadingQuery#LoadingQueryRequest");
        _myOperationsList.add(_oper);
        if (_myOperations.get("loadingQueryRequest") == null) {
            _myOperations.put("loadingQueryRequest", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("loadingQueryRequest")).add(_oper);
    }

    public LoadingQuerySoapBindingSkeleton() {
        this.impl = new com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQuerySoapBindingImpl();
    }

    public LoadingQuerySoapBindingSkeleton(com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType impl) {
        this.impl = impl;
    }
    public void loadingQueryRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, javax.xml.rpc.holders.IntHolder statusCode, javax.xml.rpc.holders.StringHolder statusDescription) throws java.rmi.RemoteException
    {
        impl.loadingQueryRequest(eventID, systemID, sendTime, statusCode, statusDescription);
    }

}
