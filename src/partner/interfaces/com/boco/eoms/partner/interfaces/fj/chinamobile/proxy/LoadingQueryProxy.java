package com.boco.eoms.partner.interfaces.fj.chinamobile.proxy;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.fj.chinamobile.bo.LoadingQueryStub;
import com.res.base.core.ResSystem;
import com.res.base.logs.DebuggingLog;
import com.res.base.logs.MessageLog;
import com.res.soa.message.SOAException;
import com.res.soa.message.SOAFactory;
import javax.xml.namespace.QName;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;

import com.res.esb.soap.connector.AXISRegistryType;
import com.res.soa.message.command.SOAPModel;
import com.res.soa.proxy.ServiceProxy;

/*
 * Class LoadingQueryProxy.
 */
public class LoadingQueryProxy {

	public static String requesterId = "Web Service Proxy TEST";

	public static void doLoadingQuery(String accessPoint,javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, javax.xml.rpc.holders.IntHolder statusCode, javax.xml.rpc.holders.StringHolder statusDescription) throws Exception {

		SOAPModel model = new SOAPModel();
		if(!accessPoint.equals("")){
			model.setAccessPoint(accessPoint);
		}
		LoadingQueryStub.timeout = 600000;
		LoadingQueryStub.LoadingQueryRequest req = new LoadingQueryStub.LoadingQueryRequest();	
		req.setSendTime(sendTime);
		req.setSystemID(systemID);
		req.setEventID(eventID.value);
		model.setParamTypes(new Class[]{LoadingQueryStub.LoadingQueryRequest.class});
		model.setParamObjs(new Object[]{req});
					
		Object obj = ServiceProxy.getInstance().sendReceive(requesterId,
			"www.fj.chinamobile.com/DataCenterSOAP/LoadingQueryDataRequest", model, null);
		
		statusCode =new IntHolder(( (LoadingQueryStub.LoadingQueryRequestResponse)obj).getStatusCode());
		statusDescription = new StringHolder(( (LoadingQueryStub.LoadingQueryRequestResponse)obj).getStatusDescription());
		if (DebuggingLog.willPrintDetailedInfo()) {
			DebuggingLog
					.printDetailedInfo("doLoadingQuery() - completed successfully!");
		}
//		return obj;
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
			LoadingQueryProxy testClient = new LoadingQueryProxy();
			StringHolder eventID = new StringHolder("IRMS");
			String systemID = "IRMS";
			Calendar sendTime = new GregorianCalendar();
			IntHolder statusCode = new IntHolder();
            StringHolder statusDescription = new StringHolder();
            String accessPoint ="http://10.46.1.236:8080/irmsrtu2.0/services/LoadingQuerySOAPport_http";
			testClient.doLoadingQuery(accessPoint,eventID,systemID,sendTime,statusCode,statusDescription);
			System.out.println(statusCode);
			//-------------------------------------------------------
			// > Shutdown the SOA Messaging
			//-------------------------------------------------------
			SOAFactory.shutdownClient();
		} catch (SOAException soex) {
			ResSystem.getLog().note(
					MessageLog.SEV_ERROR,
					"LoadingQueryProxy",
					"LoadingQueryProxy::main() - execution failure: SOAException encountered: "
							+ soex.getMessage());
			soex.printStackTrace();
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResSystem.getLog().note(MessageLog.SEV_ERROR, "LoadingQueryProxy",
					"LoadingQueryProxy::main() - - startup or execution failure");
			System.exit(0);
		}
	}
}
