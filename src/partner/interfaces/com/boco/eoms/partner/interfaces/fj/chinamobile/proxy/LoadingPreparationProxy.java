package com.boco.eoms.partner.interfaces.fj.chinamobile.proxy;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.fj.chinamobile.bo.LoadingPreparationStub;
import com.res.base.core.ResSystem;
import com.res.base.logs.DebuggingLog;
import com.res.base.logs.MessageLog;
import com.res.soa.message.SOAException;
import com.res.soa.message.SOAFactory;
import javax.xml.namespace.QName;
import javax.xml.rpc.holders.BooleanHolder;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import com.res.esb.soap.connector.AXISRegistryType;
import com.res.soa.message.command.SOAPModel;
import com.res.soa.proxy.ServiceProxy;

/*
 * Class LoadingPreparationProxy.
 */
public class LoadingPreparationProxy {

	public static String requesterId = "Web Service Proxy TEST";

	public static LoadingPreparationStub.LoadingPreparationRequestResponse doLoadingPreparation(
			String accessPoint,
			javax.xml.rpc.holders.StringHolder eventID, 
    		java.lang.String systemID, java.util.Calendar sendTime, 
    		int suggestWorkMode, int suggestFileFormat, int suggestCharSet, 
    		javax.xml.rpc.holders.IntHolder workMode, javax.xml.rpc.holders.IntHolder charSet, 
    		javax.xml.rpc.holders.StringHolder connectionString, javax.xml.rpc.holders.StringHolder path, 
    		javax.xml.rpc.holders.BooleanHolder isCompressed) throws Exception {

		SOAPModel model = new SOAPModel();
		if(!accessPoint.equals("")){
			model.setAccessPoint(accessPoint);
		}
		LoadingPreparationStub.timeout=600000;
		LoadingPreparationStub.LoadingPreparationRequest req = new LoadingPreparationStub.LoadingPreparationRequest();	
		req.setEventID(eventID.value);
		req.setSystemID(systemID);
		req.setSendTime(sendTime);
		req.setSuggestCharSet(suggestCharSet);
		req.setSuggestFileFormat(suggestFileFormat);
		req.setSuggestWorkMode(workMode.value);
	
//		//test
//		LoadingPreparationStub stub = new LoadingPreparationStub();
//		LoadingPreparationStub.LoadingPreparationRequestResponse result = stub.LoadingPreparationRequest(req);
//		System.out.println(result.getConnectionString());
//		//test end
		
		model.setParamTypes(new Class[]{LoadingPreparationStub.LoadingPreparationRequest.class});
		model.setParamObjs(new Object[]{req});
					
		Object obj = ServiceProxy.getInstance().sendReceive(requesterId,
			"www.fj.chinamobile.com/DataCenterSOAP/LoadingPreparationDataRequest", model, LoadingPreparationStub.class);
		charSet.value =((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getCharSet();
		connectionString.value =((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getConnectionString();
		eventID.value = ((LoadingPreparationStub.LoadingPreparationRequestResponse)obj). getEventID();
		isCompressed.value= ((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getIsCompressed();
		path.value=((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getPath();
		workMode.value = ((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getWorkMode();
		
//		charSet = new IntHolder(((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getCharSet());
//		connectionString = new StringHolder(((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getConnectionString());
//		eventID = new StringHolder(((LoadingPreparationStub.LoadingPreparationRequestResponse)obj). getEventID());
//		isCompressed =new BooleanHolder(((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getIsCompressed());
//		path = new StringHolder(((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getPath());
//		workMode = new IntHolder(((LoadingPreparationStub.LoadingPreparationRequestResponse)obj).getWorkMode());
		if (DebuggingLog.willPrintDetailedInfo()) {
			DebuggingLog.printDetailedInfo("doLoadingPreparation() - completed successfully!");
		}
		return (LoadingPreparationStub.LoadingPreparationRequestResponse)obj;
	}
	
	

	public static void main(String[] args) {
		String configurationFileName = null;
		try {
			configurationFileName = args[0];
		} catch (ArrayIndexOutOfBoundsException ex) {
			configurationFileName = "D:/linduo/ResSynchronization/ResSynchronization/conf/serviceproxyNewland.ini";
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
			LoadingPreparationProxy testClient = new LoadingPreparationProxy();
			
			StringHolder eventID = new StringHolder("IRMS");
			String systemID = "IRMS";
			Calendar sendTime = new GregorianCalendar();
			int suggestWorkMode = 1; // 0����ʾ������Ϣ�������װ�� 1����ʾ�����ļ��������װ�� 9������ṩ���޽���
			int suggestCharSet = 0; //0��GBK 1��UTF-16��Unicode��  2��UTF-8 9������ṩ���޽���
			int suggestFileFormat = 2; 
			IntHolder workMode = new  IntHolder();
			IntHolder charSet = new  IntHolder();
    		
    		javax.xml.rpc.holders.StringHolder connectionString = new StringHolder() ;
    		javax.xml.rpc.holders.StringHolder path= new StringHolder();
    		javax.xml.rpc.holders.BooleanHolder isCompressed= new BooleanHolder();
    		String accessPoint ="http://10.46.1.236:8080/irmsrtu2.0/services/LoadingPreparationSOAPport_http";
			LoadingPreparationStub.LoadingPreparationRequestResponse ret = 
				LoadingPreparationProxy.
				doLoadingPreparation(accessPoint,eventID,systemID,sendTime,suggestWorkMode,suggestFileFormat,suggestCharSet,
						workMode,charSet,connectionString,path,isCompressed);
			
			System.out.println(path.value);
			System.out.println(connectionString.value);
			System.out.println(isCompressed.value);
			//-------------------------------------------------------
			// > Shutdown the SOA Messaging
			//-------------------------------------------------------
			SOAFactory.shutdownClient();
		} catch (SOAException soex) {
			ResSystem.getLog().note(
					MessageLog.SEV_ERROR,
					"LoadingPreparationProxy",
					"LoadingPreparationProxy::main() - execution failure: SOAException encountered: "
							+ soex.getMessage());
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResSystem.getLog().note(MessageLog.SEV_ERROR, "LoadingPreparationProxy",
					"LoadingPreparationProxy::main() - - startup or execution failure");
			System.exit(0);
		}
	}
}
