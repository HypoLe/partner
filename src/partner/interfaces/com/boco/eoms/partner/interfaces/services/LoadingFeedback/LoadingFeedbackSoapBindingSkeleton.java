/**
 * LoadingFeedbackSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingFeedback;

public class LoadingFeedbackSoapBindingSkeleton implements com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackPortType, org.apache.axis.wsdl.Skeleton {
    private com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackPortType impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EndStatusCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "EndStatusDescription"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "SendTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("loadingFeedbackRequest", _params, new javax.xml.namespace.QName("", "SystemID"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://ws.apache.org/axis2/services/LoadingFeedback/types", "LoadingFeedbackRequest"));
        _oper.setSoapAction("LoadingFeedback#LoadingFeedbackRequest");
        _myOperationsList.add(_oper);
        if (_myOperations.get("loadingFeedbackRequest") == null) {
            _myOperations.put("loadingFeedbackRequest", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("loadingFeedbackRequest")).add(_oper);
    }

    public LoadingFeedbackSoapBindingSkeleton() {
        this.impl = new com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackSoapBindingImpl();
    }

    public LoadingFeedbackSoapBindingSkeleton(com.boco.eoms.partner.interfaces.services.LoadingFeedback.LoadingFeedbackPortType impl) {
        this.impl = impl;
    }
    public java.lang.String loadingFeedbackRequest(javax.xml.rpc.holders.StringHolder eventID, int endStatusCode, java.lang.String endStatusDescription, java.util.Calendar sendTime) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.loadingFeedbackRequest(eventID, endStatusCode, endStatusDescription, sendTime);
        return ret;
    }

}
