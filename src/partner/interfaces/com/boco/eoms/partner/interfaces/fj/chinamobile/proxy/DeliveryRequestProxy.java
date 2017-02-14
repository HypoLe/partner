package com.boco.eoms.partner.interfaces.fj.chinamobile.proxy;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.fj.chinamobile.bo.DeliveryRequestStub;
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
 * Class DeliveryRequestProxy.
 */
public class DeliveryRequestProxy {

	public static String requesterId = "Web Service Proxy TEST";

	public static String doDeliveryRequest(String accessPoint, javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, java.lang.String filter, java.lang.String dataReadyRequestUri) throws Exception {

		SOAPModel model = new SOAPModel();
		if(!accessPoint.equals("")){
			model.setAccessPoint(accessPoint);
		}
		DeliveryRequestStub.timeout=600000;
		DeliveryRequestStub.DeliveryRequestRequest req = new DeliveryRequestStub.DeliveryRequestRequest();
		req.setEventID(eventID.value);
		req.setSendTime(sendTime);
		req.setFilter(filter);
		req.setSystemID(systemID);
		req.setDataReadyRequestUri(dataReadyRequestUri);
		
		
		model.setParamTypes(new Class[]{DeliveryRequestStub.DeliveryRequestRequest.class});
		model.setParamObjs(new Object[]{req});
		
		Object obj = ServiceProxy.getInstance().sendReceive(requesterId,
			"www.fj.chinamobile.com/DataCenterSOAP/DeliveryRequestDataRequest", model, null);
		if (DebuggingLog.willPrintDetailedInfo()) {
			DebuggingLog
					.printDetailedInfo("doDeliveryRequest() - completed successfully!");
		}
		eventID=new StringHolder(((DeliveryRequestStub.DeliveryRequestRequestResponse)obj).getEventID());
		return ((DeliveryRequestStub.DeliveryRequestRequestResponse)obj).getEventID();
	}
	
	

	public static void main(String[] args) {
		String configurationFileName = null;
		try {
			configurationFileName = args[0];
		} catch (ArrayIndexOutOfBoundsException ex) {
			configurationFileName = "D:\\TNMS\\TNMS\\tnms-conf\\resesb\\serviceproxy.ini";
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
			DeliveryRequestProxy testClient = new DeliveryRequestProxy();
			StringHolder eventID = new StringHolder("IRMS");
			String systemID = "IRMS";
			Calendar sendTime = new GregorianCalendar();
			String dataReadyRequestUri = "http://10.46.1.236:8080/irmsrtu/services/DeliveryReadySOAPport_http";
			//����ļ�
			String filter = "RADIO_CELL_G.null";
			//����ַ�
			///String filter = "RADIO_CELL_G.ONLINE_STATE=1";//����ݿ��Ҳ鵽�����ʱ���xml��ʽ���ַ�
			String accessPoint ="http://10.46.1.236:8080/irmsrtu_north/services/DeliveryFeedbackSOAPport_http";
			
			System.out.println(testClient.doDeliveryRequest(accessPoint,eventID, systemID, sendTime, filter, dataReadyRequestUri));
			//-------------------------------------------------------
			// > Shutdown the SOA Messaging
			//-------------------------------------------------------
			SOAFactory.shutdownClient();
		} catch (SOAException soex) {
			ResSystem.getLog().note(
					MessageLog.SEV_ERROR,
					"DeliveryRequestProxy",
					"DeliveryRequestProxy::main() - execution failure: SOAException encountered: "
							+ soex.getMessage());
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResSystem.getLog().note(MessageLog.SEV_ERROR, "DeliveryRequestProxy",
					"DeliveryRequestProxy::main() - - startup or execution failure");
			System.exit(0);
		}
	}
}
