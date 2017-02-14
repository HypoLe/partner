package com.boco.eoms.partner.interfaces.fj.chinamobile.proxy;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.fj.chinamobile.bo.DeliveryFeedbackStub;
import com.res.base.core.ResSystem;
import com.res.base.logs.DebuggingLog;
import com.res.base.logs.MessageLog;
import com.res.soa.message.SOAException;
import com.res.soa.message.SOAFactory;
import javax.xml.namespace.QName;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import com.res.esb.soap.connector.AXISRegistryType;
import com.res.soa.message.command.SOAPModel;
import com.res.soa.proxy.ServiceProxy;

/*
 * Class DeliveryFeedbackProxy.
 */
public class DeliveryFeedbackProxy {

	public static String requesterId = "Web Service Proxy TEST";

	public static void doDeliveryFeedback(String accessPoint, javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, int endStatusCode, java.lang.String endStatusDescription, java.util.Calendar sendTime) throws Exception {

		SOAPModel model = new SOAPModel();
		DeliveryFeedbackStub.timeout = 600000;
		DeliveryFeedbackStub.DeliveryFeedbackRequest req = new DeliveryFeedbackStub.DeliveryFeedbackRequest();
		if(!accessPoint.equals("")){
			model.setAccessPoint(accessPoint);
		}
		req.setEventID(eventID.value);
		req.setSendTime(sendTime);
		req.setSystemID(systemID);
		req.setEndStatusCode(endStatusCode);
		req.setEndStatusDescription(endStatusDescription);
		
		model.setParamTypes(new Class[]{DeliveryFeedbackStub.DeliveryFeedbackRequest.class});
		model.setParamObjs(new Object[]{req});
					
		DeliveryFeedbackStub.DeliveryFeedbackRequestResponse obj = (DeliveryFeedbackStub.DeliveryFeedbackRequestResponse)ServiceProxy.getInstance().sendReceive(requesterId,
			"www.fj.chinamobile.com/DataCenterSOAP/DeliveryFeedbackDataRequest", model, null);
		

		if (DebuggingLog.willPrintDetailedInfo()) {
			DebuggingLog
					.printDetailedInfo("doDeliveryFeedback() - completed successfully!");
		}
		
		eventID = new StringHolder(((DeliveryFeedbackStub.DeliveryFeedbackRequestResponse)obj).getEventID());
//		return obj.getEventID();
	}
	
	

	public static void main(String[] args) {
		String configurationFileName = null;
		try {
			configurationFileName = args[0];
		} catch (ArrayIndexOutOfBoundsException ex) {
			configurationFileName = "C:/a/test/RID/workspace/DataCenterSOAP/.build/adapter/config/adapter_rid.ini";
		}
		try {
			//-------------------------------------------------------
			// > init the config file for passed INI name or default
			//-------------------------------------------------------
			ServiceProxy.getInstance().initialize(configurationFileName);
			//-------------------------------------------------------
			// > Initialize the configuration of Client
			//-------------------------------------------------------
//			SOAFactory.initClient();
			//-------------------------------------------------------
			// > do the tests
			//-------------------------------------------------------
			DeliveryFeedbackProxy testClient = new DeliveryFeedbackProxy();
			int endStatusCode=1;
			StringHolder eventID =new StringHolder("IRMS");
			String systemID = "IRMS";
			Calendar sendTime = new GregorianCalendar();
			String accessPoint ="http://10.46.1.236:8080/irmsrtu_north/services/DeliveryFeedbackSOAPport_http";
			testClient.doDeliveryFeedback(accessPoint,eventID,systemID,endStatusCode,"description",sendTime);
			System.out.println(eventID.value);
			//-------------------------------------------------------
			// > Shutdown the SOA Messaging
			//-------------------------------------------------------
			SOAFactory.shutdownClient();
		} catch (SOAException soex) {
			ResSystem.getLog().note(
					MessageLog.SEV_ERROR,
					"DeliveryFeedbackProxy",
					"DeliveryFeedbackProxy::main() - execution failure: SOAException encountered: "
							+ soex.getMessage());
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResSystem.getLog().note(MessageLog.SEV_ERROR, "DeliveryFeedbackProxy",
					"DeliveryFeedbackProxy::main() - - startup or execution failure");
			System.exit(0);
		}
	}
}
