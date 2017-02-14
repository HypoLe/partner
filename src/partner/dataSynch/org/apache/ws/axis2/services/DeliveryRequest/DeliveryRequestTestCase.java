/**
 * DeliveryRequestTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Aug 08, 2005 (11:49:10 PDT) WSDL2Java emitter.
 */

package org.apache.ws.axis2.services.DeliveryRequest;

public class DeliveryRequestTestCase extends junit.framework.TestCase {
    public DeliveryRequestTestCase(java.lang.String name) {
        super(name);
    }

    public void testDeliveryRequestSOAPport_httpWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestLocator().getDeliveryRequestSOAPport_httpAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1DeliveryRequestSOAPport_httpDeliveryRequestRequest() throws Exception {
        org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestSOAPport_httpSoapBindingStub binding;
        try {
            binding = (org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestSOAPport_httpSoapBindingStub)
                          new org.apache.ws.axis2.services.DeliveryRequest.DeliveryRequestLocator().getDeliveryRequestSOAPport_http();
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
        binding.deliveryRequestRequest(new javax.xml.rpc.holders.StringHolder(new java.lang.String()), new java.lang.String(), java.util.Calendar.getInstance(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

}
