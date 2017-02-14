package com.boco.eoms.partner.interfaces.dataprovider.proxy;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.dataprovider.bo.LoadingFeedbackStub;
import com.res.base.core.ResSystem;
import com.res.base.logs.DebuggingLog;
import com.res.base.logs.MessageLog;
import com.res.soa.message.SOAException;
import com.res.soa.message.SOAFactory;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import com.res.soa.message.command.SOAPModel;
import com.res.soa.proxy.ServiceProxy;

/*
 * Class LoadingFeedbackProxy.
 */
public class LoadingFeedbackProxy {

	public static String requesterId = "Web Service Proxy TEST";

	public static String doLoadingFeedback(String accessPoint,StringHolder eventId,Calendar sendTime,int endStatusCode,String endStatusDescription) throws Exception {

		// TODO Auto-generated method stub

		SOAPModel model = new SOAPModel();
		if(!accessPoint.equals("")){
			model.setAccessPoint(accessPoint);
		}
		LoadingFeedbackStub.timeout=600000;
		LoadingFeedbackStub.LoadingFeedbackRequest req = new LoadingFeedbackStub.LoadingFeedbackRequest();
		req.setEventID(eventId.value);
		req.setSendTime(sendTime);
		req.setEndStatusCode(endStatusCode);
		req.setEndStatusDescription(endStatusDescription);
		
		model.setParamTypes(new Class[]{LoadingFeedbackStub.LoadingFeedbackRequest.class});
		model.setParamObjs(new Object[]{req});
					
		LoadingFeedbackStub.LoadingFeedbackRequestResponse obj = (LoadingFeedbackStub.LoadingFeedbackRequestResponse)ServiceProxy.getInstance().sendReceive(requesterId,
			"www.dataprovider.com/DataProviderSOAP/LoadingFeedbackDataRequest", model, null);
		
		eventId.value = obj.getEventID();
		if (DebuggingLog.willPrintDetailedInfo()) {
			DebuggingLog
					.printDetailedInfo("doLoadingFeedback() - completed successfully!");
		}
		return "LoadingFeedback excute successfully";
	}
	
	

	public static void main(String[] args) {
		String configurationFileName = null;
		try {
			configurationFileName = args[0];
		} catch (ArrayIndexOutOfBoundsException ex) {
			configurationFileName = "C:/a/test/RID/workspace/DataProviderSOAP/.build/adapter/config/adapter_rid.ini";
		}
		try {
			//-------------------------------------------------------
			// > init the config file for passed INI name or default
			//-------------------------------------------------------
			ServiceProxy.getInstance().initialize(configurationFileName);
			//-------------------------------------------------------
			// > Initialize the configuration of Client
			//-------------------------------------------------------
			SOAFactory.initClient();
			//-------------------------------------------------------
			// > do the tests
			//-------------------------------------------------------
			
			StringHolder eventID = new StringHolder("IRMS");
			Calendar sendTime = new GregorianCalendar();
			int code = 0;
			String des = "";
			String accessPoint="http://10.46.0.49:8888/sample/services/LoadingFeedback";
			String ret =LoadingFeedbackProxy.doLoadingFeedback(accessPoint,eventID,sendTime,code,des);
			System.out.println(ret);
			System.out.println(eventID.value);
			//-------------------------------------------------------
			// > Shutdown the SOA Messaging
			//-------------------------------------------------------
			SOAFactory.shutdownClient();
		} catch (SOAException soex) {
			ResSystem.getLog().note(
					MessageLog.SEV_ERROR,
					"LoadingFeedbackProxy",
					"LoadingFeedbackProxy::main() - execution failure: SOAException encountered: "
							+ soex.getMessage());
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResSystem.getLog().note(MessageLog.SEV_ERROR, "LoadingFeedbackProxy",
					"LoadingFeedbackProxy::main() - - startup or execution failure");
			System.exit(0);
		}
	}
}
