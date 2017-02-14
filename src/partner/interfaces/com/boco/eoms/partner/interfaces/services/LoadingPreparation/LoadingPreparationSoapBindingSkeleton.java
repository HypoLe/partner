/**
 * LoadingPreparationSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingPreparation;

public class LoadingPreparationSoapBindingSkeleton implements com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType, org.apache.axis.wsdl.Skeleton {
    private com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SuggestWorkMode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SuggestFileFormat"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SuggestCharSet"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "WorkMode"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CharSet"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ConnectionString"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Path"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "IsCompressed"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("loadingPreparationRequest", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/LoadingPreparation/types", "LoadingPreparationRequest"));
        _oper.setSoapAction("LoadingPreparation#LoadingPreparationRequest");
        _myOperationsList.add(_oper);
        if (_myOperations.get("loadingPreparationRequest") == null) {
            _myOperations.put("loadingPreparationRequest", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("loadingPreparationRequest")).add(_oper);
    }

    public LoadingPreparationSoapBindingSkeleton() {
        this.impl = new com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationSoapBindingImpl();
    }

    public LoadingPreparationSoapBindingSkeleton(com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType impl) {
        this.impl = impl;
    }
    public void loadingPreparationRequest(javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, int suggestWorkMode, int suggestFileFormat, int suggestCharSet, javax.xml.rpc.holders.IntHolder workMode, javax.xml.rpc.holders.IntHolder charSet, javax.xml.rpc.holders.StringHolder connectionString, javax.xml.rpc.holders.StringHolder path, javax.xml.rpc.holders.BooleanHolder isCompressed) throws java.rmi.RemoteException
    {
        impl.loadingPreparationRequest(eventID, systemID, sendTime, suggestWorkMode, suggestFileFormat, suggestCharSet, workMode, charSet, connectionString, path, isCompressed);
    }

}
