/**
 * SMSServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Aug 08, 2005 (11:49:10 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.contact.util.smsclient;

public class SMSServiceTestCase extends junit.framework.TestCase {
    public SMSServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testSMSServiceHttpSoap11EndpointWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.boco.eoms.partner.contact.util.smsclient.SMSServiceLocator().getSMSServiceHttpSoap11EndpointAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.boco.eoms.partner.contact.util.smsclient.SMSServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1SMSServiceHttpSoap11EndpointSmsSend() throws Exception {
        com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap11BindingStub binding;
        try {
            binding = (com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap11BindingStub)
                          new com.boco.eoms.partner.contact.util.smsclient.SMSServiceLocator().getSMSServiceHttpSoap11Endpoint();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.smsSend(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void testSMSServiceHttpSoap12EndpointWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.boco.eoms.partner.contact.util.smsclient.SMSServiceLocator().getSMSServiceHttpSoap12EndpointAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.boco.eoms.partner.contact.util.smsclient.SMSServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test2SMSServiceHttpSoap12EndpointSmsSend() throws Exception {
        com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap12BindingStub binding;
        try {
            binding = (com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap12BindingStub)
                          new com.boco.eoms.partner.contact.util.smsclient.SMSServiceLocator().getSMSServiceHttpSoap12Endpoint();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.smsSend(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

}
