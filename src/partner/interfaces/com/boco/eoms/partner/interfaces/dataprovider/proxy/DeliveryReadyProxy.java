package com.boco.eoms.partner.interfaces.dataprovider.proxy;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.dataprovider.bo.DeliveryReadyStub;
import com.res.base.core.ResSystem;
import com.res.base.logs.DebuggingLog;
import com.res.base.logs.MessageLog;
import com.res.soa.message.SOAException;
import com.res.soa.message.SOAFactory;
import javax.xml.rpc.holders.StringHolder;

import com.res.soa.message.command.SOAPModel;
import com.res.soa.proxy.ServiceProxy;

/*
 * Class DeliveryReadyProxy.
 */
public class DeliveryReadyProxy {

	public static String requesterId = "Web Service Proxy TEST";

	public static  String doDeliveryReady(String accessPoint, StringHolder eventId,Calendar deliveryTime,
			int charset, int workMode, int fileFormat, String conn, String dataInfo,
			String description, String fieldNameList,
			int fieldSeparator, boolean isCompressed,
			int lineSeparator, String path, int readyStatusCode,
			String xmlSchema,String systemId) throws Exception {

		SOAPModel model = new SOAPModel();
		
		if(!accessPoint.equals("")){
			model.setAccessPoint(accessPoint);
		}
		DeliveryReadyStub.timeout=600000;
		DeliveryReadyStub.DeliveryReadyRequest req = new DeliveryReadyStub.DeliveryReadyRequest();
		req.setCharSet(charset);
		req.setConnectionString(conn);
		req.setDataInfo(dataInfo);
		req.setSendTime(deliveryTime);
		req.setFieldNameList(fieldNameList);
		req.setEventID(eventId.toString());
		req.setFieldSeparator(fieldSeparator);
		req.setFileFormat(fileFormat);
		req.setIsCompressed(isCompressed);
		req.setLineSeparator(lineSeparator);
		req.setPath(path);
		req.setReadyStatusCode(readyStatusCode);
		req.setWorkMode(workMode);
		req.setXmlSchema(xmlSchema);
		req.setReadyStatusDescription(description);

		model.setParamTypes(new Class[] { DeliveryReadyStub.DeliveryReadyRequest.class });
		model.setParamObjs(new Object[] { req });
					
		DeliveryReadyStub.DeliveryReadyRequestResponse obj = (DeliveryReadyStub.DeliveryReadyRequestResponse)ServiceProxy.getInstance().sendReceive(requesterId,
			"www.dataprovider.com/DataProviderSOAP/DeliveryReadyDataRequest", model, null);
		eventId.value = obj.getEventID();
		systemId = obj.getSystemID();
		if (DebuggingLog.willPrintDetailedInfo()) {
			DebuggingLog
					.printDetailedInfo("doDeliveryReady() - completed successfully!");
		}
		return "DeliveryReady excute successfully";
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
			
			int charset = 0;
			String conn = "";
			String dataInfo = "";
			Calendar deliveryTime = new GregorianCalendar(2008, 9, 1, 10, 10,
					10);
			String description = "";
			String fieldNameList = ",";
			StringHolder eventId =new StringHolder( "IRMS200811141424112241");
			int fieldSeparator = 0;
			int fileFormat = 0;
			boolean isCompressed = true;
			int lineSeparator = 0;
			String path = "";
			int readyStatusCode = 0;
			int workMode = 0;
			String xmlSchema = "";
			String accessPoint = "http://10.46.0.49:8888/sample/services/DeliveryReady";
			String systemID="";
			System.out.println(DeliveryReadyProxy.doDeliveryReady(accessPoint, eventId,deliveryTime, charset,  workMode,  fileFormat,  conn,  dataInfo,
					  description, fieldNameList,
					 fieldSeparator,  isCompressed,
					 lineSeparator,  path,  readyStatusCode,
					 xmlSchema,systemID));
			System.out.println(systemID);
			System.out.println(eventId.value);
			//-------------------------------------------------------
			// > Shutdown the SOA Messaging
			//-------------------------------------------------------
			SOAFactory.shutdownClient();
		} catch (SOAException soex) {
			ResSystem.getLog().note(
					MessageLog.SEV_ERROR,
					"DeliveryReadyProxy",
					"DeliveryReadyProxy::main() - execution failure: SOAException encountered: "
							+ soex.getMessage());
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResSystem.getLog().note(MessageLog.SEV_ERROR, "DeliveryReadyProxy",
					"DeliveryReadyProxy::main() - - startup or execution failure");
			System.exit(0);
		}
	}
}
